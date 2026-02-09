package app.aplication.sgd.ui.theme.app.componentes.infoClientCard

import android.R.id.bold
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import app.aplication.sgd.ui.theme.theme.LowPolyBackgroundToCard
@Composable
fun InfoClientCardEmpty(
    onAgregarCliente: () -> Unit = {}
){
//Contendor principal de la card
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(447.dp)
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
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(25.dp)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(R.drawable.search_card_icon),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(88.dp)
            )
            TextUi(
                " Buscar para mostrar \n cliente o, agrega a un \n cliente nuevo",
                size = 19,
                bold = true,
                color = Color.White
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom,
            ) {
                //Boton para añadir cliente
                TextUi("Agregar Cliente", 13, true, Color.White, Modifier
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ){
                        onAgregarCliente()
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun InfoClientCardEmptyPreview() {
    InfoClientCardEmpty()
}
