package com.sanxingrenge.benben.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanxingrenge.benben.constant.MainInterestLevelList
import com.sanxingrenge.benben.constant.dataStoreGetUserData
import com.sanxingrenge.benben.constant.filterDataStoreGetTempLocation
import com.sanxingrenge.benben.constant.filterDataStoreGetUserData
import com.sanxingrenge.benben.constant.filterDataStoreSetData
import com.sanxingrenge.benben.constant.filterDataStoreSetTempLocation
import com.sanxingrenge.benben.dataModel.FilterDataObject
import com.sanxingrenge.benben.dataModel.LocationObject
import com.sanxingrenge.benben.responseModel.DiscoverResponseData
import com.sanxingrenge.benben.retrofilt.MainRepo
import com.sanxingrenge.benben.retrofilt.Resource
import com.sanxingrenge.benben.screens.loginRegisterModule.SignInScreenState
import com.sanxingrenge.benben.uiComponents.InterestChildListItem
import com.sanxingrenge.benben.uiComponents.InterestFilterChildListItem
import com.sanxingrenge.benben.uiComponents.InterestFilterNestedItem
import com.sanxingrenge.benben.uiComponents.InterestGridItem
import com.sanxingrenge.benben.uiComponents.InterestNestedItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(private val mainRepo: MainRepo) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInScreenState.EmailOtp)
    val uiState: StateFlow<SignInScreenState> = _uiState


    init {
        viewModelScope.launch {
            uiState.collect {

                Log.d("TAG", "testDataSetChanged: ${it.name}")

            }
        }
    }


    fun updateUserDataLocal(
        userobj: FilterDataObject,
        context: Context,
        onComplete: (status: Boolean) -> Unit
    ) = viewModelScope.launch {

        Log.d("TAG", "updateUserDataLocal54: " + userobj)
        launch { context.filterDataStoreSetData(userobj, "41") }.join()
        launch { context.filterDataStoreSetTempLocation("", "", "", 38) }.join()
    }

    private var discoverDataList: ArrayList<DiscoverResponseData>? = null

    fun userFilterApi(
        context: Context,
        onComplete: (ArrayList<DiscoverResponseData>?, status: Boolean) -> Unit,
    ) = viewModelScope.launch {
        val userObj = context.dataStoreGetUserData().firstOrNull()
        val uid = userObj?.userId
        val userData = context.filterDataStoreGetUserData().firstOrNull()
        Log.d("TAG", "userFilte64rApi213: " + userData?.relationship)
        val response = mainRepo.getUserFilter(
            gender = userData?.gender ?: "",
            user_id = uid.toString(),
            lookingFor = userData?.relationship.toString(),
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
        Log.d("TAG", "sdfsdfsdfsdf:sd " + response)

        when (response) {
            is Resource.Error -> {
                onComplete.invoke(null, false)
                Log.d("TAG", "userFilterApi89:error " + "error")
                Log.d("TAG", "userFilterApi89:error " + response.data)

            }

            is Resource.Success -> {
                Log.d("TAG", "userFilterApi89:sucess " + response.data)
                if (response.data?.status == true) {
                    onComplete.invoke(response.data.data, true)

                }
            }
        }

    }

    /*fun userFilterApi(
        context: Context,
        onComplete: (ArrayList<DiscoverResponseData>?) -> Unit,
        onStatus: (status: Boolean) -> Unit
    ) = viewModelScope.launch {
        val userObj = context.dataStoreGetUserData().firstOrNull()
        val uid = userObj?.userId
        val userData = context.filterDataStoreGetUserData().firstOrNull()
        Log.d("TAG", "userFilte64rApi: "+userData)
        val response = mainRepo.getUserFilter(
            gender = userData?.gender ?: "",
            user_id = uid.toString(),
            relationship_id = userData?.relationship ?: "",
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
        Log.d("TAG", "userFilterApi231:142 "+response.data?.data)

        when (response) {
            is Resource.Error -> {
                onComplete.invoke(response.data?.data)
                onStatus.invoke(false)
                viewModelScope.launch {
                    launch {
                        val userDataNew = context.filterDataStoreGetUserData().firstOrNull() ?: return@launch
                        context.filterDataStoreSetData(
                            userDataNew.copy(
                                gender = response.data?.data?.firstOrNull()?.gender,
                                userId = response.data?.data?.firstOrNull()?.id,
                                relationship = response.data?.data?.firstOrNull()?.relationship_id,
                                address = response.data?.data?.firstOrNull()?.address,
                                latitude = response.data?.data?.firstOrNull()?.latitude,
                                longitude = response.data?.data?.firstOrNull()?.longitude,
                                interestId1 = (response.data?.data?.firstOrNull()?.user_interest?.getOrNull(
                                    0
                                )?.interest_id ?: 0).toString(),
                                interestLevel1 = response.data?.data?.firstOrNull()?.user_interest?.getOrNull(
                                    0
                                )?.level ?: "",
                                interestId2 = (response.data?.data?.firstOrNull()?.user_interest?.getOrNull(
                                    1
                                )?.interest_id ?: 0).toString(),
                                interestLevel2 = response.data?.data?.firstOrNull()?.user_interest?.getOrNull(
                                    1
                                )?.level ?: "",
                                interestId3 = (response.data?.data?.firstOrNull()?.user_interest?.getOrNull(
                                    2
                                )?.interest_id ?: 0).toString(),
                                interestLevel3 = response.data?.data?.firstOrNull()?.user_interest?.getOrNull(
                                    2
                                )?.level ?: "",
                                interestId4 = (response.data?.data?.firstOrNull()?.user_interest?.getOrNull(
                                    3
                                )?.interest_id ?: 0).toString(),
                                interestLevel4 = response.data?.data?.firstOrNull()?.user_interest?.getOrNull(
                                    3
                                )?.level ?: "",
                                interestId5 = (response.data?.data?.firstOrNull()?.user_interest?.getOrNull(
                                    4
                                )?.interest_id ?: 0).toString(),
                                interestLevel5 = response.data?.data?.firstOrNull()?.user_interest?.getOrNull(
                                    4
                                )?.level ?: "",
                            ), "96"
                        )
                        Log.d("TAG", "userFiltedrApi: "+userDataNew)
                    }.join()

                    //context.filterDataStoreSetTempLocation("", "", "", 126)
                    // onComplete.invoke(true)
                }

                discoverDataList=response.data?.data
                Log.d("TAG", "userFilterApi231:142 "+discoverDataList)
                onComplete.invoke(discoverDataList)
                onStatus.invoke(true)

            }
            is Resource.Success -> {
                Log.d("TAG", "userFilterApi231: "+"87988")

                if (response.data?.status == true) {
                    Log.d("TAG", "userFilterApi231: "+"97")
                    viewModelScope.launch {
                        launch {
                            val userDataNew = context.filterDataStoreGetUserData().firstOrNull() ?: return@launch
                            context.filterDataStoreSetData(
                                userDataNew.copy(
                                    gender = response.data.data?.firstOrNull()?.gender,
                                    userId = response.data.data?.firstOrNull()?.id,
                                    relationship = response.data.data?.firstOrNull()?.relationship_id,
                                    address = response.data.data?.firstOrNull()?.address,
                                    latitude = response.data.data?.firstOrNull()?.latitude,
                                    longitude = response.data.data?.firstOrNull()?.longitude,
                                    interestId1 = (response.data.data?.firstOrNull()?.user_interest?.getOrNull(
                                        0
                                    )?.interest_id ?: 0).toString(),
                                    interestLevel1 = response.data.data?.firstOrNull()?.user_interest?.getOrNull(
                                        0
                                    )?.level ?: "",
                                    interestId2 = (response.data.data?.firstOrNull()?.user_interest?.getOrNull(
                                        1
                                    )?.interest_id ?: 0).toString(),
                                    interestLevel2 = response.data.data?.firstOrNull()?.user_interest?.getOrNull(
                                        1
                                    )?.level ?: "",
                                    interestId3 = (response.data.data?.firstOrNull()?.user_interest?.getOrNull(
                                        2
                                    )?.interest_id ?: 0).toString(),
                                    interestLevel3 = response.data.data?.firstOrNull()?.user_interest?.getOrNull(
                                        2
                                    )?.level ?: "",
                                    interestId4 = (response.data.data?.firstOrNull()?.user_interest?.getOrNull(
                                        3
                                    )?.interest_id ?: 0).toString(),
                                    interestLevel4 = response.data.data?.firstOrNull()?.user_interest?.getOrNull(
                                        3
                                    )?.level ?: "",
                                    interestId5 = (response.data.data?.firstOrNull()?.user_interest?.getOrNull(
                                        4
                                    )?.interest_id ?: 0).toString(),
                                    interestLevel5 = response.data.data?.firstOrNull()?.user_interest?.getOrNull(
                                        4
                                    )?.level ?: "",
                                ), "96"
                            )
                            Log.d("TAG", "userFiltedrApi: "+userDataNew)
                        }.join()

                        //context.filterDataStoreSetTempLocation("", "", "", 126)
                       // onComplete.invoke(true)
                    }

                    discoverDataList=response.data.data
                    Log.d("TAG", "userFiltedrApi:203 "+response)

                    onComplete.invoke(response.data.data)
                    onStatus.invoke(true)


                } else {
                    Log.d("TAG", "userFiltedrApi:1536 "+"321")
                    // onComplete.invoke(null,true)
                }

                Log.d("TAG", "userFiltedrApi: "+response)

            }
        }

    }*/

    /*    fun userFilterApi(
            context: Context,
            onComplete: (ArrayList<DiscoverResponseData>?) -> Unit,
            onStatus: (status: Boolean) -> Unit
        ) = viewModelScope.launch {
            val userObj = context.dataStoreGetUserData().firstOrNull()
            val uid = userObj?.userId
            val locationData = context.filterDataStoreGetTempLocation().firstOrNull()
            val userData = context.filterDataStoreGetUserData().firstOrNull()
            val response = mainRepo.getUserFilter(
                gender = userData?.gender ?: "",
                user_id = uid.toString(),
                relationship_id = userData?.relationship ?: "",
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

                }
                is Resource.Success -> {
                    if (response.data?.status == true) {
                        viewModelScope.launch {
                            launch {
                                val userDataNew = context.filterDataStoreGetUserData().firstOrNull() ?: return@launch
                                context.filterDataStoreSetData(
                                    userDataNew.copy(
                                        gender = response.data.data?.firstOrNull()?.gender,
                                        userId = response.data.data?.firstOrNull()?.id,
                                        relationship = response.data.data?.firstOrNull()?.relationship_id,
                                        address = response.data.data?.firstOrNull()?.address,
                                        latitude = response.data.data?.firstOrNull()?.latitude,
                                        longitude = response.data.data?.firstOrNull()?.longitude,
                                        interestId1 = (response.data.data?.firstOrNull()?.user_interest?.getOrNull(
                                            0
                                        )?.interest_id ?: 0).toString(),
                                        interestLevel1 = response.data.data?.firstOrNull()?.user_interest?.getOrNull(
                                            0
                                        )?.level ?: "",
                                        interestId2 = (response.data.data?.firstOrNull()?.user_interest?.getOrNull(
                                            1
                                        )?.interest_id ?: 0).toString(),
                                        interestLevel2 = response.data.data?.firstOrNull()?.user_interest?.getOrNull(
                                            1
                                        )?.level ?: "",
                                        interestId3 = (response.data.data?.firstOrNull()?.user_interest?.getOrNull(
                                            2
                                        )?.interest_id ?: 0).toString(),
                                        interestLevel3 = response.data.data?.firstOrNull()?.user_interest?.getOrNull(
                                            2
                                        )?.level ?: "",
                                        interestId4 = (response.data.data?.firstOrNull()?.user_interest?.getOrNull(
                                            3
                                        )?.interest_id ?: 0).toString(),
                                        interestLevel4 = response.data.data?.firstOrNull()?.user_interest?.getOrNull(
                                            3
                                        )?.level ?: "",
                                        interestId5 = (response.data.data?.firstOrNull()?.user_interest?.getOrNull(
                                            4
                                        )?.interest_id ?: 0).toString(),
                                        interestLevel5 = response.data.data?.firstOrNull()?.user_interest?.getOrNull(
                                            4
                                        )?.level ?: "",
                                    ), "96"
                                )
                            }.join()


                        }

                        discoverDataList=response.data.data

                    } else {

                    }

                }
            }

        }*/

    var tempLocationObject: LocationObject? = null
    fun getLocationSelected(context: Context, onFetchedLocation: (LocationObject) -> Unit) =
        viewModelScope.launch {
            context.filterDataStoreGetTempLocation().collect {
                Log.d("TAG", "getLocationSelected: " + it)
                tempLocationObject = it
                onFetchedLocation.invoke(it)
            }
        }

    fun clierData(context: Context) {
        viewModelScope.launch {
            launch {
                val userDataNew =
                    context.filterDataStoreGetUserData().firstOrNull() ?: return@launch
                context.filterDataStoreSetData(
                    userDataNew.copy(
                        gender = "",
                        userId = null,
                        relationship ="",
                        address = "",
                        latitude = "",
                        longitude = "",
                        interestId1 = "",
                        interestLevel1 = "",
                        interestId2 = "",
                        interestLevel2 = "",
                        interestId3 = "",
                        interestLevel3 = "",
                        interestId4 = "",
                        interestLevel4 = "",
                        interestId5 = "",
                        interestLevel5 = "",
                    ), "96"
                )
            }.join()
        }
    }


    private fun clearTempLocation(context: Context) = viewModelScope.launch {
        context.filterDataStoreSetTempLocation("", "", "", 189)
    }


    private val _podcasts = MutableStateFlow(FilterDataObject())
    var podcastsLive: StateFlow<FilterDataObject> = _podcasts
    fun collectUserData(context: Context) = viewModelScope.launch {
        context.filterDataStoreGetUserData().collect {
            Log.d("TAG", "collectUserData: "+it)
            _podcasts.value = it

        }

    }


    private var job2: Job? = null
    fun updateInterestList(gridItems: List<InterestGridItem>, context: Context) {

        job2?.cancel()
        job2 = viewModelScope.launch {

            val userData = context.filterDataStoreGetUserData().firstOrNull() ?: return@launch

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

            context.filterDataStoreSetData(newDataObj, "127")
            Log.d("TAG", "updasdsteInterestList: " + newDataObj)
        }
    }

    private val listOfLevel = listOf(
        InterestFilterNestedItem("Beginner"),
        InterestFilterNestedItem("Intermediate"),
        InterestFilterNestedItem("Master"),
    )

    fun getSelectedInterestList(
        context: Context,
        onResponse: (ArrayList<InterestFilterChildListItem>) -> Unit
    ) =
        viewModelScope.launch {
            val list = ArrayList<InterestFilterChildListItem>()

            val userData = context.filterDataStoreGetUserData().firstOrNull()

            val localList = MainInterestLevelList

            val item1 = localList.find { it.id == userData?.interestId1?.toIntOrNull() }
            if (item1 != null) list.add(
                InterestFilterChildListItem(
                    name = item1.name?: "",
                    imageResource = item1.image ?: "",
                    childNameList = listOfLevel
                )
            )
            val item2 = localList.find { it.id == userData?.interestId2?.toIntOrNull() }
            if (item2 != null) list.add(
                InterestFilterChildListItem(
                    name = item2.name?: "",
                    imageResource = item2.image ?: "",
                    childNameList = listOfLevel
                )
            )
            val item3 = localList.find { it.id == userData?.interestId3?.toIntOrNull() }
            if (item3 != null) list.add(
                InterestFilterChildListItem(
                    name = item3.name ?: "",
                    imageResource = item3.image ?: "",
                    childNameList = listOfLevel
                )
            )
            val item4 = localList.find { it.id == userData?.interestId4?.toIntOrNull() }
            if (item4 != null) list.add(
                InterestFilterChildListItem(
                    name = item4.name ?: "",
                    imageResource = item4.image ?: "",
                    childNameList = listOfLevel
                )
            )
            val item5 = localList.find { it.id == userData?.interestId5?.toIntOrNull() }
            if (item5 != null) list.add(
                InterestFilterChildListItem(
                    name = item5.name?: "",
                    imageResource = item5.image ?: "",
                    childNameList = listOfLevel
                )
            )

            Log.d(
                "TAG",
                "getSelectedInterestList: testFlowABInterestData>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
            )
            Log.d("TAG", "getSelectedInterestList: testFlowABInterestData>>${item1}")
            Log.d("TAG", "getSelectedInterestList: testFlowABInterestData>>${item2}")
            Log.d("TAG", "getSelectedInterestList: testFlowABInterestData>>${item3}")
            Log.d("TAG", "getSelectedInterestList: testFlowABInterestData>>${item4}")
            Log.d("TAG", "getSelectedInterestList: testFlowABInterestData>>${item5}")

            onResponse.invoke(list)
        }

    var isChildListDataValid = false

    fun updateChildInterestLevel(
        childListItems: List<InterestFilterChildListItem>,
        context: Context
    ) {
        viewModelScope.launch {

            isChildListDataValid = false
            var isAllDataSelected = true

            childListItems.forEach {
                if (it.childSelectedName.isEmpty() || it.childSelectedName == "null") {
                    isAllDataSelected = false
                }
            }

            if (isAllDataSelected) {

                val userData =
                    context.filterDataStoreGetUserData().firstOrNull() ?: return@launch

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
                    "TAG",
                    "updateChildInterestLevel: testFinalFlowInterest>>$selected1>>$selected2>>$selected3>>$selected4>>$selected5"
                )
                Log.d("TAG", "updateChildInterestLevel: "+newObj)

                context.filterDataStoreSetData(newObj, "239")

            }

        }
    }

}