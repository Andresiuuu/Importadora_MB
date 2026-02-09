package app.aplication.sgd.ui.theme.app.componentes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
fun textFieldUi(value:String,weight: Int = 380, keyboardType: KeyboardType = KeyboardType.Text ,onValueChange:(String) -> Unit){
    BasicTextField(
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = TextStyle(
            fontSize = 16.sp,
            color = Color.White
        ),
        modifier = Modifier
            .width(weight.dp)
            .height(36.dp),
        decorationBox = { innerTextField1 ->
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
                Spacer(modifier = Modifier.width(6.dp))
                //Campo de texto
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    innerTextField1()
                }

                Spacer(modifier = Modifier.width(6.dp))
            }
        }
    )
}


