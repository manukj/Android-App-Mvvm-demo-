package com.example.database.ViewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;


import com.example.database.Model.Note;
import com.example.database.Model.NoteDatabase;
import com.example.database.Model.NotesDAO;

import java.util.List;

/**
 * Created by Manu K J on 9/11/19.
 */
public class NoteRepository {

    private NotesDAO notesDAO;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application) {
        NoteDatabase databse = NoteDatabase.getInstance(application);
        notesDAO = databse.noteDao();
        allNotes = notesDAO.getAllNotes();
    }

    public void insert(Note note) {
    new InsertNoteAsyncTask(notesDAO).execute(note);
    }

    public void update(Note note) {
        new UpdateNoteAsyncTask(notesDAO).execute(note);
    }

    public void delete(Note note) {
        new DeleteNoteAsyncTask(notesDAO).execute(note);
    }

    public void deleteAllNote() {
        new DeleteAllNoteAsyncTask(notesDAO).execute();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NotesDAO notesDAO;

        private InsertNoteAsyncTask(NotesDAO notesDAO) {
            this.notesDAO = notesDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            notesDAO.insert(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NotesDAO notesDAO;

        private DeleteNoteAsyncTask(NotesDAO notesDAO) {
            this.notesDAO = notesDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            notesDAO.delete(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NotesDAO notesDAO;

        private UpdateNoteAsyncTask(NotesDAO notesDAO) {
            this.notesDAO = notesDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            notesDAO.update(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNoteAsyncTask extends AsyncTask<Void, Void, Void> {

        private NotesDAO notesDAO;

        private DeleteAllNoteAsyncTask(NotesDAO notesDAO) {
            this.notesDAO = notesDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            notesDAO.deleteAll();
            return null;
        }
    }
}
