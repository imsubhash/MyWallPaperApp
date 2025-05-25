package com.subhash.mywallpaperapp.ui.util

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun LoadImage(url: String) {
    GlideImage(
        imageModel = { url },
        imageOptions = ImageOptions(
            contentDescription = "Wallpaper image"
        ),
        modifier = Modifier.fillMaxHeight(),

    )
}
