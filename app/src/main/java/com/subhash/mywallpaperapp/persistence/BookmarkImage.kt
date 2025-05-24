package com.subhash.mywallpaperapp.persistence

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class BookmarkImage(
    @PrimaryKey val imageName: String,
    @ColumnInfo val imageUrlFull: String?,
    @ColumnInfo val imageUrlRegular: String?
) : Parcelable