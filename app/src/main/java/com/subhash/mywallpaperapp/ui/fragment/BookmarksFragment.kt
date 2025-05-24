package com.subhash.mywallpaperapp.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import com.subhash.mywallpaperapp.ui.util.TopBar
import com.subhash.mywallpaperapp.ui.wallpaperLists.BookmarkListItem
import com.subhash.mywallpaperapp.R



@AndroidEntryPoint
class BookmarksFragment : Fragment() {

    private val bookmarkViewModel: BookmarkViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Scaffold(
                    topBar = { TopBar() },
                    containerColor = colorResource(R.color.listBackground)
                ) {
                    BookmarkList()
                }
            }
        }
    }

    @Composable
    fun BookmarkList() {
        val bookmarkImages by bookmarkViewModel.bookmarkList.observeAsState()
        LazyColumn {
            itemsIndexed(
                items = bookmarkImages.orEmpty().asReversed()
            ) { _, item ->
                BookmarkListItem(item) {
                    findNavController().navigate(
                        BookmarksFragmentDirections.bookmarkToDet(item.imageName)
                    )
                }
            }
        }
    }
}


