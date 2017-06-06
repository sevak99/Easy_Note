package com.example.easynote.fragments;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easynote.App;
import com.example.easynote.R;
import com.example.easynote.activities.CreateActivity;
import com.example.easynote.pojos.Note;
import com.example.easynote.utils.note.FileNoteStorage;
import com.example.easynote.utils.note.NoteAdapter;
import com.example.easynote.utils.note.NoteStorage;
import com.example.easynote.utils.note.NoteViewHolder;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotesFragment extends Fragment implements View.OnClickListener {
    public static final int REQUEST_CODE = 12;

    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private NoteAdapter noteAdapter;
    private NoteStorage noteStorage;
    private NoteViewHolder.NoteItemClickListener itemClickListener;
    private NoteViewHolder.NoteItemDeleteListener itemDeleteListener;
    private NoteViewHolder.NoteItemLongClickListener itemLongClickListener;


    public NotesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.main_FloatingActionButton);
        recyclerView = (RecyclerView) view.findViewById(R.id.main_RecyclerView);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        floatingActionButton.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(App.getInstance(), LinearLayoutManager.VERTICAL, false));
        initializeNoteItemDeleteListener();
        noteAdapter = new NoteAdapter(itemClickListener, itemDeleteListener, itemLongClickListener);
        recyclerView.setAdapter(noteAdapter);
        noteStorage = new FileNoteStorage();
        noteStorage.findAllNotes(new NoteStorage.NotesFoundListener() {
            @Override
            public void onNotesFound(List<Note> notes) {
                noteAdapter.setNotes(notes);
            }
        });
    }

    private void initializeNoteItemDeleteListener() {
        itemDeleteListener = new NoteViewHolder.NoteItemDeleteListener() {
            @Override
            public void onNoteItemDelete(Note note) {
                noteStorage.deleteNote(note);
                noteAdapter.deleteNote(note);
            }
        };
    }

    public NoteViewHolder.NoteItemDeleteListener getItemDeleteListener() {
        return itemDeleteListener;
    }

    public void setItemClickListener(NoteViewHolder.NoteItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setItemLongClickListener(NoteViewHolder.NoteItemLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_FloatingActionButton:
                Intent intent = new Intent(App.getInstance(), CreateActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE:
                    noteAdapter.addNote((Note) data.getSerializableExtra(CreateActivity.KEY_TITLE));
                    break;
            }
        }
    }

    public void update(Note note) {
        noteAdapter.updateNote(note);
        noteStorage.updateNote(note, null);
    }

}
