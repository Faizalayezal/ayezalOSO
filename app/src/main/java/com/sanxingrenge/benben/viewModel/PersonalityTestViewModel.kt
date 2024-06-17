package com.sanxingrenge.benben.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanxingrenge.benben.constant.dataStoreGetUserData
import com.sanxingrenge.benben.responseModel.GetQuestionsListDataResponse
import com.sanxingrenge.benben.responseModel.PersonalityDesignDataResponse
import com.sanxingrenge.benben.responseModel.PersonalityDesignResponse
import com.sanxingrenge.benben.retrofilt.MainRepo
import com.sanxingrenge.benben.retrofilt.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalityTestViewModel @Inject constructor(val repository: MainRepo) : ViewModel() {

    private var questionDataList: ArrayList<GetQuestionsListDataResponse>? = null


    fun getPersonalityQuestionList(onListReceived: (ArrayList<GetQuestionsListDataResponse>,status:Boolean) -> Unit) =
        viewModelScope.launch {

           /* if (!questionDataList.isNullOrEmpty()) {
                onListReceived.invoke(questionDataList!!,false)
                return@launch
            }*/

            when (val data = repository.getQuestionList()) {
                is Resource.Error -> {
                    Log.d("TAG", "getZodiacList: " + data.message)
                    onListReceived.invoke(questionDataList!!,false)

                }
                is Resource.Success -> {
                    questionDataList = data.data?.data
                    onListReceived.invoke(data.data?.data?:arrayListOf(),true)
                   /* if (!questionDataList.isNullOrEmpty()) onListReceived.invoke(
                        questionDataList!!,true
                    )*/
                }
            }

        }


    fun addQuestionAnswerApi(
        context: Context,
        questionId: Int,
        answere: String,
        onListReceived: (status: Boolean) -> Unit
    ) =
        viewModelScope.launch {
            val userObj = context.dataStoreGetUserData().firstOrNull()
            val uid = userObj?.userId


            when (val data = repository.addQuestionAnswer(uid ?: 0, questionId, answere)) {
                is Resource.Error -> {
                    onListReceived.invoke(false)

                }
                is Resource.Success -> {
                    Log.d("TAG", "addQuestionAnswerApi64: "+data)
                    onListReceived.invoke(true)

                }
            }

        }

    fun getUserPersonality(
        context: Context,
        onListReceived: (status: Boolean,data:String) -> Unit
    ) =
        viewModelScope.launch {
            val userObj = context.dataStoreGetUserData().firstOrNull()
            val uid = userObj?.userId


            when (val data = repository.getUserPersonality(uid ?: 0)) {
                is Resource.Error -> {
                    onListReceived.invoke(false,data.data?.data?:"")

                }
                is Resource.Success -> {
                    onListReceived.invoke(true,data.data?.data?:"")

                }
            }

        }

    private var personalityDataList: ArrayList<PersonalityDesignDataResponse>? = null


    fun PersonalityDataApi(onListReceived: (ArrayList<PersonalityDesignDataResponse>) -> Unit) =
        viewModelScope.launch {

            if (!personalityDataList.isNullOrEmpty()) {
                onListReceived.invoke(personalityDataList!!)
                return@launch
            }

            when (val data = repository.personalityData()) {
                is Resource.Error -> {
                    Log.d("TAG", "getZodiacList: " + data.message)

                }
                is Resource.Success -> {
                    personalityDataList = data.data?.data

                    if (!personalityDataList.isNullOrEmpty()) onListReceived.invoke(
                        personalityDataList!!,
                    )
                }
            }

        }


}



