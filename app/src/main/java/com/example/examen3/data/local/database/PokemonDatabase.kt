package com.example.examen3.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.examen3.data.local.dao.PokemonDao
import com.example.examen3.data.local.entities.PokemonEntity

@Database(
    entities = [PokemonEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}