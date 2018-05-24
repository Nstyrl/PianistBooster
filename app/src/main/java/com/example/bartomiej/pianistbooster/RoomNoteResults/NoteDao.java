package com.example.bartomiej.pianistbooster.RoomNoteResults;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM notes")
    public List<Note> getAll();

    @Query("SELECT * FROM notes WHERE uid IN (:userIds)")
    public List<Note> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM notes WHERE uid = (:userId)")
    public Note loadById(int userId);

    @Insert
    public void insertAll(Note... notes);

    @Delete
    public void delete(Note note);

    @Update
    public void updateAll(Note... note);
}
