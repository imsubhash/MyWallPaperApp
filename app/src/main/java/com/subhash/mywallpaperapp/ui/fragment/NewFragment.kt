package com.subhash.mywallpaperapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import com.subhash.mywallpaperapp.ui.util.LoadingBox
import com.subhash.mywallpaperapp.ui.util.TopBarNew
import com.subhash.mywallpaperapp.ui.wallpaperLists.WallpaperListItem
import com.subhash.mywallpaperapp.util.Constants.PAGE_SIZE
import com.subhash.mywallpaperapp.R

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@AndroidEntryPoint
class NewFragment : Fragment() {

    private val postViewModel: PostViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val searchQuery = remember { mutableStateOf("") }
                Scaffold(
                    topBar = {
                        TopBarNew(
                            searchQuery.value,
                            {
                                postViewModel.updateSearch(it)
                                searchQuery.value = it
                            },
                            { postViewModel.loadInitData() },
                            postViewModel.searchProgress.value
                        )
                    },
                    containerColor = colorResource(R.color.listBackground) // Use containerColor instead of backgroundColor in Material3
                ) { paddingValues ->

                    WallpaperListSetup(Modifier.padding(paddingValues))

                }
            }
        }
    }

    @Composable
    fun WallpaperListSetup(modifier: Modifier = Modifier) {
        val newWallpapers = postViewModel.newList.value
        val loading = postViewModel.loadingNew.value
        val page = postViewModel.pageNew.value

        if (loading && page == 1) {
            LoadingBox()
        }
        if (newWallpapers.isEmpty() && !loading) {
            Text(
                text = "No Images!",
                color = Color.Red,
                modifier = modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier
        ) {
            itemsIndexed(
                items = newWallpapers
            ) { index, item ->
                Log.d("LVG", "WallpaperListSetup : $index")
                postViewModel.onChangeNewScrollPosition(index)
                if ((index + 1) >= (page * PAGE_SIZE) && !loading) {
                    postViewModel.nextPageNew()
                }
                WallpaperListItem(item) {
                    findNavController().navigate(
                        NewFragmentDirections.newToDetails(item.id!!)
                    )
                }
            }
        }
    }
}
