package com.cafe.noteapp.ui.home.list

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import arrow.core.Either.Left
import arrow.core.Either.Right
import com.cafe.data.source.db.model.File
import com.cafe.data.source.db.model.Folder
import com.cafe.data.source.db.model.Note
import com.cafe.data.source.mapper.Error
import com.cafe.data.source.repository.notes.NoteRepository
import com.cafe.noteapp.R
import com.cafe.noteapp.event.DeleteNoteEvent
import com.cafe.noteapp.ui.base.BaseViewModel
import com.cafe.noteapp.ui.home.dialog.FolderItem
import com.cafe.noteapp.util.hepers.Convertor
import com.cafe.noteapp.util.livedata.NonNullLiveData
import com.cafe.noteapp.util.livedata.SingleEventLiveData
import com.cafe.noteapp.util.provider.BaseResourceProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeListViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val resourceProvider: BaseResourceProvider
) : BaseViewModel() {


    private val _loadingVisibility = NonNullLiveData<Boolean>(false)
    val loadingVisibility: LiveData<Boolean>
        get() = _loadingVisibility

    private val _listItemLiveData = NonNullLiveData<List<ListItem>>(emptyList())
    val listItemLiveData: LiveData<List<ListItem>>
        get() = _listItemLiveData

    val deleteEventLiveData = SingleEventLiveData<DeleteNoteEvent>()

    @Inject
    lateinit var convertor: Convertor


    private fun refreshList() {
        getFiles()
    }

    init {
        refreshList()
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


    fun onOptionClicked(listItem: ListItem) {
        when (listItem.type) {
            NOTE -> deleteEventLiveData.value = DeleteNoteEvent(listItem.id)
        }
    }


    private fun getFiles() {
        viewModelScope.launch {
            _loadingVisibility.value = true
            when (val result = noteRepository.getListItem()) {
                is Right -> {
                    _loadingVisibility.value = false
                    _listItemLiveData.value = mapFilesToListItem(result.b)
                }
                is Left -> {
                    _loadingVisibility.value = false
                    showError(result.a)
                }
            }
        }
    }


    private fun mapFilesToListItem(files: List<File>): List<ListItem> {
        return files.map {
            ListItem(
                id = it.id,
                name = it.name,
                type = it.type,
                description = if (it.type == NOTE) convertor.getTimeAgo(it.createdData) else "حاوی ${it.childCount} یادداشت ",
                createDate = convertor.getTimeAgo(it.createdData),
                icon = if (it.type == NOTE) resourceProvider.getDrawable(R.drawable.ic_note_blue) else resourceProvider.getDrawable(
                    R.drawable.ic_folder_orange
                ),
                iconBackground = if (it.type == NOTE) resourceProvider.getDrawable(R.drawable.circle_light_blue_bg) else resourceProvider.getDrawable(
                    R.drawable.circle_light_orange_bg
                )
            )
        }.filter { it.id != 0 }
    }

    fun deleteNote(noteId: Int) {
        viewModelScope.launch {
            _loadingVisibility.value = true
            when (val result = noteRepository.deleteNote(noteId)) {
                is Right -> {
                    _loadingVisibility.value = false
                    refreshList()
                }
                is Left -> {
                    showError(result.a)
                }
            }
        }
    }

    companion object {
        val TAG = "HomeListViewModel"
        val NOTE = "NOTE"
        val FOLDER = "FOLDER"
    }
}