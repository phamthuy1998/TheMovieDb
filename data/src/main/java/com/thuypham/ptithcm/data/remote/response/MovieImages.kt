package com.thuypham.ptithcm.data.remote.response
import com.google.gson.annotations.SerializedName


data class MovieImage(
    @SerializedName("backdrops")
    val backdrops: List<ImageInfo>? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("logos")
    val logos: List<Any?>? = null,
    @SerializedName("posters")
    val posters: List<ImageInfo>? = null
)
