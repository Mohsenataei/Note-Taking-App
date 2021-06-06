package com.cafe.data.source.db

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cafe.data.source.db.dao.FolderDao
import com.cafe.data.source.db.dao.NoteDao
import com.cafe.data.source.db.model.Folder
import com.cafe.data.source.db.model.Note
import javax.inject.Inject

@Database(
    entities = [Note::class, Folder::class],
    version = NoteDataBase.VERSION
)
abstract class NoteDataBase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
    abstract fun folderDao(): FolderDao


    companion object {
        const val DB_NAME = "note.db"
        const val VERSION = 2

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `folder` (`index` INTEGER NOT NULL, `name` TEXT NOT NULL, `create_date` TEXT NOT NULL , PRIMARY KEY(`id`))"
                )
            }

        }

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
                .addMigrations(MIGRATION_1_2)
                .build()
        }
    }


}