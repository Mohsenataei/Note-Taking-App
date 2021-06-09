package com.cafe.noteapp.util.extentions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.cafe.noteapp.R
import com.cafe.noteapp.ui.home.dialog.DeleteDialogFragment

fun Fragment.findNaveController() =
    NavHostFragment.findNavController(this)

@Synchronized
fun Fragment.messageDialog(
    message: String,
    title: String,
    tag: String? = null,
    cancelable: Boolean = true,
    onConfirm: (() -> Unit)? = null,
    onCancel: (() -> Unit)? = null
) = DeleteDialogFragment.newInstance(message, title, onConfirm, onCancel)
    .show(childFragmentManager, tag ?: "DeleteDialogMessage")

@Synchronized
fun Fragment.DeleteDialog(
    message: String,
    title: String,
    onConfirm: (() -> Unit)?,
    onCancel: (() -> Unit)?
) = messageDialog(
    message,
    title,
    onCancel = onCancel,
    onConfirm = onConfirm
)


