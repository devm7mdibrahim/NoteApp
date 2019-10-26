package com.devmohamedibrahim1997.noteapp.UI;

import android.app.Application;
import android.os.AsyncTask;

import com.devmohamedibrahim1997.noteapp.pojo.Note;
import com.devmohamedibrahim1997.noteapp.persistence.NoteDao;
import com.devmohamedibrahim1997.noteapp.persistence.NoteDataBase;

import java.util.List;

import androidx.lifecycle.LiveData;

class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;


    NoteRepository(Application context){
        NoteDataBase instance = NoteDataBase.getInstance(context);
        noteDao = instance.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    void insertNote(Note note){new InsertNoteAsyncTask(noteDao).execute(note);}

    void updateNote(Note note){new UpdateNoteAsyncTask(noteDao).execute(note);}

    void deleteNote(Note note){new DeleteNoteAsyncTask(noteDao).execute(note);}

    LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }


    private static class InsertNoteAsyncTask extends AsyncTask<Note,Void,Void> {
        private NoteDao noteDao;
        InsertNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insertNote(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note,Void,Void> {
        private NoteDao noteDao;
        UpdateNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.updateNote(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note,Void,Void> {
        private NoteDao noteDao;
        DeleteNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.deleteNote(notes[0]);
            return null;
        }
    }

}
