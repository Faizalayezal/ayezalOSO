package com.sanxingrenge.benben.responseModel


data class ConstellationResponse(
    val status: Boolean?,
    val message: String?,
    val data: ArrayList<ConstellationDataResponse>?,
)

data class ConstellationDataResponse(

    val id: Int?,
    val title: String?,
    val icon: String?,
    val thumbnail: String?,
    val thumbnail_title: String?,
    val ruling_planet_icon: String?,
    val ruling_planet_title: String?,
    val element_icon: String?,
    val element_title: String?,
    val key_words: String?,
    val key_trait: String?,
    val lucky_number: String?,
    val lucky_color: String?,
    val occupation: String?,
    val advantage: String?,
    val weakness: String?,
    val introduction: String?,
    val symbol: String?,
    val birthdate: String?,
    val flower: String?,
    val ruling_house: String?,
    val polarity: String?,
    val quality: String?,
    val description: String?,
    val updated_at: String?,
    val created_at: String?,
    val jewelry_icon: ArrayList<JewelryIconList>?,
    val best_matches_images: ArrayList<MatchesImageList>?,
)

data class JewelryIconList(

    val id: Int?,
    val constellation_id: Int?,
    val icon: String?,
    val title: String?,
    val updated_at: String?,
    val created_at: String?,
)

data class MatchesImageList(

    val id: Int?,
    val constellation_id: Int?,
    val image: String?,
    val updated_at: String?,
    val created_at: String?,
)
