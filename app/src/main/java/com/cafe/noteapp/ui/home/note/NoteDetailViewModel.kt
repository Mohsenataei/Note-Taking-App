package com.cafe.noteapp.ui.home.note

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import arrow.core.Either.Left
import arrow.core.Either.Right
import com.cafe.data.source.mapper.Error
import com.cafe.data.source.repository.notes.NoteRepository
import com.cafe.noteapp.ui.base.BaseFragment
import com.cafe.noteapp.ui.base.BaseViewModel
import com.cafe.noteapp.ui.home.list.HomeListViewModel
import com.cafe.noteapp.ui.home.list.NoteItem
import com.cafe.noteapp.ui.home.list.mapToNote
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


    fun saveNote(noteItem: NoteItem) {
        _noteLoadingLiveData.value = false
        viewModelScope.launch {
            when (val result = noteRepository.insertNote(mapToNote(noteItem))) {
                is Right -> {
                    _noteLoadingLiveData.value = true
                    isInsertionDone.value = true
                }
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