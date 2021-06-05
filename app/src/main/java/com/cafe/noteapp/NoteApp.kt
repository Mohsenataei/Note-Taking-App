package com.cafe.noteapp

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class NoteApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        TODO("Not yet implemented")
    }
}