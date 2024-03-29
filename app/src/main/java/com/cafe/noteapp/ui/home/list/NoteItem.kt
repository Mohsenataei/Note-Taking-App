package com.cafe.noteapp.ui.home.list

import com.cafe.data.source.db.model.Note

data class NoteItem(
    val id: Int?,
    val folderId: Int? = 0,
    val content: String,
    val title: String,
    val created_data: Long
)

fun mapToNoteItems(notes: List<Note>): List<NoteItem> {
    return notes.map {
        NoteItem(
            id = it.index,
            folderId = it.folderId?.toInt(),
            content = it.contents,
            title = it.title,
            created_data = it.creationDate
        )
    }
}

fun mapToNoteItem(notes: Note): NoteItem {
    return NoteItem(
        id = notes.folderId?.toInt(),
        content = notes.contents,
        title = notes.title,
        created_data = notes.creationDate
    )
}

fun mapToNote(noteItem: NoteItem): Note {
    return Note(
        index = noteItem.id,
        folderId = noteItem.folderId,
        contents = noteItem.content,
        title = noteItem.title,
        creationDate = noteItem.created_data
    )
}
