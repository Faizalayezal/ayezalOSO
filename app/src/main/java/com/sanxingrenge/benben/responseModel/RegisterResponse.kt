package com.sanxingrenge.benben.responseModel

data class RegisterResponse(
    val status: Boolean?,
    val message: String?,
    val data: RegisterDataResponse?,
)

data class RegisterDataResponse(
    val id: Int?,
    val email: String?,
    val password: String?,
    val name: String?,
    val birthdate: String?,
    val age: Int?,
    val register_otp: String?,
    val religion_id: String?,
    val gender: String?,
    val education_id: String?,
    val occupation: String?,
    val height: String?,
    val weight: String?,
    val relationship_id: String?,
    val marital_status_id: String?,
    val kid_id: String?,
    val zodiac_sign_id: String?,
    val animal_sign_id: String?,
    val address: String?,
    val bio: String?,
    val latitude: String?,
    val longitude: String?,
    val image: String?,
    val updated_at: String?,
    val created_at: String?,
    val user_photos: ArrayList<usersphoto>?,

    )
