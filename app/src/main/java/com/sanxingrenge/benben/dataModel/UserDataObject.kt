package com.sanxingrenge.benben.dataModel


data class UserDataObject(
    var userId: Int? = null,
    val email: String? = null,
    val emailIsError: Boolean? = null,

    val pwd: String? = null,
    val otp: String? = null,
    val pwdIsError: Boolean? = null,

    val bday: String? = null,
    val age: String? = null,
    val bdayIsError: Boolean? = null,

    val name: String? = null,
    val nameIsError: Boolean? = null,

    val bio: String? = null,
    val bioIsError: Boolean? = null,

    val google: String? = null,

    val facebook: String? = null,


    val gender: String? = "male",
    val genderIsError: Boolean? = null,

    val religion: String? = null,
    val religionIsError: Boolean? = null,

    val education: String? = null,
    val educationIsError: Boolean? = null,

    val occupation: String? = null,
    val occupationIsError: Boolean? = null,

    val relationship: String? = null,
    val relationshipIsError: Boolean? = null,

    val maritalStatus: String? = null,
    val maritalStatusIsError: Boolean? = null,

    val kids: String? = null,
    val kidsIsError: Boolean? = null,

    val zodiacSign: String? = null,
    val zodiacSignError: Boolean? = null,

    val animalSign: String? = null,
    val animalSignError: Boolean? = null,


    val height: String? = null,
    val weight: String? = null,

    val imagePath: String? = null,
    var imageUrl: String? = null,
    val imageId: String? = null,

    val imagePath22: String? = null,
    var imageUrl22: String? = null,
    val imageId22: String? = null,

    val imagePath33: String? = null,
    var imageUrl33: String? = null,
    val imageId33: String? = null,

    val imagePath44: String? = null,
    var imageUrl44: String? = null,
    val imageId44: String? = null,

    val interestId1: String? = null,
    val interestId2: String? = null,
    val interestId3: String? = null,
    val interestId4: String? = null,
    val interestId5: String? = null,

    val interestLevel1: String? = null,
    val interestLevel2: String? = null,
    val interestLevel3: String? = null,
    val interestLevel4: String? = null,
    val interestLevel5: String? = null,

    val address: String? = null,
    val latitude: String? = null,
    val longitude: String? = null,


    val imagePath1: String? = null,
    val imagePath2: String? = null,
    val imagePath3: String? = null,
    val imagePath4: String? = null,
    val imagePath5: String? = null,
    val imagePath6: String? = null,

    val plan_type: String? = null,

    val count: Int = 0,
)

data class SubscriptionListLocal(
    val id: Int?,
    val user_id: Int?,
    val start_date: String?,
    val end_date: String?,
    val plan_type: String?,
    val amount: Int?,
    val transaction_id: Int?,
    val updated_at: String?,
    val created_at: String?,
)

data class HeghLightObject(
    val DETAIL_ZODI: String? = null,
    val DETAIL_ANIM: String? = null,
    val PRO_ZODI: String? = null,
    val PRO_ANIM: String? = null,

    )
