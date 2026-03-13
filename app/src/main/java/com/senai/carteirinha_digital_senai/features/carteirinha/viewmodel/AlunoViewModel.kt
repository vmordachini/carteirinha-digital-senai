package com.senai.carteirinha_digital_senai.features.carteirinha.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.senai.carteirinha_digital_senai.data.model.Aluno
import com.senai.carteirinha_digital_senai.data.repository.AlunoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AlunoViewModel(private val repository: AlunoRepository) : ViewModel() {
    // Transforma o Flow do Repositório em um StateFlow para o Compose
    // Se o banco mudar, a UI reage automaticamente
    val alunoState: StateFlow<Aluno?> = repository.aluno
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun salvarAluno(nome: String, curso: String, matricula: String, fotoUri: String?, codigoQr: String) {
        viewModelScope.launch {
            val novoAluno = Aluno(
                id = 1, // Usamos ID fixo 1 pois só teremos uma carteirinha por app
                nome = nome,
                curso = curso,
                matricula = matricula,
                fotoUri = fotoUri,
                codigoQr = codigoQr
            )
            repository.salvarAluno(novoAluno)
        }
    }

    fun deletarAluno() {
        viewModelScope.launch {
            repository.deletarAluno() // Precisamos adicionar isso no repositório também
        }
    }
}