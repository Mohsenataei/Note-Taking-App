package com.cafe.noteapp.di.module

import android.content.Context
import androidx.room.Room
import com.cafe.data.source.db.NoteDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DataBaseModule {
    @Provides
    @Singleton
    fun provideRoomDatabase(context: Context) = NoteDataBase.getInstance(context)

    fun providePlayerDao(db: NoteDataBase) = db.noteDao()

}