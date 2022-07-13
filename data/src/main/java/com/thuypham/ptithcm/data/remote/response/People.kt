package com.thuypham.ptithcm.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class Person(
    @SerializedName("adult")
    var adult: Boolean?,
    @SerializedName("gender")
    var gender: Int?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("known_for")
    var knownFor: List<Movie>?,
    @SerializedName("known_for_department")
    var knownForDepartment: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("popularity")
    var popularity: Double?,
    @SerializedName("profile_path")
    var profilePath: String?
) : Parcelable {

}
