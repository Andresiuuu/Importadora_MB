package app.aplication.sgd.ui.theme.app.componentes


import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.aplication.sgd.R

@Composable
fun TextFieldPassUi(value:String,onValueChange:(String) -> Unit){
    var show by rememberSaveable { mutableStateOf(false) }
    BasicTextField(
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        value = value,
        onValueChange = onValueChange,
        visualTransformation =  if(show) VisualTransformation.None else
            PasswordVisualTransformation(),
        singleLine = true,
        textStyle = TextStyle(
            fontSize = 16.sp,
            color = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp),
        decorationBox = { innerTextField ->
            //Diseño
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        width = 1.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(9.dp)
                    )
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                //Icono inicial
                Icon(
                    painter = painterResource(id = R.drawable.lock_icon),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )

                Spacer(modifier = Modifier.width(6.dp))

                //Campo de texto
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    innerTextField()
                }

                Spacer(modifier = Modifier.width(6.dp))

                //Icono final
                Icon(
                    painter = painterResource(if (show) R.drawable.eye_icon else R.drawable.eye_off_icon) ,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                        .clickable {
                            show = !show
                    }
                )

            }
        }
    )

}