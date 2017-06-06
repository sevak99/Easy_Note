package com.example.easynote.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.easynote.App;
import com.example.easynote.R;
import com.example.easynote.fragments.NoteDetailsFragment;
import com.example.easynote.fragments.NotesFragment;
import com.example.easynote.pojos.Note;
import com.example.easynote.utils.note.NoteAdapter;
import com.example.easynote.utils.note.NoteViewHolder;


public class MainActivity extends AppCompatActivity{
    private int containerResId;
    private NotesFragment notesFragment;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private NoteViewHolder.NoteItemClickListener itemClickListener;
    private NoteViewHolder.NoteItemLongClickListener itemLongClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        containerResId = R.id.activity_main_container;
        initializeNoteItemClickListener();
        initializeNoteItemLongClickListener();
        notesFragment = new NotesFragment();
        notesFragment.setItemClickListener(itemClickListener);
        notesFragment.setItemLongClickListener(itemLongClickListener);
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

    private void initializeNoteItemLongClickListener() {
        itemLongClickListener = new NoteViewHolder.NoteItemLongClickListener() {
            @Override
            public void onNoteItemLongClick(final Note note) {
                Log.d("testt", "MainActivity -- YESEY");
                AlertDialog dialog = (new AlertDialog.Builder(MainActivity.this)).setTitle(note.getTitle())
                        .setNegativeButton("delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                notesFragment.getItemDeleteListener().onNoteItemDelete(note);
                            }
                        })
                        .setPositiveButton("edit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(App.getInstance(), CreateActivity.class);
                                intent.putExtra(NoteDetailsFragment.KEY_TITLE, note);
                                startActivityForResult(intent, NoteDetailsFragment.REQUEST_CODE);
                            }
                        })
                        .setNeutralButton("cancel alarm", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                note.getAlarmDate().setTimeInMillis(System.currentTimeMillis() - 1);
                                notesFragment.update(note);
                            }
                        })
                        .create();
                dialog.show();
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
        if((requestCode == NoteDetailsFragment.REQUEST_CODE) && (resultCode == RESULT_OK)) {
            Note note = (Note) data.getSerializableExtra(CreateActivity.KEY_TITLE);
            notesFragment.update(note);
        }
    }
}