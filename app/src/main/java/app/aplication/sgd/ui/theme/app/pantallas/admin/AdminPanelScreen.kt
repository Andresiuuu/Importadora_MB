package app.aplication.sgd.ui.theme.app.pantallas.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.aplication.sgd.R
import app.aplication.sgd.ui.theme.app.componentes.Space
import app.aplication.sgd.ui.theme.app.componentes.TextUi
import app.aplication.sgd.ui.theme.app.model.UserStatusResponse
import app.aplication.sgd.ui.theme.app.viewModel.AuthViewModel
import app.aplication.sgd.ui.theme.background.LowPolyBackground
import app.aplication.sgd.ui.theme.theme.LowPolyBackgroundToCard

private val CardBg = Color.White.copy(alpha = 0.10f)
private val GreenBtn = Color(0xFF2E7D32)
private val RedBtn = Color(0xFF9C1E22)

@Composable
fun AdminPanelScreen(
    authViewModel: AuthViewModel
) {
    val pendingUsers by authViewModel.pendingUsers.collectAsStateWithLifecycle()
    val allUsers by authViewModel.allUsers.collectAsStateWithLifecycle()
    var selectedTab by rememberSaveable { mutableStateOf(0) } // 0=Pendientes, 1=Todos

    LaunchedEffect(Unit) {
        authViewModel.loadPendingUsers()
        authViewModel.loadAllUsers()
    }

    LowPolyBackground()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp, 54.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextUi("Panel de Admin", 28, bold = true)
        Space(16)

        // Tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White.copy(alpha = 0.08f)),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TabItem("Pendientes (${pendingUsers.size})", selectedTab == 0) { selectedTab = 0 }
            TabItem("Todos (${allUsers.size})", selectedTab == 1) { selectedTab = 1 }
        }
        Space(16)

        when (selectedTab) {
            0 -> {
                if (pendingUsers.isEmpty()) {
                    EmptyState("No hay usuarios pendientes")
                } else {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(pendingUsers, key = { it.id }) { user ->
                            PendingUserCard(
                                user = user,
                                onApprove = { authViewModel.approveUser(user.id) },
                                onReject = { authViewModel.rejectUser(user.id) }
                            )
                        }
                    }
                }
            }
            1 -> {
                if (allUsers.isEmpty()) {
                    EmptyState("No hay usuarios registrados")
                } else {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(allUsers, key = { it.id }) { user ->
                            AllUserCard(
                                user = user,
                                onChangeRole = { newRole -> authViewModel.changeUserRole(user.id, newRole) },
                                onDelete = { authViewModel.deleteUser(user.id) },
                                onApprove = { authViewModel.approveUser(user.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TabItem(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(40.dp)
            .clip(RoundedCornerShape(12.dp))
            .then(
                if (isSelected) Modifier.background(RedBtn)
                else Modifier
            )
            .clickable { onClick() }
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = text,
            color = if (isSelected) Color.White else Color.White.copy(alpha = 0.6f),
            fontSize = 13.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
private fun EmptyState(message: String) {
    Column(
        modifier = Modifier.fillMaxSize().padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.user_round_search),
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.4f),
            modifier = Modifier.size(48.dp)
        )
        Space(12)
        TextUi(message, 14, color = Color.White.copy(alpha = 0.4f))
    }
}

@Composable
fun PendingUserCard(
    user: UserStatusResponse,
    onApprove: () -> Unit,
    onReject: () -> Unit
) {
    GlassCard {
        TextUi(user.nombre, 16, bold = true, color = Color.White)
        Space(4)
        TextUi(user.email, 13, color = Color.White.copy(alpha = 0.7f))
        Space(12)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onApprove,
                colors = ButtonDefaults.buttonColors(containerColor = GreenBtn),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f)
            ) { Text("Aprobar", color = Color.White) }
            Button(
                onClick = onReject,
                colors = ButtonDefaults.buttonColors(containerColor = RedBtn),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f)
            ) { Text("Rechazar", color = Color.White) }
        }
    }
}

@Composable
fun AllUserCard(
    user: UserStatusResponse,
    onChangeRole: (String) -> Unit,
    onDelete: () -> Unit,
    onApprove: () -> Unit
) {
    val statusColor = when (user.status) {
        "APPROVED" -> Color(0xFF4CAF50)
        "REJECTED" -> Color(0xFFFF5252)
        else -> Color(0xFFFFA726)
    }
    val roleLabel = if (user.role == "ADMIN") "ADMIN" else "USER"

    GlassCard {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                TextUi(user.nombre, 15, bold = true, color = Color.White)
                Space(2)
                TextUi(user.email, 12, color = Color.White.copy(alpha = 0.6f))
            }
            // Status badge
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(statusColor.copy(alpha = 0.2f))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(user.status, color = statusColor, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }
        Space(4)
        Text("Rol: $roleLabel", color = Color.White.copy(alpha = 0.5f), fontSize = 12.sp)
        Space(10)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Toggle role
            val newRole = if (user.role == "ADMIN") "USER" else "ADMIN"
            Button(
                onClick = { onChangeRole(newRole) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f)
            ) { Text(if (user.role == "ADMIN") "→ User" else "→ Admin", color = Color.White, fontSize = 12.sp) }

            // Approve if pending
            if (user.status == "PENDING") {
                Button(
                    onClick = onApprove,
                    colors = ButtonDefaults.buttonColors(containerColor = GreenBtn),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                ) { Text("Aprobar", color = Color.White, fontSize = 12.sp) }
            }

            // Delete
            Button(
                onClick = onDelete,
                colors = ButtonDefaults.buttonColors(containerColor = RedBtn),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f)
            ) { Text("Eliminar", color = Color.White, fontSize = 12.sp) }
        }
    }
}

@Composable
private fun GlassCard(content: @Composable () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(12.dp))
                .background(CardBg)
                .blur(radius = 8.dp)
        ) { LowPolyBackgroundToCard() }
        Box(
            modifier = Modifier
                .matchParentSize()
                .border(1.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
        )
        Column(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }
}
