package com.startup.museumapp.domain.useCases

import com.startup.museumapp.domain.dataModels.MuseumInfo
import com.startup.museumapp.domain.interfaces.MuseumRepository


class GetMuseumsListUseCase(private val museumRepository: MuseumRepository) {
    fun getMuseumsList(): List<MuseumInfo>{
        return museumRepository.getMuseumsList()
    }

    fun getLikedMuseumsList(): List<Int>{
        return museumRepository.getLikedMuseumsIds()
    }
}