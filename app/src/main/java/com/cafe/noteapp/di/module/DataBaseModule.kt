package com.cafe.noteapp.di.module

import android.content.Context
import androidx.room.Room
import com.cafe.data.source.db.AppDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DataBaseModule {
    @Provides
    @Singleton
    fun provideRoomDatabase(context: Context): AppDataBase {
        return Room
            .databaseBuilder(context, AppDataBase::class.java, AppDataBase.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

}