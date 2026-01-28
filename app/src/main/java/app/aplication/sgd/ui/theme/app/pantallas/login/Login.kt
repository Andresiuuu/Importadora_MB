package com.example.loginapp

import TextFieldUserUi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.aplication.sgd.ui.theme.background.LowPolyBackground
import app.aplication.sgd.ui.theme.app.componentes.LoginButton
import app.aplication.sgd.ui.theme.app.componentes.Space
import app.aplication.sgd.ui.theme.app.componentes.TextFieldPassUi
import app.aplication.sgd.ui.theme.app.componentes.TextUi
import androidx.compose.ui.draw.blur
import androidx.compose.ui.res.painterResource
import app.aplication.sgd.R
import app.aplication.sgd.ui.theme.theme.LowPolyBackgroundToCard


@Preview
@Composable
fun Login(){
    Box(modifier = Modifier.fillMaxSize()) {
        //Fondo low-poly
        LowPolyBackground()

        // Variables internas
        var IdUsuario by rememberSaveable { mutableStateOf("") }
        var Password by rememberSaveable { mutableStateOf("") }

        // Contenedor principal
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(50.dp, 0.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.logo_tlyly1111_artguru),
                contentDescription = null,
                alignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(236.dp,70.dp)

            )
            Space(50)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(329.dp)
            ) {

                //Efecto transparente
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clip(RoundedCornerShape(9.dp))
                        .background(Color.White.copy(alpha = 0.10f))
                        .blur(radius = 8.dp)
                ){
                    LowPolyBackgroundToCard()
                }

                // Borde
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .border(
                            1.dp,
                            Color.White.copy(alpha = 0.80f),
                            RoundedCornerShape(9.dp)
                        )
                )

                // Contenido
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(33.dp, 30.dp)
                ) {
                    TextUi(
                        text =  "Inicie sesión para continuar",
                        size = 16,
                        color = Color.White
                        )
                    Space(26)

                    Column(Modifier.fillMaxWidth()) {
                        TextUi(
                            text = "Usuario",
                            size = 13,
                            color = Color.White)
                        TextFieldUserUi(
                            value = IdUsuario,
                            onValueChange = { IdUsuario = it }
                        )
                        Space(26)
                        TextUi(
                            text = "Contraseña",
                            size = 13,
                            color = Color.White)
                        TextFieldPassUi(
                            value = Password,
                            onValueChange = { Password = it }
                        )
                    }

                    Space(26)
                    LoginButton()
                    Space(22)
                    TextUi("versión 1.0", color = Color.White)
                }
            }

        }
    }
}