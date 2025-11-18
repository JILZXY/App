package com.example.practica1u3.Data.InterfaceStudent

import androidx.room.Dao
import androidx.room.Delete
import com.example.practica1u3.Data.Entity.Student
import androidx.room.Query
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDAO {
    @Query("SELECT * FROM students")
    fun getAll(): Flow<List<Student>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg student: Student)
    @Delete()
    suspend fun delete(student: Student)
    @Update()
    suspend fun update(vararg student: Student)
    @Query("SELECT AVG(score) FROM students")
    suspend fun getAverage(): Float
    @Query("SELECT * FROM students WHERE `group` = :group ORDER BY score DESC LIMIT 3")
    suspend fun getTop3(group: String): List<Student>
    @Query("SELECT MIN(score) FROM students")
    suspend fun getMin(): Float

}