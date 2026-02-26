package app.aplication.sgd.ui.theme.app.pantallas.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.aplication.sgd.R
import app.aplication.sgd.ui.theme.app.model.NavItem
import app.aplication.sgd.ui.theme.app.pantallas.admin.AdminPanelScreen
import app.aplication.sgd.ui.theme.app.pantallas.clientes.ClientesPage
import app.aplication.sgd.ui.theme.app.pantallas.inicio.HistorialPage
import app.aplication.sgd.ui.theme.app.pantallas.inicio.InicioPage
import app.aplication.sgd.ui.theme.app.viewModel.AuthViewModel

private val NavBarBg = Color(0xFF111118)
private val ActiveRed = Color(0xFF9C1E22)
private val InactiveGray = Color(0xFF7E7E86)

@Composable
fun MainScreen(
    userName: String = "Usuario",
    isAdmin: Boolean = false,
    authViewModel: AuthViewModel? = null,
    onSignOut: () -> Unit = {}
) {
    // Fixed nav: Inicio, Historial, Clientes, Salir  (Admin is inside Inicio or a separate page)
    val navItems = listOf(
        NavItem("Inicio", icon = ImageVector.vectorResource(R.drawable.house_icon)),
        NavItem("Historial", ImageVector.vectorResource(R.drawable.clipboard_icon)),
        NavItem("Clientes", ImageVector.vectorResource(R.drawable.client_icon)),
        NavItem("Salir", ImageVector.vectorResource(R.drawable.log_out_icon))
    )

    var selectedIndex by rememberSaveable { mutableStateOf(0) }
    // Admin panel is accessed via a 4th content index (hidden from nav)
    val showAdmin = selectedIndex == 4

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Column {
                // Top divider line
                HorizontalDivider(thickness = 0.5.dp, color = Color.White.copy(alpha = 0.12f))
                // Custom bottom bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(NavBarBg)
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    navItems.forEachIndexed { index, navItem ->
                        val isSalir = index == navItems.lastIndex
                        val isSelected = selectedIndex == index && !isSalir

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) {
                                    if (isSalir) onSignOut()
                                    else selectedIndex = index
                                }
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(40.dp)
                                    .then(
                                        if (isSelected) Modifier
                                            .clip(CircleShape)
                                            .background(ActiveRed)
                                        else Modifier
                                    )
                            ) {
                                Icon(
                                    imageVector = navItem.icon,
                                    contentDescription = navItem.label,
                                    tint = if (isSelected) Color.White else InactiveGray,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Text(
                                text = navItem.label,
                                fontSize = 10.sp,
                                fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
                                color = if (isSelected) Color.White else InactiveGray
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when {
                showAdmin && isAdmin && authViewModel != null -> AdminPanelScreen(authViewModel = authViewModel)
                selectedIndex == 0 -> InicioPage(
                    userName = userName,
                    isAdmin = isAdmin,
                    onOpenAdmin = { selectedIndex = 4 }
                )
                selectedIndex == 1 -> HistorialPage()
                selectedIndex == 2 -> ClientesPage()
            }
        }
    }
}
