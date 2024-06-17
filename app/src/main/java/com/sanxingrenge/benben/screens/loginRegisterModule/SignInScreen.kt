package com.sanxingrenge.benben.screens.loginRegisterModule

import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.sanxingrenge.benben.R
import com.sanxingrenge.benben.constant.Constns
import com.sanxingrenge.benben.constant.Constns.FBName
import com.sanxingrenge.benben.constant.Constns.FBStatus
import com.sanxingrenge.benben.constant.Constns.emailLoginStore
import com.sanxingrenge.benben.constant.Constns.emailLoginUserExist
import com.sanxingrenge.benben.constant.Constns.emailSee
import com.sanxingrenge.benben.constant.Constns.isFirstTime
import com.sanxingrenge.benben.constant.Constns.pwdSee
import com.sanxingrenge.benben.constant.PWD_NOT_MATCH
import com.sanxingrenge.benben.constant.forgotPasswordText
import com.sanxingrenge.benben.constant.resendCodeText
import com.sanxingrenge.benben.constant.showToast
import com.sanxingrenge.benben.constant.signInUsingPasswordText
import com.sanxingrenge.benben.constant.slideInEnterAnimation
import com.sanxingrenge.benben.constant.slideInExitAnimation
import com.sanxingrenge.benben.screens.Route
import com.sanxingrenge.benben.ui.theme.Amaranth
import com.sanxingrenge.benben.ui.theme.Dune
import com.sanxingrenge.benben.ui.theme.Harp
import com.sanxingrenge.benben.ui.theme.Nobel
import com.sanxingrenge.benben.ui.theme.VioletBlue
import com.sanxingrenge.benben.ui.theme.White
import com.sanxingrenge.benben.ui.theme.poppinsMedium
import com.sanxingrenge.benben.ui.theme.poppinsRegular
import com.sanxingrenge.benben.uiComponents.AddSpace
import com.sanxingrenge.benben.uiComponents.CustomInputType
import com.sanxingrenge.benben.uiComponents.CustomSignInText
import com.sanxingrenge.benben.uiComponents.CustomSignUpText
import com.sanxingrenge.benben.uiComponents.OtpEditText
import com.sanxingrenge.benben.uiComponents.SignInEditText
import com.sanxingrenge.benben.uiComponents.SolidButton
import com.sanxingrenge.benben.uiComponents.WelcomeText
import com.sanxingrenge.benben.viewModel.RegisterViewModel
import com.sanxingrenge.benben.viewModel.SignInViewModel

/*
companion object { const val IS_NEW_USER = "isNewUser" }
private val isNewUser: Boolean by lazy { intent.getBooleanExtra(IS_NEW_USER, false) }
*/

private const val TAG = "SignInScreen"

enum class VerificationFlow {
    ragister,
    emailOtp
}

//private var cuurantVerifationFlow=VerificationFlow.emailOtp
private var cuurantVerifationFlow = VerificationFlow.ragister

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun SignInScreen(
    openScreen: ((String, Boolean) -> Unit)? = null,
    showLoading: ((Boolean) -> Unit)? = null,
    googleLogin: (() -> Unit)? = null,
    faceBookLogin: (() -> Unit)? = null,
    popBack: (() -> Unit)? = null,
    viewModel: SignInViewModel? = null
) {
    val signInScreenState =
        viewModel?.uiState?.collectAsState()?.value
    Log.d(TAG, "googleLogin:SigninScreen 108 " + emailLoginStore)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            when (signInScreenState) {
                SignInScreenState.EmailOtp -> {
                    AddSpace(80.dp)
                }

                SignInScreenState.EmailPwd, SignInScreenState.CreateAccount, SignInScreenState.VerifyOtp, SignInScreenState.ForgotPwd, SignInScreenState.ForgotPwdVerifyOtp, SignInScreenState.ResetPwd -> {
                    AddSpace(size = 20.dp)
                    Box(Modifier.width(320.dp)) {
                        IconButton(onClick = {
                            when (signInScreenState) {
                                SignInScreenState.EmailOtp -> popBack?.invoke()
                                SignInScreenState.EmailPwd -> popBack?.invoke()
                                SignInScreenState.CreateAccount -> popBack?.invoke()
                                SignInScreenState.VerifyOtp -> popBack?.invoke()
                                SignInScreenState.ForgotPwd -> popBack?.invoke()
                                SignInScreenState.ForgotPwdVerifyOtp -> popBack?.invoke()
                                SignInScreenState.ResetPwd -> popBack?.invoke()
                            }
                            Constns.flow = ""
                        }) {
                            Image(
                                painter = painterResource(id = R.drawable.back_arrow),
                                contentDescription = "",
                                modifier = Modifier.size(40.dp),
                            )
                        }
                    }
                    AddSpace(size = 11.dp)
                }

                else -> {}
            }
            when (signInScreenState) {
                SignInScreenState.EmailOtp, SignInScreenState.EmailPwd -> WelcomeText(
                    "Welcome", "Sign In to continue"
                )

                SignInScreenState.ForgotPwd -> WelcomeText("Forgot Password", "")
                SignInScreenState.CreateAccount -> WelcomeText("Create Account", "")
                SignInScreenState.VerifyOtp -> WelcomeText("Verification", "")
                SignInScreenState.ForgotPwdVerifyOtp -> WelcomeText("Verification", "")
                SignInScreenState.ResetPwd -> WelcomeText("Reset Password", "")
                else -> {}
            }
            AddSpace(60.dp)
            Box {
                androidx.compose.animation.AnimatedVisibility(
                    visible = signInScreenState == SignInScreenState.EmailOtp,
                    enter = slideInEnterAnimation(),
                    exit = slideInExitAnimation()
                ) {
                    EmailOtpScreen(viewModel, showLoading, googleLogin, faceBookLogin, openScreen)
                }
                androidx.compose.animation.AnimatedVisibility(
                    visible = signInScreenState == SignInScreenState.ForgotPwd,
                    enter = slideInEnterAnimation(),
                    exit = slideInExitAnimation()
                ) {
                    ForgotPwd(viewModel, showLoading, googleLogin, faceBookLogin)
                }
                androidx.compose.animation.AnimatedVisibility(
                    visible = signInScreenState == SignInScreenState.EmailPwd,
                    enter = slideInEnterAnimation(),
                    exit = slideInExitAnimation()
                ) {
                    EmailPwdScreen(viewModel, showLoading, openScreen, googleLogin, faceBookLogin)
                }
                androidx.compose.animation.AnimatedVisibility(
                    visible = signInScreenState == SignInScreenState.CreateAccount,
                    enter = slideInEnterAnimation(),
                    exit = slideInExitAnimation()
                ) {
                    openScreen?.let {
                        CreateAccountWithEmailPwdScreen(it, showLoading, googleLogin, faceBookLogin)
                    }
                }
                androidx.compose.animation.AnimatedVisibility(
                    visible = signInScreenState == SignInScreenState.VerifyOtp,
                    enter = slideInEnterAnimation(),
                    exit = slideInExitAnimation()
                ) {
                    if (openScreen != null && showLoading != null) {
                        OtpVerificationScreen(
                            openScreen = openScreen,
                            showLoading = showLoading,
                            viewModel = viewModel,
                            cuurantVerifationFlow = cuurantVerifationFlow
                        )
                    }
                }
                androidx.compose.animation.AnimatedVisibility(
                    visible = signInScreenState == SignInScreenState.ForgotPwdVerifyOtp,
                    enter = slideInEnterAnimation(),
                    exit = slideInExitAnimation()
                ) {
                    if (openScreen != null && showLoading != null) {
                        ForgotPSWOtpVerificationScreen(
                            openScreen = openScreen,
                            showLoading = showLoading,
                            viewModel = viewModel,
                        )
                    }
                }

                androidx.compose.animation.AnimatedVisibility(
                    visible = signInScreenState == SignInScreenState.ResetPwd,
                    enter = slideInEnterAnimation(),
                    exit = slideInExitAnimation()
                ) {
                    if (openScreen != null && showLoading != null) {
                        ResetPwdScreen(
                            openScreen = openScreen,
                            showLoading = showLoading,
                            viewModel = viewModel,
                        )
                    }
                }
            }
            if (signInScreenState == SignInScreenState.EmailOtp || signInScreenState == SignInScreenState.EmailPwd || signInScreenState == SignInScreenState.VerifyOtp || signInScreenState == SignInScreenState.ForgotPwd || signInScreenState == SignInScreenState.ForgotPwdVerifyOtp || signInScreenState == SignInScreenState.ResetPwd) {
                CustomSignUpText {
                    //signup click
                    viewModel.updateUiState(SignInScreenState.CreateAccount, 141)
                }
            } else if (signInScreenState == SignInScreenState.CreateAccount) {
                CustomSignInText {
                    //signup click
                    cuurantVerifationFlow = VerificationFlow.ragister
                    viewModel.updateUiState(SignInScreenState.EmailOtp, 146)
                }
            }
            AddSpace(size = 20.dp)
            BackHandler {
                if (signInScreenState == SignInScreenState.EmailOtp) popBack?.invoke()
                else viewModel?.updateUiState(SignInScreenState.EmailOtp, 152)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun OtpVerificationScreen(
    viewModel: SignInViewModel? = hiltViewModel(),
    openScreen: (String, Boolean) -> Unit,
    showLoading: (Boolean) -> Unit,
    cuurantVerifationFlow: VerificationFlow? = null
) {
    val openRecoderDialog = remember {
        mutableStateOf(false)
    }
    val showAllError = remember { mutableStateOf(false) }
    val userEmail = remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    viewModel?.getStoredEmail(context) {
        userEmail.value = it
    }


    Log.d(TAG, "ForgotPSWOtpVerificationScreen321: " + userEmail.value)
    val viewModelOtp: RegisterViewModel = hiltViewModel()

    var strOtp = ""
    var isErrorTop = true
    Column {
        Text(
            text = "Code has sent to",
            color = Nobel,
            fontFamily = poppinsRegular,
            fontSize = 14.sp,
        )
        Row {
            Text(
                text = emailSee,
                color = VioletBlue,
                fontFamily = poppinsRegular,
                fontSize = 14.sp,
            )
            AddSpace(size = 15.dp)
            Log.d(TAG, "OtpVerificationScreen:298 " + emailLoginStore)
            Log.d(TAG, "OtpVerificationScreen:299 " + FBName)
            if (FBName == null || FBName == "") {
                if (emailLoginStore == "" || emailLoginStore == null) {
                    Image(
                        painter = painterResource(id = R.drawable.editpencil),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .size(15.dp)
                            .clickable {
                                openRecoderDialog.value = true
                            }
                    )
                }

            } else {

            }

        }

        AddSpace(size = 20.dp)
        OtpEditText(showAllError) { str, isError ->
            strOtp = str
            isErrorTop = isError
        }
        AddSpace(size = 5.dp)
        Box(
            modifier = Modifier.width(300.dp), contentAlignment = Alignment.CenterEnd
        ) {
            ClickableText(text = resendCodeText, onClick = {
                if (cuurantVerifationFlow == VerificationFlow.ragister || Constns.flow == "1") {
                    if (emailSee != "" && pwdSee != "") {
                        showLoading?.invoke(true)

                        viewModelOtp.sendEmailOtp(
                            context,
                            emailSee,
                            pwdSee
                        ) { status: Boolean, msg: String, otp: String ->

                            showLoading?.invoke(false)
                            if (status) {
                                context.showToast(msg)
                                //context.showToast(otp)
                            } else context.showToast(msg)
                        }

                        //   openScreen.invoke("${Route.REGISTER_BASE}/$email/$pwd", false)
                    } else {
                        showAllError.value = true
                    }


                } else {
                    showLoading.invoke(true)
                    viewModel?.getEmailOTp(emailSee, "") { isSuccess, errorMsg, otp ->
                        showLoading.invoke(false)
                        if (isSuccess) {
                            // context.showToast(otp)
                            viewModel.updateUiState(
                                SignInScreenState.VerifyOtp, 331
                            )

                        } else {
                            context.showToast(errorMsg)
                        }

                    }
                }

                /* when (cuurantVerifationFlow) {
                     VerificationFlow.ragister -> {
                         if (emailSee != "" && pwdSee != "") {
                             showLoading?.invoke(true)

                             viewModelOtp.sendEmailOtp(context,emailSee,pwdSee) { status: Boolean, msg: String, otp: String ->

                                 showLoading?.invoke(false)
                                 if (status) {
                                     context.showToast(msg)
                                     context.showToast(otp)
                                 } else context.showToast(msg)
                             }

                             //   openScreen.invoke("${Route.REGISTER_BASE}/$email/$pwd", false)
                         }else {
                             showAllError.value = true
                         }

                     }

                     VerificationFlow.emailOtp -> {
                         showLoading?.invoke(true)
                         viewModel?.getEmailOTp(emailSee,"") { isSuccess, errorMsg, otp ->
                             showLoading?.invoke(false)
                             if (isSuccess) {
                                 context.showToast(otp)
                                 viewModel.updateUiState(
                                     SignInScreenState.VerifyOtp, 331
                                 )

                             } else {
                                 context.showToast(errorMsg)
                             }

                         }
                     }

                     null -> {

                     }
                 }*/


            })
        }
        AddSpace(size = 30.dp)
        SolidButton("Continue", poppinsMedium) {
            if (!isErrorTop && strOtp != "") {
                showLoading.invoke(true)
                when (cuurantVerifationFlow) {
                    VerificationFlow.ragister -> {
                        /* viewModel?.callVerifyOtpAndRegisterApi(strOtp, context) {
                             showLoading.invoke(false)
                             if (it) {
                                 openScreen.invoke(Route.HOME_BASE, true)
                             }
                         }*/
                        viewModel?.getVerifyRegister(emailSee, strOtp, context) { status, msg ->
                            Log.d(TAG, "OtpVerificationScreen: " + status)
                            showLoading.invoke(false)
                            if (status == true) {
                                context.showToast(msg)
                                openScreen.invoke(Route.HOME_BASE, true)
                            }
                            if (status == false) {
                                context.showToast(msg)

                            }

                        }
                    }

                    VerificationFlow.emailOtp -> {
                        showLoading.invoke(true)
                        viewModel?.getLoginWithOtp(strOtp, context) { isSuccess, errorMsg ->
                            showLoading.invoke(false)
                            if (isSuccess) {
                                openScreen.invoke(Route.HOME_BASE, true)
                            } else {
                                context.showToast(errorMsg)
                            }

                        }
                    }

                    null -> {

                    }
                }


            } else {
                context.showToast("Please Enter OTP")

            }
        }
        AddSpace(size = 217.dp)
    }

    if (openRecoderDialog.value) {
        Dialog(
            onDismissRequest = {
                openRecoderDialog.value = false
            }
        ) {
            AudioRecordPopUpContent(showLoading) {
                openRecoderDialog.value = false
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AudioRecordPopUpContent(
    showLoading: (Boolean) -> Unit,
    dismissDialog: ((Boolean) -> Unit)?
) {
    val showAllError = remember {
        mutableStateOf(false)
    }

    val viewModelOtp: RegisterViewModel = hiltViewModel()
    var emailError = true
    var emailStr = ""
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(horizontal = 0.dp)
            .background(
                Color.White, RoundedCornerShape(20.dp)
            )
            .fillMaxWidth()
            .padding(top = 0.dp, bottom = 20.dp)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center,
    ) {


        AddSpace(20.dp)
        SignInEditText("Enter email address",
            CustomInputType.Email,
            showAllError = showAllError,
            onDataChanged = {}) { isError, str ->
            emailError = isError
            emailStr = str
        }
        AddSpace(35.dp)

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Button(
                onClick = {
                    if (emailStr != "") {
                        showLoading.invoke(true)

                        viewModelOtp.sendEmailOtp(
                            context,
                            emailStr,
                            ""
                        ) { status: Boolean, msg: String, otp: String ->

                            showLoading.invoke(false)
                            if (status) {
                                emailSee = emailStr
                                context.showToast(msg)
                                // context.showToast(otp)
                                dismissDialog?.invoke(false)
                            } else context.showToast(msg)
                        }

                        //   openScreen.invoke("${Route.REGISTER_BASE}/$email/$pwd", false)
                    } else {
                        showAllError.value = true
                    }
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Amaranth
                ),
                modifier = Modifier
                    .weight(1f, true)
                    .padding(horizontal = 70.dp),
                contentPadding = PaddingValues(vertical = 9.dp)
            ) {
                Text(
                    text = "Submit",
                    fontFamily = poppinsMedium,
                    fontSize = 12.sp,
                    color = White
                )
            }
        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun ForgotPSWOtpVerificationScreen(
    viewModel: SignInViewModel? = hiltViewModel(),
    openScreen: (String, Boolean) -> Unit,
    showLoading: (Boolean) -> Unit,
) {
    val showAllError = remember { mutableStateOf(false) }
    val userEmail = remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    viewModel?.getStoredEmail(context) {
        userEmail.value = it
    }
    val viewModelOtp: RegisterViewModel = hiltViewModel()
    var strOtp = ""
    var isErrorTop = true
    Log.d(TAG, "ForgotPSWOtpVerificationScreen321: " + userEmail.value)
    Column {
        Text(
            text = "Code has sent to",
            color = Nobel,
            fontFamily = poppinsRegular,
            fontSize = 14.sp,
        )
        Text(
            text = emailSee,
            color = VioletBlue,
            fontFamily = poppinsRegular,
            fontSize = 14.sp,
        )
        AddSpace(size = 20.dp)
        OtpEditText(showAllError) { str, isError ->
            strOtp = str
            isErrorTop = isError
        }
        AddSpace(size = 5.dp)
        Box(
            modifier = Modifier.width(300.dp), contentAlignment = Alignment.CenterEnd
        ) {
            ClickableText(text = resendCodeText, onClick = {
                if (emailSee != "" && pwdSee != "") {
                    showLoading.invoke(true)

                    viewModelOtp.sendEmailOtp(
                        context,
                        emailSee,
                        pwdSee
                    ) { status: Boolean, msg: String, otp: String ->

                        showLoading.invoke(false)
                        if (status) {
                            context.showToast(msg)
                            // context.showToast(otp)

                        } else context.showToast(msg)
                    }

                    //   openScreen.invoke("${Route.REGISTER_BASE}/$email/$pwd", false)
                } else {
                    showAllError.value = true
                }
            })
        }
        AddSpace(size = 30.dp)
        SolidButton("Continue", poppinsMedium) {
            if (!isErrorTop && strOtp != "") {

                showLoading.invoke(true)
                viewModel?.getOtpVerifyForPwd(strOtp, context) { isSuccess, errorMsg ->
                    showLoading.invoke(false)
                    if (isSuccess) {
                        viewModel.updateUiState(
                            SignInScreenState.ResetPwd, 331
                        )
                        // openScreen.invoke(Route.ON_BOARDING_ROUTE,true)
                    } else {
                        context.showToast(errorMsg)
                    }

                }


            }
        }
        AddSpace(size = 217.dp)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateAccountWithEmailPwdScreen(
    openScreen: (String, Boolean) -> Unit,
    showLoading: ((Boolean) -> Unit)?,
    googleLogin: (() -> Unit)? = null,
    faceBookLogin: (() -> Unit)? = null,

    ) {
    val context = LocalContext.current
    val showAllError = remember {
        mutableStateOf(false)
    }
    var isEmailValid = false
    var isPwdValid = false
    var isConfirmPwdValid = false
    var email = if (emailLoginStore == "") "" else emailLoginStore
    var pwd = ""
    var confirmPwd = ""
    val viewModel: RegisterViewModel = hiltViewModel()


    val msgemail = remember {
        mutableStateOf("")
    }
    Log.d(TAG, "CreateAccountWithEmailPdsaasdwdScreen: " + emailLoginStore + "--" + FBName)

    Column {
        if (emailLoginStore != null && emailLoginStore != "") {
            SignInEditText("Enter Email Email",
                CustomInputType.Email,
                isEnabled = false,
                showAllError = showAllError,
                selectedText = email,
                onDataChanged = {
                    if (it.count() > 6) {
                        viewModel.getEmailExist(context, it) { it, msg ->
                            Log.d(TAG, "CreateAccountWithEmailPwdScreen:702 " + it)
                            Log.d(TAG, "CreateAccountWithEmailPwdScreen:703 " + Constns.FBStatus)
                            showLoading?.invoke(false)
                            if (it == true) {
                                if (FBStatus == true && emailLoginUserExist == 0 || emailLoginUserExist == 1) {
                                    msgemail.value = emailLoginStore ?: ""
                                    email = ""
                                    openScreen.invoke(Route.HOME_BASE, true)
                                }
                            }


                        }
                    }
                }) { isError, str ->
                isEmailValid = !isError
                email = str
            }
        } else if (FBName != null && FBName != "" && isFirstTime==true) {
            Log.d(TAG, "CreateAccountWithEmailPwdScreen:719 " + Constns.FBName)
            isFirstTime=false
            openScreen.invoke(Route.HOME_BASE, true)
        } else {
            SignInEditText("Enter Email Address",
                CustomInputType.Email,
                showAllError = showAllError,
                onDataChanged = {
                    if (it.count() > 6) {
                        viewModel.getEmailExist(context, it) { it, msg ->
                            showLoading?.invoke(false)
                            Log.d(TAG, "CreateAccountWithEmailPwdScreen:692 " + emailLoginStore)
                            msgemail.value = msg

                        }
                    }
                }) { isError, str ->
                isEmailValid = !isError
                email = str
            }
        }
        AddSpace(size = 20.dp)
        if (emailLoginStore != null && emailLoginStore != "") {

        } else if (FBName != null && FBName != "") {
            showLoading?.invoke(false)
            openScreen.invoke(Route.HOME_BASE, true)
        } else {
            SignInEditText(
                "Enter password",
                CustomInputType.Pwd,
                showAllError = showAllError,
                onDataChanged = {}) { isError, str ->
                isPwdValid = !isError
                pwd = str
            }
            AddSpace(size = 20.dp)
            SignInEditText("Enter Confirm Password",
                CustomInputType.PwdC,
                showAllError = showAllError,
                onDataChanged = {}) { isError, str ->
                isConfirmPwdValid = !isError
                confirmPwd = str
            }

            AddSpace(size = 50.dp)
        }


        SolidButton("Continue", poppinsMedium) {
            //    openScreen.invoke(ScreenType.Register.name+"/hsbch.dcd.com/21652161", false)
            emailSee = email ?: ""
            pwdSee = pwd
            if (pwd != confirmPwd) {
                context.showToast(PWD_NOT_MATCH)
            } else if (msgemail.value == "Email is exist") {
                context.showToast("Email is Exist")
                return@SolidButton
            } else if (isEmailValid && isPwdValid && isConfirmPwdValid && email != "" && pwd != "") {
                showLoading?.invoke(true)

                viewModel.sendEmailOtp(
                    context,
                    email ?: "",
                    pwd
                ) { status: Boolean, msg: String, otp: String ->

                    showLoading?.invoke(false)
                    if (status) {

                        context.showToast(msg)
                        //context.showToast(otp)
                        email = ""
                        Constns.flow = "1"
                        openScreen.invoke(
                            Route.SIGN_IN_BASE + "/${SignInScreenState.VerifyOtp.name}",
                            false
                        )


                    } else context.showToast(msg)
                }

                //   openScreen.invoke("${Route.REGISTER_BASE}/$email/$pwd", false)
            } else {
                showAllError.value = true
            }
        }

        AddSpace(size = 50.dp)


        Row {
            AddSpace(size = 20.dp)

            OutlinedButton(
                modifier = Modifier
                    .width(120.dp)
                    .height(45.dp), onClick = {
                    //fb button click
                    googleLogin?.invoke()
                }, border = BorderStroke(1.dp, Dune), shape = RoundedCornerShape(10.dp)
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AddSpace(size = 5.dp)
                    Text(
                        text = "Google",
                        fontFamily = poppinsMedium,
                        fontSize = 13.sp,
                        color = Dune,
                        modifier = Modifier.clickable {

                        }
                    )
                }


            }
            AddSpace(size = 20.dp)

            OutlinedButton(
                modifier = Modifier
                    .width(120.dp)
                    .height(45.dp), onClick = {
                    //fb button click
                    faceBookLogin?.invoke()
                }, border = BorderStroke(1.dp, Dune), shape = RoundedCornerShape(10.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AddSpace(size = 5.dp)
                    Text(
                        text = "Facebook",
                        fontFamily = poppinsMedium,
                        fontSize = 13.sp,
                        color = Dune,
                        modifier = Modifier.clickable {
                            //faceBookLogin?.invoke()
                        }
                    )
                }

            }


        }

        AddSpace(size = 70.dp)
    }
    Route.SPLASH_BASE
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun EmailPwdScreen(
    viewModel: SignInViewModel? = hiltViewModel(),
    showLoading: ((Boolean) -> Unit)?,
    openScreen: ((String, Boolean) -> Unit)?,
    googleLogin: (() -> Unit)? = null,
    faceBookLogin: (() -> Unit)? = null,

    ) {
    val viewModelRag: RegisterViewModel = hiltViewModel()
    val context = LocalContext.current
    Column {
        val showAllError = remember {
            mutableStateOf(false)
        }
        var emailError = true
        var pwdError = true
        var pwdStr = ""
        var emailStr = if (emailLoginStore == "") "" else emailLoginStore

        if (emailLoginStore != null && emailLoginStore != "") {
            SignInEditText("Enter Email Address",
                CustomInputType.Email,
                isEnabled = false,
                showAllError = showAllError,
                selectedText = emailLoginStore,
                onDataChanged = {}) { isValid, str ->
                emailError = isValid
                emailStr = str
            }
        } else {
            SignInEditText("Enter Email Address",
                CustomInputType.Email,
                showAllError = showAllError,
                onDataChanged = {}) { isValid, str ->
                emailError = isValid
                emailStr = str
            }
        }

        AddSpace(size = 20.dp)
        SignInEditText(
            "Enter Password",
            CustomInputType.Pwd,
            showAllError = showAllError,
            onDataChanged = {}) { isError, str ->
            pwdError = isError
            pwdStr = str
        }
        AddSpace(size = 5.dp)
        Box(
            modifier = Modifier.width(300.dp), contentAlignment = Alignment.CenterEnd
        ) {
            ClickableText(text = forgotPasswordText, onClick = {
                viewModel?.updateUiState(
                    SignInScreenState.ForgotPwd, 340
                )

            })
        }
        AddSpace(size = 50.dp)
        SolidButton("Continue", poppinsMedium) {

            if (!emailError && !pwdError && emailStr != "" && pwdStr != "") {
                showLoading?.invoke(true)
                viewModel?.getEmailPassword(
                    context,
                    emailStr ?: "",
                    pwdStr
                ) { isSuccess, errorMsg ->
                    context.showToast(errorMsg)
                    showLoading?.invoke(false)
                    if (isSuccess == true) {
                        openScreen?.invoke(Route.HOME_BASE, true)

                    } else {
                        viewModel.updateUiState(
                            SignInScreenState.EmailPwd, 308
                        )
                    }
                }
                /* viewModel.updateUiState(
                SignInScreenState.EmailOtp,308)*/
            } else showAllError.value = true
        }


        AddSpace(size = 50.dp)


        /* Row {
             AddSpace(size = 20.dp)

             OutlinedButton(
                 modifier = Modifier
                     .width(120.dp)
                     .height(45.dp), onClick = {
                     //fb button click
                 }, border = BorderStroke(1.dp, Dune), shape = RoundedCornerShape(10.dp)
             ) {

                 Column(
                     horizontalAlignment = Alignment.CenterHorizontally,
                     modifier = Modifier.fillMaxWidth()
                 ) {
                     AddSpace(size = 5.dp)
                     Text(
                         text = "Google",
                         fontFamily = poppinsMedium,
                         fontSize = 13.sp,
                         color = Dune,
                         modifier = Modifier.clickable {
                             googleLogin?.invoke()
                         }
                     )
                 }
             }
             AddSpace(size = 20.dp)

             OutlinedButton(
                 modifier = Modifier
                     .width(120.dp)
                     .height(45.dp), onClick = {
                     //fb button click
                 }, border = BorderStroke(1.dp, Dune), shape = RoundedCornerShape(10.dp)
             ) {
                 Column(
                     horizontalAlignment = Alignment.CenterHorizontally,
                     modifier = Modifier.fillMaxWidth()
                 ) {
                     AddSpace(size = 5.dp)
                     Text(
                         text = "Facebook",
                         fontFamily = poppinsMedium,
                         fontSize = 13.sp,
                         color = Dune,
                         modifier = Modifier.clickable {
                             faceBookLogin?.invoke()
                         }
                     )
                 }

             }


         }*/

        AddSpace(size = 70.dp)

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun ResetPwdScreen(
    viewModel: SignInViewModel? = hiltViewModel(),
    showLoading: ((Boolean) -> Unit)?,
    openScreen: ((String, Boolean) -> Unit)?
) {
    Column {
        val showAllError = remember {
            mutableStateOf(false)
        }
        var pwdError = true
        var pwdStr = ""
        var pwdConfiError = true
        var pwdConfiStr = ""
        SignInEditText("Enter New Password",
            CustomInputType.Pwd,
            showAllError = showAllError,
            onDataChanged = {}) { isError, str ->
            pwdError = isError
            pwdStr = str
        }
        AddSpace(size = 20.dp)
        SignInEditText(
            "Confirm Password",
            CustomInputType.Pwd,
            showAllError = showAllError,
            onDataChanged = {}) { isError, str ->
            pwdConfiError = isError
            pwdConfiStr = str
        }
        AddSpace(size = 5.dp)

        AddSpace(size = 50.dp)
        val context = LocalContext.current
        SolidButton("Continue", poppinsMedium) {

            if (!pwdError && !pwdConfiError && pwdStr != "" && pwdConfiStr != "") {
                showLoading?.invoke(true)
                viewModel?.changeForgotPwd(pwdStr, pwdConfiStr) { isSuccess, errorMsg ->
                    showLoading?.invoke(false)
                    if (isSuccess) {
                        openScreen?.invoke(Route.ON_BOARDING_ROUTE, true)
                    } else {
                        context.showToast(errorMsg)
                    }

                }
                /* viewModel.updateUiState(
                SignInScreenState.EmailOtp,308)*/
            } else showAllError.value = true
        }
        AddSpace(size = 179.dp)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EmailOtpScreen(
    viewModel: SignInViewModel? = hiltViewModel(),
    showLoading: ((Boolean) -> Unit)?,
    googleLogin: (() -> Unit)? = null,
    faceBookLogin: (() -> Unit)? = null,
    openScreen: ((String, Boolean) -> Unit)?,

    ) {
    val showAllError = remember { mutableStateOf(false) }
    var isEmailError = true
    // var emailStr = ""
    var emailStr = if (emailLoginStore == "") "" else emailLoginStore
    Log.d(TAG, "EmailOtpScreen:974 " + emailLoginStore)
    Column {
        if (emailLoginStore != null && emailLoginStore != "") {
            SignInEditText("Enter Email Address",
                CustomInputType.Email,
                isEnabled = false,
                showAllError = showAllError,
                selectedText = emailLoginStore,
                onDataChanged = {}) { isValid, str ->
                isEmailError = isValid
                emailStr = str
            }
        } else {
            SignInEditText("Enter Email Address",
                CustomInputType.Email,
                showAllError = showAllError,
                onDataChanged = {}) { isValid, str ->
                isEmailError = isValid
                emailStr = str
            }
        }
        /*   SignInEditText("Enter Email Address",
            CustomInputType.Email,
            showAllError = showAllError,
            onDataChanged = {}) { isValid, str ->
            isEmailError = isValid
            emailStr = str
        }*/
        AddSpace(30.dp)
        val context = LocalContext.current
        Log.d(TAG, "EmailOtpsdsdScreen:1133 " + emailLoginUserExist)
        Log.d(TAG, "EmailOtpsdsdScreen:1134 " + FBStatus)
        if (emailLoginUserExist == 0 || emailLoginUserExist == 1 && FBStatus == true && isFirstTime ==true) {
            isFirstTime=false
            openScreen?.invoke(Route.HOME_BASE, true)

        }


        SolidButton("Get a Code", poppinsMedium) {
            if (!isEmailError && emailStr != "") {
                Log.d(TAG, "EmailOtpScreen578: " + emailStr)
                emailSee = emailStr ?: ""
                showLoading?.invoke(true)
                viewModel?.getEmailOTp(emailStr ?: "", "") { isSuccess, errorMsg, otp ->
                    showLoading?.invoke(false)
                    if (isSuccess) {
                        // context.showToast(otp)
                        cuurantVerifationFlow = VerificationFlow.emailOtp
                        viewModel.updateUiState(
                            SignInScreenState.VerifyOtp, 331
                        )

                    } else {
                        context.showToast(errorMsg)
                    }

                }

                /* viewModel?.updateUiState(
                     SignInScreenState.VerifyOtp, 331
                 )*/
            } else showAllError.value = true
        }
        AddSpace(size = 5.dp)
        Box(
            modifier = Modifier.width(300.dp), contentAlignment = Alignment.CenterEnd
        ) {
            ClickableText(text = signInUsingPasswordText, onClick = {
                viewModel?.updateUiState(
                    SignInScreenState.EmailPwd, 340
                )
            })
        }
        AddSpace(size = 80.dp)
        Row(
            Modifier.width(300.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            AddSpace(size = 10.dp)

            Box(
                Modifier
                    .weight(1f)
                    .background(Harp)
                    .height(1.dp)
            )
            AddSpace(size = 10.dp)
            Text(
                text = "OR CONTINUE WITH",
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier,
                color = Nobel,
                fontFamily = poppinsMedium
            )
            AddSpace(size = 10.dp)
            Box(
                Modifier
                    .weight(1f)
                    .background(Harp)
                    .height(1.dp)
            )
        }
        AddSpace(size = 40.dp)

        Row(horizontalArrangement = Arrangement.Center) {
            AddSpace(size = 20.dp)
            OutlinedButton(
                modifier = Modifier
                    .width(120.dp)
                    .height(45.dp), onClick = {
                    //fb button click
                    googleLogin?.invoke()
                }, border = BorderStroke(1.dp, Dune), shape = RoundedCornerShape(10.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AddSpace(size = 5.dp)
                    Text(
                        text = "Google", fontFamily = poppinsMedium, fontSize = 13.sp, color = Dune,
                        modifier = Modifier.clickable {
                            //googleLogin?.invoke()

                        }
                    )
                }

            }
            AddSpace(size = 20.dp)

            OutlinedButton(
                modifier = Modifier
                    .width(120.dp)
                    .height(45.dp), onClick = {
                    //fb button click
                    faceBookLogin?.invoke()
                }, border = BorderStroke(1.dp, Dune), shape = RoundedCornerShape(10.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AddSpace(size = 5.dp)
                    Text(
                        text = "Facebook",
                        fontFamily = poppinsMedium,
                        fontSize = 13.sp,
                        color = Dune,
                        modifier = Modifier.clickable {
                            // faceBookLogin?.invoke()
                        }
                    )
                }

            }


        }



        AddSpace(size = 90.dp)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForgotPwd(
    viewModel: SignInViewModel? = hiltViewModel(),
    showLoading: ((Boolean) -> Unit)?,
    googleLogin: (() -> Unit)? = null,
    faceBookLogin: (() -> Unit)? = null,
) {
    val showAllError = remember { mutableStateOf(false) }
    var isEmailError = true
    var emailStr = ""
    Column {
        SignInEditText("Enter Email Address",
            CustomInputType.Email,
            showAllError = showAllError,
            onDataChanged = {}) { isValid, str ->
            isEmailError = isValid
            emailStr = str
        }
        AddSpace(30.dp)
        val context = LocalContext.current

        SolidButton("Get a Code", poppinsMedium) {
            if (!isEmailError && emailStr != "") {


                showLoading?.invoke(true)
                viewModel?.getEmailOTp(emailStr, "1") { isSuccess, errorMsg, otp ->
                    showLoading?.invoke(false)
                    if (isSuccess == true) {
                        //  context.showToast(otp)
                        cuurantVerifationFlow = VerificationFlow.emailOtp
                        viewModel.updateUiState(
                            SignInScreenState.ForgotPwdVerifyOtp, 331
                        )

                    } else {
                        context.showToast(errorMsg)
                    }

                }

                /* viewModel?.updateUiState(
                     SignInScreenState.VerifyOtp, 331
                 )*/
            } else showAllError.value = true
        }
        AddSpace(size = 5.dp)
        /* Box(
             modifier = Modifier.width(300.dp), contentAlignment = Alignment.CenterEnd
         ) {
             ClickableText(text = signInUsingPasswordText, onClick = {
                 viewModel?.updateUiState(
                     SignInScreenState.EmailPwd, 340
                 )
             })
         }*/


        AddSpace(size = 90.dp)
    }
}


enum class SignInScreenState {
    EmailOtp, EmailPwd, CreateAccount, VerifyOtp, ForgotPwd, ForgotPwdVerifyOtp, ResetPwd
}


