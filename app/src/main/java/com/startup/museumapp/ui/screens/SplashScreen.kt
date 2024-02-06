package com.startup.museumapp.ui.screens

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.startup.museumapp.MainActivity
import com.startup.museumapp.MuseumActivity
import com.startup.museumapp.R
import com.startup.museumapp.ui.commonElements.GradientBackground
import com.startup.museumapp.ui.theme.Brown
import com.startup.museumapp.ui.theme.anonymousPro
import com.startup.museumapp.ui.viewModels.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun splashScreen(navController: NavController, viewModel: MainViewModel, mainActivity: MainActivity){
    LaunchedEffect(key1 = true){
        delay(2000)
        if (!viewModel.getUserAuthenticated()){
            navController.navigate("region"){
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
            }
        } else{
            with(mainActivity){
                startActivity(Intent(this, MuseumActivity::class.java))
            }
        }
    }
    GradientBackground()
    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Transparent)) {
        val (icon, label) = createRefs()
        val startGuideline = createGuidelineFromStart(0.2f)
        val endGuideline = createGuidelineFromEnd(0.2f)
        Image(
            painter = painterResource(id = R.drawable.iconforsplash),
            contentDescription = "Splash Icon",
            modifier = Modifier.constrainAs(icon){
                start.linkTo(startGuideline)
                end.linkTo(endGuideline)
                linkTo(top = parent.top, bottom = parent.bottom, bias = 0.4f)
            })
        Text(
            text = "Исследователь Исскуства",
            fontFamily = anonymousPro,
            fontWeight = FontWeight(400),
            fontSize = 26.sp,
            color = Brown,
            modifier = Modifier.constrainAs(label){
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(icon.bottom, 5.dp)
            }
        )
    }
}