package com.thuypham.ptithcm.data.remote.response


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class MovieGenres(
    @SerializedName("genres")
    var genres: List<MovieGenge>?
) : Parcelable