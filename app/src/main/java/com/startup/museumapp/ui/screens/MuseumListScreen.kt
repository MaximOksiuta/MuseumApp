package com.startup.museumapp.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.startup.museumapp.R
import com.startup.museumapp.domain.dataModels.MuseumInfo
import com.startup.museumapp.ui.commonElements.museumItem
import com.startup.museumapp.ui.commonElements.standardShadow
import com.startup.museumapp.ui.theme.anonymousPro
import com.startup.museumapp.ui.viewModels.MuseumViewModel

@Composable
fun MuseumListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: MuseumViewModel
) {
    val museumsList by viewModel.museumInfo.collectAsState()
    MuseumList(museumsList) {
        viewModel.selectedMuseum = it
        navController.navigate("museumDetails")
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        var searchText by remember {
            mutableStateOf("")
        }
        TextField(
            value = searchText,
            onValueChange = {
                searchText = it
            },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
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
                .padding(top = 40.dp, start = 30.dp, end = 30.dp, bottom = 30.dp)
                .fillMaxWidth()
                .standardShadow()
//                .rsBlurShadow(
//                    radius = 20.dp,
//                    spread = 0.dp,
//                    offset = DpOffset(10.dp, 10.dp),
//                    shape = RoundedCornerShape(25.dp)
//                )
            ,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(
                            top = 5.dp,
                            bottom = 5.dp,
                            start = 20.dp,
                            end = 10.dp
                        )
                        .size(20.dp)
                )
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_clear),
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(top = 5.dp, bottom = 5.dp, end = 20.dp, start = 10.dp)
                        .clickable {
                            searchText = ""
                        }
                        .size(20.dp)
                )
            },
            placeholder = {
                Text(
                    text = "Введите название музея",
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    fontFamily = anonymousPro,
                    fontWeight = FontWeight(400),
                    color = Color(0xB3FFFFFF)
                )
            },
            textStyle = TextStyle(
                fontFamily = anonymousPro,
                fontSize = 14.sp,
                color = Color.White
            )
        )
    }
}

@Composable
fun MuseumList(museumsList: SnapshotStateList<MuseumInfo>, onClick: (info: MuseumInfo) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Spacer(modifier = Modifier.height(130.dp))
        }
        items(
            museumsList
        ) {
            museumItem(it.imageUrl, it.name) {
                onClick(it)
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

//data class MuseumInfoTesting(val imageUrl: String, val name: String)