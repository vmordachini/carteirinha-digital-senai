package com.senai.carteirinha_digital_senai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.senai.carteirinha_digital_senai.ui.theme.CarteirinhadigitalsenaiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CarteirinhadigitalsenaiTheme() {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CarteirinhaDeEstudante(modifier = Modifier.padding(innerPadding).fillMaxSize())
                }
            }
        }
    }
}


@Composable
fun CarteirinhaDeEstudante(modifier: Modifier = Modifier){
    Column (modifier, verticalArrangement = Arrangement.SpaceAround, horizontalAlignment = Alignment.CenterHorizontally){
        Image(
            painter = painterResource(R.drawable.senai_s_o_paulo_logo),
            contentDescription = "logo_senai_sp",
            modifier = Modifier.size(200.dp)

        )

        Image(
            painter = painterResource(R.drawable.avatar_vazio),
            contentDescription = "img_3359",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
        )
        Row {
            Text(text = "Nome: ",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Victor Mordachini",
                modifier = Modifier.padding(start = 10.dp),
                style = MaterialTheme.typography.bodyLarge
            )

        }
        Row {
            Text(text = "Curso: ",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Desenvolvimento de Sistemas",
                modifier = Modifier.padding(start = 10.dp),
                style = MaterialTheme.typography.bodyLarge
            )

        }
        QrCode(
            conteudo = "90000000001417333273"
        )
    }
}