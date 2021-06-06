package com.cafe.noteapp.ui.home.list

import android.graphics.drawable.Drawable

data class ListItem(
    val name: String,
    val type: String,
    val description: String,
    val icon: Drawable?,
    val iconBackground: Drawable?
)