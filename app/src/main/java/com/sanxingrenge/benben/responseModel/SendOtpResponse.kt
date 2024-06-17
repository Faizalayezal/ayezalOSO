package com.sanxingrenge.benben.responseModel

//@SerializedName("body")
data class SendOtpResponse(
    val status: Boolean?,
    val otp: Int?,
    val message: String?,
    val user_id: Int?,
)