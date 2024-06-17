package com.sanxingrenge.benben.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sanxingrenge.benben.R
import com.sanxingrenge.benben.constant.dataStoreGetUserData
import com.sanxingrenge.benben.viewModel.SplashViewModel
import kotlinx.coroutines.flow.firstOrNull

private const val TAG = "SplashScreen"

@Preview
@Composable
fun SplashScreen(openScreen: ((String, Boolean) -> Unit)? = null) {
    //val viewModel = SplashViewModel()
    val viewModel: SplashViewModel = hiltViewModel()

    Box(
        modifier = Modifier.fillMaxSize(),
        Alignment.Center
    ) {

        //bg
        Image(
            painter = painterResource(id = R.drawable.mainbg),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillBounds,
        )
        //logo
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "",
            modifier = Modifier
                .size(180.dp),
            contentScale = ContentScale.Fit
        )
    }

    val context = LocalContext.current
    //original flow
    LaunchedEffect(true) {

        val userData = context.dataStoreGetUserData().firstOrNull()
        Log.d(TAG, "SplashScreen:595 "+userData)
        viewModel.getAllBasicLists {

            Log.d(TAG, "SplashScreen: testFlowAb>>$userData")

            if (userData?.userId != null && userData.name != null && userData.imageUrl != null) {
                openScreen?.invoke(Route.HOME_BASE, true)
            } else {
                viewModel.clearLocalData(context) {
                    openScreen?.invoke(Route.ON_BOARDING_BASE, true)
                }
            }


        }
    }

    //test flow
    /*lifecycleScope.launch {
        startActivity(Intent(this@SplashActivity, TestActivity::class.java))
        finish()
    }*/

}
