package com.cafe.data.source.repository.notes

import androidx.room.Dao
import arrow.core.Either
import com.cafe.data.source.db.dao.NoteDao
import com.cafe.data.source.db.model.Note
import com.cafe.data.source.mapper.Error
import com.cafe.data.source.mapper.ErrorMapper
import com.cafe.data.source.repository.BaseRepository
import javax.inject.Inject

class NoteRepository @Inject constructor(
    errorMapper: ErrorMapper,
    private val noteDao: NoteDao
) : BaseRepository(errorMapper) {

    suspend fun getAllNotes(): Either<Error, List<Note>> {
        return safeCall { noteDao.getNotes() }
    }

    suspend fun insertNote(note: Note): Either<Error, Long> {
        return safeCall { noteDao.insertOrUpdate(note) }
    }

    suspend fun deleteNote(note: Note) {
        safeCall {
            noteDao.deleteNote(note)
        }
    }

    suspend fun getNoteById(creationDate: String): Either<Error, Note> {

        return safeCall { noteDao.getNoteById(creationDate) }
    }
}