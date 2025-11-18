package com.example.practica1u3.Data.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practica1u3.Data.Entity.Student
import com.example.practica1u3.Data.InterfaceStudent.StudentDAO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class studentViewModel(private val studentDao: StudentDAO) : ViewModel() {

    val allStudents: StateFlow<List<Student>> = studentDao.getAll()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            emptyList()
        )

    fun insertStudents(vararg students: Student) {
        viewModelScope.launch {
            studentDao.insert(*students)
        }
    }

    fun updateStudents(vararg students: Student) {
        viewModelScope.launch {
            studentDao.update(*students)
        }
    }

    fun deleteStudent(student: Student) {
        viewModelScope.launch {
            studentDao.delete(student)
        }
    }

    private val _averageScore = MutableStateFlow<Float?>(null)
    val averageScore: StateFlow<Float?> = _averageScore.asStateFlow()

    private val _minScore = MutableStateFlow<Float?>(null)
    val minScore: StateFlow<Float?> = _minScore.asStateFlow()

    private val _top3Students = MutableStateFlow<List<Student>>(emptyList())
    val top3Students: StateFlow<List<Student>> = _top3Students.asStateFlow()

    fun fetchAverageScore() {
        viewModelScope.launch {
            _averageScore.value = studentDao.getAverage()
        }
    }

    fun fetchMinScore() {
        viewModelScope.launch {
            _minScore.value = studentDao.getMin()
        }
    }

    fun fetchTop3Students(group: String) {
        viewModelScope.launch {
            _top3Students.value = studentDao.getTop3(group)
        }
    }
}