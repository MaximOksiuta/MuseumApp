package com.startup.museumapp

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.startup.museumapp.app.BluetoothRequestContract
import com.startup.museumapp.app.PermissionManager
import com.startup.museumapp.data.api.Exhibit
import com.startup.museumapp.domain.dataModels.ExhibitInfo
import com.startup.museumapp.ui.commonElements.GradientBackground
import com.startup.museumapp.ui.screens.ChatScreen
import com.startup.museumapp.ui.screens.DeviceConnectionScreen
import com.startup.museumapp.ui.screens.ExhibitDetailsScreen
import com.startup.museumapp.ui.screens.InMuseumScreen
import com.startup.museumapp.ui.screens.MapScreen
import com.startup.museumapp.ui.screens.MuseumDetailScreen
import com.startup.museumapp.ui.screens.MuseumListScreen
import com.startup.museumapp.ui.screens.PaymentMethod
import com.startup.museumapp.ui.screens.ProfileScreen
import com.startup.museumapp.ui.screens.TicketsScreen
import com.startup.museumapp.ui.theme.anonymousPro
import com.startup.museumapp.ui.viewModels.MuseumViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MuseumActivity : ComponentActivity() {

    private val viewModel by viewModel<MuseumViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val permissionManager = PermissionManager(this)
        permissionManager.requestPermissions()
        viewModel.setBluetoothRequester {
            registerForActivityResult(BluetoothRequestContract()) {
                finish()
                startActivity(intent)
            }.launch(0)
        }
        setContent {
            viewModel.updateMuseumsList()
            val notificationExhibit = remember {
                mutableStateOf<ExhibitInfo?>(null)
            }
            viewModel.showExhibitFun = {
                Log.d("MuseumActivity", it.toString())
                notificationExhibit.value = it
            }
            val visibleState = derivedStateOf {
                notificationExhibit.value != null
            }
            GradientBackground()
//            viewModel.setNotifyViewer {
//                Log.d("MuseumViewModel", it.toString())
//            }
            Column(modifier = Modifier.fillMaxSize()) {
                val navController = rememberNavController()
                var showButtonMenu by remember {
                    mutableStateOf(true)
                }
                navController.addOnDestinationChangedListener { controller, destination, arguments ->
                    if (destination.route == "chatScreen") {
                        showButtonMenu = false
                        changeResizable(false)
                    } else {
                        showButtonMenu = true
                        changeResizable(true)
                    }
                }
                NavHost(
                    navController = navController,
                    startDestination = "museumList",
                    modifier = Modifier.weight(1f)
                ) {
                    composable("museumList") {
                        MuseumListScreen(
                            modifier = Modifier.fillMaxSize(),
                            navController = navController,
                            viewModel = viewModel
                        )
                        changeResizable(false)
                    }
                    composable("deviceConnecting") {
                        DeviceConnectionScreen(
                            modifier = Modifier.fillMaxSize(),
                            viewModel = viewModel,
                            navController = navController,
                            activity = this@MuseumActivity
                        )
                    }
                    composable("inMuseumScreen") {
                        InMuseumScreen(navController)
                    }
                    composable("mapScreen") {
                        MapScreen(navController, viewModel)
                    }
                    composable("museumDetails") {
                        MuseumDetailScreen(this@MuseumActivity, viewModel, navController)
                    }
                    composable("profileScreen") {
                        ProfileScreen(navController)
                    }
                    composable("ticketScreen") {
                        TicketsScreen(navController)
                    }
                    composable("paymentScreen") {
                        PaymentMethod(navController)
                    }
                    composable("exhibitDetails") {
                        ExhibitDetailsScreen(navController)
                    }
                    composable("chatScreen") {
                        ChatScreen(navController)
                    }
                }
                if (showButtonMenu) {
                    Row(
                        modifier = Modifier
                            .background(
                                color = Color(0xFFA5A68F),
                                shape = RoundedCornerShape(
                                    topStart = 25.dp,
                                    topEnd = 25.dp,
                                    bottomStart = 0.dp,
                                    bottomEnd = 0.dp
                                )
                            )
                            .padding(vertical = 5.dp)
                            .height(70.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        val interactionSource1 = remember { MutableInteractionSource() }
                        val interactionSource2 = remember { MutableInteractionSource() }
                        val interactionSource3 = remember { MutableInteractionSource() }
                        IconWithText(
                            icon = R.drawable.ic_museums,
                            text = "Музеи",
                            modifier = Modifier.clickable(interactionSource1, null) {
                                navController.navigate("museumList")
                            })

                        IconWithText(
                            icon = R.drawable.ic_room,
                            text = "Залы",
                            modifier = Modifier.clickable(interactionSource2, null) {
                                if (viewModel.deviceIsConnected.not()) {
                                    navController.navigate("deviceConnecting")
                                } else {
                                    navController.navigate("mapScreen")
                                }
                            })

                        IconWithText(
                            icon = R.drawable.ic_user_profile,
                            text = "Профиль",
                            modifier = Modifier.clickable(interactionSource3, null) {
                                navController.navigate("profileScreen")
                            })
                    }
                }
            }
            if (visibleState.value) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth().padding(horizontal = 10.dp)
                        .offset(y = 20.dp),
                    shape = RoundedCornerShape(
                        topStart = 25.dp,
                        topEnd = 25.dp,
                        bottomStart = 25.dp,
                        bottomEnd = 25.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFA5A68F)
                    )
                ) {
                    Log.d("mamaegora", "mamaegora")
                    Text(
                        text = "Вы находитесь возле картины К.С.Малевича «Чёрный квадрат» ",
                        style = TextStyle(
                            fontSize = 17.sp,
                            lineHeight = 22.sp,
                            fontFamily = anonymousPro,
                            fontWeight = FontWeight(400),
                            color = Color(0xFFFFFFFF),
                            textAlign = TextAlign.Center,
                        ),
                        modifier = Modifier.padding(start = 50.dp, end = 50.dp, top = 20.dp, bottom = 10.dp)
                    )
                    Row(modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Прослушать",
                            style = TextStyle(
                                fontSize = 17.sp,
                                lineHeight = 22.sp,
                                fontFamily = anonymousPro,
                                fontWeight = FontWeight(400),
                                color = Color(0xFF7E7052),
                                textAlign = TextAlign.Center,
                            ),
                            modifier = Modifier.padding(end = 10.dp)
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_play_card),
                            "",
                            tint = Color(0xFF7E7052)
                        )
                    }
                }
            }
        }
    }

    private fun changeResizable(state: Boolean) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            window.setDecorFitsSystemWindows(state)
//        } else {
//            if (state.not()) {
//                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
//            } else {
//                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
//            }
//        }
        if (state.not()) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        } else {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        }
    }

    @Composable
    fun IconWithText(modifier: Modifier = Modifier, @DrawableRes icon: Int, text: String) {
        Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = text + "icon",
                tint = Color.White,
                modifier = Modifier.size(50.dp)
            )
            Text(
                text = text,
                fontSize = 14.sp,
                lineHeight = 22.sp,
                fontFamily = anonymousPro,
                fontWeight = FontWeight(400),
                color = Color(0xFFFFFFFF)
            )
        }
    }
}