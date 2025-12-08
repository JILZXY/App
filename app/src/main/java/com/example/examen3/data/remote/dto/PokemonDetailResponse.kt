package com.example.examen3.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PokemonDetailResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("height")
    val height: Int,
    @SerializedName("weight")
    val weight: Int,
    @SerializedName("base_experience")
    val baseExperience: Int?,
    @SerializedName("types")
    val types: List<TypeSlot>,
    @SerializedName("stats")
    val stats: List<StatItem>,
    @SerializedName("abilities")
    val abilities: List<AbilitySlot>,
    @SerializedName("sprites")
    val sprites: Sprites,
    @SerializedName("species")
    val species: Species
)

data class TypeSlot(
    @SerializedName("slot")
    val slot: Int,
    @SerializedName("type")
    val type: Type
)

data class Type(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)

data class StatItem(
    @SerializedName("base_stat")
    val baseStat: Int,
    @SerializedName("effort")
    val effort: Int,
    @SerializedName("stat")
    val stat: Stat
)

data class Stat(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)

data class AbilitySlot(
    @SerializedName("ability")
    val ability: Ability,
    @SerializedName("is_hidden")
    val isHidden: Boolean,
    @SerializedName("slot")
    val slot: Int
)

data class Ability(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)

data class Sprites(
    @SerializedName("front_default")
    val frontDefault: String?,
    @SerializedName("front_shiny")
    val frontShiny: String?,
    @SerializedName("back_default")
    val backDefault: String?,
    @SerializedName("back_shiny")
    val backShiny: String?,
    @SerializedName("other")
    val other: OtherSprites?
)

data class OtherSprites(
    @SerializedName("official-artwork")
    val officialArtwork: OfficialArtwork?
)

data class OfficialArtwork(
    @SerializedName("front_default")
    val frontDefault: String?
)

data class Species(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)