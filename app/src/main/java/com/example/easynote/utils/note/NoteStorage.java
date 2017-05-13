package com.example.easynote.utils.note;

import com.example.easynote.pojos.Note;

import java.util.List;

/**
 * Created by SEVAK on 09.05.2017.
 */

public abstract class NoteStorage {

    public abstract void createNote(Note note, NoteListener listener);

    public abstract void findNoteByID(long id, NoteListener listener);

    public abstract void updateNote(Note note, NoteListener noteListener);

    public abstract void findAllNotes(NotesFoundListener listener);


    protected void notifyNoteFound(Note note, NoteListener listener) {
        if(listener != null) {
            listener.onNote(note);
        }
    }

    protected void notifyNotesFound(List<Note> notes, NotesFoundListener listener) {
        if(listener != null) {
            listener.onNotesFound(notes);
        }
    }


    public interface NotesFoundListener {
        void onNotesFound(List<Note> notes);
    }

    public interface NoteListener {
        void onNote(Note note);
    }
}
