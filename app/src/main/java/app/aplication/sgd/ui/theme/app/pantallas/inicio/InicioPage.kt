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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import app.aplication.sgd.R
import app.aplication.sgd.ui.theme.app.componentes.Space
import app.aplication.sgd.ui.theme.app.componentes.TextUi
import app.aplication.sgd.ui.theme.app.componentes.card.CardInfo
import app.aplication.sgd.ui.theme.app.model.Usuario
import app.aplication.sgd.ui.theme.app.viewModel.ClientViewModel
import app.aplication.sgd.ui.theme.background.LowPolyBackground
import androidx.compose.runtime.collectAsState

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioPage (
    userName: String = "Usuario",
    isAdmin: Boolean = false,
    onOpenAdmin: () -> Unit = {},
    viewModel: ClientViewModel = viewModel()
){
    val recientes by viewModel.actualizacionesRecientes.collectAsStateWithLifecycle()
    val totalDeuda by viewModel.totalDeudaGlobal.collectAsStateWithLifecycle()
    val totalFormateado = java.text.NumberFormat.getCurrencyInstance().format(totalDeuda)
    val infoCard1 by rememberSaveable { mutableStateOf("Clientes Registrados")}
    val infoCard2 by rememberSaveable { mutableStateOf("Deuda total")}
    val infoCard3 by rememberSaveable { mutableStateOf("Actualizaciones totales en los ultimos 5 dias")}
    var isRefreshing by rememberSaveable { mutableStateOf(false) }
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
                TextUi("Bienvenido",45)
                TextUi(
                    userName,
                    36,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth())

            }
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
                            numero = viewModel.clientes.collectAsState().value.size.toString()


                        )
                        Spacer(modifier = Modifier.weight(1f))

                        CardInfo(
                            icono = ImageVector.vectorResource(R.drawable.money_icon_card),
                            infoCard2,
                            totalFormateado

                        )
                    }
                    Space(13)
                    Row (modifier = Modifier.fillMaxWidth()
                    ) {
                        CardInfo(
                            icono = ImageVector.vectorResource(R.drawable.arrowlogo_icon),
                            infoCard3,
                            recientes
                            )

                        if (isAdmin) {
                            Spacer(modifier = Modifier.weight(1f))
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

