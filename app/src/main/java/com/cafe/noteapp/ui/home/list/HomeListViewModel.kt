package com.cafe.noteapp.ui.home.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import arrow.core.Either.Left
import arrow.core.Either.Right
import com.cafe.data.source.db.model.Folder
import com.cafe.data.source.db.model.Note
import com.cafe.data.source.mapper.Error
import com.cafe.data.source.repository.notes.NoteRepository
import com.cafe.noteapp.R
import com.cafe.noteapp.ui.base.BaseViewModel
import com.cafe.noteapp.ui.home.dialog.FolderItem
import com.cafe.noteapp.util.livedata.CombinedLiveData
import com.cafe.noteapp.util.livedata.NonNullLiveData
import com.cafe.noteapp.util.provider.BaseResourceProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeListViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val resourceProvider: BaseResourceProvider
) : BaseViewModel() {

    private val _allNotes = NonNullLiveData<List<NoteItem>>(emptyList())
    val allNotes: LiveData<List<NoteItem>>
        get() = _allNotes

    private val _loadingVisibility = NonNullLiveData<Boolean>(false)
    val loadingVisibility: LiveData<Boolean>
        get() = _loadingVisibility

    private val _allFolders = NonNullLiveData<List<FolderItem>>(emptyList())
    val allFolders: LiveData<List<FolderItem>>
        get() = _allFolders

    private val _ListItemLiveData = MediatorLiveData<List<ListItem>>().apply {
        addSource(_allFolders) {
            value = mapFoldersToListItem(it)
        }

        addSource(_allNotes) {
            value = mapNotesToListItem(it)
        }

    }
    val listItemLiveData: LiveData<List<ListItem>>
        get() = _ListItemLiveData


    private fun refreshList() {
        getAllNotes()
        getAllFolders()
    }

    init {
        refreshList()
    }

    private fun getAllNotes() {
        viewModelScope.launch {
            _loadingVisibility.value = true

            when (val result = noteRepository.getAllNotes()) {
                is Right -> {
                    _loadingVisibility.value = false
                    _allNotes.value = mapToNoteItem(result.b)
                }
                is Left -> {
                    _loadingVisibility.value = false
                    showError(result.a)
                }
            }
        }
    }

    private fun mapToNoteItem(notes: List<Note>): List<NoteItem> {
        return notes.map {
            NoteItem(
                id = it.folderId?.toInt(),
                content = it.contents,
                title = it.title,
                created_data = it.creationDate
            )
        }
    }

    private fun getAllFolders() {
        viewModelScope.launch {
            _loadingVisibility.value = true
            when (val result = noteRepository.getAllFolders()) {
                is Right -> {
                    _loadingVisibility.value = false
                    _allFolders.value = mapToFolderItem(result.b)
                }
                is Left -> {
                    _loadingVisibility.value = false
                    showError(result.a)
                }
            }
        }
    }

    fun insertNewFolder(newFolder: FolderItem) {
        _loadingVisibility.value = true
        viewModelScope.launch {
            when (val result = noteRepository.insertNewFolder(mapToFolder(newFolder))) {
                is Right -> {
                    _loadingVisibility.value = false
                    refreshList()
                }
                is Left -> {
                    _loadingVisibility.value = false
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

    private fun mapToFolderItem(folders: List<Folder>): List<FolderItem> {
        return folders.map { folder ->
            FolderItem(
                id = folder.id.toInt(),
                folderName = folder.name,
                createDate = folder.createDate
            )
        }.filter {
            it.id != 0
        }
    }

    private fun mapNotesToListItem(notes: List<NoteItem>): List<ListItem> {
        return notes.map {
            ListItem(
                id = it.id ?: -1,
                name = it.title,
                description = it.created_data.toString(),
                type = NOTE,
                icon = resourceProvider.getDrawable(R.drawable.ic_note_blue),
                iconBackground = resourceProvider.getDrawable(R.drawable.circle_light_blue_bg)
            )
        }
    }

    private fun mapFoldersToListItem(folders: List<FolderItem>): List<ListItem> {
        return folders.map {
            ListItem(
                id = it.id,
                name = it.folderName,
                description = "حاوی ${folders.size} یادداشت ",
                type = FOLDER,
                icon = resourceProvider.getDrawable(R.drawable.ic_folder_orange),
                iconBackground = resourceProvider.getDrawable(R.drawable.circle_light_orange_bg)
            )
        }
    }

    companion object {
        val TAG = "HomeListViewModel"
        val NOTE = "Note"
        val FOLDER = "Folder"
    }
}