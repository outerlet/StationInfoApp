package jp.craftman1take.stationinfoapp.network

import jp.craftman1take.stationinfoapp.data.HeartRails
import jp.craftman1take.stationinfoapp.data.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HeartRailsService {
    @GET("api/json?method=getAreas")
    suspend fun getAreas() : Response<HeartRails.Area>

    @GET("api/json?method=getPrefectures&area={area_name}")
    suspend fun getPrefectures(@Path("area_name") areaName: String) : Response<HeartRails.Prefecture>

    @GET("api/json?method=getLines&prefecture={prefecture_name}")
    suspend fun getLines(@Path("prefecture_name") prefectureName: String) : Response<HeartRails.Line>

    @GET("api/json?method=getStations&prefecture={prefecture_name}&line={line_name}")
    suspend fun getStations(
        @Path("prefecture_name") prefectureName: String,
        @Path("line_name") lineName: String,
    ) : Response<HeartRails.Station>
}