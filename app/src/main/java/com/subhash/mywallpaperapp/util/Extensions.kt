package com.subhash.mywallpaperapp.util

import android.R.attr.duration
import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Environment
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.core.content.FileProvider
import androidx.transition.TransitionManager
import com.github.chrisbanes.photoview.BuildConfig
import java.io.File


fun Context.shortToast(message: String) = Toast.makeText(this, message, LENGTH_SHORT).show()

fun ViewGroup.makeFadeTransition(animationDuration: Long) {
    val fade: androidx.transition.Fade = androidx.transition.Fade()

    fade.apply {
        duration = animationDuration
    }
    TransitionManager.beginDelayedTransition(this, fade)
}


fun Context.getUriForId(id: String): Uri =
    FileProvider.getUriForFile(
        this, "com.subhash.mywallpaperapp" + ".fileprovider",
        File("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}/WallPortal/$id.jpg")
    )

