package com.startup.museumapp.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.startup.museumapp.ui.commonElements.standardShadow
import com.startup.museumapp.ui.theme.anonymousPro
import com.startup.museumapp.ui.viewModels.MuseumViewModel
import kotlin.math.min

@SuppressLint("MissingPermission")
@Composable
fun DeviceConnectionScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: MuseumViewModel,
    activity: ComponentActivity
) {
    var countOfTry = 0
    var connectingWindowVisible by remember {
        mutableStateOf(false)
    }
    var connectingError by remember {
        mutableStateOf(false)
    }
    var deviceNumber by remember {
        mutableStateOf("")
    }

    viewModel.setListeners(
        onConnected = {
            navController.navigate("inMuseumScreen") {
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
            }
        },
        onDisconnected = {
            Log.d("deviceConnection", "device disconnected handle")
            if (countOfTry >= 5) {
                Log.d("deviceConnection", "reached max attempts count")
                connectingWindowVisible = true
                connectingError = true
            } else {
                countOfTry += 1
                viewModel.connectDevice(deviceNumber)
            }
            if (navController.currentDestination?.route != "deviceConnecting") {
                navController.navigate("deviceConnecting") {
                    this.popUpTo("deviceConnecting")
                }
            }
        }
    )

    ConstraintLayout(modifier = modifier) {
        val (label, textInput, nextButton) = createRefs()
        val startGuideline = createGuidelineFromStart(0.25f)
        val endGuideline = createGuidelineFromEnd(0.25f)
        Text(
            text = "Введите номер полученного девайса",
            fontSize = 17.sp,
            lineHeight = 22.sp,
            fontFamily = anonymousPro,
            fontWeight = FontWeight(800),
            color = Color(0xFF7E7052),
            textAlign = TextAlign.Center,
            modifier = Modifier.constrainAs(label) {
                start.linkTo(startGuideline)
                end.linkTo(endGuideline)
                linkTo(parent.top, parent.bottom, bias = 0.2f)
                width = Dimension.fillToConstraints
            }
        )

        TextField(
            value = deviceNumber,
            onValueChange = {
                deviceNumber = it
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            shape = CircleShape,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFA5A68F),
                unfocusedContainerColor = Color(0xFFA5A68F),
                unfocusedTextColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedTextColor = Color.White,
                cursorColor = Color.White
            ),
            modifier = Modifier
                .constrainAs(textInput) {
                    start.linkTo(startGuideline)
                    end.linkTo(endGuideline)
                    top.linkTo(label.bottom, 20.dp)
                    width = Dimension.fillToConstraints
                }.standardShadow()
//                .rsBlurShadow(
//                    radius = 20.dp,
//                    spread = 0.dp,
//                    offset = DpOffset(10.dp, 10.dp),
//                    shape = RoundedCornerShape(25.dp)
//                )
            ,
            textStyle = TextStyle(
                fontFamily = anonymousPro,
                fontSize = 32.sp,
                textAlign = TextAlign.Center
            )
        )
        Button(
            onClick = {
                countOfTry = 0
                connectingWindowVisible = true
                connectingError = false
                viewModel.connectDevice(deviceNumber)
//                      navController.navigate("inMuseumScreen")
            },
            shape = CircleShape,
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = 30.dp,
                pressedElevation = 35.dp,
                disabledElevation = 0.dp,
                hoveredElevation = 4.dp,
                focusedElevation = 4.dp
            ),
            modifier = Modifier
                .constrainAs(nextButton) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(textInput.bottom, 20.dp)
                },
            colors = ButtonColors(
                containerColor = Color(0xFF7E7052),
                contentColor = Color.White,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = Color.Transparent
            )
        ) {
            Text(
                text = "Продолжить",
                fontFamily = anonymousPro,
                fontWeight = FontWeight(400),
                fontSize = 17.sp,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }

    }
    if (connectingWindowVisible) {
        if (!connectingError) {
            Log.d("dialogManager", "showing connectingDialog")
            connectingDialog {
                connectingWindowVisible = false
            }
        } else {
            Log.d("dialogManager", "showing errorDialog")
            errorDialog {
                connectingWindowVisible = false
                connectingError = false
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun deviceConnectionScreenPreview() {
//    deviceConnectionScreen(modifier = Modifier.fillMaxSize())
//}

@Preview(showBackground = true)
@Composable
fun connectingDialog(onDismissRequest: () -> Unit = {}) {
    val config = LocalConfiguration.current
    val width = config.screenWidthDp
    val height = config.screenHeightDp
    Dialog(onDismissRequest = onDismissRequest) {
        ConstraintLayout(
            modifier = Modifier
                .size((min(width, height) - 60).dp)
                .background(
                    color = Color(0x99A5A68F),
                    shape = RoundedCornerShape(20.dp)
                )
        ) {
            val (label, progressBar, button) = createRefs()
            Text(
                text = "Подключаемся к системе музея",
                style = TextStyle(
                    fontSize = 32.sp,
                    lineHeight = 22.sp,
                    fontFamily = anonymousPro,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.constrainAs(label) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top, 20.dp)
                }
            )
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier
                    .size(100.dp)
                    .constrainAs(progressBar) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(label.bottom)
                        bottom.linkTo(button.top)
                    })
            Button(
                onClick = {
                },
                shape = CircleShape,
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = 30.dp,
                    pressedElevation = 35.dp,
                    disabledElevation = 0.dp,
                    hoveredElevation = 4.dp,
                    focusedElevation = 4.dp
                ),
                modifier = Modifier
                    .constrainAs(button) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom, 20.dp)
                    },
                colors = ButtonColors(
                    containerColor = Color(0xFF7E7052),
                    contentColor = Color.White,
                    disabledContainerColor = Color.Transparent,
                    disabledContentColor = Color.Transparent
                )
            ) {
                Text(
                    text = "Отменить",
                    fontFamily = anonymousPro,
                    fontWeight = FontWeight(400),
                    fontSize = 17.sp,
                    color = Color.White,
                    modifier = Modifier.clip(CircleShape)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun errorDialog(onDismissRequest: () -> Unit = {}) {
    val config = LocalConfiguration.current
    val width = config.screenWidthDp
    val height = config.screenHeightDp
    Dialog(onDismissRequest = onDismissRequest) {
        ConstraintLayout(
            modifier = Modifier
                .width((min(width, height) - 60).dp)
                .background(
                    color = Color(0x99A5A68F),
                    shape = RoundedCornerShape(20.dp)
                )
        ) {
            val (label, label2) = createRefs()
            Text(
                text = "Ошибка подключения",
                style = TextStyle(
                    fontSize = 32.sp,
                    lineHeight = 22.sp,
                    fontFamily = anonymousPro,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.constrainAs(label) {
                    start.linkTo(parent.start, 20.dp)
                    end.linkTo(parent.end, 20.dp)
                    top.linkTo(parent.top, 20.dp)
                    this.width = Dimension.fillToConstraints
                }
            )
            Text(
                text = "Обратитесь к администратору музея",
                style = TextStyle(
                    fontSize = 15.sp,
                    lineHeight = 22.sp,
                    fontFamily = anonymousPro,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .constrainAs(label2) {
                        start.linkTo(parent.start, 20.dp)
                        end.linkTo(parent.end, 20.dp)
                        top.linkTo(label.bottom, 20.dp)
                        bottom.linkTo(parent.bottom, 30.dp)
                        this.width = Dimension.fillToConstraints
                    })
//            Button(
//                onClick = {
//                },
//                shape = CircleShape,
//                elevation = ButtonDefaults.elevatedButtonElevation(
//                    defaultElevation = 30.dp,
//                    pressedElevation = 35.dp,
//                    disabledElevation = 0.dp,
//                    hoveredElevation = 4.dp,
//                    focusedElevation = 4.dp
//                ),
//                modifier = Modifier
//                    .constrainAs(button) {
//                        start.linkTo(parent.start)
//                        end.linkTo(parent.end)
//                        bottom.linkTo(parent.bottom, 20.dp)
//                        top.linkTo(label2.bottom, 10.dp)
//                    },
//                colors = ButtonColors(
//                    containerColor = Color(0xFF7E7052),
//                    contentColor = Color.White,
//                    disabledContainerColor = Color.Transparent,
//                    disabledContentColor = Color.Transparent
//                )
//            ) {
//                Text(
//                    text = "Отменить",
//                    fontFamily = anonymousPro,
//                    fontWeight = FontWeight(400),
//                    fontSize = 17.sp,
//                    color = Color.White,
//                    modifier = Modifier.clip(CircleShape)
//                )
//            }
        }
    }
}