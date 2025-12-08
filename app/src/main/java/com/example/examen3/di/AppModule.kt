package com.example.examen3.di

import com.example.examen3.data.local.dao.PokemonDao
import com.example.examen3.data.remote.api.PokeApiService
import com.example.examen3.data.repository.PokemonRepositoryImpl
import com.example.examen3.domain.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePokemonRepository(
        api: PokeApiService,
        dao: PokemonDao
    ): PokemonRepository {
        return PokemonRepositoryImpl(api, dao)
    }
}