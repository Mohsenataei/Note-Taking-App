package com.cafe.data.source.db.model

import androidx.room.*


@Entity(
    tableName = "notes",
    indices = [Index("creation_date")],
    foreignKeys = arrayOf(
        ForeignKey(
            entity = Folder::class,
            parentColumns = arrayOf("index"),
            childColumns = arrayOf("folderId"),
            onDelete = ForeignKey.CASCADE
        )
    )
)
data class Note(
    @PrimaryKey
    @ColumnInfo(name = "creation_date")
    val creationDate: String,
    val contents: String,
    val title: String,
    val folderId: String? = null
)
