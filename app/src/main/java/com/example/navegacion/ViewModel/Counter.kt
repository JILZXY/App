package com.example.navegacion.ViewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class Counter: ViewModel(){
    private val _counter = mutableStateOf(0)
    val counter= _counter

    fun add() {
        _counter.value++
    }

    fun less(){
        _counter.value--
    }

    fun mul(){
        _counter.value *= _counter.value
    }

    fun division(){
        _counter.value /= _counter.value
    }

}