package com.startup.museumapp.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.startup.museumapp.R
import com.startup.museumapp.ui.commonElements.GradientBackground
import com.startup.museumapp.ui.commonElements.standardShadow
import com.startup.museumapp.ui.theme.anonymousPro

@Composable
fun ProfileScreen(navController: NavController) {
    GradientBackground()
    Column(
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth()
                .background(color = Color(0xFFA5A68F), shape = RoundedCornerShape(25.dp))
                .shadow(elevation = (-30).dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Мой профиль",
                    style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 22.sp,
                        fontFamily = anonymousPro,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                )
                Box(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .size(100.dp)
                        .background(color = Color(0xFFB27F5D), shape = CircleShape)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_user_profile),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        tint = Color.White
                    )
                }
                Text(
                    text = "Номер телефона",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        fontFamily = anonymousPro,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Start,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp)
                )
                var phoneNumber by remember {
                    mutableStateOf("")
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp)
                        .padding(top = 5.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                ) {
                    Box(
                        modifier = Modifier.height(40.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        BasicTextField(
                            value = phoneNumber,
                            onValueChange = {
                                phoneNumber = it
                            },
                            textStyle = TextStyle(textAlign = TextAlign.Start),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp)
                        )
                    }
                }
                Text(
                    text = "Электронная почта",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        fontFamily = anonymousPro,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Start,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, start = 30.dp)
                )
                var mail by remember {
                    mutableStateOf("")
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp)
                        .padding(top = 5.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                ) {
                    Box(
                        modifier = Modifier.height(40.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        BasicTextField(
                            value = mail,
                            onValueChange = {
                                mail = it
                            },
                            textStyle = TextStyle(textAlign = TextAlign.Start),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp)
                        )
                    }
                }

                Text(
                    text = "Редактировать",
                    style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 22.sp,
                        fontFamily = anonymousPro,
                        fontWeight = FontWeight(400),
                        color = Color(0xB2FFFFFF),
                        textAlign = TextAlign.End,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 30.dp, top = 10.dp, bottom = 10.dp)
                )
            }
        }
        ButtonCard(
            modifier = Modifier.padding(top = 50.dp),
            iconResource = R.drawable.ic_my_tickets,
            text = "Мои билеты"
        ) {
            navController.navigate("ticketScreen")
        }
        ButtonCard(
            modifier = Modifier.padding(top = 50.dp),
            iconResource = R.drawable.ic_cards_info,
            text = "Способы оплаты"
        ) {
            navController.navigate("paymentScreen")
        }
        ButtonCard(
            modifier = Modifier.padding(top = 50.dp),
            iconResource = R.drawable.ic_review_star_outline,
            text = "Избранные музеи"
        ) {

        }
        ButtonCard(
            modifier = Modifier.padding(top = 50.dp, bottom = 50.dp),
            iconResource = R.drawable.ic_support,
            text = "Помощь и поддержка"
        ) {

        }
    }
}

@Composable
fun ButtonCard(
    modifier: Modifier = Modifier,
    @DrawableRes iconResource: Int,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color(0xFF7E7052), shape = RoundedCornerShape(size = 25.dp))
            .clickable {
                onClick()
            }
            .standardShadow(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconResource),
            contentDescription = "tickets info",
            tint = Color.White,
            modifier = Modifier
                .padding(13.dp)
                .size(24.dp),
        )
        Text(
            text = text,
            style = TextStyle(
                fontSize = 20.sp,
                lineHeight = 22.sp,
                fontFamily = anonymousPro,
                fontWeight = FontWeight(400),
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center,
            )
        )
        Spacer(modifier = Modifier.weight(1f))
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

@Composable
fun TicketsScreen(navController: NavController) {
    GradientBackground()
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (backArrow, archiveIcon, archiveLabel, nothingTicketsLabel, ticketIcon, adLabel) = createRefs()
        val interactionSource = remember{ MutableInteractionSource() }
        Icon(
            painter = painterResource(id = R.drawable.ic_back_arrow),
            contentDescription = "back",
            tint = Color.White,
            modifier = Modifier
                .constrainAs(backArrow) {
                    start.linkTo(parent.start, 20.dp)
                    top.linkTo(parent.top, 20.dp)
                }
                .clickable (interactionSource, null){
                    navController.popBackStack()
                }
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_arshive),
            contentDescription = "archive",
            tint = Color.White,
            modifier = Modifier.constrainAs(archiveIcon) {
                end.linkTo(parent.end, 20.dp)
                top.linkTo(parent.top, 20.dp)
            }
        )

        Text(
            text = "Архив",
            style = TextStyle(
                fontSize = 12.sp,
                lineHeight = 22.sp,
                fontFamily = anonymousPro,
                fontWeight = FontWeight(400),
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center,
            ),
            modifier = Modifier.constrainAs(archiveLabel) {
                centerHorizontallyTo(archiveIcon)
                top.linkTo(archiveIcon.bottom)
            }
        )

        createVerticalChain(
            nothingTicketsLabel,
            ticketIcon,
            adLabel,
            chainStyle = ChainStyle.Packed
        )

        Text(
            text = "Доступных билетов нет",
            style = TextStyle(
                fontSize = 25.sp,
                lineHeight = 22.sp,
                fontFamily = anonymousPro,
                fontWeight = FontWeight(400),
                color = Color(0xFF7E7052),
                textAlign = TextAlign.Center,
            ),
            modifier = Modifier.constrainAs(nothingTicketsLabel) {
                linkTo(backArrow.end, archiveIcon.start)
                width = Dimension.fillToConstraints
            }
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_ticket),
            contentDescription = "",
            tint = Color(0xFF7E7052),
            modifier = Modifier
                .constrainAs(ticketIcon) {
                    centerHorizontallyTo(parent)
                }
                .padding(vertical = 10.dp)
        )
        Text(
            text = "Они появятся здесь, как только вы оплатите билеты, через приложение",
            style = TextStyle(
                fontSize = 17.sp,
                lineHeight = 22.sp,
                fontFamily = anonymousPro,
                fontWeight = FontWeight(400),
                color = Color(0xFF7E7052),
                textAlign = TextAlign.Center,
            ),
            modifier = Modifier.constrainAs(adLabel) {
                start.linkTo(parent.start, 30.dp)
                end.linkTo(parent.end, 30.dp)
                width = Dimension.fillToConstraints
            }
        )
    }
}

@Composable
fun PaymentMethod(navController: NavController) {
    ConstraintLayout {
        val (backArrow, methodsColumn) = createRefs()
        val interactionSource = remember{ MutableInteractionSource() }
        Icon(
            painter = painterResource(id = R.drawable.ic_back_arrow),
            contentDescription = "back",
            tint = Color.White,
            modifier = Modifier
                .constrainAs(backArrow) {
                    start.linkTo(parent.start, 20.dp)
                    top.linkTo(parent.top, 20.dp)
                }
                .clickable (interactionSource, null){
                    navController.popBackStack()
                }
        )

        Column(modifier = Modifier
            .padding(horizontal = 30.dp)
            .constrainAs(methodsColumn) {
                centerHorizontallyTo(parent)
                top.linkTo(backArrow.bottom, 20.dp)
            }
        ) {
            repeat(1){
                PaymentCard()
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Preview
@Composable
fun PaymentCard() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(
                color = Color(0xFFA5A68F),
                shape = RoundedCornerShape(size = 15.dp)
            )
            .fillMaxWidth()
            .standardShadow()
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_card),
            contentDescription = "",
            tint = Color.White,
            modifier = Modifier
                .padding(10.dp)
                .size(40.dp)
        )
        Column {

            Text(
                text = "МИР",
                style = TextStyle(
                    fontSize = 18.sp,
                    lineHeight = 22.sp,
                    fontFamily = anonymousPro,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center,
                )
            )
            Text(
                text = "****0848",
                style = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 22.sp,
                    fontFamily = anonymousPro,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center,
                )
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow),
            contentDescription = "",
            tint = Color.White,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .size(15.dp)
        )
    }
}