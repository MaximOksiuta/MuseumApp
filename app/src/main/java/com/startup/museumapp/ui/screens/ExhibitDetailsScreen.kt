package com.startup.museumapp.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.gigamole.composeshadowsplus.rsblur.rsBlurShadow
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.startup.museumapp.R
import com.startup.museumapp.domain.dataModels.ExhibitInfo
import com.startup.museumapp.domain.dataModels.ExtensionInfo
import com.startup.museumapp.domain.dataModels.ExtensionLinks
import com.startup.museumapp.ui.commonElements.GradientBackground
import com.startup.museumapp.ui.commonElements.advanceShadow
import com.startup.museumapp.ui.commonElements.getCardDefaultElevation
import com.startup.museumapp.ui.commonElements.standardShadow
import com.startup.museumapp.ui.theme.anonymousPro
import kotlin.math.ceil


@Composable fun Shadowed(modifier: Modifier, color: Color, offsetX: Dp, offsetY: Dp, blurRadius: Dp, content: @Composable () -> Unit) {
    val density = LocalDensity.current
    val offsetXPx = with(density) { offsetX.toPx() }.toInt()
    val offsetYPx = with(density) { offsetY.toPx() }.toInt()
    val blurRadiusPx = ceil(with(density) {
        blurRadius.toPx()
    }).toInt()

    // Modifier to render the content in the shadow color, then
    // blur it by blurRadius
    val shadowModifier = Modifier
        .drawWithContent {
            val matrix = shadowColorMatrix(color)
            val filter = ColorFilter.colorMatrix(matrix)
            val paint = Paint().apply {
                colorFilter = filter
            }
            drawIntoCanvas { canvas ->
                canvas.saveLayer(Rect(0f, 0f, size.width, size.height), paint)
                drawContent()
                canvas.restore()
            }
        }
        .blur(radius = blurRadius, BlurredEdgeTreatment.Unbounded)
        .padding(all = blurRadius) // Pad to prevent clipping blur

    // Layout based solely on the content, placing shadow behind it
    Layout(modifier = modifier, content = {
        // measurables[0] = content, measurables[1] = shadow
        content()
        Box(modifier = shadowModifier) { content() }
    }) { measurables, constraints ->
        // Allow shadow to go beyond bounds without affecting layout
        val contentPlaceable = measurables[0].measure(constraints)
        val shadowPlaceable = measurables[1].measure(Constraints(maxWidth = contentPlaceable.width + blurRadiusPx * 2, maxHeight = contentPlaceable.height + blurRadiusPx * 2))
        layout(width = contentPlaceable.width, height = contentPlaceable.height) {
            shadowPlaceable.placeRelative(x = offsetXPx - blurRadiusPx, y = offsetYPx - blurRadiusPx)
            contentPlaceable.placeRelative(x = 0, y = 0)
        }
    }
}

// Return a color matrix with which to paint our content
// as a shadow of the given color
private fun shadowColorMatrix(color: Color): ColorMatrix {
    return ColorMatrix().apply {
        set(0, 0, 0f) // Do not preserve original R
        set(1, 1, 0f) // Do not preserve original G
        set(2, 2, 0f) // Do not preserve original B

        set(0, 4, color.red * 255) // Use given color's R
        set(1, 4, color.green * 255) // Use given color's G
        set(2, 4, color.blue * 255) // Use given color's B
        set(3, 3, color.alpha) // Multiply by given color's alpha
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ExhibitDetailsScreen(navController: NavController) {
    GradientBackground()
    val exhibitInfo by remember {
        mutableStateOf<ExhibitInfo?>(null)
    }
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (backArrow, notNearLabel, mainColumn) = createRefs()
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
                .zIndex(1f)
                .clickable(interactionSource, null) {
                    navController.popBackStack()
                }
        )
        if (exhibitInfo != null) {
            Text(
                text = "Когда вы будете рядом с экспонатом, здесь появиться информация о нем",
                style = TextStyle(
                    fontSize = 25.sp,
                    lineHeight = 22.sp,
                    fontFamily = anonymousPro,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF7E7052),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .constrainAs(notNearLabel) {
                        centerTo(parent)
                        width = Dimension.fillToConstraints
                    }
                    .padding(30.dp)
            )
        } else {
            Column(modifier = Modifier
                .constrainAs(mainColumn) {
                    centerHorizontallyTo(parent)
                    top.linkTo(parent.top)
                    width = Dimension.fillToConstraints
                }
                .verticalScroll(rememberScrollState())) {
                Spacer(modifier = Modifier.padding(top = 80.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFB27F5D)
                    ),
                    elevation = getCardDefaultElevation()
                ) {
                    val width = LocalConfiguration.current.screenWidthDp
                    AsyncImage(
                        model = "https://webpulse.imgsmail.ru/imgpreview?key=pulse_cabinet-image-6d479a37-d07d-4fe1-9dc2-3836cd38c997&mb=webpulse",
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(((width - 60) / 1.5).dp)
                            .clip(RoundedCornerShape(25.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = "Чёрный квадрат",
                        style = TextStyle(
                            fontSize = 26.sp,
                            lineHeight = 22.sp,
                            fontFamily = anonymousPro,
                            fontWeight = FontWeight(400),
                            color = Color(0xFFFFFFFF),
                            textAlign = TextAlign.Center,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    )
                    Text(
                        text = "Это одна из самых обсуждаемых и самых известных картин в мировом искусстве. Входит в цикл супрематических работ Малевича, в которых художник исследовал базовые возможности цвета и композиции.",
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 22.sp,
                            fontFamily = anonymousPro,
                            fontWeight = FontWeight(400),
                            color = Color(0xFFFFFFFF),
                            textAlign = TextAlign.Justify,
                        ),
                        modifier = Modifier.padding(10.dp)
                    )
                }
//                Shadowed(modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 30.dp)
//                    .padding(horizontal = 30.dp)
//                    , color = Color.Black, offsetX = 10.dp, offsetY = 10.dp, blurRadius = 20.dp) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 30.dp)
                            .padding(horizontal = 30.dp)
                            .rsBlurShadow(
                                radius = 20.dp,
                                spread = 0.dp,
//                            offset = DpOffset(10.dp, 10.dp),
                                shape = RoundedCornerShape(25.dp)
                            ),
                        shape = RoundedCornerShape(25.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xB27E7052)
                        ),
//                    elevation = CardDefaults.cardElevation(
//                        defaultElevation = 20.dp
//                    )
//                    elevation = getCardDefaultElevation()
                    ) {
                        Text(
                            text = "Чёрный квадрат",
                            style = TextStyle(
                                fontSize = 14.sp,
                                lineHeight = 22.sp,
                                fontFamily = anonymousPro,
                                fontWeight = FontWeight(700),
                                color = Color(0xFFFFFFFF),
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp)
                        )
                        Text(
                            text = "К.С. Малевич",
                            style = TextStyle(
                                fontSize = 14.sp,
                                lineHeight = 22.sp,
                                fontFamily = anonymousPro,
                                fontWeight = FontWeight(400),
                                color = Color(0xCCFFFFFF),
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 15.dp)
                        ) {
                            Text(
                                text = "2:30",
                                style = TextStyle(
                                    fontSize = 10.sp,
                                    lineHeight = 22.sp,
                                    fontFamily = anonymousPro,
                                    fontWeight = FontWeight(400),
                                    color = Color(0xB2FFFFFF),
                                ),
                                modifier = Modifier.padding(start = 20.dp)
                            )
                            LinearProgressIndicator(
                                color = Color.White,
                                trackColor = Color.White.copy(alpha = 0.6f),
                                strokeCap = StrokeCap.Round,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 10.dp)
                            )
                            Text(
                                text = "-3:03",
                                style = TextStyle(
                                    fontSize = 10.sp,
                                    lineHeight = 22.sp,
                                    fontFamily = anonymousPro,
                                    fontWeight = FontWeight(400),
                                    color = Color(0xB2FFFFFF)
                                ),
                                modifier = Modifier.padding(end = 20.dp)
                            )
                        }
                        Icon(
                            painter = painterResource(id = R.drawable.ic_play),
                            contentDescription = "",
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(vertical = 10.dp),
                            tint = Color.White
                        )
                    }
//                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp)
                        .padding(horizontal = 30.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Card(
                        shape = RoundedCornerShape(25.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xB27E7052)
                        ),
                        elevation = getCardDefaultElevation()
//                        modifier = Modifier.standardShadow()
//                        modifier = Modifier.rsBlurShadow(
//                            radius = 20.dp,
//                            spread = 0.dp,
//                            offset = DpOffset(10.dp, 10.dp),
//                            shape = RoundedCornerShape(25.dp)
//                        )
                    ) {
                        AsyncImage(
                            model = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/63/Casimir_Malevich_photo.jpg/440px-Casimir_Malevich_photo.jpg",
                            contentDescription = "",
                            modifier = Modifier
                                .clip(RoundedCornerShape(25.dp))
                                .width(150.dp)
                        )
                        Text(
                            text = "К. С. Малевич",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontFamily = anonymousPro,
                                fontWeight = FontWeight(400),
                                color = Color(0xFFFFFFFF),
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(vertical = 10.dp)
                        )
                    }
                    ConstraintLayout(
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .fillMaxWidth()
                    ) {
                        val (firstCard, secondCard) = createRefs()
                        createVerticalChain(firstCard, secondCard, chainStyle = ChainStyle.Packed)
                        Card(
                            modifier = Modifier
                                .constrainAs(firstCard) {
                                    centerHorizontallyTo(parent)
                                }
                                .padding(vertical = 10.dp)
                                ,
//                                .rsBlurShadow(
//                                    radius = 20.dp,
//                                    spread = 0.dp,
//                                    offset = DpOffset(10.dp, 10.dp),
//                                    shape = RoundedCornerShape(25.dp)
//                                ),
                            shape = RoundedCornerShape(25.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFA5A68F)
                            ),
                            elevation = getCardDefaultElevation()
                        ) {
                            Column(
                                verticalArrangement = Arrangement.SpaceEvenly,
                                modifier = Modifier.padding(vertical = 10.dp)
                            ) {
                                ExtensionInfo(
                                    listOf(
                                        Pair("Год создания", "1915"),
                                        Pair("Направление", "супрематизм"),
                                        Pair("Размер", "79,5 * 79,5 см")
                                    )
                                ).infoList.forEach {
                                    Text(
                                        text = "${it.first}: ${it.second}",
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            lineHeight = 22.sp,
                                            fontFamily = anonymousPro,
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFFFFFFFF),
                                            textAlign = TextAlign.Center,
                                        ),
                                        modifier = Modifier.padding(
                                            vertical = 10.dp,
                                            horizontal = 5.dp
                                        )
                                    )
                                }
                            }
                        }
                        Card(
                            modifier = Modifier
                                .constrainAs(secondCard) {
                                    centerHorizontallyTo(firstCard)
                                    width = Dimension.fillToConstraints
                                }
                                .padding(vertical = 10.dp)
//                                .standardShadow()
//                                .rsBlurShadow(
//                                    radius = 20.dp,
//                                    spread = 0.dp,
//                                    offset = DpOffset(10.dp, 10.dp),
//                                    shape = RoundedCornerShape(25.dp)
//                                )
                            ,
                            shape = RoundedCornerShape(15.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFA5A68F)
                            ),
                            elevation = getCardDefaultElevation()
                        ) {
                            Text(
                                text = "Интересные факты",
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontFamily = anonymousPro,
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFFFFFFFF),
                                    textAlign = TextAlign.Center,
                                ),
                                modifier = Modifier
                                    .padding(horizontal = 10.dp, vertical = 10.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }

                val pagerState = rememberPagerState(pageCount = 2)
                val sliderList = listOf(
                    ExtensionLinks(
                        "Чёрный круг",
                        "https://static.tildacdn.com/tild3162-6530-4161-b938-363065376138/_YaF0bLL7To.jpg",
                        "https://ru.wikipedia.org/wiki/Чёрный_круг"
                    ),
                    ExtensionLinks(
                        "Чёрный круг",
                        "https://static.tildacdn.com/tild3162-6530-4161-b938-363065376138/_YaF0bLL7To.jpg",
                        "https://ru.wikipedia.org/wiki/Чёрный_круг"
                    )
                )

                HorizontalPager(
                    state = pagerState,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 30.dp)
                ) { page ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFB27F5D)
                        ),
                        shape = RoundedCornerShape(25.dp),
                        modifier = Modifier
                            .padding(horizontal = 30.dp)
//                            .fillMaxWidth()
                            .graphicsLayer {

                                val pageOffset = calculateCurrentOffsetForPage(page)

                                lerp(
                                    start = 0.85f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                ).also { scale ->
                                    scaleX = scale
                                    scaleY = scale
                                }
                                alpha = lerp(
                                    start = 0.5f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                )
                            },
                        elevation = getCardDefaultElevation()
//                            .standardShadow()
//                            .rsBlurShadow(
//                                radius = 20.dp,
//                                spread = 0.dp,
//                                offset = DpOffset(10.dp, 10.dp),
//                                shape = RoundedCornerShape(25.dp)
//                            )
                    ) {
                        val width = LocalConfiguration.current.screenWidthDp
                        AsyncImage(
                            model = sliderList[page].imageUrl,
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(((width - 60) / 1.5).dp)
                                .clip(RoundedCornerShape(25.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = sliderList[page].name,
                            style = TextStyle(
                                fontSize = 26.sp,
                                lineHeight = 22.sp,
                                fontFamily = anonymousPro,
                                fontWeight = FontWeight(400),
                                color = Color(0xFFFFFFFF),
                                textAlign = TextAlign.Center,
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        )
                    }
                }
            }
        }
    }
}