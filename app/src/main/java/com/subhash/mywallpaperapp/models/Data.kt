package com.subhash.mywallpaperapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Data {

    @SerializedName("data")
    @Expose
    val imageDetails : WallHavenResponse?= null
}