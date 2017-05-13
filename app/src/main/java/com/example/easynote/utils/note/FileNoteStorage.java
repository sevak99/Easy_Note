package com.example.easynote.utils.note;

import android.util.Log;

import com.example.easynote.pojos.Note;
import com.example.easynote.utils.StorageHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by SEVAK on 09.05.2017.
 */

public class FileNoteStorage extends NoteStorage {
    private static final String NOTES_FILE_NAME = "FileNoteStorage_Notes";

    private NotesWrapper notesWrapper;

    public FileNoteStorage() {
        notesWrapper = (NotesWrapper) StorageHelper.deserialize(getFileName());

        if(notesWrapper == null) {
            notesWrapper = new NotesWrapper();
        }

        Log.d("testt", "Yes0" + notesWrapper.toString());
    }

    public static String getFileName() {
        return String.format("%s", NOTES_FILE_NAME);
    }

//    Create Note
    @Override
    public void createNote(Note note, NoteListener listener) {
        note.setId(System.currentTimeMillis());
        note.setCreateDate(new Date());
        notesWrapper.getNotes().add(note);

        StorageHelper.serialize(getFileName(), notesWrapper);
        notifyNoteFound(note, listener);
    }

// get Note Position
    @Override
    public int getNotePosition(long id) {
        for(int i = 0; i < notesWrapper.getNotes().size(); i++) {
            if(notesWrapper.getNotes().get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    //    Update Note
    @Override
    public void updateNote(final Note note, NoteListener noteListener) {
        note.setCreateDate(new Date());
        int i = getNotePosition(note.getId());
        notesWrapper.getNotes().remove(i);
        notesWrapper.getNotes().add(i, note);
        StorageHelper.serialize(getFileName(), notesWrapper);
        notifyNoteFound(note, noteListener);
    }

//    Delete Note
    @Override
    public void deleteNote(Note note) {
        int i = getNotePosition(note.getId());
        notesWrapper.getNotes().remove(i);
        StorageHelper.serialize(getFileName(), notesWrapper);
    }

    @Override
    public void findAllNotes(NotesFoundListener listener) {
        notifyNotesFound(notesWrapper.getNotes(), listener);
    }


    public static class NotesWrapper implements Serializable {
        private static final long serialVersionUID = -1892561327013038124L;
        private List<Note> notes;

        public NotesWrapper() {
            notes = new ArrayList<>();
        }
        public List<Note> getNotes() {
            return notes;
        }
    }
}
