package com.senai.carteirinha_digital_senai.features.carteirinha.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.senai.carteirinha_digital_senai.data.repository.AlunoRepository

class AlunoViewModelFactory (private val repository: AlunoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlunoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AlunoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}