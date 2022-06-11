package com.thuypham.ptithcm.baseapp.data.remote.response


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.data.remote.response.Movie
import com.thuypham.ptithcm.baseapp.model.CategorySubItem
import com.thuypham.ptithcm.baseapp.ui.adapter.ItemModel
import com.thuypham.ptithcm.baseapp.util.ApiHelper

@Parcelize
data class MovieResponse(
    @SerializedName("page")
    var page: Int?,
    @SerializedName("results")
    var results: ArrayList<Movie>?,
    @SerializedName("total_pages")
    var totalPages: Int?,
    @SerializedName("total_results")
    var totalResults: Int?
) : Parcelable


@Parcelize
class Movie(
    @SerializedName("adult")
    var adult: Boolean?,
    @SerializedName("backdrop_path")
    var backdropPath: String?,
    @SerializedName("first_air_date")
    var firstAirDate: String?,
    @SerializedName("genre_ids")
    var genreIds: List<Int>?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("media_type")
    var mediaType: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("origin_country")
    var originCountry: List<String>?,
    @SerializedName("original_language")
    var originalLanguage: String?,
    @SerializedName("original_name")
    var originalName: String?,
    @SerializedName("original_title")
    var originalTitle: String?,
    @SerializedName("overview")
    var overview: String?,
    @SerializedName("popularity")
    var popularity: Double?,
    @SerializedName("poster_path")
    var posterPath: String?,
    @SerializedName("release_date")
    var releaseDate: String?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("video")
    var video: Boolean?,
    @SerializedName("vote_average")
    var voteAverage: Double?,
    @SerializedName("vote_count")
    var voteCount: Int?
) : ItemModel, Parcelable {
    override fun layoutId(): Int {
        return R.layout.item_movie
    }

    fun getImagePath() = ApiHelper.baseImageV3Url().format(posterPath)
}