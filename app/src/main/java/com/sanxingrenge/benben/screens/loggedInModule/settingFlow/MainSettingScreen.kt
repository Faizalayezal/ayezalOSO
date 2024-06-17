package com.sanxingrenge.benben.screens.loggedInModule.settingFlow

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.facebook.login.LoginManager
import com.sanxingrenge.benben.R
import com.sanxingrenge.benben.constant.Constns
import com.sanxingrenge.benben.constant.Constns.FaceBookId
import com.sanxingrenge.benben.constant.Constns.GoogleId
import com.sanxingrenge.benben.constant.Constns.backemail
import com.sanxingrenge.benben.constant.dataStoreGetUserData
import com.sanxingrenge.benben.constant.showToast
import com.sanxingrenge.benben.screens.Route
import com.sanxingrenge.benben.screens.loggedInModule.settingFlow.Common.SettingTopBar
import com.sanxingrenge.benben.ui.theme.Amaranth
import com.sanxingrenge.benben.ui.theme.Mercury
import com.sanxingrenge.benben.ui.theme.OsomateTheme
import com.sanxingrenge.benben.ui.theme.SilverChalice
import com.sanxingrenge.benben.ui.theme.White
import com.sanxingrenge.benben.ui.theme.WhiteSmoke
import com.sanxingrenge.benben.ui.theme.poppinsMedium
import com.sanxingrenge.benben.ui.theme.poppinsRegular
import com.sanxingrenge.benben.ui.theme.poppinsSemiBold
import com.sanxingrenge.benben.uiComponents.AddSpace
import com.sanxingrenge.benben.viewModel.MainSettingScreenViewModel
import kotlinx.coroutines.flow.firstOrNull

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun MainSettingScreen(openScreen: ((String) -> Unit)? = null,logOut: (() -> Unit)? = null, popBack: (() -> Unit)? = null) {
    val context = LocalContext.current
    val viewModel: MainSettingScreenViewModel = hiltViewModel()
    if (backemail == "1") {
        popBack?.invoke()
        backemail = ""
    } else {

    }
   // viewModel.collectUserData(context)
   // val userLiveData = viewModel.podcastsLive.collectAsState().value

    Log.d("TAG", "MainSettingScreen: "+GoogleId+"-->"+FaceBookId)
    val openDeleteDialog = remember {
        mutableStateOf(false)
    }
    val openLogoutDialog = remember {
        mutableStateOf(false)
    }

    OsomateTheme {

        LazyColumn(modifier = Modifier.fillMaxSize(), content = {

            /* item {
                OptionDesign("Change password",if(userLiveData.google==null && userLiveData.google=="" ||  userLiveData.facebook==null && userLiveData.facebook=="") ShapeType.TopRounded else ShapeType.TopBottom) {
                    openScreen?.invoke(Route.CHANGE_PASSWORD_BASE)
                }
            }*/
            item {
                SettingTopBar("Settings") {
                    popBack?.invoke()
                }
            }
           if(GoogleId==null || GoogleId==""){
               if(FaceBookId==null || FaceBookId=="") {
                   item { TitleText("User setting") }

                   item {
                       OptionDesign("Change password", ShapeType.TopRounded) {
                           openScreen?.invoke(Route.CHANGE_PASSWORD_BASE)
                       }
                   }
                   item {
                       OptionDesign("Change email", ShapeType.BottomRounded) {
                           openScreen?.invoke(Route.CHANGE_EMAIL_BASE)
                       }
                   }
               }
           }else{

            }



            item { TitleText("About osomate") }
            item {
                OptionDesign("Terms and Agreements", ShapeType.TopRounded) {
                    openScreen?.invoke("${Route.TERMS_PRIVACY_BASE}/terms")
                    //.putExtra("type", "terms")
                }
            }
            item {
                OptionDesign("Privacy Policy") {
                    openScreen?.invoke("${Route.TERMS_PRIVACY_BASE}/privacy")
                    //.putExtra("type", "privacy")
                }
            }
            //  item { OptionDesign("About us") }
            item {
                OptionDesign("Check for updates", ShapeType.BottomRounded, onItemClicked = {
                    context.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=com.sanxingrenge.benben&hl=en-IN")
                        )
                    )
                })
            }

            item { TitleText("Support") }
            item {
                OptionDesign("Contact Us", ShapeType.TopRounded) {
                    openScreen?.invoke(Route.FEEDBACK_BASE)

                }
            }
            item {
                OptionDesign("Guidelines", ShapeType.BottomRounded) {
                    openScreen?.invoke("${Route.TERMS_PRIVACY_BASE}/guidelines")

                }
            }
            // item { OptionDesign("Contact us", ShapeType.BottomRounded) }

            item { AddSpace(20.dp) }

            item {
                OptionDesign("Delete Account", ShapeType.TopRounded) {
                    openDeleteDialog.value = true
                }
            }
            item {
                OptionDesign("Logout", ShapeType.BottomRounded, Amaranth) {
                    openLogoutDialog.value = true
                }
            }

            item { AddSpace(20.dp) }
        })
    }

    if (openDeleteDialog.value) {
        Dialog(
            onDismissRequest = {
                openDeleteDialog.value = false
            }
        ) {
            DeleteAccountPopUpContent(openScreen,logOut) {
                openDeleteDialog.value = false

            }
        }
    }
    if (openLogoutDialog.value) {
        Dialog(
            onDismissRequest = {
                openLogoutDialog.value = false
            }
        ) {
            LogoutPopUpContent(openScreen,logOut) {
                openLogoutDialog.value = false
            }
        }
    }

}


@Composable
private fun LogoutPopUpContent(
    openScreen: ((String) -> Unit)? = null,
    logOut: (() -> Unit)? = null,
    dismissDialog: ((Boolean) -> Unit)?,

    ) {
    val viewModel: MainSettingScreenViewModel = hiltViewModel()
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(horizontal = 0.dp)
            .background(
                Color.White,
                RoundedCornerShape(20.dp)
            )
            .fillMaxWidth()
            .padding(top = 0.dp, bottom = 20.dp)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center,
    ) {


        AddSpace(20.dp)
        Text(
            text = "Leaving so soon?",
            fontSize = 20.sp,
            fontFamily = poppinsSemiBold,
            color = Color.Black
        )
        AddSpace(10.dp)
        Text(
            lineHeight = 25.sp,
            text = "Are you sure, you want to Logout?",
            fontSize = 12.sp,
            fontFamily = poppinsRegular,
            color = Color.Black
        )
        AddSpace(25.dp)

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            OutlinedButton(
                onClick = { dismissDialog?.invoke(false) },
                border = BorderStroke(1.dp, Color.Black),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f, true),
                contentPadding = PaddingValues(vertical = 9.dp),
            ) {
                Text(
                    text = "Cancel",
                    fontFamily = poppinsMedium,
                    fontSize = 12.sp,
                    color = Color.Black
                )
            }

            AddSpace(size = 20.dp)

            Button(
                onClick = {

                    viewModel.logout(
                        context,
                    ) { isSuccess, msg ->

                        if (isSuccess) {
                            context.showToast(msg)
                            viewModel.updateFcmToken(
                                context,
                            ) { isSuccess, msg ->
                                if (isSuccess) {
                                    viewModel.clearUserData(context) {
                                        logOut?.invoke()
                                        openScreen?.invoke(Route.ON_BOARDING_BASE)
                                    }
                                }
                            }


                        } else {
                            context.showToast(msg)
                        }

                    }

                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Amaranth
                ),
                modifier = Modifier.weight(1f, true),
                contentPadding = PaddingValues(vertical = 9.dp)
            ) {
                Text(
                    text = "Yes, Logout",
                    fontFamily = poppinsMedium,
                    fontSize = 12.sp,
                    color = White
                )
            }
        }

    }
}

@Composable
private fun DeleteAccountPopUpContent(
    openScreen: ((String) -> Unit)?,
    logOut: (() -> Unit)? = null,
    dismissDialog: ((Boolean) -> Unit)?
) {
    val context = LocalContext.current
    val viewModel: MainSettingScreenViewModel = hiltViewModel()

    Column(
        modifier = Modifier
            .padding(horizontal = 0.dp)
            .background(
                Color.White,
                RoundedCornerShape(20.dp)
            )
            .fillMaxWidth()
            .padding(top = 0.dp, bottom = 20.dp)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center,
    ) {


        AddSpace(20.dp)
        Text(
            text = "Delete Account ?",
            fontSize = 20.sp,
            fontFamily = poppinsSemiBold,
            color = Color.Black
        )
        AddSpace(10.dp)
        Text(
            lineHeight = 25.sp,
            text = "You’ll permanently lose your Profile, " +
                    "messages and account information and " +
                    "can not be recovered.",
            fontSize = 12.sp,
            fontFamily = poppinsRegular,
            color = Color.Black
        )
        AddSpace(20.dp)

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            OutlinedButton(
                onClick = { dismissDialog?.invoke(false) },
                border = BorderStroke(1.dp, Color.Black),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f,true),
                contentPadding = PaddingValues(vertical = 9.dp)
            ) {
                Text(
                    text = "Cancel",
                    fontFamily = poppinsMedium,
                    fontSize = 12.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.width(15.dp))

            Button(
                onClick = {
                    viewModel.deleteUser(
                        context,

                        ) { isSuccess, msg ->

                        if (isSuccess) {
                            context.showToast(msg)
                            viewModel.clearUserData(context) {
                                logOut?.invoke()
                                openScreen?.invoke(Route.ON_BOARDING_BASE)
                            }

                        } else {
                            context.showToast(msg)
                        }

                    }

                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Amaranth
                ),
                modifier = Modifier.weight(1f,true),
                contentPadding = PaddingValues(vertical = 9.dp)
            ) {
                Text(
                    text = "Delete Account",
                    fontFamily = poppinsMedium,
                    fontSize = 12.sp,
                    color = White
                )
            }
        }

    }
}

enum class ShapeType {
    TopRounded,
    BottomRounded,
    NoneRounded,
    TopBottom,
}

@Composable
private fun OptionDesign(
    text: String, shapeType: ShapeType = ShapeType.NoneRounded, textColor: Color = Color.Black,
    onItemClicked: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .clickable {
                onItemClicked?.invoke()
            }, contentAlignment = Alignment.BottomCenter
    ) {
        val radius = 12.dp
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    WhiteSmoke,
                    when (shapeType) {
                        ShapeType.TopRounded -> RoundedCornerShape(
                            topStart = radius,
                            topEnd = radius
                        )

                        ShapeType.BottomRounded -> RoundedCornerShape(
                            bottomEnd = radius,
                            bottomStart = radius
                        )

                        ShapeType.NoneRounded -> RoundedCornerShape(0.dp)
                        ShapeType.TopBottom -> RoundedCornerShape(
                            topStart = radius,
                            topEnd = radius,
                            bottomEnd = radius,
                            bottomStart = radius
                        )
                    }
                )
                .padding(horizontal = 15.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text, fontSize = 13.sp, fontFamily = poppinsRegular, color = textColor,
                modifier = Modifier.weight(1f, true)
            )
            Image(
                painter = painterResource(id = R.drawable.back_arrow), contentDescription = "",
                modifier = Modifier
                    .size(25.dp)
                    .rotate(180f)
            )
        }

        if (shapeType != ShapeType.BottomRounded) {
            Spacer(
                modifier = Modifier
                    .padding(start = 15.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Mercury)
            )
        }
    }
}

@Composable
private fun TitleText(title: String) {
    Text(
        text = title, fontFamily = poppinsMedium, fontSize = 11.sp, color = SilverChalice,
        modifier = Modifier
            .padding(top = 25.dp, bottom = 5.dp)
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    )
}