package com.sanxingrenge.benben.dataModel

data class SignVideoCallResponse(
    val status: Boolean,
    val message: String?,
    val data: SignVideoCallDataResponse?,
)

data class SignVideoCallDataResponse(
    val sender_id: String?,
    val receiver_id: String?,
    val receiver_u_id: String?,
    val channel_name: String?,
    val receiver_token: String?,
    val sender_name: String?,
    val sender_image: String?,
    val image: String?,
    val userIdReceiver: String?,
    val roleReceiver: String?,
    val rtcTokenReceiver: String?,

    )


data class Message(
    val sender_id: String,
    val receiver_id: String,
    val receiver_u_id: String,
    val channel_name: String,
    val receiver_token: String,
    val sender_name: String,
    val sender_image: String,
    val image: String,
    val userIdReceiver: String,
    val roleReceiver: String,
    val rtcTokenReceiver: String,
)