package com.cafe.data.source.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.cafe.data.source.db.model.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes")
    suspend fun getNotes(): List<Note>

    @Query("SELECT * FROM notes WHERE `folderId` = :folderId")
    suspend fun getNotesInFolder(folderId: String): List<Note>

    @Query("SELECT * FROM notes WHERE `index` = :id")
    suspend fun getNoteById(id: Int): Note

    @Update
    suspend fun updateNote(note: Note): Int

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * from notes WHERE folderId = 0")
    suspend fun getOrphanNotes(): List<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(note: Note): Long
}