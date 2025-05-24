package com.subhash.mywallpaperapp.persistence

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [BookmarkImage::class],version = 1)
abstract class BookmarkDatabase : RoomDatabase() {
    abstract fun bookmarkDao() : BookmarkDao
}