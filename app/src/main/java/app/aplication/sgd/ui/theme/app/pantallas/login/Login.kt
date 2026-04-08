package app.aplication.sgd.ui.theme.app.pantallas.login

import TextFieldUserUi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.aplication.sgd.R
import app.aplication.sgd.ui.theme.app.componentes.LoginButton
import app.aplication.sgd.ui.theme.app.componentes.Space
import app.aplication.sgd.ui.theme.app.componentes.TextFieldPassUi
import app.aplication.sgd.ui.theme.app.componentes.TextUi
import app.aplication.sgd.ui.theme.background.LowPolyBackground
import app.aplication.sgd.ui.theme.theme.LowPolyBackgroundToCard
@Composable
fun LoginScreen(
    onSignIn: (email: String, password: String) -> Unit,
    onNavigateToRegister: () -> Unit,
    isLoading: Boolean = false,
    errorMessage: String? = null
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LowPolyBackground()

        var email by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(50.dp, 0.dp)
        ) {
            Space(30)
            Image(
                painter = painterResource(R.drawable.logo_removebg_preview),
                contentDescription = null,
                alignment = Alignment.CenterStart,
                modifier = Modifier
                    .size(250.dp)

            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(380.dp)
            ) {
                // Efecto transparente
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clip(RoundedCornerShape(9.dp))
                        .background(Color.White.copy(alpha = 0.10f))
                        .blur(radius = 8.dp)
                ) {
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
                        text = "Inicie sesión para continuar",
                        size = 16,
                        color = Color.White
                    )
                    Space(26)

                    Column(Modifier.fillMaxWidth()) {
                        TextUi(text = "Correo", size = 13, color = Color.White)
                        TextFieldUserUi(
                            value = email,
                            onValueChange = { email = it }
                        )
                        Space(26)
                        TextUi(text = "Contraseña", size = 13, color = Color.White)
                        TextFieldPassUi(
                            value = password,
                            onValueChange = { password = it }
                        )
                    }

                    if (errorMessage != null) {
                        Space(8)
                        TextUi(text = errorMessage, size = 12, color = Color(0xFFFF6B6B))
                    }

                    Space(26)

                    if (isLoading) {
                        CircularProgressIndicator(color = Color.White)
                    } else {
                        LoginButton(
                            text = "Iniciar Sesión",
                            onClick = { onSignIn(email, password) },
                            enabled = email.isNotBlank() && password.isNotBlank()
                        )
                    }

                    Space(16)
                    TextUi(
                        text = "\u00bfNo tienes cuenta? Reg\u00edstrate",
                        size = 13,
                        color = Color.White,
                        modifier = Modifier.clickable { onNavigateToRegister() }
                    )
                    Space(12)
                    TextUi("versión 1.0", color = Color.White)
                }
            }
        }
    }
}
