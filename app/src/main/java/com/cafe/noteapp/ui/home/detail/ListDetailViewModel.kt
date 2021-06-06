package com.cafe.noteapp.ui.home.detail

import com.cafe.data.source.db.dao.NoteDao
import com.cafe.noteapp.ui.base.BaseViewModel
import javax.inject.Inject

class ListDetailViewModel @Inject constructor(
    private val noteDao: NoteDao
) : BaseViewModel() {


}