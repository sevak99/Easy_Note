package com.example.easynote.utils.note;

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
    private NoteViewHolder.NoteItemClickListener listener;

    public void addNote(Note note) {
        notes.add(note);
        notifyDataSetChanged();
    }

    public void updateNote(Note note) {
        for (int i = 0; i < notes.size(); i++) {
            if(notes.get(i).getId() == note.getId()) {
                notes.remove(i);
                notes.add(i, note);
                notifyDataSetChanged();
                break;
            }
        }
    }

    public void setNotes(List<Note> notes) {
        this.notes.addAll(notes);
        notifyDataSetChanged();
    }

    public NoteAdapter(final NoteViewHolder.NoteItemClickListener listener) {
        notes = new ArrayList<>();
        this.listener = listener;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(App.getInstance());
        View view = inflater.inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        holder.bind(notes.get(position));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}

