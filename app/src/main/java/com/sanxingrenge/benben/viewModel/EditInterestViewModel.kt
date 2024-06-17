package com.sanxingrenge.benben.viewModel

import android.content.Context
import android.util.Log
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.text.toUpperCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import com.sanxingrenge.benben.constant.MainInterestLevelList
import com.sanxingrenge.benben.constant.dataStoreGetTempLocation
import com.sanxingrenge.benben.constant.dataStoreGetUserData
import com.sanxingrenge.benben.constant.dataStoreSetTempLocation
import com.sanxingrenge.benben.constant.dataStoreSetUserData
import com.sanxingrenge.benben.retrofilt.MainRepo
import com.sanxingrenge.benben.retrofilt.Resource
import com.sanxingrenge.benben.uiComponents.InterestChildListItem
import com.sanxingrenge.benben.uiComponents.InterestGridItem
import com.sanxingrenge.benben.uiComponents.InterestNestedItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

data class InterestDataModal(
    val interestId1: String = "",
    val interestId2: String = "",
    val interestId3: String = "",
    val interestId4: String = "",
    val interestId5: String = "",

    val interestLevel1: String = "",
    val interestLevel2: String = "",
    val interestLevel3: String = "",
    val interestLevel4: String = "",
    val interestLevel5: String = "",
)

@HiltViewModel
class EditInterestViewModel @Inject constructor(private val mainRepo: MainRepo) : ViewModel() {

    private val _interestLiveData = MutableStateFlow(InterestDataModal())
    private var interestLiveData: StateFlow<InterestDataModal> = _interestLiveData

    private var job2: Job? = null
    fun updateInterestList(gridItems: List<InterestGridItem>, context: Context) {

        job2?.cancel()
        job2 = viewModelScope.launch {

            val localList = MainInterestLevelList

            var selectedItem1Id = ""
            var selectedItem2Id = ""
            var selectedItem3Id = ""
            var selectedItem4Id = ""
            var selectedItem5Id = ""

            gridItems.forEachIndexed { index, interestGridItem ->

                val foundItem = localList.find { it.name == interestGridItem.name }

                when (index) {
                    0 -> selectedItem1Id = foundItem?.id.toString()
                    1 -> selectedItem2Id = foundItem?.id.toString()
                    2 -> selectedItem3Id = foundItem?.id.toString()
                    3 -> selectedItem4Id = foundItem?.id.toString()
                    4 -> selectedItem5Id = foundItem?.id.toString()
                }
            }

            val newDataObj = InterestDataModal(
                interestId1 = selectedItem1Id,
                interestId2 = selectedItem2Id,
                interestId3 = selectedItem3Id,
                interestId4 = selectedItem4Id,
                interestId5 = selectedItem5Id,
            )

            Log.d(TAG, "updateInterestList123: " + newDataObj)





            _interestLiveData.value = newDataObj
        }
    }


    fun getSelectedInterestList(onResponse: (ArrayList<InterestChildListItem>) -> Unit) =
        viewModelScope.launch {
            val list = ArrayList<InterestChildListItem>()

            Log.d(TAG, "getSelectedInterestList:966 " + interestLiveData.value.interestId1)
            Log.d(TAG, "getSelectedInterestList:977 " + interestLiveData.value.interestLevel1)
            val interestId1 = interestLiveData.value.interestId1
            val interestId2 = interestLiveData.value.interestId2
            val interestId3 = interestLiveData.value.interestId3
            val interestId4 = interestLiveData.value.interestId4
            val interestId5 = interestLiveData.value.interestId5

            val interestLevel1 = interestLiveData.value.interestLevel1
            val interestLevel2 = interestLiveData.value.interestLevel2
            val interestLevel3 = interestLiveData.value.interestLevel3
            val interestLevel4 = interestLiveData.value.interestLevel4
            val interestLevel5 = interestLiveData.value.interestLevel5

            val localList = MainInterestLevelList


            val item1 = localList.find { it.id == interestId1.toIntOrNull() }
            if (item1 != null) list.add(
                InterestChildListItem(
                    name = item1.name ?: "",
                    imageResource = item1.image ?: "",
                    childNameList = listOfLevel,
                    childSelectedName = interestLevel1
                )
            )
            val item2 = localList.find { it.id == interestId2.toIntOrNull() }
            if (item2 != null) list.add(
                InterestChildListItem(
                    name = item2.name ?: "",
                    imageResource = item2.image ?: "",
                    childNameList = listOfLevel,
                    childSelectedName = interestLevel2

                )
            )
            val item3 = localList.find { it.id == interestId3.toIntOrNull() }
            if (item3 != null) list.add(
                InterestChildListItem(
                    name = item3.name ?: "",
                    imageResource = item3.image ?: "",
                    childNameList = listOfLevel,
                    childSelectedName = interestLevel3

                )
            )
            val item4 = localList.find { it.id == interestId4.toIntOrNull() }
            if (item4 != null) list.add(
                InterestChildListItem(
                    name = item4.name ?: "",
                    imageResource = item4.image ?: "",
                    childNameList = listOfLevel,
                    childSelectedName = interestLevel4

                )
            )
            val item5 = localList.find { it.id == interestId5.toIntOrNull() }
            if (item5 != null) list.add(
                InterestChildListItem(
                    name = item5.name ?: "",
                    imageResource = item5.image ?: "",
                    childNameList = listOfLevel,
                    childSelectedName = interestLevel5

                )
            )

            onResponse.invoke(list)
        }

    fun getSelectedInterestList2(
        context: Context,
        onResponse: (ArrayList<InterestChildListItem>) -> Unit
    ) =
        viewModelScope.launch {
            val list = ArrayList<InterestChildListItem>()
            val userData = context.dataStoreGetUserData().firstOrNull()


            Log.d(TAG, "getSelectedInterestList:966 " + userData?.interestId1)
            Log.d(TAG, "getSelectedInterestList:977 " + userData?.interestLevel1)
            val interestId1 = userData?.interestId1
            val interestId2 = userData?.interestId2
            val interestId3 = userData?.interestId3
            val interestId4 = userData?.interestId4
            val interestId5 = userData?.interestId5

            val interestLevel1 = userData?.interestLevel1
            val interestLevel2 = userData?.interestLevel2
            val interestLevel3 = userData?.interestLevel3
            val interestLevel4 = userData?.interestLevel4
            val interestLevel5 = userData?.interestLevel5

            val localList = MainInterestLevelList


            val abc=interestLevel1?.capitalize() == "Beginner"
            val abc2=interestLevel1?.capitalize() == "Intermediate"
            val abc3=interestLevel1?.capitalize() == "Master"

            Log.d(TAG,"getSelectedInterestList321321:1955 "+interestLevel1?.capitalize())
            Log.d(TAG,"getSelectedInterestList321321:1999559 "+abc)
            Log.d(TAG, "getSelectedInterestList321321:1955 "+abc2)
            Log.d(TAG, "getSelectedInterestList321321:1955 "+abc3)


            val item1 = localList.find { it.id == interestId1?.toIntOrNull() }
            Log.d(TAG, "getSelectedInterestList2:1888 " + item1)
            if (item1 != null) list.add(
                InterestChildListItem(
                    name = item1.name ?: "",
                    imageResource = item1.image ?: "",
                    childNameList = listOf(
                        InterestNestedItem(name = "Beginner",isSelected = interestLevel1?.capitalize() == "Beginner"),
                        InterestNestedItem(name ="Intermediate",isSelected = interestLevel1?.capitalize() == "Intermediate"),
                        InterestNestedItem(name ="Master",isSelected = interestLevel1?.capitalize() == "Master"),
                    ),
                    childSelectedName = interestLevel1 ?: ""
                )
            )
            val item2 = localList.find { it.id == interestId2?.toIntOrNull() }
            if (item2 != null) list.add(
                InterestChildListItem(
                    name = item2.name ?: "",
                    imageResource = item2.image ?: "",
                    childNameList = listOf(
                        InterestNestedItem(name = "Beginner",isSelected = interestLevel2?.capitalize() == "Beginner"),
                        InterestNestedItem(name ="Intermediate",isSelected = interestLevel2?.capitalize() == "Intermediate"),
                        InterestNestedItem(name ="Master",isSelected = interestLevel2?.capitalize() == "Master"),
                    ),
                    childSelectedName = interestLevel2 ?: ""

                )
            )
            val item3 = localList.find { it.id == interestId3?.toIntOrNull() }
            if (item3 != null) list.add(
                InterestChildListItem(
                    name = item3.name ?: "",
                    imageResource = item3.image ?: "",
                    childNameList = listOf(
                        InterestNestedItem(name = "Beginner",isSelected = interestLevel3?.capitalize() == "Beginner"),
                        InterestNestedItem(name ="Intermediate",isSelected = interestLevel3?.capitalize() == "Intermediate"),
                        InterestNestedItem(name ="Master",isSelected = interestLevel3?.capitalize() == "Master"),
                    ),
                    childSelectedName = interestLevel3 ?: ""

                )
            )
            val item4 = localList.find { it.id == interestId4?.toIntOrNull() }
            if (item4 != null) list.add(
                InterestChildListItem(
                    name = item4.name ?: "",
                    imageResource = item4.image ?: "",
                    childNameList = listOf(
                        InterestNestedItem(name = "Beginner",isSelected = interestLevel4?.capitalize() == "Beginner"),
                        InterestNestedItem(name ="Intermediate",isSelected = interestLevel4?.capitalize() == "Intermediate"),
                        InterestNestedItem(name ="Master",isSelected = interestLevel4?.capitalize() == "Master"),
                    ),
                    childSelectedName = interestLevel4 ?: ""

                )
            )
            val item5 = localList.find { it.id == interestId5?.toIntOrNull() }
            if (item5 != null) list.add(
                InterestChildListItem(
                    name = item5.name ?: "",
                    imageResource = item5.image ?: "",
                    childNameList = listOf(
                        InterestNestedItem(name = "Beginner",isSelected = interestLevel5?.capitalize() == "Beginner"),
                        InterestNestedItem(name ="Intermediate",isSelected = interestLevel5?.capitalize() == "Intermediate"),
                        InterestNestedItem(name ="Master",isSelected = interestLevel5?.capitalize() == "Master"),
                    ),
                    childSelectedName = interestLevel5 ?: ""

                )
            )

            onResponse.invoke(list)
        }

    private var isChildListDataValid = false
    fun updateChildInterestLevel(childListItems: List<InterestChildListItem>, context: Context) {
        viewModelScope.launch {
            isChildListDataValid = false
            var isAllDataSelected = true

            childListItems.forEach {
                Log.d(TAG, "updateChildInterestLevel:1722 " + it)
                if (it.childSelectedName.isEmpty() || it.childSelectedName == "null") {
                    isAllDataSelected = false
                }
            }

            if (isAllDataSelected) {

                val selected1 =
                    childListItems.getOrNull(0)?.childSelectedName
                val selected2 =
                    childListItems.getOrNull(1)?.childSelectedName
                val selected3 =
                    childListItems.getOrNull(2)?.childSelectedName
                val selected4 =
                    childListItems.getOrNull(3)?.childSelectedName
                val selected5 =
                    childListItems.getOrNull(4)?.childSelectedName

                val newObj = _interestLiveData.value.copy(
                    interestLevel1 = selected1 ?: "",
                    interestLevel2 = selected2 ?: "",
                    interestLevel3 = selected3 ?: "",
                    interestLevel4 = selected4 ?: "",
                    interestLevel5 = selected5 ?: "",
                )
                val userObj = context.dataStoreGetUserData().firstOrNull() ?: return@launch
                Log.d(TAG, "updateChildInterestLevel231: " + newObj.interestId1)
                context.dataStoreSetUserData(
                    userObj
                        .copy(interestId1 = newObj.interestId1)
                        .copy(interestId2 = newObj.interestId2)
                        .copy(interestId3 = newObj.interestId3)
                        .copy(interestId4 = newObj.interestId4)
                        .copy(interestId5 = newObj.interestId5)
                        .copy(interestLevel1 = newObj.interestLevel1)
                        .copy(interestLevel2 = newObj.interestLevel2)
                        .copy(interestLevel3 = newObj.interestLevel3)
                        .copy(interestLevel4 = newObj.interestLevel4)
                        .copy(interestLevel5 = newObj.interestLevel5),
                    "380"
                )
                Log.d(TAG, "updateChildInterestLevel321:197 " + newObj)
                Log.d(TAG, "updateChildInterestLevel321:198 " + userObj)

                isChildListDataValid = true
                _interestLiveData.value = newObj
            }

        }
    }

    fun updateUserInterestWithApi(
        context: Context,
        onComplete: (status: Boolean, msg: String) -> Unit
    ) =
        viewModelScope.launch {
            val userData = context.dataStoreGetUserData().firstOrNull()
            Log.d(TAG, "updateUserInterestWithApi1230:213 " + userData);
            val response = mainRepo.updateUser(
                user_id = userData?.userId.toString(),
                email = userData?.email ?: "",
                register_otp = "",
                name = userData?.name ?: "",
                birthdate = userData?.bday ?: "",
                religion_id = userData?.religion ?: "",
                gender = userData?.gender ?: "",
                education_id = userData?.education ?: "",
                occupation = userData?.occupation ?: "",
                height = userData?.height ?: "",
                weight = userData?.weight ?: "",
                relationship_id = userData?.relationship ?: "",
                marital_status_id = userData?.maritalStatus ?: "",
                kid_id = userData?.kids ?: "",
                zodiac_sign_id = userData?.zodiacSign ?: "",
                animal_sign_id = userData?.animalSign ?: "",
                image = userData?.imagePath ?: "",
                image22 = userData?.imagePath22 ?: "",
                image33 = userData?.imagePath33 ?: "",
                image44 = userData?.imagePath44 ?: "",
                address = userData?.address ?: "",
                bio = userData?.bio ?: "",
                lat = userData?.latitude ?: "",
                long = userData?.longitude ?: "",
                interestId1 = _interestLiveData.value.interestId1,
                interestId2 = _interestLiveData.value.interestId2,
                interestId3 = _interestLiveData.value.interestId3,
                interestId4 = _interestLiveData.value.interestId4,
                interestId5 = _interestLiveData.value.interestId5,
                interestLevel1 = _interestLiveData.value.interestLevel1,
                interestLevel2 = _interestLiveData.value.interestLevel2,
                interestLevel3 = _interestLiveData.value.interestLevel3,
                interestLevel4 = _interestLiveData.value.interestLevel4,
                interestLevel5 = _interestLiveData.value.interestLevel5,
            )

            when (response) {
                is Resource.Error -> {
                    onComplete.invoke(response.data?.status ?: true, response.data?.message ?: "")
                }

                is Resource.Success -> {

                    if (response.data?.status == true) {

                        viewModelScope.launch {
                            launch {
                                val userDataNew =
                                    context.dataStoreGetUserData().firstOrNull() ?: return@launch
                                context.dataStoreSetUserData(
                                    userDataNew.copy(
                                        email = response.data.data?.email,
                                        name = response.data.data?.name,
                                        bday = response.data.data?.birthdate,
                                        religion = response.data.data?.religion_id,
                                        gender = response.data.data?.gender,
                                        education = response.data.data?.education_id,
                                        occupation = response.data.data?.occupation,
                                        height = response.data.data?.height,
                                        weight = response.data.data?.weight,
                                        relationship = response.data.data?.relationship_id,
                                        maritalStatus = response.data.data?.marital_status_id,
                                        kids = response.data.data?.kid_id,
                                        zodiacSign = response.data.data?.zodiac_sign_id,
                                        animalSign = response.data.data?.animal_sign_id,
                                        address = response.data.data?.address,
                                        bio = response.data.data?.bio,
                                        latitude = response.data.data?.latitude,
                                        longitude = response.data.data?.longitude,

                                        imageUrl = response.data.data?.user_photos?.getOrNull(0)?.image_url,
                                        imageUrl22 = response.data.data?.user_photos?.getOrNull(1)?.image_url,
                                        imageUrl33 = response.data.data?.user_photos?.getOrNull(2)?.image_url,
                                        imageUrl44 = response.data.data?.user_photos?.getOrNull(3)?.image_url,

                                        imageId = response.data.data?.user_photos?.getOrNull(0)?.id.toString(),
                                        imageId22 = response.data.data?.user_photos?.getOrNull(1)?.id.toString(),
                                        imageId33 = response.data.data?.user_photos?.getOrNull(2)?.id.toString(),
                                        imageId44 = response.data.data?.user_photos?.getOrNull(3)?.id.toString(),


                                        age = (response.data.data?.age ?: 0).toString(),


                                        interestId1 = (response.data.data?.user_interest?.getOrNull(
                                            0
                                        )?.interest_id ?: 0).toString(),
                                        interestLevel1 = response.data.data?.user_interest?.getOrNull(
                                            0
                                        )?.level ?: "",
                                        interestId2 = (response.data.data?.user_interest?.getOrNull(
                                            1
                                        )?.interest_id ?: 0).toString(),
                                        interestLevel2 = response.data.data?.user_interest?.getOrNull(
                                            1
                                        )?.level ?: "",
                                        interestId3 = (response.data.data?.user_interest?.getOrNull(
                                            2
                                        )?.interest_id ?: 0).toString(),
                                        interestLevel3 = response.data.data?.user_interest?.getOrNull(
                                            2
                                        )?.level ?: "",
                                        interestId4 = (response.data.data?.user_interest?.getOrNull(
                                            3
                                        )?.interest_id ?: 0).toString(),
                                        interestLevel4 = response.data.data?.user_interest?.getOrNull(
                                            3
                                        )?.level ?: "",
                                        interestId5 = (response.data.data?.user_interest?.getOrNull(
                                            4
                                        )?.interest_id ?: 0).toString(),
                                        interestLevel5 = response.data.data?.user_interest?.getOrNull(
                                            4
                                        )?.level ?: "",
                                    ), "104"
                                )
                            }.join()

                            context.dataStoreSetTempLocation("", "", "", 121)
                            onComplete.invoke(
                                response.data?.status ?: true,
                                response.data?.message ?: ""
                            )
                        }


                    } else {
                        onComplete.invoke(
                            response.data?.status ?: true,
                            response.data?.message ?: ""
                        )
                    }


                }
            }
        }


    fun updateSelectedPos(
        allItemList: List<InterestGridItem>,
        context: Context,
        onComplete: (status: List<InterestGridItem>) -> Unit
    ) = viewModelScope.launch {

        val userData = context.dataStoreGetUserData().firstOrNull()

        allItemList.updateSelectedData(userData?.interestId1?.toIntOrNull())
        allItemList.updateSelectedData(userData?.interestId2?.toIntOrNull())
        allItemList.updateSelectedData(userData?.interestId3?.toIntOrNull())
        allItemList.updateSelectedData(userData?.interestId4?.toIntOrNull())
        allItemList.updateSelectedData(userData?.interestId5?.toIntOrNull())

        onComplete.invoke(allItemList)
    }

    //register mate

    fun callVerifyOtpAndRegisterApi(
        context: Context,
        onComplete: (status: Boolean, msg: String) -> Unit
    ) = viewModelScope.launch {

        val locationData = context.dataStoreGetTempLocation().firstOrNull()
        val userData = context.dataStoreGetUserData().firstOrNull()
        Log.d(TAG, "callVerifyOtpAndRegisterApi231: " + userData)
        val response = mainRepo.registerUser(
            email = userData?.email ?: "",
            password = userData?.pwd ?: "",
            register_otp = userData?.otp ?: "",
            name = userData?.name ?: "",
            birthdate = userData?.bday ?: "",
            religion_id = userData?.religion ?: "",
            gender = userData?.gender ?: "",
            education_id = userData?.education ?: "",
            occupation = userData?.occupation ?: "",
            height = userData?.height ?: "",
            weight = userData?.weight ?: "",
            relationship_id = userData?.relationship ?: "",
            marital_status_id = userData?.maritalStatus ?: "",
            kid_id = userData?.kids ?: "",
            zodiac_sign_id = userData?.zodiacSign ?: "",
            animal_sign_id = userData?.animalSign ?: "",
            image = userData?.imagePath ?: "",
            image22 = userData?.imagePath22 ?: "",
            image33 = userData?.imagePath33 ?: "",
            image44 = userData?.imagePath44 ?: "",
            bio = userData?.bio ?: "",
            faceBookLogin = userData?.facebook ?: "",
            googleLogin = userData?.google ?: "",
            address = userData?.address ?: "",
            lat = userData?.latitude ?: "",
            long = userData?.longitude ?: "",
            interestId1 = userData?.interestId1 ?: "",
            interestId2 = userData?.interestId2 ?: "",
            interestId3 = userData?.interestId3 ?: "",
            interestId4 = userData?.interestId4 ?: "",
            interestId5 = userData?.interestId5 ?: "",
            interestLevel1 = userData?.interestLevel1 ?: "",
            interestLevel2 = userData?.interestLevel2 ?: "",
            interestLevel3 = userData?.interestLevel3 ?: "",
            interestLevel4 = userData?.interestLevel4 ?: "",
            interestLevel5 = userData?.interestLevel5 ?: "",
        )

        when (response) {
            is Resource.Error -> {
                onComplete.invoke(response.data?.status ?: true, response.data?.message ?: "")
            }

            is Resource.Success -> {

                if (response.data?.status == true) {

                    viewModelScope.launch {
                        launch {
                            val userDataNew =
                                context.dataStoreGetUserData().firstOrNull() ?: return@launch

                            Log.d("TAG", "callVerifyOtpAndRegisterApi31: " + userDataNew)
                            context.dataStoreSetUserData(
                                userDataNew.copy(
                                    email = response.data.data?.email,
                                    pwd = response.data.data?.password,
                                    name = response.data.data?.name,
                                    bday = response.data.data?.birthdate,
                                    religion = response.data.data?.religion_id,
                                    gender = response.data.data?.gender,
                                    education = response.data.data?.education_id,
                                    occupation = response.data.data?.occupation,
                                    height = response.data.data?.height,
                                    weight = response.data.data?.weight,
                                    relationship = response.data.data?.relationship_id,
                                    maritalStatus = response.data.data?.marital_status_id,
                                    kids = response.data.data?.kid_id,
                                    zodiacSign = response.data.data?.zodiac_sign_id,
                                    animalSign = response.data.data?.animal_sign_id,
                                    address = response.data.data?.address,
                                    latitude = response.data.data?.latitude,
                                    longitude = response.data.data?.longitude,
                                    bio = response.data.data?.bio,
                                    imageUrl = response.data.data?.user_photos?.getOrNull(0)?.image_url,
                                    imageUrl22 = response.data.data?.user_photos?.getOrNull(1)?.image_url,
                                    imageUrl33 = response.data.data?.user_photos?.getOrNull(2)?.image_url,
                                    imageUrl44 = response.data.data?.user_photos?.getOrNull(3)?.image_url,

                                    userId = response.data.data?.id,
                                    age = (response.data.data?.age ?: 0).toString(),
                                ), "104"
                            )
                        }.join()

                        context.dataStoreSetTempLocation("", "", "", 121)
                        onComplete.invoke(
                            response.data?.status ?: true,
                            response.data?.message ?: ""
                        )

                        FirebaseMessaging.getInstance().token
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val token = task.result
                                    Log.d(TAG, "callVerifyOtpAndRegisterApi:474 " + token)

                                    viewModelScope.launch(Dispatchers.IO) {
                                        val userDataNew =
                                            context.dataStoreGetUserData().firstOrNull()
                                                ?: return@launch
                                        Log.d(
                                            TAG,
                                            "callVerifyOtpAndRegisterApi:479 " + userDataNew.userId
                                        )

                                        mainRepo.updateFcmToken(
                                            userDataNew.userId.toString(),
                                            token
                                        )
                                    }

                                    // Save the token to your server.
                                } else {
                                    Log.w(
                                        TAG,
                                        "Fetching FCM registration token failed",
                                        task.exception
                                    )
                                }
                            }


                    }


                } else {
                    onComplete.invoke(response.data?.status ?: true, response.data?.message ?: "")
                }


            }
        }

    }

}

private const val TAG = "EditInterestViewModel"
private fun List<InterestGridItem>.updateSelectedData(id: Int?) {
    val obj1 = MainInterestLevelList.find { (it.id ?: -1) == id }
    val foundItem = find { it.name == (obj1?.name) }
    foundItem?.let {
        val index = indexOf(it)
        this[index].isSelected = true
    }
}
