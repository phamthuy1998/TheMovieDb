package com.thuypham.ptithcm.baseapp.data.remote.response


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.model.CategorySubItem

@Parcelize
data class MovieGenres(
    @SerializedName("genres")
    var genres: List<MovieGenre>?
) : CategorySubItem, Parcelable


@Parcelize
data class MovieGenre(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("name")
    var name: String?
) : Parcelable {
}