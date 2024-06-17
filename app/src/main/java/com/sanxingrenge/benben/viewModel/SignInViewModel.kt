package com.sanxingrenge.benben.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import com.sanxingrenge.benben.constant.dataStoreGetTempLocation
import com.sanxingrenge.benben.constant.dataStoreGetUserData
import com.sanxingrenge.benben.constant.dataStoreSetTempLocation
import com.sanxingrenge.benben.constant.dataStoreSetUserData
import com.sanxingrenge.benben.dataModel.UserDataObject
import com.sanxingrenge.benben.retrofilt.MainRepo
import com.sanxingrenge.benben.retrofilt.Resource
import com.sanxingrenge.benben.screens.loginRegisterModule.SignInScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URLEncoder
import javax.inject.Inject

private const val TAG = "SignInViewModel"

@HiltViewModel
class SignInViewModel @Inject constructor(private val mainRepo: MainRepo) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInScreenState.EmailOtp)
    val uiState: StateFlow<SignInScreenState> = _uiState
    fun updateUiState(state: SignInScreenState, tag: Int) {
        _uiState.value = state
        Log.d(TAG, "updateUiState: testStateChanged>>$tag>>${state.name}")
    }

    init {
        viewModelScope.launch {
            uiState.collect {

                Log.d(TAG, "testDataSetChanged: ${it.name}")

            }
        }
    }

    private var userDataObject: UserDataObject? = null
    fun getStoredEmail(context: Context, onReceived: (String) -> Unit) = viewModelScope.launch {
        userDataObject = context.dataStoreGetUserData().firstOrNull()
        onReceived.invoke(userDataObject?.email ?: "")
        Log.d(TAG, "getStoredEmailsd: "+userDataObject?.email)
    }

    fun callVerifyOtpAndRegisterApi(
        strOtp: String,
        context: Context,
        onComplete: (status: Boolean) -> Unit
    ) = viewModelScope.launch {

        val locationData = context.dataStoreGetTempLocation().firstOrNull()
        val userData = context.dataStoreGetUserData().firstOrNull()
        val response = mainRepo.registerUser(
            email = userData?.email ?: "",
            password = userData?.pwd ?: "",
            register_otp = strOtp,
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
            address = locationData?.name ?: "",
            lat = locationData?.lat ?: "",
            long = locationData?.long ?: "",
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
                        onComplete.invoke(true)
                    }


                } else {
                    onComplete.invoke(false)
                }


            }
        }

    }

    fun getEmailPassword(
        context: Context,
        email: String,
        password: String,
        onListReceived: (Boolean, String) -> Unit
    ) = viewModelScope.launch {

        when (val data = mainRepo.getSignInWithPassword(email, password)) {
            is Resource.Error -> {
                onListReceived.invoke(data.data?.status?:true, data.data?.message?:"")

            }
            is Resource.Success -> {
                if (data.data?.status == true) {
                    viewModelScope.launch {
                        launch {
                            val userDataNew =
                                context.dataStoreGetUserData().firstOrNull() ?: return@launch
                            context.dataStoreSetUserData(
                                userDataNew.copy(
                                    email = data.data.data?.email,
                                    name = data.data.data?.name,
                                    bday = data.data.data?.birthdate,
                                    religion = data.data.data?.religion_id,
                                    gender = data.data.data?.gender,
                                    education = data.data.data?.education_id,
                                    occupation = data.data.data?.occupation,
                                    height = data.data.data?.height,
                                    weight = data.data.data?.weight,
                                    relationship = data.data.data?.relationship_id,
                                    maritalStatus = data.data.data?.marital_status_id,
                                    kids = data.data.data?.kid_id,
                                    address = data.data.data?.address,
                                    latitude = data.data.data?.latitude,
                                    longitude = data.data.data?.longitude,
                                    imageUrl = data.data.data?.image,
                                    userId = data.data.data?.id,
                                    age = (data.data.data?.age ?: 0).toString(),
                                ), "104"
                            )
                        }.join()

                        FirebaseMessaging.getInstance().token
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val token = task.result
                                    Log.d(TAG, "callVerifyOtpAndRegisterApi:474 "+token)

                                    viewModelScope.launch(Dispatchers.IO) {
                                        val userDataNew =
                                            context.dataStoreGetUserData().firstOrNull() ?: return@launch
                                        Log.d(TAG, "callVerifyOtpAndRegisterApi:479 "+userDataNew.userId)

                                        mainRepo.updateFcmToken(userDataNew.userId.toString(),token)
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
                    onListReceived.invoke(data.data.status, data.data.message?:"")



                } else {
                    onListReceived.invoke(data.data?.status?:true, data.data?.message?:"")
                }


            }

        }

    }


    var tempEmail = ""
    var userId = 0

    fun getEmailOTp(
        email: String,
        is_forgot_pwd: String,
        onListReceived: (Boolean, String, String) -> Unit
    ) = viewModelScope.launch {


        tempEmail = email

       // val encodedEmail = URLEncoder.encode(tempEmail, "UTF-8")

       //  val encodedEmail2=encodedEmail.replace("%2540", "@")

        when (val data = mainRepo.getSignInWithEmail(tempEmail,is_forgot_pwd)) {
            is Resource.Error -> {
                Log.d("TAG", "getZodiacList: " + data.message)
                onListReceived.invoke(false, data.message.toString(), "")

            }
            is Resource.Success -> {
                if (data.data?.status == true) {
                    onListReceived.invoke(
                        true,
                        data.data.message.toString(),
                        data.data.otp.toString()
                    )
                } else {

                    onListReceived.invoke(false, data.data?.message.toString(), "")
                }


            }

        }

    }

    fun getLoginWithOtp(
        otp: String,
        context: Context,
        onListReceived: (Boolean, String) -> Unit
    ) = viewModelScope.launch {


        when (val data = mainRepo.getLoginWithOtp(tempEmail, otp)) {
            is Resource.Error -> {
                Log.d("TAG", "getZodiacList: " + data.message)
                onListReceived.invoke(false, data.message.toString())

            }
            is Resource.Success -> {
                if (data.data?.status == true) {
                    onListReceived.invoke(true, data.data.message.toString())
                    viewModelScope.launch {
                        launch {
                            val userDataNew =
                                context.dataStoreGetUserData().firstOrNull() ?: return@launch
                            context.dataStoreSetUserData(
                                userDataNew.copy(
                                    email = data.data.data?.email,
                                    name = data.data.data?.name,
                                    bday = data.data.data?.birthdate,
                                    religion = data.data.data?.religion_id,
                                    gender = data.data.data?.gender,
                                    education = data.data.data?.education_id,
                                    occupation = data.data.data?.occupation,
                                    height = data.data.data?.height,
                                    weight = data.data.data?.weight,
                                    relationship = data.data.data?.relationship_id,
                                    maritalStatus = data.data.data?.marital_status_id,
                                    kids = data.data.data?.kid_id,
                                    address = data.data.data?.address,
                                    latitude = data.data.data?.latitude,
                                    longitude = data.data.data?.longitude,
                                    imageUrl = data.data.data?.image,
                                    userId = data.data.data?.id,
                                    age = (data.data.data?.age ?: 0).toString(),
                                ), "235"
                            )
                        }.join()
                        FirebaseMessaging.getInstance().token
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val token = task.result
                                    Log.d(TAG, "callVerifyOtpAndRegisterApi:474 "+token)

                                    viewModelScope.launch(Dispatchers.IO) {
                                        val userDataNew =
                                            context.dataStoreGetUserData().firstOrNull() ?: return@launch
                                        Log.d(TAG, "callVerifyOtpAndRegisterApi:479 "+userDataNew.userId)

                                        mainRepo.updateFcmToken(userDataNew.userId.toString(),token)
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

                        context.dataStoreSetTempLocation("", "", "", 238)
                    }
                } else {

                    onListReceived.invoke(false, data.data?.message.toString())
                }


            }

        }

    }




    fun getOtpVerifyForPwd(
        otp: String,
        context: Context,
        onListReceived: (Boolean, String) -> Unit
    ) = viewModelScope.launch {


        when (val data = mainRepo.getOtpVerifyForPwd(tempEmail, otp)) {
            is Resource.Error -> {
                Log.d("TAG", "getZodiacList: " + data.message)
                onListReceived.invoke(false, data.message.toString())

            }
            is Resource.Success -> {
                if (data.data?.status == true) {
                    userId= data.data.user_id!!

                    onListReceived.invoke(true, data.data.message.toString())

                } else {

                    onListReceived.invoke(false, data.data?.message.toString())
                }


            }

        }

    }

    fun changeForgotPwd(
        password: String,
        Confrim_password: String,
        onListReceived: (Boolean, String) -> Unit
    ) = viewModelScope.launch {


        when (val data = mainRepo.changeForgotPwd(userId.toString(),password,Confrim_password)) {
            is Resource.Error -> {
                Log.d("TAG", "getZodiacList318: " + data.message)
                onListReceived.invoke(false, data.message.toString())

            }
            is Resource.Success -> {
                if (data.data?.status == true) {
                    onListReceived.invoke(true, data.data.message.toString())

                } else {

                    onListReceived.invoke(false, data.data?.message.toString())
                }


            }

        }

    }


    fun getVerifyRegister(
        emailSee: String,
        otp: String,
        context: Context,
        onListReceived: (Boolean, String) -> Unit
    ) = viewModelScope.launch {

        val userObj = context.dataStoreGetUserData().firstOrNull()?:return@launch
        val email = userObj.email
        when (val data = mainRepo.getVerifyRegister(emailSee?:"", otp)) {
            is Resource.Error -> {
                Log.d("TAG", "getZodiacList: " + data.message)
                onListReceived.invoke(data.data?.status?:true, data.data?.message?:"")

            }
            is Resource.Success -> {

                onListReceived.invoke(data.data?.status?:true, data.data?.message?:"")
                if (data.data?.status == true) {
                    Log.d("TAG", "getZodiacasdasdList: " + emailSee)
                    Log.d("TAG", "getZodiacasdasdList: " + otp)

                    context.dataStoreSetUserData(userObj.copy(email=emailSee,otp = otp), "380")

                  //  onListReceived.invoke(data.data.status, data.data.message.toString())

                } else {

                   // onListReceived.invoke(data.data?.status?:true, data.data?.message.toString())
                }


            }

        }

    }


}



