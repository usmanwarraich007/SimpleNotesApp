package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

public class NotesEditor extends AppCompatActivity {

    DBController dbController;
    EditText noteseditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_editor);
        noteseditor = findViewById(R.id.editor);
        dbController = new DBController(this,"",null,1);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String note = getIntent().getStringExtra("note");
        noteseditor.setText(note);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(Checks.state.equals("CreateNote")){
                    createnote();
                }
                if(Checks.state.equals("EditNote")){
                    updatenote();
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void createnote(){
        if(!noteseditor.getText().toString().isEmpty()) {
            dbController.insert_notes(noteseditor.getText().toString());
            startActivity(new Intent(NotesEditor.this,MainActivity.class));
        }
    }

    public void updatenote(){
        if(!noteseditor.getText().toString().isEmpty()) {
            dbController.update_note(Checks.noteid,noteseditor.getText().toString());
            startActivity(new Intent(NotesEditor.this,MainActivity.class));
        }
    }

}




