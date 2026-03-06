package com.senai.carteirinha_digital_senai.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alunos")
data class Aluno(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val curso: String,
    val matricula: String,
    val fotoUri: String?, // Armazena o caminho da foto selecionada
    val codigoQr: String  // Código que será transformado em QR Code
)
