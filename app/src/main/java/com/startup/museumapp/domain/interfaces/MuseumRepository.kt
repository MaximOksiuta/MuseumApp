package com.startup.museumapp.domain.interfaces

import com.startup.museumapp.domain.dataModels.MuseumInfo

interface MuseumRepository {

    fun getLikedMuseumsIds(): List<Int>
    fun getMuseumsList(): List<MuseumInfo>
}