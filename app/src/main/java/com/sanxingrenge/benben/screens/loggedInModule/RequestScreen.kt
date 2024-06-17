package com.sanxingrenge.benben.screens.loggedInModule.settingFlow


import android.util.Log
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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sanxingrenge.benben.constant.dataStoreGetUserData
import com.sanxingrenge.benben.constant.showToast
import com.sanxingrenge.benben.screens.Route
import com.sanxingrenge.benben.screens.zodiacSignModule.FriendMainList
import com.sanxingrenge.benben.ui.theme.BlackRock
import com.sanxingrenge.benben.ui.theme.PearlBush
import com.sanxingrenge.benben.ui.theme.Seashell
import com.sanxingrenge.benben.ui.theme.SilverChalice
import com.sanxingrenge.benben.ui.theme.VioletBlue
import com.sanxingrenge.benben.ui.theme.poppinsBold
import com.sanxingrenge.benben.ui.theme.poppinsMedium
import com.sanxingrenge.benben.ui.theme.poppinsRegular
import com.sanxingrenge.benben.ui.theme.shimmerBaseColor
import com.sanxingrenge.benben.ui.theme.shimmerTopColor
import com.sanxingrenge.benben.uiComponents.AddSpace
import com.sanxingrenge.benben.uiComponents.ListAnimator
import com.sanxingrenge.benben.uiComponents.shimmerRotation
import com.sanxingrenge.benben.viewModel.GetFriendListViewModel
import com.valentinilk.shimmer.LocalShimmerTheme
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.delay
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import kotlin.math.abs

@Composable
fun RequestScreen(openScreen: ((String) -> Unit)? = null, showLoading: (Boolean) -> Unit,popBack: (() -> Unit)? = null) {

    val context = LocalContext.current
    val viewModel: GetFriendListViewModel = hiltViewModel()

    val isLoading = remember {
        mutableStateOf(true)
    }
    val uId = remember {
        mutableStateOf(0)
    }


    LaunchedEffect(key1 = true, block = {


        viewModel.getFriendRequestList(onListReceived = { list, isSuccssfull ->
            Log.d("TAG", "RequestScreen83: " + list)

            FriendMainList = if (!list.isNullOrEmpty()) list
            else emptyList()
            isLoading.value = false


        }, context)


        context.dataStoreGetUserData().collect {

            uId.value = it.userId ?: 0

        }


    })



    Log.d("TAG", "RequestScreen32132: " + FriendMainList)

    /*
        LaunchedEffect(Unit) {
            while(true) {

                viewModel.getFriendRequestList(onListReceived = { list, isSuccssfull ->
                    Log.d("TAG", "RequestScreen83: " + list)

                    FriendMainList = if (!list.isNullOrEmpty()) list
                    else emptyList()
                    isLoading.value = false


                }, context)


                context.dataStoreGetUserData().collect {

                    uId.value = it.userId ?: 0

                }
                delay(5000)
            }
        }
    */



    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Text(
            text = "Friend Requests",
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
                items(FriendMainList.size) { pos ->
                    val data = FriendMainList[pos]

                    ListAnimator {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .clickable {
                                    //openScreen?.invoke(Route.CHAT_BASE)
                                },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row(
                                Modifier.padding(horizontal = 30.dp, vertical = 13.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(Modifier.size(55.dp), contentAlignment = Alignment.BottomEnd) {
                                    AsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(if (uId.value == data.sender_user?.id) FriendMainList[pos].receiver_user?.user_photos?.getOrNull(0)?.image_url else FriendMainList[pos].sender_user?.user_photos?.getOrNull(0)?.image_url)
                                            .crossfade(true).build(),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(100.dp))
                                            .fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )

                                }
                                AddSpace(size = 15.dp)


                                //currant date to last date count days
                                val date = Calendar.getInstance().time
                                val dateFormat: DateFormat = SimpleDateFormat("yyyy-mm-dd")
                                val strDate: String = dateFormat.format(date)
                                val endDateValue = FriendMainList[pos].created_at
                                val date1: Date = dateFormat.parse(strDate) as Date
                                val date2: Date = dateFormat.parse(endDateValue.toString()) as Date
                                val difference = abs(date1.time - date2.time)
                                val differenceDates = difference / (24 * 60 * 60 * 1000)
                                val dayDifference = differenceDates.toString()




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
                                            text = if (uId.value == data.sender_user?.id) FriendMainList[pos].receiver_user?.name
                                                ?: "" else FriendMainList[pos].sender_user?.name
                                                ?: "",
                                            color = Color.Black,
                                            fontFamily = poppinsBold,
                                            fontSize = 20.sp
                                        )

                                        Text(
                                            text = if (dayDifference == "0") {
                                                "Today"
                                            } else {
                                                "$dayDifference day ago"
                                            },
                                            color = Color.Black,
                                            fontFamily = poppinsMedium,
                                            fontSize = 13.sp
                                        )


                                    }

                                    Row(
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(end = 60.dp, bottom = 10.dp),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {

                                        if (uId.value == data.sender_user?.id) {
                                            Text(
                                                text = "Request Pending",
                                                fontSize = 13.sp,
                                                maxLines = 1,
                                                fontFamily = poppinsRegular,
                                                color = Color.Black,
                                                modifier = Modifier.padding(end = 0.dp).fillMaxWidth()
                                            )
                                        } else {
                                            Button(
                                                onClick = {
                                                    showLoading.invoke(true)
                                                    viewModel.approveFrinendRequest(
                                                        data.id?:0,
                                                        "reject",
                                                        context
                                                    ) {it,msg->
                                                        context.showToast(msg)
                                                        showLoading.invoke(false)
                                                        if (it) {
                                                           openScreen?.invoke(Route.HOME_ROUTE)
                                                        }

                                                    }


                                                },
                                                colors = ButtonDefaults.buttonColors(
                                                    backgroundColor = SilverChalice
                                                ),
                                                shape = RoundedCornerShape(30),
                                                modifier = Modifier
                                                    .size(75.dp, 30.dp)
                                            ) {
                                                Row(
                                                    horizontalArrangement = Arrangement.Center,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {

                                                    Text(
                                                        text = "Delete",
                                                        fontSize = 10.sp,
                                                        fontFamily = poppinsRegular,
                                                        color = BlackRock
                                                    )
                                                }

                                            }

                                            AddSpace(size = 20.dp)

                                            Button(
                                                onClick = {
                                                    Log.d("TAG", "RequestScreen123: "+data.sender_id)
                                                    showLoading.invoke(true)
                                                    viewModel.approveFrinendRequest(
                                                        data.id?:0,
                                                        "accept",
                                                        context
                                                    ) {it,msg->
                                                        showLoading.invoke(false)
                                                        context.showToast(msg)
                                                        if (it) {
                                                            openScreen?.invoke(Route.HOME_ROUTE)

                                                        }
                                                    }


                                                },
                                                colors = ButtonDefaults.buttonColors(
                                                    backgroundColor = VioletBlue
                                                ),
                                                shape = RoundedCornerShape(30),
                                                modifier = Modifier
                                                    .size(75.dp, 30.dp)
                                            ) {
                                                Row(
                                                    horizontalArrangement = Arrangement.Center,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {

                                                    Text(
                                                        text = "Accept",
                                                        fontSize = 10.sp,
                                                        fontFamily = poppinsRegular,
                                                        color = Seashell
                                                    )
                                                }

                                            }
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