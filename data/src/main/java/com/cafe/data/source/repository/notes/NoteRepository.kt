package com.cafe.data.source.repository.notes

import android.content.Context
import arrow.core.Either
import arrow.core.Either.Left
import arrow.core.Either.Right
import com.cafe.data.source.db.dao.FolderDao
import com.cafe.data.source.db.dao.NoteDao
import com.cafe.data.source.db.model.File
import com.cafe.data.source.db.model.Folder
import com.cafe.data.source.db.model.Note
import com.cafe.data.source.mapper.Error
import com.cafe.data.source.mapper.ErrorMapper
import com.cafe.data.source.repository.BaseRepository
import javax.inject.Inject

class NoteRepository @Inject constructor(
    errorMapper: ErrorMapper,
    private val noteDao: NoteDao,
    private val folderDao: FolderDao
) : BaseRepository(errorMapper) {

    suspend fun getAllNotes(): Either<Error, List<Note>> {
        return safeCall { noteDao.getNotes() }
    }

    suspend fun insertNote(note: Note): Either<Error, Long> {
        return safeCall { noteDao.insertOrUpdate(note) }
    }

    suspend fun deleteNote(noteId: Int): Either<Error,Int>  {
      return  safeCall {
            noteDao.deleteNote(noteId)
        }
    }

    suspend fun getNotesInFolder(folderId: String): Either<Error, List<Note>> {
        return safeCall { noteDao.getNotesInFolder(folderId) }
    }

    suspend fun getNoteById(id: Int): Either<Error, Note> {

        return safeCall { noteDao.getNoteById(id) }
    }

    suspend fun updateNote(note: Note): Either<Error, Int> {
        return safeCall { noteDao.updateNote(note) }
    }

    suspend fun insertNewFolder(folder: Folder): Either<Error, Long> {
        return safeCall { folderDao.insertOrUpdate(folder) }
    }

    suspend fun getAllFolders(): Either<Error, List<Folder>> {
        return safeCall { folderDao.getAllFolders() }
    }

    suspend fun updateFolder(folder: Folder): Either<Error, Int> {
        return safeCall { folderDao.update(folder) }
    }

    suspend fun getOrphanNotes(): Either<Error, List<Note>> {
        return safeCall { noteDao.getOrphanNotes() }
    }

    suspend fun getNotesCountInFolder(folderId: Int?): Either<Error, Int> {
        return safeCall { noteDao.getFNotesInFolderCount(folderId) }
    }


    // new approach

    suspend fun getListItem(): Either<Error, List<File>> {
        var notes: MutableList<Note>? = null
        var folders: List<Folder>? = null
        when (val result = getOrphanNotes()) {
            is Left -> return result
            is Right -> notes = result.b.toMutableList()
        }

        when (val result = getAllFolders()) {
            is Left -> return result
            is Right -> folders = result.b
        }

        val listItem = notes.map {
            File(
                id = it.index ?: -1,
                name = it.title,
                description = null,
                childCount = 0,
                type = "NOTE",
                createdData = it.creationDate
            )
        }.toMutableList()
            .apply {
                folders.map {
                    File(
                        id = it.id.toInt(),
                        name = it.name,
                        description = null,
                        type = "FOLDER",
                        createdData = it.createDate
                    )
                }.let {
                    this?.addAll(
                        it
                    )
                }
            }.sortedWith(compareByDescending { it.createdData })

        return Either.right(listItem)

    }
}