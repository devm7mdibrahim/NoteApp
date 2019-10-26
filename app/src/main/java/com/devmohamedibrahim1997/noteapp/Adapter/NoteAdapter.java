package com.devmohamedibrahim1997.noteapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.devmohamedibrahim1997.noteapp.pojo.Note;
import com.devmohamedibrahim1997.noteapp.R;
import com.devmohamedibrahim1997.noteapp.databinding.NoteDataBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> implements Filterable {

    private static ClickListener clickListener;
    private Context context;
    private List<Note> notes;
    private List<Note> copyNotes;

    public NoteAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NoteDataBinding noteDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.note_card,parent,false);
        return new NoteViewHolder(noteDataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.bindNote(note);
    }

    @Override
    public int getItemCount() {
        return notes!= null? notes.size(): 0;
    }

    public void submitNotes(List<Note> notes){
        this.notes = notes;
        copyNotes = new ArrayList<>(notes);
    }

    public Note getNoteAt(int position){
        return notes.get(position);
    }

    @Override
    public Filter getFilter() {
        return noteFilter;
    }

    private Filter noteFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Note> filteredList = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0){
                filteredList.addAll(copyNotes);
            }else {
                for(Note note : copyNotes){
                    if(note.getContent().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(note);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            notes.clear();
            notes.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };


    class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        NoteDataBinding noteDataBinding;

        NoteViewHolder(NoteDataBinding noteDataBinding) {
            super(noteDataBinding.getRoot());
            this.noteDataBinding = noteDataBinding;
            itemView.setOnClickListener(this);
        }

        void bindNote(Note note) {
            noteDataBinding.setNote(note);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(),view);

        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        NoteAdapter.clickListener = clickListener;
    }


    public interface ClickListener {
        void onItemClick(int position, View view);
    }
}
