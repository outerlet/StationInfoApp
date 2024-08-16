package jp.craftman1take.stationinfoapp.data

data class PresentList(
    val areaList: List<Entity.Area>? = null,
    val prefectureList: List<Entity.Prefecture>? = null,
    val lineList: List<Entity.Line>? = null,
    val stationList: List<Entity.Station>? = null,
) {
    constructor() : this(
        areaList = null,
        prefectureList = null,
        lineList = null,
        stationList = null,
    )

    val title: String
        get() = when {
            stationList != null -> "駅一覧"
            lineList != null -> "路線一覧"
            prefectureList != null -> "都道府県一覧"
            areaList != null -> "地域一覧"
            else -> ""
        }
}
