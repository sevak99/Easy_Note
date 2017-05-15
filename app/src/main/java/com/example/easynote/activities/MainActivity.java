package com.example.easynote.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.easynote.R;
import com.example.easynote.fragments.NoteDetailsFragment;
import com.example.easynote.fragments.NotesFragment;
import com.example.easynote.pojos.Note;
import com.example.easynote.utils.note.NoteViewHolder;


public class MainActivity extends AppCompatActivity{
    private int containerResId;
    private NotesFragment notesFragment;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private NoteViewHolder.NoteItemClickListener itemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        containerResId = R.id.activity_main_container;
        initializeNoteItemClickListener();
        notesFragment = new NotesFragment();
        notesFragment.setItemClickListener(itemClickListener);
        fm = getFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(containerResId, notesFragment);
        ft.commit();
    }

    private void initializeNoteItemClickListener() {
        itemClickListener = new NoteViewHolder.NoteItemClickListener() {
            @Override
            public void onNoteItemClick(Note note) {
                ft = fm.beginTransaction();
                Log.d("testt", "initializeNoteItemClickListener");
                Fragment fragment;
                fragment = NoteDetailsFragment.newInstance(note);
                ft.addToBackStack(null);
                ft.replace(containerResId, fragment);
                ft.commit();
            }
        };
    }

    @Override
    public void onBackPressed() {
        if((fm.getBackStackEntryCount() == 2) &&
                (fm.getBackStackEntryAt(1) instanceof NoteDetailsFragment)) {
            notesFragment.update(((NoteDetailsFragment) fm.getBackStackEntryAt(1)).getNote());
        }
        if(!fm.popBackStackImmediate()) {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}