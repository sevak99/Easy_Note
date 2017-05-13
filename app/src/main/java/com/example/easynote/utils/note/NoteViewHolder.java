package com.example.easynote.utils.note;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.easynote.R;
import com.example.easynote.pojos.Note;

import java.util.Date;

/**
 * Created by SEVAK on 11.05.2017.
 */

public class NoteViewHolder extends RecyclerView.ViewHolder {
    private View item;
    private View note_color;
    private TextView title;
    private TextView description;
    private View isImportant;
    private TextView date;
    private View note_delete;
    private Note note;
    private NoteItemClickListener noteItemClickListener;
    private NoteItemDeleteListener noteItemDeleteListener;

    public NoteViewHolder(View itemView, final NoteItemClickListener itemClickListener, NoteItemDeleteListener itemDeleteListener) {
        super(itemView);
        item = itemView.findViewById(R.id.note_item);
        note_color = itemView.findViewById(R.id.note_color);
        title = (TextView) itemView.findViewById(R.id.note_title);
        description = (TextView) itemView.findViewById(R.id.note_description);
        isImportant = itemView.findViewById(R.id.note_isImportant);
        date = (TextView) itemView.findViewById(R.id.note_date);
        note_delete = itemView.findViewById(R.id.note_delete);

        noteItemClickListener = itemClickListener;
        noteItemDeleteListener = itemDeleteListener;
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyNoteItemClicked(note);
            }
        });
        note_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyNoteItemDeleted(note);
            }
        });
    }

    public void bind(Note note) {
        note_color.setBackgroundColor(note.getColor());
        title.setText(note.getTitle());
        description.setText(note.getDescription());
        if(note.isImportant()) isImportant.setVisibility(View.VISIBLE);
        else isImportant.setVisibility(View.GONE);
        date.setText(getDate(note.getCreateDate()));

        this.note = note;
    }

    private String getDate(Date date) {
        Date now = new Date();
        if(date.getDay() == now.getDay()) return String.format("%d:%d", date.getHours(), date.getMinutes());
        return String.format("%s.%s.%s", date.getDay(), date.getMonth(), date.getYear());
    }

    private void notifyNoteItemClicked(Note note) {
        if(noteItemClickListener != null)
            noteItemClickListener.onNoteItemClick(note);
    }

    public interface NoteItemClickListener {
        void onNoteItemClick(Note note);
    }

    private void notifyNoteItemDeleted(Note note) {
        if(noteItemDeleteListener != null)
            noteItemDeleteListener.onNoteItemDelete(note);
    }

    public interface NoteItemDeleteListener {
        void onNoteItemDelete(Note note);
    }
}