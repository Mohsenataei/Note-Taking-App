package com.cafe.data.source.db.model

import android.graphics.drawable.Drawable

data class File(
    val id: Int = -1,
    val name: String,
    val type: String,
    val createdData: Long,
    val childCount: Int? = -1,
    val description: String? = null,
    val icon: Drawable? = null,
    val iconBackground: Drawable? = null
)