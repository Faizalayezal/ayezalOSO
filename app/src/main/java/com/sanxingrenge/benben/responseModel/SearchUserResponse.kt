package com.sanxingrenge.benben.responseModel

data class SearchUserResponse(
    val status: Boolean?,
    val message: String?,
    val data: ArrayList<SearchUserResponseData>?,
)

data class SearchUserResponseData(
    val id: Int?,
    val email: String?,
    val name: String?,
    val gender: String?,
    val image: String?,
    val zodiac_sign_id: Int?,
    val animal_sign_id: Int?,
    val latitude: String?,
    val longitude: String?,
    val age: String?,
    val gender_image: String?,
    val distance: String?,
    val zodiac_sign: searchSignList?,
    val animal_sign: searchSignList?,
)
data class searchSignList(
    val id: Int?,
    val title: String?,
    val image: String?,
    val image_full_path: String?,
)

