package app.aplication.sgd.ui.theme.app.componentes

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Space (size:Int = 20 ){
    Spacer(modifier = Modifier.height(size.dp))
}