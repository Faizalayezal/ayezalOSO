package com.sanxingrenge.benben.screens

import LocationPickerHelper
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.*
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookAuthorizationException
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.util.CollectionUtils.listOf
import com.google.android.gms.tasks.Task
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.limurse.iap.BillingClientConnectionListener
import com.limurse.iap.DataWrappers
import com.limurse.iap.IapConnector
import com.limurse.iap.PurchaseServiceListener
import com.limurse.iap.SubscriptionServiceListener
import com.plcoding.audiorecorder.playback.AndroidAudioPlayer
import com.plcoding.audiorecorder.record.AndroidAudioRecorder
import com.sanxingrenge.benben.GoogleAuthUiClient
import com.sanxingrenge.benben.R
import com.sanxingrenge.benben.SingleCallActivity
import com.sanxingrenge.benben.constant.*
import com.sanxingrenge.benben.constant.Constns.FBName
import com.sanxingrenge.benben.constant.Constns.FBStatus
import com.sanxingrenge.benben.constant.Constns.FBid
import com.sanxingrenge.benben.constant.Constns.ImageShowEdit
import com.sanxingrenge.benben.constant.Constns.channel_name
import com.sanxingrenge.benben.constant.Constns.emailLoginStore
import com.sanxingrenge.benben.constant.Constns.emailLoginUserExist
import com.sanxingrenge.benben.constant.Constns.getImageUri
import com.sanxingrenge.benben.constant.Constns.image
import com.sanxingrenge.benben.constant.Constns.image1
import com.sanxingrenge.benben.constant.Constns.image2
import com.sanxingrenge.benben.constant.Constns.image3
import com.sanxingrenge.benben.constant.Constns.image4
import com.sanxingrenge.benben.constant.Constns.image5
import com.sanxingrenge.benben.constant.Constns.image6
import com.sanxingrenge.benben.constant.Constns.lastMonth
import com.sanxingrenge.benben.constant.Constns.producatId
import com.sanxingrenge.benben.constant.Constns.que
import com.sanxingrenge.benben.constant.Constns.receiver_token
import com.sanxingrenge.benben.constant.Constns.receiver_u_id
import com.sanxingrenge.benben.constant.Constns.scaleBitmap
import com.sanxingrenge.benben.constant.Constns.sender_id
import com.sanxingrenge.benben.constant.Constns.sender_name
import com.sanxingrenge.benben.constant.Constns.startDate
import com.sanxingrenge.benben.constant.Constns.userImage1
import com.sanxingrenge.benben.constant.Constns.userImage2
import com.sanxingrenge.benben.constant.Constns.userImage3
import com.sanxingrenge.benben.constant.Constns.userImage4
import com.sanxingrenge.benben.constant.Constns.userPos1
import com.sanxingrenge.benben.constant.Constns.userPos1Register
import com.sanxingrenge.benben.constant.Constns.writeBitmap
import com.sanxingrenge.benben.constant.Constns.years
import com.sanxingrenge.benben.dataModel.UserDataObject
import com.sanxingrenge.benben.getArg
import com.sanxingrenge.benben.screens.loggedInModule.*
import com.sanxingrenge.benben.screens.loggedInModule.settingFlow.*
import com.sanxingrenge.benben.screens.loginRegisterModule.*
import com.sanxingrenge.benben.screens.zodiacSignModule.ChineseZodiacScreen
import com.sanxingrenge.benben.screens.zodiacSignModule.NormalZodiacScreen
import com.sanxingrenge.benben.ui.*
import com.sanxingrenge.benben.ui.theme.ProgressColor
import com.sanxingrenge.benben.viewModel.DiscoverScreenViewModel
import com.sanxingrenge.benben.viewModel.GetMessageListViewModel
import com.sanxingrenge.benben.viewModel.ProfileScreenViewModel
import com.sanxingrenge.benben.viewModel.RegisterViewModel
import com.sanxingrenge.benben.viewModel.SignInViewModel
import com.sanxingrenge.benben.viewModel.SubscriptionViewModel
import com.sanxingrenge.benben.viewModel.UserLastSeenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.*


private const val TAG = "SuperActivity"

@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class SuperActivity : ComponentActivity() {

    private val _isLoading = MutableStateFlow(false)
    private val isLoading = _isLoading.asStateFlow()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var isReadPermissionGranted = false
    private var isCameraPermissionGranted = false

    //private lateinit var appUpdateManager: AppUpdateManager
    private val updateType = AppUpdateType.IMMEDIATE
    private val userLastSeenViewModel: UserLastSeenViewModel by viewModels()
    private var callbackManager: CallbackManager? = null


    // C:\OSOMATE\osomate_letast.jks
    //$ keytool -export -rfc -keystore osomate_newletast.jks -alias key0 -file C:\OSOMATE\upload_certificate.pem
    // keytool -list -v -keystore osomate_letast.jks -alias C:\OSOMATE\upload_certificate.pem
    //keytool -list -v -keystore ~/.android/debug.keystore
    //C:\OSOMATE\osomate_newletast.jks

    val messageViewModel: GetMessageListViewModel by viewModels()
    val viewModel: SubscriptionViewModel by viewModels()
    val viewModelRemoveImg: ProfileScreenViewModel by viewModels()
    var userDataObject: UserDataObject? = null
    var customData = ""
    var callActivity = ""

    //how to bulid video call without internet in android kotlin code programically
    val handler = Handler(Looper.getMainLooper())
    private var accessToken: AccessToken? = null
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var iapConnector: IapConnector
    val isBillingClientConnected: MutableLiveData<Boolean> = MutableLiveData()
    lateinit var googleSignInClient: GoogleSignInClient
    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        customSplashScreenInit()
        //getZodiacList("NPaKyJ6L786p4R4dA22FJvIVzLvUhEty26ehPHwx2xKPEH7Bj4KkT2dzIyeSrBd5")
        intent.apply {
            customData = intent.getStringExtra("custom") ?: ""
        }
        firebaseAuth = FirebaseAuth.getInstance()
        /* appUpdateManager = AppUpdateManagerFactory.create(applicationContext)

         if (updateType == AppUpdateType.FLEXIBLE) {
             appUpdateManager.registerListener(installStateUpdatedListener)
         }*/
        // callbackManager = CallbackManager.Factory.create()
        //  accessToken = AccessToken.getCurrentAccessToken()

        /* LoginManager.getInstance()
             .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                 override fun onSuccess(result: LoginResult?) {

                 }

                 override fun onCancel() {
                 }

                 override fun onError(error: FacebookException?) {
                 }

             })*/
        //LoginManager.getInstance().logOut()
        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("957862856792-p8k87nlqcnd93s05u8212dav2gg2cat7.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this@SuperActivity, googleSignInOption)


        isBillingClientConnected.value = false
        val subsList =
            listOf("com.monthlyosomatenew", "com.quarterlyosomatenew", "com.yearplanosomate")

        iapConnector = IapConnector(
            context = this,
            subscriptionKeys = subsList,
            key = getString(R.string.app_Id),
            enableLogging = true
        )
        iapConnector.addBillingClientConnectionListener(object : BillingClientConnectionListener {
            override fun onConnected(status: Boolean, billingResponseCode: Int) {
                Log.d(
                    "KSA",
                    "This is the status: $status and response code is: $billingResponseCode"
                )
                isBillingClientConnected.value = status
            }
        })
        iapConnector.addPurchaseListener(object : PurchaseServiceListener {
            override fun onPricesUpdated(iapKeyPrices: Map<String, List<DataWrappers.ProductDetails>>) {
                //plan ni list dekhade play console ma hoy a
                Log.d(TAG, "onPricesUpdated:229 " + iapKeyPrices)
            }

            override fun onProductPurchased(purchaseInfo: DataWrappers.PurchaseInfo) {
                Log.d(TAG, "onPricesUpdated:233 " + purchaseInfo)

            }

            override fun onProductRestored(purchaseInfo: DataWrappers.PurchaseInfo) {
                Log.d(TAG, "onPricesUpdated:238 " + purchaseInfo)

            }

            override fun onPurchaseFailed(
                purchaseInfo: DataWrappers.PurchaseInfo?,
                billingResponseCode: Int?
            ) {
                Log.d(TAG, "onPricesUpdated:238 " + purchaseInfo)

            }

        })
        iapConnector.addSubscriptionListener(object : SubscriptionServiceListener {

            override fun onPricesUpdated(iapKeyPrices: Map<String, List<DataWrappers.ProductDetails>>) {

            }

            override fun onPurchaseFailed(
                purchaseInfo: DataWrappers.PurchaseInfo?,
                billingResponseCode: Int?
            ) {
                Log.d(TAG, "onSubscriptionPurchased:287 " + purchaseInfo?.sku)
            }

            override fun onSubscriptionPurchased(purchaseInfo: DataWrappers.PurchaseInfo) {
                Log.d(TAG, "onSubscriptionPurchased:154 " + purchaseInfo.sku)
                when (purchaseInfo.sku) {
                    "com.monthlyosomatenew" -> {
                        Log.d(TAG, "onSubscriptionPurchased: " + "month")
                        viewModel.subscriptionPlanPurchesApi(
                            this@SuperActivity,
                            startDate.toString(),
                            lastMonth.toString(),
                            "monthly",
                            "9.99"
                        ) { it, msg ->
                            if (it) {
                                applicationContext.showToast(msg)
                            }

                        }

                    }

                    "com.quarterlyosomatenew" -> {

                        Log.d(TAG, "onSubscriptionPurchased: " + "quarter")
                        viewModel.subscriptionPlanPurchesApi(
                            this@SuperActivity,
                            startDate.toString(),
                            que.toString(),
                            "quarterly",
                            "19.99"
                        ) { it, msg ->
                            if (it) {
                                applicationContext.showToast(msg)
                            }

                        }

                    }

                    "com.yearplanosomate" -> {
                        Log.d(TAG, "onSubscriptionPurchased: " + "year")
                        viewModel.subscriptionPlanPurchesApi(
                            this@SuperActivity,
                            startDate.toString(),
                            years.toString(),
                            "yearly",
                            "39.99"
                        ) { it, msg ->

                            if (it) {
                                applicationContext.showToast(msg)
                            }

                        }

                    }
                }
            }

            override fun onSubscriptionRestored(purchaseInfo: DataWrappers.PurchaseInfo) {
            }

        })


        // checkForAppUpdate()


        setContent {
            NavigationComponent(rememberAnimatedNavController())

        }
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permission ->
                isReadPermissionGranted =
                    permission[android.Manifest.permission.READ_EXTERNAL_STORAGE]
                        ?: isReadPermissionGranted
                isCameraPermissionGranted =
                    permission[android.Manifest.permission.CAMERA] ?: isCameraPermissionGranted


            }

        handler.post {
            try {
                val jsonObject = JSONObject(customData)
                receiver_u_id.value = jsonObject.getString("receiver_u_id")
                sender_id.value = jsonObject.getString("sender_id")
                channel_name.value = jsonObject.getString("channel_name")
                sender_name.value = jsonObject.getString("sender_name")
                image.value = jsonObject.getString("image")
                receiver_token.value = jsonObject.getString("receiver_token")
                val intent = Intent(this@SuperActivity, SingleCallActivity::class.java)
                startActivity(intent)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }




        initFacebookLogin()
    }

    private fun customSplashScreenInit() {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                isLoading.value
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun NavigationComponent(navController: NavHostController) {
        /* val launcher = rememberLauncherForActivityResult(
              contract = ActivityResultContracts.StartIntentSenderForResult(),
              onResult = { result ->
                  if (result.resultCode == RESULT_OK) {
                      lifecycleScope.launch {
                          val signInResult = googleAuthUiClient.signInWithIntent(
                              intent = result.data ?: return@launch
                          )
                          loginHandler(signInResult)
                          // viewModel.onSignInResult(signInResult)
                      }
                  }
              }
          )*/

        val progressState = isLoading.collectAsState().value
        val enterAnim = slideInHorizontally(initialOffsetX = { 1000 })
        val exitAnim = slideOutHorizontally(targetOffsetX = { -1000 })

        screenRefreshCount += 1

        Log.d(TAG, "NavigationComponent: test>>screenRefreshCount=$screenRefreshCount")

        AnimatedNavHost(
            navController = navController,
            startDestination = Route.SPLASH_ROUTE,
            modifier = Modifier.background(Color.White),
        ) {
            composable(Route.SPLASH_ROUTE,
                enterTransition = { enterAnim },
                exitTransition = { exitAnim }) {
                SplashScreen(navController::startNavigation)
            }
            composable(Route.ON_BOARDING_ROUTE,
                enterTransition = { enterAnim },
                exitTransition = { exitAnim }) {
                OnBoardingScreen(
                    navController::startNavigation,
                )
            }
            composable(Route.SIGN_IN_ROUTE,
                enterTransition = { enterAnim },
                exitTransition = { exitAnim }) {

                val viewModel: SignInViewModel = hiltViewModel()

                if (screenRefreshCount == 0 || screenRefreshCount == 1) {
                    val abc = it.getArg(Arg.ARG_IS_NEW_USER)

                    when (abc) {
                        SignInScreenState.EmailOtp.name -> viewModel.updateUiState(
                            SignInScreenState.EmailOtp,
                            48
                        )

                        SignInScreenState.CreateAccount.name -> viewModel.updateUiState(
                            SignInScreenState.CreateAccount,
                            50
                        )

                        SignInScreenState.EmailPwd.name -> viewModel.updateUiState(
                            SignInScreenState.EmailPwd,
                            52
                        )

                        SignInScreenState.VerifyOtp.name -> viewModel.updateUiState(
                            SignInScreenState.VerifyOtp,
                            53
                        )
                    }
                }

                //957862856792-jvujove4f1479hvdi8886j3r3v71up5h.apps.googleusercontent.com
                SignInScreen(
                    openScreen = navController::startNavigation,
                    popBack = navController::goBack,
                    showLoading = {
                        _isLoading.value = it
                    },
                    googleLogin = {
                        GoogleLogin()

                    },
                    faceBookLogin = {
                        FaceBookLogin()
                    },
                    viewModel = viewModel
                )
            }

            composable(Route.TRANSPERANT_ROUTE,
                enterTransition = { enterAnim },
                exitTransition = { exitAnim }) {
                TransparentLayerWithBottomNavigation(
                    openScreen = navController::startNavigation
                )

            }
            composable(Route.HOME_ROUTE,
                enterTransition = { enterAnim },
                exitTransition = { exitAnim }) {
                val discoverModel: DiscoverScreenViewModel = hiltViewModel()

                HomeScreen(
                    openScreen = navController::startNavigation,
                    showLoading = { _isLoading.value = it },
                    navController::goBack
                )
            }
            composable(Route.CHAT_ROUTE,

                enterTransition = { enterAnim },
                exitTransition = { exitAnim }) {

                val userId = it.getArg(Arg.CHAT_USER_ID)
                val userName = it.getArg(Arg.CHAT_USER_NAME)
                // val userImage = it.getArg(Arg.CHAT_USER_PHOTO)
                Log.d(TAG, "NavigationComponent312: " + Arg.CHAT_USER_PHOTO)

                ChatScreen(
                    openScreen = navController::startNavigation,
                    openScreenvideocall = {
                        videocallScree()
                    },
                    navController::goBack,
                    userId,
                    userName,
                    multiFilePicker,
                    recorder,
                    player,
                )

            }
            composable(Route.MEDIA_ROUTE,
                enterTransition = { enterAnim },
                exitTransition = { exitAnim }) {


                val chatModel: GetMessageListViewModel = hiltViewModel()

                MediaScreen(
                    openScreen = navController::startNavigation,
                    popBack = navController::goBack,
                    viewModel = chatModel,
                )
            }
            composable(Route.SINGLE_MEDIA_ROUTE,
                enterTransition = { enterAnim },
                exitTransition = { exitAnim }) {


                val chatModel: GetMessageListViewModel = hiltViewModel()

                SingleMediaScreen(
                    openScreen = navController::startNavigation,
                    popBack = navController::goBack,
                    viewModel = chatModel,
                )
            }
            composable(Route.EDIT_PROFILE_ROUTE,
                enterTransition = { enterAnim },
                exitTransition = { exitAnim }) {
                EditProfileScreen(
                    openScreen = navController::startNavigation, navController::goBack,
                    showLoading = {
                        _isLoading.value = it
                    },
                    openLocationPicker = {
                        lifecycleScope.launch {
                            launch { dataStoreSetTempLocation("", "", "", 165) }.join()
                            locationPickerHelper.openLocationPicker(resultLauncher)
                        }
                    },
                    openImagePicker = {
                        // setImagePickerFlow()
                        setImagePickerSeondFlow()
                    },
                    openImageCameraPicker = {
                        setImagePickerCameraFlow()
                    },
                    /*faceDatect = {
                      //  detectFaceIn(bitmap!!, fileData!!)
                    }*/

                )
            }

            composable(Route.RAGI_PROFILE_ROUTE,
                enterTransition = { enterAnim },
                exitTransition = { exitAnim }) {
                RagiProfileScreen(
                    openScreen = navController::startNavigation, navController::goBack,
                    showLoading = {
                        _isLoading.value = it
                    },
                    openLocationPicker = {
                        lifecycleScope.launch {
                            launch { dataStoreSetTempLocation("", "", "", 165) }.join()
                            locationPickerHelper.openLocationPicker(resultLauncher)
                        }
                    },
                    openImagePicker = {
                        //setImagePickerFlow()
                        setImagePickerSeondFlow()
                    },
                    openImageCameraPicker = {
                        setImagePickerCameraFlow()
                    },
                    /*faceDatect = {
                      //  detectFaceIn(bitmap!!, fileData!!)
                    }*/

                )
            }


            composable(Route.MY_PERSONALITY_ROUTE,
                enterTransition = { enterAnim },
                exitTransition = { exitAnim }) {
                MyPersonalityScreen(
                    it.getArg(Arg.ARG_SELECTED_POS), navController::goBack
                )
            }
            composable(Route.NORMAL_ZODIAC_ROUTE,
                enterTransition = { enterAnim },
                exitTransition = { exitAnim }) {
                NormalZodiacScreen(
                    navController::goBack,
                    showLoading = {
                        _isLoading.value = it
                    },
                )
            }
            composable(Route.CHINESE_ZODIAC_ROUTE,
                enterTransition = { enterAnim },
                exitTransition = { exitAnim }) {
                ChineseZodiacScreen(
                    navController::goBack,
                    showLoading = {
                        _isLoading.value = it
                    },
                )
            }
            composable(Route.PERSONALITY_TEST_ROUTE,
                enterTransition = { enterAnim },
                exitTransition = { exitAnim }) {
                PersonalityTestScreen(
                    navController::startNavigation, navController::goBack,
                    showLoading = {
                        _isLoading.value = it
                    },
                )
            }
            composable(Route.GET_PREMIUM_ROUTE,
                enterTransition = { enterAnim },
                exitTransition = { exitAnim }) {
                GetPremiumScreen(
                    navController::startNavigation, navController::goBack,
                    showLoading = {
                        _isLoading.value = it
                    },
                    clickPlan = {
                        clickSubscriptionPlan()
                    },
                )
            }
            composable(Route.FEEDBACK_RECORDS_ROUTE,
                enterTransition = { enterAnim },
                exitTransition = { exitAnim }) { FeedbackRecordsScreen(navController::goBack) }
            composable(Route.CHANGE_PASSWORD_ROUTE,
                enterTransition = { enterAnim },
                exitTransition = { exitAnim }) {
                ChangePasswordScreen(
                    navController::goBack,
                    showLoading = {
                        _isLoading.value = it
                    },
                )
            }
            composable(Route.CHANGE_EMAIL_ROUTE,
                enterTransition = { enterAnim },
                exitTransition = { exitAnim }) {
                ChangeEmailScreen(
                    navController::goBack,
                    showLoading = {
                        _isLoading.value = it
                    },
                )
            }
            composable(Route.TERMS_PRIVACY_ROUTE,
                enterTransition = { enterAnim },
                exitTransition = { exitAnim }) {
                TermsPrivacyScreen(
                    type = it.getArg(Arg.ARG_TYPE), navController::goBack,
                    showLoading = {
                        _isLoading.value = it
                    },
                )
            }
            composable(Route.FEEDBACK_ROUTE,
                enterTransition = { enterAnim },
                exitTransition = { exitAnim }) {
                FeedbackScreen(
                    openScreen = navController::startNavigation, navController::goBack,
                    openImagePicker = {
                        setMultiPleImagePickerFlow()
                    },
                    showLoading = {
                        _isLoading.value = it
                    }
                )
            }
            composable(Route.REGISTER_ROUTE,
                enterTransition = { enterAnim },
                exitTransition = { exitAnim }) {
                RegisterScreenImpl(it, navController)
            }
            composable(Route.MAIN_SETTING_ROUTE,
                enterTransition = { enterAnim },
                exitTransition = { exitAnim }) {
                MainSettingScreen(
                    navController::startNavigation,
                    logOut = {
                        logoutUser()
                    },
                    navController::goBack,

                    )
            }
            composable(Route.SEARCH_USER_ROUTE,
                enterTransition = { enterAnim },
                exitTransition = { exitAnim }) {
                SearchUserScreen(
                    navController::goBack,
                    navController::startNavigation
                )
            }
            composable(Route.DISCOVER_FILTER_ROUTE,
                enterTransition = { enterAnim },
                exitTransition = { exitAnim }) {
                DiscoverFilterScreen(
                    navController::goBack, navController::startNavigation,
                    showLoading = {
                        _isLoading.value = it
                    },
                    openLocationPicker = {
                        lifecycleScope.launch {
                            launch { dataStoreSetTempLocation("", "", "", 165) }.join()
                            locationPickerHelper.openLocationPicker(resultLauncherfilters)
                        }
                    },

                    )
            }
            composable(Route.PROFILE_DETAIL_ROUTE,
                enterTransition = { enterAnim },
                exitTransition = { exitAnim }) {

                val userId = it.getArg(Arg.ARG_USER_ID)

                ProfileDetailScreen(
                    userId,
                    navController::startNavigation, navController::goBack,
                    showLoading = {
                        _isLoading.value = it
                    },
                )
            }
            composable(Route.EDIT_INTEREST_ROUTE,
                enterTransition = { enterAnim },
                exitTransition = { exitAnim }) {

                EditInterestActivity(
                    navController::goBack,
                    showLoading = {
                        _isLoading.value = it
                    },
                )
            }

            composable(Route.REGISTER_INTEREST_ROUTE,
                enterTransition = { enterAnim },
                exitTransition = { exitAnim }) {

                RegisterInterestActivity(
                    navController::startNavigation, navController::goBack,
                    showLoading = {
                        _isLoading.value = it
                    },
                )
            }
            composable(Route.FILTER_INTEREST_ROUTE,
                enterTransition = { enterAnim },
                exitTransition = { exitAnim }) {

                FilterInterestActivity(navController::goBack)
            }


        }

        if (progressState) ProgressDesign()
    }


    private fun logoutUser() {

        googleSignInClient.signOut()
        /* val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
             .requestIdToken("957862856792-p8k87nlqcnd93s05u8212dav2gg2cat7.apps.googleusercontent.com")
             .requestEmail()
             .build()
         val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
         mGoogleSignInClient.signOut()
         FirebaseMessaging.getInstance().deleteToken()
         FirebaseAuth.getInstance().signOut()
         LoginManager.getInstance().logOut()*/

    }


    private fun GoogleLogin() {

        val intent = googleSignInClient.signInIntent
        startActivityForResult(intent, 1010)


        /* val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
             .requestIdToken("957862856792-p8k87nlqcnd93s05u8212dav2gg2cat7.apps.googleusercontent.com")
             .requestEmail()
             .build()
         val mGoogleSignInClient = GoogleSignIn.getClient(this@SuperActivity, gso)
         Log.d("TAG", "googleLogin:810 " + mGoogleSignInClient.signInIntent)
         mGoogleSignInClient.signOut()
         val signInIntent = mGoogleSignInClient.signInIntent
         Log.d("TAG", "googleLogin:813 " + signInIntent.data)
         resultGoogleLauncher.launch(signInIntent)*/
    }

    private val resultGoogleLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result ->
            Log.d("TAG", "googleLogin:820 " + result)

            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    // firebaseAuthWithGoogle(account!!)
                    // handleSignInResult(task)

                    /* result?.let {
                         val task: Task<GoogleSignInAccount> =
                             GoogleSignIn.getSignedInAccountFromIntent(it.data)

                     }*/
                } catch (e: ApiException) {
                    Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT)
                        .show()

                    // Sign in failed, handle the error
                }
            }


        }

    private fun handleSignInResult(
        completedTask: Task<GoogleSignInAccount>,
    ) {
        lifecycleScope.launch {
            Log.d(TAG, "googleLogin:869 " + completedTask)

            val account = completedTask.getResult(ApiException::class.java)
            emailLoginStore = account.email ?: ""
            val userObj =
                this@SuperActivity.dataStoreGetUserData().firstOrNull() ?: return@launch
            Log.d(TAG, "googleLogin:803 " + account.id + "-->" + account.displayName)
            this@SuperActivity.dataStoreSetUserData(
                userObj.copy(google = account.id, email = account.email), "380"
            )
            Log.d(TAG, "googleLogin:809 " + userObj)
            _isLoading.value = true
            viewModelRemoveImg.startSocialLogin(
                this@SuperActivity,
                account.id ?: ""
            ) { status, msg, existUser ->
                if (status == true) {
                    _isLoading.value = false
                    Log.d(TAG, "EmailOtpsdsdScreen:887 " + msg)
                    //SocialLoginStatus = status
                    Constns.emailLoginUserExist = existUser
                    FBStatus = status
                    showToast(msg)
                }
            }

        }

    }

    /*private fun handleSignInGoogleResult(
        completedTask: Task<GoogleSignInAccount>,
    ) {
        lifecycleScope.launch {
            Log.d(TAG, "googleLogin:869 " + completedTask)

            val account = completedTask.getResult(ApiException::class.java)
            emailLoginStore = account.email ?: ""
            val userObj =
                this@SuperActivity.dataStoreGetUserData().firstOrNull() ?: return@launch
            Log.d(TAG, "googleLogin:803 " + account.id + "-->" + account.displayName)
            this@SuperActivity.dataStoreSetUserData(
                userObj.copy(google = account.id, email = account.email), "380"
            )
            Log.d(TAG, "googleLogin:809 " + userObj)
            _isLoading.value = true
            viewModelRemoveImg.startSocialLogin(
                this@SuperActivity,
                account.id ?: ""
            ) { status, msg ->
                if (status == true) {
                    _isLoading.value = false
                    FBStatus = status
                    showToast(msg)
                }
            }

        }

    }*/


    /*private fun loginHandler(signInResult: SignInResult) {
        lifecycleScope.launch {
            val account = signInResult.data
            emailLoginStore = account?.email ?: ""
            val userObj =
                this@SuperActivity.dataStoreGetUserData().firstOrNull() ?: return@launch
            Log.d(TAG, "googleLogin:803 " + account?.userId + "-->" + account?.profilePictureUrl)
            this@SuperActivity.dataStoreSetUserData(
                userObj.copy(google = account?.userId, email = account?.email), "380"
            )
            Log.d(TAG, "googleLogin:809 " + userObj)

            _isLoading.value = true
            Log.d(TAG, "googleLogin:813 " + account?.userId)
            viewModelRemoveImg.startSocialLogin(
                this@SuperActivity,
                account?.userId ?: ""
            ) { status, msg ->
                Log.d(TAG, "googleLogin:818 " + status)
                Log.d(TAG, "googleLogin:819 " + msg)
                if (status == true) {
                    _isLoading.value = false
                    FBStatus = status
                    showToast(msg)
                }
            }

        }
    }*/

    private fun initFacebookLogin() {
        FacebookSdk.sdkInitialize(applicationContext)
        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    accessToken = loginResult.accessToken
                    Log.d("TAG", "onError108:758 " + accessToken?.token ?: "")

                    accessToken?.let {
                        getFacebookUserProfile(it)
                    }
                    Log.d("TAG", "onError108:762 " + loginResult.accessToken.toString())

                }

                override fun onCancel() {}
                override fun onError(exception: FacebookException) {
                    if (exception is FacebookAuthorizationException) {
                        if (AccessToken.getCurrentAccessToken() != null) {
                            LoginManager.getInstance().logOut()
                        }
                    }
                    Log.d("TAG", "onError108: " + exception.message)
                    Log.d("TAG", "onError108: " + exception.cause.toString())
                }
            })
    }


    private fun getFacebookUserProfile(accessToken: AccessToken) {
        try {
            val request: GraphRequest = GraphRequest.newMeRequest(
                accessToken
            ) { objects, response ->
                val jsonObject: JSONObject? = response!!.getJSONObject()
                FBName = jsonObject?.get("first_name").toString()

                FBid = jsonObject?.get("id").toString()
                CoroutineScope(Dispatchers.IO).launch {
                    val userObj =
                        applicationContext.dataStoreGetUserData().firstOrNull() ?: return@launch
                    Log.d(TAG, "getFacebookUserProfile:808 " + FBName + "-->" + FBid)
                    applicationContext.dataStoreSetUserData(
                        userObj.copy(name = FBName, facebook = FBid), "380"
                    )
                }


                lifecycleScope.launch(Dispatchers.IO) {
                    _isLoading.value = true
                    viewModelRemoveImg.startSocialLogin(
                        applicationContext,
                        FBid ?: ""
                    ) { status, msg, userExist ->
                        if (status == true) {
                            FBStatus = status
                            emailLoginUserExist = userExist
                            showToast(msg)
                            _isLoading.value = false
                        }
                    }
                }

                if (jsonObject?.has("email") == true) {
                    Log.d(TAG, "getFacebookUserProfile:1020 " + "111111111")

                    emailLoginStore = jsonObject.getString("email") ?: ""
                } else {
                    Log.d(TAG, "getFacebookUserProfile:1024 " + "22222222")

                }


            }
            val parameters = Bundle()
            parameters.putString(
                "fields",
                "id, name, first_name,last_name, picture.type(large),email,gender,birthday"
            )
            request.parameters = parameters
            request.executeAsync()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun FaceBookLogin() {
        /* LoginManager.getInstance()
             .setReadPermissions(
                 this@SuperActivity, listOf("public_profile", "email")
             )*/
        LoginManager.getInstance().logInWithReadPermissions(
            this, listOf(
                //"user_likes",
                //"user_photos",
                "email",
                "public_profile",  //"user_mobile_phone",
                //"user_gender",
                //"user_birthday" // "user_education_history",
                //"user_friends",
                //"user_location",
                //"user_relationships",
                // "user_work_history"
            )


        )
    }


    @Preview
    @Composable
    private fun ProgressDesign() {
        Box(modifier = Modifier
            .clickable {}
            .focusable(true)
            .fillMaxSize()
            .background(ProgressColor), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    @Composable
    private fun RegisterScreenImpl(it: NavBackStackEntry, navController: NavHostController) {
        val email = it.getArg(Arg.ARG_EMAIL)
        val pwd = it.getArg(Arg.ARG_PWD)

        val viewModel: RegisterViewModel = hiltViewModel()


        RegisterScreen(
            topEmail = email,
            topPwd = pwd,
            navController::startNavigation,
            navController::goBack,
            showLoading = {
                Log.d(TAG, "RegisterScreenImpl: testLoadingFlow>>$it")
                _isLoading.value = it
            }, openLocationPicker = {
                lifecycleScope.launch {
                    launch { dataStoreSetTempLocation("", "", "", 258) }.join()
                    locationPickerHelper.openLocationPicker(resultLauncher)
                }
            },
            openImagePicker = {
                setImagePickerFlow()
            },
            viewModel = viewModel
        )
    }

    fun videocallScree() {
        startActivity(Intent(applicationContext, SingleCallActivity::class.java))
    }

    private val locationPickerHelper: LocationPickerHelper by lazy {
        LocationPickerHelper().apply {
            initialize(this@SuperActivity)
        }
    }
    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes

                locationPickerHelper.getDataFromResult(result) { address, lat, long ->

                    lifecycleScope.launch {
                        dataStoreSetTempLocation(name = address, lat = lat, long = long, 281)
                    }

                }
            }
        }

    private var resultLauncherfilters =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes

                locationPickerHelper.getDataFromResult(result) { address, lat, long ->

                    lifecycleScope.launch {
                        filterDataStoreSetTempLocation(name = address, lat = lat, long = long, 477)
                    }

                }
            }
        }


    private fun setImagePickerFlow() {
        Log.d(TAG, "setImagePickerFlowsdf: " + "permissoin not")
        /* if (ContextCompat.checkSelfPermission(this@SuperActivity, READ_EXTERNAL_STORAGE)
             != PackageManager.PERMISSION_GRANTED ||
             ContextCompat.checkSelfPermission(this@SuperActivity, WRITE_EXTERNAL_STORAGE)
             != PackageManager.PERMISSION_GRANTED
         ) {
             Log.d(TAG, "setImagePickerFlow: " + "permissoin yes")

             ActivityCompat.requestPermissions(
                 this@SuperActivity,
                 arrayOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE),
                 100
             )*/
        // } else {
        /*ImagePicker.with(this@SuperActivity)
            .crop()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .createIntent { intent ->
                startForProfileImageResult.launch(intent)
            }*/
        Log.d(TAG, "setImagePickerFlow: " + "permissoin not")
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        //  }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun setImagePickerCameraFlow() {
        isReadPermissionGranted = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_MEDIA_IMAGES
        ) ==
                PackageManager.PERMISSION_GRANTED

        isCameraPermissionGranted =
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_GRANTED

        val permissionRequest: MutableList<String> = ArrayList()
        if (!isReadPermissionGranted) {
            permissionRequest.add(android.Manifest.permission.READ_MEDIA_IMAGES)
        }
        if (!isCameraPermissionGranted) {
            permissionRequest.add(android.Manifest.permission.CAMERA)
        }
        if (permissionRequest.isNotEmpty()) {
            permissionLauncher.launch(permissionRequest.toTypedArray())
        } else {
            ImagePicker.with(this@SuperActivity)
                .cameraOnly()
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }
    }


    private fun setImagePickerSeondFlow() {
        Log.d(TAG, "setImagePickerFlowsdf: " + "permissoin not")
        if (ContextCompat.checkSelfPermission(this@SuperActivity, READ_MEDIA_IMAGES)
            != PackageManager.PERMISSION_GRANTED /*||
            ContextCompat.checkSelfPermission(this@SuperActivity, WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED*/
        ) {
            Log.d(TAG, "setImagePickerFlow: " + "permissoin yes")

            ActivityCompat.requestPermissions(
                this@SuperActivity,
                arrayOf(READ_MEDIA_IMAGES/*, WRITE_EXTERNAL_STORAGE*/),
                100
            )
        } else {
            /* ImagePicker.with(this@SuperActivity)
                 .crop()
                 .compress(1024)
                 .maxResultSize(1080, 1080)
                 .createIntent { intent ->
                     startForProfileImageResult.launch(intent)
                 }*/
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            // Log.d(TAG, "setImagePickerFlow: " + "permissoin not")
            // pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }


    private fun setMultiPleImagePickerFlow() {
        if (ContextCompat.checkSelfPermission(this@SuperActivity, READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this@SuperActivity, WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@SuperActivity,
                arrayOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE),
                100
            )
        } else {
            /*ImagePicker.with(this@SuperActivity)
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }*/
            multiPlePickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data!!
                    var bitmap =
                        ImagePickerHelper(fileUri, this@SuperActivity).getBitmap()
                    if (bitmap != null) {
                        bitmap = scaleBitmap(bitmap, 999999)
                        if (bitmap != null) {
                            val uri = getImageUri(bitmap, this@SuperActivity)

                            bitmap =
                                ImagePickerHelper(uri!!, this@SuperActivity).getBitmap()

                        }
                    }

                    val file = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                        File(filesDir.absolutePath + "${System.currentTimeMillis()}image.png")
                    else
                        File(
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                            "${System.currentTimeMillis()}image.png"
                        )

                    file.writeBitmap(bitmap!!, Bitmap.CompressFormat.PNG, 100)

                    detectFaceIn(bitmap, file)
                }

                ImagePicker.RESULT_ERROR -> {
                    showToast(ImagePicker.getError(data))
                }

                else -> {
                }
            }
        }

    /* private val pickMedia =
         registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
             // Callback is invoked after the user selects a media item or closes the
             // photo picker.
             if (uri != null) {

                 lifecycleScope.launch(Dispatchers.IO) {
                     val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
                     contentResolver.takePersistableUriPermission(uri, flag)

                     val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)

                     val newFilePath = filesDir.path + "/${System.currentTimeMillis()}.png"

                     if (bitmap != null) {

                         try {

                             FileOutputStream(newFilePath).use { out ->
                                 bitmap.compress(
                                     Bitmap.CompressFormat.PNG,
                                     100,
                                     out
                                 ) // bmp is your Bitmap instance
                             }
                         } catch (e: IOException) {
                             e.printStackTrace()
                         }

                     }


                     // val newFile = File(newFilePath)
                     Log.d("PhotoPicker", "Nomediaselected 622" + newFilePath)
                     Log.d("PhotoPicker", "Nomediaselected 647" + bitmap)

                     val newFile = detectFaceIn(bitmap, File(newFilePath))

                     val userObj = dataStoreGetUserData().firstOrNull() ?: return@launch


                     dataStoreSetUserData(
                         userObj.copy(
                             imagePath = newFile.toString(),
                             imageUrl = newFile.toString()
                         ), "380"
                     )
                     Log.d("PhotoPicker", "Nomediaselected 622" + newFilePath)
                     Log.d("PhotoPicker", "Nomediaselected 623" + newFile)

                 }
             } else {
                 Log.d("PhotoPicker", "No media selected")
             }
         }*/
    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            if (uri != null) {

                lifecycleScope.launch(Dispatchers.IO) {
                    val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    contentResolver.takePersistableUriPermission(uri, flag)

                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)

                    val newFilePath = filesDir.path + "/${System.currentTimeMillis()}.png"

                    if (bitmap != null) {
                        try {
                            FileOutputStream(newFilePath).use { out ->
                                bitmap.compress(
                                    Bitmap.CompressFormat.PNG,
                                    100,
                                    out
                                ) // bmp is your Bitmap instance
                            }
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }

                    }
                    val file = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                        File(filesDir.absolutePath + "${System.currentTimeMillis()}image.png")
                    else
                        File(
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                            "${System.currentTimeMillis()}image.png"
                        )

                    file.writeBitmap(bitmap!!, Bitmap.CompressFormat.PNG, 100)
                    // val newFile = File(newFilePath)
                    Log.d("PhotoPicker", "Nomediaselected 622" + newFilePath)
                    Log.d("PhotoPicker", "Nomediaselected 647" + bitmap)

                    // detectFaceIn(bitmap, File(newFilePath))
                    detectFaceIn(bitmap, file)

                    //val userObj = dataStoreGetUserData().firstOrNull() ?: return@launch


                    /*dataStoreSetUserData(
                        userObj.copy(
                            imagePath = newFile.toString(),
                            imageUrl = newFile.toString()
                        ), "380"
                    )*/


                }
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }


    @RequiresApi(Build.VERSION_CODES.O)
    fun detectFaceIn(topBitmap: Bitmap, file: File) {
        val image = InputImage.fromBitmap(topBitmap, 0)
        FaceDetection.getClient().process(image).addOnSuccessListener { faces ->


            when (faces.size) {
                1 -> {
                    Log.d(TAG, "detectFaceInsdsd: " + userPos1)
                    if (userPos1 == 1) {
                        Log.d(TAG, "detectFaceInsdsd:991 " + Constns.ImageIdRemove)
                        viewModelRemoveImg.removeImg(Constns.ImageIdRemove)

                    }
                    if (userPos1 == 2) {
                        Log.d(TAG, "detectFaceInsdsd:995 " + Constns.ImageIdRemove)

                        viewModelRemoveImg.removeImg(Constns.ImageIdRemove)

                    }
                    if (userPos1 == 3) {
                        Log.d(TAG, "detectFaceInsdsd:1001 " + Constns.ImageIdRemove)

                        viewModelRemoveImg.removeImg(Constns.ImageIdRemove)

                    }
                    if (userPos1 == 4) {
                        Log.d(TAG, "detectFaceInsdsd:1007 " + Constns.ImageIdRemove)

                        viewModelRemoveImg.removeImg(Constns.ImageIdRemove)

                    }

                    when (userPos1) {
                        1 -> userImage1.value = file.absolutePath
                        2 -> userImage2.value = file.absolutePath
                        3 -> userImage3.value = file.absolutePath
                        4 -> userImage4.value = file.absolutePath

                    }

                    when (userPos1Register) {
                        1 -> userImage1.value = file.absolutePath
                        2 -> userImage2.value = file.absolutePath
                        3 -> userImage3.value = file.absolutePath
                        4 -> userImage4.value = file.absolutePath

                    }


                    CoroutineScope(Dispatchers.IO).launch {
                        val userObj = dataStoreGetUserData().firstOrNull() ?: return@launch
                        dataStoreSetUserData(
                            userObj.copy(
                                imagePath = if (userImage1.value != null) userImage1.value else userObj.imagePath,
                                imageUrl = if (userImage1.value != null) userImage1.value else userObj.imageUrl,
                                imagePath22 = if (userImage2.value != null) userImage2.value else userObj.imagePath22,
                                imageUrl22 = if (userImage2.value != null) userImage2.value else userObj.imageUrl22,
                                imagePath33 = if (userImage3.value != null) userImage3.value else userObj.imagePath33,
                                imageUrl33 = if (userImage3.value != null) userImage3.value else userObj.imageUrl33,
                                imagePath44 = if (userImage4.value != null) userImage4.value else userObj.imagePath44,
                                imageUrl44 = if (userImage4.value != null) userImage4.value else userObj.imageUrl44,
                            ), "556"
                        )

                        ImageShowEdit = file.absolutePath
                        Log.d(TAG, "detectFaceInsgssd: " + userObj)
                        Log.d(TAG, "detectFaceInsgssd:1021 " + userImage1.value)
                        Log.d(TAG, "detectFaceInsgssd:1023 " + userImage2.value)
                    }

                }

                else -> {
                    /* Toast.makeText(
                         this@SuperActivity,
                         "Please upload image with face",
                         Toast.LENGTH_LONG
                     ).show()*/
                    showToast("Please Upload Image With Face")


                }
            }
        }
            .addOnFailureListener {

            }
    }

    private val SECOND_ACTIVITY_REQUEST_CODE = 0

    private val multiPlePickMedia =
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(6)) { uri ->
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.


            lifecycleScope.launch(Dispatchers.IO) {


                uri.forEachIndexed { index, uri ->

                    if (uri != null) {


                        val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
                        contentResolver.takePersistableUriPermission(uri, flag)

                        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)

                        val newFilePath = filesDir.path + "/${System.currentTimeMillis()}.png"

                        if (bitmap != null) {

                            try {

                                FileOutputStream(newFilePath).use { out ->
                                    bitmap.compress(
                                        Bitmap.CompressFormat.PNG,
                                        100,
                                        out
                                    ) // bmp is your Bitmap instance
                                }
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }

                        }

                        val newFile = File(newFilePath)

                        Log.d(TAG, "Selected FILE EXIST:+ " + newFile.absolutePath)
                        lifecycleScope.launch {
                            if (index == 0) image1.value = newFile.absolutePath
                            if (index == 1) image2.value = newFile.absolutePath
                            if (index == 2) image3.value = newFile.absolutePath
                            if (index == 3) image4.value = newFile.absolutePath
                            if (index == 4) image5.value = newFile.absolutePath
                            if (index == 5) image6.value = newFile.absolutePath
                        }


                        //dataStoreSetUserData(userObj.copy(imagePath1 = newFile.path), "380")
                    } else {
                        Log.d("PhotoPicker", "No media selected")
                    }

                }
                val userObj = dataStoreGetUserData().firstOrNull() ?: return@launch

                dataStoreSetUserData(
                    userObj.copy(
                        imagePath1 = image1.value,
                        imagePath2 = image2.value,
                        imagePath3 = image3.value,
                        imagePath4 = image4.value,
                        imagePath5 = image5.value,
                        imagePath6 = image6.value
                    ), "556"
                )


            }
            //


        }

    private val multiFilePicker: MultiFilePicker by lazy {
        MultiFilePicker(this, filePickerListener)
    }
    private val recorder by lazy {
        AndroidAudioRecorder(applicationContext)
    }

    private val player by lazy {
        AndroidAudioPlayer(applicationContext)
    }


    @SuppressLint("UnsafeIntentLaunch")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "onActivityResult:1573--> " + requestCode)
        try {
            if (requestCode == 1010) {
                Log.d(TAG, "onActivityResult:1575 " + "15785")
                val signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
                if (signInAccountTask.isSuccessful) {
                    showToast("Google sign in Successfully")
                    try {
                        handleSignInResult(signInAccountTask)
                        val googleSignInAccount =
                            signInAccountTask.getResult(ApiException::class.java)
                        firebaseAuthWithGoogle(googleSignInAccount)
                    } catch (e: ApiException) {
                        Log.d(TAG, "onActivityResult:1576  " + e.localizedMessage)
                        Log.d(TAG, "onActivityResult:1576  " + e)
                        throw RuntimeException(e)
                    }
                }

            }
        } catch (e: ApiException) {
            Log.d(TAG, "onActivityResult:1576  " + e.localizedMessage)
            Log.d(TAG, "onActivityResult:1576  " + e)
            throw RuntimeException(e)
        }


        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) { // Activity.RESULT_OK
                finish()

            }
        } else {
            multiFilePicker.attachActivityResult(requestCode, resultCode, data)
        }

        if (requestCode == 123) {
            if (resultCode != RESULT_OK) {
                println("Somthing went to wrong update...")
            }
        }


        callbackManager?.onActivityResult(requestCode, resultCode, data)

        /* val pendingIntent = PendingIntent.getActivity(
               this@SuperActivity,
               requestCode,
               data,
               PendingIntent.FLAG_IMMUTABLE  or PendingIntent.FLAG_MUTABLE
           )*/


    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "googleLogin:success" + "sucesss")
                } else {
                    Log.d(TAG, "googleLogin:failure", task.exception)
                    Log.d(TAG, "googleLogin:failure-->" + task.exception?.localizedMessage)
                    Log.d(TAG, "googleLogin:failure-->2" + task.exception?.message)
                }
            }
    }

    private val filePickerListener = object : MultiFilePicker.OnMultiFileListener {

        override fun onImagesSelected(bitmaps: ArrayList<Uri>) {

            lifecycleScope.launch(Dispatchers.IO) {

                Log.d("TAG", "onImagesSelected321: " + bitmaps)

                messageViewModel.sendFiles(
                    bitmaps, GetMessageListViewModel.SendFileType.Images, this@SuperActivity
                )
            }
        }

        override fun onVideosSelected(videoList: ArrayList<Uri>) {
            lifecycleScope.launch(Dispatchers.IO) {
                /* messageViewModel.sendFiles(
                     videoList, GetMessageListViewModel.SendFileType.Videos,this@SuperActivity
                 )*/
            }
        }

        override fun onFilesSelected(recordList: ArrayList<Uri>) {
            /* messageViewModel.sendFiles(
                 recordList, GetMessageListViewModel.SendFileType.Videos,this@SuperActivity
             )*/

        }
    }


    fun clickSubscriptionPlan() {
        Log.d(TAG, "clickSubscriptionPlan:1206 " + producatId.value)

        iapConnector.subscribe(
            this,
            if (producatId.value == null) "com.monthlyosomatenew" else producatId.value ?: ""
        )
    }

    private fun checkForAppUpdate() {
        /*appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
            val isUpdateAvalible = info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
            val isUpdateAllowed = when (updateType) {
                AppUpdateType.FLEXIBLE -> info.isFlexibleUpdateAllowed
                AppUpdateType.IMMEDIATE -> info.isImmediateUpdateAllowed
                else -> false
            }
            if (isUpdateAvalible && isUpdateAllowed) {
                appUpdateManager.startUpdateFlowForResult(
                    info,
                    updateType,
                    this,
                    123
                )
            }
        }*/
    }

    private val installStateUpdatedListener = InstallStateUpdatedListener { state ->
        /* if (state.installStatus() == InstallStatus.DOWNLOADED) {
             showToast("Dowanload sucessful. Restarting app in 5 seconds.")
             lifecycleScope.launch {
                 delay(5000)
                 appUpdateManager.completeUpdate()

             }
         }
 */

    }

    override fun onResume() {
        super.onResume()
        /* if (updateType == AppUpdateType.IMMEDIATE) {
             appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
                 if (info.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                     appUpdateManager.startUpdateFlowForResult(
                         info,
                         updateType,
                         this,
                         123
                     )
                 }
             }
         }*/

        userLastSeenViewModel.makeOnline()

    }

    override fun onPause() {
        super.onPause()
        userLastSeenViewModel.makeOffline()
    }

    override fun onStart() {
        super.onStart()
        userLastSeenViewModel.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        /*if (updateType == AppUpdateType.FLEXIBLE) {
            appUpdateManager.registerListener(installStateUpdatedListener)
        }*/
        userLastSeenViewModel.makeOffline()

    }


}


/*private fun NavHostController.startNavigation(
    screenType: ScreenType,
    clearBackStack: Boolean = false
) {
    if (clearBackStack) {
        navigate(screenType.name) {
            popUpTo(graph.findStartDestination().id) {
                inclusive = true
            }
        }
    } else {
        navigate(screenType.name)
    }

    *//*if (clearBackStack) {
        popBackStack(graph.startDestinationId, true)
        graph.setStartDestination(screenType.name)
    }*//*
    *//*if (clearBackStack) popBackStack(0, false)
    navigate(screenType.name)*//*
}*/

private var screenRefreshCount = 0

private fun NavHostController.startNavigation(
    route: String, clearBackStack: Boolean = false
) {
    if (clearBackStack) {
        navigate(route) {
            popUpTo(graph.findStartDestination().id) {
                inclusive = true
            }
        }
    } else {
        navigate(route)

        screenRefreshCount = 0
    }
}


private fun NavHostController.goBack() = popBackStack()


object Arg {
    internal const val ARG_TYPE = "type"
    internal const val ARG_SELECTED_POS = "selectedPos"
    internal const val ARG_IS_NEW_USER = "isNewUser"
    internal const val ARG_EMAIL = "email"
    internal const val ARG_PWD = "pwd"
    internal const val ARG_USER_ID = "userId"
    internal const val CHAT_USER_ID = "chatUserId"
    internal const val CHAT_USER_NAME = "chatUserName"
    internal const val CHAT_USER_PHOTO = "chatUserPhoto"
    internal const val LIST_IMAGE_DATE = "listImageData"
}

object Route {
    internal const val SPLASH_BASE = "splash"
    internal const val SPLASH_ROUTE = SPLASH_BASE

    internal const val ON_BOARDING_BASE = "onBoarding"
    internal const val ON_BOARDING_ROUTE = ON_BOARDING_BASE

    internal const val SIGN_IN_BASE = "signIn"
    internal const val SIGN_IN_ROUTE = "$SIGN_IN_BASE/{${Arg.ARG_IS_NEW_USER}}"

    internal const val HOME_BASE = "home"
    internal const val HOME_ROUTE = HOME_BASE


    internal const val TRANSPERANT_BASE = "layer"
    internal const val TRANSPERANT_ROUTE = TRANSPERANT_BASE


    internal const val MEDIA_BASE = "media"
    internal const val MEDIA_ROUTE = MEDIA_BASE

    internal const val SINGLE_MEDIA_BASE = "single"
    internal const val SINGLE_MEDIA_ROUTE = SINGLE_MEDIA_BASE

    internal const val CHAT_BASE = "chat"
    internal const val CHAT_ROUTE = "$CHAT_BASE/{${Arg.CHAT_USER_ID}}/{${Arg.CHAT_USER_NAME}}"


    internal const val EDIT_PROFILE_BASE = "editProfile"
    internal const val EDIT_PROFILE_ROUTE = EDIT_PROFILE_BASE

    internal const val RAGI_PROFILE_BASE = "ragiProfile"
    internal const val RAGI_PROFILE_ROUTE = RAGI_PROFILE_BASE

    internal const val MY_PERSONALITY_BASE = "myPersonality"
    internal const val MY_PERSONALITY_ROUTE = "$MY_PERSONALITY_BASE/{${Arg.ARG_SELECTED_POS}}"

    internal const val NORMAL_ZODIAC_BASE = "normalZodiac"
    internal const val NORMAL_ZODIAC_ROUTE = NORMAL_ZODIAC_BASE

    internal const val CHINESE_ZODIAC_BASE = "chineseZodiac"
    internal const val CHINESE_ZODIAC_ROUTE = CHINESE_ZODIAC_BASE

    internal const val PERSONALITY_TEST_BASE = "personalityTest"
    internal const val PERSONALITY_TEST_ROUTE = PERSONALITY_TEST_BASE

    internal const val GET_PREMIUM_BASE = "getPremium"
    internal const val GET_PREMIUM_ROUTE = GET_PREMIUM_BASE

    internal const val FEEDBACK_RECORDS_BASE = "feedbackRecords"
    internal const val FEEDBACK_RECORDS_ROUTE = FEEDBACK_RECORDS_BASE

    internal const val CHANGE_PASSWORD_BASE = "changePassword"
    internal const val CHANGE_PASSWORD_ROUTE = CHANGE_PASSWORD_BASE

    internal const val CHANGE_EMAIL_BASE = "changeEmail"
    internal const val CHANGE_EMAIL_ROUTE = CHANGE_EMAIL_BASE

    internal const val TERMS_PRIVACY_BASE = "termsPrivacy"
    internal const val TERMS_PRIVACY_ROUTE = "$TERMS_PRIVACY_BASE/{${Arg.ARG_TYPE}}"

    internal const val FEEDBACK_BASE = "feedback"
    internal const val FEEDBACK_ROUTE = FEEDBACK_BASE

    internal const val REGISTER_BASE = "register"
    internal const val REGISTER_ROUTE = "$REGISTER_BASE/{${Arg.ARG_EMAIL}}/{${Arg.ARG_PWD}}"

    internal const val MAIN_SETTING_BASE = "mainSetting"
    internal const val MAIN_SETTING_ROUTE = MAIN_SETTING_BASE

    internal const val SEARCH_USER_BASE = "searchUser"
    internal const val SEARCH_USER_ROUTE = SEARCH_USER_BASE

    internal const val DISCOVER_FILTER_BASE = "discoverFilter"
    internal const val DISCOVER_FILTER_ROUTE = DISCOVER_FILTER_BASE

    internal const val PROFILE_DETAIL_BASE = "profileDetail"
    internal const val PROFILE_DETAIL_ROUTE = "$PROFILE_DETAIL_BASE/{${Arg.ARG_USER_ID}}"

    internal const val EDIT_INTEREST_BASE = "editInterest"
    internal const val EDIT_INTEREST_ROUTE = EDIT_INTEREST_BASE

    internal const val REGISTER_INTEREST_BASE = "registerInterest"
    internal const val REGISTER_INTEREST_ROUTE = REGISTER_INTEREST_BASE


    internal const val FILTER_INTEREST_BASE = "filterInterest"
    internal const val FILTER_INTEREST_ROUTE = FILTER_INTEREST_BASE
}

