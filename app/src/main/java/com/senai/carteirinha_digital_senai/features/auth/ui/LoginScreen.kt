package com.senai.carteirinha_digital_senai.features.auth.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.senai.carteirinha_digital_senai.features.auth.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    isCadastro: Boolean = false,
    onAuthSuccess: () -> Unit
) {
    val savedPin by viewModel.savedPin.collectAsState()
    var pinInput by remember { mutableStateOf("") }
    var erro by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (isCadastro) "Cadastre seu PIN de Acesso" else "Digite seu PIN",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = pinInput,
            onValueChange = { pinInput = it },
            label = { Text("PIN (4 dígitos)") },
            visualTransformation = PasswordVisualTransformation(),
            isError = erro,
            modifier = Modifier.fillMaxWidth()
        )

        if (erro) {
            Text("PIN Incorreto!", color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (isCadastro) {
                    viewModel.cadastrarPin(pinInput)
                    onAuthSuccess()
                } else {
                    if (pinInput == savedPin) onAuthSuccess() else erro = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isCadastro) "Salvar PIN" else "Entrar")
        }

        if (!isCadastro) { // Só mostra o reset se não estiver na tela de criação
            TextButton(
                onClick = { viewModel.resetarPin() },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(
                    text = "Esqueci meu PIN (Resetar)",
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}