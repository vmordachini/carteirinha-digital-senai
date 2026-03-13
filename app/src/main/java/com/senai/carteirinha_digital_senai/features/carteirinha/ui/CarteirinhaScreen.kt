package com.senai.carteirinha_digital_senai.features.carteirinha.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import coil.compose.AsyncImage
import com.senai.carteirinha_digital_senai.R
import com.senai.carteirinha_digital_senai.data.model.Aluno
import com.senai.carteirinha_digital_senai.core.util.gerarQrCode

@Composable
fun CarteirinhaScreen(
    aluno: Aluno,
    onEditar: () -> Unit,
    onDeletar: () -> Unit
) {
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
            Row {
                IconButton(onClick = onEditar) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar Dados")
                }
                // Botão de deletar para o CRUD
                IconButton(onClick = onDeletar) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_menu_delete),
                        contentDescription = "Excluir",
                        tint = Color.Red
                    )
                }
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
                AsyncImage(
                    model = aluno.fotoUri ?: R.drawable.avatar_vazio,
                    contentDescription = "Foto do Aluno",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    contentScale = ContentScale.Crop,
                    error = painterResource(R.drawable.avatar_vazio)
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = aluno.nome.uppercase(), fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    Text(text = aluno.curso, fontSize = 16.sp, color = Color.DarkGray)
                    Text(text = "Matrícula: ${aluno.matricula}", fontSize = 14.sp, color = Color.Gray)
                }

                val qrCodeBitmap = gerarQrCode(aluno.codigoQr)
                if (qrCodeBitmap != null) {
                    Image(
                        bitmap = qrCodeBitmap.asImageBitmap(),
                        contentDescription = "QR Code",
                        modifier = Modifier.size(120.dp)
                    )
                }

                Text(text = "CARTEIRINHA DIGITAL", fontWeight = FontWeight.Light, letterSpacing = 2.sp, color = Color.Red)
            }
        }
    }
}