package com.example.examen3.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.examen3.data.local.entities.PokemonEntity
import com.example.examen3.utils.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: PokemonEntity)

    @Delete
    suspend fun deletePokemon(pokemon: PokemonEntity)

    @Query("SELECT * FROM ${Constants.FAVORITES_TABLE} ORDER BY addedAt DESC")
    fun getAllFavorites(): Flow<List<PokemonEntity>>

    @Query("SELECT * FROM ${Constants.FAVORITES_TABLE} WHERE id = :pokemonId")
    suspend fun getPokemonById(pokemonId: Int): PokemonEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM ${Constants.FAVORITES_TABLE} WHERE id = :pokemonId)")
    suspend fun isFavorite(pokemonId: Int): Boolean

    @Query("DELETE FROM ${Constants.FAVORITES_TABLE} WHERE id = :pokemonId")
    suspend fun deletePokemonById(pokemonId: Int)

    @Query("DELETE FROM ${Constants.FAVORITES_TABLE}")
    suspend fun deleteAllFavorites()

    @Query("SELECT COUNT(*) FROM ${Constants.FAVORITES_TABLE}")
    suspend fun getFavoritesCount(): Int

    @Query("SELECT * FROM ${Constants.FAVORITES_TABLE} WHERE name LIKE '%' || :query || '%' ORDER BY name ASC")
    fun searchFavorites(query: String): Flow<List<PokemonEntity>>
}