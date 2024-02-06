package com.startup.museumapp.domain.dataModels

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExtensionInfo(val infoList: List<Pair<String, String>>): Parcelable