package com.startup.museumapp.ui.screens

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.isSpecified
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.draw
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.graphics.withSaveLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.gigamole.composeshadowsplus.common.shadowsPlus
import com.gigamole.composeshadowsplus.rsblur.rsBlurShadow
import com.startup.museumapp.R
import com.startup.museumapp.data.api.Exhibit
import com.startup.museumapp.data.api.Point
import com.startup.museumapp.data.api.mapInformation
import com.startup.museumapp.ui.commonElements.GradientBackground
import com.startup.museumapp.ui.commonElements.advanceShadow
import com.startup.museumapp.ui.commonElements.getCardDefaultElevation
import com.startup.museumapp.ui.theme.anonymousPro
import com.startup.museumapp.ui.viewModels.MuseumViewModel
import kotlin.math.max
import kotlin.math.min

//data class Sofa(val )
fun Offset.inCoordinates(objectCoordinates: ObjectCoordinates) =
    (
            x < objectCoordinates.end &&
                    x > objectCoordinates.start &&
                    y < objectCoordinates.bottom &&
                    y > objectCoordinates.top
            )

@Composable
fun MapScreen(navController: NavController, viewModel: MuseumViewModel) {
    Log.d("mapScreen", "rendering mapScreen")
    GradientBackground()
    var scaleRatio by remember {
        mutableFloatStateOf(1f)
    }
    var userPosition = viewModel.userPosition.collectAsState()
    var startRoomPosition by remember { mutableStateOf(Offset(0f, 0f)) }

    val mapSize = remember {
        mutableStateOf(MapSize(0f, 0f, 0f, 0f))
    }
    val correctMapSize =
        with(LocalDensity.current) { (LocalConfiguration.current.screenWidthDp - 60).dp.toPx() }
    var renderCount = 0
    var canvasHeight by remember { mutableStateOf(10000f) }
    Log.d("testing", LocalDensity.current.density.toString())
    var showDialog by remember {
        mutableStateOf(false)
    }

    val userPosImage =
        rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.ic_user_position))
    Log.d("mapScreen", "all values specified now work for render mainUi")
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (backArrow, map, signs, floor, room) = createRefs()

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
        Log.d("mapScreen", "icon rendered")
        val centerGuideline = createGuidelineFromTop(0.5f)
        Canvas(
            modifier = Modifier
                .constrainAs(map) {
                    centerHorizontallyTo(parent)
                    bottom.linkTo(centerGuideline)
                    width = Dimension.fillToConstraints
                }
                .height(canvasHeight.dp)
                .padding(horizontal = 30.dp)
                .padding(top = 10.dp)
        ) {
            runInMapScope(
                this,
                startRoomPosition,
                mapSize = mapSize,
                scaleRatio = scaleRatio,
                userPosition = userPosition.value
            ) {
                renderCount += 1
                Log.d("mapScreen", "canvas rendering. render count is - $renderCount")
                mapInformation.walls.forEach {
                    renderLine(it.first, it.second)
                }
                mapInformation.exhibits.forEach {
                    renderObject(exhibit = it, Offset(0f, 0f))
                }
                Log.d("mapScreen", "rendered all")
                updateMapState()
                Log.d("mapScreen", "mapStateUpdated")
                if (renderCount == 1 && (mapSize.value.maximalX - mapSize.value.minimalX) != 0f) {
                    val nowSize = (mapSize.value.maximalX - mapSize.value.minimalX)
                    val newRatio = (correctMapSize / nowSize)
                    scaleRatio *= newRatio
                    Log.d(
                        "mapScreen",
                        "updated ratio $newRatio params - need map size $correctMapSize fact map size $nowSize"
                    )
                }
                Log.d("mapScreen", "rendered all 2")
                updateMapState()
                Log.d("mapScreen", "mapStateUpdated")
                (mapSize.value.maximalY - mapSize.value.minimalY) != 0f
                Log.d("mapScreen", ((mapSize.value.maximalY - mapSize.value.minimalY) != 0f).toString())
                Log.d("mapScreen", renderCount.toString())
                if (renderCount == 2 && (mapSize.value.maximalY - mapSize.value.minimalY) != 0f) {
                    Log.d("mapScreen", "calculating height")
                    canvasHeight = (mapSize.value.maximalY - mapSize.value.minimalY).toDp().value
                    Log.d("mapScreen", "now height is " + (mapSize.value.maximalY - mapSize.value.minimalY).toString())
                }
                if (userPosition.value.isSpecified){
                    renderUserMarker(imageBitmap = userPosImage)
                }
            }
        }
        Log.d("mapScreen", "canvas rendered")
        val interactionSource2 = remember {
            MutableInteractionSource()
        }
        Card(
            shape = CircleShape,
            colors = CardDefaults.cardColors(
                containerColor = Color(0x997E7052)
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp,
                pressedElevation = 5.dp,
                hoveredElevation = 0.dp
            ),
            modifier = Modifier
                .constrainAs(signs) {
                    start.linkTo(parent.start, 30.dp)
                    top.linkTo(map.bottom, 15.dp)
                }
//                .shadow(elevation = 10.dp)
                .clickable(interactionSource2, null) {
                    showDialog = true
                },
            onClick = {
                showDialog = true
            }
        ) {
            Text(
                text = "условные обозначения",
                style = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 22.sp,
                    fontFamily = anonymousPro,
                    fontWeight = FontWeight(400),
                    color = Color(0x99FFFFFF),
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 10.dp, vertical = 2.dp)
            )
        }
        Card(shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0x99A5A68F)
            ),
            modifier = Modifier
                .constrainAs(floor) {
                    end.linkTo(parent.end, 30.dp)
                    top.linkTo(signs.bottom, 10.dp)
                },
            elevation = getCardDefaultElevation()
//                    .shadow(elevation = 30.dp)
//                .rsBlurShadow(
//                    radius = 20.dp,
//                    spread = 0.dp,
//                    offset = DpOffset(10.dp, 10.dp),
//                    shape = RoundedCornerShape(15.dp)
//                )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "Этаж  1",
                    style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 22.sp,
                        fontFamily = anonymousPro,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .padding(vertical = 10.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_select_arrows),
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
            }
        }

        Card(shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0x99A5A68F)
            ),
            modifier = Modifier
                .constrainAs(room) {
                    end.linkTo(parent.end, 30.dp)
                    top.linkTo(floor.bottom, 20.dp)
                },
            elevation = getCardDefaultElevation()
//                    .shadow(elevation = 30.dp)
//                .rsBlurShadow(
//                    radius = 20.dp,
//                    spread = 0.dp,
//                    offset = DpOffset(10.dp, 10.dp),
//                    shape = RoundedCornerShape(15.dp)
//                )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "Зал   1",
                    style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 22.sp,
                        fontFamily = anonymousPro,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .padding(vertical = 10.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_select_arrows),
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
            }
        }
    }
    if (showDialog) {
        signDialog {
            showDialog = false
        }
    }
}

@Preview(showBackground = true)
@Composable
fun signDialog(onDismissRequest: () -> Unit = {}) {
    val config = LocalConfiguration.current
    val width = config.screenWidthDp
    val height = config.screenHeightDp
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .width((width - 60).dp)
                .background(
                    color = Color(0xB27E7052),
                    shape = RoundedCornerShape(25.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Условные обозначения",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 22.sp,
                    fontFamily = anonymousPro,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center,
                    textDecoration = TextDecoration.Underline,
                ),
                modifier = Modifier.padding(vertical = 10.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .width(10.dp)
                        .height(60.dp)
                        .background(color = Color(0xFFB27F5D))
                )
                Text(
                    text = "Вход/ выход",
                    style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 22.sp,
                        fontFamily = anonymousPro,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier.padding(end = 20.dp)
                )
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .width(5.dp)
                        .height(30.dp)
                        .background(color = Color(0xFFA5A68F))
                )
                Text(
                    text = "Экспонат",
                    style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 22.sp,
                        fontFamily = anonymousPro,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier.padding(end = 20.dp)
                )
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .width(10.dp)
                        .height(60.dp)
                        .background(color = Color(0xFF7E7052))
                )
                Text(
                    text = "Диван",
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
            Box(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .width(250.dp)
                    .height(20.dp)
                    .background(color = Color(0xB2D39E84))
            )
            Text(
                text = "Витринные экспонаты",
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    fontFamily = anonymousPro,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier.padding(top = 10.dp, bottom = 20.dp)
            )
        }
    }
}

private fun runInMapScope(
    drawScopeContext: DrawScope,
    startOffset: Offset,
    scaleRatio: Float,
    mapSize: MutableState<MapSize>,
    basicMapOptions: BasicMapOptions? = null,
    userPosition: Offset,
    content: MapScope.() -> Unit,
) {

    val mapScope = if (basicMapOptions == null) {
        MapScopeImpl(
            drawScopeContext = drawScopeContext,
            startOffset = startOffset,
            mapStates = mapSize,
            scaleRatio = scaleRatio,
            userPosition = userPosition
        )
    } else {
        MapScopeImpl(
            drawScopeContext = drawScopeContext,
            startOffset = startOffset,
            scaleRatio = scaleRatio,
            mapStates = mapSize,
            basicMapOptions = basicMapOptions,
            userPosition = userPosition
        )
    }

    with(mapScope) {
        content()
    }
}

interface MapScope {

    fun renderUserMarker(imageBitmap: VectorPainter)
    fun updateMapState()
    fun renderObject(exhibit: Exhibit, roomOffset: Offset)

    fun renderLine(startOffset: Offset, endOffset: Offset)
}

data class MapSize(
    var minimalX: Float,
    var minimalY: Float,
    var maximalX: Float,
    var maximalY: Float
)

class MapScopeImpl(
    private val drawScopeContext: DrawScope,
    var startOffset: Offset,
    var scaleRatio: Float,
    var mapStates: MutableState<MapSize>,
    var basicMapOptions: BasicMapOptions = DefaultOptions,
    var userPosition: Offset
) : MapScope {
    companion object {
        val DefaultOptions = BasicMapOptions()
    }

    private var lineWidth = 12f
    private var exhibitsCoordinates = mutableMapOf<Int, ObjectCoordinates>()


    private var minimalX: Float? = null
        set(value) {
            field = value
            Log.d("mapController", "minimalX $value")
//            mapStates.value.minimalX = value ?: 0f

        }
    private var minimalY: Float? = null
        set(value) {
            field = value
            Log.d("mapController", "minimalY $value")
//            mapStates.value.minimalY = value ?: 0f
        }
    private var maximalX: Float? = null
        set(value) {
            field = value
            Log.d("mapController", "maximalX $value")
//            mapStates.value.maximalX = value ?: 0f
        }
    private var maximalY: Float? = null
        set(value) {
            field = value
            Log.d("mapController", "maximalY $value")
//            mapStates.value.maximalY = value ?: 0f
        }

    override fun updateMapState() {
        mapStates.value = MapSize(minimalX!!, minimalY!!, maximalX!!, maximalY!!)
    }

    override fun renderUserMarker(imageBitmap: VectorPainter) {

        with(drawScopeContext) {
            val size = Size(
                30.dp.toPx(),
                (imageBitmap.intrinsicSize.height / imageBitmap.intrinsicSize.width) * (30.dp.toPx())
            )
            val position = (userPosition * scaleRatio)
            translate(left = position.x - (size.width/2), top = position.y - (size.height)){
                with(imageBitmap) {
                    draw(
                        size
                    )
                }
            }
        }
    }

    override fun renderObject(
        exhibit: Exhibit,
        roomOffset: Offset
    ) {
        exhibitsCoordinates[exhibit.id] =
            renderRectRelative(
                RectOptions(
                    fill = true,
                    basicSize = exhibit.size,
                    color = exhibit.color,
                    mapPosition = startOffset,
                    objectPosition = (exhibit.position + roomOffset),
                    mapScaleRatio = scaleRatio
                )
            )
    }

    override fun renderLine(startOffset: Offset, endOffset: Offset) {
        var start = (startOffset * scaleRatio) + this.startOffset
        var end = (endOffset * scaleRatio) + this.startOffset
        Log.d("render", "render line with positions $start - $end")
        val addSize = (lineWidth) / 2
        if (start.x == end.x) {
            if (start.y > end.y) {
                start += Offset(y = addSize, x = 0f)
                end -= Offset(y = addSize, x = 0f)
            } else {
                start -= Offset(y = addSize, x = 0f)
                end += Offset(y = addSize, x = 0f)
            }
        } else {
            if (start.x > end.x) {
                start += Offset(x = addSize, y = 0f)
                end -= Offset(x = addSize, y = 0f)
            } else {
                start -= Offset(x = addSize, y = 0f)
                end += Offset(x = addSize, y = 0f)
            }
        }
        drawScopeContext.drawLine(
            color = Color(0xFF7E7052),
            start = start,
            end = end,
            strokeWidth = lineWidth
        )
        if (minimalX == null || minimalX!! > min(start.x, end.x)) {
            minimalX = min(start.x, end.x)
        }
        if (maximalX == null || maximalX!! < max(start.x, end.x)) {
            maximalX = max(start.x, end.x)
        }
        if (minimalY == null || minimalY!! > min(start.y, end.y)) {
            minimalY = min(start.y, end.y)
        }
        if (maximalY == null || maximalY!! < max(start.y, end.y)) {
            maximalY = max(start.y, end.y)
        }
    }

    private fun renderRectRelative(rectOptions: RectOptions): ObjectCoordinates {
        with(rectOptions) {

            val xCoordinate = mapPosition.x + (objectPosition.x * mapScaleRatio)
            val yCoordinate = mapPosition.y + (objectPosition.y * mapScaleRatio)
            Log.d(
                "render",
                "render rect with positions $xCoordinate - $yCoordinate and ${xCoordinate + (basicSize.width * scaleRatio)} - ${yCoordinate + (basicSize.height * scaleRatio)}"
            )
            // save values to minimal/maximal variables for calculate map image size
            if (minimalX == null || minimalX!! > xCoordinate) {
                minimalX = xCoordinate
            }
            if (maximalX == null || maximalX!! < xCoordinate + (basicSize.width * scaleRatio)) {
                maximalX = xCoordinate + (basicSize.width * scaleRatio)
            }
            if (minimalY == null || minimalY!! > yCoordinate) {
                minimalY = yCoordinate
            }
            if (maximalY == null || maximalY!! < yCoordinate + (basicSize.height * scaleRatio)) {
                maximalY = yCoordinate + (basicSize.height * scaleRatio)
            }

            if (fill) {
                drawScopeContext.drawRect(
                    color = color,
                    size = basicSize * mapScaleRatio,
                    topLeft = Offset(xCoordinate, yCoordinate)
                )
            } else {
                if (strokeWidth != null) {
                    drawScopeContext.drawRect(
                        color = color,
                        style = Stroke(width = strokeWidth * mapScaleRatio),
                        size = basicSize * mapScaleRatio,
                        topLeft = Offset(xCoordinate, yCoordinate)
                    )
                } else {
                    throw IllegalStateException("Rendering error! Parameter fill set as false, but no value passed for strokeWidth")
                }
            }
            return ObjectCoordinates(
                start = xCoordinate,
                top = yCoordinate,
                end = xCoordinate + (basicSize.width * mapScaleRatio),
                bottom = yCoordinate + (basicSize.height * mapScaleRatio)
            )
        }
    }
}

data class ObjectCoordinates(
    val top: Float,
    val bottom: Float,
    val start: Float,
    val end: Float
)

data class RectOptions(
    val fill: Boolean,
    val strokeWidth: Float? = null,
    val color: Color,
    val basicSize: Size,
    val objectPosition: Offset,
    val mapPosition: Offset,
    val mapScaleRatio: Float
)

data class BasicMapOptions(
    val backgroundColor: Color = Color.Transparent,
    val roomStrokeColor: Color = Color(0x997E7052),
    val objectBasicColor: Color = Color(0xFFA5A68F),
    val userMarker: Int = R.drawable.ic_launcher_background,
    val userMarkerTint: Color = Color(0xFFA5A68F)
)