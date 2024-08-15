package jp.craftman1take.stationinfoapp.repository

import jp.craftman1take.stationinfoapp.data.Entity
import jp.craftman1take.stationinfoapp.data.HeartRails
import jp.craftman1take.stationinfoapp.data.Response

interface HeartRailsRepository {
    suspend fun requestAreas() : Response<HeartRails.Area>
    suspend fun requestPrefectures(area: Entity.Area) : Response<HeartRails.Prefecture>
    suspend fun requestLines(prefecture: Entity.Prefecture) : Response<HeartRails.Line>
    suspend fun requestStations(line: Entity.Line) : Response<HeartRails.Station>
}