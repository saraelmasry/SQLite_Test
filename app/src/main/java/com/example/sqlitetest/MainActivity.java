package com.example.sqlitetest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Filter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sqlitetest.pojo.DBManager;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
     Button save,load,delete,update,updateNow;
     EditText name ,pass;
    SharedPreference sharedPreference;
    Context context;
    DBManager dbManager;
     int RecordID;
    ArrayList<AdapterListView>    listnewsData = new ArrayList<AdapterListView>();
    MyCustomAdapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inti();

        ///look in it again!!!!
        // must be "this" instead of "context"
        dbManager=new DBManager (context);
    }

    public void inti() {
        save=findViewById(R.id.save);
        load=findViewById(R.id.load);
        delete=findViewById(R.id.btnDelete);
        update=findViewById(R.id.btnUpdate);
        updateNow=findViewById(R.id.updateNow);
        name=findViewById(R.id.name);
        pass=findViewById(R.id.Pass);
        sharedPreference=new SharedPreference(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save :
                btnSave();
                break;

            case R.id.updateNow:
                btnUpdate();
                break;

            case R.id.load:
                btnLoad();
                break;

        }

    }



    private void btnSave(){
        sharedPreference.SaveData(name.getText().toString(),pass.getText().toString());
        ContentValues values=new ContentValues();
        values.put(DBManager.ColUserName,name.getText().toString());
        values.put(DBManager.ColPassword,pass.getText().toString());
        long id= dbManager.Insert(values);
        if(id>0)
            Toast.makeText(context,"data is added and user id: "+id,Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context,"cannot insert ",Toast.LENGTH_LONG).show();}


    private void btnUpdate() {
        ContentValues values=new ContentValues();
        values.put(DBManager.ColUserName,name.getText().toString());
        values.put(DBManager.ColPassword,pass.getText().toString());
        values.put(DBManager.ColID,RecordID);
        String[]SelectionArgs={String.valueOf(RecordID)};
        dbManager.Update(values,"ID=?",SelectionArgs);
    }


    private void btnLoad(){
        //String Data=sharedPreference.LoadData();
        // String[] projection={"UserName","Password"};
        String[] SelectionArgs={"%"+name.getText().toString()+"%"};
        listnewsData.clear();
        ////////////////////////////////////////////
        Cursor cursor=dbManager.Query(null,"UserName like ?",SelectionArgs,DBManager.ColUserName);

        if (cursor.moveToFirst()){
            String TableData="";
            do {                              /*TableData +=cursor.getString(cursor.getColumnIndex(DBManager.ColUserName))+" , "+
                                cursor.getString(cursor.getColumnIndex(DBManager.ColPassword))+"::";
*/
                listnewsData.add(new AdapterListView(cursor.getInt(cursor.getColumnIndex(DBManager.ColID)),
                        cursor.getString(cursor.getColumnIndex(DBManager.ColUserName)),
                        cursor.getString(cursor.getColumnIndex(DBManager.ColPassword))));

            }while (cursor.moveToNext());
            Toast.makeText(context,TableData,Toast.LENGTH_LONG).show();
        }

        myadapter=new MyCustomAdapter(listnewsData);
        ListView ListNews=(ListView)findViewById(R.id.ListNews);
        ListNews.setAdapter(myadapter);

    }


    public SharedPreferences getSharedPreferences(String myRef, int modePrivate) {
        return null;
    }

    private class MyCustomAdapter extends BaseAdapter {
        public  ArrayList<AdapterListView>  listnewsDataAdpater ;

        public MyCustomAdapter(ArrayList<AdapterListView> listnewsDataAdpater) {
            this.listnewsDataAdpater=listnewsDataAdpater;
        }


        @Override
        public int getCount() {
            return listnewsDataAdpater.size();
        }

        @Override
        public String getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            Context context = null;
            LayoutInflater mInflater =  LayoutInflater.from(context);
            @SuppressLint("ViewHolder") View myView = mInflater.inflate(R.layout.layout_ticket, null);

            final   AdapterListView adapterListView = listnewsDataAdpater.get(position);

            TextView txtID=myView.findViewById(R.id.txtID);
            txtID.setText(String.valueOf(adapterListView.ID));

            TextView txtUserName=myView.findViewById(R.id.txtUserName);
            txtUserName.setText(adapterListView.UserName);

            TextView txtPassword=myView.findViewById(R.id.txtPassword);
            txtPassword.setText(adapterListView.Password);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] SelectionArgs={String.valueOf(adapterListView.ID)};
                    int count=dbManager.Delete("ID=?",SelectionArgs);
                    if(count>0){
                        btnLoad();
                    }
                }
            });

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    name.setText(adapterListView.UserName);
                    pass.setText(adapterListView.Password);
                    RecordID=adapterListView.ID;

                }
            });
            return myView;
        }

    }
}