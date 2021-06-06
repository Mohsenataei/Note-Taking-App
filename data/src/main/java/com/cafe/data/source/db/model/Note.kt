package com.cafe.data.source.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "notes",
    indices = [Index("creation_data")]
)
data class Note(
    @PrimaryKey
    @ColumnInfo(name = "creation_date")
    val creationDate: String,
    val contents: String
)
