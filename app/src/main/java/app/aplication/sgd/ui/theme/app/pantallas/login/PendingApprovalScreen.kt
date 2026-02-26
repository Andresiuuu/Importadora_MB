package app.aplication.sgd.ui.theme.app.pantallas.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import app.aplication.sgd.R
import app.aplication.sgd.ui.theme.app.componentes.LoginButton
import app.aplication.sgd.ui.theme.app.componentes.Space
import app.aplication.sgd.ui.theme.app.componentes.TextUi
import app.aplication.sgd.ui.theme.background.LowPolyBackground

@Composable
fun PendingApprovalScreen(
    onRefresh: () -> Unit,
    onSignOut: () -> Unit
) {
    LowPolyBackground()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.user_round_search),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(64.dp)
        )
        Space(24)
        TextUi(
            text = "Esperando aprobación",
            size = 24,
            bold = true,
            color = Color.White
        )
        Space(12)
        TextUi(
            text = "Tu cuenta está pendiente de aprobación por un administrador. Intenta de nuevo más tarde.",
            size = 14,
            color = Color.White.copy(alpha = 0.8f)
        )
        Space(32)
        LoginButton(
            text = "Verificar estado",
            onClick = onRefresh
        )
        Space(16)
        LoginButton(
            text = "Cerrar sesión",
            onClick = onSignOut
        )
    }
}

@Composable
fun RejectedScreen(
    onSignOut: () -> Unit
) {
    LowPolyBackground()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextUi(
            text = "Acceso denegado",
            size = 24,
            bold = true,
            color = Color(0xFFFF6B6B)
        )
        Space(12)
        TextUi(
            text = "Tu solicitud de acceso ha sido rechazada. Contacta al administrador.",
            size = 14,
            color = Color.White.copy(alpha = 0.8f)
        )
        Space(32)
        LoginButton(
            text = "Cerrar sesión",
            onClick = onSignOut
        )
    }
}
