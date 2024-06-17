package com.sanxingrenge.benben.screens.loginRegisterModule

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sanxingrenge.benben.R
import com.sanxingrenge.benben.constant.annotatedColoredText
import com.sanxingrenge.benben.constant.annotatedSimpleText
import com.sanxingrenge.benben.screens.Route
import com.sanxingrenge.benben.ui.theme.Nobel
import com.sanxingrenge.benben.ui.theme.VioletBlue
import com.sanxingrenge.benben.ui.theme.White
import com.sanxingrenge.benben.ui.theme.poppinsMedium
import com.sanxingrenge.benben.ui.theme.poppinsRegular
import com.sanxingrenge.benben.uiComponents.AddImage
import com.sanxingrenge.benben.uiComponents.AddSpace
import com.sanxingrenge.benben.uiComponents.SolidButton
import com.sanxingrenge.benben.uiComponents.SolidButton2

@Preview
@Composable
fun OnBoardingScreen(openScreen: ((String) -> Unit)? = null
) {

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


            AddSpace(90.dp)
            AddImage(R.drawable.osomatelogo, 130.dp)
            AddSpace(size = 35.dp)
            CustomTextTop()
            AddSpace(size = 105.dp)
            OutlinedButton(
                modifier = Modifier.size(300.dp, 50.dp),
                onClick = {
                    openScreen?.invoke(Route.SIGN_IN_BASE + "/${SignInScreenState.CreateAccount.name}")
                    /*context.startActivity(
                        Intent(
                            context,
                            SignInActivity::class.java
                        ).putExtra(SignInActivity.IS_NEW_USER, true)
                    )*/
                },
                border = BorderStroke(1.dp, VioletBlue),
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    AddSpace(size = 5.dp)
                    Text(
                        text = "I'm a new User", fontFamily = poppinsMedium,
                        fontSize = 14.sp
                    )
                }

            }
            AddSpace(size = 15.dp)
            SolidButton2("Sign In") {
                openScreen?.invoke(Route.SIGN_IN_BASE + "/${SignInScreenState.EmailOtp.name}")
            }
            AddSpace(size = 60.dp)
            val termClicked = {
                    openScreen?.invoke("${Route.TERMS_PRIVACY_BASE}/terms")
            }

            val privacyClicked = {
                    openScreen?.invoke("${Route.TERMS_PRIVACY_BASE}/privacy")
            }
            CustomClickableText(termClicked, privacyClicked)
            AddSpace(size = 30.dp)
        }


    }
}

@Composable
private fun CustomClickableText(termClicked: () -> Unit?, privecyClicked: () -> Unit?) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        /* ClickableText(
             text = annotatedColoredTermsCondition,
             onClick = { offset ->
                 annotatedColoredTermsCondition.getStringAnnotations(
                     tag = "UserClicked",
                     start = offset,
                     end = offset
                 ).getOrNull(0)?.let { i ->
                     //do your stuff when it gets clicked
                     Log.d("TAG", "CustomClickableText: "+i.item)
                     itemClicked.invoke()
                 }
             },


             )*/
        Row {
            Text(
                text = "By signing up, you agree to the ",
                fontSize = 10.sp,
                fontFamily = poppinsRegular,
                color = Nobel,

                textAlign = TextAlign.Center
            )
            Text(
                text = "User Terms",
                fontSize = 10.sp,
                color = VioletBlue,
                fontFamily = poppinsMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.clickable {
                    termClicked.invoke()
                }
            )
            Text(
                text = " & ",
                fontSize = 10.sp,
                color = Nobel,
                fontFamily = poppinsRegular,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Privacy Policy",
                fontSize = 10.sp,
                color = VioletBlue,
                fontFamily = poppinsMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.clickable {
                    privecyClicked.invoke()

                }
            )
        }


    }
}

@Composable
private fun CustomTextTop() {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = annotatedSimpleText,
            fontSize = 23.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .offset(
                    x = 1.dp,
                    y = 1.dp
                )
                .alpha(0.40f)
        )
        Text(
            text = annotatedColoredText,
            fontSize = 23.sp,
            textAlign = TextAlign.Center
        )

    }
}
