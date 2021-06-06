package com.cafe.data.source.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "folder",
    indices = [Index("index")]
)
data class Folder(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "index")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "create_date")
    val createDate: String
)