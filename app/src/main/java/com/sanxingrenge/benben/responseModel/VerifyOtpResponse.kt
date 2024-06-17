package com.sanxingrenge.benben.responseModel

data class VerifyOtpResponse(
    val status: Boolean?,
    val message: String?,
)

data class SkuDetails(
    val sku : String?,
    val price : String?,
    val description : String?,

)

