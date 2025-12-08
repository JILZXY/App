package com.example.examen3.utils

import androidx.compose.ui.graphics.Color

import java.util.Locale

fun String.capitalizeFirst(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault())
        else it.toString()
    }
}

fun Int.toPokemonId(): String {
    return "#${this.toString().padStart(3, '0')}"
}


fun String.extractPokemonId(): Int {
    return this.trimEnd('/').split("/").last().toIntOrNull() ?: 0
}

fun Int.toOfficialArtworkUrl(): String {
    return "${Constants.OFFICIAL_ARTWORK_URL}${this}${Constants.SPRITE_EXTENSION}"
}

fun Int.decimetersToFeetInches(): String {
    val totalInches = this * 3.937 // 1 decímetro = 3.937 pulgadas
    val feet = (totalInches / 12).toInt()
    val inches = (totalInches % 12).toInt()
    return "$feet'$inches\""
}

fun Int.hectogramsToPounds(): String {
    val pounds = this * 0.220462
    return String.format("%.1f lbs", pounds)
}

fun Int.hectogramsToKilograms(): String {
    val kg = this / 10.0
    return String.format("%.1f kg", kg)
}

fun Int.decimetersToMeters(): String {
    val meters = this / 10.0
    return String.format("%.1f m", meters)
}

fun String.toTypeColor(): Color {
    return when (this.lowercase()) {
        Constants.PokemonTypes.NORMAL -> Color(0xFFA8A878)
        Constants.PokemonTypes.FIRE -> Color(0xFFF08030)
        Constants.PokemonTypes.WATER -> Color(0xFF6890F0)
        Constants.PokemonTypes.ELECTRIC -> Color(0xFFF8D030)
        Constants.PokemonTypes.GRASS -> Color(0xFF78C850)
        Constants.PokemonTypes.ICE -> Color(0xFF98D8D8)
        Constants.PokemonTypes.FIGHTING -> Color(0xFFC03028)
        Constants.PokemonTypes.POISON -> Color(0xFFA040A0)
        Constants.PokemonTypes.GROUND -> Color(0xFFE0C068)
        Constants.PokemonTypes.FLYING -> Color(0xFFA890F0)
        Constants.PokemonTypes.PSYCHIC -> Color(0xFFF85888)
        Constants.PokemonTypes.BUG -> Color(0xFFA8B820)
        Constants.PokemonTypes.ROCK -> Color(0xFFB8A038)
        Constants.PokemonTypes.GHOST -> Color(0xFF705898)
        Constants.PokemonTypes.DRAGON -> Color(0xFF7038F8)
        Constants.PokemonTypes.DARK -> Color(0xFF705848)
        Constants.PokemonTypes.STEEL -> Color(0xFFB8B8D0)
        Constants.PokemonTypes.FAIRY -> Color(0xFFEE99AC)
        else -> Color(0xFF68A090)
    }
}

fun String.toTypeLightColor(): Color {
    return when (this.lowercase()) {
        Constants.PokemonTypes.NORMAL -> Color(0xFFD4D4C8)
        Constants.PokemonTypes.FIRE -> Color(0xFFFFC8A0)
        Constants.PokemonTypes.WATER -> Color(0xFFB4C8F8)
        Constants.PokemonTypes.ELECTRIC -> Color(0xFFFFF0A0)
        Constants.PokemonTypes.GRASS -> Color(0xFFBCE4A8)
        Constants.PokemonTypes.ICE -> Color(0xFFCCECEC)
        Constants.PokemonTypes.FIGHTING -> Color(0xFFE08078)
        Constants.PokemonTypes.POISON -> Color(0xFFD080D0)
        Constants.PokemonTypes.GROUND -> Color(0xFFF0E0B4)
        Constants.PokemonTypes.FLYING -> Color(0xFFD4C8F8)
        Constants.PokemonTypes.PSYCHIC -> Color(0xFFFCACC4)
        Constants.PokemonTypes.BUG -> Color(0xFFD4DC90)
        Constants.PokemonTypes.ROCK -> Color(0xFFDCD09C)
        Constants.PokemonTypes.GHOST -> Color(0xFFB8ACC4)
        Constants.PokemonTypes.DRAGON -> Color(0xFFB89CFC)
        Constants.PokemonTypes.DARK -> Color(0xFFB8AC98)
        Constants.PokemonTypes.STEEL -> Color(0xFFDCDCE8)
        Constants.PokemonTypes.FAIRY -> Color(0xFFF7CCD6)
        else -> Color(0xFFA4D0C8)
    }
}

fun String.translateType(): String {
    return when (this.lowercase()) {
        Constants.PokemonTypes.NORMAL -> "Normal"
        Constants.PokemonTypes.FIRE -> "Fuego"
        Constants.PokemonTypes.WATER -> "Agua"
        Constants.PokemonTypes.ELECTRIC -> "Eléctrico"
        Constants.PokemonTypes.GRASS -> "Planta"
        Constants.PokemonTypes.ICE -> "Hielo"
        Constants.PokemonTypes.FIGHTING -> "Lucha"
        Constants.PokemonTypes.POISON -> "Veneno"
        Constants.PokemonTypes.GROUND -> "Tierra"
        Constants.PokemonTypes.FLYING -> "Volador"
        Constants.PokemonTypes.PSYCHIC -> "Psíquico"
        Constants.PokemonTypes.BUG -> "Bicho"
        Constants.PokemonTypes.ROCK -> "Roca"
        Constants.PokemonTypes.GHOST -> "Fantasma"
        Constants.PokemonTypes.DRAGON -> "Dragón"
        Constants.PokemonTypes.DARK -> "Siniestro"
        Constants.PokemonTypes.STEEL -> "Acero"
        Constants.PokemonTypes.FAIRY -> "Hada"
        else -> this.capitalizeFirst()
    }
}

fun String.translateStat(): String {
    return when (this.lowercase()) {
        Constants.Stats.HP -> "PS"
        Constants.Stats.ATTACK -> "Ataque"
        Constants.Stats.DEFENSE -> "Defensa"
        Constants.Stats.SPECIAL_ATTACK -> "At. Esp."
        Constants.Stats.SPECIAL_DEFENSE -> "Def. Esp."
        Constants.Stats.SPEED -> "Velocidad"
        else -> this.capitalizeFirst()
    }
}

fun Int.toStatColor(): Color {
    return when {
        this >= 150 -> Color(0xFF00C853)
        this >= 100 -> Color(0xFF76FF03)
        this >= 80 -> Color(0xFFFFEB3B)
        this >= 50 -> Color(0xFFFF9800)
        else -> Color(0xFFFF5252)
    }
}