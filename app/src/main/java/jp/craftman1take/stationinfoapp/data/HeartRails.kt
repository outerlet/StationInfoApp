package jp.craftman1take.stationinfoapp.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Response<Res : HeartRails>(
    @Json(name = "response")
    val response: Res,
)

sealed interface HeartRails {
    @JsonClass(generateAdapter = true)
    data class Area(
        @Json(name = "area")
        val names: List<String>,
    ) : HeartRails

    @JsonClass(generateAdapter = true)
    data class Prefecture(
        @Json(name = "prefecture")
        val names: List<String>,
    ) : HeartRails

    @JsonClass(generateAdapter = true)
    data class Line(
        @Json(name = "line")
        val names: List<String>,
    ) : HeartRails

    @JsonClass(generateAdapter = true)
    data class Station(
        @Json(name = "name")
        val name: String,
        @Json(name = "prev")
        val previousName: String?,
        @Json(name = "next")
        val nextName: String?,
        @Json(name = "y")
        val latitude: String,
        @Json(name = "x")
        val longitude: String,
        @Json(name = "line")
        val line: String,
        @Json(name = "prefecture")
        val prefecture: String,
    ) : HeartRails
}