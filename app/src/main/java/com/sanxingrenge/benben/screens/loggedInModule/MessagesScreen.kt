package com.sanxingrenge.benben.screens.loggedInModule


import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.Coil
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sanxingrenge.benben.constant.Constns.getTimeAgoFromDateOnly
import com.sanxingrenge.benben.constant.Constns.otherUserImage
import com.sanxingrenge.benben.constant.FormatDateUseCase.getTimeAgoFromDate
import com.sanxingrenge.benben.constant.FormatDateUseCase.getTimeAgoFromDateMSGOnly
import com.sanxingrenge.benben.responseModel.ConversionDataRsponse
import com.sanxingrenge.benben.screens.Route
import com.sanxingrenge.benben.screens.zodiacSignModule.userMsgDataList
import com.sanxingrenge.benben.ui.theme.Amaranth
import com.sanxingrenge.benben.ui.theme.PearlBush
import com.sanxingrenge.benben.ui.theme.poppinsBold
import com.sanxingrenge.benben.ui.theme.poppinsMedium
import com.sanxingrenge.benben.ui.theme.poppinsRegular
import com.sanxingrenge.benben.ui.theme.poppinsSemiBold
import com.sanxingrenge.benben.ui.theme.shimmerBaseColor
import com.sanxingrenge.benben.ui.theme.shimmerTopColor
import com.sanxingrenge.benben.uiComponents.AddSpace
import com.sanxingrenge.benben.uiComponents.ListAnimator
import com.sanxingrenge.benben.uiComponents.shimmerRotation
import com.sanxingrenge.benben.viewModel.GetMessageListViewModel
import com.valentinilk.shimmer.LocalShimmerTheme
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.delay

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MessagesScreen(openScreen: ((String) -> Unit)? = null) {

   // var userMsgDataList: List<ConversionDataRsponse> = listOf()


    val context = LocalContext.current
    val viewModel: GetMessageListViewModel = hiltViewModel()
    //jeckup

    val isLoading = remember {
        mutableStateOf(true)
    }



    val scope = rememberCoroutineScope()
   /* if(viewModel.seconds2=="20"){
        viewModel.stop2()
        viewModel.start2()
    }*/
   /* scope.launch(Dispatchers.IO) {
        try {
            viewModel.start2()
        }catch (e: Exception) {
            // Handle the exception.
        }

    }
*/
   // Log.d("TAG", "MessagesScreen132:-->" + viewModel.seconds2)


    LaunchedEffect(key1 = true, block = {
        viewModel.getUserListOfMessage(onListReceived = { list, isSuccssfull ->
            if(isSuccssfull==true){
                Log.d("TAG", "MessagesScreensdf:99 "+list)
                userMsgDataList = if (!list.isNullOrEmpty()) list
                else emptyList()

                isLoading.value = false
            }

        }, context)

    })



    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Text(
            text = "Messages",
            fontSize = 30.sp,
            fontFamily = poppinsBold,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 20.dp, end = 20.dp),
        )
        AddSpace(size = 10.dp)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(PearlBush)
        ) {

        }




        LazyColumn(content = {
            if (isLoading.value) {
                items(10) { pos ->
                    ListAnimator {
                        LoadingSkeleton()
                    }
                }
            } else {
                items(userMsgDataList.size) { pos ->
                    ListAnimator {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .clickable {
                                    val data =
                                        userMsgDataList[pos].user_photos?.getOrNull(0)?.image_url
                                    otherUserImage = data ?: ""
                                    openScreen?.invoke("${Route.CHAT_BASE}/${userMsgDataList[pos].id}/${userMsgDataList[pos].name}")
                                },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row(
                                Modifier.padding(horizontal = 30.dp, vertical = 13.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(Modifier.size(60.dp), contentAlignment = Alignment.BottomEnd) {
                                    Log.d("TAG", "MessagesScreensdf:167 "+ userMsgDataList[pos].user_photos?.getOrNull(0)?.image_url)
                                    AsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data( userMsgDataList[pos].user_photos?.getOrNull(0)?.image_url)
                                            .crossfade(true).build(),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(100.dp))
                                            .fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )

                                    /* if (userMsgDataList[pos].name) {
                                         Box(
                                             Modifier
                                                 .size(20.dp)
                                                 .padding(3.dp)
                                                 .background(Color.White, RoundedCornerShape(100.dp))
                                                 .padding(2.dp)
                                                 .background(LawnGreen, RoundedCornerShape(100.dp))
                                         ) {

                                         }
                                     }*/
                                }
                                AddSpace(size = 15.dp)
                                Column(
                                    Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {

                                    Row(
                                        Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Log.d("TAG", "MessagesScreensdf:204 "+ userMsgDataList[pos].name)

                                        Text(
                                            text = userMsgDataList[pos].name ?: "",
                                            color = Color.Black,
                                            fontFamily = poppinsBold,
                                            fontSize = 15.sp
                                        )
                                        Text(
                                            text =getTimeAgoFromDate(userMsgDataList[pos].last_message_date_time?:""),
                                            color = Color.Black,
                                            fontFamily = poppinsMedium,
                                            fontSize = 11.sp
                                        )
                                    }
                                    Row(
                                        Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            /* if (data.isMediaMsg) {
                                                 Image(
                                                     painter = painterResource(id = R.drawable.volume),
                                                     contentDescription = ""
                                                 )
                                                 AddSpace(size = 5.dp)
                                             }*/
                                            Text(
                                                text = userMsgDataList[pos].last_message ?: "",
                                                color = Color.Black,
                                                fontFamily = poppinsRegular,
                                                fontSize = 13.sp
                                            )
                                        }

                                        AnimatedVisibility(visible = userMsgDataList[pos].unseen_messages_count != "0") {

                                            Text(
                                                text = userMsgDataList[pos].unseen_messages_count
                                                    ?: "",
                                                color = Color.White,
                                                fontFamily = poppinsSemiBold,
                                                fontSize = 9.sp,
                                                modifier = Modifier
                                                    .background(
                                                        Amaranth,
                                                        RoundedCornerShape(100.dp)
                                                    )
                                                    .size(15.dp)
                                                    .padding(top = 1.dp),
                                                textAlign = TextAlign.Center
                                            )


                                        }
                                    }
                                }
                            }
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .padding(horizontal = 30.dp)
                                    .background(PearlBush)
                            )
                        }
                    }
                }
            }
        })
        LaunchedEffect(key1 = true, block = {
            delay(5000)
            isLoading.value = false
        })
    }

}

@Composable
fun LoadingSkeleton() {
    Column(
        Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(
                shimmerBaseColor,
                RoundedCornerShape(10.dp)
            )
            .shimmer(
                rememberShimmer(
                    shimmerBounds = ShimmerBounds.View,
                    theme = LocalShimmerTheme.current.copy(
                        rotation = shimmerRotation
                    )
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            Modifier.padding(horizontal = 10.dp, vertical = 9.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(Modifier.size(60.dp), contentAlignment = Alignment.BottomEnd) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(100.dp))
                        .fillMaxSize()
                        .background(shimmerTopColor),
                )
            }
            AddSpace(size = 14.dp)
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "             ",
                        fontSize = 15.sp,
                        modifier = Modifier
                            .padding(bottom = 1.dp)
                            .background(shimmerTopColor, RoundedCornerShape(5.dp))

                    )
                }
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "                                            ",
                        fontSize = 13.sp,
                        modifier = Modifier
                            .background(shimmerTopColor, RoundedCornerShape(5.dp))
                    )
                }
            }
        }
        Box(
            Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(horizontal = 30.dp)
        )
    }
}