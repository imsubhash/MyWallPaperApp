package com.subhash.mywallpaperapp.ui.fragment

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.subhash.mywallpaperapp.models.WallHavenResponse
import com.subhash.mywallpaperapp.persistence.BookmarkImage
import com.subhash.mywallpaperapp.repository.ImageDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.subhash.mywallpaperapp.util.FileUtils
import com.subhash.mywallpaperapp.util.Resource
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val fileUtils: FileUtils,
    private val repository: ImageDetailsRepository
) : ViewModel() {

    val isBookmark: MutableLiveData<Boolean> = MutableLiveData(false)
    val loading = mutableStateOf(false)
    val bookmarkList: LiveData<List<BookmarkImage>> = repository.getBookmarks().asLiveData()

    suspend fun getImageDetails(id: String): Resource<WallHavenResponse> {
        return repository.getWallpaperData(id)
    }

    fun setBookmark(item: WallHavenResponse) {
        isBookmark.value = true
        viewModelScope.launch {
            repository.setBookmark(item)
        }
    }

    fun checkBookmark(id: String){
        viewModelScope.launch {
            if (repository.checkBookmark(id)) isBookmark.value = true
        }
    }

    fun downloadImage(bitmap: Bitmap, id: String) {
        fileUtils.saveImage(bitmap, id)
    }

    fun deleteBookmark(id: String){
        isBookmark.value = false
        viewModelScope.launch {
            repository.deleteBookmark(id)
        }
    }
}