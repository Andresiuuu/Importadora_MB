package app.aplication.sgd.ui.theme.app.pantallas.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import app.aplication.sgd.ui.theme.app.model.NavItem
import app.aplication.sgd.R
import app.aplication.sgd.ui.theme.app.pantallas.clientes.ClientesPage
import app.aplication.sgd.ui.theme.app.pantallas.inicio.HistorialPage
import app.aplication.sgd.ui.theme.app.pantallas.inicio.InicioPage

@Composable
fun MainScreen (){
    val NavItemList = listOf(
        NavItem("Inicio", icon = ImageVector.vectorResource(R.drawable.house_icon)),
        NavItem("Historial", ImageVector.vectorResource(R.drawable.clipboard_icon)),
        NavItem("Clientes", ImageVector.vectorResource(R.drawable.client_icon)),
        NavItem("Salir", ImageVector.vectorResource(R.drawable.log_out_icon))
    )
    var selectedIndex by rememberSaveable {
        mutableStateOf(0)
    }

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar{
                NavItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                        },
                        icon = {
                            Icon(imageVector = navItem.icon, contentDescription = "Icono")
                        },
                        label = {
                            Text(text= navItem.label)
                        }
                    )
                }
            }
        }
    ){
        innerPadding ->
        ContentScreen(modifier = Modifier.padding(innerPadding),selectedIndex)
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier, selectendIndex: Int){
    when(selectendIndex){
        0 -> InicioPage()
        1 -> HistorialPage()
        2 -> ClientesPage()
    }

}