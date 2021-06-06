package com.cafe.noteapp.di.component

import android.content.Context
import com.cafe.noteapp.app.NoteApp
import com.cafe.noteapp.di.builder.ActivityBuilder
import com.cafe.noteapp.di.module.AppModule
import com.cafe.noteapp.di.module.DataBaseModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        (DataBaseModule::class),
        (AppModule::class),
        (AndroidInjectionModule::class),
        (ActivityBuilder::class)
    ]
)
interface AppComponent : AndroidInjector<NoteApp> {
    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<NoteApp> {
        interface Factory {
            fun create(@BindsInstance application: Context): AppComponent
        }
    }


}