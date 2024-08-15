package jp.craftman1take.stationinfoapp.data

sealed interface Entity {
    val name: String

    data class Area(
        override val name: String,
    ) : Entity

    data class Prefecture(
        val area: Area,
        override val name: String,
    ) : Entity

    data class Line(
        val prefecture: Prefecture,
        override val name: String,
    ) : Entity

    sealed interface Station : Entity {
        val line: Line
        val latitude: Double
        val longitude: Double

        data class Starting(
            override val line: Line,
            override val name: String,
            val nextName: String,
            override val latitude: Double,
            override val longitude: Double,
        ) : Station

        data class Terminal(
            override val line: Line,
            override val name: String,
            val previousName: String,
            override val latitude: Double,
            override val longitude: Double,
        ) : Station

        data class Normal(
            override val line: Line,
            override val name: String,
            val previousName: String,
            val nextName: String,
            override val latitude: Double,
            override val longitude: Double,
        ) : Station
    }
}