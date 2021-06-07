package com.cafe.data.source.db.model

import androidx.room.*


@Entity(
    tableName = "notes",
    indices = [Index("index")],
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
    val index: Int?,
    @ColumnInfo(name = "creation_date")
    val creationDate: Long,
    val contents: String,
    val title: String,
    val folderId: String? = null
)
