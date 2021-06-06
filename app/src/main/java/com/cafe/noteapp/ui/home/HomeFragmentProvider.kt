package com.cafe.noteapp.ui.home

import com.cafe.noteapp.ui.home.detail.ListDetailFragment
import com.cafe.noteapp.ui.home.dialog.NewFolderDialog
import com.cafe.noteapp.ui.home.list.HomeListFragment
import com.cafe.noteapp.ui.home.note.NoteDetailFragment
import com.cafe.noteapp.ui.home.note.NoteDetailViewModel
import dagger.Module
import dagger.android.ContributesAndroidInjector


// TODO: 6/6/21 provide home fragments here
@Module
abstract class HomeFragmentProvider {
    @ContributesAndroidInjector
    abstract fun provideHomeListFragment(): HomeListFragment

    @ContributesAndroidInjector
    abstract fun provideListDetailFragment(): ListDetailFragment

    @ContributesAndroidInjector
    abstract fun provideNoteDetailFragment(): NoteDetailFragment


    @ContributesAndroidInjector
    abstract fun provideNewFolderDialogFragment(): NewFolderDialog
}