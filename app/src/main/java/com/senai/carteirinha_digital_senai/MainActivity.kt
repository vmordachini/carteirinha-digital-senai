package com.senai.carteirinha_digital_senai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.senai.carteirinha_digital_senai.data.repository.AlunoRepository
import com.senai.carteirinha_digital_senai.data.local.AppDatabase
import com.senai.carteirinha_digital_senai.features.carteirinha.viewmodel.AlunoViewModelFactory
import com.senai.carteirinha_digital_senai.features.configuracao.ui.DadosAlunoScreen
import com.senai.carteirinha_digital_senai.core.ui.theme.CarteirinhadigitalsenaiTheme
import com.senai.carteirinha_digital_senai.data.repository.AuthRepository
import com.senai.carteirinha_digital_senai.features.auth.ui.LoginScreen
import com.senai.carteirinha_digital_senai.features.auth.viewmodel.AuthViewModel
import com.senai.carteirinha_digital_senai.features.auth.viewmodel.AuthViewModelFactory
import com.senai.carteirinha_digital_senai.features.carteirinha.ui.CarteirinhaScreen
import com.senai.carteirinha_digital_senai.features.carteirinha.viewmodel.AlunoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicialização da infraestrutura de dados
        val db = AppDatabase.getDatabase(this)
        val repository = AlunoRepository(db.alunoDao())
        val factory = AlunoViewModelFactory(repository)

        setContent {
            CarteirinhadigitalsenaiTheme {
                val navController = rememberNavController()
                val viewModel: AlunoViewModel = viewModel(factory = factory)

                // Observa o estado do aluno no banco
                val aluno by viewModel.alunoState.collectAsState()

                // Define para onde o usuário vai ao abrir o app
                // Se não houver dados, ele é forçado a configurar primeiro
                val destinoInicial = if (aluno == null) "configurar" else "carteirinha"

                val authRepository = AuthRepository(LocalContext.current)
                val authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(
                    authRepository
                )
                )
                val savedPin by authViewModel.savedPin.collectAsState()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = when {
                            savedPin == null -> "cadastrar_pin"
                            else -> "login"
                        }
                    ) {
                        composable("cadastrar_pin") {
                            LoginScreen(viewModel = authViewModel, isCadastro = true) {
                                navController.navigate("configurar")
                            }
                        }
                        composable("login") {
                            LoginScreen(viewModel = authViewModel) {
                                navController.navigate("carteirinha")
                            }
                        }

                        // Tela da Carteirinha
                        composable("carteirinha") {
                            aluno?.let { dados ->
                                CarteirinhaScreen(
                                    aluno = dados,
                                    onEditar = { navController.navigate("configurar") },
                                    onDeletar = { viewModel.deletarAluno() }
                                )
                            }
                        }

                        // Tela de Formulário
                        composable("configurar") {
                            DadosAlunoScreen(
                                viewModel = viewModel,
                                onDadosSalvos = {
                                    // Navega para a carteirinha e limpa a pilha de navegação
                                    navController.navigate("carteirinha") {
                                        popUpTo("configurar") { inclusive = true }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
