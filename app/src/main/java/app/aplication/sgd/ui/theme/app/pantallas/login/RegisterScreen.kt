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
import androidx.compose.ui.unit.dp
import app.aplication.sgd.R
import app.aplication.sgd.ui.theme.app.componentes.LoginButton
import app.aplication.sgd.ui.theme.app.componentes.Space
import app.aplication.sgd.ui.theme.app.componentes.TextFieldPassUi
import app.aplication.sgd.ui.theme.app.componentes.TextUi
import app.aplication.sgd.ui.theme.background.LowPolyBackground
import app.aplication.sgd.ui.theme.theme.LowPolyBackgroundToCard

@Composable
fun RegisterScreen(
    onSignUp: (email: String, password: String, nombre: String) -> Unit,
    onNavigateToLogin: () -> Unit,
    isLoading: Boolean = false,
    errorMessage: String? = null
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LowPolyBackground()

        var nombre by rememberSaveable { mutableStateOf("") }
        var email by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }

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
                    .size(236.dp, 70.dp)
            )
            Space(30)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(440.dp)
            ) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clip(RoundedCornerShape(9.dp))
                        .background(Color.White.copy(alpha = 0.10f))
                        .blur(radius = 8.dp)
                ) {
                    LowPolyBackgroundToCard()
                }

                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .border(
                            1.dp,
                            Color.White.copy(alpha = 0.80f),
                            RoundedCornerShape(9.dp)
                        )
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(33.dp, 24.dp)
                ) {
                    TextUi(
                        text = "Crear cuenta",
                        size = 16,
                        color = Color.White
                    )
                    Space(20)

                    Column(Modifier.fillMaxWidth()) {
                        TextUi(text = "Nombre completo", size = 13, color = Color.White)
                        TextFieldUserUi(
                            value = nombre,
                            onValueChange = { nombre = it }
                        )
                        Space(16)
                        TextUi(text = "Correo", size = 13, color = Color.White)
                        TextFieldUserUi(
                            value = email,
                            onValueChange = { email = it }
                        )
                        Space(16)
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

                    Space(20)

                    if (isLoading) {
                        CircularProgressIndicator(color = Color.White)
                    } else {
                        LoginButton(
                            text = "Registrarse",
                            onClick = { onSignUp(email, password, nombre) },
                            enabled = nombre.isNotBlank() && email.isNotBlank() && password.length >= 6
                        )
                    }

                    Space(12)
                    TextUi(
                        text = "¿Ya tienes cuenta? Inicia sesión",
                        size = 13,
                        color = Color.White,
                        modifier = Modifier.clickable { onNavigateToLogin() }
                    )
                }
            }
        }
    }
}
