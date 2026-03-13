package com.senai.carteirinha_digital_senai.data.repository

import com.senai.carteirinha_digital_senai.data.local.AlunoDAO
import com.senai.carteirinha_digital_senai.data.model.Aluno
import kotlinx.coroutines.flow.Flow

class AlunoRepository (private val alunoDao: AlunoDAO) {
    // Retorna o Flow do banco de dados (sempre atualizado)
    val aluno: Flow<Aluno?> = alunoDao.getAluno()

    // Função para salvar ou atualizar o aluno
    suspend fun salvarAluno(aluno: Aluno) {
        alunoDao.insertOrUpdate(aluno)
    }

    // Encaminha a operação de exclusão para o DAO
    suspend fun deletarAluno() {
        alunoDao.deletarAluno()
    }
}