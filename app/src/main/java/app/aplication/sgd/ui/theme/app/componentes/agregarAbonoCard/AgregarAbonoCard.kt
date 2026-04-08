package app.aplication.sgd.ui.theme.app.componentes.agregarAbonoCard


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.aplication.sgd.R
import app.aplication.sgd.ui.theme.app.componentes.Space
import app.aplication.sgd.ui.theme.app.componentes.TextUi
import app.aplication.sgd.ui.theme.theme.LowPolyBackgroundToCard
@Composable
fun AgregarAbonoCard(
    nombre: String,
    ciudad: String,
    fechaDeuda: String,
    monto: String,
    abono: String,
    modifier: Modifier = Modifier,
    onvalueChange: (String) -> Unit,
    onAbonar: () -> Unit = {},
    onCancelar: () -> Unit = {}
) {
    // Estado para controlar la visibilidad del diálogo de confirmación
    var showDialog by remember { androidx.compose.runtime.mutableStateOf(false) }

    // Diálogo de Confirmación
    if (showDialog) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                TextUi("Confirmar Abono", 18, bold = true)
            },
            text = {
                Column {
                    TextUi("¿Estás seguro de que deseas registrar este abono?", 14)
                    Space(8)
                    TextUi("Cliente: $nombre", 14, bold = true)
                    TextUi("Cantidad: $$abono", 16, bold = true, color = Color(0xFF4CAF50))
                }
            },
            confirmButton = {
                androidx.compose.material3.TextButton(
                    onClick = {
                        showDialog = false
                        onAbonar() // Ejecuta la función real de guardado
                    }
                ) {
                    TextUi("Confirmar", 14, bold = true)
                }
            },
            dismissButton = {
                androidx.compose.material3.TextButton(
                    onClick = { showDialog = false }
                ) {
                    TextUi("Cancelar", 14)
                }
            }
        )
    }

    // Contenedor principal de la card
    Box(
        modifier = modifier // Usamos el modifier pasado por parámetro
            .fillMaxWidth()
            .height(344.dp)
    ) {
        // Efecto transparente
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(28.dp))
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
                    RoundedCornerShape(28.dp)
                )
        )
        // Contenido de la Card
        Column(
            modifier = Modifier
                .padding(20.dp, 30.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            // Muestra información del nombre
            TextUi(nombre, 20, color = Color.White, bold = true)
            Space(8)
            Row(
                modifier = Modifier
                    .height(40.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Icon(
                    painter = painterResource(R.drawable.map_pin_icon),
                    contentDescription = null,
                    tint = Color.White,
                )
                Spacer(modifier = Modifier.width(5.dp))
                // Muestra información de la ciudad
                TextUi(ciudad, 13, color = Color.White)
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(R.drawable.line),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.fillMaxHeight()
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(R.drawable.calendars_icon),
                    contentDescription = null,
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(5.dp))
                TextUi(fechaDeuda, 13, color = Color.White)
            }
            Space()
            // Caja de Deuda Total
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(68.dp)
            ) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color.White.copy(alpha = 0.10f))
                        .blur(radius = 8.dp)
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .border(
                            1.dp,
                            Color.White.copy(alpha = 0.80f),
                            RoundedCornerShape(14.dp)
                        )
                        .padding(10.dp, 5.dp)
                ) {
                    Column {
                        TextUi("Deuda total", 13, color = Color.White)
                        Spacer(modifier = Modifier.weight(1f))
                        TextUi(monto, 20, true, Color.White)
                    }
                }
            }
            // Entrada para descontar deuda (Abono)
            Space()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(68.dp)
            ) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color.White.copy(alpha = 0.10f))
                        .blur(radius = 8.dp)
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .border(
                            1.dp,
                            Color.White.copy(alpha = 0.80f),
                            RoundedCornerShape(14.dp)
                        )
                        .padding(10.dp, 5.dp)
                ) {
                    Column {
                        TextUi("Ingrese cantidad de abono", 13, color = Color.White)
                        Spacer(modifier = Modifier.height(20.dp))
                        BasicTextField(
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            value = abono,
                            onValueChange = onvalueChange,
                            singleLine = true,
                            textStyle = TextStyle(
                                fontSize = 16.sp,
                                color = Color.White
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp)
                        )
                    }
                }
            }
            // Botones de acción
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
            ) {
                // Botón para cancelar
                TextUi("Cancelar", 13, true, Color.White, Modifier
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        onCancelar()
                    }
                )
                // Botón para añadir abono (Activa el diálogo)
                TextUi("Añadir abono", 13, true, Color.White, Modifier
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        // Solo mostramos el diálogo si hay algo escrito en abono
                        if (abono.isNotBlank()) {
                            showDialog = true
                        }
                    }
                )
            }
        }
    }
}
