package com.cafe.noteapp.di.builder

import com.cafe.noteapp.ui.home.HomeActivity
import com.cafe.noteapp.ui.home.HomeFragmentProvider
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [(HomeFragmentProvider::class)])
    internal abstract fun bindHomeActivity(): HomeActivity
}