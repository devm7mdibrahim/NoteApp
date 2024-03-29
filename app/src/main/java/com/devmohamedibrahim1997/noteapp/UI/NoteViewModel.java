package com.devmohamedibrahim1997.noteapp.UI;

import android.app.Application;

import com.devmohamedibrahim1997.noteapp.pojo.Note;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository noteRepository;
    private LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);

        noteRepository = new NoteRepository(application);
        allNotes = noteRepository.getAllNotes();
    }

    public void insertNote(Note note){noteRepository.insertNote(note);}

    public void updateNote(Note note){noteRepository.updateNote(note);}

    public void deleteNote(Note note){noteRepository.deleteNote(note);}

    public LiveData<List<Note>> getAllNotes() { return allNotes;}
}
