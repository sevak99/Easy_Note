package com.example.easynote.utils.note;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easynote.App;
import com.example.easynote.R;
import com.example.easynote.pojos.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SEVAK on 09.05.2017.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {
    private List<Note> notes;
    private NoteViewHolder.NoteItemClickListener itemClickListener;
    private NoteViewHolder.NoteItemDeleteListener itemDeleteListener;
    private NoteViewHolder.NoteItemLongClickListener itemLongClickListener;

    public NoteAdapter(final NoteViewHolder.NoteItemClickListener itemClickListener,
                       NoteViewHolder.NoteItemDeleteListener itemDeleteListener,
                       NoteViewHolder.NoteItemLongClickListener itemLongClickListener) {
        notes = new ArrayList<>();
        this.itemClickListener = itemClickListener;
        this.itemDeleteListener = itemDeleteListener;
        this.itemLongClickListener = itemLongClickListener;
    }

    public void setNotes(List<Note> notes) {
        this.notes.addAll(notes);
        notifyDataSetChanged();
    }

    public void addNote(Note note) {
        notes.add(note);
        notifyDataSetChanged();
    }

//    Update Note
    public void updateNote(Note note) {
        int i = getNotePosition(note.getId());
        notes.remove(i);
        notes.add(i, note);
        notifyDataSetChanged();
    }
//    Delete Note
    public void deleteNote(Note note) {
        int i = getNotePosition(note.getId());
        notes.remove(i);
        notifyDataSetChanged();
    }

//    get Note Position
    private int getNotePosition(long id) {
        for (int i = 0; i < notes.size(); i++) {
            if(notes.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(App.getInstance());
        View view = inflater.inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view, itemClickListener, itemDeleteListener, itemLongClickListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        holder.bind(notes.get(position));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}

