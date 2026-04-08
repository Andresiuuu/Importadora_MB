package app.aplication.sgd.ui.theme.app.componentes.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.aplication.sgd.ui.theme.app.componentes.Space
import app.aplication.sgd.ui.theme.app.componentes.TextUi
import app.aplication.sgd.ui.theme.theme.LowPolyBackgroundToCard

private val AbonoGreen = Color(0xFF2E7D32)

@Composable
fun CardAbonoHistorial(
    nombre: String,
    ciudad: String,
    fechaAbono: String,
    montoAbono: String,
    modifier: Modifier = Modifier
) {
    //Contenedor principal de la card
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(135.dp)
    ) {
        //Efecto transparente
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(28.dp))
                .background(Color.White.copy(alpha = 0.10f))
                .blur(radius = 8.dp)
        ) {
            LowPolyBackgroundToCard()
        }
        // Borde verde para diferenciar de las cards de clientes
        Box(
            modifier = Modifier
                .matchParentSize()
                .border(
                    1.5.dp,
                    Color.White.copy(alpha = 0.90f),
                    RoundedCornerShape(28.dp)
                )
        )
        // Contenido de la Card
        Column(
            modifier = Modifier
                .padding(20.dp, 15.dp)
        ) {
            // Etiqueta "Abono" para diferenciar
            TextUi("Abono", 12, color = AbonoGreen, bold = true)
            Space(8)
            TextUi(nombre, 20, color = Color.White, bold = true)
            Space(8)
            TextUi(ciudad, 16, color = Color.White)
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom,
            ) {
                TextUi(fechaAbono, 20, true, Color.White)
                Spacer(modifier = Modifier.weight(1f))
                TextUi(montoAbono, 20, true, AbonoGreen)
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun CardAbonoHistorialPreview() {
    Box(modifier = Modifier.padding(16.dp)) {
        CardAbonoHistorial(
            nombre = "Juan Pérez",
            ciudad = "Buenos Aires",
            fechaAbono = "27/02/2026",
            montoAbono = "-$500.00"
        )
    }
}
