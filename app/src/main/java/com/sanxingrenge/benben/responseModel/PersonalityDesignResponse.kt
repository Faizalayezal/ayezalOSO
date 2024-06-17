package com.sanxingrenge.benben.responseModel


data class PersonalityDesignResponse(
    val status: Boolean?,
    val message: String?,
    val data: ArrayList<PersonalityDesignDataResponse>?,
)

data class PersonalityDesignDataResponse(

    val id: Int?,
    val title: String?,
    val icon: String?,
    val introduction: String?,
    val status: String?,
    val updated_at: String?,
    val created_at: String?,
)
