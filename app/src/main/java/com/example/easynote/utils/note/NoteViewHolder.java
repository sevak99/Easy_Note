package com.example.easynote.utils.note;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.easynote.R;
import com.example.easynote.pojos.Note;

import java.util.Date;

/**
 * Created by SEVAK on 11.05.2017.
 */

public class NoteViewHolder extends RecyclerView.ViewHolder {
    private View item;
    private View view;
    private TextView title;
    private TextView description;
    private View isImportant;
    private TextView date;
    private Note note;
    private NoteItemClickListener listener;

    public NoteViewHolder(View itemView, final NoteItemClickListener listener) {
        super(itemView);
        item = itemView.findViewById(R.id.note_item);
        view = itemView.findViewById(R.id.note_color);
        title = (TextView) itemView.findViewById(R.id.note_title);
        description = (TextView) itemView.findViewById(R.id.note_description);
        isImportant = itemView.findViewById(R.id.note_isImportant);
        date = (TextView) itemView.findViewById(R.id.note_date);

        this.listener = listener;
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyNoteItemClicked(note);
            }
        });
    }

    public void bind(Note note) {
        view.setBackgroundColor(note.getColor());
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
        if(listener != null)
            listener.onNoteItemClick(note);
    }

    public interface NoteItemClickListener {
        void onNoteItemClick(Note note);
    }
}