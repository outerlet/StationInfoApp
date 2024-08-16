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
    ) : HeartRails {
        fun toEntityList(): List<Entity.Area> = names.map { Entity.Area(name = it) }
    }

    @JsonClass(generateAdapter = true)
    data class Prefecture(
        @Json(name = "prefecture")
        val names: List<String>,
    ) : HeartRails {
        fun toEntityList(area: Entity.Area): List<Entity.Prefecture> =
            names.map { Entity.Prefecture(area = area, name = it) }
    }

    @JsonClass(generateAdapter = true)
    data class Line(
        @Json(name = "line")
        val names: List<String>,
    ) : HeartRails {
        fun toEntityList(prefecture: Entity.Prefecture) =
            names.map { Entity.Line(prefecture = prefecture, name = it) }
    }

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
    ) : HeartRails {
        fun toEntity(line: Entity.Line): Entity.Station =
            when {
                previousName.isNullOrBlank() && !nextName.isNullOrBlank() ->
                    Entity.Station.Starting(
                        line = line,
                        name = name,
                        nextName = nextName,
                        latitude = latitude.toDouble(),
                        longitude = longitude.toDouble(),
                    )
                !previousName.isNullOrBlank() && nextName.isNullOrBlank() ->
                    Entity.Station.Terminal(
                        line = line,
                        name = name,
                        previousName = previousName,
                        latitude = latitude.toDouble(),
                        longitude = longitude.toDouble(),
                    )
                else ->
                    Entity.Station.Normal(
                        line = line,
                        name = name,
                        nextName = checkNotNull(nextName),
                        previousName = checkNotNull(previousName),
                        latitude = latitude.toDouble(),
                        longitude = longitude.toDouble(),
                    )
            }
    }
}