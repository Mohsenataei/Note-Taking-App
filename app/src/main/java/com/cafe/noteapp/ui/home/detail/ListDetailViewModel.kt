package com.cafe.noteapp.ui.home.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import arrow.core.Either.Left
import arrow.core.Either.Right
import com.cafe.data.source.mapper.Error
import com.cafe.data.source.repository.notes.NoteRepository
import com.cafe.noteapp.R
import com.cafe.noteapp.ui.base.BaseViewModel
import com.cafe.noteapp.ui.home.list.HomeListViewModel
import com.cafe.noteapp.ui.home.list.ListItem
import com.cafe.noteapp.ui.home.list.NoteItem
import com.cafe.noteapp.ui.home.list.mapToNoteItems
import com.cafe.noteapp.util.hepers.Convertor
import com.cafe.noteapp.util.livedata.NonNullLiveData
import com.cafe.noteapp.util.provider.BaseResourceProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListDetailViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val resourceProvider: BaseResourceProvider
) : BaseViewModel() {

    @Inject
    lateinit var convertor: Convertor
    private val _allNotesLiveData = NonNullLiveData<List<ListItem>>(emptyList())
    val allNotesLiveData: LiveData<List<ListItem>>
        get() = _allNotesLiveData

    var currentFolderId: Int = 0

    fun getAllNotes(folderId: String) {
        viewModelScope.launch {
            when (val result = noteRepository.getNotesInFolder(folderId)) {
                is Right -> {
                    // TODO: 6/8/21 fix this mess later
                    _allNotesLiveData.value = mapNotesToListItem(mapToNoteItems(result.b))
                }
                is Left -> {
                    showError(result.a)
                }
            }
        }
    }

    private fun mapNotesToListItem(notes: List<NoteItem>): List<ListItem> {
        return notes.map {
            ListItem(
                id = it.id ?: -1,
                name = it.title,
                description = it.created_data.toString(),
                type = HomeListViewModel.NOTE,
                createDate = convertor.getTimeAgo(it.created_data),
                icon = resourceProvider.getDrawable(R.drawable.ic_note_blue),
                iconBackground = resourceProvider.getDrawable(R.drawable.circle_light_blue_bg)
            )
        }
    }

    private fun showError(error: Error) {
        Log.d(TAG, "showError: ")
    }

    companion object {
        const val TAG = "ListDetailViewModel"
    }

}