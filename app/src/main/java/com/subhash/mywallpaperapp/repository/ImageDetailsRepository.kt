package com.subhash.mywallpaperapp.repository

import com.subhash.mywallpaperapp.data.JsonApi
import com.subhash.mywallpaperapp.models.WallHavenResponse
import com.subhash.mywallpaperapp.persistence.BookmarkDao
import com.subhash.mywallpaperapp.persistence.BookmarkImage
import com.subhash.mywallpaperapp.util.Constants
import com.subhash.mywallpaperapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn

import javax.inject.Inject

class ImageDetailsRepository @Inject constructor(
    private val wallpaperService: JsonApi,
    private val dao: BookmarkDao
) {

    fun getBookmarks(): Flow<List<BookmarkImage>> = dao.getAll()
        .flowOn(Dispatchers.Main)
        .conflate()

    suspend fun getNewList(currentPage: Int, queryParam: String): Resource<List<WallHavenResponse>> {
        val response = try {
            wallpaperService.getImageList(
                queryParam,
                Constants.sortingNew,
                currentPage
            ).body()?.data
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred.")
        }
        return Resource.Success(response!!)
    }

    suspend fun getPopularList(currentPage: Int): Resource<List<WallHavenResponse>> {
        val response = try {
            wallpaperService.getImageList(
                Constants.queryParamPopular,
                Constants.sortingPopular,
                currentPage
            ).body()?.data
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred.")
        }
        return Resource.Success(response!!)

    }

    suspend fun getWallpaperData(id: String): Resource<WallHavenResponse> {
        val response = try {
            wallpaperService.getImageDetails(id).body()?.imageDetails
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred.")
        }
        return Resource.Success(response!!)
    }

    suspend fun checkBookmark(name: String) : Boolean = (dao.getItemByName(name).isNotEmpty())

    suspend fun deleteBookmark(id: String) {
        dao.deleteBookmark(id)
    }

    suspend fun setBookmark(imageDetails: WallHavenResponse) {
        dao.insert(
            BookmarkImage(
                imageName = imageDetails.id!!,
                imageUrlFull = imageDetails.path,
                imageUrlRegular = imageDetails.thumbs?.small
            )
        )
    }

}