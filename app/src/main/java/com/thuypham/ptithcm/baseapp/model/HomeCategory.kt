package com.thuypham.ptithcm.baseapp.model

import android.content.Context
import com.thuypham.ptithcm.baseapp.R
import java.lang.ref.WeakReference

data class HomeCategory(
    val id: Int,
    val type: String,
) {
    fun toHomeCategoryData(index: Int, context: WeakReference<Context>): HomeCategoryData {
        val homeCategory: HomeCategory = this
        return HomeCategoryData(
            id = homeCategory.id,
            type = type,
            title = getTitleByCategoryType(homeCategory.type, context),
            listItems = arrayListOf(LoadingItem()),
            position = index
        )
    }

    private fun getTitleByCategoryType(categoryType: String, context: WeakReference<Context>): String {
        context.get()?.run {
            return when (categoryType) {
                HomeCategoryType.MOVIE_TRENDING -> getString(R.string.movie_trending)
                HomeCategoryType.MOVIE_NOW_PLAYING -> getString(R.string.movie_now_playing)
                HomeCategoryType.MOVIE_UPCOMING -> getString(R.string.movie_up_coming)
                HomeCategoryType.MOVIE_POPULAR -> getString(R.string.movie_popular)
                HomeCategoryType.MOVIE_TOP_RATE -> getString(R.string.movie_top_rate)
                HomeCategoryType.MOVIE_GENRES -> getString(R.string.movie_genres)
                HomeCategoryType.POPULAR_PEOPLE -> getString(R.string.popular_people)
                else -> ""
            }

        }
        return ""
    }
}

class LoadingItem

data class HomeCategoryData(
    val id: Int,
    val position: Int,
    val type: String,
    val title: String,
    var listItems: ArrayList<Any>? = null
) {
    override fun toString(): String {
        return "id: $id, position: $position, type: $type, title: $title, listItems:$listItems-size:${listItems?.size}"
    }
}

object HomeCategoryType {
    const val MOVIE_TRENDING = "MovieTrending"
    const val MOVIE_NOW_PLAYING = "MovieNowPlaying"
    const val MOVIE_UPCOMING = "MovieUpComing"
    const val MOVIE_POPULAR = "MoviePopular"
    const val MOVIE_TOP_RATE = "MovieTopRate"
    const val MOVIE_GENRES = "MovieGenres"
    const val POPULAR_PEOPLE = "PopularPeople"
}