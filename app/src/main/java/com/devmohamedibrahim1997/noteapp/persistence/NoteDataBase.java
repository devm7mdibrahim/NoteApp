package com.devmohamedibrahim1997.noteapp.persistence;

import android.content.Context;

import com.devmohamedibrahim1997.noteapp.pojo.Note;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Note.class, version = 6, exportSchema = false)
public abstract class NoteDataBase extends RoomDatabase {

    private static NoteDataBase instance;
    public abstract NoteDao noteDao();

    public static synchronized NoteDataBase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), NoteDataBase.class,"note_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
