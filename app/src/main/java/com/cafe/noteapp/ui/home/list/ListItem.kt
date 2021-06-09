package com.cafe.noteapp.ui.home.list

import android.graphics.drawable.Drawable

data class ListItem(
        val id: Int = -1,
        val name: String,
        val type: String,
        val description: String?,
        val createDate: String?,
        val icon: Drawable?,
        val iconBackground: Drawable?
)