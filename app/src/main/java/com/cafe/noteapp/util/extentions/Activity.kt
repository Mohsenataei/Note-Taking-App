package com.cafe.noteapp.util.extentions

import androidx.appcompat.app.AppCompatActivity
import com.cafe.noteapp.ui.home.dialog.DeleteDialogFragment

@Synchronized
fun AppCompatActivity.messageDialog(
    message: String,
    title: String,
    tag: String? = null,
    cancelable: Boolean = true,
    onclick: (() -> Unit)? = null,
    onCancel: (() -> Unit)? = null
) = DeleteDialogFragment.newInstance(message, title, onclick, onCancel)
    .show(supportFragmentManager, tag ?: "DeleteDialogMessage")


