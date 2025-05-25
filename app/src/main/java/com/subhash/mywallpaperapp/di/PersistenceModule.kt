package com.subhash.mywallpaperapp.di

import android.content.Context
import androidx.room.Room
import com.subhash.mywallpaperapp.persistence.BookmarkDao
import com.subhash.mywallpaperapp.persistence.BookmarkDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext application: Context): BookmarkDatabase {
        return Room
            .databaseBuilder(application, BookmarkDatabase::class.java, "bookmark-database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideBookmarkDao(bookmarkDatabase: BookmarkDatabase) : BookmarkDao {
        return bookmarkDatabase.bookmarkDao()
    }
}