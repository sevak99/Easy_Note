package com.example.easynote.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.easynote.App;
import com.example.easynote.R;
import com.example.easynote.pojos.Note;
import com.example.easynote.utils.note.FileNoteStorage;
import com.example.easynote.utils.note.NoteAdapter;
import com.example.easynote.utils.note.NoteStorage;
import com.example.easynote.utils.note.NoteViewHolder;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String KEY_TITLE = "com.example.easynote.activities.MainActivity_Note";
    private static final int REQUEST_CODE = 12;
    private static final int REQUEST_CODE_FOR_UPDATE = 143;

    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private NoteAdapter noteAdapter;
    private NoteStorage noteStorage;
    private NoteViewHolder.NoteItemClickListener noteItemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.main_FloatingActionButton);
        floatingActionButton.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.main_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        noteItemClickListener = new NoteViewHolder.NoteItemClickListener() {
            @Override
            public void onNoteItemClick(Note note) {
                Intent intent = new Intent(App.getInstance(), CreateActivity.class);
                intent.putExtra(KEY_TITLE, note);
                startActivityForResult(intent, REQUEST_CODE_FOR_UPDATE);
            }
        };

        noteAdapter = new NoteAdapter(noteItemClickListener);
        recyclerView.setAdapter(noteAdapter);
        noteStorage = new FileNoteStorage();
        noteStorage.findAllNotes(new NoteStorage.NotesFoundListener() {
            @Override
            public void onNotesFound(List<Note> notes) {
                noteAdapter.setNotes(notes);
            }
        });



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_FloatingActionButton:
                Intent intent = new Intent(this, CreateActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE:
                    noteAdapter.addNote((Note) data.getSerializableExtra(CreateActivity.KEY_TITLE));
                    break;
                case REQUEST_CODE_FOR_UPDATE:
                    noteAdapter.updateNote((Note) data.getSerializableExtra(CreateActivity.KEY_TITLE));
                    noteAdapter.notifyDataSetChanged();
            }
        }
    }
}