package com.thuypham.ptithcm.baseapp.model

import android.content.Context
import com.thuypham.ptithcm.baseapp.R

data class HomeCategory(
    val id: Int,
    val type: String,
) {
    fun toHomeCategoryData(index: Int, context: Context): HomeCategoryData {
        val homeCategory: HomeCategory = this
        return HomeCategoryData(
            id = homeCategory.id,
            type = type,
            title = getTitleByCategoryType(homeCategory.type, context),
            listItems = arrayListOf(LoadingItem()),
            position = index
        )
    }

    private fun getTitleByCategoryType(categoryType: String, context: Context): String {
        return when (categoryType) {
            HomeCategoryType.MOVIE_TRENDING -> context.getString(R.string.movie_trending)
            HomeCategoryType.MOVIE_NOW_PLAYING -> context.getString(R.string.movie_now_playing)
            HomeCategoryType.MOVIE_UPCOMING -> context.getString(R.string.movie_up_coming)
            HomeCategoryType.MOVIE_POPULAR -> context.getString(R.string.movie_popular)
            HomeCategoryType.MOVIE_TOP_RATE -> context.getString(R.string.movie_top_rate)
            HomeCategoryType.MOVIE_GENRES -> context.getString(R.string.movie_genres)
            HomeCategoryType.POPULAR_PEOPLE -> context.getString(R.string.popular_people)
            else -> ""
        }
    }
}

class LoadingItem

data class HomeCategoryData(
    val id: Int,
    val position: Int,
    val type: String,
    val title: String,
    var listItems: ArrayList<Any>? = null
)

object HomeCategoryType {
    const val MOVIE_TRENDING = "MovieTrending"
    const val MOVIE_NOW_PLAYING = "MovieNowPlaying"
    const val MOVIE_UPCOMING = "MovieUpComing"
    const val MOVIE_POPULAR = "MoviePopular"
    const val MOVIE_TOP_RATE = "MovieTopRate"
    const val MOVIE_GENRES = "MovieGenres"
    const val POPULAR_PEOPLE = "PopularPeople"
}