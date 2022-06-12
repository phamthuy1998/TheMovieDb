package com.thuypham.ptithcm.data.remote.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieGenres(
    @SerializedName("genres")
    var genres: List<MovieGenre>?
) : Parcelable


@Parcelize
data class MovieGenre(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("name")
    var name: String?
) : Parcelable {
}