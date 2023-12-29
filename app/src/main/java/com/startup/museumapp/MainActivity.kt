package com.startup.museumapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.startup.museumapp.ui.theme.provider
import com.startup.museumapp.utils.rememberKeyboardState
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val keyboardState = rememberKeyboardState()
            WindowCompat.setDecorFitsSystemWindows(window, true)
            val navController = rememberNavController()
            val authorized by remember{ mutableStateOf(false) }
            NavHost(navController = navController, startDestination = "splash"){
                composable("splash"){
                    splashScreen(navController, authorized)
                }
                composable("signIn"){
                    signIn(keyboardState = keyboardState)
                }
                composable("signUp"){

                }
            }


        }
    }

    @Composable
    fun signIn(keyboardState: State<Boolean>){
        GradientBackground()
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (labelTop, loginInput, passwordInput) = createRefs()
            Text(
                text = "Вход в аккаунт",
                fontFamily = FontFamily(
                    Font(googleFont = GoogleFont("Anonymous Pro"), fontProvider = provider)
                ),
                fontWeight = FontWeight(400),
                fontSize = 22.sp,
                color = Color(0xFF7E7052),
                modifier = Modifier.constrainAs(labelTop){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    linkTo(parent.top, parent.bottom, bias = 0.25f)
                }
            )
            var textState by remember{
                mutableStateOf("")
            }
            TextField(
                value = textState,
                onValueChange = { text: String ->
                    textState = text
                },

                modifier = Modifier
                    .constrainAs(loginInput) {
                        top.linkTo(labelTop.bottom, 30.dp)
                        start.linkTo(parent.start, 30.dp)
                        end.linkTo(parent.end, 30.dp)
                        width = Dimension.fillToConstraints
                    }
                    .shadow(
                        elevation = 20.dp,
                        spotColor = Color(0x40000000),
                        ambientColor = Color(0x40000000)
                    ),
                shape = CircleShape,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFB27F5D),
                    unfocusedContainerColor = Color(0xFFB27F5D),
                    unfocusedTextColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    cursorColor = Color.White
                ),
                textStyle = TextStyle(
                    fontFamily = FontFamily(
                        Font(googleFont = GoogleFont("Anonymous Pro"), fontProvider = provider)
                    ),
                    fontWeight = FontWeight(400),
                    fontSize = 17.sp,
                    ),
                placeholder = {
                    Text(text = "Логин",
                        fontFamily = FontFamily(
                            Font(googleFont = GoogleFont("Anonymous Pro"), fontProvider = provider)
                        ),
                        fontWeight = FontWeight(400),
                        fontSize = 17.sp,
                        color = Color.White
                    )
                }
            )
            var textState2 by remember {
                mutableStateOf("")
            }
            var passwordVisible by remember {
                mutableStateOf(false)
            }
            TextField(
                value = textState2,
                onValueChange = { text: String ->
                    textState2 = text
                },
                modifier = Modifier
                    .constrainAs(passwordInput) {
                        top.linkTo(loginInput.bottom, 10.dp)
                        start.linkTo(parent.start, 30.dp)
                        end.linkTo(parent.end, 30.dp)
                        width = Dimension.fillToConstraints
                    }
                    .padding(vertical = 10.dp)
                    .shadow(
                        elevation = 20.dp,
                        spotColor = Color(0x40000000),
                        ambientColor = Color(0x40000000)
                    ),
                shape = CircleShape,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFB27F5D),
                    unfocusedContainerColor = Color(0xFFB27F5D),
                    unfocusedTextColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    cursorColor = Color.White
                ),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                textStyle = TextStyle(
                    fontFamily = FontFamily(
                        Font(googleFont = GoogleFont("Anonymous Pro"), fontProvider = provider)
                    ),
                    fontWeight = FontWeight(400),
                    fontSize = 17.sp,
                ),
                placeholder = {
                    Text(text = "Пароль",
                        fontFamily = an,
                        fontWeight = FontWeight(400),
                        fontSize = 17.sp,
                        color = Color.White
                    )
                },
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Outlined.Visibility
                    else Icons.Outlined.VisibilityOff

                    // Localized description for accessibility services
                    val description = if (passwordVisible) "Hide password" else "Show password"

                    // Toggle button to hide or display password
                    IconButton(onClick = {passwordVisible = !passwordVisible}, modifier = Modifier.padding(horizontal = 10.dp)){
                        Icon(imageVector  = image, description, tint = Color.White)
                    }
                }
            )
            Text(text = "Создать аккаунт")
        }
    }

    @Preview
    @Composable
    fun signInPreview(){
        signIn(keyboardState = mutableStateOf(false))
    }

    @Composable
    fun splashScreen(navController: NavController, authorized: Boolean){
        LaunchedEffect(key1 = true){
            delay(2000)
            if (!authorized){
                navController.navigate("signIn"){
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
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
                text = "Музейный гид",
                fontFamily = FontFamily(
                    Font(googleFont = GoogleFont("Anonymous Pro"), fontProvider = provider)
                ),
                fontWeight = FontWeight(400),
                fontSize = 26.sp,
                color = Color(0xFF7E7052),
                modifier = Modifier.constrainAs(label){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(icon.bottom, 5.dp)
                }
            )
        }
    }

    @Composable
    fun GradientBackground(){
        val configuration = LocalConfiguration.current

        val screenHeight = configuration.screenHeightDp.dp
        val screenWidth = configuration.screenWidthDp.dp

        // A surface container using the 'background' color from the theme
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .blur(2000.dp)
        ) {
            val (topEllipse, centerEllipse, bottomEllipse) = createRefs()
            Box(
                modifier = Modifier
                    .size(width = screenWidth, height = screenWidth)
                    .scale(scaleX = 1.75f, scaleY = (screenHeight.value * 0.5f) / screenWidth.value)
                    .offset(
                        x = 0.dp,
                        y = (-(screenWidth.value - (screenHeight.value * 0.5f)) / 2f - (screenHeight.value * 0.125f)).dp
                    )
                    .background(shape = CircleShape, color = Color(0xA5A68F99))
                    .constrainAs(topEllipse) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }
            )
            Box(
                modifier = Modifier
                    .size(width = screenWidth, height = screenWidth)
                    .scale(scaleX = 1.5f, scaleY = (screenHeight.value * 0.6f) / screenWidth.value)
                    .offset(
                        x = 0.dp,
                        y = (-(screenWidth.value - (screenHeight.value * 0.6f)) / 2f).dp
                    )
                    .background(shape = CircleShape, color = Color(0xFFF4E0C8))
                    .constrainAs(centerEllipse) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            )
            Box(
                modifier = Modifier
                    .size(width = screenWidth, height = screenWidth)
                    .scale(scaleX = 1.8f, scaleY = (screenHeight.value * 0.7f) / screenWidth.value)
                    .offset(
                        x = 0.dp,
                        y = (-(screenWidth.value - (screenHeight.value * 0.7f)) / 2f + (screenHeight.value * 0.1f)).dp
                    )
                    .background(shape = CircleShape, color = Color(0xFFD39E84))
                    .constrainAs(bottomEllipse) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            )
        }
    }
}

