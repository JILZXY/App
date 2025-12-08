package com.example.examen3.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.examen3.utils.Constants

@Entity(tableName = Constants.FAVORITES_TABLE)
data class PokemonEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: String,
    val height: Int,
    val weight: Int,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int,
    val abilities: String,
    val addedAt: Long = System.currentTimeMillis()
)