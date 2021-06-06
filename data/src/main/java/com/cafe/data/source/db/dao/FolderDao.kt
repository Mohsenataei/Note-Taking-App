package com.cafe.data.source.db.dao

import androidx.room.*
import com.cafe.data.source.db.model.Folder
import com.cafe.data.source.db.model.Note

@Dao
interface FolderDao {
    @Query("SELECT * FROM folder")
    suspend fun getAllFolders(): List<Folder>

    @Query("SELECT * FROM folder WHERE  `index` = :index")
    suspend fun getNoteById(index: Long): Folder

    @Delete
    suspend fun deleteNote(note: Folder)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(folder: Folder): Long

    @Query("SELECT * FROM notes WHERE folderId = :id")
    suspend fun getAllNotesInFolder(id: Long): List<Note>
}