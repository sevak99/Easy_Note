package com.example.easynote.utils.note;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.easynote.R;
import com.example.easynote.pojos.Note;

import java.util.Date;

/**
 * Created by SEVAK on 11.05.2017.
 */

public class NoteViewHolder extends RecyclerView.ViewHolder
        implements View.OnLongClickListener{
    private View item;
    private View note_color;
    private TextView title;
    private TextView description;
    private View isImportant;
    private TextView date;
    private View note_delete;
    private TextView alarmDate;
    private Note note;
    private NoteItemClickListener noteItemClickListener;
    private NoteItemDeleteListener noteItemDeleteListener;
    private NoteItemLongClickListener noteItemLongClickListener;

    public NoteViewHolder(View itemView,
                          final NoteItemClickListener itemClickListener,
                          NoteItemDeleteListener itemDeleteListener,
                          NoteItemLongClickListener itemLongClickListener) {
        super(itemView);
        item = itemView.findViewById(R.id.note_item);
        note_color = itemView.findViewById(R.id.note_color);
        title = (TextView) itemView.findViewById(R.id.note_title);
        description = (TextView) itemView.findViewById(R.id.note_description);
        isImportant = itemView.findViewById(R.id.note_isImportant);
        date = (TextView) itemView.findViewById(R.id.note_date);
        note_delete = itemView.findViewById(R.id.note_delete);
        alarmDate = (TextView) itemView.findViewById(R.id.note_alarmDate);

        noteItemClickListener = itemClickListener;
        noteItemDeleteListener = itemDeleteListener;
        noteItemLongClickListener = itemLongClickListener;
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
        item.setOnLongClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void bind(Note note) {
        note_color.setBackgroundColor(note.getColor());
        title.setText(note.getTitle());
        description.setText(note.getDescription());
        if(note.isImportant()) isImportant.setVisibility(View.VISIBLE);
        else isImportant.setVisibility(View.GONE);
        date.setText(note.getCreateDate());
        if(note.getAlarmDate().getTimeInMillis() > System.currentTimeMillis()) {
            alarmDate.setVisibility(View.VISIBLE);
            alarmDate.setText("Alarm worked in " + note.getAlarmDateInString());
        }
        else alarmDate.setVisibility(View.GONE);

        this.note = note;
    }

//    Long Click
    @Override
    public boolean onLongClick(View v) {
        Log.d("testt", "NoteViewHolder -- this is long click");
        switch (v.getId()) {
            case R.id.note_item:
                Log.d("testt", "NoteViewHolder -- Long clicked");
                notifyNoteItemLongClicked(note);
        }
        return false;
    }

//    Listeners
    public interface NoteItemClickListener {
        void onNoteItemClick(Note note);
    }

    public interface NoteItemDeleteListener {
        void onNoteItemDelete(Note note);
    }

    public interface NoteItemLongClickListener {
        void onNoteItemLongClick(Note note);
    }

//    Notify
    private void notifyNoteItemClicked(Note note) {
        if(noteItemClickListener != null)
            noteItemClickListener.onNoteItemClick(note);
    }

    private void notifyNoteItemDeleted(Note note) {
        if(noteItemDeleteListener != null)
            noteItemDeleteListener.onNoteItemDelete(note);
    }

    private void notifyNoteItemLongClicked(Note note) {
        if(noteItemLongClickListener != null) {
            Log.d("testt", "NoteViewHolder -- YES");
            noteItemLongClickListener.onNoteItemLongClick(note);
        }
    }
}