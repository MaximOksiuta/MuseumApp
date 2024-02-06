package com.startup.museumapp.data.repositories

import com.startup.museumapp.data.localSaver.SharedPrefs
import com.startup.museumapp.domain.dataModels.AuthorInfo
import com.startup.museumapp.domain.dataModels.ExhibitInfo
import com.startup.museumapp.domain.dataModels.ExtensionInfo
import com.startup.museumapp.domain.dataModels.MuseumInfo
import com.startup.museumapp.domain.interfaces.MuseumRepository

class MuseumRepositoryImpl (private val prefs: SharedPrefs) : MuseumRepository {
    override fun getLikedMuseumsIds(): List<Int> = prefs.likedMuseums

    override fun getMuseumsList(): List<MuseumInfo> {
        return listOf(
            MuseumInfo(
                id = 0,
                name = "Государственный музей им А.С.Пушкина",
                imageUrl = "https://kremltour.ru/wp-content/uploads/2023/04/tretyakovskaya-galereya-v-moskve-ekskursii.jpg",
                description = "Российский государственный художественный музей, созданный на основе исторических коллекций купцов братьев Павла и Сергея Михайловичей Третьяковых.  Одно из крупнейших в мире собраний русского изобразительного искусства.",
                exhibits = listOf(
                    ExhibitInfo(
                        id = 1,
                        name = "Черный квадрат",
                        imageUrl = "https://webpulse.imgsmail.ru/imgpreview?key=pulse_cabinet-image-6d479a37-d07d-4fe1-9dc2-3836cd38c997&mb=webpulse",
                        authorInfo = AuthorInfo(name = "Казимир Малевич", imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/63/Casimir_Malevich_photo.jpg/440px-Casimir_Malevich_photo.jpg", link = ""),
                        description = "Это одна из самых обсуждаемых и самых известных картин в мировом искусстве. Входит в цикл супрематических работ Малевича, в которых художник исследовал базовые возможности цвета и композиции.",
                        extensionInfo = ExtensionInfo(
                            emptyList()
                        ),
                        extensionLinks = emptyList(),
                        record = 0
                    ),

                ),
                yandexMaps = "https://yandex.ru/maps/-/CDuPqOpp"
            ),
            MuseumInfo(
                id = 1,
                name = "Государственный музей им А.С.Пушкина",
                imageUrl = "https://kremltour.ru/wp-content/uploads/2023/04/tretyakovskaya-galereya-v-moskve-ekskursii.jpg",
                description = "Российский государственный художественный музей, созданный на основе исторических коллекций купцов братьев Павла и Сергея Михайловичей Третьяковых.  Одно из крупнейших в мире собраний русского изобразительного искусства.",
                exhibits = emptyList(),
                yandexMaps = "https://yandex.ru/maps/-/CDuPqOpp"
            ),
            MuseumInfo(
                id = 2,
                name = "Государственный музей им А.С.Пушкина",
                imageUrl = "https://kremltour.ru/wp-content/uploads/2023/04/tretyakovskaya-galereya-v-moskve-ekskursii.jpg",
                description = "Российский государственный художественный музей, созданный на основе исторических коллекций купцов братьев Павла и Сергея Михайловичей Третьяковых.  Одно из крупнейших в мире собраний русского изобразительного искусства.",
                exhibits = emptyList(),
                yandexMaps = "https://yandex.ru/maps/-/CDuPqOpp"
            ), MuseumInfo(
                id = 3,
                name = "Государственный музей им А.С.Пушкина",
                imageUrl = "https://kremltour.ru/wp-content/uploads/2023/04/tretyakovskaya-galereya-v-moskve-ekskursii.jpg",
                description = "Российский государственный художественный музей, созданный на основе исторических коллекций купцов братьев Павла и Сергея Михайловичей Третьяковых.  Одно из крупнейших в мире собраний русского изобразительного искусства.",
                exhibits = emptyList(),
                yandexMaps = "https://yandex.ru/maps/-/CDuPqOpp"
            ), MuseumInfo(
                id = 4,
                name = "Государственный музей им А.С.Пушкина",
                imageUrl = "https://kremltour.ru/wp-content/uploads/2023/04/tretyakovskaya-galereya-v-moskve-ekskursii.jpg",
                description = "Российский государственный художественный музей, созданный на основе исторических коллекций купцов братьев Павла и Сергея Михайловичей Третьяковых.  Одно из крупнейших в мире собраний русского изобразительного искусства.",
                exhibits = emptyList(),
                yandexMaps = "https://yandex.ru/maps/-/CDuPqOpp"
            )
        )

    }
}