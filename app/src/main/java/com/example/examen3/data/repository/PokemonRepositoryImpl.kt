package com.example.examen3.data.repository

import com.example.examen3.data.local.dao.PokemonDao
import com.example.examen3.data.local.entities.PokemonEntity
import com.example.examen3.data.remote.api.PokeApiService
import com.example.examen3.data.remote.dto.PokemonDetailResponse
import com.example.examen3.domain.model.Pokemon
import com.example.examen3.domain.model.PokemonType
import com.example.examen3.domain.repository.PokemonRepository
import com.example.examen3.utils.Constants
import com.example.examen3.utils.NetworkResult
import com.example.examen3.utils.extractPokemonId
import com.example.examen3.utils.toOfficialArtworkUrl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val api: PokeApiService,
    private val dao: PokemonDao
) : PokemonRepository {

    override suspend fun getPokemonList(limit: Int, offset: Int): NetworkResult<List<Pokemon>> {
        return try {
            val response = api.getPokemonList(limit, offset)
            if (response.isSuccessful && response.body() != null) {
                val pokemonList = response.body()!!.results.map { result ->
                    val id = result.url.extractPokemonId()
                    Pokemon(
                        id = id,
                        name = result.name,
                        imageUrl = id.toOfficialArtworkUrl(),
                        types = emptyList()
                    )
                }
                NetworkResult.Success(pokemonList)
            } else {
                NetworkResult.Error(Constants.ErrorMessages.NETWORK_ERROR)
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: Constants.ErrorMessages.UNKNOWN_ERROR)
        }
    }

    override suspend fun getPokemonDetail(id: Int): NetworkResult<Pokemon> {
        return try {
            val response = api.getPokemonById(id)
            if (response.isSuccessful && response.body() != null) {
                val detail = response.body()!!
                NetworkResult.Success(detail.toPokemon())
            } else {
                NetworkResult.Error(Constants.ErrorMessages.POKEMON_NOT_FOUND)
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: Constants.ErrorMessages.UNKNOWN_ERROR)
        }
    }

    override suspend fun searchPokemon(query: String): NetworkResult<Pokemon> {
        return try {
            val response = api.getPokemonByName(query.lowercase())
            if (response.isSuccessful && response.body() != null) {
                val detail = response.body()!!
                NetworkResult.Success(detail.toPokemon())
            } else {
                NetworkResult.Error(Constants.ErrorMessages.POKEMON_NOT_FOUND)
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: Constants.ErrorMessages.POKEMON_NOT_FOUND)
        }
    }

    override suspend fun addToFavorites(pokemon: Pokemon) {
        dao.insertPokemon(pokemon.toEntity())
    }

    override suspend fun removeFromFavorites(pokemonId: Int) {
        dao.deletePokemonById(pokemonId)
    }

    override fun getFavorites(): Flow<List<Pokemon>> {
        return dao.getAllFavorites().map { entities ->
            entities.map { it.toPokemon() }
        }
    }

    override suspend fun isFavorite(pokemonId: Int): Boolean {
        return dao.isFavorite(pokemonId)
    }

    override suspend fun getFavoriteById(pokemonId: Int): Pokemon? {
        return dao.getPokemonById(pokemonId)?.toPokemon()
    }

    private fun PokemonDetailResponse.toPokemon(): Pokemon {
        return Pokemon(
            id = id,
            name = name,
            imageUrl = sprites.other?.officialArtwork?.frontDefault
                ?: id.toOfficialArtworkUrl(),
            types = types.map { typeSlot ->
                PokemonType(
                    name = typeSlot.type.name,
                    slot = typeSlot.slot
                )
            },
            height = height,
            weight = weight,
            baseExperience = baseExperience ?: 0,
            hp = stats.find { it.stat.name == Constants.Stats.HP }?.baseStat ?: 0,
            attack = stats.find { it.stat.name == Constants.Stats.ATTACK }?.baseStat ?: 0,
            defense = stats.find { it.stat.name == Constants.Stats.DEFENSE }?.baseStat ?: 0,
            specialAttack = stats.find { it.stat.name == Constants.Stats.SPECIAL_ATTACK }?.baseStat
                ?: 0,
            specialDefense = stats.find { it.stat.name == Constants.Stats.SPECIAL_DEFENSE }?.baseStat
                ?: 0,
            speed = stats.find { it.stat.name == Constants.Stats.SPEED }?.baseStat ?: 0,
            abilities = abilities.map { it.ability.name }
        )
    }

    private fun Pokemon.toEntity(): PokemonEntity {
        return PokemonEntity(
            id = id,
            name = name,
            imageUrl = imageUrl,
            types = types.joinToString(",") { it.name },
            height = height,
            weight = weight,
            hp = hp,
            attack = attack,
            defense = defense,
            specialAttack = specialAttack,
            specialDefense = specialDefense,
            speed = speed,
            abilities = abilities.joinToString(",")
        )
    }

    private fun PokemonEntity.toPokemon(): Pokemon {
        return Pokemon(
            id = id,
            name = name,
            imageUrl = imageUrl,
            types = types.split(",").mapIndexed { index, typeName ->
                PokemonType(name = typeName, slot = index + 1)
            },
            height = height,
            weight = weight,
            hp = hp,
            attack = attack,
            defense = defense,
            specialAttack = specialAttack,
            specialDefense = specialDefense,
            speed = speed,
            abilities = abilities.split(",")
        )
    }
}