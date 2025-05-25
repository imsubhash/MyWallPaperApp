package com.subhash.mywallpaperapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OriginalResolutionFragment : Fragment() {

    private val navArgs: OriginalResolutionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                OriginalPhotoView(navArgs.urlFull)
            }
        }
    }

    @Composable
    fun OriginalPhotoView(url: String) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                PhotoView(context).apply {
                    // Optional: Configure PhotoView here if needed
                }
            },
            update = { view ->
                Glide.with(view.context)
                    .asBitmap()
                    .load(url)
                    .into(view)
            }
        )
    }
}
