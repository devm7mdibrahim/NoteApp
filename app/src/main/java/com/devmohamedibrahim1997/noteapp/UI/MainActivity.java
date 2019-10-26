package com.devmohamedibrahim1997.noteapp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import com.devmohamedibrahim1997.noteapp.Adapter.NoteAdapter;
import com.devmohamedibrahim1997.noteapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.noteSearchView)
    SearchView searchView;
    @BindView(R.id.mainToolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.noteRecyclerView)
    RecyclerView noteRecyclerView;

    private NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        initFloatingActionButton();
        initSearchView();
        initRecyclerView();
        initViewModel();
    }


    void initViewModel() {
        NoteViewModel noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, notes -> {
            noteAdapter.submitNotes(notes);
            noteAdapter.notifyDataSetChanged();

            //on note clicked
            noteAdapter.setOnItemClickListener((position, view) -> {
                Intent intent = new Intent(MainActivity.this,NoteActivity.class);
                intent.putExtra("selected_note", notes.get(position));
                startActivity(intent);
            });

            //on note swapped
            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    noteViewModel.deleteNote(noteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                }
            }).attachToRecyclerView(noteRecyclerView);
        });
    }

    private void initRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this,1);
        noteAdapter = new NoteAdapter(MainActivity.this);
        noteRecyclerView.setLayoutManager(layoutManager);
        noteRecyclerView.setAdapter(noteAdapter);
    }

    void initFloatingActionButton() {
        fab.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, NoteActivity.class)));
        noteRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView noteRecyclerView, int dx, int dy)
            {
                if (dy > 0 ||dy<0 && fab.isShown())
                {
                    fab.hide();
                }
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView noteRecyclerView, int newState)
            {
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                {
                    fab.show();
                }

                super.onScrollStateChanged(noteRecyclerView, newState);
            }
        });
    }

    void initSearchView(){
        searchView = findViewById(R.id.noteSearchView);
        searchView.onActionViewExpanded();
        searchView.clearFocus();
        searchView.setImeOptions(EditorInfo.IME_ACTION_GO);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                noteAdapter.getFilter().filter(newText);
                return false;
            }
        });
        searchView.clearFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_settings){
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }
}