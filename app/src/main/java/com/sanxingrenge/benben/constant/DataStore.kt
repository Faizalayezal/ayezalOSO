package com.sanxingrenge.benben.constant

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sanxingrenge.benben.dataModel.FilterDataObject
import com.sanxingrenge.benben.dataModel.HeghLightObject
import com.sanxingrenge.benben.dataModel.LocationObject
import com.sanxingrenge.benben.dataModel.UserDataObject
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_datastore")

//private const val TAG = "dataStore"
class DataStoreManager(val context: Context) {
    companion object {
        //user's all data
        val U_IMAGE_PATH = stringPreferencesKey("U_IMAGE_PATH")
        val U_IMAGE_URL = stringPreferencesKey("U_IMAGE_URL")
        val U_IMAGE_ID = stringPreferencesKey("U_IMAGE_ID")

        val U_IMAGE_PATH22 = stringPreferencesKey("U_IMAGE_PATH22")
        val U_IMAGE_URL22 = stringPreferencesKey("U_IMAGE_URL22")
        val U_IMAGE_ID22 = stringPreferencesKey("U_IMAGE_ID22")

        val U_IMAGE_PATH33 = stringPreferencesKey("U_IMAGE_PATH33")
        val U_IMAGE_URL33 = stringPreferencesKey("U_IMAGE_URL33")
        val U_IMAGE_ID33 = stringPreferencesKey("U_IMAGE_ID33")

        val U_IMAGE_PATH44 = stringPreferencesKey("U_IMAGE_PATH44")
        val U_IMAGE_URL44 = stringPreferencesKey("U_IMAGE_URL44")
        val U_IMAGE_ID44 = stringPreferencesKey("U_IMAGE_ID44")


        val U_EMAIL = stringPreferencesKey("U_EMAIL")
        val U_PWD = stringPreferencesKey("U_PWD")
        val U_OTP = stringPreferencesKey("U_OTP")
        val U_BDAY = stringPreferencesKey("U_BDAY")
        val U_NAME = stringPreferencesKey("U_NAME")
        val U_BIO = stringPreferencesKey("U_BIO")
        val U_GOOGLE = stringPreferencesKey("U_GOOGLE")
        val U_FACEBOOK = stringPreferencesKey("U_FACEBOOK")
        val U_GENDER = stringPreferencesKey("U_GENDER")
        val U_RELIGION = stringPreferencesKey("U_RELIGION")
        val U_EDUCATION = stringPreferencesKey("U_EDUCATION")
        val U_OCCUPATION = stringPreferencesKey("U_OCCUPATION")
        val U_RELATIONSHIP = stringPreferencesKey("U_RELATIONSHIP")
        val U_MARITAL_STATUS = stringPreferencesKey("U_MARITAL_STATUS")
        val U_KIDS = stringPreferencesKey("U_KIDS")
        val U_ZODIAC = stringPreferencesKey("U_ZODIAC")
        val U_ANIMAL = stringPreferencesKey("U_ANIMAL")
        val U_HEIGHT = stringPreferencesKey("U_HEIGHT")
        val U_WEIGHT = stringPreferencesKey("U_WEIGHT")
        val U_ADDRESS = stringPreferencesKey("U_ADDRESS")
        val U_LAT = stringPreferencesKey("U_LAT")
        val U_LONG = stringPreferencesKey("U_LONG")
        val U_ID = stringPreferencesKey("U_ID")
        val U_AGE = stringPreferencesKey("U_AGE")
        val U_PLANTYPE = stringPreferencesKey("U_PLANTYPE")


        val INTEREST_ID_1 = stringPreferencesKey("INTEREST_ID_1")
        val INTEREST_ID_2 = stringPreferencesKey("INTEREST_ID_2")
        val INTEREST_ID_3 = stringPreferencesKey("INTEREST_ID_3")
        val INTEREST_ID_4 = stringPreferencesKey("INTEREST_ID_4")
        val INTEREST_ID_5 = stringPreferencesKey("INTEREST_ID_5")
        val INTEREST_LEVEL_1 = stringPreferencesKey("INTEREST_LEVEL_1")
        val INTEREST_LEVEL_2 = stringPreferencesKey("INTEREST_LEVEL_2")
        val INTEREST_LEVEL_3 = stringPreferencesKey("INTEREST_LEVEL_3")
        val INTEREST_LEVEL_4 = stringPreferencesKey("INTEREST_LEVEL_4")
        val INTEREST_LEVEL_5 = stringPreferencesKey("INTEREST_LEVEL_5")

        val TEMP_LOCATION_NAME = stringPreferencesKey("TEMP_LOCATION_NAME")
        val TEMP_LOCATION_LAT = stringPreferencesKey("TEMP_LOCATION_LAT")
        val TEMP_LOCATION_LONG = stringPreferencesKey("TEMP_LOCATION_LONG")

        val U_IMAGE_PATH1 = stringPreferencesKey("U_IMAGE_PATH1")
        val U_IMAGE_PATH2 = stringPreferencesKey("U_IMAGE_PATH2")
        val U_IMAGE_PATH3 = stringPreferencesKey("U_IMAGE_PATH3")
        val U_IMAGE_PATH4 = stringPreferencesKey("U_IMAGE_PATH4")
        val U_IMAGE_PATH5 = stringPreferencesKey("U_IMAGE_PATH5")
        val U_IMAGE_PATH6 = stringPreferencesKey("U_IMAGE_PATH6")

        val CurrantId = stringPreferencesKey("CurrantId")
        val OtherId = stringPreferencesKey("OtherId")



        //FilterData

        val F_GENDER = stringPreferencesKey("F_GENDER")
        val F_RELATIONSHIP = stringPreferencesKey("F_RELATIONSHIP")
        val F_ADDRESS = stringPreferencesKey("F_ADDRESS")
        val F_LAT = stringPreferencesKey("F_LAT")
        val F_LONG = stringPreferencesKey("F_LONG")
        val F_UID = stringPreferencesKey("F_UID")

        val F_INTEREST_ID_1 = stringPreferencesKey("F_INTEREST_ID_1")
        val F_INTEREST_ID_2 = stringPreferencesKey("F_INTEREST_ID_2")
        val F_INTEREST_ID_3 = stringPreferencesKey("F_INTEREST_ID_3")
        val F_INTEREST_ID_4 = stringPreferencesKey("F_INTEREST_ID_4")
        val F_INTEREST_ID_5 = stringPreferencesKey("F_INTEREST_ID_5")
        val F_INTEREST_LEVEL_1 = stringPreferencesKey("F_INTEREST_LEVEL_1")
        val F_INTEREST_LEVEL_2 = stringPreferencesKey("F_INTEREST_LEVEL_2")
        val F_INTEREST_LEVEL_3 = stringPreferencesKey("F_INTEREST_LEVEL_3")
        val F_INTEREST_LEVEL_4 = stringPreferencesKey("F_INTEREST_LEVEL_4")
        val F_INTEREST_LEVEL_5 = stringPreferencesKey("F_INTEREST_LEVEL_5")
        val F_TEMP_LOCATION_NAME = stringPreferencesKey("F_TEMP_LOCATION_NAME")
        val F_TEMP_LOCATION_LAT = stringPreferencesKey("F_TEMP_LOCATION_LAT")
        val F_TEMP_LOCATION_LONG = stringPreferencesKey("F_TEMP_LOCATION_LONG")

        //heighlighttext mate

        val DETAIL_ZODI = stringPreferencesKey("DETAIL_ZODI")
        val DETAIL_ANIM = stringPreferencesKey("DETAIL_ZODI")

        val PRO_ZODI = stringPreferencesKey("PRO_ZODI")
        val PRO_ANIM = stringPreferencesKey("PRO_ANIM")



    }
}


suspend fun Context.dataStoreSetGroupPopup(data: HeghLightObject) {
    dataStore.edit {
        it[DataStoreManager.DETAIL_ZODI] = data.DETAIL_ZODI ?: ""
        it[DataStoreManager.DETAIL_ANIM] = data.DETAIL_ANIM ?: ""
        it[DataStoreManager.PRO_ZODI] = data.PRO_ZODI ?: ""
        it[DataStoreManager.PRO_ANIM] = data.PRO_ANIM ?: ""
    }
}

fun Context.dataStoreGetGroupPopup() = dataStore.data.map {
    HeghLightObject(
        DETAIL_ZODI = it[DataStoreManager.DETAIL_ZODI] ?: "",
        DETAIL_ANIM = it[DataStoreManager.DETAIL_ANIM] ?: "",
        PRO_ZODI = it[DataStoreManager.PRO_ZODI] ?: "",
        PRO_ANIM = it[DataStoreManager.PRO_ANIM] ?: "",

    )
}

suspend fun Context.dataStoreSetTempLocation(name: String, lat: String, long: String, tag: Int) =
    dataStore.edit {
        it[DataStoreManager.TEMP_LOCATION_NAME] = name
        it[DataStoreManager.TEMP_LOCATION_LAT] = lat
        it[DataStoreManager.TEMP_LOCATION_LONG] = long
        Log.d(TAG, "dataStoreSetTempLocation: testSavingLocation>>$tag>>$name")
    }

fun Context.dataStoreGetTempLocation() = dataStore.data.map {
    LocationObject(
        name = it[DataStoreManager.TEMP_LOCATION_NAME] ?: "",
        lat = it[DataStoreManager.TEMP_LOCATION_LAT] ?: "",
        long = it[DataStoreManager.TEMP_LOCATION_LONG] ?: "",
    )
}

suspend fun Context.dataStoreSetUserData(data: UserDataObject, tag: String) = dataStore.edit {
    Log.d(TAG, "dataStoreSetUserDatasdd: "+data)
    it[DataStoreManager.U_EMAIL] = data.email ?: ""
    it[DataStoreManager.U_PWD] = data.pwd ?: ""
    it[DataStoreManager.U_OTP] = data.otp ?: ""
    it[DataStoreManager.U_BDAY] = data.bday ?: ""
    it[DataStoreManager.U_NAME] = data.name ?: ""
    it[DataStoreManager.U_GOOGLE] = data.google ?: ""
    it[DataStoreManager.U_FACEBOOK] = data.facebook ?: ""
    it[DataStoreManager.U_BIO] = data.bio ?: ""
    it[DataStoreManager.U_GENDER] = data.gender ?: ""
    it[DataStoreManager.U_RELIGION] = data.religion ?: ""
    it[DataStoreManager.U_EDUCATION] = data.education ?: ""
    it[DataStoreManager.U_OCCUPATION] = data.occupation ?: ""
    it[DataStoreManager.U_RELATIONSHIP] = data.relationship ?: ""
    it[DataStoreManager.U_MARITAL_STATUS] = data.maritalStatus ?: ""
    it[DataStoreManager.U_KIDS] = data.kids ?: ""
    it[DataStoreManager.U_ZODIAC] = data.zodiacSign ?: ""
    it[DataStoreManager.U_ANIMAL] = data.animalSign ?: ""
    it[DataStoreManager.U_HEIGHT] = data.height ?: ""
    it[DataStoreManager.U_WEIGHT] = data.weight ?: ""


    it[DataStoreManager.U_IMAGE_URL] = data.imageUrl ?: ""
    if ((data.imagePath ?: "") != "") it[DataStoreManager.U_IMAGE_PATH] = data.imagePath ?: ""
    it[DataStoreManager.U_IMAGE_ID] = data.imageId ?: ""

    it[DataStoreManager.U_IMAGE_URL22] = data.imageUrl22 ?: ""
    if ((data.imagePath22 ?: "") != "") it[DataStoreManager.U_IMAGE_PATH22] = data.imagePath22 ?: ""
    it[DataStoreManager.U_IMAGE_ID22] = data.imageId22 ?: ""

    it[DataStoreManager.U_IMAGE_URL33] = data.imageUrl33 ?: ""
    if ((data.imagePath33 ?: "") != "") it[DataStoreManager.U_IMAGE_PATH33] = data.imagePath33 ?: ""
    it[DataStoreManager.U_IMAGE_ID33] = data.imageId33 ?: ""

    it[DataStoreManager.U_IMAGE_URL44] = data.imageUrl44 ?: ""
    if ((data.imagePath44 ?: "") != "") it[DataStoreManager.U_IMAGE_PATH44] = data.imagePath44 ?: ""
    it[DataStoreManager.U_IMAGE_ID44] = data.imageId44 ?: ""

    it[DataStoreManager.INTEREST_ID_1] = data.interestId1 ?: ""
    it[DataStoreManager.INTEREST_ID_2] = data.interestId2 ?: ""
    it[DataStoreManager.INTEREST_ID_3] = data.interestId3 ?: ""
    it[DataStoreManager.INTEREST_ID_4] = data.interestId4 ?: ""
    it[DataStoreManager.INTEREST_ID_5] = data.interestId5 ?: ""
    it[DataStoreManager.INTEREST_LEVEL_1] = data.interestLevel1 ?: ""
    it[DataStoreManager.INTEREST_LEVEL_2] = data.interestLevel2 ?: ""
    it[DataStoreManager.INTEREST_LEVEL_3] = data.interestLevel3 ?: ""
    it[DataStoreManager.INTEREST_LEVEL_4] = data.interestLevel4 ?: ""
    it[DataStoreManager.INTEREST_LEVEL_5] = data.interestLevel5 ?: ""
    it[DataStoreManager.U_ADDRESS] = data.address ?: ""
    it[DataStoreManager.U_LAT] = data.latitude ?: ""
    it[DataStoreManager.U_LONG] = data.longitude ?: ""
    it[DataStoreManager.U_ID] = data.userId.toString()
    it[DataStoreManager.U_AGE] = data.age ?: ""
    it[DataStoreManager.U_PLANTYPE] = data.plan_type ?: ""

    if ((data.imagePath1 ?: "") != "") it[DataStoreManager.U_IMAGE_PATH1] = data.imagePath1 ?: ""
    if ((data.imagePath2 ?: "") != "") it[DataStoreManager.U_IMAGE_PATH2] = data.imagePath2 ?: ""
    if ((data.imagePath3 ?: "") != "") it[DataStoreManager.U_IMAGE_PATH3] = data.imagePath3 ?: ""
    if ((data.imagePath4 ?: "") != "") it[DataStoreManager.U_IMAGE_PATH4] = data.imagePath4 ?: ""
    if ((data.imagePath5 ?: "") != "") it[DataStoreManager.U_IMAGE_PATH5] = data.imagePath5 ?: ""
    if ((data.imagePath6 ?: "") != "") it[DataStoreManager.U_IMAGE_PATH6] = data.imagePath6 ?: ""


    Log.d(
        TAG,
        "dataStoreSetUserData: testSavingPrivateData>>+++++++++++++++++++++++++++++++++++++++++++++++++++<<$tag"
    )
    Log.d(
        TAG,
        "dataStoreSetUserData: testSavingPrivateData>>${data.interestId1}>>${data.interestId2}>>${data.interestId3}>>${data.interestId4}>>${data.interestId5}"
    )
    Log.d(
        TAG,
        "dataStoreSetUserData: testSavingPrivateData>>${data.interestLevel1}>>${data.interestLevel2}>>${data.interestLevel3}>>${data.interestLevel4}>>${data.interestLevel5}"
    )
}

private const val TAG = "DataStore"
suspend fun Context.dataStoreClearAllData() = dataStore.edit {
    it.clear()
}

fun Context.dataStoreGetUserData() = dataStore.data.map {
    UserDataObject(
        email = it[DataStoreManager.U_EMAIL] ?: "",
        pwd = it[DataStoreManager.U_PWD] ?: "",
        otp = it[DataStoreManager.U_OTP] ?: "",
        bday = it[DataStoreManager.U_BDAY] ?: "",
        name = it[DataStoreManager.U_NAME] ?: "",
        bio = it[DataStoreManager.U_BIO] ?: "",
        facebook = it[DataStoreManager.U_FACEBOOK] ?: "",
        google = it[DataStoreManager.U_GOOGLE] ?: "",
        gender = it[DataStoreManager.U_GENDER] ?: "",
        religion = it[DataStoreManager.U_RELIGION] ?: "",
        education = it[DataStoreManager.U_EDUCATION] ?: "",
        occupation = it[DataStoreManager.U_OCCUPATION] ?: "",
        relationship = it[DataStoreManager.U_RELATIONSHIP] ?: "",
        maritalStatus = it[DataStoreManager.U_MARITAL_STATUS] ?: "",
        kids = it[DataStoreManager.U_KIDS] ?: "",
        zodiacSign = it[DataStoreManager.U_ZODIAC] ?: "",
        animalSign = it[DataStoreManager.U_ANIMAL] ?: "",
        height = it[DataStoreManager.U_HEIGHT] ?: "",
        weight = it[DataStoreManager.U_WEIGHT] ?: "",
        interestId1 = it[DataStoreManager.INTEREST_ID_1] ?: "",
        interestId2 = it[DataStoreManager.INTEREST_ID_2] ?: "",
        interestId3 = it[DataStoreManager.INTEREST_ID_3] ?: "",
        interestId4 = it[DataStoreManager.INTEREST_ID_4] ?: "",
        interestId5 = it[DataStoreManager.INTEREST_ID_5] ?: "",
        interestLevel1 = it[DataStoreManager.INTEREST_LEVEL_1] ?: "",
        interestLevel2 = it[DataStoreManager.INTEREST_LEVEL_2] ?: "",
        interestLevel3 = it[DataStoreManager.INTEREST_LEVEL_3] ?: "",
        interestLevel4 = it[DataStoreManager.INTEREST_LEVEL_4] ?: "",
        interestLevel5 = it[DataStoreManager.INTEREST_LEVEL_5] ?: "",
        address = it[DataStoreManager.U_ADDRESS] ?: "",
        latitude = it[DataStoreManager.U_LAT] ?: "",
        longitude = it[DataStoreManager.U_LONG] ?: "",

        imagePath = it[DataStoreManager.U_IMAGE_PATH] ?: "",
        imageUrl = it[DataStoreManager.U_IMAGE_URL] ?: "",
        imageId = it[DataStoreManager.U_IMAGE_ID] ?: "",

        imagePath22 = it[DataStoreManager.U_IMAGE_PATH22] ?: "",
        imageUrl22 = it[DataStoreManager.U_IMAGE_URL22] ?: "",
        imageId22 = it[DataStoreManager.U_IMAGE_ID22] ?: "",

        imagePath33 = it[DataStoreManager.U_IMAGE_PATH33] ?: "",
        imageUrl33 = it[DataStoreManager.U_IMAGE_URL33] ?: "",
        imageId33 = it[DataStoreManager.U_IMAGE_ID33] ?: "",

        imagePath44 = it[DataStoreManager.U_IMAGE_PATH44] ?: "",
        imageUrl44 = it[DataStoreManager.U_IMAGE_URL44] ?: "",
        imageId44 = it[DataStoreManager.U_IMAGE_ID44] ?: "",

        userId = it[DataStoreManager.U_ID]?.toIntOrNull(),
        age = it[DataStoreManager.U_AGE] ?: "",
        plan_type = it[DataStoreManager.U_PLANTYPE] ?: "",

        imagePath1 = it[DataStoreManager.U_IMAGE_PATH1] ?: "",
        imagePath2 = it[DataStoreManager.U_IMAGE_PATH2] ?: "",
        imagePath3 = it[DataStoreManager.U_IMAGE_PATH3] ?: "",
        imagePath4 = it[DataStoreManager.U_IMAGE_PATH4] ?: "",
        imagePath5 = it[DataStoreManager.U_IMAGE_PATH5] ?: "",
        imagePath6 = it[DataStoreManager.U_IMAGE_PATH6] ?: "",


        )
}


suspend fun Context.filterDataStoreSetData(data: FilterDataObject, tag: String) = dataStore.edit {
    it[DataStoreManager.F_GENDER] = data.gender ?: ""
    it[DataStoreManager.F_RELATIONSHIP] = data.relationship?:""
    it[DataStoreManager.F_INTEREST_ID_1] = data.interestId1 ?: ""
    it[DataStoreManager.F_INTEREST_ID_2] = data.interestId2 ?: ""
    it[DataStoreManager.F_INTEREST_ID_3] = data.interestId3 ?: ""
    it[DataStoreManager.F_INTEREST_ID_4] = data.interestId4 ?: ""
    it[DataStoreManager.F_INTEREST_ID_5] = data.interestId5 ?: ""
    it[DataStoreManager.F_INTEREST_LEVEL_1] = data.interestLevel1 ?: ""
    it[DataStoreManager.F_INTEREST_LEVEL_2] = data.interestLevel2 ?: ""
    it[DataStoreManager.F_INTEREST_LEVEL_3] = data.interestLevel3 ?: ""
    it[DataStoreManager.F_INTEREST_LEVEL_4] = data.interestLevel4 ?: ""
    it[DataStoreManager.F_INTEREST_LEVEL_5] = data.interestLevel5 ?: ""
    it[DataStoreManager.F_ADDRESS] = data.address ?: ""
    it[DataStoreManager.F_LAT] = data.latitude ?: ""
    it[DataStoreManager.F_LONG] = data.longitude ?: ""
    it[DataStoreManager.F_UID] = data.userId.toString()

    Log.d(
        TAG,
        "dataStoreSetUserData: testSavingPrivateData>>+++++++++++++++++++++++++++++++++++++++++++++++++++<<$tag"
    )
    Log.d(
        TAG,
        "dataStoreSetUserData: testSavingPrivateData filter>>${data.interestId1}>>${data.interestId2}>>${data.interestId3}>>${data.relationship}>>${data.gender}"
    )
    Log.d(
        TAG,
        "dataStoreSetUserData: testSavingPrivateData>>${data.interestLevel1}>>${data.interestLevel2}>>${data.interestLevel3}>>${data.interestLevel4}>>${data.interestLevel5}"
    )
}

fun Context.filterDataStoreGetUserData() = dataStore.data.map {
    FilterDataObject(
        gender = it[DataStoreManager.F_GENDER] ?: "",
        userId = it[DataStoreManager.F_UID]?.toIntOrNull(),
        relationship = it[DataStoreManager.F_RELATIONSHIP] ?:"",
        interestId1 = it[DataStoreManager.F_INTEREST_ID_1] ?: "",
        interestId2 = it[DataStoreManager.F_INTEREST_ID_2] ?: "",
        interestId3 = it[DataStoreManager.F_INTEREST_ID_3] ?: "",
        interestId4 = it[DataStoreManager.F_INTEREST_ID_4] ?: "",
        interestId5 = it[DataStoreManager.F_INTEREST_ID_5] ?: "",
        interestLevel1 = it[DataStoreManager.F_INTEREST_LEVEL_1] ?: "",
        interestLevel2 = it[DataStoreManager.F_INTEREST_LEVEL_2] ?: "",
        interestLevel3 = it[DataStoreManager.F_INTEREST_LEVEL_3] ?: "",
        interestLevel4 = it[DataStoreManager.F_INTEREST_LEVEL_4] ?: "",
        interestLevel5 = it[DataStoreManager.F_INTEREST_LEVEL_5] ?: "",
        address = it[DataStoreManager.F_ADDRESS] ?: "",
        latitude = it[DataStoreManager.F_LAT] ?: "",
        longitude = it[DataStoreManager.F_LONG] ?: "",
    )
}

suspend fun Context.filterDataStoreSetTempLocation(
    name: String,
    lat: String,
    long: String,
    tag: Int
) = dataStore.edit {
    it[DataStoreManager.F_TEMP_LOCATION_NAME] = name
    it[DataStoreManager.F_TEMP_LOCATION_LAT] = lat
    it[DataStoreManager.F_TEMP_LOCATION_LONG] = long
    Log.d(TAG, "dataStoreSetTempLocation: testSavingLocation>>$tag>>$name")
}

fun Context.filterDataStoreGetTempLocation() = dataStore.data.map {
    LocationObject(
        name = it[DataStoreManager.F_TEMP_LOCATION_NAME] ?: "",
        lat = it[DataStoreManager.F_TEMP_LOCATION_LAT] ?: "",
        long = it[DataStoreManager.F_TEMP_LOCATION_LONG] ?: "",
    )
}









