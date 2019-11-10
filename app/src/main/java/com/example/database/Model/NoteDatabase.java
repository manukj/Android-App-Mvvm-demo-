package com.example.database.Model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * Created by Manu K J on 9/11/19.
 */
@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {


    private static NoteDatabase instance;
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDatabase(instance).execute();
        }
    };

    //singleton class
    public static synchronized NoteDatabase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class,
                    "notedatabse")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    public abstract NotesDAO noteDao();

    private static class PopulateDatabase extends AsyncTask<Void, Void, Void> {

        private NotesDAO notesDAO;

        private PopulateDatabase(NoteDatabase nd) {
            notesDAO = nd.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            notesDAO.insert(new Note("Title1", "Description1", 1));
            notesDAO.insert(new Note("Title2", "Description2", 2));

            notesDAO.insert(new Note("Title3", "Description3", 3));

            return null;
        }
    }
}