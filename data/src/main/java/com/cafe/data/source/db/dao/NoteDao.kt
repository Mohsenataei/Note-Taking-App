package com.cafe.data.source.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.cafe.data.source.db.model.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes")
    suspend fun getNotes(): List<Note>

    @Query("SELECT * FROM notes WHERE folderId = :folderId")
    suspend fun getNotesInFolder(folderId: String): List<Note>

    @Query("SELECT * FROM notes WHERE creation_date = :creationDate")
    suspend fun getNoteById(creationDate: String): Note

    @Delete
    suspend fun deleteNote(note: Note)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(note: Note): Long
}