package com.startup.museumapp.domain.dataModels

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthorInfo(val name: String, val imageUrl: String, val link: String): Parcelable
