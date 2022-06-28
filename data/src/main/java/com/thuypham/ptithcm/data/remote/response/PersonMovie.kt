package com.thuypham.ptithcm.data.remote.response
import android.os.Parcelable

import kotlinx.parcelize.Parcelize

import com.google.gson.annotations.SerializedName


@Parcelize
data class PersonMovie(
    @SerializedName("cast")
    var cast: List<Movie>?,
    @SerializedName("id")
    var id: Int?
) : Parcelable
