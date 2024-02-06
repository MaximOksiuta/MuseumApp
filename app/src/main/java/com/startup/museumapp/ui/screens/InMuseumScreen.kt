package com.startup.museumapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
//import com.gigamole.composeshadowsplus.rsblur.rsBlurShadow
import com.startup.museumapp.R
import com.startup.museumapp.ui.commonElements.GradientBackground
import com.startup.museumapp.ui.commonElements.getCardDefaultElevation
import com.startup.museumapp.ui.commonElements.standardShadow
import com.startup.museumapp.ui.theme.anonymousPro

@Composable
fun InMuseumScreen(navController: NavController) {
    GradientBackground()
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (backArrow, excursionButton, mapButton, helpButton) = createRefs()
        createVerticalChain(excursionButton, mapButton, helpButton, chainStyle = ChainStyle.Packed)
        val interactionSource = remember { MutableInteractionSource() }
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

        Card(
            modifier = Modifier
                .constrainAs(excursionButton) {
                    centerHorizontallyTo(parent)
                    width = Dimension.fillToConstraints
                }
                .padding(horizontal = 30.dp, vertical = 20.dp)
//                .standardShadow()
//                .rsBlurShadow(
//                    radius = 20.dp,
//                    spread = 0.dp,
//                    offset = DpOffset(10.dp, 10.dp),
//                    shape = RoundedCornerShape(25.dp)
//                )
                ,
            shape = RoundedCornerShape(25.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFB27F5D)
            ),
            onClick = {
                navController.navigate("exhibitDetails")
            },
            elevation = getCardDefaultElevation()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_excursion),
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
                )
                Text(
                    text = "Экскурсия",
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
                        .padding(vertical = 15.dp, horizontal = 20.dp)
                        .size(15.dp),
                )
            }
        }
        Card(
            modifier = Modifier
                .constrainAs(mapButton) {
                    centerHorizontallyTo(parent)
                    width = Dimension.fillToConstraints
                }
                .padding(horizontal = 30.dp, vertical = 20.dp)
//                .standardShadow()
//                .rsBlurShadow(
//                    radius = 20.dp,
//                    spread = 0.dp,
//                    offset = DpOffset(10.dp, 10.dp),
//                    shape = RoundedCornerShape(25.dp)
//                )
            ,
            shape = RoundedCornerShape(25.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFB27F5D)
            ),
            onClick = {
                navController.navigate("mapScreen")
            },
            elevation = getCardDefaultElevation()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_map),
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
                )
                Text(
                    text = "Карта музея",
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
                        .padding(vertical = 15.dp, horizontal = 20.dp)
                        .size(15.dp),
                )
            }
        }
        Card(
            modifier = Modifier
                .constrainAs(helpButton) {
                    centerHorizontallyTo(parent)
                    width = Dimension.fillToConstraints
                }
                .padding(horizontal = 30.dp, vertical = 20.dp)
//                .standardShadow()
//                .rsBlurShadow(
//                    radius = 20.dp,
//                    spread = 0.dp,
//                    offset = DpOffset(10.dp, 10.dp),
//                    shape = RoundedCornerShape(25.dp)
//                )
            ,
            shape = RoundedCornerShape(25.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFB27F5D)
            ),
            onClick = {
                navController.navigate("chatScreen")
            },
            elevation = getCardDefaultElevation()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_help_chat),
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
                )
                Text(
                    text = "Чат поддержки",
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
                        .padding(vertical = 15.dp, horizontal = 20.dp)
                        .size(15.dp),
                )
            }
        }
    }
}