package com.senai.carteirinha_digital_senai.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.senai.carteirinha_digital_senai.data.model.Aluno
import kotlinx.coroutines.flow.Flow

@Dao
interface AlunoDAO {

    // Busca o único aluno cadastrado
    @Query("SELECT * FROM alunos LIMIT 1")
    fun getAluno(): Flow<Aluno?>

    // Insere um novo aluno ou substitui o existente se houver conflito de ID
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertOrUpdate(aluno: Aluno)

    // Remove todos os registros da tabela de alunos para o "Reset" da carteirinha
    @Query("DELETE FROM alunos")
    suspend fun deletarAluno()
}