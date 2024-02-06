package com.startup.museumapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.startup.museumapp.ui.screens.regionScreen
import com.startup.museumapp.ui.screens.signIn
import com.startup.museumapp.ui.screens.signUp
import com.startup.museumapp.ui.screens.splashScreen
import com.startup.museumapp.ui.viewModels.MainViewModel
import com.startup.museumapp.utils.rememberKeyboardState
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel by viewModel<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "activity started!")
        setContent {
            val keyboardState = rememberKeyboardState()
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "splash") {
                composable("splash") {
                    Log.d("MainActivity", "navigating to splash")
                    splashScreen(navController, viewModel, this@MainActivity)
                }
                composable("region"){
                    regionScreen(navController = navController)
                }
                composable("signIn") {
                    Log.d("MainActivity", "navigating to signIn")
                    signIn(
                        keyboardState = keyboardState,
                        navController = navController,
                        viewModel = viewModel,
                        mainActivity = this@MainActivity
                    )
                }
                composable("signUp") {
                    Log.d("MainActivity", "navigating to signUp")
                    signUp(navController = navController, viewModel = viewModel, mainActivity = this@MainActivity)
                }
            }
        }
    }
}

