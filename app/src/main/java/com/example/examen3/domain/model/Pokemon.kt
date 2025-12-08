package com.example.examen3.domain.model

data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: List<PokemonType>,
    val height: Int = 0,
    val weight: Int = 0,
    val baseExperience: Int = 0,
    val hp: Int = 0,
    val attack: Int = 0,
    val defense: Int = 0,
    val specialAttack: Int = 0,
    val specialDefense: Int = 0,
    val speed: Int = 0,
    val abilities: List<String> = emptyList()
)