package com.senai.carteirinha_digital_senai.features.configuracao.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.senai.carteirinha_digital_senai.features.carteirinha.viewmodel.AlunoViewModel

@Composable
fun DadosAlunoScreen(
    viewModel: AlunoViewModel,
    onDadosSalvos: () -> Unit
) {
    // Estados locais para o formulário
    var nome by remember { mutableStateOf("") }
    var curso by remember { mutableStateOf("") }
    var matricula by remember { mutableStateOf("") }
    var codigoQr by remember { mutableStateOf("") }
    var fotoUri by remember { mutableStateOf<String?>(null) }

    // Launcher para selecionar imagem da galeria
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        fotoUri = uri?.toString()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "Configurar Carteirinha", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(value = nome, onValueChange = { nome = it }, label = { Text("Nome Completo") }, modifier = Modifier.fillMaxWidth())

        OutlinedTextField(value = curso, onValueChange = { curso = it }, label = { Text("Curso") }, modifier = Modifier.fillMaxWidth())

        OutlinedTextField(value = matricula, onValueChange = { matricula = it }, label = { Text("Matrícula") }, modifier = Modifier.fillMaxWidth())

        OutlinedTextField(value = codigoQr, onValueChange = { codigoQr = it }, label = { Text("Código do QR Code") }, modifier = Modifier.fillMaxWidth())

        Button(onClick = { launcher.launch("image/*") }) {
            Text(if (fotoUri == null) "Selecionar Foto" else "Foto Selecionada ✓")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.salvarAluno(nome, curso, matricula, fotoUri, codigoQr)
                onDadosSalvos()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = nome.isNotBlank() && matricula.isNotBlank()
        ) {
            Text("Salvar e Gerar Carteirinha")
        }
    }
}