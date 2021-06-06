package com.cafe.noteapp.ui.home

import androidx.lifecycle.ViewModel
import com.cafe.noteapp.di.builder.ViewModelKey
import com.cafe.noteapp.ui.home.detail.ListDetailFragment
import com.cafe.noteapp.ui.home.detail.ListDetailViewModel
import com.cafe.noteapp.ui.home.dialog.NewFolderViewModel
import com.cafe.noteapp.ui.home.list.HomeListViewModel
import com.cafe.noteapp.ui.home.note.NoteDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class HomeViewModelBuilder {
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeListViewModel::class)
    abstract fun bindHomeListViewModel(homeListViewModel: HomeListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ListDetailViewModel::class)
    abstract fun bindListDetailViewModel(listDetailViewModel: ListDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NoteDetailViewModel::class)
    abstract fun bindNoteDetailViewModel(noteDetailViewModel: NoteDetailViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(NewFolderViewModel::class)
    abstract fun bindNewFolderViewModel(newFolderViewModel: NewFolderViewModel): ViewModel

}