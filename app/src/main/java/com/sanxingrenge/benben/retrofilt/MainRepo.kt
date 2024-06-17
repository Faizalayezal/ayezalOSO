package com.sanxingrenge.benben.retrofilt

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


//repository work for data provide
interface MainRepo {


    suspend fun getZodiacList(
        email: String
    ): Resource<AllListResponse>


    suspend fun registerUser(
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
    ): Resource<RegisterResponse>

    suspend fun getSendEmailOtp(
        email: String
    ): Resource<SendOtpResponse>

    suspend fun getVerifyRegister(
        email: String,
        register_otp: String,
    ): Resource<VerifyOtpResponse>

    suspend fun getSignInWithPassword(
        email: String, password: String
    ): Resource<SignInEmailPasswordResponse>

    suspend fun readMessage(
        currantId: String, otherId: String
    ): Resource<String>

    suspend fun getSignInWithEmail(
        email: String,
        is_forgot_pwd: String
    ): Resource<SendOtpResponse>

    suspend fun changeForgotEmailSentOtp(
        email: String,
    ): Resource<SendOtpResponse>

    suspend fun removeProfileImage(
        image_id: String,
    ): Resource<SendOtpResponse>

    suspend fun getLoginWithOtp(
        email: String, otp: String
    ): Resource<SignInEmailPasswordResponse>

    suspend fun getOtpVerifyForPwd(
        email: String, otp: String
    ): Resource<SignInEmailPasswordResponse>

    suspend fun getEmailExist(
        email: String
    ): Resource<SendOtpResponse>

    suspend fun getDummyUserList(
    ): Resource<DiscoverResponse>

    suspend fun changeForgotPwd(
        user_id: String,
        password: String, password_confirmation: String
    ): Resource<SignInEmailPasswordResponse>

    suspend fun updateUser(
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
        interestLevel5: String,
    ): Resource<UpdaterResponse>

    suspend fun getConstellationList(
    ): Resource<ConstellationResponse>

    suspend fun getChinesZodiacList(
    ): Resource<ChinesZodicResponse>

    suspend fun getUserProfile(
        user_id: Int,
        login_user_id: Int,
    ): Resource<UpdaterResponse>


    suspend fun updateFcmToken(
        user_id: String,
        fcm_token: String,
    ): Resource<UpdaterFCMResponse>
    suspend fun singleVideoCall(
        receiver_id: String,
        sender_id: String,
    ): Resource<SignVideoCallResponse>

    suspend fun logout(
        user_id: String,
    ): Resource<FriendRequestResponse>

    suspend fun sociallogin(
        social_id: String,
    ): Resource<SocialLoginResponse>

    suspend fun sendFriendRequest(
        sender_id: Int,
        receiver_id: Int
    ): Resource<DiscoverResponse>

    suspend fun getFriendRequestList(
        user_id: Int
    ): Resource<FriendRequestResponse>

    suspend fun getUserListOfMessage(
        user_id: Int
    ): Resource<ConversionRsponse>

    suspend fun approveFrinendRequest(
        friend_request_id: Int,
        status: String,
    ): Resource<FriendRequestResponse>


    suspend fun getRecommendedUserList(
        user_id: Int
    ): Resource<DiscoverResponse>

    suspend fun getNearbyUserList(
        user_id: Int
    ): Resource<DiscoverResponse>


    suspend fun getUserFilter(
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
        interestLevel5: String,
    ): Resource<DiscoverResponse>

    suspend fun getAgreementContentList(
    ): Resource<AgreementResponse>


    suspend fun addFeedback(
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
    ): Resource<ChatResponse>

    suspend fun getFeedback(
        user_id: Int
    ): Resource<GetFeedBackListResponse>

    suspend fun changePassword(
        user_id: Int,
        currantPassword: String,
        password: String,
        passwordConfirm: String,
    ): Resource<GetFeedBackListResponse>

    suspend fun changeEmailSandOtp(
        user_id: Int,
        current_email: String,
        new_email: String,
    ): Resource<SendOtpResponse>

    suspend fun changeEmail(
        user_id: Int,
        newEmail: String,
        otp: String,
    ): Resource<SendOtpResponse>

    suspend fun deleteUser(
        user_id: Int,
    ): Resource<GetFeedBackListResponse>

    suspend fun sendChatMessage(
        sender_id: String,
        message: String,
        receiver_id: String,
    ): Resource<ChatResponse>

    suspend fun userSearch(
        user_id: Int,
        search: String
    ): Resource<SearchUserResponse>

    suspend fun addSubscriptionPlan(
        user_id: Int,
        start_date: String,
        end_date: String,
        plan_type: String,
        amount: String,
        transaction_id: String,
    ): Resource<SubsctiptionResponse>

    suspend fun getQuestionList(
    ): Resource<GetQuestionsListResponse>

    suspend fun addQuestionAnswer(
        user_id: Int,
        question_id: Int,
        answer: String,
    ): Resource<AddQuestionsAnswerResponse>

    suspend fun getUserPersonality(
        user_id: Int,
    ): Resource<PersonlityResponse>

    suspend fun personalityData(
    ): Resource<PersonalityDesignResponse>


}