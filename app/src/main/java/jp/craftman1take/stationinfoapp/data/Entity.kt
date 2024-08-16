package jp.craftman1take.stationinfoapp.data

import androidx.compose.ui.graphics.Color

sealed interface Entity {
    val name: String

    /**
     * 地域
     */
    data class Area(
        override val name: String,
    ) : Entity {
        // 駅一覧で地域ごとにつける鉄道ラインカラー（少しお遊び）
        val stationLineColor: Color?
            get() = when (name) {
                "北海道" -> Color(0xFF90ee90)
                "東北", "関東" -> Color.Green
                "中部" -> Color(0xFFFFA500)
                "近畿", "中国" -> Color.Blue
                "四国" -> Color(0xFFADD8E6)
                "九州" -> Color.Red
                else -> null
            }
    }

    /**
     * 都道府県
     */
    data class Prefecture(
        val area: Area,
        override val name: String,
    ) : Entity

    /**
     * 路線
     */
    data class Line(
        val prefecture: Prefecture,
        override val name: String,
    ) : Entity

    /**
     * 駅
     */
    sealed interface Station : Entity {
        val line: Line
        val latitude: Double
        val longitude: Double

        /**
         * 始発駅
         */
        data class Starting(
            override val line: Line,
            override val name: String,
            val nextName: String,
            override val latitude: Double,
            override val longitude: Double,
        ) : Station

        /**
         * 終着駅
         */
        data class Terminal(
            override val line: Line,
            override val name: String,
            val previousName: String,
            override val latitude: Double,
            override val longitude: Double,
        ) : Station

        /**
         * 一般駅（途中駅）
         */
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