package com.example.sqlitetest;

public class AdapterListView {
    public   int ID;
    public  String UserName;
    public  String Password;
    //for news details


    public AdapterListView(int ID, String userName, String password) {
        this.ID = ID;
        UserName = userName;
        Password = password;
    }
}
