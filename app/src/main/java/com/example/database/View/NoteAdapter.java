package com.example.database.View;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.database.Model.Note;
import com.example.database.R;

/**
 * Created by Manu K J on 10/11/19.
 */
public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteHolder> {

    private OnItemCLikEvent liestiner;

    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(Note oldItem, Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Note oldItem, Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getPriority() == newItem.getPriority();
        }
    };

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_iteam, parent, false);

        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote = getItem(position);
        holder.textViewPrioprity.setText("" + currentNote.getPriority());
        holder.textViewDescription.setText(currentNote.getDescription());
        holder.textViewTitle.setText(currentNote.getTitle());
    }


    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewPrioprity;
        private TextView textViewDescription;


        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.view_title);
            textViewDescription = itemView.findViewById(R.id.view_description);
            textViewPrioprity = itemView.findViewById(R.id.view_priority);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Update", "clicked");
                    int point = getAdapterPosition();
                    if (liestiner != null && point != RecyclerView.NO_POSITION)
                        liestiner.onItemclick(getItem(point));
                }
            });
        }
    }

    public Note getNote(int position) {
        return getItem(position);
    }

    public interface OnItemCLikEvent {
        void onItemclick(Note note);
    }

    public void setOnItemClickLiestiner(OnItemCLikEvent liestiner) {
        this.liestiner = liestiner;
    }
}
