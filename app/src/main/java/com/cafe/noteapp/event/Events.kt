package com.cafe.noteapp.event

import com.cafe.noteapp.ui.home.dialog.FolderItem

data class DeleteFolderEvent(val folderItem: FolderItem)

data class DeleteNoteEvent(val noteId: Int)

data class UpdateFolderEvent(val folderItem: FolderItem)