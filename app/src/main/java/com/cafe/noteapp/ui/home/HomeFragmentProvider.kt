package com.cafe.noteapp.ui.home

import com.cafe.noteapp.ui.home.list.HomeListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


// TODO: 6/6/21 provide home fragments here
@Module
abstract class HomeFragmentProvider {
    @ContributesAndroidInjector
    abstract fun provideHomeListFragment(): HomeListFragment
}