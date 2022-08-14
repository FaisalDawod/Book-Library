package com.example.project_test2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    //Create Variables
    private Context context;
    //private static final String or any DataType --> Constant
    private static final String DATABASE_NAME = "BookLibrary.db"; //DataBase Name
    private static final int DATABASE_VERSION = 1; //DataBase Version

    private static final String TABLE_NAME = "my_Library"; //Table Name In DataBase
    private static final String COLUMN_ID = "_id"; //Add first Column
    private static final String COLUMN_TITLE = "book_title"; //Add second Column
    private static final String COLUMN_AUTHOR = "book_author"; //Add third Column
    private static final String COLUMN_PAGES = "book_pages"; //Add forth Column

    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create Table - SQL Schema
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_AUTHOR + " TEXT, " +
                COLUMN_PAGES + " INTEGER);";

        //Check if Query Probably formatted
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //Create Another Query
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //Create a new Method
    //----Insert To Database----
    void addBook(String title, String author, int pages){
        //Create Database Object
        //this --> refer to SQLite (Back to SQLite)
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues(); //Content Values Object to store all our data from our application

        //put it is a method
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_AUTHOR, author);
        cv.put(COLUMN_PAGES, pages);
        //Store Result
        long result = db.insert(TABLE_NAME,null, cv);

        //Condition if is result it is equals to -1 that's mean the application failed to insert the data
        if(result == -1){
            Toast.makeText(context, "Failed to Insert",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Added Successfully!",Toast.LENGTH_SHORT).show();
        }
    }

    //Create a new Method
    //----Read From Database----
    //Return Cursor or Object
    Cursor readAllData(){
        //Create String Query
        String query = "SELECT * FROM " + TABLE_NAME; //Selecting all the data from Database Table
        SQLiteDatabase db =  this.getReadableDatabase(); //Create SQL Object

        //Create Cursor Object
        Cursor cursor = null;

        //Create Condition
        if(db != null){
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    //Create a new Method
    //----Update Record in Database----
    void updateData(String row_id, String title, String author, String pages){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_AUTHOR, author);
        cv.put(COLUMN_PAGES, pages);

        //Store Data in result
        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});

        //Condition
        if(result == -1){ //When result == -1 that's mean no Data ERROR Message
            Toast.makeText(context, "Failed to Update.",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Successfully Update!",Toast.LENGTH_SHORT).show();
        }
    }

    //Create a new Method
    //----Delete Record from Database----
    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    //Create a new method to Delete all Records in Database
    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}

