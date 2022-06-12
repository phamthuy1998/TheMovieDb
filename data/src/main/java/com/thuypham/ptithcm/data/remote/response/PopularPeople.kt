package com.thuypham.ptithcm.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class PopularPeople(
    @SerializedName("page")
    var page: Int?,
    @SerializedName("results")
    var people: List<Person>?,
    @SerializedName("total_pages")
    var totalPages: Int?,
    @SerializedName("total_results")
    var totalResults: Int?
) : Parcelable

@Parcelize
data class Person(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("profile_path")
    var profilePath: String?
) : Parcelable{

}

@Parcelize
data class KnownFor(
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
) : Parcelable