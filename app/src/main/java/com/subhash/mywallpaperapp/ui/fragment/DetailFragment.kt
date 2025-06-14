package com.subhash.mywallpaperapp.ui.fragment

import android.app.WallpaperManager
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.sharp.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.subhash.mywallpaperapp.R
import com.subhash.mywallpaperapp.models.WallHavenResponse
import com.subhash.mywallpaperapp.util.Resource
import com.subhash.mywallpaperapp.util.getUriForId
import com.subhash.mywallpaperapp.util.shortToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import com.subhash.mywallpaperapp.ui.util.LoadImage

@AndroidEntryPoint
class DetailFragment : Fragment() {

    val bookMarkViewModel: BookmarkViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                DetailsContentWrapper()
            }
        }
    }

    @Composable
    fun DetailsContentWrapper() {
        val imageDetails = produceState<Resource<WallHavenResponse>>(
            initialValue = Resource.Loading()
        ) {
            value = bookMarkViewModel.getImageDetails(args.id)
        }.value

        when (imageDetails) {
            is Resource.Success -> {
                DetailsContent(imageDetails.data!!)
                bookMarkViewModel.checkBookmark(imageDetails.data.id!!)
            }
            is Resource.Error -> {
                Text(text = imageDetails.message!!, color = Color.Red)
            }
            is Resource.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DetailsContent(imageDetails: WallHavenResponse) {
        val isBookmark by bookMarkViewModel.isBookmark.observeAsState()
        BottomSheetScaffold(
            sheetContent = {
                ImageInformationAndOptions(imageDetails = imageDetails)
            },
            containerColor = colorResource(R.color.listBackground),
            sheetContainerColor = colorResource(R.color.pastelPrimary).copy(alpha = 0.8f),
            sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            sheetPeekHeight = 90.dp,
        ) { _ ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(R.color.listBackground))
            ) {
                LoadImage(url = imageDetails.path!!)
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                ) {
                    IconButton(
                        onClick = { findNavController().navigateUp() },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(colorResource(R.color.pastelPrimary).copy(alpha = 0.4f))
                            .size(45.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "back-button",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    IconButton(
                        onClick = { addBookmark(isBookmark == true, imageDetails) },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(colorResource(R.color.pastelPrimary).copy(alpha = 0.4f))
                            .size(45.dp)
                    ) {
                        Icon(
                            imageVector = if (isBookmark == true) Icons.Outlined.Bookmark else Icons.Outlined.BookmarkBorder,
                            contentDescription = "bookmark-button",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun ImageInformationAndOptions(imageDetails: WallHavenResponse) {
        Column(
            modifier = Modifier.padding(20.dp, 10.dp, 20.dp, 20.dp)
        ) {
            Divider(
                modifier = Modifier
                    .width(80.dp)
                    .height(5.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(3.dp))
                    .alpha(0.3f),
                color = Color.LightGray
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconAction(Icons.Outlined.ArrowDropDown, "download", { download(imageDetails.path!!, imageDetails.id!!) })
                IconAction(Icons.Sharp.Wallpaper, "set-wallpaper", { setWallpaper(imageDetails) })
                IconAction(Icons.Outlined.OpenInNew, "open-browser", {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(imageDetails.url)))
                })
                IconAction(Icons.Outlined.Landscape, "original-res", { navigateOriginalRes(imageDetails.path!!) })
                IconAction(Icons.Outlined.Share, "share", { shareImage(imageDetails.url!!) })
            }
            Spacer(modifier = Modifier.height(25.dp))
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .align(Alignment.CenterHorizontally)
                    .alpha(0.3f),
                color = Color.LightGray
            )
            RowWithIconAndText(Icons.Sharp.AccountCircle, imageDetails.uploader?.username ?: "")
            RowWithIconAndText(Icons.Sharp.HdrPlus, imageDetails.resolution ?: "")
            RowWithIconAndText(Icons.Sharp.Fingerprint, (imageDetails.views ?: 0).toString())
            RowWithIconAndText(Icons.Sharp.Category, imageDetails.category ?: "")
        }
    }

    @Composable
    fun IconAction(icon: ImageVector, description: String, onClick: () -> Unit) {
        Icon(
            imageVector = icon,
            contentDescription = description,
            tint = Color.White,
            modifier = Modifier
                .size(30.dp)
                .clickable(onClick = onClick)
        )
        Spacer(modifier = Modifier.width(10.dp))
    }

    @Composable
    fun RowWithIconAndText(icon: ImageVector, text: String) {
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = text, color = Color.White)
        }
    }

    private fun addBookmark(isBookmark: Boolean, imageDetails: WallHavenResponse) {
        if (!isBookmark) {
            bookMarkViewModel.setBookmark(imageDetails)
            requireContext().shortToast("Bookmark Added!")
        } else {
            bookMarkViewModel.deleteBookmark(imageDetails.id!!)
            requireContext().shortToast("Bookmark Removed")
        }
    }

    private fun setWallpaper(imageDetails: WallHavenResponse) {
        Glide.with(this)
            .asBitmap()
            .load(imageDetails.path!!)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    CoroutineScope(Dispatchers.IO).launch {
                        bookMarkViewModel.downloadImage(resource, imageDetails.id!!)
                        withContext(Dispatchers.Main) {
                            startWallpaperIntent(requireContext().getUriForId(imageDetails.id))
                        }
                    }
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }

    private fun startWallpaperIntent(uri: Uri) {
        try {
            val wallpaperIntent = WallpaperManager.getInstance(requireContext())
                .getCropAndSetWallpaperIntent(uri)
                .setDataAndType(uri, "image/*")
                .putExtra("mimeType", "image/*")

            startActivity(wallpaperIntent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun download(urlFull: String, id: String) {
        Glide.with(this)
            .asBitmap()
            .load(urlFull)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    bookMarkViewModel.downloadImage(resource, id)
                    requireContext().shortToast("Download Started")
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    requireContext().shortToast("Downloaded!")
                }
            })
    }

    private fun navigateOriginalRes(urlFull: String) {
        findNavController().navigate(DetailFragmentDirections.detailsToOR(urlFull))
    }

    private fun shareImage(url: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}