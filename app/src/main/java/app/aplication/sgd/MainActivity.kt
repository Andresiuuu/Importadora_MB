package app.aplication.sgd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import app.aplication.sgd.ui.theme.app.model.ApiService
import app.aplication.sgd.ui.theme.app.model.RetrofitClient
import app.aplication.sgd.ui.theme.app.pantallas.main.MainScreen

import com.example.loginapp.Login
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }
}

