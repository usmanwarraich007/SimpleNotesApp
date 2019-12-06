package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.webkit.WebHistoryItem;

import androidx.annotation.Nullable;

public class DBController extends SQLiteOpenHelper {
    public DBController(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "notes.db", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE NOTES(NOTE_ID INTEGER PRIMARY KEY  AUTOINCREMENT, NOTE_TEXT TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS NOTES;");
        onCreate(db);
    }





    public void insert_notes(String note){
        ContentValues contentValues = new ContentValues();
        contentValues.put("NOTE_TEXT",note);
        this.getWritableDatabase().insertOrThrow("NOTES","",contentValues);
    }

    public void update_note(int noteid , String notetext){
        this.getWritableDatabase().execSQL("UPDATE NOTES SET NOTE_TEXT='"+notetext+"' WHERE NOTE_ID='"+noteid+"'");
    }

    public void update_student(String rollno, String new_name, String new_rollno){
        this.getWritableDatabase().execSQL("UPDATE STUDENTS SET NAME='"+new_name+"', ROLLNO='"+new_rollno+"' WHERE ROLLNO='"+rollno+"'");
    }


    public Cursor get_notes(){
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM NOTES",null);
        return cursor;
    }


    public Cursor get_note(int noteid){
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM NOTES WHERE NOTE_ID ='"+noteid+"'",null);
        return cursor;
    }

    public void  delete_notes(int noteid){
        this.getWritableDatabase().delete("NOTES","NOTE_ID='"+noteid+"'",null);
    }



}
