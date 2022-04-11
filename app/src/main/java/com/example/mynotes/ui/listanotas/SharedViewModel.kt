package com.example.mynotes.ui.listanotas

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mynotes.Nota

class SharedViewModel : ViewModel() {
    var selected = MutableLiveData<String>()

    fun select(nota: String) {
        selected.value = nota
    }
}