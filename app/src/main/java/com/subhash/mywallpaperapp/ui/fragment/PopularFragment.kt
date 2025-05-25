package com.subhash.mywallpaperapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.subhash.mywallpaperapp.R
import com.subhash.mywallpaperapp.ui.util.LoadingBox
import com.subhash.mywallpaperapp.ui.util.TopBar
import com.subhash.mywallpaperapp.ui.wallpaperLists.WallpaperListItem
import com.subhash.mywallpaperapp.util.Constants
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalFoundationApi
@AndroidEntryPoint
class PopularFragment : Fragment() {
    private val postViewModel: PostViewModel by viewModels()

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
                ) { paddingValues ->
                    WallpaperList(Modifier.padding(paddingValues))
                }
            }
        }
    }

    @ExperimentalFoundationApi
    @Composable
    fun WallpaperList(modifier: Modifier = Modifier) {
        val newWallpapers = postViewModel.popList.value ?: emptyList()
        val loading = postViewModel.loadingPop.value ?: false
        val page = postViewModel.pagePopular.value ?: 1

        if (loading && page == 1) {
            LoadingBox()
            return
        }
        if (newWallpapers.isEmpty() && !loading) {
            Text(text = "No Data", color = Color.Red, modifier = modifier)
            return
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier
        ) {
            itemsIndexed(newWallpapers) { index, item ->
                postViewModel.onChangePopularScrollPosition(index)
                if ((index + 1) >= (page * Constants.PAGE_SIZE) && !loading) {
                    postViewModel.nextPagePop()
                }
                WallpaperListItem(item) {
                    findNavController().navigate(
                        PopularFragmentDirections.popularToDetails(item.id!!)
                    )
                }
            }
        }
    }
}
