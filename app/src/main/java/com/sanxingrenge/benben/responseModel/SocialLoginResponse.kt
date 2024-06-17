package com.sanxingrenge.benben.responseModel
data class SocialLoginResponse(
    val status: Boolean,
    val message: String?,
    val is_user_exist: Int?,
    val data: SignInEmailPasswordDataResponse?,
)




