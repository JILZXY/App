package com.example.examen3.utils

object Constants {
    const val BASE_URL = "https://pokeapi.co/api/v2/"
    const val POKEMON_ENDPOINT = "pokemon"
    const val POKEMON_SPECIES_ENDPOINT = "pokemon-species"
    const val TYPE_ENDPOINT = "type"
    const val PAGE_SIZE = 20
    const val INITIAL_OFFSET = 0
    const val MAX_POKEMON_ID = 1010
    const val NETWORK_TIMEOUT = 30L
    const val DATABASE_NAME = "pokemon_database"
    const val FAVORITES_TABLE = "favorites_table"
    const val OFFICIAL_ARTWORK_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"
    const val SPRITE_EXTENSION = ".png"

    object Routes {
        const val HOME = "home"
        const val DETAIL = "detail"
        const val SEARCH = "search"
        const val FAVORITES = "favorites"
        const val DETAIL_WITH_ARG = "detail/{pokemonId}"
    }

    object Args {
        const val POKEMON_ID = "pokemonId"
    }

    object ErrorMessages {
        const val NETWORK_ERROR = "Error de conexión. Verifica tu internet."
        const val UNKNOWN_ERROR = "Ocurrió un error inesperado."
        const val POKEMON_NOT_FOUND = "Pokémon no encontrado."
        const val EMPTY_FAVORITES = "No tienes Pokémon favoritos aún."
        const val EMPTY_SEARCH = "No se encontraron resultados."
    }

    object PokemonTypes {
        const val NORMAL = "normal"
        const val FIRE = "fire"
        const val WATER = "water"
        const val ELECTRIC = "electric"
        const val GRASS = "grass"
        const val ICE = "ice"
        const val FIGHTING = "fighting"
        const val POISON = "poison"
        const val GROUND = "ground"
        const val FLYING = "flying"
        const val PSYCHIC = "psychic"
        const val BUG = "bug"
        const val ROCK = "rock"
        const val GHOST = "ghost"
        const val DRAGON = "dragon"
        const val DARK = "dark"
        const val STEEL = "steel"
        const val FAIRY = "fairy"
    }

    object Stats {
        const val HP = "hp"
        const val ATTACK = "attack"
        const val DEFENSE = "defense"
        const val SPECIAL_ATTACK = "special-attack"
        const val SPECIAL_DEFENSE = "special-defense"
        const val SPEED = "speed"
    }

    const val ANIMATION_DURATION = 300
    const val SHIMMER_DURATION = 1000
    const val MIN_SEARCH_LENGTH = 2
    const val SEARCH_DEBOUNCE_MS = 500L
}