package com.sanxingrenge.benben.retrofilt

import android.util.Log
import com.sanxingrenge.benben.dataModel.SignVideoCallResponse
import com.sanxingrenge.benben.responseModel.SocialLoginResponse
import com.sanxingrenge.benben.responseModel.AddQuestionsAnswerResponse
import com.sanxingrenge.benben.responseModel.AgreementResponse
import com.sanxingrenge.benben.responseModel.AllListResponse
import com.sanxingrenge.benben.responseModel.ChatResponse
import com.sanxingrenge.benben.responseModel.ChinesZodicResponse
import com.sanxingrenge.benben.responseModel.ConstellationResponse
import com.sanxingrenge.benben.responseModel.ConversionRsponse
import com.sanxingrenge.benben.responseModel.DiscoverResponse
import com.sanxingrenge.benben.responseModel.FriendRequestResponse
import com.sanxingrenge.benben.responseModel.GetFeedBackListResponse
import com.sanxingrenge.benben.responseModel.GetQuestionsListResponse
import com.sanxingrenge.benben.responseModel.PersonalityDesignResponse
import com.sanxingrenge.benben.responseModel.PersonlityResponse
import com.sanxingrenge.benben.responseModel.RegisterResponse
import com.sanxingrenge.benben.responseModel.SearchUserResponse
import com.sanxingrenge.benben.responseModel.SendOtpResponse
import com.sanxingrenge.benben.responseModel.SignInEmailPasswordResponse
import com.sanxingrenge.benben.responseModel.SubsctiptionResponse
import com.sanxingrenge.benben.responseModel.UpdaterFCMResponse
import com.sanxingrenge.benben.responseModel.UpdaterResponse
import com.sanxingrenge.benben.responseModel.VerifyOtpResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.HashMap
import javax.inject.Inject

class DefaultMainRepo
@Inject constructor(private val apiService: ApiServiceClass) : MainRepo {

    val clientSecret =
        "NPaKyJ6L786p4R4dA22FJvIVzLvUhEty26ehPHwx2xKPEH7Bj4KkT2dzIyeSrBd5"

    override suspend fun getZodiacList(
        email: String
    ): Resource<AllListResponse> {
        return try {

            val parts: MutableMap<String, RequestBody> = HashMap()

            parts["email"] = email.reqBody()

            val response = apiService.getZodiacList(
                clientSecret,parts
            )
            if (response.isSuccessful) {
                Log.d("TAG", "getZodiacList: " + 27)
                Resource.Success(response.body(), "")
            } else {
                Log.d("TAG", "getZodiacList: " + 30)
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }


    override suspend fun getSendEmailOtp(email: String): Resource<SendOtpResponse> {
        return try {
            val response = apiService.getSendEmailOtp(
                clientSecret, email
            )
            if (response.isSuccessful) {
                Log.d("TAG", "getZodiacList: " + 27)
                Resource.Success(response.body(), "")
            } else {
                Log.d("TAG", "getZodiacList: " + 30)
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }

    override suspend fun getVerifyRegister(
        email: String,
        register_otp: String
    ): Resource<VerifyOtpResponse> {
        return try {

            val response = apiService.getVerifyRegister(
                clientSecret, email, register_otp
            )
            if (response.isSuccessful) {
                Log.d("TAG", "getZodiacList: " + 27)
                Resource.Success(response.body(), "")
            } else {
                Log.d("TAG", "getZodiacList: " + 30)
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }

    override suspend fun getSignInWithPassword(
        email: String,
        password: String
    ): Resource<SignInEmailPasswordResponse> {
        return try {

            val response = apiService.getSignInWithPassword(
                clientSecret, email, password
            )
            if (response.isSuccessful) {
                Log.d("TAG", "getZodiacList: " + 27)
                Resource.Success(response.body(), "")
            } else {
                Log.d("TAG", "getZodiacList: " + 30)
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }

    override suspend fun readMessage(
        currantId: String,
        otherId: String
    ): Resource<String> {
        return try {

            val response = apiService.readMessage(
                clientSecret, currantId, otherId
            )
            if (response.isSuccessful) {
                Log.d("TAG", "getZodiacList: " + 27)
                Resource.Success(response.body(), "")
            } else {
                Log.d("TAG", "getZodiacList: " + 30)
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }

    override suspend fun getSignInWithEmail(
        email: String,
        is_forgot_pwd: String
    ): Resource<SendOtpResponse> {
        return try {

            val parts: MutableMap<String, RequestBody> = HashMap()
            parts["is_forgot_pwd"] = is_forgot_pwd.reqBody()

            val response = apiService.getSignInWithEmail(
                clientSecret, email.reqBody(), parts
            )
            if (response.isSuccessful) {
                Log.d("TAG", "getZodiacList: " + 27)
                Resource.Success(response.body(), "")
            } else {
                Log.d("TAG", "getZodiacList: " + 30)
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }

    override suspend fun changeForgotEmailSentOtp(email: String): Resource<SendOtpResponse> {
        return try {
            val response = apiService.changeForgotEmailSentOtp(
                clientSecret, email.reqBody()
            )
            if (response.isSuccessful) {
                Log.d("TAG", "getZodiacList: " + 27)
                Resource.Success(response.body(), "")
            } else {
                Log.d("TAG", "getZodiacList: " + 30)
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }
    override suspend fun removeProfileImage(image_id: String): Resource<SendOtpResponse> {
        return try {
            val response = apiService.removeProfileImage(
                clientSecret, image_id
            )
            if (response.isSuccessful) {
                Log.d("TAG", "getZodiacList: " + 27)
                Resource.Success(response.body(), "")
            } else {
                Log.d("TAG", "getZodiacList: " + 30)
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }

    override suspend fun getLoginWithOtp(
        email: String,
        otp: String
    ): Resource<SignInEmailPasswordResponse> {
        return try {
            val parts: MutableMap<String, RequestBody> = HashMap()
            parts["otp"] = otp.reqBody()

            val response = apiService.getLoginWithOtp(
                clientSecret, email.reqBody(), parts
            )
            if (response.isSuccessful) {
                Resource.Success(response.body(), "")
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }

    override suspend fun changeForgotPwd(
        user_id: String,
        password: String,
        password_confirmation: String,
    ): Resource<SignInEmailPasswordResponse> {
        return try {
            val parts: MutableMap<String, RequestBody> = HashMap()
            parts["user_id"] = user_id.reqBody()
            parts["password"] = password.reqBody()
            parts["password_confirmation"] = password_confirmation.reqBody()

            val response = apiService.changeForgotPwd(
                clientSecret, parts
            )
            if (response.isSuccessful) {
                Resource.Success(response.body(), "")
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }

    override suspend fun getOtpVerifyForPwd(
        email: String,
        otp: String
    ): Resource<SignInEmailPasswordResponse> {
        return try {
            val parts: MutableMap<String, RequestBody> = HashMap()
            parts["forgot_otp"] = otp.reqBody()

            val response = apiService.getOtpVerifyForPwd(
                clientSecret, email.reqBody(), parts
            )
            if (response.isSuccessful) {
                Resource.Success(response.body(), "")
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }

    override suspend fun registerUser(
        email: String,
        password: String,
        register_otp: String,
        name: String,
        birthdate: String,
        religion_id: String,
        gender: String,
        education_id: String,
        occupation: String,
        height: String,
        weight: String,
        relationship_id: String,
        marital_status_id: String,
        kid_id: String,
        zodiac_sign_id: String,
        animal_sign_id: String,
        bio: String,
        faceBookLogin: String,
        googleLogin: String,
        image: String,
        image22: String,
        image33: String,
        image44: String,
        address: String,
        lat: String,
        long: String,
        interestId1: String,
        interestId2: String,
        interestId3: String,
        interestId4: String,
        interestId5: String,
        interestLevel1: String,
        interestLevel2: String,
        interestLevel3: String,
        interestLevel4: String,
        interestLevel5: String,
    ): Resource<RegisterResponse> {
        val imageStrArray = ArrayList<String>()
        if (image != "") imageStrArray.add(image)
        if (image22 != "") imageStrArray.add(image22)
        if (image33 != "") imageStrArray.add(image33)
        if (image44 != "") imageStrArray.add(image44)

        return try {

            Log.d("TAG", "registerUser:321 "+bio)
            val parts: MutableMap<String, RequestBody> = HashMap()

            parts["email"] = email.reqBody()
            parts["password"] = password.reqBody()
            parts["register_otp"] = register_otp.reqBody()
            parts["name"] = name.reqBody()
            parts["birthdate"] = birthdate.reqBody()
            //parts["country_id"] = country_id.reqBody()
            parts["religion_id"] = religion_id.reqBody()
            parts["gender"] = gender.reqBody()
            parts["education_id"] = education_id.reqBody()
            parts["occupation"] = occupation.reqBody()
            parts["height"] = height.reqBody()
            parts["weight"] = weight.reqBody()
            parts["relationship_id"] = relationship_id.reqBody()
            parts["marital_status_id"] = marital_status_id.reqBody()
            parts["kid_id"] = kid_id.reqBody()
            parts["zodiac_sign_id"] = zodiac_sign_id.reqBody()
            parts["animal_sign_id"] = animal_sign_id.reqBody()
            parts["latitude"] = lat.reqBody()
            parts["longitude"] = long.reqBody()
            parts["address"] = address.reqBody()
            parts["bio"] = bio.reqBody()
            parts["facebook_id"] = faceBookLogin.reqBody()
            parts["google_id"] = googleLogin.reqBody()
            //parts["city_id"] = city_id.reqBody()

            var countFlow = 0

            if (interestId1.isNotEmpty() && interestId1 != "null" && interestLevel1.isNotEmpty() && interestLevel1 != "null") {
                parts["interests[$countFlow][id]"] = interestId1.reqBody()
                parts["interests[$countFlow][level]"] = interestLevel1.reqBody()
                countFlow += 1
            }
            if (interestId2.isNotEmpty() && interestId2 != "null" && interestLevel2.isNotEmpty() && interestLevel2 != "null") {
                parts["interests[$countFlow][id]"] = interestId2.reqBody()
                parts["interests[$countFlow][level]"] = interestLevel2.reqBody()
                countFlow += 1
            }
            if (interestId3.isNotEmpty() && interestId3 != "null" && interestLevel3.isNotEmpty() && interestLevel3 != "null") {
                parts["interests[$countFlow][id]"] = interestId3.reqBody()
                parts["interests[$countFlow][level]"] = interestLevel3.reqBody()
                countFlow += 1
            }
            if (interestId4.isNotEmpty() && interestId4 != "null" && interestLevel4.isNotEmpty() && interestLevel4 != "null") {
                parts["interests[$countFlow][id]"] = interestId4.reqBody()
                parts["interests[$countFlow][level]"] = interestLevel4.reqBody()
                countFlow += 1
            }
            if (interestId5.isNotEmpty() && interestId5 != "null" && interestLevel5.isNotEmpty() && interestLevel5 != "null") {
                parts["interests[$countFlow][id]"] = interestId5.reqBody()
                parts["interests[$countFlow][level]"] = interestLevel5.reqBody()
                countFlow += 1
            }



            var imagePart1: MultipartBody.Part? = null
            var imagePart2: MultipartBody.Part? = null
            var imagePart3: MultipartBody.Part? = null
            var imagePart4: MultipartBody.Part? = null


            try {
                imageStrArray.forEachIndexed { index, str ->
                    if (str != "" && File(str).exists()) {
                        when (index) {
                            0 -> {
                                imagePart1 = MultipartBody.Part.createFormData(
                                    "image[0]",
                                    "myPic1",
                                    File(str).asRequestBody("image/*".toMediaTypeOrNull())
                                )
                            }
                            1 -> {
                                imagePart2 = MultipartBody.Part.createFormData(
                                    "image[1]",
                                    "myPic2",
                                    File(str).asRequestBody("image/*".toMediaTypeOrNull())
                                )
                            }
                            2 -> {
                                imagePart3 = MultipartBody.Part.createFormData(
                                    "image[2]",
                                    "myPic3",
                                    File(str).asRequestBody("image/*".toMediaTypeOrNull())
                                )
                            }
                            3 -> {
                                imagePart4 = MultipartBody.Part.createFormData(
                                    "image[3]",
                                    "myPic4",
                                    File(str).asRequestBody("image/*".toMediaTypeOrNull())
                                )
                            }
                        }
                    }
                }
                // val imageFile = File(image)
                // if (image != "" && image != "null" && imageFile.exists()) {
                //     imagePart = MultipartBody.Part.createFormData(
                //         "image",
                //         "myPic1",
                //         imageFile.asRequestBody("image/*".toMediaTypeOrNull())
                //     )
                //
                // }

            } catch (e: Exception) {
                e.printStackTrace()
            }

            val resp = apiService.registerUser(
                token = clientSecret, parts,
                image1 = imagePart1,
                image2 = imagePart2,
                image3 = imagePart3,
                image4 = imagePart4,
            )

            try {
                File(image).delete()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            if (resp.isSuccessful) Resource.Success(resp.body(), "")
            else Resource.Error(resp.message())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }

    override suspend fun updateUser(
        user_id: String,
        email: String,
        register_otp: String,
        name: String,
        birthdate: String,
        religion_id: String,
        gender: String,
        education_id: String,
        occupation: String,
        height: String,
        weight: String,
        relationship_id: String,
        marital_status_id: String,
        kid_id: String,
        zodiac_sign_id: String,
        animal_sign_id: String,
        image: String,
        image22: String,
        image33: String,
        image44: String,
        address: String,
        bio: String,
        lat: String,
        long: String,
        interestId1: String,
        interestId2: String,
        interestId3: String,
        interestId4: String,
        interestId5: String,
        interestLevel1: String,
        interestLevel2: String,
        interestLevel3: String,
        interestLevel4: String,
        interestLevel5: String
    ): Resource<UpdaterResponse> {

        val imageStrArray = ArrayList<String>()
        if (image != "") imageStrArray.add(image)
        if (image22 != "") imageStrArray.add(image22)
        if (image33 != "") imageStrArray.add(image33)
        if (image44 != "") imageStrArray.add(image44)

        return try {

            val parts: MutableMap<String, RequestBody> = HashMap()

            parts["user_id"] = user_id.reqBody()
            parts["email"] = email.reqBody()
            parts["register_otp"] = register_otp.reqBody()
            parts["name"] = name.reqBody()
            parts["birthdate"] = birthdate.reqBody()
            //parts["country_id"] = country_id.reqBody()
            parts["religion_id"] = religion_id.reqBody()
            parts["gender"] = gender.reqBody()
            parts["education_id"] = education_id.reqBody()
            parts["occupation"] = occupation.reqBody()
            parts["height"] = height.reqBody()
            parts["weight"] = weight.reqBody()
            parts["relationship_id"] = relationship_id.reqBody()
            parts["marital_status_id"] = marital_status_id.reqBody()
            parts["kid_id"] = kid_id.reqBody()
            parts["zodiac_sign_id"] = zodiac_sign_id.reqBody()
            parts["animal_sign_id"] = animal_sign_id.reqBody()
            parts["latitude"] = lat.reqBody()
            parts["longitude"] = long.reqBody()
            parts["address"] = address.reqBody()
            parts["bio"] = bio.reqBody()
            //parts["city_id"] = city_id.reqBody()

            var countFlow = 0

            if (interestId1.isNotEmpty() && interestId1 != "null" && interestLevel1.isNotEmpty() && interestLevel1 != "null") {
                parts["interests[$countFlow][id]"] = interestId1.reqBody()
                parts["interests[$countFlow][level]"] = interestLevel1.reqBody()
                countFlow += 1
            }
            if (interestId2.isNotEmpty() && interestId2 != "null" && interestLevel2.isNotEmpty() && interestLevel2 != "null") {
                parts["interests[$countFlow][id]"] = interestId2.reqBody()
                parts["interests[$countFlow][level]"] = interestLevel2.reqBody()
                countFlow += 1
            }
            if (interestId3.isNotEmpty() && interestId3 != "null" && interestLevel3.isNotEmpty() && interestLevel3 != "null") {
                parts["interests[$countFlow][id]"] = interestId3.reqBody()
                parts["interests[$countFlow][level]"] = interestLevel3.reqBody()
                countFlow += 1
            }
            if (interestId4.isNotEmpty() && interestId4 != "null" && interestLevel4.isNotEmpty() && interestLevel4 != "null") {
                parts["interests[$countFlow][id]"] = interestId4.reqBody()
                parts["interests[$countFlow][level]"] = interestLevel4.reqBody()
                countFlow += 1
            }
            if (interestId5.isNotEmpty() && interestId5 != "null" && interestLevel5.isNotEmpty() && interestLevel5 != "null") {
                parts["interests[$countFlow][id]"] = interestId5.reqBody()
                parts["interests[$countFlow][level]"] = interestLevel5.reqBody()
                countFlow += 1
            }



            var imagePart1: MultipartBody.Part? = null
            var imagePart2: MultipartBody.Part? = null
            var imagePart3: MultipartBody.Part? = null
            var imagePart4: MultipartBody.Part? = null


            try {
                imageStrArray.forEachIndexed { index, str ->
                    if (str != "" && File(str).exists()) {
                        when (index) {
                            0 -> {
                                imagePart1 = MultipartBody.Part.createFormData(
                                    "image[0]",
                                    "myPic1",
                                    File(str).asRequestBody("image/*".toMediaTypeOrNull())
                                )
                            }
                            1 -> {
                                imagePart2 = MultipartBody.Part.createFormData(
                                    "image[1]",
                                    "myPic2",
                                    File(str).asRequestBody("image/*".toMediaTypeOrNull())
                                )
                            }
                            2 -> {
                                imagePart3 = MultipartBody.Part.createFormData(
                                    "image[2]",
                                    "myPic3",
                                    File(str).asRequestBody("image/*".toMediaTypeOrNull())
                                )
                            }
                            3 -> {
                                imagePart4 = MultipartBody.Part.createFormData(
                                    "image[3]",
                                    "myPic4",
                                    File(str).asRequestBody("image/*".toMediaTypeOrNull())
                                )
                            }
                        }
                    }
                }
                // val imageFile = File(image)
                // if (image != "" && image != "null" && imageFile.exists()) {
                //     imagePart = MultipartBody.Part.createFormData(
                //         "image",
                //         "myPic1",
                //         imageFile.asRequestBody("image/*".toMediaTypeOrNull())
                //     )
                //
                // }

            } catch (e: Exception) {
                e.printStackTrace()
            }

            val resp = apiService.updateUser(
                token = clientSecret, parts,
                image1 = imagePart1,
                image2 = imagePart2,
                image3 = imagePart3,
                image4 = imagePart4,
            )

            try {
                File(image).delete()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            if (resp.isSuccessful) Resource.Success(resp.body(), "")
            else Resource.Error(resp.message())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }

    override suspend fun getUserProfile(
        user_id: Int,
        login_user_id: Int
    ): Resource<UpdaterResponse> {
        return try {

            val response = apiService.getUserProfile(
                clientSecret, user_id, login_user_id
            )
            if (response.isSuccessful) {
                Log.d("TAG", "getZodiacList: " + 368)
                Resource.Success(response.body(), "")
            } else {
                Log.d("TAG", "getZodiacList: " + 371)
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }

    override suspend fun updateFcmToken(user_id: String, fcm_token: String): Resource<UpdaterFCMResponse> {
        return try {

            val response = apiService.updateFcmToken(
                clientSecret, user_id, fcm_token
            )
            if (response.isSuccessful) {
                Log.d("TAG", "getZodiacList: " + 368)
                Resource.Success(response.body(), "")
            } else {
                Log.d("TAG", "getZodiacList: " + 371)
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }

    override suspend fun singleVideoCall(
        receiver_id: String,
        sender_id: String
    ): Resource<SignVideoCallResponse> {
        return try {

            val response = apiService.singleVideoCall(
                clientSecret, receiver_id, sender_id
            )
            if (response.isSuccessful) {
                Log.d("TAG", "getZodiacList: " + 368)
                Resource.Success(response.body(), "")
            } else {
                Log.d("TAG", "getZodiacList: " + 371)
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }

    override suspend fun logout(user_id: String): Resource<FriendRequestResponse> {
        return try {

            val response = apiService.logout(
                clientSecret, user_id
            )
            if (response.isSuccessful) {
                Log.d("TAG", "getZodiacList: " + 368)
                Resource.Success(response.body(), "")
            } else {
                Log.d("TAG", "getZodiacList: " + 371)
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }

    override suspend fun sociallogin(social_id: String): Resource<SocialLoginResponse> {
        return try {

            val response = apiService.sociallogin(
                clientSecret, social_id
            )
            if (response.isSuccessful) {
                Log.d("TAG", "getZodiacList: " + 368)
                Resource.Success(response.body(), "")
            } else {
                Log.d("TAG", "getZodiacList: " + 371)
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }

    override suspend fun getFriendRequestList(user_id: Int): Resource<FriendRequestResponse> {
        return try {

            val response = apiService.getFriendRequestList(
                clientSecret, user_id
            )
            if (response.isSuccessful) {
                Log.d("TAG", "getZodiacList: " + 368)
                Resource.Success(response.body(), "")
            } else {
                Log.d("TAG", "getZodiacList: " + 371)
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }


    override suspend fun getUserListOfMessage(user_id: Int): Resource<ConversionRsponse> {
        return try {

            val response = apiService.getUserListOfMessage(
                clientSecret, user_id
            )
            if (response.isSuccessful) {
                Log.d("TAG", "getZodiacList: " + 368)
                Resource.Success(response.body(), "")
            } else {
                Log.d("TAG", "getZodiacList: " + 371)
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }

    override suspend fun approveFrinendRequest(
        friend_request_id: Int,
        status: String
    ): Resource<FriendRequestResponse> {
        return try {

            val response = apiService.approveFrinendRequest(
                clientSecret, friend_request_id, status
            )
            if (response.isSuccessful) {
                Log.d("TAG", "getZodiacList: " + 368)
                Resource.Success(response.body(), "")
            } else {
                Log.d("TAG", "getZodiacList: " + 371)
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)
        }

    }

    override suspend fun sendFriendRequest(
        sender_id: Int,
        receiver_id: Int
    ): Resource<DiscoverResponse> {
        return try {

            val response = apiService.sendFriendRequest(
                clientSecret, sender_id, receiver_id
            )
            if (response.isSuccessful) {
                Log.d("TAG", "getZodiacList: " + 368)
                Resource.Success(response.body(), "")
            } else {
                Log.d("TAG", "getZodiacList: " + 371)
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)
        }

    }


    override suspend fun getConstellationList(): Resource<ConstellationResponse> {
        return try {

            val parts: MutableMap<String, RequestBody> = HashMap()

            parts["email"] = "".reqBody()
            val response = apiService.getConstellationList(
                clientSecret, parts
            )
            if (response.isSuccessful) {
                Log.d("TAG", "getZodiacList: " + 346)
                Resource.Success(response.body(), "")
            } else {
                Log.d("TAG", "getZodiacList: " + 349)
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }

    override suspend fun getChinesZodiacList(): Resource<ChinesZodicResponse> {
        return try {

            val parts: MutableMap<String, RequestBody> = HashMap()

            parts["email"] = "".reqBody()
            val response = apiService.getChinesZodiacList(
                clientSecret, parts
            )
            if (response.isSuccessful) {
                Log.d("TAG", "getZodiacList: " + 368)
                Resource.Success(response.body(), "")
            } else {
                Log.d("TAG", "getZodiacList: " + 371)
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }

    /*override suspend fun getUserFilter(
        gender: String,
        user_id: String,
        relationship_id: String,
        address: String,
        lat: String,
        long: String,
        interestId1: String,
        interestId2: String,
        interestId3: String,
        interestId4: String,
        interestId5: String,
        interestLevel1: String,
        interestLevel2: String,
        interestLevel3: String,
        interestLevel4: String,
        interestLevel5: String
    ): Resource<DiscoverResponse> {
        return try {

            val parts = linkedMapOf<String, RequestBody>()

            parts["gender"] = gender.reqBody()
            parts["user_id"] = user_id.reqBody()
            parts["relationship_id"] = relationship_id.reqBody()
            parts["latitude"] = lat.reqBody()
            parts["longitude"] = long.reqBody()
            parts["address"] = address.reqBody()

            var countFlow = 0

            if (interestId1.isNotEmpty() && interestId1 != "null" && interestLevel1.isNotEmpty() && interestLevel1 != "null") {
                parts["interests[$countFlow][id]"] = interestId1.reqBody()
                parts["interests[$countFlow][level]"] = interestLevel1.reqBody()
                countFlow += 1
            }
            if (interestId2.isNotEmpty() && interestId2 != "null" && interestLevel2.isNotEmpty() && interestLevel2 != "null") {
                parts["interests[$countFlow][id]"] = interestId2.reqBody()
                parts["interests[$countFlow][level]"] = interestLevel2.reqBody()
                countFlow += 1
            }
            if (interestId3.isNotEmpty() && interestId3 != "null" && interestLevel3.isNotEmpty() && interestLevel3 != "null") {
                parts["interests[$countFlow][id]"] = interestId3.reqBody()
                parts["interests[$countFlow][level]"] = interestLevel3.reqBody()
                countFlow += 1
            }
            if (interestId4.isNotEmpty() && interestId4 != "null" && interestLevel4.isNotEmpty() && interestLevel4 != "null") {
                parts["interests[$countFlow][id]"] = interestId4.reqBody()
                parts["interests[$countFlow][level]"] = interestLevel4.reqBody()
                countFlow += 1
            }
            if (interestId5.isNotEmpty() && interestId5 != "null" && interestLevel5.isNotEmpty() && interestLevel5 != "null") {
                parts["interests[$countFlow][id]"] = interestId5.reqBody()
                parts["interests[$countFlow][level]"] = interestLevel5.reqBody()
                countFlow += 1
            }

            val resp = apiService.getUserFilter(token = clientSecret, parts)
            Log.d("TAG", "getUserFilter701: "+resp.body())
            Log.d("TAG", "getUserFilter701: "+resp.message())
            if (resp.isSuccessful) {
                Resource.Success(resp.body(), "")
            }
            else{
                Resource.Error(resp.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }*/

    override suspend fun getUserFilter(
        gender: String,
        user_id: String,
        lookingFor: String,
        lat: String,
        long: String,
        interestId1: String,
        interestId2: String,
        interestId3: String,
        interestId4: String,
        interestId5: String,
        interestLevel1: String,
        interestLevel2: String,
        interestLevel3: String,
        interestLevel4: String,
        interestLevel5: String
    ): Resource<DiscoverResponse> {
        return try {
            //  val parts = linkedMapOf<String, RequestBody>()
            val parts: MutableMap<String, RequestBody> = HashMap()
            var countFlow = 0
            parts["gender"] = gender.reqBody()
            parts["user_id"] = user_id.reqBody()
            Log.d("TAG", "getUserFilter32123: " + lookingFor)
            /*parts["looking_for"] = (lookingFor?.reqBody() ?: "") as RequestBody*/
            parts["looking_for"] = lookingFor.reqBody()
            parts["latitude"] = lat.reqBody()
            parts["longitude"] = long.reqBody()


            if (interestId1.isNotEmpty() && interestId1 != "null" && interestLevel1.isNotEmpty() && interestLevel1 != "null") {
                parts["interests[$countFlow][id]"] = interestId1.reqBody()
                parts["interests[$countFlow][level]"] = interestLevel1.toLowerCase().reqBody()
                countFlow += 1
            }
            if (interestId2.isNotEmpty() && interestId2 != "null" && interestLevel2.isNotEmpty() && interestLevel2 != "null") {
                parts["interests[$countFlow][id]"] = interestId2.reqBody()
                parts["interests[$countFlow][level]"] = interestLevel2.toLowerCase().reqBody()
                countFlow += 1
            }
            if (interestId3.isNotEmpty() && interestId3 != "null" && interestLevel3.isNotEmpty() && interestLevel3 != "null") {
                parts["interests[$countFlow][id]"] = interestId3.reqBody()
                parts["interests[$countFlow][level]"] = interestLevel3.toLowerCase().reqBody()
                countFlow += 1
            }
            if (interestId4.isNotEmpty() && interestId4 != "null" && interestLevel4.isNotEmpty() && interestLevel4 != "null") {
                parts["interests[$countFlow][id]"] = interestId4.reqBody()
                parts["interests[$countFlow][level]"] = interestLevel4.toLowerCase().reqBody()
                countFlow += 1
            }
            if (interestId5.isNotEmpty() && interestId5 != "null" && interestLevel5.isNotEmpty() && interestLevel5 != "null") {
                parts["interests[$countFlow][id]"] = interestId5.reqBody()
                parts["interests[$countFlow][level]"] = interestLevel5.toLowerCase().reqBody()
                countFlow += 1
            }


            val resp = apiService.getUserFilter(token = clientSecret, parts)
            Log.d("TAG", "getUserFilter701: " + resp.body()?.status)
            Log.d("TAG", "getUserFilter701: " + resp.message())
            if (resp.isSuccessful) {
                Resource.Success(resp.body(), "")
            } else {
                Log.d("TAG", "getUserFiltesdr701: " + resp.message())
                Resource.Error(resp.message())

            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }


    override suspend fun getAgreementContentList(): Resource<AgreementResponse> {
        return try {
            val response = apiService.getAgreementContentList(
                clientSecret, ""
            )
            if (response.isSuccessful) {
                Log.d("TAG", "getZodiacList: " + 27)
                Resource.Success(response.body(), "")
            } else {
                Log.d("TAG", "getZodiacList: " + 30)
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }

    override suspend fun getEmailExist(email: String): Resource<SendOtpResponse> {
        return try {
            val response = apiService.getEmailExist(
                clientSecret, email
            )
            if (response.isSuccessful) {
                Log.d("TAG", "getZodiacList: " + 27)
                Resource.Success(response.body(), "")
            } else {
                Log.d("TAG", "getZodiacList: " + 30)
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }

    override suspend fun addFeedback(
        user_id: String,
        email: String,
        feedback_type: String,
        description: String,
        imageStr1: String,
        imageStr2: String,
        imageStr3: String,
        imageStr4: String,
        imageStr5: String,
        imageStr6: String,
    ): Resource<ChatResponse> {

        val imageStrArray = ArrayList<String>()
        if (imageStr1 != "") imageStrArray.add(imageStr1)
        if (imageStr2 != "") imageStrArray.add(imageStr2)
        if (imageStr3 != "") imageStrArray.add(imageStr3)
        if (imageStr4 != "") imageStrArray.add(imageStr4)
        if (imageStr5 != "") imageStrArray.add(imageStr5)
        if (imageStr6 != "") imageStrArray.add(imageStr6)

        return try {
            var imagePart1: MultipartBody.Part? = null
            var imagePart2: MultipartBody.Part? = null
            var imagePart3: MultipartBody.Part? = null
            var imagePart4: MultipartBody.Part? = null
            var imagePart5: MultipartBody.Part? = null
            var imagePart6: MultipartBody.Part? = null

            val parts: MutableMap<String, RequestBody> = HashMap()

            parts["user_id"] = user_id.reqBody()
            parts["email"] = email.reqBody()
            parts["feedback_type"] = feedback_type.reqBody()
            parts["description"] = description.reqBody()




            try {
                imageStrArray.forEachIndexed { index, str ->
                    if (str != "" && File(str).exists()) {
                        when (index) {
                            0 -> {
                                imagePart1 = MultipartBody.Part.createFormData(
                                    "images[0]",
                                    "myPic1",
                                    File(str).asRequestBody("image/*".toMediaTypeOrNull())
                                )
                            }

                            1 -> {
                                imagePart2 = MultipartBody.Part.createFormData(
                                    "images[1]",
                                    "myPic2",
                                    File(str).asRequestBody("image/*".toMediaTypeOrNull())
                                )
                            }

                            2 -> {
                                imagePart3 = MultipartBody.Part.createFormData(
                                    "images[2]",
                                    "myPic3",
                                    File(str).asRequestBody("image/*".toMediaTypeOrNull())
                                )
                            }

                            3 -> {
                                imagePart4 = MultipartBody.Part.createFormData(
                                    "images[3]",
                                    "myPic4",
                                    File(str).asRequestBody("image/*".toMediaTypeOrNull())
                                )
                            }

                            4 -> {
                                imagePart5 = MultipartBody.Part.createFormData(
                                    "images[4]",
                                    "myPic5",
                                    File(str).asRequestBody("image/*".toMediaTypeOrNull())
                                )
                            }

                            5 -> {
                                imagePart6 = MultipartBody.Part.createFormData(
                                    "images[5]",
                                    "myPic6",
                                    File(str).asRequestBody("image/*".toMediaTypeOrNull())
                                )
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()

            }


            val resp = apiService.addFeedback(
                token = clientSecret,
                parts,
                image1 = imagePart1,
                image2 = imagePart2,
                image3 = imagePart3,
                image4 = imagePart4,
                image5 = imagePart5,
                image6 = imagePart6,
            )
            if (resp.isSuccessful) Resource.Success(resp.body(), "")
            else Resource.Error(resp.message())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }

    override suspend fun getFeedback(user_id: Int): Resource<GetFeedBackListResponse> {
        return try {

            val response = apiService.getFeedback(
                clientSecret, user_id
            )
            if (response.isSuccessful) {
                Log.d("TAG", "getZodiacList: " + 368)
                Resource.Success(response.body(), "")
            } else {
                Log.d("TAG", "getZodiacList: " + 371)
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }

    override suspend fun changePassword(
        user_id: Int,
        currantPassword: String,
        password: String,
        passwordConfirm: String,
    ): Resource<GetFeedBackListResponse> {
        return try {

            val response = apiService.changePassword(
                clientSecret, user_id, currantPassword, password, passwordConfirm
            )
            if (response.isSuccessful) {
                Resource.Success(response.body(), "")
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }

    override suspend fun changeEmailSandOtp(
        user_id: Int,
        current_email: String,
        new_email: String
    ): Resource<SendOtpResponse> {
        return try {

            val response = apiService.changeEmailSandOtp(
                clientSecret, user_id, current_email, new_email
            )
            if (response.isSuccessful) {
                Resource.Success(response.body(), "")
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)
        }

    }

    override suspend fun changeEmail(
        user_id: Int,
        newEmail: String,
        otp: String
    ): Resource<SendOtpResponse> {
        return try {

            val response = apiService.changeEmail(
                clientSecret, user_id, newEmail, otp
            )
            if (response.isSuccessful) {
                Resource.Success(response.body(), "")
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }

    override suspend fun deleteUser(user_id: Int): Resource<GetFeedBackListResponse> {
        return try {

            val response = apiService.deleteUser(
                clientSecret, user_id
            )
            if (response.isSuccessful) {
                Resource.Success(response.body(), "")
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }


    override suspend fun getRecommendedUserList(user_id: Int): Resource<DiscoverResponse> {
        return try {

            val response = apiService.getRecommendedUserList(
                clientSecret, user_id
            )
            if (response.isSuccessful) {
                Log.d("TAG", "getZodiacList: " + 368)
                Resource.Success(response.body(), "")
            } else {
                Log.d("TAG", "getZodiacList: " + 371)
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }

    override suspend fun getDummyUserList(): Resource<DiscoverResponse> {
        return try {

            val response = apiService.getDummyUserList(
                clientSecret
            )
            if (response.isSuccessful) {
                Log.d("TAG", "getZodiacList927: " + response)
                Resource.Success(response.body(), "")
            } else {
                Log.d("TAG", "getZodiacList: " + 371)
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }

    override suspend fun getNearbyUserList(user_id: Int): Resource<DiscoverResponse> {
        return try {

            val response = apiService.getNearbyUserList(
                clientSecret, user_id
            )
            if (response.isSuccessful) {
                Log.d("TAG", "getZodiacList: " + 368)
                Resource.Success(response.body(), "")
            } else {
                Log.d("TAG", "getZodiacList: " + 371)
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }


    override suspend fun sendChatMessage(
        sender_id: String,
        message: String,
        receiver_id: String
    ): Resource<ChatResponse> {

        return try {
            val parts: MutableMap<String, RequestBody> = HashMap()

            parts["sender_id"] = sender_id.reqBody()
            parts["message"] = message.reqBody()
            parts["receiver_id"] = receiver_id.reqBody()

            Log.d("TAG", "sendChatMessage1095: " + parts)


            val response = apiService.sendChatMessage(
                clientSecret, parts
            )

            if (response.isSuccessful) Resource.Success(response.body(), "")
            else Resource.Error(response.message())

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)

        }

    }

    override suspend fun userSearch(
        user_id: Int,
        search: String
    ): Resource<SearchUserResponse> {
        return try {

            val response = apiService.userSearch(
                clientSecret, user_id, search
            )
            if (response.isSuccessful) {
                Log.d("TAG", "getZodiacList: " + 27)
                Resource.Success(response.body(), "")
            } else {
                Log.d("TAG", "getZodiacList: " + 30)
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }

    override suspend fun addSubscriptionPlan(
        user_id: Int,
        start_date: String,
        end_date: String,
        plan_type: String,
        amount: String,
        transaction_id: String
    ): Resource<SubsctiptionResponse> {
        return try {

            val response = apiService.addSubscriptionPlan(
                clientSecret, user_id, start_date, end_date, plan_type, amount, transaction_id,
            )

            if (response.isSuccessful) Resource.Success(response.body(), "")
            else Resource.Error(response.message())

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)

        }
    }

    override suspend fun getQuestionList(): Resource<GetQuestionsListResponse> {
        return try {

            val response = apiService.getQuestionList(
                clientSecret, ""
            )

            if (response.isSuccessful) Resource.Success(response.body(), "")
            else Resource.Error(response.message())

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)

        }
    }

    override suspend fun addQuestionAnswer(
        user_id: Int,
        question_id: Int,
        answer: String
    ): Resource<AddQuestionsAnswerResponse> {
        return try {

            val response = apiService.addQuestionAnswer(
                clientSecret, user_id, question_id, answer
            )

            if (response.isSuccessful) Resource.Success(response.body(), "")
            else Resource.Error(response.message())

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)

        }
    }

    override suspend fun getUserPersonality(user_id: Int): Resource<PersonlityResponse> {
        return try {

            val response = apiService.getUserPersonality(
                clientSecret, user_id
            )

            if (response.isSuccessful) Resource.Success(response.body(), "")
            else Resource.Error(response.message())

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)

        }
    }

    override suspend fun personalityData(): Resource<PersonalityDesignResponse> {
        return try {

            val response = apiService.personalityData(
                clientSecret, ""
            )

            if (response.isSuccessful) Resource.Success(response.body(), "")
            else Resource.Error(response.message())

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message)

        }
    }


}

fun String.reqBody(): RequestBody {
    return RequestBody.create("text/plain".toMediaTypeOrNull(), this)
}