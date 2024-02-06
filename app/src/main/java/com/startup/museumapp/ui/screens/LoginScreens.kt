package com.startup.museumapp.ui.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.startup.museumapp.MainActivity
import com.startup.museumapp.MuseumActivity
import com.startup.museumapp.R
import com.startup.museumapp.ui.commonElements.GradientBackground
import com.startup.museumapp.ui.commonElements.getCardDefaultElevation
import com.startup.museumapp.ui.commonElements.standardShadow
import com.startup.museumapp.ui.theme.Brown
import com.startup.museumapp.ui.theme.anonymousPro
import com.startup.museumapp.ui.viewModels.MainViewModel

@Composable
fun regionScreen(navController: NavController) {
    GradientBackground()
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (languageSelector, citySelector, nextButton) = createRefs()
        createVerticalChain(
            languageSelector,
            citySelector,
            nextButton,
            chainStyle = ChainStyle.Packed
        )
        Card(modifier = Modifier
            .constrainAs(languageSelector) {
                centerHorizontallyTo(parent)
                width = Dimension.fillToConstraints
            }
            .padding(horizontal = 30.dp, vertical = 10.dp)
//            .standardShadow()
//            .rsBlurShadow(
//                radius = 20.dp,
//                spread = 0.dp,
//                offset = DpOffset(10.dp, 10.dp),
//                shape = RoundedCornerShape(25.dp)
//            )
            ,
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF7E7052)
            ),
            shape = RoundedCornerShape(15.dp),
            elevation = getCardDefaultElevation()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_language),
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.padding(10.dp)
                )
                Text(
                    text = "Язык",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        fontFamily = anonymousPro,
                        fontWeight = FontWeight(400),
                        color = Color(0x99FFFFFF),
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Русский",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        fontFamily = anonymousPro,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFFFFFFF)
                    )
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow),
                    contentDescription = "next arrow",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(15.dp)
                        .size(15.dp),
                )
            }

        }
        Card(modifier = Modifier
            .constrainAs(citySelector) {
                centerHorizontallyTo(parent)
                width = Dimension.fillToConstraints
            }
            .padding(horizontal = 30.dp, vertical = 10.dp)
//            .standardShadow()
//            .rsBlurShadow(
//                radius = 20.dp,
//                spread = 0.dp,
//                offset = DpOffset(10.dp, 10.dp),
//                shape = RoundedCornerShape(25.dp)
//            )
            ,
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF7E7052)
            ),
            shape = RoundedCornerShape(15.dp),
            elevation = getCardDefaultElevation()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_city_selection),
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.padding(10.dp)
                )
                Text(
                    text = "Город",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        fontFamily = anonymousPro,
                        fontWeight = FontWeight(400),
                        color = Color(0x99FFFFFF),
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Москва",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        fontFamily = anonymousPro,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFFFFFFF)
                    )
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow),
                    contentDescription = "next arrow",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(15.dp)
                        .size(15.dp),
                )
            }
        }
        Button(
            onClick = {
                navController.navigate("signIn")
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
                    centerHorizontallyTo(parent)
                }
                .padding(vertical = 10.dp),
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
}


@Composable
fun signIn(
    keyboardState: State<Boolean>,
    navController: NavController,
    viewModel: MainViewModel,
    mainActivity: MainActivity
) {
    GradientBackground()
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (labelTop, loginInput, passwordInput, createAccountLabel, nextButton) = createRefs()
        Text(
            text = "Вход в аккаунт",
            fontFamily = anonymousPro,
            fontWeight = FontWeight(400),
            fontSize = 22.sp,
            color = Brown,
            modifier = Modifier.constrainAs(labelTop) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                linkTo(parent.top, parent.bottom, bias = 0.25f)
            }
        )
        var textState by remember {
            mutableStateOf("")
        }
        TextField(
            value = textState,
            onValueChange = { text: String ->
                textState = text
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            ),
            modifier = Modifier
                .constrainAs(loginInput) {
                    top.linkTo(labelTop.bottom, 30.dp)
                    start.linkTo(parent.start, 30.dp)
                    end.linkTo(parent.end, 30.dp)
                    width = Dimension.fillToConstraints
                }
                .standardShadow()
//                .rsBlurShadow(
//                    radius = 20.dp,
//                    spread = 0.dp,
//                    offset = DpOffset(10.dp, 10.dp),
//                    shape = RoundedCornerShape(25.dp)
//                )
            ,
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
                fontFamily = anonymousPro,
                fontWeight = FontWeight(400),
                fontSize = 17.sp,
            ),
            placeholder = {
                Text(
                    text = "Логин",
                    fontFamily = anonymousPro,
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
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            modifier = Modifier
                .constrainAs(passwordInput) {
                    top.linkTo(loginInput.bottom, 10.dp)
                    start.linkTo(parent.start, 30.dp)
                    end.linkTo(parent.end, 30.dp)
                    width = Dimension.fillToConstraints
                }
                .padding(vertical = 10.dp)
                .standardShadow()
//                .rsBlurShadow(
//                    radius = 20.dp,
//                    spread = 0.dp,
//                    offset = DpOffset(10.dp, 10.dp),
//                    shape = RoundedCornerShape(25.dp)
//                )
            ,
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
                fontFamily = anonymousPro,
                fontWeight = FontWeight(400),
                fontSize = 17.sp,
            ),
            placeholder = {
                Text(
                    text = "Пароль",
                    fontFamily = anonymousPro,
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
                IconButton(
                    onClick = { passwordVisible = !passwordVisible },
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    Icon(imageVector = image, description, tint = Color.White)
                }
            }
        )
        Text(
            text = "Создать аккаунт",
            fontSize = 14.sp,
            color = Brown,
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight(400),
            modifier = Modifier
                .constrainAs(createAccountLabel) {
                    end.linkTo(passwordInput.end, 10.dp)
                    top.linkTo(passwordInput.bottom)
                }
                .clickable {
                    navController.navigate("signUp")
                })
        Button(
            onClick = {
                with(mainActivity) {
                    startActivity(Intent(this, MuseumActivity::class.java))
                }
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
                    top.linkTo(createAccountLabel.bottom, 20.dp)
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
}


@Composable
fun signUp(navController: NavController, viewModel: MainViewModel, mainActivity: MainActivity) {
    GradientBackground()
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (createAccountLabel, nameInput, phoneInput, mailInput, passwordInput, signInLabel, nextButton) = createRefs()
        var nameState by remember {
            mutableStateOf("")
        }
        var phoneState by remember {
            mutableStateOf("")
        }
        var mailState by remember {
            mutableStateOf("")
        }
        var passwordState by remember {
            mutableStateOf("")
        }
        Text(text = "Создать аккаунт",
            fontFamily = anonymousPro,
            fontWeight = FontWeight(400),
            fontSize = 22.sp,
            color = Brown,
            modifier = Modifier.constrainAs(createAccountLabel) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                linkTo(parent.top, parent.bottom, bias = 0.15f)
            }
        )

        // nameTextField
        TextField(
            value = nameState,
            onValueChange = { text: String ->
                nameState = text
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
            modifier = Modifier
                .constrainAs(nameInput) {
                    top.linkTo(createAccountLabel.bottom, 30.dp)
                    start.linkTo(parent.start, 30.dp)
                    end.linkTo(parent.end, 30.dp)
                    width = Dimension.fillToConstraints
                }
                .standardShadow()
//                .rsBlurShadow(
//                    radius = 20.dp,
//                    spread = 0.dp,
//                    offset = DpOffset(10.dp, 10.dp),
//                    shape = RoundedCornerShape(25.dp)
//                )
            ,
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
            textStyle = TextStyle(
                fontFamily = anonymousPro,
                fontWeight = FontWeight(400),
                fontSize = 17.sp,
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = "profile icon",
                    tint = Color.White,
                    modifier = Modifier.padding(start = 15.dp)
                )
            },
            label = {
                Text(
                    text = "Имя", fontSize = 14.sp,
                    lineHeight = 22.sp,
                    fontFamily = anonymousPro,
                    fontWeight = FontWeight(400),
                    color = Color(0xB2FFFFFF)
                )
            }
        )

        // PhoneTextField
        TextField(
            value = phoneState,
            onValueChange = { text: String ->
                phoneState = text
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Phone
            ),
            modifier = Modifier
                .constrainAs(phoneInput) {
                    top.linkTo(nameInput.bottom, 20.dp)
                    start.linkTo(parent.start, 30.dp)
                    end.linkTo(parent.end, 30.dp)
                    width = Dimension.fillToConstraints
                }
                .standardShadow()
//                .rsBlurShadow(
//                    radius = 20.dp,
//                    spread = 0.dp,
//                    offset = DpOffset(10.dp, 10.dp),
//                    shape = RoundedCornerShape(25.dp)
//                )
            ,
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
            textStyle = TextStyle(
                fontFamily = anonymousPro,
                fontWeight = FontWeight(400),
                fontSize = 17.sp,
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_phone_call),
                    contentDescription = "phone icon",
                    tint = Color.White,
                    modifier = Modifier.padding(start = 15.dp)
                )
            },
            label = {
                Text(
                    text = "Телефон", fontSize = 14.sp,
                    lineHeight = 22.sp,
                    fontFamily = anonymousPro,
                    fontWeight = FontWeight(400),
                    color = Color(0xB2FFFFFF)
                )
            }
        )

        // mailTextField
        TextField(
            value = mailState,
            onValueChange = { text: String ->
                mailState = text
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            ),
            modifier = Modifier
                .constrainAs(mailInput) {
                    top.linkTo(phoneInput.bottom, 20.dp)
                    start.linkTo(parent.start, 30.dp)
                    end.linkTo(parent.end, 30.dp)
                    width = Dimension.fillToConstraints
                }
                .standardShadow()
//                .rsBlurShadow(
//                    radius = 20.dp,
//                    spread = 0.dp,
//                    offset = DpOffset(10.dp, 10.dp),
//                    shape = RoundedCornerShape(25.dp)
//                )
            ,
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
            textStyle = TextStyle(
                fontFamily = anonymousPro,
                fontWeight = FontWeight(400),
                fontSize = 17.sp,
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_user_mail),
                    contentDescription = "mail icon",
                    tint = Color.White,
                    modifier = Modifier.padding(start = 15.dp)
                )
            },
            label = {
                Text(
                    text = "Почта", fontSize = 14.sp,
                    lineHeight = 22.sp,
                    fontFamily = anonymousPro,
                    fontWeight = FontWeight(400),
                    color = Color(0xB2FFFFFF)
                )
            }
        )

        // passwordTextField
        TextField(
            value = passwordState,
            onValueChange = { text: String ->
                passwordState = text
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            modifier = Modifier
                .constrainAs(passwordInput) {
                    top.linkTo(mailInput.bottom, 20.dp)
                    start.linkTo(parent.start, 30.dp)
                    end.linkTo(parent.end, 30.dp)
                    width = Dimension.fillToConstraints
                }
                .standardShadow()
//                .rsBlurShadow(
//                    radius = 20.dp,
//                    spread = 0.dp,
//                    offset = DpOffset(10.dp, 10.dp),
//                    shape = RoundedCornerShape(25.dp)
//                )
            ,
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
            textStyle = TextStyle(
                fontFamily = anonymousPro,
                fontWeight = FontWeight(400),
                fontSize = 17.sp,
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_lock),
                    contentDescription = "lock icon",
                    tint = Color.White,
                    modifier = Modifier.padding(start = 15.dp)
                )
            },
            label = {
                Text(
                    text = "Придумайте пароль", fontSize = 14.sp,
                    lineHeight = 22.sp,
                    fontFamily = anonymousPro,
                    fontWeight = FontWeight(400),
                    color = Color(0xB2FFFFFF)
                )
            }
        )
        Text(
            text = "Войти в аккаунт",
            fontSize = 14.sp,
            color = Brown,
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight(400),
            modifier = Modifier
                .constrainAs(signInLabel) {
                    end.linkTo(passwordInput.end, 10.dp)
                    top.linkTo(passwordInput.bottom, 10.dp)
                }
                .clickable {
                    navController.navigate("signIn")
                })
        Button(
            onClick = {
                viewModel.loginUser(login = nameState, password = passwordState)
                with(mainActivity) {
                    startActivity(Intent(this, MuseumActivity::class.java))
                }
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
                    top.linkTo(signInLabel.bottom, 20.dp)
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
}

//@Preview
//@Composable
//fun signInPreview(){
//    val viewModel by viewModel<MainViewModel>()
//    signIn(keyboardState = mutableStateOf(false), viewModel = MainViewModel(checkLoginUseCase = checkLogiin))
//}