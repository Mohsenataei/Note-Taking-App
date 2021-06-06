package com.cafe.data.source.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cafe.data.source.db.dao.NoteDao
import com.cafe.data.source.db.model.Note

@Database(entities = [Note::class], version = NoteDataBase.VERSION)
abstract class NoteDataBase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        const val DB_NAME = "note.db"
        const val VERSION = 1


        @Volatile
        private var instance: NoteDataBase? = null

        fun getInstance(context: Context): NoteDataBase {
            return instance ?: synchronized(this) {
                instance ?: buildDataBase(context).also {
                    instance = it
                }
            }
        }

        fun buildDataBase(context: Context): NoteDataBase {
            return Room
                .databaseBuilder(context, NoteDataBase::class.java, NoteDataBase.DB_NAME)
                .build()
        }
    }
}