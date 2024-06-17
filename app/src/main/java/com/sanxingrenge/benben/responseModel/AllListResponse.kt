package com.sanxingrenge.benben.responseModel

import java.io.Serializable

//@SerializedName("body")
data class AllListResponse(
    val status: Boolean?,
    val data: AllListData?,
) : Serializable

data class AllListData(
    val country_list: List<AllListItem>?,
    val religion_list: List<AllListItem>?,
    val education_list: List<AllListItem>?,
    val marital_status_list: List<AllListItem>?,
    val kid_list: List<AllListItem>?,
    val zodiac_sign_list: List<AllListItem>?,
    val animal_sign_list: List<AllListItem>?,
    val interest_list: List<AllListItem>?,
    val relationship_list: List<AllListItem>?,
) : Serializable

data class AllListItem(
    val id: Int?,
    val name: String?,
    val image: String?,
    val title: String?,
) : Serializable

data class ZodiacSign(val name: String, val startDate: String, val endDate: String)
data class AnimalSign(val name: String, val startYear: Int)