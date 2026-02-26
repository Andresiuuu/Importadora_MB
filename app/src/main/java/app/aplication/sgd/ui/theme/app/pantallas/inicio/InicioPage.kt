package app.aplication.sgd.ui.theme.app.pantallas.inicio

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.aplication.sgd.R
import app.aplication.sgd.ui.theme.app.componentes.Space
import app.aplication.sgd.ui.theme.app.componentes.TextUi
import app.aplication.sgd.ui.theme.app.componentes.card.CardInfo
import app.aplication.sgd.ui.theme.app.model.Usuario
import app.aplication.sgd.ui.theme.background.LowPolyBackground

@Preview
@Composable
fun InicioPage (
    userName: String = "Usuario",
    isAdmin: Boolean = false,
    onOpenAdmin: () -> Unit = {}
){
    val infoCard1 by rememberSaveable { mutableStateOf("Clientes Registrados")}
    val infoCard2 by rememberSaveable { mutableStateOf("Deuda total")}
    val infoCard3 by rememberSaveable { mutableStateOf("Actualizaciones totales en la semana")}
        //Fondo low-poly
        LowPolyBackground()
        // Contenedor principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp, 54.dp),
            horizontalAlignment = Alignment.CenterHorizontally,


        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(R.drawable.logo_tlyly1111_artguru),
                    contentDescription = null,
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(173.dp,47.dp)

                )
                TextUi("Bienvenido",45)
                TextUi(
                    userName,
                    36,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth())

            }
            Space()
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()))
            {
                //Fila contenedora de las cards
                Row (modifier = Modifier.fillMaxWidth()
                ){
                    CardInfo(

                        icono = ImageVector.vectorResource(R.drawable.user_logo_card_icon),
                        infoCard1,
                        "386"


                    )
                    Spacer(modifier = Modifier.weight(1f))

                    CardInfo(
                        icono = ImageVector.vectorResource(R.drawable.money_icon_card),
                        infoCard2,
                        "$8.632,25"

                    )
                }
                Space(13)
                Row (modifier = Modifier.fillMaxWidth()
                ) {
                    CardInfo(
                        icono = ImageVector.vectorResource(R.drawable.arrowlogo_icon),
                        infoCard3,
                        "16"
                        )

                }
                if (isAdmin) {
                    Space(13)
                    Row(modifier = Modifier.fillMaxWidth()) {
                        CardInfo(
                            icono = ImageVector.vectorResource(R.drawable.user_round_search),
                            "Panel de Admin",
                            "Gestionar",
                            onClick = onOpenAdmin
                        )
                    }
                }

            }





        }
    }
