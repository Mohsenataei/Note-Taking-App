package com.cafe.noteapp.di.builder

import androidx.lifecycle.ViewModelProvider
import com.cafe.noteapp.ui.home.HomeViewModelBuilder
import com.cafe.noteapp.viewmdel.NoteViewModelFactory
import dagger.Binds
import dagger.Module


@Module(
    includes = [
        (HomeViewModelBuilder::class)
    ]
)
abstract class ViewModelBuilder {
    @Binds
    abstract fun bindViewModelFactory(noteViewModelFactory: NoteViewModelFactory): ViewModelProvider.Factory
}
