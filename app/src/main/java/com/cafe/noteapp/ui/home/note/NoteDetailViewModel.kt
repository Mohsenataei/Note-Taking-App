package com.cafe.noteapp.ui.home.note

import com.cafe.data.source.repository.notes.NoteRepository
import com.cafe.noteapp.ui.base.BaseFragment
import com.cafe.noteapp.ui.base.BaseViewModel
import javax.inject.Inject

class NoteDetailViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : BaseViewModel() {
}