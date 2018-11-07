package com.example.shadhin.helloworldonlyjava;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.widget.Toast;

public class DBManager {
    private SQLiteDatabase sqlDB;

    static final String DB_NAME = "DB_Login_Reg";
    static final String TABLE_NAME = "Users";
    static final String TABLE_NAME1 = "Note";
    static final String COL_ID = "ID";
    static final String COL_USERNAME_MAIN = "User";
    static final String COL_USERNAME = "UserName";
    static final String COL_EMAIL = "Email";
    static final String COL_PASSWORD = "Password";
    static final String COL_PHONE = "Phone";
    static final String COL_BIRTHDAY = "Birthday";
    static final String COL_GENDER = "Gender";
    static final String COL_COUNTRY = "Country";
    static final String COL_ProfilePic = "ProfilePic";
    static final String COL_NOTE_TITEL = "Titel";
    static final String COL_NOTE = "Note";
    static final String COL_AGREE = "Note";
    static final int DBVersion = 1;
    static final String CreateTable = "Create table IF NOT EXISTS " + TABLE_NAME + "(ID integer PRIMARY KEY AUTOINCREMENT," + COL_USERNAME + " text," + COL_USERNAME_MAIN + " text," + COL_BIRTHDAY
            + " text," + COL_EMAIL + " text," + COL_PASSWORD + " text," + COL_PHONE + " text," + COL_GENDER + " text," + COL_AGREE + " text," + COL_COUNTRY + " text," + COL_ProfilePic + " BLOB DEFAULT NUll" + " );";
    static final String CreateTable1 = "Create table IF NOT EXISTS " + TABLE_NAME1 + "(ID integer PRIMARY KEY AUTOINCREMENT," + COL_EMAIL + " text," + COL_NOTE_TITEL + " text," + COL_NOTE + " text" + ");";

    static class DatabaseHelperUser extends SQLiteOpenHelper {
        Context context;

        DatabaseHelperUser(Context context) {
            super(context, DB_NAME, null, DBVersion);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Toast.makeText(context, "Table is created", Toast.LENGTH_LONG).show();
            db.execSQL(CreateTable);
            db.execSQL(CreateTable1);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(" Drop table IF EXISTS " + TABLE_NAME);
            onCreate(db);
            db.execSQL(" Drop table IF EXISTS " + TABLE_NAME1);
            onCreate(db);
        }
    }

    public DBManager(Context context) {
        DatabaseHelperUser db = new DatabaseHelperUser(context);
        sqlDB = db.getWritableDatabase();
    }

    public long insert(ContentValues values) {
        long id = sqlDB.insert(TABLE_NAME, "", values);
        return id;
    }
    public long insert1(ContentValues values) {
        long id = sqlDB.insert(TABLE_NAME1, "", values);
        return id;
    }

    //select username,password from logins where id=1
    public Cursor query(String[] Projection, String Selection, String[] SelectionArgs, String SortOrder, String limit) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TABLE_NAME);
        Cursor cursor = qb.query(sqlDB, Projection, Selection, SelectionArgs, null, null, SortOrder, limit);
        return cursor;
    }
    public Cursor query1(String[] Projection, String Selection, String[] SelectionArgs, String SortOrder, String limit) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TABLE_NAME1);
        Cursor cursor = qb.query(sqlDB, Projection, Selection, SelectionArgs, null, null, SortOrder, limit);
        return cursor;
    }

    public int Delete(String Selection, String[] SelectionArgs) {
        int id = sqlDB.delete(TABLE_NAME, Selection, SelectionArgs);
        return id;
    }

    public int update(ContentValues values, String Selection, String[] SelectionArgs) {
        int id = sqlDB.update(TABLE_NAME, values, Selection, SelectionArgs);
        return id;
    }
    /*public int addToDbImage(ContentValues values, String Selection, String[] SelectionArgs){
       // ContentValues contentValues=new ContentValues();
        //contentValues.put(COL_ProfilePic,image);
        //sqlDB.insert(TABLE_NAME,"",contentValues);
        int id = sqlDB.update(TABLE_NAME, values, Selection, SelectionArgs);
        return id;
    }*/
    /*public int login(String[] Projection, String Selection, String[] SelectionArgs){
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TABLE_NAME);
        int id=qb.query(sqlDB,Projection,Selection,SelectionArgs,null,null,null,"1");
        return id;
    }*/
}























