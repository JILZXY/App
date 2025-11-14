package com.example.practica1u3.Data.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.practica1u3.Data.InterfaceStudent.StudentDAO

class StudentFactory(private val studentDao: StudentDAO) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(studentViewModel::class.java)){
            @Suppress("UNCHEKED_CAST")
            return studentViewModel(studentDao) as T
            }
            throw IllegalArgumentException("VIew Model Class Incorrecto")
    }
}