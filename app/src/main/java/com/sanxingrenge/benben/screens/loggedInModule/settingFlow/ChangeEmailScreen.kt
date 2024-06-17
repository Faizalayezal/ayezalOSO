package com.sanxingrenge.benben.screens.loggedInModule.settingFlow

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.sanxingrenge.benben.R
import com.sanxingrenge.benben.constant.Constns.backemail
import com.sanxingrenge.benben.constant.dataStoreGetUserData
import com.sanxingrenge.benben.constant.showToast
import com.sanxingrenge.benben.ui.theme.Amaranth
import com.sanxingrenge.benben.ui.theme.Harp
import com.sanxingrenge.benben.ui.theme.Nobel
import com.sanxingrenge.benben.ui.theme.VioletBlue
import com.sanxingrenge.benben.ui.theme.poppinsMedium
import com.sanxingrenge.benben.ui.theme.poppinsRegular
import com.sanxingrenge.benben.ui.theme.poppinsSemiBold
import com.sanxingrenge.benben.uiComponents.AddSpace
import com.sanxingrenge.benben.uiComponents.CustomInputType
import com.sanxingrenge.benben.uiComponents.SignInEditText
import com.sanxingrenge.benben.uiComponents.SolidButton
import com.sanxingrenge.benben.viewModel.ChangePasswordViewModel

@Composable
fun ChangeEmailScreen(popBack: (() -> Unit)? = null, showLoading: (Boolean) -> Unit) {
    val context = LocalContext.current
    var oldEmail by remember {
        mutableStateOf("")
    }
    var newEmail by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = true, block = {
        context.dataStoreGetUserData().collect {
            oldEmail = it.email ?: ""
        }
    })

    val openDialog = remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState()), content = {

        val screenState = remember {
            mutableStateOf(ScreenType.EnterEmail)
        }

        Common.SettingTopBar("Change Email") {
            when (screenState.value) {
                ScreenType.EnterEmail -> {
                    popBack?.invoke()
                }

                ScreenType.EnterOtp -> {
                    screenState.value = ScreenType.EnterEmail
                }
            }
        }

        AnimatedVisibility(visible = screenState.value == ScreenType.EnterEmail) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                EnterEmailScreen(screenState, oldEmail, newEmail, showLoading) {
                    newEmail = it

                }
            }
        }
        AnimatedVisibility(visible = screenState.value == ScreenType.EnterOtp) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                EnterOtpScreen(openDialog, newEmail,popBack,showLoading)
            }
        }
    })
    if (openDialog.value) {
        Dialog(
            onDismissRequest = {
                openDialog.value = false
                popBack?.invoke()
            }
        ) {
            PopUpContent(newEmail)
        }
    }
}

enum class ScreenType {
    EnterEmail,
    EnterOtp,
}

@Composable
private fun PopUpContent(newEmail: String) {
    Column(
        modifier = Modifier
            .padding(horizontal = 0.dp)
            .background(
                Color.White,
                RoundedCornerShape(20.dp)
            )
            .fillMaxWidth()
            .padding(top = 40.dp, bottom = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Image(
            painter = painterResource(id = R.drawable.check), contentDescription = "",
            modifier = Modifier.size(80.dp)
        )
        AddSpace(30.dp)
        Text(
            text = "Successfully Changed",
            fontSize = 20.sp,
            fontFamily = poppinsSemiBold,
            color = Color.Black
        )
        AddSpace(0.dp)
        Text(
            text = newEmail,
            fontSize = 12.sp,
            fontFamily = poppinsRegular,
            color = Amaranth,
            textDecoration = TextDecoration.Underline
        )

    }
}

@Composable
private fun EnterOtpScreen(
    showPopup: MutableState<Boolean>,
    newEmail: String,
    popBack: (() -> Unit)?,
    showLoading: (Boolean) -> Unit,

    ) {
    val viewModel: ChangePasswordViewModel = hiltViewModel()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 130.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Verify new email address",
            fontSize = 12.sp,
            fontFamily = poppinsRegular,
            color = Nobel
        )
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = newEmail,
            fontSize = 17.sp,
            fontFamily = poppinsRegular,
            color = VioletBlue
        )

        val selectedOtp = remember {
            mutableStateOf("")
        }

        val isFocused = remember {
            mutableStateOf(false)
        }

        BasicTextField(
            modifier = Modifier
                .padding(top = 50.dp)
                .padding(horizontal = 40.dp)
                .fillMaxWidth()
                .background(
                    Harp, RoundedCornerShape(10.dp)
                )
                .border(
                    1.dp,
                    if (isFocused.value) VioletBlue
                    else Color.Transparent, RoundedCornerShape(10.dp)
                )
                .padding(horizontal = 10.dp, vertical = 0.dp)
                .onFocusChanged {
                    isFocused.value = it.hasFocus
                },
            value = selectedOtp.value,
            onValueChange = {
                if (it.length < 7) {
                    selectedOtp.value = it
                }
            },
            textStyle = TextStyle(
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontSize = 40.sp,
                letterSpacing = 5.sp,
            ),
            cursorBrush = SolidColor(Color.Transparent),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        )

        Box(
            modifier = Modifier
                .padding(top = 50.dp, bottom = 30.dp)
                .fillMaxWidth(), contentAlignment = Alignment.Center
        ) {
            SolidButton(
                "Continue", poppinsMedium, onclick = {
                    showLoading.invoke(true)

                    viewModel.changeEmail(
                        context,
                        newEmail,
                        selectedOtp.value

                    ) { isSuccess, msg ->
                        showLoading.invoke(false)

                        if (isSuccess) {
                            context.showToast(msg)
                            showPopup.value = true
                            backemail="1"
                            popBack?.invoke()


                        } else {
                            context.showToast(msg)
                        }

                    }

                }
            )


        }

    }
}

@Composable
private fun EnterEmailScreen(
    screenState: MutableState<ScreenType>,
    oldEmail: String,
    newEmail: String,
    showLoading: (Boolean) -> Unit,
    newEmailReturn: (String) -> Unit

) {

    val viewModel: ChangePasswordViewModel = hiltViewModel()
    val context = LocalContext.current



    Column(
        modifier = Modifier
            .padding(top = 25.dp)
            .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SignInEditText(
            oldEmail,
            CustomInputType.Email, false,
            onDataChanged = { str ->

            }
        )
    }
    Column(
        modifier = Modifier
            .padding(top = 25.dp)
            .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SignInEditText(
            "Enter new Email",
            CustomInputType.Email,
            onDataChanged = { str ->
                newEmailReturn.invoke(str)
            }
        )
    }
    Box(
        modifier = Modifier
            .padding(top = 60.dp, bottom = 30.dp)
            .fillMaxWidth(), contentAlignment = Alignment.Center
    ) {
        SolidButton(
            "Verify", poppinsMedium, onclick = {

                if (newEmail == "") {
                    context.showToast("Please Enter New Email")

                } else {
                    showLoading.invoke(true)
                    viewModel.changeEmailSandOtp(
                        context,
                        oldEmail,
                        newEmail,
                    ) { isSuccess, msg, otp ->
                        showLoading.invoke(false)
                        Log.d("TAG", "EnterEmailScreen21: "+isSuccess+"-->"+msg)

                        if(isSuccess==false){
                            context.showToast(msg)
                        }else{
                            screenState.value = ScreenType.EnterOtp
                            context.showToast(otp.toString())
                        }




                    }
                }

            }
        )
    }
}
