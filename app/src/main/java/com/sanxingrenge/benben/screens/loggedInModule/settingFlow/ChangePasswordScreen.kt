package com.sanxingrenge.benben.screens.loggedInModule.settingFlow

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sanxingrenge.benben.constant.PWD_NOT_MATCH
import com.sanxingrenge.benben.constant.showToast
import com.sanxingrenge.benben.ui.theme.poppinsMedium
import com.sanxingrenge.benben.uiComponents.CustomInputType
import com.sanxingrenge.benben.uiComponents.SignInEditText
import com.sanxingrenge.benben.uiComponents.SolidButton
import com.sanxingrenge.benben.viewModel.ChangePasswordViewModel

@Composable
fun ChangePasswordScreen(popBack: (() -> Unit)? = null, showLoading: (Boolean) -> Unit) {
    val viewModel: ChangePasswordViewModel = hiltViewModel()
    val context = LocalContext.current

    val showAllError = remember {
        mutableStateOf(false)
    }
    var currantPassword = ""
    var newPassword = ""
    var confirmPassword = ""
    var isPwdValid = false
    var newPwdValid = false
    var isConfirmPwdValid = false

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        content = {
            Common.SettingTopBar("Change Password") {
                popBack?.invoke()
            }
            Column(
                modifier = Modifier
                    .padding(top = 50.dp)
                    .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                /* SignInEditText(
                     "Current password",
                     CustomInputType.Pwd, onDataChanged = { str ->
                         currantPassword = str

                     }
                 )*/

                SignInEditText(
                    "Current password",
                    CustomInputType.Cpwd,
                    showAllError = showAllError,
                    onDataChanged = {}) { isError, str ->
                    isPwdValid = !isError
                    currantPassword = str
                }

            }
            Column(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                /* SignInEditText(
                     "New password",
                     CustomInputType.Pwd, onDataChanged = { str ->

                         newPassword = str
                     }
                 )*/
                SignInEditText(
                    "New password",
                    CustomInputType.Npwd,
                    showAllError = showAllError,
                    onDataChanged = {}) { isError, str ->
                    newPwdValid = !isError
                    newPassword = str
                }
            }
            Column(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                /*  SignInEditText(
                      "Confirm new password",
                      CustomInputType.Pwd, onDataChanged = { str ->
                          confirmPassword = str
                      }
                  )*/
                SignInEditText(
                    "Confirm new password",
                    CustomInputType.PwdC,
                    showAllError = showAllError,
                    onDataChanged = {}) { isError, str ->
                    isConfirmPwdValid = !isError
                    confirmPassword = str
                }
            }
            Box(
                modifier = Modifier
                    .padding(top = 50.dp, bottom = 30.dp)
                    .fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
                SolidButton(
                    "Change", poppinsMedium, onclick = {
                        /* showLoading.invoke(true)
                         if (currantPassword == "") {

                             context.showToast("Please Enter Currant Password")
                         } else if (newPassword == "") {
                             context.showToast("Please Enter New Password")

                         } else if (confirmPassword == "") {
                             context.showToast("Please Enter Confirm Password")

                         } else {
                             viewModel.changePassword(
                                 context,
                                 currantPassword,
                                 newPassword,
                                 confirmPassword
                             ) { isSuccess, msg ->
                                 showLoading.invoke(false)
                                 if (isSuccess == true) {
                                     popBack?.invoke()
                                 } else {
                                     context.showToast(msg)
                                 }

                             }


                         }*/
                        if (newPwdValid != isConfirmPwdValid) {
                            context.showToast(PWD_NOT_MATCH)
                        } else if (isPwdValid && isConfirmPwdValid && newPwdValid && currantPassword != "" && newPassword != "" && confirmPassword != "") {
                            showLoading.invoke(true)
                            //   openScreen.invoke("${Route.REGISTER_BASE}/$email/$pwd", false)
                            viewModel.changePassword(
                                context,
                                currantPassword,
                                newPassword,
                                confirmPassword
                            ) { isSuccess, msg ->
                                showLoading.invoke(false)
                                if (isSuccess == true) {
                                    popBack?.invoke()
                                } else {
                                    context.showToast(msg)
                                }

                            }
                        } else {
                            showAllError.value = true
                        }
                    }

                )
            }
        })
}
