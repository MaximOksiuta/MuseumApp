package com.startup.museumapp.data.api

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color


data class Marker(val x: Float, val y: Float)

data class Point(val x: Float, val y: Float)

data class Exhibit(val id: Int, val position: Offset, val size: Size, val color: Color)

data class MapInformation(val markers: Map<Int, Marker>, val exhibits: List<Exhibit>, val walls: List<Pair<Offset, Offset>>)

val mapInformation = MapInformation(
    markers = mapOf(
        1 to Marker(x = 0f, y = 0f),
        2 to Marker(x = 330f, y = 0f),
        3 to Marker(x = 165f, y = 250f)
    ),
    exhibits = listOf(
        Exhibit(
            id = 1,
            position = Offset(15f, 7.5f),
            size = Size(70f, 5f),
            color = Color(0xFFA5A68F)
        ),
        Exhibit(
            id = 2,
            position = Offset(123f, 7.5f),
            size = Size(70f, 5f),
            color = Color(0xFFA5A68F)
        ),
        Exhibit(
            id = 3,
            position = Offset(230f, 7.5f),
            size = Size(70f, 5f),
            color = Color(0xFFA5A68F)
        ),
        Exhibit(
            id = 4,
            position = Offset(325f, 10.5f),
            size = Size(5f, 50f),
            color = Color(0xFFA5A68F)
        ),
        Exhibit(
            id = 5,
            position = Offset(71f, 57.5f),
            size = Size(70f, 5f),
            color = Color(0xFFA5A68F)
        ),
        Exhibit(
            id = 6,
            position = Offset(75f, 104.5f),
            size = Size(5f, 30f),
            color = Color(0xFFA5A68F)
        ),
        Exhibit(
            id = 7,
            position = Offset(75f, 162.5f),
            size = Size(5f, 30f),
            color = Color(0xFFA5A68F)
        ),
        Exhibit(
            id = 8,
            position = Offset(324f, 122.5f),
            size = Size(5f, 50f),
            color = Color(0xFFA5A68F)
        ),
        Exhibit(
            id = 9,
            position = Offset(75f, 74.5f),
            size = Size(252f, 21f),
            color = Color(0xB3D39E84)
        ),
        Exhibit(
            id = -1,
            position = Offset(159f, 113.5f),
            size = Size(9f, 67f),
            color = Color(0xFF7E7052)
        ),
        Exhibit(
            id = -1,
            position = Offset(232f, 113.5f),
            size = Size(9f, 67f),
            color = Color(0xFF7E7052)
        ),
        Exhibit(
            id = -1,
            position = Offset(0f, 197.5f),
            size = Size(9f, 61f),
            color = Color(0xFFB27F5D)
        ),
        Exhibit(
            id = -1,
            position = Offset(330f, 197.5f),
            size = Size(9f, 61f),
            color = Color(0xFFB27F5D)
        )
    ),
    walls = listOf(
        Pair(Offset(4.5f, 0f), Offset(4.5f, 256.5f)),
        Pair(Offset(4.5f, 256.5f), Offset(334.5f, 256.5f)),
        Pair(Offset(334.5f, 256.5f), Offset(334.5f, 0f)),
        Pair(Offset(334.5f, 0f), Offset(4.5f, 0f)),
        Pair(Offset(69.5f, 67.5f), Offset(334f, 67.5f)),
        Pair(Offset(69.5f, 67.5f), Offset(69.5f, 198.5f))
    ),

)