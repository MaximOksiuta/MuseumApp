package com.startup.museumapp.ui.screens

import android.os.CountDownTimer
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
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
import com.startup.museumapp.ui.commonElements.getCardDefaultElevation
import com.startup.museumapp.ui.commonElements.standardShadow
import com.startup.museumapp.ui.theme.anonymousPro
import java.lang.Exception

@Composable
fun ChatScreen(navController: NavController) {
    GradientBackground(
//        modifier = Modifier.offset(y = .dp)
    )
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (topBox, backArrow, userIcon, userColumn, phoneIcon, messagesColumn, messageInput, microphoneIcon) = createRefs()
        // i love you ♥️
        val interactionSource = remember { MutableInteractionSource() }
        createHorizontalChain(userIcon, userColumn, chainStyle = ChainStyle.Packed)
        Surface(
            modifier = Modifier
                .constrainAs(topBox) {
                    centerHorizontallyTo(parent)
                    width = Dimension.fillToConstraints
                    top.linkTo(parent.top)
                    bottom.linkTo(userColumn.bottom, (-10).dp)
                    height = Dimension.fillToConstraints
                }, color = Color(0xFFA5A68F)
        ) {

        }
        Icon(
            painter = painterResource(id = R.drawable.ic_back_arrow),
            contentDescription = "back",
            tint = Color.White,
            modifier = Modifier
                .constrainAs(backArrow) {
                    start.linkTo(parent.start, 20.dp)
                    top.linkTo(parent.top, 20.dp)
                }
                .clickable(interactionSource = interactionSource, indication = null) {
                    navController.popBackStack()
                }
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_default_user_icon),
            contentDescription = "",
            tint = Color.White,
            modifier = Modifier.constrainAs(userIcon) {
                centerVerticallyTo(backArrow)
                start.linkTo(backArrow.end)
            }
        )
        Column(modifier = Modifier
            .constrainAs(userColumn) {
                bottom.linkTo(userIcon.bottom)
                end.linkTo(phoneIcon.start)
            }
            .padding(start = 10.dp, end = 40.dp)) {
            Text(
                text = "Марина",
                style = TextStyle(
                    fontSize = 17.sp,
                    lineHeight = 22.sp,
                    fontFamily = anonymousPro,
                    fontWeight = FontWeight(700),
                    color = Color(0xFFFFFFFF)
                )
            )
            Text(
                text = "Сотрудник музея",
                style = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 22.sp,
                    fontFamily = anonymousPro,
                    fontWeight = FontWeight(400),
                    color = Color(0xB2FFFFFF),
                ),
                modifier = Modifier.padding(top = 5.dp)
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_phone_chat),
            contentDescription = "",
            tint = Color.White,
            modifier = Modifier.constrainAs(phoneIcon) {
                centerVerticallyTo(backArrow)
                end.linkTo(parent.end, 30.dp)
            }
        )
        val preparedAnswers = mutableListOf(
            Pair(
                false,
                "Вы можете попробовать перезагрузить устройство долгим нажатием кнопки или заменить его в пункте выдачи устройств на входе в выставочный зал."
            ),
            Pair(false, "Была рада помочь. Вы можете написать снова, если возникнут вопросы")
        )
        val messages = remember{
            mutableStateListOf(
                Pair(false, "Здравствуйте! Я - Марина, сотрудник музея. Чем могу помочь?")
//                Pair(false, "Была рада помочь. Вы можете написать снова, если возникнут вопросы"),
//                Pair(true, "Спасибо, получилось!"),
//                Pair(
//                    false,
//                    "Вы можете попробовать перезагрузить устройство долгим нажатием кнопки или заменить его в пункте выдачи устройств на входе в выставочный зал."
//                ),
//                Pair(true, "Добрый день. Возникли проблемы с подключением девайса"),
//                Pair(false, "Здравствуйте! Я - Марина, сотрудник музея. Чем могу помочь?")
            )
        }
        val maxMessageWidth = ((LocalConfiguration.current.screenWidthDp - 60) * 0.7).dp
        Column(modifier = Modifier
            .constrainAs(messagesColumn) {
                centerHorizontallyTo(parent)
                width = Dimension.fillToConstraints
                top.linkTo(topBox.bottom)
                bottom.linkTo(parent.bottom)
                height = Dimension.fillToConstraints
            }) {
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .fillMaxHeight(),
                reverseLayout = true
            ) {
                item {
                    Spacer(modifier = Modifier.height(70.dp))
                }
                items(messages) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 10.dp)
                    ) {
                        Card(
                            modifier = Modifier
                                .widthIn(max = maxMessageWidth)
                                .align(if (it.first) Alignment.End else Alignment.Start).shadow(20.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (it.first) Color(0xCC7E7052) else Color(
                                    0xCCB27F5D
                                )
                            ),


                            ) {
                            Text(
                                text = it.second,
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    lineHeight = 22.sp,
                                    fontFamily = anonymousPro,
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFFFFFFFF),
                                    textAlign = TextAlign.Justify
                                ),
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
        }
        var messageInputValue by remember {
            mutableStateOf("")
        }
        BasicTextField(
            modifier = Modifier
                .constrainAs(messageInput) {
                    start.linkTo(parent.start, 30.dp)
                    end.linkTo(microphoneIcon.start, 10.dp)
                    bottom.linkTo(parent.bottom, 20.dp)
                    width = Dimension.fillToConstraints
                }
                .background(color = Color(0xFFA5A68F), shape = CircleShape)
                .padding(vertical = 10.dp, horizontal = 20.dp)
                .standardShadow(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions(
                onSend = {
                    messages.add(0, Pair(true, messageInputValue))
                    messageInputValue = ""
                    object: CountDownTimer(1000, 1000){
                        override fun onTick(millisUntilFinished: Long) {

                        }

                        override fun onFinish() {
                            try {
                                messages.add(0, preparedAnswers.removeAt(0))
                            } catch (e: Exception){
                                Log.d("MessageProvider", "messages is empty")
                            }
                        }
                    }.start()
                }
            ),
            value = messageInputValue,
            onValueChange = {
                messageInputValue = it
            },
            cursorBrush = Brush.verticalGradient(colors = listOf(Color.White, Color.White)),
            textStyle = TextStyle(
                fontSize = 15.sp,
                lineHeight = 22.sp,
                fontFamily = anonymousPro,
                fontWeight = FontWeight(400),
                color = Color.White,
                textAlign = TextAlign.Start,
            ),
            decorationBox = { innerTextField ->
                if (messageInputValue.isEmpty()) {
                    Text(
                        text = "Введите сообщение",
                        style = TextStyle(
                            fontSize = 15.sp,
                            lineHeight = 22.sp,
                            fontFamily = anonymousPro,
                            fontWeight = FontWeight(400),
                            color = Color(0x99FFFFFF),
                            textAlign = TextAlign.Start,
                        )
                    )
                } else {
                    innerTextField()
                }
            }
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_sosiska_v_bokale),
            contentDescription = "",
            tint = Color(0xFFA5A68F),
            modifier = Modifier.constrainAs(microphoneIcon){
                centerVerticallyTo(messageInput)
                height = Dimension.fillToConstraints
                end.linkTo(parent.end, 30.dp)
            }
        )
//            colors = TextFieldDefaults.colors(
//                disabledContainerColor = Color(0xFFA5A68F),
//                errorContainerColor = Color(0xFFA5A68F),
//                focusedContainerColor = Color(0xFFA5A68F),
//                unfocusedContainerColor = Color(0xFFA5A68F),
//                unfocusedIndicatorColor = Color.Transparent,
//                focusedIndicatorColor = Color.Transparent,
//                errorIndicatorColor = Color.Transparent,
//                disabledIndicatorColor = Color.Transparent,
//                cursorColor = Color.White,
//                errorTextColor = Color.White,
//                disabledTextColor = Color.White,
//                unfocusedTextColor = Color.White,
//                focusedTextColor = Color.White
//            ),
//            shape = CircleShape,
//            placeholder = {
//                Text(
//                    text = "Введите сообщение",
//                    style = TextStyle(
//                        fontSize = 15.sp,
//                        lineHeight = 22.sp,
//                        fontFamily = anonymousPro,
//                        fontWeight = FontWeight(400),
//                        color = Color(0x99FFFFFF),
//                        textAlign = TextAlign.Center,
//                    )
//                )
//            }

    }
}