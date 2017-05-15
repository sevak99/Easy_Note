package com.example.easynote.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easynote.App;
import com.example.easynote.R;
import com.example.easynote.fragments.NoteDetailsFragment;
import com.example.easynote.pojos.Note;
import com.example.easynote.utils.note.FileNoteStorage;
import com.example.easynote.utils.note.NoteStorage;

import java.io.File;
import java.io.IOException;

import petrov.kristiyan.colorpicker.ColorPicker;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String KEY_TITLE = "com.example.easynote.activities.CreateActivity_note";
    private static final int DEFAULT_COLOR = Color.GREEN;

    private EditText title;
    private EditText description;
    private CheckBox isImportant;
    private View note_color;
    private Button save;
    private Note note;
    private NoteStorage noteStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        title = (EditText) findViewById(R.id.createNote_Title);
        description = (EditText) findViewById(R.id.createNote_Description);
        isImportant = (CheckBox) findViewById(R.id.createNote_isImportant);
        note_color = findViewById(R.id.createNote_ColorPicker);
        note_color.setBackgroundColor(DEFAULT_COLOR);
        note_color.setOnClickListener(this);
        save = (Button) findViewById(R.id.createNote_save_btn);
        save.setOnClickListener(this);

        noteStorage = new FileNoteStorage();

        setNote();
    }

//    Set Note
    private void setNote(){
        Note note = (Note) getIntent().getSerializableExtra(NoteDetailsFragment.KEY_TITLE);
        if(note != null) {
            title.setText(note.getTitle());
            description.setText(note.getDescription());
            isImportant.setChecked(note.isImportant());
            note_color.setBackgroundColor(note.getColor());
            this.note = note;
        } else this.note = new Note();
    }

//    ONClick
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createNote_save_btn:
                if(note.getId() == 0) {
                    createNote();
                } else {
                    updateNote();
                }
                break;
            case R.id.createNote_ColorPicker:
                openColorPicker();
                break;
        }
    }

//    Create Note
    private void createNote() {
        if(saveNote()) {
            noteStorage.createNote(note, new NoteStorage.NoteListener() {
                @Override
                public void onNote(Note note) {
                    Intent intent = new Intent();
                    intent.putExtra(KEY_TITLE, note);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        }
    }

//    Update Note
    private void updateNote() {
        if(saveNote()) {
            noteStorage.updateNote(note, new NoteStorage.NoteListener() {
                @Override
                public void onNote(Note note) {
                    Intent intent = new Intent();
                    intent.putExtra(KEY_TITLE, note);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        }
    }

//    Save Note
    private boolean saveNote() {
        if(title.getText().length() == 0) {
            Toast.makeText(App.getInstance(), "Title can not be empty", Toast.LENGTH_LONG).show();
            return false;
        }
        if(description.getText().length() == 0) {
            Toast.makeText(App.getInstance(), "Description can not be empty", Toast.LENGTH_LONG).show();
            return false;
        }
        note.setTitle(title.getText().toString());
        note.setDescription(description.getText().toString());
        note.setImportant(isImportant.isChecked());
        if(note.getColor() == 0) note.setColor(DEFAULT_COLOR);
        return true;
    }


//    ColorPicker
    private void openColorPicker() {
        ColorPicker colorPicker = new ColorPicker(this);
        colorPicker.show();
        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position, int color) {
                note.setColor(color);
                note_color.setBackgroundColor(color);
            }
            @Override
            public void onCancel() {};
        });
    }
}
