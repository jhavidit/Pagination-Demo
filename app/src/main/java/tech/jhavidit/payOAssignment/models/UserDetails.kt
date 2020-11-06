package tech.jhavidit.payOAssignment.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserDetails(
    val ad: Ad? = null,
    val data: List<Data>? = null,
    val page: Int = 0,
    @Json(name = "per_page")
    val perPage: Int = 0,
    val total: Int = 0,
    @Json(name = "total_pages")
    val totalPages: Int = 0
) : Parcelable

@Parcelize
data class Data(
    val avatar: String = "",
    val email: String = "",
    @Json(name = "first_name")
    val firstName: String = "",
    val id: Int = 0,
    @Json(name = "last_name")
    val lastName: String = ""
) : Parcelable

@Parcelize
data class Ad(
    val company: String="",
    val text: String="",
    val url: String=""
) : Parcelable