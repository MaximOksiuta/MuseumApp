package com.startup.museumapp.domain.dataModels

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExhibitInfo(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val description: String,
    val authorInfo: AuthorInfo,
    val extensionInfo: ExtensionInfo,
    val extensionLinks: List<ExtensionLinks>,
    val record: Int
) : Parcelable
