package com.example.practica1u3.Data

import android.app.Application
import androidx.room.Room

class ApplicationClass : Application() {
    lateinit var database: AppDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "student_database"
        ).build()
    }
}