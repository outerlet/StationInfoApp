package jp.craftman1take.stationinfoapp.network

import jp.craftman1take.stationinfoapp.data.HeartRails
import jp.craftman1take.stationinfoapp.data.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HeartRailsService {
    @GET("api/json?method=getAreas")
    suspend fun getAreas() : Response<HeartRails.Area>

    @GET("api/json?method=getPrefectures")
    suspend fun getPrefectures(@Query("area") areaName: String) : Response<HeartRails.Prefecture>

    @GET("api/json?method=getLines")
    suspend fun getLines(@Query("prefecture") prefectureName: String) : Response<HeartRails.Line>

    @GET("api/json?method=getStations")
    suspend fun getStations(
        @Query("prefecture") prefectureName: String,
        @Query("line") lineName: String,
    ) : Response<HeartRails.Station>
}