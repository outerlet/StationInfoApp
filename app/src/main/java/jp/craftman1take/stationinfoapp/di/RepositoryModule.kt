package jp.craftman1take.stationinfoapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.craftman1take.stationinfoapp.repository.HeartRailsRepository
import jp.craftman1take.stationinfoapp.repository.impl.HeartRailsRepositoryImpl

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindHeartRailsRepository(
        impl: HeartRailsRepositoryImpl,
    ) : HeartRailsRepository
}