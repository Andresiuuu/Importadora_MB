package app.aplication.sgd.ui.theme.app.pantallas.inicio

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import app.aplication.sgd.ui.theme.app.componentes.FilterButton
import app.aplication.sgd.ui.theme.app.componentes.Space
import app.aplication.sgd.ui.theme.app.componentes.TextUi
import app.aplication.sgd.ui.theme.app.viewModel.ClientViewModel
import app.aplication.sgd.ui.theme.background.LowPolyBackground
import app.aplication.sgd.ui.theme.app.componentes.card.CardAbonoHistorial
import app.aplication.sgd.ui.theme.app.componentes.card.CardClientesHistorial
import app.aplication.sgd.ui.theme.app.pantallas.main.MainScreen
import java.text.SimpleDateFormat
import java.util.Locale


@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialPage (
    viewModel: ClientViewModel = viewModel() //Inyección de dependencias
){
    //Fondo low-poly
    LowPolyBackground()
    val listaClientes by viewModel.clientes.collectAsStateWithLifecycle()
    val listaAbonos by viewModel.abonos.collectAsStateWithLifecycle()
    var isDescending by remember { mutableStateOf(true) }
    var paginaActual by remember { mutableIntStateOf(0) }
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    val itemsPorPagina = 20

    // Crear lista unificada de items del historial (clientes + abonos)
    data class HistorialItem(
        val nombre: String,
        val ciudad: String,
        val fecha: String,
        val monto: String,
        val esAbono: Boolean
    )

    val listaUnificada = remember(listaClientes, listaAbonos, isDescending) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val itemsClientes = listaClientes.map {
            HistorialItem(
                nombre = it.fullname ?: "Sin datos",
                ciudad = it.city ?: "Sin datos",
                fecha = (it.registrationDate ?: "").take(10),
                monto = "${it.debt}",
                esAbono = false
            )
        }
        val itemsAbonos = listaAbonos.map {
            HistorialItem(
                nombre = it.clientName ?: "Sin datos",
                ciudad = it.city ?: "Sin datos",
                fecha = (it.paymentDate ?: "").take(10),
                monto = "-${it.amount}",
                esAbono = true
            )
        }

        (itemsClientes + itemsAbonos).sortedWith { a, b ->
            try {
                val dateA = dateFormat.parse(a.fecha)
                val dateB = dateFormat.parse(b.fecha)
                if (isDescending) {
                    (dateB?.time ?: 0).compareTo(dateA?.time ?: 0)
                } else {
                    (dateA?.time ?: 0).compareTo(dateB?.time ?: 0)
                }
            } catch (e: Exception) {
                0
            }
        }
    }

    // Paginación
    val totalPaginas = if (listaUnificada.isEmpty()) 1
        else (listaUnificada.size + itemsPorPagina - 1) / itemsPorPagina
    // Resetear página si cambia el orden o los datos
    val paginaSegura = paginaActual.coerceIn(0, (totalPaginas - 1).coerceAtLeast(0))
    val itemsPaginados = listaUnificada
        .drop(paginaSegura * itemsPorPagina)
        .take(itemsPorPagina)


    // Contenedor principal
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp, 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextUi("Historial", 36, bold = true)
            Space()
            Row (
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ){
                FilterButton(
                    isDescending = isDescending,
                    onClick = {
                        isDescending = !isDescending
                        paginaActual = 0
                    }
                )
            }
            Space()
            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = {
                    paginaActual = 0
                    viewModel.refreshAll()
                },
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(585.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(itemsPaginados) { item ->
                        if (item.esAbono) {
                            CardAbonoHistorial(
                                nombre = item.nombre,
                                ciudad = item.ciudad,
                                fechaAbono = item.fecha,
                                montoAbono = item.monto
                            )
                        } else {
                            CardClientesHistorial(
                                nombre = item.nombre,
                                ciudad = item.ciudad,
                                fechaDeuda = item.fecha,
                                monto = item.monto
                            )
                        }
                    }
                    // Botones de paginación al final de la lista
                    }
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    // Botón Anterior
                    PaginationButton(
                        text = "Anterior",
                        icon = Icons.AutoMirrored.Filled.ArrowBack,
                        iconAtStart = true,
                        enabled = paginaSegura > 0,
                        onClick = { paginaActual = (paginaActual - 1).coerceAtLeast(0) }
                    )
                    // Indicador de página
                    TextUi("${paginaSegura + 1} / $totalPaginas", 14, color = Color.White)
                    // Botón Siguiente
                    PaginationButton(
                        text = "Siguiente",
                        icon = Icons.AutoMirrored.Filled.ArrowForward,
                        iconAtStart = false,
                        enabled = paginaSegura < totalPaginas - 1,
                        onClick = { paginaActual = (paginaActual + 1).coerceAtMost(totalPaginas - 1) }
                    )
                }
            }

        }

    }

}

@Composable
private fun PaginationButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconAtStart: Boolean,
    enabled: Boolean,
    onClick: () -> Unit
) {
    val alpha = if (enabled) 1f else 0.35f
    Box(
        modifier = Modifier
            .width(130.dp)
            .height(40.dp)
            .then(if (enabled) Modifier.clickable { onClick() } else Modifier)
    ) {
        // Fondo glassmorphism
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(28.dp))
                .background(Color.Black.copy(alpha = 0.15f))
                .blur(radius = 8.dp)
        )
        // Borde
        Box(
            modifier = Modifier
                .matchParentSize()
                .border(
                    1.dp,
                    Color.White.copy(alpha = 0.80f * alpha),
                    RoundedCornerShape(28.dp)
                )
        )
        // Contenido
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
        ) {
            if (iconAtStart) {
                Icon(
                    imageVector = icon,
                    contentDescription = text,
                    tint = Color.White.copy(alpha = alpha),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
            }
            TextUi(text, 12, color = Color.White.copy(alpha = alpha))
            if (!iconAtStart) {
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    imageVector = icon,
                    contentDescription = text,
                    tint = Color.White.copy(alpha = alpha),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}
