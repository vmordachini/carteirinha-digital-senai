package com.senai.carteirinha_digital_senai.features.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.senai.carteirinha_digital_senai.data.repository.AuthRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AuthViewModel (private val repository: AuthRepository) : ViewModel() {
    val savedPin = repository.userPin.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    fun cadastrarPin(pin: String) {
        viewModelScope.launch { repository.salvarPin(pin) }
    }

    fun resetarPin() {
        viewModelScope.launch {
            repository.resetarPin()
        }
    }
}