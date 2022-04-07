package com.example.challengechapter4

import androidx.room.*

@Dao
interface NoteDao {

    @Insert
    fun insertNote(note: Note) : Long

    @Query("SELECT * FROM Note")
    fun getallNote() : List<Note>

    @Delete
    fun deleteNote(note: Note) : Int

    @Update
    fun updateNote(note: Note) : Int

}