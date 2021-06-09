package com.cafe.noteapp.ui.home.note

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import arrow.core.Either.Left
import arrow.core.Either.Right
import com.cafe.data.source.mapper.Error
import com.cafe.data.source.repository.notes.NoteRepository
import com.cafe.noteapp.ui.base.BaseViewModel
import com.cafe.noteapp.ui.home.list.NoteItem
import com.cafe.noteapp.ui.home.list.mapToNote
import com.cafe.noteapp.ui.home.list.mapToNoteItem
import com.cafe.noteapp.util.livedata.NonNullLiveData
import com.cafe.noteapp.util.livedata.SingleEventLiveData
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteDetailViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : BaseViewModel() {

    val isInsertionDone = SingleEventLiveData<Boolean>().apply { value = false }
    private val _noteLoadingLiveData = NonNullLiveData<Boolean>(false)
    val noteLoadingLiveData: LiveData<Boolean>
        get() = _noteLoadingLiveData

    private val _savedNoteLiveData = MutableLiveData<NoteItem>()
    val savedNoteItem: LiveData<NoteItem>
        get() = _savedNoteLiveData

    var currentFolerId = -1
    fun saveNote(noteItem: NoteItem) {
        _noteLoadingLiveData.value = true
        viewModelScope.launch {
            when (val result = noteRepository.insertNote(mapToNote(noteItem))) {
                is Right -> {
                    _noteLoadingLiveData.value = false
                    isInsertionDone.value = true
                }
                is Left -> {
                    _noteLoadingLiveData.value = false
                    showError(result.a)
                }
            }
        }
    }

    fun getNoteById(noteId: Int) {
        viewModelScope.launch {
            when (val result = noteRepository.getNoteById(noteId)) {
                is Right -> _savedNoteLiveData.value = mapToNoteItem(result.b)
                is Left -> showError(result.a)
            }
        }
    }


    private fun showError(error: Error) {
        Log.d(TAG, "showError: $error")
    }


    companion object {
        const val TAG = "NoteDetailFragment"
    }
}