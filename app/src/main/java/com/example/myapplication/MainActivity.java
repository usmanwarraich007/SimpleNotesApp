package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DBController dbController;
    ListView notes_list;
    Button addnotes,ok,cancel;
    Dialog deletenotedialogue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbController = new DBController(this,"",null,1);
        notes_list = findViewById(R.id.noteslist);
        addnotes = findViewById(R.id.addnotes);


        deletenotedialogue = new Dialog(MainActivity.this);

        addnotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,NotesEditor.class));
                Checks.state = "CreateNote";
            }
        });

        populate_noteslist();


        notes_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = dbController.get_note(position+1);
                String note="";
                while (cursor.moveToNext()){
                note = cursor.getString(1);
                }
                Checks.state = "EditNote";
                Checks.noteid = position+1;
                Intent intent = new Intent(MainActivity.this,NotesEditor.class);
                intent.putExtra("note",note);
                startActivity(intent);
            }
        });

        notes_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                delete_note(position+1);
                return false;
            }
        });

    }


    public  void populate_noteslist(){
        Cursor cursor = dbController.get_notes();
        ArrayList<String> noteslist = new ArrayList<>();
        while(cursor.moveToNext()){
            noteslist.add(cursor.getString(1));
        }
        ListAdapter adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,noteslist);
        notes_list.setAdapter(adapter);
    }


    public void delete_note(final int id){
        deletenotedialogue.setContentView(R.layout.deletenote_dialogue);
        ok = deletenotedialogue.findViewById(R.id.btn_ok);
        cancel = deletenotedialogue.findViewById(R.id.btn_cancel);
        deletenotedialogue.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        deletenotedialogue.show();


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletenotedialogue.dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbController.delete_notes(id);
                populate_noteslist();
                deletenotedialogue.dismiss();
            }
        });




    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
