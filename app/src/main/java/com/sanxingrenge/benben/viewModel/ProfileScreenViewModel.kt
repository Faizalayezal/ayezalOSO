package com.sanxingrenge.benben.viewModel

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.api.ApiException
import com.google.firebase.messaging.FirebaseMessaging
import com.sanxingrenge.benben.constant.Constns.FaceBookId
import com.sanxingrenge.benben.constant.Constns.GoogleId
import com.sanxingrenge.benben.constant.Constns.SocialLoginMessage
import com.sanxingrenge.benben.constant.Constns.SocialLoginStatus
import com.sanxingrenge.benben.constant.Constns.convertDateFormat
import com.sanxingrenge.benben.constant.Constns.emailLoginUserExist
import com.sanxingrenge.benben.constant.Constns.userImage1
import com.sanxingrenge.benben.constant.Constns.userImage2
import com.sanxingrenge.benben.constant.Constns.userImage3
import com.sanxingrenge.benben.constant.Constns.userImage4
import com.sanxingrenge.benben.constant.MainInterestLevelList
import com.sanxingrenge.benben.constant.dataStoreGetTempLocation
import com.sanxingrenge.benben.constant.dataStoreGetUserData
import com.sanxingrenge.benben.constant.dataStoreSetTempLocation
import com.sanxingrenge.benben.constant.dataStoreSetUserData
import com.sanxingrenge.benben.dataModel.LocationObject
import com.sanxingrenge.benben.dataModel.UserDataObject
import com.sanxingrenge.benben.responseModel.AllListItem
import com.sanxingrenge.benben.retrofilt.MainRepo
import com.sanxingrenge.benben.retrofilt.Resource
import com.sanxingrenge.benben.uiComponents.InterestGridItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "ProfileScreenViewModel"

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class ProfileScreenViewModel @Inject constructor(private val mainRepo: MainRepo) : ViewModel() {

    fun getUserProfile(
        userId: Int,
        context: Context,
        Listner: (status: Boolean, sign: String, zodictit: String, zodicimg: String, animaltit: String, animalimg: String) -> Unit
    ) = viewModelScope.launch {

        when (val data = mainRepo.getUserProfile(userId, userId)) {
            is Resource.Error -> {
                Log.d("TAG", "getZodiacList: " + data.message)
            }

            is Resource.Success -> {
                Log.d(TAG, "getUserProfilesdfsdf: " + data.data?.data)
                if (data.data?.status == true) {
                    viewModelScope.launch {
                        launch {
                            val userDataNew =
                                context.dataStoreGetUserData().firstOrNull() ?: return@launch
                            GoogleId = data.data.data?.google_id ?: ""
                            FaceBookId = data.data.data?.facebook_id ?: ""
                            context.dataStoreSetUserData(
                                userDataNew.copy(
                                    email = data.data.data?.email,
                                    name = data.data.data?.name,
                                    /* bday = data.data.data?.birthdate,*/
                                    bday = convertDateFormat(data.data.data?.birthdate ?: ""),
                                    religion = data.data.data?.religion_id,
                                    gender = data.data.data?.gender,
                                    education = data.data.data?.education_id,
                                    occupation = data.data.data?.occupation,
                                    height = data.data.data?.height,
                                    weight = data.data.data?.weight,
                                    relationship = data.data.data?.relationship_id,
                                    maritalStatus = data.data.data?.marital_status_id,
                                    kids = data.data.data?.kid_id,
                                    zodiacSign = data.data.data?.zodiac_sign?.title ?: "",
                                    animalSign = data.data.data?.animal_sign?.title ?: "",
                                    address = data.data.data?.address,
                                    latitude = data.data.data?.latitude,
                                    longitude = data.data.data?.longitude,
                                    google = data.data.data?.google_id,
                                    facebook = data.data.data?.facebook_id,
                                    bio = data.data.data?.bio,

                                    imageUrl = data.data.data?.user_photos?.getOrNull(0)?.image_url,
                                    imageUrl22 = data.data.data?.user_photos?.getOrNull(1)?.image_url,
                                    imageUrl33 = data.data.data?.user_photos?.getOrNull(2)?.image_url,
                                    imageUrl44 = data.data.data?.user_photos?.getOrNull(3)?.image_url,

                                    imageId = data.data.data?.user_photos?.getOrNull(0)?.id.toString(),
                                    imageId22 = data.data.data?.user_photos?.getOrNull(1)?.id.toString(),
                                    imageId33 = data.data.data?.user_photos?.getOrNull(2)?.id.toString(),
                                    imageId44 = data.data.data?.user_photos?.getOrNull(3)?.id.toString(),


                                    age = (data.data.data?.age ?: 0).toString(),
                                    plan_type = data.data.data?.user_subscription?.firstOrNull()?.plan_type,
                                    interestId1 = (data.data.data?.user_interest?.getOrNull(0)?.interest_id
                                        ?: 0).toString(),
                                    interestLevel1 = data.data.data?.user_interest?.getOrNull(0)?.level
                                        ?: "",
                                    interestId2 = (data.data.data?.user_interest?.getOrNull(1)?.interest_id
                                        ?: 0).toString(),
                                    interestLevel2 = data.data.data?.user_interest?.getOrNull(1)?.level
                                        ?: "",
                                    interestId3 = (data.data.data?.user_interest?.getOrNull(2)?.interest_id
                                        ?: 0).toString(),
                                    interestLevel3 = data.data.data?.user_interest?.getOrNull(2)?.level
                                        ?: "",
                                    interestId4 = (data.data.data?.user_interest?.getOrNull(3)?.interest_id
                                        ?: 0).toString(),
                                    interestLevel4 = data.data.data?.user_interest?.getOrNull(3)?.level
                                        ?: "",
                                    interestId5 = (data.data.data?.user_interest?.getOrNull(4)?.interest_id
                                        ?: 0).toString(),
                                    interestLevel5 = data.data.data?.user_interest?.getOrNull(4)?.level
                                        ?: "",
                                ), "235"
                            )

                            Log.d(
                                TAG,
                                "getUserProfilesfdsf:104 " + data.data.data?.user_photos?.getOrNull(
                                    1
                                )?.image_url
                            )
                            Log.d(
                                TAG,
                                "getUserProfilesfdsf:105 " + data.data.data?.user_photos?.getOrNull(
                                    2
                                )?.image_url
                            )
                            Log.d(
                                TAG,
                                "getUserProfilesfdsf:106 " + data.data.data?.user_photos?.getOrNull(
                                    3
                                )?.image_url
                            )
                            Log.d(TAG, "getUserProfilesfdsf:10777 " + userDataNew)
                        }
                    }
                    Listner.invoke(
                        true,
                        data.data.data?.personality ?: "",
                        data.data.data?.zodiac_sign?.title ?: "",
                        data.data.data?.zodiac_sign?.image_full_path ?: "",
                        data.data.data?.animal_sign?.title ?: "",
                        data.data.data?.animal_sign?.image_full_path ?: ""
                    )
                } else {

                }


            }

        }

    }


    private val _podcasts = MutableStateFlow(UserDataObject())
    var podcastsLive: StateFlow<UserDataObject> = _podcasts

    fun collectUserData(context: Context) = viewModelScope.launch {
        context.dataStoreGetUserData().collect {
            _podcasts.value = it
            Log.d(TAG, "collectUserData:104 " + it)
            if (it.imageUrl != null && it.imageUrl != "") {
                Log.d(TAG, "collectUserData: " + 34)
                /*updateUserApi(context,it.imageUrl){
                    //lodernocode
                }*/
            }
        }
    }

    fun getInterestList(onCompleted: (ArrayList<InterestGridItem>, ArrayList<InterestGridItem>, ArrayList<InterestGridItem>) -> Unit) {

        val userObj = _podcasts.value


        val interest1 = getInterestObj((userObj.interestId1?.toIntOrNull()) ?: 0)
        val interest2 = getInterestObj((userObj.interestId2?.toIntOrNull()) ?: 0)
        val interest3 = getInterestObj((userObj.interestId3?.toIntOrNull()) ?: 0)
        val interest4 = getInterestObj((userObj.interestId4?.toIntOrNull()) ?: 0)
        val interest5 = getInterestObj((userObj.interestId5?.toIntOrNull()) ?: 0)


        val arraylist1 = arrayListOf<InterestGridItem>()
        val arraylist2 = arrayListOf<InterestGridItem>()
        val arraylist3 = arrayListOf<InterestGridItem>()

        val int1Obj = InterestGridItem(interest1?.name ?: "", interest1?.image ?: "")
        when {
            userObj.interestLevel1.equals("Beginner", true) && interest1 != null -> arraylist1.add(
                int1Obj
            )

            userObj.interestLevel1.equals(
                "Intermediate",
                true
            ) && interest1 != null -> arraylist2.add(int1Obj)

            userObj.interestLevel1.equals("Master", true) && interest1 != null -> arraylist3.add(
                int1Obj
            )
        }

        val int2Obj = InterestGridItem(interest2?.name ?: "", interest2?.image ?: "")
        when {
            userObj.interestLevel2.equals("Beginner", true) && interest2 != null -> arraylist1.add(
                int2Obj
            )

            userObj.interestLevel2.equals(
                "Intermediate",
                true
            ) && interest2 != null -> arraylist2.add(int2Obj)

            userObj.interestLevel2.equals("Master", true) && interest2 != null -> arraylist3.add(
                int2Obj
            )
        }
        val int3Obj = InterestGridItem(interest3?.name ?: "", interest3?.image ?: "")
        when {
            userObj.interestLevel3.equals("Beginner", true) && interest3 != null -> arraylist1.add(
                int3Obj
            )

            userObj.interestLevel3.equals(
                "Intermediate",
                true
            ) && interest3 != null -> arraylist2.add(int3Obj)

            userObj.interestLevel3.equals("Master", true) && interest3 != null -> arraylist3.add(
                int3Obj
            )
        }
        val int4Obj = InterestGridItem(interest4?.name ?: "", interest4?.image ?: "")
        when {
            userObj.interestLevel4.equals("Beginner", true) && interest4 != null -> arraylist1.add(
                int4Obj
            )

            userObj.interestLevel4.equals(
                "Intermediate",
                true
            ) && interest4 != null -> arraylist2.add(int4Obj)

            userObj.interestLevel4.equals("Master", true) && interest4 != null -> arraylist3.add(
                int4Obj
            )
        }
        val int5Obj = InterestGridItem(interest5?.name ?: "", interest5?.image ?: "")
        when {
            userObj.interestLevel5.equals("Beginner", true) && interest5 != null -> arraylist1.add(
                int5Obj
            )

            userObj.interestLevel5.equals(
                "Intermediate",
                true
            ) && interest5 != null -> arraylist2.add(int5Obj)

            userObj.interestLevel5.equals("Master", true) && interest5 != null -> arraylist3.add(
                int5Obj
            )
        }

        onCompleted.invoke(arraylist1, arraylist2, arraylist3)
    }

    private fun getInterestObj(i: Int): AllListItem? =
        MainInterestLevelList.find { (it.id ?: 0) == i }

    fun updateUserDataLocal(
        userobj: UserDataObject,
        context: Context,
        onComplete: (status: Boolean) -> Unit
    ) = viewModelScope.launch {
        launch { context.dataStoreSetUserData(userobj, "86") }.join()
        val userData = context.dataStoreGetUserData().firstOrNull()

        updateUserApi(
            context,
            userImage1.value ?: "",
            userImage2.value ?: "",
            userImage3.value ?: "",
            userImage4.value ?: ""
        ) {
            // clearTempImageData(context, userobj)
            clearTempLocation(context)
            onComplete.invoke(it)
        }
    }

    fun updateImagesUserDataLocal(
        userobj: UserDataObject,
        context: Context,
        onComplete: (status: Boolean) -> Unit
    ) = viewModelScope.launch {
        launch { context.dataStoreSetUserData(userobj, "86") }.join()
        val userData = context.dataStoreGetUserData().firstOrNull()

        updateImageUserApi(
            context,
            userImage1.value ?: "",
            userImage2.value ?: "",
            userImage3.value ?: "",
            userImage4.value ?: ""

        ) {
            // clearTempImageData(context, userobj)
            clearTempLocation(context)
            onComplete.invoke(it)
        }
    }

    private fun clearTempImageData(context: Context, userobj: UserDataObject) =
        viewModelScope.launch {
            context.dataStoreSetUserData(
                userobj.copy(imagePath = "").copy(imageUrl = "").copy(imageId = "")
                    .copy(imagePath22 = "").copy(imageUrl22 = "").copy(imageId22 = "")
                    .copy(imagePath33 = "").copy(imageUrl33 = "").copy(imageId33 = "")
                    .copy(imagePath44 = "").copy(imageUrl44 = "").copy(imageId44 = ""),
                "168"
            )
        }

    private fun updateUserApi(
        context: Context,
        image1: String,
        image2: String,
        image3: String,
        image4: String,
        onComplete: (status: Boolean) -> Unit
    ) = viewModelScope.launch {

        val userData = context.dataStoreGetUserData().firstOrNull()
        Log.d(TAG, "updateUserApi:292 " + userData)

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
            image = image1,
            image22 = image2,
            image33 = image3,
            image44 = image4,
            address = userData?.address ?: "",
            bio = userData?.bio ?: "",
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
        Log.d(TAG, "updateUserApi1:330 " + userData?.imagePath)
        Log.d(TAG, "updateUserApi1:331 " + userData?.imageUrl)


        when (response) {
            is Resource.Error -> {
                onComplete.invoke(false)
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


                                    age = (response.data.data?.age ?: 0).toString(),
                                    interestId1 = (response.data.data?.user_interest?.getOrNull(0)?.interest_id
                                        ?: 0).toString(),
                                    interestLevel1 = response.data.data?.user_interest?.getOrNull(0)?.level
                                        ?: "",
                                    interestId2 = (response.data.data?.user_interest?.getOrNull(1)?.interest_id
                                        ?: 0).toString(),
                                    interestLevel2 = response.data.data?.user_interest?.getOrNull(1)?.level
                                        ?: "",
                                    interestId3 = (response.data.data?.user_interest?.getOrNull(2)?.interest_id
                                        ?: 0).toString(),
                                    interestLevel3 = response.data.data?.user_interest?.getOrNull(2)?.level
                                        ?: "",
                                    interestId4 = (response.data.data?.user_interest?.getOrNull(3)?.interest_id
                                        ?: 0).toString(),
                                    interestLevel4 = response.data.data?.user_interest?.getOrNull(3)?.level
                                        ?: "",
                                    interestId5 = (response.data.data?.user_interest?.getOrNull(4)?.interest_id
                                        ?: 0).toString(),
                                    interestLevel5 = response.data.data?.user_interest?.getOrNull(4)?.level
                                        ?: "",
                                ), "104"
                            )
                        }.join()


                        context.dataStoreSetTempLocation("", "", "", 121)
                        onComplete.invoke(true)
                    }


                } else {
                    onComplete.invoke(false)
                }


            }
        }

    }


    private fun updateImageUserApi(
        context: Context,
        image1: String,
        image2: String,
        image3: String,
        image4: String,
        onComplete: (status: Boolean) -> Unit
    ) = viewModelScope.launch {

        val userData = context.dataStoreGetUserData().firstOrNull()
        Log.d(TAG, "updateUserApi:292 " + image1)

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
            image = image1,
            image22 = image2,
            image33 = image3,
            image44 = image4,
            address = userData?.address ?: "",
            bio = userData?.bio ?: "",
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
        Log.d(TAG, "updateUserApi1:330 " + userData?.imagePath)
        Log.d(TAG, "updateUserApi1:331 " + userData?.imageUrl)


        when (response) {
            is Resource.Error -> {
                onComplete.invoke(false)
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


                                    age = (response.data.data?.age ?: 0).toString(),
                                    interestId1 = (response.data.data?.user_interest?.getOrNull(0)?.interest_id
                                        ?: 0).toString(),
                                    interestLevel1 = response.data.data?.user_interest?.getOrNull(0)?.level
                                        ?: "",
                                    interestId2 = (response.data.data?.user_interest?.getOrNull(1)?.interest_id
                                        ?: 0).toString(),
                                    interestLevel2 = response.data.data?.user_interest?.getOrNull(1)?.level
                                        ?: "",
                                    interestId3 = (response.data.data?.user_interest?.getOrNull(2)?.interest_id
                                        ?: 0).toString(),
                                    interestLevel3 = response.data.data?.user_interest?.getOrNull(2)?.level
                                        ?: "",
                                    interestId4 = (response.data.data?.user_interest?.getOrNull(3)?.interest_id
                                        ?: 0).toString(),
                                    interestLevel4 = response.data.data?.user_interest?.getOrNull(3)?.level
                                        ?: "",
                                    interestId5 = (response.data.data?.user_interest?.getOrNull(4)?.interest_id
                                        ?: 0).toString(),
                                    interestLevel5 = response.data.data?.user_interest?.getOrNull(4)?.level
                                        ?: "",
                                ), "104"
                            )
                        }.join()


                        context.dataStoreSetTempLocation("", "", "", 121)
                        onComplete.invoke(true)
                    }


                } else {
                    onComplete.invoke(false)
                }


            }
        }

    }

    var tempLocationObject: LocationObject? = null
    fun getLocationSelected(context: Context, function: (LocationObject) -> Unit) =
        viewModelScope.launch {
            context.dataStoreGetTempLocation().collect {
                tempLocationObject = it
                function.invoke(it)
            }
        }

    private fun clearTempLocation(context: Context) = viewModelScope.launch {
        context.dataStoreSetTempLocation("", "", "", 189)
    }


    private var job2: Job? = null
    fun updateInterestList(gridItems: List<InterestGridItem>, context: Context) {

        job2?.cancel()
        job2 = viewModelScope.launch {

            val userData = context.dataStoreGetUserData().firstOrNull() ?: return@launch

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

            val newDataObj = userData.copy(
                interestId1 = selectedItem1Id,
                interestId2 = selectedItem2Id,
                interestId3 = selectedItem3Id,
                interestId4 = selectedItem4Id,
                interestId5 = selectedItem5Id,
            )

            context.dataStoreSetUserData(newDataObj, "127")
        }
    }

    fun removeImg(
        imageId: String,

        ) = viewModelScope.launch {
        when (val data = mainRepo.removeProfileImage(imageId)) {
            is Resource.Error -> {
                Log.d("TAG", "profileremove:484 " + data.message)

            }

            is Resource.Success -> {

                Log.d("TAG", "profileremove:489 " + data.message)


            }

        }

    }

    fun swapNextImageViews(userPos1: Int, context: Context) {
        /*  if (userPos1 == 1) {

              viewModelScope.launch {

                  context.dataStoreSetUserData(
                      userDataNew.copy(
                          imagePath = userDataNew.imagePath22,
                          imageUrl = userDataNew.imageUrl22,
                          imagePath22 = userDataNew.imagePath33,
                          imageUrl22 = userDataNew.imageUrl33,
                          imagePath33 = userDataNew.imagePath44,
                          imageUrl33 = userDataNew.imageUrl44,
                          imagePath44 = "",
                          imageUrl44 = "",
                      ), "556"
                  )


              }

          }*/

        viewModelScope.launch {
            val userDataNew =
                context.dataStoreGetUserData().firstOrNull() ?: return@launch

            when (userPos1) {
                1 -> {
                    context.dataStoreSetUserData(
                        userDataNew.copy(
                            imagePath = userDataNew.imagePath22,
                            imageUrl = userDataNew.imageUrl22,
                            imagePath22 = userDataNew.imagePath33,
                            imageUrl22 = userDataNew.imageUrl33,
                            imagePath33 = userDataNew.imagePath44,
                            imageUrl33 = userDataNew.imageUrl44,
                            imagePath44 = "",
                            imageUrl44 = "",
                        ), "556"
                    )
                }

                2 -> {
                    context.dataStoreSetUserData(
                        userDataNew.copy(
                            imagePath = userDataNew.imagePath,
                            imageUrl = userDataNew.imageUrl,
                            imagePath22 = userDataNew.imagePath33,
                            imageUrl22 = userDataNew.imageUrl33,
                            imagePath33 = userDataNew.imagePath44,
                            imageUrl33 = userDataNew.imageUrl44,
                            imagePath44 = "",
                            imageUrl44 = "",
                        ), "556"
                    )
                }

                3 -> {
                    context.dataStoreSetUserData(
                        userDataNew.copy(
                            imagePath = userDataNew.imagePath,
                            imageUrl = userDataNew.imageUrl,
                            imagePath22 = userDataNew.imagePath22,
                            imageUrl22 = userDataNew.imageUrl22,
                            imagePath33 = userDataNew.imagePath44,
                            imageUrl33 = userDataNew.imageUrl44,
                            imagePath44 = "",
                            imageUrl44 = "",
                        ), "556"
                    )
                }

                4 -> {
                    context.dataStoreSetUserData(
                        userDataNew.copy(
                            imagePath = userDataNew.imagePath,
                            imageUrl = userDataNew.imageUrl,
                            imagePath22 = userDataNew.imagePath22,
                            imageUrl22 = userDataNew.imageUrl22,
                            imagePath33 = userDataNew.imagePath33,
                            imageUrl33 = userDataNew.imageUrl33,
                            imagePath44 = "",
                            imageUrl44 = "",
                        ), "556"
                    )
                }

            }
        }

    }


    fun startSocialLogin(
        context: Context,
        socialId: String,
        onComplete: (status: Boolean, msg: String, userIxest: Int) -> Unit
    ) = viewModelScope.launch {
        Log.d(TAG, "startSocialLogin:781 " + socialId)
        try {
            when (val data = mainRepo.sociallogin(socialId)) {
                is Resource.Error -> {

                    SocialLoginStatus = data.data?.status ?: true
                    SocialLoginMessage = data.data?.message ?: ""
                    emailLoginUserExist = data.data?.is_user_exist ?: 0
                    onComplete.invoke(
                        data.data?.status ?: true,
                        data.data?.message ?: "",
                        data.data?.is_user_exist ?: 0
                    )
                }

                is Resource.Success -> {
                    Log.d(TAG, "startSocialLogin:791 " + data.data?.is_user_exist)
                    Log.d(TAG, "startSocialLogin:792 " + data.data)
                    if (data.data?.is_user_exist == 1) {
                        viewModelScope.launch {
                            Log.d(TAG, "startSocialLogin:797 " + data.data)
                            launch {

                                CoroutineScope(Dispatchers.IO).launch {
                                    val userDataNew =
                                        context.dataStoreGetUserData().firstOrNull()
                                            ?: return@launch
                                    Log.d("TAG", "profileremove:697 " + data.data.data?.id)

                                    context.dataStoreSetUserData(
                                        userDataNew.copy(
                                            email = data.data.data?.email,
                                            userId = data.data.data?.id,
                                        ), "104"
                                    )
                                }

                            }.join()

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
                            onComplete.invoke(
                                data.data.status,
                                data.data.message ?: "",
                                data.data.is_user_exist
                            )


                        }
                    } else {
                        Log.d("TAG", "startSocialLogin:844 " + data.data)
                        onComplete.invoke(
                            data.data?.status ?: true,
                            data.data?.message ?: "",
                            data.data?.is_user_exist ?: 0
                        )

                    }


                }

            }
        } catch (e: ApiException) {
            e.printStackTrace()
            Log.d(TAG, "startSocialLogin:861 " + e.localizedMessage)
        }


    }
}