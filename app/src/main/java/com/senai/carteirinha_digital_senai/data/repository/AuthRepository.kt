package com.senai.carteirinha_digital_senai.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


// Extensão para usar DataStore (forma moderna de salvar preferências)
private val Context.dataStore by preferencesDataStore(name = "auth_prefs")

class AuthRepository(private val context: Context) {
    private val PIN_KEY = stringPreferencesKey("user_pin")

    // Recupera o PIN salvo
    val userPin: Flow<String?> = context.dataStore.data.map { it[PIN_KEY] }

    // Salva um novo PIN (Cadastro de senha)
    suspend fun salvarPin(pin: String) {
        context.dataStore.edit { it[PIN_KEY] = pin }
    }

    // Em AuthRepository.kt
    suspend fun resetarPin() {
        context.dataStore.edit { preferences ->
            preferences.remove(PIN_KEY)
        }
    }
}