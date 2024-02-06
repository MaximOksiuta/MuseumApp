package com.startup.museumapp.ui.commonElements

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.startup.museumapp.R
import com.startup.museumapp.ui.theme.anonymousPro

@Composable
fun museumItem(imageUrl: String, name: String, onClick: () -> Unit) {
    val padding = 30.dp
    val configuration = LocalConfiguration.current
    val height = configuration.screenHeightDp
    val width = configuration.screenWidthDp

    Card(
        shape = RoundedCornerShape(25.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF7E7052)
        ),

        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = padding)
                .shadow(20.dp)
//            .rsBlurShadow(
//                radius = 20.dp,
//                spread = 0.dp,
//                offset = DpOffset(10.dp, 10.dp),
//                shape = RoundedCornerShape(25.dp)
//            )

        ,
        onClick = {
            onClick()
        }
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(((width - 60) / 2).dp),
            contentScale = ContentScale.Crop
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = name,
                style = TextStyle(
                    fontSize = 17.sp,
                    lineHeight = 22.sp,
                    fontFamily = anonymousPro,
                    fontWeight = FontWeight(400),
                    color = Color(0xE5FFFFFF),
                ),
                modifier = Modifier
                    .padding(top = 10.dp, start = 20.dp, bottom = 15.dp)
                    .fillMaxWidth(0.6f)
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow),
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.padding(end = 20.dp)
            )
        }
    }
}