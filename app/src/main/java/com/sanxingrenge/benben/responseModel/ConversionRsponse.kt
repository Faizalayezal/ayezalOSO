package com.sanxingrenge.benben.responseModel

data class ConversionRsponse(
    val status: Boolean?,
    val message: String?,
    val data: ArrayList<ConversionDataRsponse>?,
)

data class ConversionDataRsponse(
    val id: Int?,
    val image: String?,
    val name: String?,
    val last_message: String?,
    val last_message_date_time: String?,
    val unseen_messages_count: String?,
    val image_url: String?,
    val user_photos: ArrayList<usersphoto>?,

    )

