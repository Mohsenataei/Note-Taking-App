package com.cafe.noteapp.di.module

import android.content.Context
import com.cafe.noteapp.app.NoteApp
import com.cafe.noteapp.di.builder.ViewModelBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module(includes = [ViewModelBuilder::class])
object AppModule {
    @Provides
    @Singleton
    fun provideContext(application: NoteApp): Context {
        return application
    }

}