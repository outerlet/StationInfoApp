package jp.craftman1take.stationinfoapp.repository.impl

import jp.craftman1take.stationinfoapp.data.Entity
import jp.craftman1take.stationinfoapp.data.HeartRails
import jp.craftman1take.stationinfoapp.data.Response
import jp.craftman1take.stationinfoapp.network.HeartRailsService
import jp.craftman1take.stationinfoapp.repository.HeartRailsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeartRailsRepositoryImpl @Inject constructor(
    private val service: HeartRailsService,
) : HeartRailsRepository {
    override suspend fun requestAreas(): Response<HeartRails.Area> = service.getAreas()

    override suspend fun requestPrefectures(area: Entity.Area): Response<HeartRails.Prefecture> =
        service.getPrefectures(areaName = area.name)

    override suspend fun requestLines(prefecture: Entity.Prefecture): Response<HeartRails.Line> =
        service.getLines(prefectureName = prefecture.name)

    override suspend fun requestStations(line: Entity.Line): Response<HeartRails.Station> =
        service.getStations(prefectureName = line.prefecture.name, lineName = line.name)
}