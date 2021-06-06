package com.cafe.noteapp.ui.home.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import arrow.core.Either.Left
import arrow.core.Either
import arrow.core.Either.Right
import com.cafe.data.source.db.model.Folder
import com.cafe.data.source.db.model.Note
import com.cafe.data.source.mapper.Error
import com.cafe.data.source.repository.notes.NoteRepository
import com.cafe.noteapp.ui.base.BaseViewModel
import com.cafe.noteapp.ui.home.dialog.FolderItem
import com.cafe.noteapp.util.livedata.NonNullLiveData
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeListViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : BaseViewModel() {

    private val _allNotes = NonNullLiveData<List<Note>>(emptyList())
    val allNote: LiveData<List<Note>>
        get() = _allNotes

    private val _loadingLiveData = NonNullLiveData<Boolean>(false)
    val loadingLiveData: LiveData<Boolean>
        get() = _loadingLiveData


    init {
        getAllNotes()
    }

    fun getAllNotes() {
        viewModelScope.launch {
            when (val result = noteRepository.getAllNotes()) {
                is Right -> _allNotes.value = result.b
                is Left -> showError(result.a)
            }
        }
    }

    fun insertNewFolder(newFolder: FolderItem) {
        _loadingLiveData.value = false
        viewModelScope.launch {
            when (val result = noteRepository.insertNewFolder(mapToFolder(newFolder))) {
                is Right -> _loadingLiveData.value = true
                is Left -> {
                    _loadingLiveData.value = true
                    showError(result.a)
                }
            }
        }

    }

    private fun showError(error: Error) {
        Log.d(TAG, "showError: $error")
    }

    private fun mapToFolder(folderItem: FolderItem) =
        Folder(0, folderItem.folderName, folderItem.createDate)


    companion object {
        val TAG = "HomeListViewModel"
    }
}