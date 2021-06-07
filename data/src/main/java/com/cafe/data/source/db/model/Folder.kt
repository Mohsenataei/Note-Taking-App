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
    val id: Long,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "create_date")
    val createDate: Long
)
// expected
// create_date=Column{name='create_date', type='TEXT', affinity='2', notNull=true, primaryKeyPosition=0, defaultValue='null'}

// found
// create_date=Column{name='create_date', type='BIGINT', affinity='3', notNull=true, primaryKeyPosition=0, defaultValue='null'}