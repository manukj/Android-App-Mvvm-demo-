package com.example.database.Model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.database.Model.Note;

import java.util.List;

/**
 * Created by Manu K J on 9/11/19.
 */
@Dao
public interface NotesDAO {
    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("Delete from note_table")
    void deleteAll();


    @Query("select * from note_table order by priority desc")
    LiveData<List<Note>> getAllNotes();
}
