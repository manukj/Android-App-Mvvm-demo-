package com.example.database.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.database.Model.Note;
import com.example.database.ViewModel.NoteViewModel;
import com.example.database.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_NODE_REQUEST = 1;
    public static final int EDIT_NODE_REQUEST = 2;
    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FloatingActionButton floatingActionButton = findViewById(R.id.add_node);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEdit_LoadActivity.class);
                startActivityForResult(intent, ADD_NODE_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);


        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                //update recycle view
                adapter.submitList(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(adapter.getNote(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Node deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);


        adapter.setOnItemClickLiestiner(new NoteAdapter.OnItemCLikEvent() {
            @Override
            public void onItemclick(Note note) {
                Intent intent = new Intent(MainActivity.this, AddEdit_LoadActivity.class);
                intent.putExtra(AddEdit_LoadActivity.EXTRA_DESC, note.getDescription());
                intent.putExtra(AddEdit_LoadActivity.EXTRA_PRIO, note.getPriority());
                intent.putExtra(AddEdit_LoadActivity.EXTRA_TITLE, note.getTitle());
                intent.putExtra(AddEdit_LoadActivity.EXTRA_ID, note.getId());
                startActivityForResult(intent, EDIT_NODE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NODE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEdit_LoadActivity.EXTRA_TITLE);
            String desc = data.getStringExtra(AddEdit_LoadActivity.EXTRA_DESC);
            int priority = data.getIntExtra(AddEdit_LoadActivity.EXTRA_PRIO, 1);

            Note note = new Note(title, desc, priority);
            noteViewModel.insert(note);

            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_NODE_REQUEST && resultCode == RESULT_OK) {

            int id = data.getIntExtra(AddEdit_LoadActivity.EXTRA_ID, -1);
            Log.d("Update", "id recieved= " + id);
            if (id == -1) {
                Toast.makeText(this, "Cannot be update", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddEdit_LoadActivity.EXTRA_TITLE);
            String desc = data.getStringExtra(AddEdit_LoadActivity.EXTRA_DESC);
            int priority = data.getIntExtra(AddEdit_LoadActivity.EXTRA_PRIO, 1);

            Note note = new Note(title, desc, priority);
            note.setId(id);
            noteViewModel.update(note);
            Toast.makeText(this, "Note is update", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteAllNode:
                noteViewModel.deleteAllNote();
                Toast.makeText(this, "Delete All node", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
