package com.cafe.noteapp.di.module

import android.content.Context
import androidx.room.Room
import com.cafe.data.source.db.NoteDataBase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Singleton

@Module
object DataBaseModule {


    /**
     * provides Gson with custom [Date] converter for [Long] epoch times
     */
    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            // Deserializer to convert json long value into Date
            .registerTypeAdapter(
                Date::class.java,
                JsonDeserializer { json, _, _ ->
                    Date(json.asJsonPrimitive.asLong)
                }
            )
            // Serializer to convert Date value into long json primitive
            .registerTypeAdapter(
                Date::class.java,
                JsonSerializer<Date> { src, _, _ ->
                    JsonPrimitive(src.time)
                }
            )
            .create()
    }

    @Provides
    @Singleton
    fun provideRoomDatabase(context: Context) = NoteDataBase.getInstance(context)

    @Provides
    @Singleton
    fun provideNoteDao(db: NoteDataBase) = db.noteDao()

    @Provides
    @Singleton
    fun provideFolderDao(db: NoteDataBase) = db.folderDao()
}