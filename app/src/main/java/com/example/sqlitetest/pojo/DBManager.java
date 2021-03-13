package com.example.sqlitetest.pojo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.widget.Toast;

import com.example.sqlitetest.MainActivity;

public class DBManager {
    private SQLiteDatabase sqLiteDatabase;
    // create database "students" & create table "login" & make columns "username" , "password"
    static final String DBName = "Students";
    static final String TableName = "Login";
    public static final String ColID = "ID";
    public static final String ColUserName = "UserName";
    public static final String ColPassword = "Password";
    static final int DBVersion = 3;

    // create table login(ID INTEGER PRIMARY KEY Not null "autoincrement", UserName TEXT Not null ,Password TEXT NOT NULL)
    // CREATE TABLE "login" (
    //       "ID"	INTEGER,"UserName"	TEXT NOT NULL,"Password"	TEXT NOT NULL,PRIMARY KEY("ID" AUTOINCREMENT));
    static final String CreateTable = "CREATE TABLE IF NOT EXISTS " + TableName +
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT , " + ColUserName
            + "TEXT, " + ColPassword + " TEXT);";


    static class DataBaseHelperUser extends SQLiteOpenHelper {
        Context context;

        public DataBaseHelperUser(Context context) {
            super(context, DBName, null, DBVersion);
            this.context = context;
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            //if database is not exist so create it
            db.execSQL(CreateTable);
            Toast.makeText(context, "Table is created", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // delete db if it is exist and get new table
            db.execSQL("Drop table IF EXISTS " + TableName);
            onCreate(db);
        }
    }

    public DBManager(Context context) {

        DataBaseHelperUser db = new DataBaseHelperUser(context);
        sqLiteDatabase = db.getWritableDatabase();
    }

    public Long Insert(ContentValues values) {
        long ID = sqLiteDatabase.insert(TableName, "", values);
        //could insert id is user id , or fail id is or equal 0
        return ID;
    }

    //select username , password from logins where id =1
    public Cursor Query(String[] Projection, String Selection,String[] SelectionArgs,String SortOrder){
        SQLiteQueryBuilder queryBuilder=new SQLiteQueryBuilder();
        queryBuilder.setTables(TableName);
       Cursor cursor=queryBuilder.query(sqLiteDatabase,Projection,Selection,SelectionArgs,null,null,SortOrder);
       return cursor;
    }

    public int Delete(String Selection,String[] SelectionArgs){
        int count =sqLiteDatabase.delete(TableName,Selection,SelectionArgs);
        return count;
    }
    public int Update(ContentValues values,String Selection,String[] SelectionArgs){
        int count =sqLiteDatabase.update(TableName,values,Selection,SelectionArgs);
        return count;
    }


}
