package com.devmohamedibrahim1997.noteapp.UI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.devmohamedibrahim1997.noteapp.R;
import com.devmohamedibrahim1997.noteapp.pojo.Note;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.devmohamedibrahim1997.noteapp.HelperClass.getDay;
import static com.devmohamedibrahim1997.noteapp.HelperClass.getMonth;
import static com.devmohamedibrahim1997.noteapp.HelperClass.getTime;
import static com.devmohamedibrahim1997.noteapp.HelperClass.getYear;

public class NoteActivity extends AppCompatActivity {

    @BindView(R.id.noteCreationDate)
    TextView noteCreationDate;
    @BindView(R.id.noteCharactersCount)
    TextView noteCharactersCount;
    @BindView(R.id.noteContentEditText)
    EditText noteContentEditText;

    private NoteViewModel noteViewModel;
    private Note mNoteInitial;
    Note mSavedNote;
    private boolean mIsNewNote;
    private boolean mIsSavedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        ButterKnife.bind(this);
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        Toolbar toolbar = findViewById(R.id.noteToolBar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if(!getIncomingIntent()){
            setNewNoteProperties();
        } else{
            setNoteProperties();
        }
    }


    private boolean getIncomingIntent() {
        if(getIntent().hasExtra("selected_note")){
            mNoteInitial = getIntent().getParcelableExtra("selected_note");
            mIsNewNote = false;
            return true;
        }
        mIsNewNote = true;
        return false;
    }

    private void insertNote(){
        String noteContent = noteContentEditText.getText().toString().trim();
        String time = noteCreationDate.getText().toString();

        if(!noteContent.equals("")) {
            mSavedNote = new Note(noteContent,time);
            noteViewModel.insertNote(mSavedNote);
            mIsNewNote = false;
            mIsSavedNote =true;

        }else {
            Toast.makeText(this, "can't save empty note", Toast.LENGTH_SHORT).show();
        }

        getSavedNote();
    }

    private void updateNote(){

        if(mIsSavedNote) {
            String newText1 = noteContentEditText.getText().toString().trim();
            if (!newText1.equals(mSavedNote.getContent())) {
                Note note = new Note(newText1, mSavedNote.getCreationDate());
                note.setId(mSavedNote.getId());
                noteViewModel.updateNote(note);
            }
        }else {
            String newText2 = noteContentEditText.getText().toString().trim();
            if (!newText2.equals(mNoteInitial.getContent())) {
                Note note = new Note(newText2, mNoteInitial.getCreationDate());
                note.setId(mNoteInitial.getId());
                noteViewModel.updateNote(note);
            }
        }
    }


    private void setNewNoteProperties(){

        getSupportActionBar().setTitle("Add Note");

        getDateAndTime();
        getCharactersCount();
    }

    private void setNoteProperties() {
        getSupportActionBar().setTitle("Edit Note");
        noteCreationDate.setText(mNoteInitial.getCreationDate());
        noteContentEditText.setText(mNoteInitial.getContent());
        getCharactersCount();
    }

    private void getDateAndTime() {
        @SuppressLint("SimpleDateFormat")
        String time = new SimpleDateFormat("dd MM yyyy HH mm").format(Calendar.getInstance().getTime());
        String[] s = time.split(" ");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getDay(s[0]));
        stringBuilder.append(getMonth(s[1]));
        stringBuilder.append(getYear(s[2]));
        stringBuilder.append(getTime(s[3],s[4]));
        noteCreationDate.setText(stringBuilder);
    }

    private void getCharactersCount(){
        noteContentEditText.addTextChangedListener(new TextWatcher() {
            @SuppressLint("SetTextI18n")
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                noteCharactersCount.setText("  |   " + charSequence.length() + " characters");
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                noteCharactersCount.setText("  |   " + charSequence.length() + " characters");
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable editable) {
                noteCharactersCount.setText("  |   " + editable.toString().length() + " characters");
            }
        });
    }

    public void getSavedNote() {
        noteViewModel.getAllNotes().observe(this, notes -> {
            if(notes != null) {
                mSavedNote = notes.get(notes.size() - 1);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.note_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.saveNote) {
            if(mIsNewNote){
                insertNote();
            }else{
                updateNote();
            }
        }else if(item.getItemId() == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
