package com.cafe.noteapp.ui.home.dialog

import com.cafe.data.source.db.model.Folder
import com.cafe.data.source.repository.notes.NoteRepository
import com.cafe.noteapp.ui.base.BaseViewModel
import javax.inject.Inject

class NewFolderViewModel @Inject constructor(
    private val noteRepository: NoteRepository

) : BaseViewModel() {

}