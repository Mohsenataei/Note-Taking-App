package com.cafe.noteapp.di.module

import com.cafe.noteapp.util.hepers.Convertor
import com.cafe.noteapp.util.provider.BaseResourceProvider
import com.cafe.noteapp.util.provider.ResourceProvider
import dagger.Binds
import dagger.Module

@Module
interface UtilModule {
    /**
     * Provide main implementation of [BaseResourceProvider] to access app raw resources
     */
    @Binds
    fun bindResourceProvider(resourceProvider: ResourceProvider): BaseResourceProvider


}