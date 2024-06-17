package com.sanxingrenge.benben.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanxingrenge.benben.constant.*
import com.sanxingrenge.benben.dataModel.LocationObject
import com.sanxingrenge.benben.dataModel.UserDataObject
import com.sanxingrenge.benben.retrofilt.MainRepo
import com.sanxingrenge.benben.retrofilt.Resource
import com.sanxingrenge.benben.screens.loginRegisterModule.ScreenStateRegisterScreen
import com.sanxingrenge.benben.uiComponents.InterestChildListItem
import com.sanxingrenge.benben.uiComponents.InterestGridItem
import com.sanxingrenge.benben.uiComponents.InterestNestedItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val TAG = "RegisterViewModel"

val listOfLevel = listOf(
    InterestNestedItem("Beginner"),
    InterestNestedItem("Intermediate"),
    InterestNestedItem("Master"),
)

@HiltViewModel
class RegisterViewModel @Inject constructor(val repository: MainRepo) : ViewModel() {


    private val _uiState = MutableStateFlow(ScreenStateRegisterScreen.CreateYourProfile)

    //private val _uiState = MutableStateFlow(ScreenStateRegisterScreen.CreateYourProfile)
    val uiState: StateFlow<ScreenStateRegisterScreen> = _uiState
    fun updateUiState(state: ScreenStateRegisterScreen) {
        _uiState.value = state
    }

    private val _podcasts = MutableStateFlow(UserDataObject())
    var podcastsLive: StateFlow<UserDataObject> = _podcasts


    fun updateUserData(getNewObj: UserDataObject?) = viewModelScope.launch {
        getNewObj?.let {
            _podcasts.value = getNewObj
        }
        Log.d(
            TAG,
            "saveLocally: updateUserData>>${_podcasts.value.height}>>${_podcasts.value.address}>>${_podcasts.value.longitude}"
        )
    }

    private var job: Job? = null
    fun saveLocally(context: Context) = viewModelScope.launch {
        var ab = context.dataStoreGetUserData().firstOrNull() ?: UserDataObject()

        ab = ab.copy(
            email = _podcasts.value.email,
            pwd = _podcasts.value.pwd,
            bday = _podcasts.value.bday,
            name = _podcasts.value.name,
            gender = _podcasts.value.gender,
            religion = MainReligionList.findIdOfItem(_podcasts.value.religion ?: ""),
            education = MainEducationList.findIdOfItem(_podcasts.value.education ?: ""),
            occupation = _podcasts.value.occupation,
            relationship = MainRelationList.findIdOfItem(_podcasts.value.relationship ?: ""),
            maritalStatus = MainMaritalList.findIdOfItem(_podcasts.value.maritalStatus ?: ""),
            kids = MainKidsList.findIdOfItem(_podcasts.value.kids ?: ""),
            zodiacSign = ZodiacList.findIdOfItemTitle(_podcasts.value.zodiacSign ?: ""),
            animalSign = AnimalList.findIdOfItemTitle(_podcasts.value.animalSign ?: ""),
            height = _podcasts.value.height,
            weight = _podcasts.value.weight,
        )
        Log.d(
            TAG,
            "saveLocally: testLastItem>>${_podcasts.value.height}>>${_podcasts.value.imagePath}"
        )
        job?.cancel()
        job = launch {
            context.dataStoreSetUserData(ab, "70")
        }
    }

    /*fun sendEmailOtp(onComplete: (status: Boolean, msg: String, otp: String) -> Unit) =
        viewModelScope.launch {
            when (val data = repository.getSendEmailOtp(_podcasts.value.email ?: "")) {
                is Resource.Error -> {
                    onComplete.invoke(false, "Something went wrong.", "")
                }
                is Resource.Success -> {
                    if (data.data?.status == true) {
                        onComplete.invoke(true, data.data.message ?: "", data.data.otp.toString())
                    } else {
                        onComplete.invoke(false, data.data?.message ?: "", "")
                    }
                }
            }
        }*/

    fun sendEmailOtp(
        context: Context,
        email: String,
        pwd:String,
        onComplete: (status: Boolean, msg: String, otp: String) -> Unit
    ) =
        viewModelScope.launch {
            when (val data = repository.getSendEmailOtp(email)) {
                is Resource.Error -> {
                    onComplete.invoke(data.data?.status?:true, data.data?.message ?: "", "")
                }

                is Resource.Success -> {
                    Log.d(TAG, "sendEmailOtp321sdfsdsdfds:119 "+Constns.pwdSee)
                    Log.d(TAG, "sendEmailOtp32sdf1sdfds:120 "+pwd)
                    val userObj = context.dataStoreGetUserData().firstOrNull() ?: return@launch
                    context.dataStoreSetUserData(userObj.copy(email = email).copy(pwd = Constns.pwdSee), "380")
                    if (data.data?.status == true) {
                        onComplete.invoke(data.data.status, data.data.message ?: "", data.data.otp.toString())
                    } else {
                        onComplete.invoke(data.data?.status?:true, data.data?.message ?: "", "")
                    }
                }
            }
        }



    fun getEmailExist(
        context: Context,
        email: String,
        onComplete: (status: Boolean, msg: String) -> Unit
    ) =
        viewModelScope.launch {
            when (val data = repository.getEmailExist(email)) {
                is Resource.Error -> {
                    onComplete.invoke(data.data?.status?:true, data.data?.message ?: "")
                }

                is Resource.Success -> {
                    if (data.data?.status == true) {
                        val userObj = context.dataStoreGetUserData().firstOrNull() ?: return@launch
                        context.dataStoreSetUserData(userObj.copy(email = email), "380")
                        onComplete.invoke(data.data.status, data.data.message ?: "")
                    } else {
                        onComplete.invoke(data.data?.status?:true, data.data?.message ?: "")
                    }
                }
            }
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


    fun getSelectedInterestList(
        context: Context,
        onResponse: (ArrayList<InterestChildListItem>) -> Unit
    ) =
        viewModelScope.launch {
            val list = ArrayList<InterestChildListItem>()

            val userData = context.dataStoreGetUserData().firstOrNull()

            val localList = MainInterestLevelList

            val item1 = localList.find { it.id == userData?.interestId1?.toIntOrNull() }
            if (item1 != null) list.add(
                InterestChildListItem(
                    name = item1.name ?: "",
                    imageResource = item1.image ?: "",
                    childNameList = listOfLevel
                )
            )
            val item2 = localList.find { it.id == userData?.interestId2?.toIntOrNull() }
            if (item2 != null) list.add(
                InterestChildListItem(
                    name = item2.name ?: "",
                    imageResource = item2.image ?: "",
                    childNameList = listOfLevel
                )
            )
            val item3 = localList.find { it.id == userData?.interestId3?.toIntOrNull() }
            if (item3 != null) list.add(
                InterestChildListItem(
                    name = item3.name ?: "",
                    imageResource = item3.image ?: "",
                    childNameList = listOfLevel
                )
            )
            val item4 = localList.find { it.id == userData?.interestId4?.toIntOrNull() }
            if (item4 != null) list.add(
                InterestChildListItem(
                    name = item4.name ?: "",
                    imageResource = item4.image ?: "",
                    childNameList = listOfLevel
                )
            )
            val item5 = localList.find { it.id == userData?.interestId5?.toIntOrNull() }
            if (item5 != null) list.add(
                InterestChildListItem(
                    name = item5.name ?: "",
                    imageResource = item5.image ?: "",
                    childNameList = listOfLevel
                )
            )

            Log.d(
                TAG,
                "getSelectedInterestList: testFlowABInterestData>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
            )
            Log.d(TAG, "getSelectedInterestList: testFlowABInterestData>>${item1}")
            Log.d(TAG, "getSelectedInterestList: testFlowABInterestData>>${item2}")
            Log.d(TAG, "getSelectedInterestList: testFlowABInterestData>>${item3}")
            Log.d(TAG, "getSelectedInterestList: testFlowABInterestData>>${item4}")
            Log.d(TAG, "getSelectedInterestList: testFlowABInterestData>>${item5}")

            onResponse.invoke(list)
        }

    var isChildListDataValid = false

    fun updateChildInterestLevel(childListItems: List<InterestChildListItem>, context: Context) {
        viewModelScope.launch {

            isChildListDataValid = false
            var isAllDataSelected = true

            childListItems.forEach {
                if (it.childSelectedName.isEmpty() || it.childSelectedName == "null") {
                    isAllDataSelected = false
                }
            }

            if (isAllDataSelected) {

                val userData = context.dataStoreGetUserData().firstOrNull() ?: return@launch

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

                val newObj = userData.copy(
                    interestLevel1 = selected1,
                    interestLevel2 = selected2,
                    interestLevel3 = selected3,
                    interestLevel4 = selected4,
                    interestLevel5 = selected5,
                )
                isChildListDataValid = true

                Log.d(
                    TAG,
                    "updateChildInterestLevel: testFinalFlowInterest>>$selected1>>$selected2>>$selected3>>$selected4>>$selected5"
                )

                context.dataStoreSetUserData(newObj, "239")

            }

        }
    }

    var tempLocationObject: LocationObject? = null
    fun getLocationSelected(context: Context, onFetchedLocation: (LocationObject) -> Unit) =
        viewModelScope.launch {
            context.dataStoreGetTempLocation().collect {
                tempLocationObject = it
                onFetchedLocation.invoke(it)
            }
        }

    fun updateLocationToUserObject(context: Context) = viewModelScope.launch {
        updateUserData(
            _podcasts.value.copy(
                address = tempLocationObject?.name,
                latitude = tempLocationObject?.lat,
                longitude = tempLocationObject?.long,
            )
        )
    }

    fun clearLocationData(context: Context) = viewModelScope.launch {
        context.dataStoreSetTempLocation("", "", "", 265)
    }

    private var userDataObject: UserDataObject? = null
    fun getStoredEmail(context: Context, onReceived: (String) -> Unit) = viewModelScope.launch {
        userDataObject = context.dataStoreGetUserData().firstOrNull()
        onReceived.invoke(userDataObject?.email ?: "")
        Log.d(TAG, "getStoredEmailsd: " + userDataObject?.email)
    }

}



