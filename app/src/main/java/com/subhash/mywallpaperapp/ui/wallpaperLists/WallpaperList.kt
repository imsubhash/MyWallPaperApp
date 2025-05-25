package com.subhash.mywallpaperapp.ui.wallpaperLists

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.subhash.mywallpaperapp.models.WallHavenResponse
import com.subhash.mywallpaperapp.persistence.BookmarkImage
import com.subhash.mywallpaperapp.ui.util.LoadImage

@Composable
fun WallpaperListItem(item: WallHavenResponse, onClick: () -> Unit) {
    Box(
        Modifier
            .height(260.dp)
            .padding(2.dp)
            .clip(RoundedCornerShape(2.dp))
            .clickable { onClick.invoke() }
    ) {
        LoadImage(url = item.thumbs?.small!!)
    }

}

@Composable
fun BookmarkListItem(item: BookmarkImage, onClick: () -> Unit) {
    Box(
        Modifier
            .height(350.dp)
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick.invoke() }
    ) {
        LoadImage(url = item.imageUrlFull!!)
    }

}

