package com.cafe.noteapp.ui.home.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import arrow.core.Ior
import com.cafe.data.source.db.model.Note
import com.cafe.data.source.mapper.Error
import com.cafe.data.source.repository.notes.NoteRepository
import com.cafe.noteapp.ui.base.BaseViewModel
import com.cafe.noteapp.util.livedata.NonNullLiveData
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeListViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : BaseViewModel() {

    private val _allNotes = NonNullLiveData<List<Note>>(emptyList())
    val allNote: LiveData<List<Note>>
        get() = _allNotes

    init {
        getAllNotes()
    }

    fun getAllNotes() {
        viewModelScope.launch {
            when (val result = noteRepository.getAllNotes()) {
                is Either.Right -> _allNotes.value = result.b
                is Either.Left -> showError(result.a)
            }
        }
    }

    private fun showError(error: Error) {
        Log.d(TAG, "showError: $error")
    }


    companion object {
        val TAG = "HomeListViewModel"
    }
}