package com.startup.museumapp.domain.dataModels

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MuseumInfo(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val description: String,
    val exhibits: List<ExhibitInfo>,
    val yandexMaps: String
): Parcelable
