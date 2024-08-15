package jp.craftman1take.stationinfoapp.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.craftman1take.stationinfoapp.network.HeartRailsService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RetrofitModule {
    @Singleton
    @Provides
    fun provideHeartRailsService(): HeartRailsService =
        Retrofit.Builder()
            .baseUrl("https://express.heartrails.com")
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
                )
            )
            .build()
            .create(HeartRailsService::class.java)
}