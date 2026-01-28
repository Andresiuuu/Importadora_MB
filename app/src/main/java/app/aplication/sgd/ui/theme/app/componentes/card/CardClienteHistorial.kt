package app.aplication.sgd.ui.theme.app.componentes.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.aplication.sgd.ui.theme.app.componentes.Space
import app.aplication.sgd.ui.theme.app.componentes.TextUi
import app.aplication.sgd.ui.theme.theme.LowPolyBackgroundToCard
@Composable
fun CardClientesHistorial( nombre: String, ciudad:String, estado:String,monto:String, modifier: Modifier = Modifier)
{
//Contendor principal de la card
    Box(
        modifier = Modifier
            .width(366.dp)
            .height(130.dp)
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
                .padding(20.dp, 15.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            TextUi(nombre, 20, color = Color.White, bold = true)
            Space(8)
            TextUi(ciudad, 16, color = Color.White)

            Space()
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom,
            ) {
                TextUi(estado, 20, true, Color.White)
                Spacer(modifier = Modifier.weight(1f))
                TextUi(monto, 20, true, Color.White)

            }

        }

    }
}