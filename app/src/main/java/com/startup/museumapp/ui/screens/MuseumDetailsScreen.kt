package com.startup.museumapp.ui.screens

//import com.gigamole.composeshadowsplus.rsblur.rsBlurShadow

import android.content.Intent
import android.net.Uri
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import com.startup.museumapp.MuseumActivity
import com.startup.museumapp.R
import com.startup.museumapp.ui.commonElements.GradientBackground
import com.startup.museumapp.ui.commonElements.getCardDefaultElevation
import com.startup.museumapp.ui.theme.anonymousPro
import com.startup.museumapp.ui.viewModels.MuseumViewModel


@Composable
fun MuseumDetailScreen(activity: ComponentActivity, viewModel: MuseumViewModel, navController: NavController) {
    val museumInfo = viewModel.selectedMuseum!!
    GradientBackground()
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (column, likeButton) = createRefs()
        Column(modifier = Modifier
            .constrainAs(column) {
                start.linkTo(parent.start, 30.dp)
                end.linkTo(parent.end, 30.dp)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
            .verticalScroll(rememberScrollState())) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp)
//                    .standardShadow()
//                    .rsBlurShadow(
//                        radius = 20.dp,
//                        spread = 0.dp,
//                        offset = DpOffset(10.dp, 10.dp),
//                        shape = RoundedCornerShape(25.dp)
//                    )
                ,
                shape = RoundedCornerShape(25.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF7E7052)
                ),
                elevation = getCardDefaultElevation()
            ) {
                Text(
                    text = museumInfo.name,
                    style = TextStyle(
                        fontSize = 26.sp,
                        fontFamily = anonymousPro,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier.padding(vertical = 20.dp, horizontal = 20.dp)
                )
                Text(
                    text = museumInfo.description,
                    style = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 22.sp,
                        fontFamily = anonymousPro,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Justify
                    ),
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                )

            }
            Card(
                modifier = Modifier
                    .padding(top = 20.dp)
//                    .standardShadow()
//                    .rsBlurShadow(
//                        radius = 20.dp,
//                        spread = 0.dp,
//                        offset = DpOffset(10.dp, 10.dp),
//                        shape = RoundedCornerShape(25.dp)
//                    )
                ,
                shape = RoundedCornerShape(25.dp),
                elevation = getCardDefaultElevation(),
                onClick = {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(museumInfo.yandexMaps))
                    with(activity){
                        startActivity(browserIntent)
                    }
                }
            ) {
                Text(
                    text = "Адрес музея",
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 22.sp,
                        fontFamily = anonymousPro,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color(0xFFA5A68F), shape = RoundedCornerShape(25.dp))
                        .padding(vertical = 25.dp)
                )
            }
            Card(
                modifier = Modifier
                    .padding(top = 20.dp)
//                    .standardShadow()
//                    .rsBlurShadow(
//                        radius = 20.dp,
//                        spread = 0.dp,
//                        offset = DpOffset(10.dp, 10.dp),
//                        shape = RoundedCornerShape(25.dp)
//                    )
                ,
                shape = RoundedCornerShape(25.dp),
                elevation = getCardDefaultElevation(),
                onClick = {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(museumInfo.yandexMaps))
                    with(activity){
                        startActivity(browserIntent)
                    }
                }
            ) {
                Text(
                    text = "Купить билет",
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 22.sp,
                        fontFamily = anonymousPro,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color(0xFFA5A68F), shape = RoundedCornerShape(25.dp))
                        .padding(vertical = 25.dp)
                )
            }
            Card(
                modifier = Modifier
                    .padding(top = 20.dp)
//                    .standardShadow()
//                    .rsBlurShadow(
//                        radius = 20.dp,
//                        spread = 0.dp,
//                        offset = DpOffset(10.dp, 10.dp),
//                        shape = RoundedCornerShape(25.dp)
//                    )
                ,
                shape = RoundedCornerShape(25.dp),
                onClick = {
                    if (viewModel.deviceIsConnected.not()) {
                        navController.navigate("deviceConnecting")
                    } else {
                        navController.navigate("inMuseumScreen")
                    }
                },
                elevation = getCardDefaultElevation()
            ) {
                Text(
                    text = "Начать исследование музея",
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 22.sp,
                        fontFamily = anonymousPro,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color(0xFFA5A68F), shape = RoundedCornerShape(25.dp))
                        .padding(vertical = 25.dp)
                )
            }
            Spacer(modifier = Modifier.height(100.dp))
        }

        FloatingActionButton(
            onClick = { /*TODO*/ },
            containerColor = Color(0xFF7E7052),
            contentColor = Color.White,
            modifier = Modifier
                .clip(CircleShape)
                .constrainAs(likeButton) {
                    end.linkTo(parent.end, 20.dp)
                    bottom.linkTo(parent.bottom, 20.dp)
                }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_review_star_outline),
                contentDescription = "reviewStar"
            )
        }
    }
}
