package ir.developer.todo_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.developer.todo_compose.navigation.SetupNavigation
import ir.developer.todo_compose.ui.theme.ToDOcomposeTheme
import ir.developer.todo_compose.ui.theme.viewmodel.SharedViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        installSplashScreen()

        setContent {
            ToDOcomposeTheme {

                navController = rememberNavController()
                SetupNavigation(
                    navController = navController,
                    sharedViewModel = sharedViewModel
                )

            }
        }
    }
}
