package com.example.practica1u3.Data.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practica1u3.Data.Entity.Student
import com.example.practica1u3.Data.InterfaceStudent.StudentDAO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class studentViewModel(private val studentDao: StudentDAO) : ViewModel() {
    private val _studentId = MutableStateFlow<Int?>(null)
    fun insertUser(student: Student) {
        if (student.id > 0) {
            viewModelScope.launch {
                studentDao.insert(student)
                _studentId.value = student.id
            }
        }
    }
 }
