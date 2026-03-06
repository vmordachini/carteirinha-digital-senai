package com.senai.carteirinha_digital_senai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.senai.carteirinha_digital_senai.data.Aluno
import com.senai.carteirinha_digital_senai.data.AlunoRepository
import com.senai.carteirinha_digital_senai.data.AppDatabase
import com.senai.carteirinha_digital_senai.ui.AlunoViewModel
import com.senai.carteirinha_digital_senai.ui.AlunoViewModelFactory
import com.senai.carteirinha_digital_senai.ui.DadosAlunoScreen
import com.senai.carteirinha_digital_senai.ui.theme.CarteirinhadigitalsenaiTheme

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

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = destinoInicial
                    ) {
                        // Tela da Carteirinha
                        composable("carteirinha") {
                            aluno?.let { dados ->
                                CarteirinhaDeEstudante(
                                    aluno = dados,
                                    onEditar = { navController.navigate("configurar") }
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

@Composable
fun CarteirinhaDeEstudante(aluno: Aluno, onEditar: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.senai_s_o_paulo_logo),
                contentDescription = "Logo SENAI",
                modifier = Modifier.height(40.dp)
            )
            IconButton(onClick = onEditar) {
                Icon(Icons.Default.Edit, contentDescription = "Editar Dados")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Foto dinâmica do banco de dados
                AsyncImage(
                    model = aluno.fotoUri ?: R.drawable.avatar_vazio,
                    contentDescription = "Foto do Aluno",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    contentScale = ContentScale.Crop,
                    error = painterResource(R.drawable.avatar_vazio),
                    placeholder = painterResource(R.drawable.avatar_vazio)
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = aluno.nome.uppercase(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = aluno.curso,
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )
                    Text(
                        text = "Matrícula: ${aluno.matricula}",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }

                // QR Code gerado a partir do código inserido pelo usuário
                val qrCodeBitmap = gerarQrCode(aluno.codigoQr)
                if (qrCodeBitmap != null) {
                    Image(
                        bitmap = qrCodeBitmap.asImageBitmap(),
                        contentDescription = "QR Code",
                        modifier = Modifier.size(120.dp)
                    )
                }

                Text(
                    text = "CARTEIRINHA DIGITAL",
                    fontWeight = FontWeight.Light,
                    letterSpacing = 2.sp,
                    color = Color.Red
                )
            }
        }
    }
}