package app.aplication.sgd.ui.theme.app.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.aplication.sgd.ui.theme.theme.LowPolyBackgroundToCard
import app.aplication.sgd.R

@Composable
fun FilterButton(
    isDescending: Boolean = true,
    onClick: () -> Unit = {}
){
    Box(
        modifier = Modifier
            .width(97.dp)
            .height(38.dp)
            .clickable { onClick() }
    ) {
        //Efecto transparente
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(28.dp))
                .background(Color.Black.copy(alpha = 0.15f))
                .blur(radius = 8.dp)
        ) {
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
        // Contenido del botón
         Row (
             horizontalArrangement = Arrangement.Center,
             verticalAlignment = Alignment.CenterVertically,
             modifier = Modifier
                 .fillMaxWidth()
                 .padding(10.dp)
         )
         {
            Icon(
                imageVector = ImageVector.vectorResource(
                    if (isDescending) R.drawable.filter_icon else R.drawable.filter_icon
                ),
                contentDescription = if (isDescending) "Más reciente primero" else "Más antigua primero",
                tint = Color.Unspecified,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            TextUi(if (isDescending) "Reciente" else "Antigua", 12, color = Color.White)
        }

    }
}

@Preview
@Composable
fun FilterButtonPreview(){
    FilterButton()
}
