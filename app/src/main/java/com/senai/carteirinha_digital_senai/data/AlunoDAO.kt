package com.senai.carteirinha_digital_senai.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AlunoDAO {
    @Query("SELECT * FROM alunos LIMIT 1")
    fun getAluno(): Flow<Aluno?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(aluno: Aluno)
}