package app.aplication.sgd.ui.theme.app.componentes

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


//Size 10
@Composable
fun TextUi(text:String, size: Int = 10, bold:Boolean = false, color: Color = Color.Black, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontSize = size.sp,
        fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal,
        color = color,
        modifier = modifier
    )
}
