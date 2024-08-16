package jp.craftman1take.stationinfoapp.data


val sampleAreaList = listOf(
    Entity.Area("関東"),
    Entity.Area("中部"),
    Entity.Area("近畿"),
    Entity.Area("九州"),
)

val samplePrefectureList = listOf("大阪", "兵庫", "京都", "奈良", "和歌山", "滋賀").map {
    Entity.Prefecture(
        area = sampleAreaList.first(),
        name = it,
    )
}

val sampleLineList = listOf(
    "JR大阪環状線",
    "JR東西線",
    "JR関西空港線",
    "JR関西本線〔大和路線〕(奈良-JR難波)",
    "JR桜島線〔ゆめ咲線〕",
    "JR山陽新幹線",
    "JR東海道本線(米原-神戸)",
    "JR片町線〔学研都市線〕",
    "JR東海道新幹線",
    "京阪交野線",
    "京阪本線",
    "近鉄信貴線",
    "近鉄大阪線",
    "近鉄長野線",
    "近鉄けいはんな線",
    "近鉄道明寺線",
    "近鉄奈良線",
).map { Entity.Line(prefecture = samplePrefectureList.first(), name = it) }

private val line = checkNotNull(sampleLineList.find { it.name == "JR大阪環状線" })

val sampleStationList = listOf(
    Entity.Station.Normal(
        line = line,
        name = "大阪",
        previousName = "天満",
        nextName = "福島",
        latitude = 34.702398,
        longitude = 135.495188,
    ),
    Entity.Station.Normal(
        line = line,
        name = "福島",
        previousName = "大阪",
        nextName = "野田",
        latitude = 34.697167,
        longitude = 135.486563,
    ),
    Entity.Station.Normal(
        line = line,
        name = "野田",
        previousName = "福島",
        nextName = "西九条",
        latitude = 34.689069,
        longitude = 135.474837,
    ),
    Entity.Station.Normal(
        line = line,
        name = "西九条",
        previousName = "野田",
        nextName = "弁天町",
        latitude = 34.68269,
        longitude = 135.466779,
    ),
    Entity.Station.Normal(
        line = line,
        name = "弁天町",
        previousName = "西九条",
        nextName = "大正",
        latitude = 34.669403,
        longitude = 135.462348,
    ),
    Entity.Station.Normal(
        line = line,
        name = "大正",
        previousName = "弁天町",
        nextName = "芦原橋",
        latitude = 34.665582,
        longitude = 135.479932,
    ),
    Entity.Station.Normal(
        line = line,
        name = "芦原橋",
        previousName = "大正",
        nextName = "今宮",
        latitude = 34.658608,
        longitude = 135.48924,
    ),
    Entity.Station.Normal(
        line = line,
        name = "今宮",
        previousName = "芦原橋",
        nextName = "新今宮",
        latitude = 34.654156,
        longitude = 135.492975,
    ),
    Entity.Station.Normal(
        line = line,
        name = "新今宮",
        previousName = "今宮",
        nextName = "天王寺",
        latitude = 34.650149,
        longitude = 135.501076,
    ),
    Entity.Station.Normal(
        line = line,
        name = "天王寺",
        previousName = "新今宮",
        nextName = "寺田町",
        latitude = 34.647243,
        longitude = 135.514124,
    ),
    Entity.Station.Normal(
        line = line,
        name = "寺田町",
        previousName = "天王寺",
        nextName = "桃谷",
        latitude = 34.647957,
        longitude = 135.523437,
    ),
    Entity.Station.Normal(
        line = line,
        name = "桃谷",
        previousName = "寺田町",
        nextName = "鶴橋",
        latitude = 34.658453,
        longitude = 135.527908,
    ),
    Entity.Station.Normal(
        line = line,
        name = "鶴橋",
        previousName = "桃谷",
        nextName = "玉造",
        latitude = 34.665264,
        longitude = 135.530133,
    ),
    Entity.Station.Normal(
        line = line,
        name = "玉造",
        previousName = "鶴橋",
        nextName = "森ノ宮",
        latitude = 34.673559,
        longitude = 135.532901,
    ),
    Entity.Station.Normal(
        line = line,
        name = "森ノ宮",
        previousName = "玉造",
        nextName = "大阪城公園",
        latitude = 34.680412,
        longitude = 135.533996,
    ),
    Entity.Station.Normal(
        line = line,
        name = "大阪城公園",
        previousName = "森ノ宮",
        nextName = "京橋",
        latitude = 34.68858,
        longitude = 135.534482,
    ),
    Entity.Station.Normal(
        line = line,
        name = "京橋",
        previousName = "大阪城公園",
        nextName = "桜ノ宮",
        latitude = 34.696047,
        longitude = 135.534253,
    ),
    Entity.Station.Normal(
        line = line,
        name = "桜ノ宮",
        previousName = "京橋",
        nextName = "天満",
        latitude = 34.704976,
        longitude = 135.520944,
    ),
    Entity.Station.Normal(
        line = line,
        name = "天満",
        previousName = "桜ノ宮",
        nextName = "大阪",
        latitude = 34.704923,
        longitude = 135.512233,
    ),
)

val sampleStation = Entity.Station.Normal(
    name = "品川",
    line = Entity.Line(
        prefecture = Entity.Prefecture(area = Entity.Area("関東"), name = "東京都"),
        name = "JR山手線",
    ),
    latitude = 35.62876,
    longitude = 139.738999,
    previousName = "田町",
    nextName = "大崎",
)