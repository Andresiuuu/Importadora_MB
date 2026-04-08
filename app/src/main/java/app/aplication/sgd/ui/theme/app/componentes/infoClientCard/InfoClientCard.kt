package app.aplication.sgd.ui.theme.app.componentes.infoClientCard

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
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.aplication.sgd.R
import app.aplication.sgd.ui.theme.app.componentes.Space
import app.aplication.sgd.ui.theme.app.componentes.TextUi
import app.aplication.sgd.ui.theme.app.model.Client
import app.aplication.sgd.ui.theme.app.model.Cliente
import app.aplication.sgd.ui.theme.theme.LowPolyBackgroundToCard
@Composable
fun InfoClientCard(
    nombre: String,
    ciudad: String,
    fechaDeuda: String,
    monto: String,
    modifier: Modifier = Modifier,
    onAñadirAbono: () -> Unit = {}
) {
//Contendor principal de la card
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(278.dp)
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
            //Muestra informacion del nombre
            TextUi(nombre, 20, color = Color.White, bold = true)
            Space(8)
            Row (
                modifier = Modifier
                    .height(40.dp),
                verticalAlignment = Alignment.Bottom
            ){
                Icon(
                    painter = painterResource(R.drawable.map_pin_icon),
                    contentDescription = null,
                    tint = Color.White,
                )
                Spacer(modifier = Modifier.width(5.dp))
                //Muestra informacion de la ciudad
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

                TextUi(fechaDeuda,13, color = Color.White)

            }
            Space()
            Box(
                modifier = Modifier
                    .width(326.dp)
                    .height(68.dp)
            ) {
                //Efecto transparente
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color.White.copy(alpha = 0.10f))
                        .blur(radius = 8.dp)
                )
                // Borde
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .border(
                            1.dp,
                            Color.White.copy(alpha = 0.80f),
                            RoundedCornerShape(14.dp)
                        )
                        .padding(10.dp, 5.dp)
                )
                {
                    Column {
                        TextUi("Deuda total",13, color = Color.White)
                        Spacer(modifier = Modifier.weight(1f))
                        //Monto de deuda
                        TextUi(monto, 20, true, Color.White)
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom,
            ) {
                //Boton para añadir abono
                TextUi("Añadir abono", 13, true, Color.White, Modifier
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ){
                        onAñadirAbono()
                    }
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF000000
) // Fondo negro para resaltar el efecto glassmorphism
@Composable
fun InfoClientCardPreview() {
    // Usamos un Box con padding para ver mejor los bordes de la card
    Box(modifier = Modifier.padding(16.dp)) {
        InfoClientCard(
            nombre = "Juan Pérez",
            ciudad = "Buenos Aires",
            fechaDeuda = "24/02/2024",
            monto = "$15.500,00"
        )
    }
}
