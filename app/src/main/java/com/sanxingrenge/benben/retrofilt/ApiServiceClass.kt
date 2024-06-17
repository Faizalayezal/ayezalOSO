package com.sanxingrenge.benben.retrofilt

import com.sanxingrenge.benben.dataModel.SignVideoCallResponse
import com.sanxingrenge.benben.responseModel.SocialLoginResponse
import com.sanxingrenge.benben.responseModel.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

const val tokenKey = "Api-Key"

interface ApiServiceClass {
    @Multipart
    @JvmSuppressWildcards
    @POST("get_registration_data")
    suspend fun getZodiacList(
        @Header(tokenKey) token: String,
        @PartMap params: Map<String, RequestBody>,
    ): Response<AllListResponse>


    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("register_otp")
    suspend fun getSendEmailOtp(
        @Header(tokenKey) token: String,
        @Field("email") email: String,
    ): Response<SendOtpResponse>

    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("email_exist")
    suspend fun getEmailExist(
        @Header(tokenKey) token: String,
        @Field("email") email: String
    ): Response<SendOtpResponse>


    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("login_with_password")
    suspend fun getSignInWithPassword(
        @Header(tokenKey) token: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response<SignInEmailPasswordResponse>

    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("otp_verify_register")
    suspend fun getVerifyRegister(
        @Header(tokenKey) token: String,
        @Field("email") email: String,
        @Field("register_otp") register_otp: String,
    ): Response<VerifyOtpResponse>

    @Multipart
    @JvmSuppressWildcards
    @POST("change_forgot_password")
    suspend fun changeForgotPwd(
        @Header(tokenKey) token: String,
        @PartMap params: Map<String, RequestBody>,
    ): Response<SignInEmailPasswordResponse>

    @Multipart
    @JvmSuppressWildcards
    @POST("login_with_otp_send_otp")
    suspend fun getSignInWithEmail(
        @Header(tokenKey) token: String,
        @Part("email") email: RequestBody,
        @PartMap params: Map<String, RequestBody>,
    ): Response<SendOtpResponse>

    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("change_forgot_email_sent_otp")
    suspend fun changeForgotEmailSentOtp(
        @Header(tokenKey) token: String,
        @Part("email") email: RequestBody,
    ): Response<SendOtpResponse>

    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("remove_profile_image")
    suspend fun removeProfileImage(
        @Header(tokenKey) token: String,
        @Field("image_id") image_id: String,
    ): Response<SendOtpResponse>


    @Multipart
    @JvmSuppressWildcards
    @POST("login_with_otp")
    suspend fun getLoginWithOtp(
        @Header(tokenKey) token: String,
        @Part("email") email: RequestBody,
        @PartMap params: Map<String, RequestBody>,
    ): Response<SignInEmailPasswordResponse>

    @Multipart
    @JvmSuppressWildcards
    @POST("otp_verify_for_pwd")
    suspend fun getOtpVerifyForPwd(
        @Header(tokenKey) token: String,
        @Part("email") email: RequestBody,
        @PartMap params: Map<String, RequestBody>,
    ): Response<SignInEmailPasswordResponse>

    @Multipart
    @JvmSuppressWildcards
    @POST("registration")
    suspend fun registerUser(
        @Header(tokenKey) token: String,
        @PartMap params: Map<String, RequestBody>,
        @Part image1: MultipartBody.Part?,
        @Part image2: MultipartBody.Part?,
        @Part image3: MultipartBody.Part?,
        @Part image4: MultipartBody.Part?,
    ): Response<RegisterResponse>


    @Multipart
    @JvmSuppressWildcards
    @POST("update_user_details")
    suspend fun updateUser(
        @Header(tokenKey) token: String,
        @PartMap params: Map<String, RequestBody>,
        @Part image1: MultipartBody.Part?,
        @Part image2: MultipartBody.Part?,
        @Part image3: MultipartBody.Part?,
        @Part image4: MultipartBody.Part?,
    ): Response<UpdaterResponse>


    @Multipart
    @JvmSuppressWildcards
    @POST("get_constellation_list")
    suspend fun getConstellationList(
        @Header(tokenKey) token: String,
        @PartMap params: Map<String, RequestBody>,
    ): Response<ConstellationResponse>

    @Multipart
    @JvmSuppressWildcards
    @POST("get_zodiac_list")
    suspend fun getChinesZodiacList(
        @Header(tokenKey) token: String,
        @PartMap params: Map<String, RequestBody>,
    ): Response<ChinesZodicResponse>

    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("get_user_details")
    suspend fun getUserProfile(
        @Header(tokenKey) token: String,
        @Field("user_id") userId: Int,
        @Field("login_user_id") loginUserId: Int,
    ): Response<UpdaterResponse>

    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("update-fcm-token")
    suspend fun updateFcmToken(
        @Header(tokenKey) token: String,
        @Field("user_id") userId: String,
        @Field("fcm_token") fcmToken: String,
    ): Response<UpdaterFCMResponse>

    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("log-out")
    suspend fun logout (
    @Header(tokenKey) token: String,
    @Field("user_id") userId: String,
    ): Response<FriendRequestResponse>


    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("check_social_user")
    suspend fun sociallogin (
    @Header(tokenKey) token: String,
    @Field("social_id") social_id: String,
    ): Response<SocialLoginResponse>


    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("get_friend_request_list")
    suspend fun getFriendRequestList(
        @Header(tokenKey) token: String,
        @Field("user_id") userId: Int,
    ): Response<FriendRequestResponse>


    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("get_user_list_of_message")
    suspend fun getUserListOfMessage(
        @Header(tokenKey) token: String,
        @Field("user_id") userId: Int,
    ): Response<ConversionRsponse>

    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("single_video_call")
    suspend fun singleVideoCall(
        @Header(tokenKey) token: String,
        @Field("receiver_id") receiverId: String,
        @Field("sender_id") senderId: String,
    ): Response<SignVideoCallResponse>

    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("approve_frinend_request")
    suspend fun approveFrinendRequest(
        @Header(tokenKey) token: String,
        @Field("friend_request_id") requestId: Int,
        @Field("status") status: String,
    ): Response<FriendRequestResponse>

    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("send_friend_request")
    suspend fun sendFriendRequest(
        @Header(tokenKey) token: String,
        @Field("sender_id") sender_id: Int,
        @Field("receiver_id") userId: Int,
    ): Response<DiscoverResponse>


    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("get_recommended_user_list")
    suspend fun getRecommendedUserList(
        @Header(tokenKey) token: String,
        @Field("user_id") userId: Int,
    ): Response<DiscoverResponse>


    @GET("dummy_dashboard")
    suspend fun getDummyUserList(
        @Header(tokenKey) token: String,
    ): Response<DiscoverResponse>

    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("get_nearby_user_list")
    suspend fun getNearbyUserList(
        @Header(tokenKey) token: String,
        @Field("user_id") userId: Int,
    ): Response<DiscoverResponse>


    /* @JvmSuppressWildcards
     @Multipart
     @POST("apply_filter")
     suspend fun getUserFilter(
         @Header(tokenKey) token: String,
         @PartMap params: Map<String, RequestBody>,
     ): Response<DiscoverResponse>*/


    @JvmSuppressWildcards
    @Multipart
    @POST("apply_filter")
    suspend fun getUserFilter(
        @Header(tokenKey) token: String,
        @PartMap params: Map<String, RequestBody>
    ): Response<DiscoverResponse>
    /* @JvmSuppressWildcards
     @FormUrlEncoded
     @POST("apply_filter")
     suspend fun getUserFilter(
         @Header(tokenKey) token: String,
         @Field("gender") gender: String,
         @Field("user_id") user_id: String,
         @Field("looking_for") looking_for: String,
         @Field("latitude") latitude: String,
         @Field("longitude") longitude: String,
         @Field("interests[0]['id']") interestsId0: String,
         @Field("interests[0]['level']") interestsLevel0: String,
         @Field("interests[1]['id']") interestsId1: String,
         @Field("interests[1]['level']") interestsLevel1: String,
         @Field("interests[2]['id']") interestsId2: String,
         @Field("interests[2]['level']") interestsLevel2: String,
         @Field("interests[3]['id']") interestsId3: String,
         @Field("interests[3]['level']") interestsLevel3: String,
         @Field("interests[4]['id']") interestsId4: String,
         @Field("interest[4]['level']") interestsLevel4: String,

     ): Response<DiscoverResponse>*/


    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("get_agreement_content_list")
    suspend fun getAgreementContentList(
        @Header(tokenKey) token: String,
        @Field("email") email: String
    ): Response<AgreementResponse>


    @JvmSuppressWildcards
    @Multipart
    @POST("add_feedback")
    suspend fun addFeedback(
        @Header(tokenKey) token: String,
        @PartMap params: Map<String, RequestBody>,
        @Part image1: MultipartBody.Part?,
        @Part image2: MultipartBody.Part?,
        @Part image3: MultipartBody.Part?,
        @Part image4: MultipartBody.Part?,
        @Part image5: MultipartBody.Part?,
        @Part image6: MultipartBody.Part?,
    ): Response<ChatResponse>

    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("get_feedback_list")
    suspend fun getFeedback(
        @Header(tokenKey) token: String,
        @Field("user_id") userId: Int,
    ): Response<GetFeedBackListResponse>

    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("change_password")
    suspend fun changePassword(
        @Header(tokenKey) token: String,
        @Field("user_id") userId: Int,
        @Field("current_password") currantPassword: String,
        @Field("password") password: String,
        @Field("password_confirmation") passwordConfirm: String,
    ): Response<GetFeedBackListResponse>


    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("change_email_sent_otp")
    suspend fun changeEmailSandOtp(
        @Header(tokenKey) token: String,
        @Field("user_id") userId: Int,
        @Field("current_email") currantPassword: String,
        @Field("new_email") password: String,
    ): Response<SendOtpResponse>

    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("change_email")
    suspend fun changeEmail(
        @Header(tokenKey) token: String,
        @Field("user_id") userId: Int,
        @Field("new_email") newEmail: String,
        @Field("otp") otp: String,
    ): Response<SendOtpResponse>

    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("delete_user_account")
    suspend fun deleteUser(
        @Header(tokenKey) token: String,
        @Field("user_id") userId: Int,
    ): Response<GetFeedBackListResponse>

    @JvmSuppressWildcards
    @Multipart
    @POST("add_message")
    suspend fun sendChatMessage(
        @Header(tokenKey) token: String?,
        @PartMap params: Map<String, RequestBody>
    ): Response<ChatResponse>


    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("search")
    suspend fun userSearch(
        @Header(tokenKey) token: String,
        @Field("user_id") userId: Int,
        @Field("search") name: String,
    ): Response<SearchUserResponse>

    @FormUrlEncoded
    @POST("add_subscription")
    suspend fun addSubscriptionPlan(
        @Header(tokenKey) token: String?,
        @Field("user_id") userId: Int,
        @Field("start_date") startDate: String,
        @Field("end_date") endDate: String,
        @Field("plan_type") planType: String,
        @Field("amount") amount: String,
        @Field("transaction_id") transactionId: String,
    ): Response<SubsctiptionResponse>

    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("get_questions_list")
    suspend fun getQuestionList(
        @Header(tokenKey) token: String,
        @Field("email") email: String
    ): Response<GetQuestionsListResponse>

    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("read_message")
    suspend fun readMessage(
        @Header(tokenKey) token: String,
        @Field("user_id") currantId: String,
        @Field("friend_id") otherId: String
    ): Response<String>

    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("add_question_answer")
    suspend fun addQuestionAnswer(
        @Header(tokenKey) token: String,
        @Field("user_id") userId: Int,
        @Field("question_id") questionId: Int,
        @Field("answer") answer: String,
    ): Response<AddQuestionsAnswerResponse>

    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("get_user_personality")
    suspend fun getUserPersonality(
        @Header(tokenKey) token: String,
        @Field("user_id") userId: Int,
    ): Response<PersonlityResponse>

    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("get_personality_data")
    suspend fun personalityData(
        @Header(tokenKey) token: String,
        @Field("email") email: String,
    ): Response<PersonalityDesignResponse>


}