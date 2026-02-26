package app.aplication.sgd.ui.theme.app.componentes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoginButton(
    text: String = "Iniciar Sesión",
    onClick: () -> Unit = {},
    enabled: Boolean = true
){
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(35.dp),
        border = BorderStroke(1.dp, Color.Black),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF9C1E22)
        ),
        enabled = enabled,
        onClick = onClick
    ){
        TextUi(text, 13, true, Color.White)
    }
}
