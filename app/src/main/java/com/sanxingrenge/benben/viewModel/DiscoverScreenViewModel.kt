package com.sanxingrenge.benben.viewModel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import com.sanxingrenge.benben.constant.MainInterestLevelList
import com.sanxingrenge.benben.constant.dataStoreGetUserData
import com.sanxingrenge.benben.responseModel.AllListItem
import com.sanxingrenge.benben.responseModel.DiscoverResponseData
import com.sanxingrenge.benben.responseModel.SearchUserResponseData
import com.sanxingrenge.benben.responseModel.UpdateDataResponse
import com.sanxingrenge.benben.retrofilt.MainRepo
import com.sanxingrenge.benben.retrofilt.Resource
import com.sanxingrenge.benben.screens.loggedInModule.AboutMeDataItem
import com.sanxingrenge.benben.uiComponents.InterestGridItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverScreenViewModel @Inject constructor(val repository: MainRepo) : ViewModel() {

    val _userLiveData = MutableStateFlow<UpdateDataResponse?>(null)
    var userLiveData: StateFlow<UpdateDataResponse?> = _userLiveData

    private var discoverDataList: ArrayList<DiscoverResponseData>? = null


    //RecommendedApi


    fun DiscoverRecommendedUser(
        onListReceived: (ArrayList<DiscoverResponseData>?, status: Boolean) -> Unit,
        context: Context

    ) =
        viewModelScope.launch {

            /* if (!discoverDataList.isNullOrEmpty()) {
                 onListReceived.invoke(discoverDataList!!)
                 return@launch
             }*/

            val userObj = context.dataStoreGetUserData().firstOrNull()
            val uid = userObj?.userId

            when (val data =
                repository.getRecommendedUserList(uid ?: 0)) {
                is Resource.Error -> {
                    onListReceived.invoke(null, false)

                }

                is Resource.Success -> {

                    //userId=data.data?.data?.firstOrNull()?.id?:0
                    discoverDataList = data.data?.data
                    // Log.d("TAG", "DiscoverRecommendedUser1: "+userId)
                    Log.d("TAG", "DiscoverRecommendedUser2: " + data.data?.data)

                    if (!discoverDataList.isNullOrEmpty()) {
                        onListReceived.invoke(
                            discoverDataList!!, true
                        )


                    } else {

                        onListReceived.invoke(null, false)

                    }
                }
            }
        }


    //nearByApi

    fun DiscoverNearByUser(
        onListReceived: (ArrayList<DiscoverResponseData>?, status: Boolean) -> Unit,
        context: Context

    ) =
        viewModelScope.launch {

            /*  if (!discoverDataList.isNullOrEmpty()) {
                  onListReceived.invoke(discoverDataList!!)
                  return@launch
              }*/


            val userObj = context.dataStoreGetUserData().firstOrNull()
            val uid = userObj?.userId

            when (val data =
                repository.getNearbyUserList(uid ?: 0)) {
                is Resource.Error -> {
                    onListReceived.invoke(null, false)

                }

                is Resource.Success -> {

                    discoverDataList = data.data?.data

                    if (!discoverDataList.isNullOrEmpty()) {
                        onListReceived.invoke(
                            discoverDataList!!, true
                        )

                    } else {

                        onListReceived.invoke(null, false)


                    }
                }
            }
        }

    //getUserApi


    private var userDataList: UpdateDataResponse? = null

    fun getUserProfile(
        context: Context,
        onListReceived: (UpdateDataResponse) -> Unit,
        loading: (status:Boolean) -> Unit,
        userId: Int,
    ) = viewModelScope.launch {
        val userObj = context.dataStoreGetUserData().firstOrNull()
        val uid = userObj?.userId

        when (val data = repository.getUserProfile(userId, uid ?: 0)) {
            is Resource.Error -> {
              //  onListReceived.invoke(false,"")
                loading.invoke(false)
            }

            is Resource.Success -> {
                _userLiveData.value=data.data?.data
                userDataList = data.data?.data
                onListReceived.invoke(userDataList!!)
                loading.invoke(true)


                if (userDataList != null) {

                    Log.d("TAG", "getUserProfile: " + userDataList)
                }


            }

        }

    }


    fun SendFriendRequest(
        context: Context,
        reciveId: Int,
        onComplete: (status: Boolean,msg:String) -> Unit

    ) =
        viewModelScope.launch {

            /*  if (!discoverDataList.isNullOrEmpty()) {
                  onListReceived.invoke(discoverDataList!!)
                  return@launch
              }*/


            val userObj = context.dataStoreGetUserData().firstOrNull()
            val uid = userObj?.userId
            when (val data =
                repository.sendFriendRequest(uid ?: 0, reciveId)) {
                is Resource.Error -> {
                    onComplete.invoke(data.data?.status?:true,data.data?.message?:"")
                }

                is Resource.Success -> {

                    if (data.data?.status == true) {
                        onComplete.invoke(data.data.status,data.data.message?:"")

                    } else {
                        onComplete.invoke(data.data?.status?:true,data.data?.message?:"")

                    }

                }
            }
        }


    private var _listOfElemenats: MutableState<MutableList<SearchUserResponseData>> =
        mutableStateOf(mutableListOf())
    val listOfElemenats: State<List<SearchUserResponseData>> = _listOfElemenats

    fun userSearch(
        onListReceived: (ArrayList<SearchUserResponseData>?) -> Unit,
        userSearch: String,
        context: Context

    ) =
        viewModelScope.launch {

            val userObj = context.dataStoreGetUserData().firstOrNull()
            val uid = userObj?.userId

            when (val data = repository.userSearch(uid ?: 0, userSearch)) {
                is Resource.Error -> {
                    Log.d("TAG", "getZodiacList: " + data.message)

                }

                is Resource.Success -> {

                    _listOfElemenats.value = data.data?.data!!
                    Log.d("TAG", "userSessgdarch: " + _listOfElemenats.value)


                }
            }

        }



    fun getInterestList(onCompleted: (ArrayList<InterestGridItem> ,ArrayList<InterestGridItem>, ArrayList<InterestGridItem>) -> Unit) {

        val arraylist1 = arrayListOf<InterestGridItem>()
        val arraylist2 = arrayListOf<InterestGridItem>()
        val arraylist3 = arrayListOf<InterestGridItem>()


        userDataList?.user_interest?.forEach {

            val interest1 = getInterestObj(it.interest_id?:0)

            val int1Obj = InterestGridItem(interest1?.name ?: "", interest1?.image ?: "")

            when {
                it.level.equals("Beginner",true) && interest1 != null -> arraylist1.add(int1Obj)
                it.level.equals("Intermediate",true) && interest1 != null -> arraylist2.add(int1Obj)
                it.level.equals("Master",true) && interest1 != null -> arraylist3.add(int1Obj)
            }

        }

        onCompleted.invoke(arraylist1,arraylist2,arraylist3)


    }

    private fun getInterestObj(i: Int): AllListItem? =
        MainInterestLevelList.find { (it.id ?: 0) == i }



    fun DiscoverDummyUser(
        onListReceived: (ArrayList<DiscoverResponseData>?, status: Boolean) -> Unit,
        context: Context

    ) =
        viewModelScope.launch {
            when (val data =
                repository.getDummyUserList()) {
                is Resource.Error -> {
                    onListReceived.invoke(null, false)

                }

                is Resource.Success -> {

                    discoverDataList = data.data?.data
                    Log.d("TAG", "DiscoverRecommendedUser2: " + data.data?.data)

                    if (!discoverDataList.isNullOrEmpty()) {
                        onListReceived.invoke(
                            discoverDataList!!, true
                        )
                    } else {
                        onListReceived.invoke(null, false)
                    }
                }
            }
        }

    /*fun updateFcmToken(
        context: Context,
        onComplete: (status: Boolean, msg: String) -> Unit
    ) =
        viewModelScope.launch {
            var token=""
            FirebaseMessaging.getInstance().token
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                         token = task.result
                        Log.d("TAG", "callVerifyOtpAndRegisterApi:3111 "+token)


                        // Save the token to your server.
                    } else {
                        Log.w(
                            "TAG",
                            "Fetching FCM registration token failed",
                            task.exception
                        )
                    }
                }


            val userObj = context.dataStoreGetUserData().firstOrNull()
            val uid = userObj?.userId

            when (val data =repository.updateFcmToken(uid.toString(),token)) {
                is Resource.Error -> {
                    onComplete.invoke(data.data?.status?:true, data.data?.message?:"")
                }
                is Resource.Success -> {
                    onComplete.invoke(data.data?.status?:true, data.data?.message?:"")
                }
            }
        }*/

}