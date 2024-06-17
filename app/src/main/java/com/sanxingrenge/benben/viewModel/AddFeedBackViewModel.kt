package com.sanxingrenge.benben.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanxingrenge.benben.constant.Constns
import com.sanxingrenge.benben.constant.Constns.image1
import com.sanxingrenge.benben.constant.Constns.image2
import com.sanxingrenge.benben.constant.Constns.image3
import com.sanxingrenge.benben.constant.Constns.image4
import com.sanxingrenge.benben.constant.Constns.image5
import com.sanxingrenge.benben.constant.Constns.image6
import com.sanxingrenge.benben.constant.dataStoreGetUserData
import com.sanxingrenge.benben.constant.dataStoreSetUserData
import com.sanxingrenge.benben.responseModel.GetFeedBackDataListResponse
import com.sanxingrenge.benben.retrofilt.MainRepo
import com.sanxingrenge.benben.retrofilt.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddFeedBackViewModel @Inject constructor(val repository: MainRepo) : ViewModel() {

    fun addFeedBackApi(
        context: Context,
        email: String,
        feedback: String,
        description: String,
        img1: String,
        img2: String,
        img3: String,
        img4: String,
        img5: String,
        img6: String,
        onComplete: (status: Boolean, msg: String) -> Unit
    ) =
        viewModelScope.launch {
            val userObj = context.dataStoreGetUserData().firstOrNull()
            val uid = userObj?.userId


            when (val data =
                repository.addFeedback(
                    uid.toString(),
                    email,
                    feedback,
                    description,
                    img1,
                    img2,
                    img3,
                    img4,
                    img5,
                    img6
                )) {
                is Resource.Error -> {
                    onComplete.invoke(false, "Something went wrong.")
                }
                is Resource.Success -> {
                    onComplete.invoke(data.data?.status?:true, data.data?.message ?: "")

                   /* if (data.data?.status == true) {
                    } else {
                        onComplete.invoke(data.data?.status?, data.data?.message ?: "")
                    }*/

                    //image path clere mate
                    userObj?.let {
                        context.dataStoreSetUserData(
                            userObj.copy(
                                imagePath1 = "",
                                imagePath2 = "",
                                imagePath3 = "",
                                imagePath4 = "",
                                imagePath5 = "",
                                imagePath6 = "",
                            ), "51"
                        )
                    }
                }
            }
        }

    fun imageCount(context: Context, onComplete: (String) -> Unit) = viewModelScope.launch {
        context.dataStoreGetUserData().collect {
            Log.d("TAG", "imageCount: "+ Constns.image1.value)
            Log.d("TAG", "imageCount: "+ Constns.image2.value)
            Log.d("TAG", "imageCount: "+ Constns.image3.value)
            var Count: Int = 0
            if (!Constns.image1.value.isNullOrEmpty()) Count += 1
            if (!Constns.image2.value.isNullOrEmpty()) Count += 1
            if (!Constns.image3.value.isNullOrEmpty()) Count += 1
            if (!Constns.image4.value.isNullOrEmpty()) Count += 1
            if (!Constns.image5.value.isNullOrEmpty()) Count += 1
            if (!Constns.image6.value.isNullOrEmpty()) Count += 1


            if (Count == 0) onComplete.invoke("Upload app screenshots")
            else onComplete.invoke(Count.toString() + " image Upload")
        }


    }

    private var FeedBacktDataList: List<GetFeedBackDataListResponse>? = null


    fun getFeedBack(
        context: Context,
        onListReceived: (
            List<GetFeedBackDataListResponse>,
            firstTimeSelectedItem: GetFeedBackDataListResponse?
        ) -> Unit
    ) =
        viewModelScope.launch {
            val userObj = context.dataStoreGetUserData().firstOrNull()
            val uid = userObj?.userId

            if (!FeedBacktDataList.isNullOrEmpty()) {
                onListReceived.invoke(FeedBacktDataList!!,null)
                return@launch
            }

            when (val data =
                repository.getFeedback(uid ?: 0)) {
                is Resource.Error -> {
                }
                is Resource.Success -> {
                    FeedBacktDataList = data.data?.data?.let {
                        ArrayList(it)
                    }

                    if (!FeedBacktDataList.isNullOrEmpty()) onListReceived.invoke(
                        FeedBacktDataList!!,
                        FeedBacktDataList?.getOrNull(0)
                    )

                }
            }
        }

    fun clereimage() {
        image1.value=""
        image2.value=""
        image3.value=""
        image4.value=""
        image5.value=""
        image6.value=""
    }


}



