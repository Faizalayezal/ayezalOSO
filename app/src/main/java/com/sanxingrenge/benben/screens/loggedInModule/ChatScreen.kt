package com.sanxingrenge.benben.screens.loggedInModule

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.plcoding.audiorecorder.playback.AndroidAudioPlayer
import com.plcoding.audiorecorder.record.AndroidAudioRecorder
import com.sanxingrenge.benben.R
import com.sanxingrenge.benben.constant.ChatMessageObject
import com.sanxingrenge.benben.constant.Constns.audioFile
import com.sanxingrenge.benben.constant.Constns.chatImageData
import com.sanxingrenge.benben.constant.Constns.chatToProfile
import com.sanxingrenge.benben.constant.Constns.currantIdOther
import com.sanxingrenge.benben.constant.Constns.getTimeAgoFromDateOnly
import com.sanxingrenge.benben.constant.Constns.listMessage
import com.sanxingrenge.benben.constant.Constns.onlineListener
import com.sanxingrenge.benben.constant.Constns.otherUserImage
import com.sanxingrenge.benben.constant.Constns.otherUserImageSingleCAll
import com.sanxingrenge.benben.constant.Constns.otherUserNameSingleCAll
import com.sanxingrenge.benben.constant.Constns.playerAudio
import com.sanxingrenge.benben.constant.Constns.reciveuid
import com.sanxingrenge.benben.constant.Constns.userIdOther
import com.sanxingrenge.benben.constant.FileData
import com.sanxingrenge.benben.constant.MultiFilePicker
import com.sanxingrenge.benben.constant.dataStoreGetUserData
import com.sanxingrenge.benben.constant.playAudio
import com.sanxingrenge.benben.constant.showToast
import com.sanxingrenge.benben.screens.Route
import com.sanxingrenge.benben.screens.zodiacSignModule.imageDatas
import com.sanxingrenge.benben.ui.theme.AquaHaze
import com.sanxingrenge.benben.ui.theme.Black
import com.sanxingrenge.benben.ui.theme.BlackEel
import com.sanxingrenge.benben.ui.theme.GainsBoro
import com.sanxingrenge.benben.ui.theme.LawnGreen
import com.sanxingrenge.benben.ui.theme.Merino
import com.sanxingrenge.benben.ui.theme.OsloGrey
import com.sanxingrenge.benben.ui.theme.Platinum
import com.sanxingrenge.benben.ui.theme.RipePlum
import com.sanxingrenge.benben.ui.theme.SmokeyGrey
import com.sanxingrenge.benben.ui.theme.TextBack
import com.sanxingrenge.benben.ui.theme.TitanWhite
import com.sanxingrenge.benben.ui.theme.VioletBlue
import com.sanxingrenge.benben.ui.theme.White
import com.sanxingrenge.benben.ui.theme.poppinsBold
import com.sanxingrenge.benben.ui.theme.poppinsMedium
import com.sanxingrenge.benben.ui.theme.poppinsRegular
import com.sanxingrenge.benben.uiComponents.AddSpace
import com.sanxingrenge.benben.viewModel.GetMessageListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import java.io.File

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint(
    "SuspiciousIndentation", "CoroutineCreationDuringComposition",
    "UnrememberedMutableState"
)
@Composable
fun ChatScreen(
    openScreen: ((String) -> Unit)? = null,
    openScreenvideocall: (() -> Unit)? = null,
    popBack: (() -> Unit)? = null,
    userId: String,
    userName: String,
    multiFilePicker: MultiFilePicker,
    recorder: AndroidAudioRecorder,
    player: AndroidAudioPlayer,
) {


    val message = remember { mutableStateOf("") }
    val context = LocalContext.current
    val viewModel: GetMessageListViewModel = hiltViewModel()
    val scope = rememberCoroutineScope()
    /* val listMessage = remember {
         mutableStateListOf<ChatMessageObject>()
     }*/

    LaunchedEffect(true, block = {
        val userData = context.dataStoreGetUserData().firstOrNull()
        viewModel.start(userData?.userId ?: 0, userId.toIntOrNull() ?: 0)

        // viewModel.idGet(userData?.userId ?: 0, userId.toIntOrNull() ?: 0)
        userIdOther = userId
        otherUserImageSingleCAll = otherUserImage
        otherUserNameSingleCAll = userName
        currantIdOther = userData?.userId.toString()
        viewModel.getLastSeenDetails(userIdOther, onlineListener)
        viewModel.getMessageList()
    })


    val itemList = (1..listMessage.size).toList()
    val scrollState = rememberLazyListState()


    val state by viewModel.msgConversion.observeAsState()
    // val state by viewModel._msgConversion.collectAsState()
    Log.d("TAG", "ChatScreen1321:164 " + state)

    LaunchedEffect(true, block = {
        state?.let { it ->
            Log.d("TAG", "ChatScreen1321: " + it)
            // listMessage.clear()
            //  listMessage.addAll(it)
            listMessage = it
        }
    })

    //  val scrollState = LazyListState()
    //faster visible chat
    LaunchedEffect(key1 = itemList) {
        Log.d("TAG", "ChatScreen213: " + itemList.size)
        scrollState.scrollToItem(itemList.size)
        // scrollState.scrollToItem(itemList.lastIndex)
    }
    val compostion by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.whitewve))
    // val corrtmpostionblack by rememberLottieComposition(LottieCompositionSpec.RawRes(R.drawable.aninasd))
    val compostionblack by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.blackwve))


    scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == scrollState.layoutInfo.totalItemsCount - 1


    // scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == scrollState.layoutInfo.viewportEndOffset
    //  scrollState.layoutInfo.reverseLayout

    var isVisibility by remember {
        mutableStateOf(false)
    }
    val openRecoderDialog = remember {
        mutableStateOf(false)
    }


    Column {

        Row(
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(10.dp)
        ) {

            IconButton(onClick = {
                chatToProfile = ""
                state?.clear()
                popBack?.invoke()
            }, Modifier.size(50.dp)) {
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp),
                    painter = painterResource(id = R.drawable.back_arrow),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds
                )
            }

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(otherUserImage)
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(100.dp))
                    .clickable {
                        chatToProfile = "1"
                        state?.clear()
                        openScreen?.invoke(Route.PROFILE_DETAIL_BASE + "/${userId}")
                    },
                contentScale = ContentScale.FillBounds
            )
            AddSpace(12.dp)
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = userName,
                    fontSize = 18.sp,
                    fontFamily = poppinsBold,
                    color = Color.Black
                )
                Row(verticalAlignment = CenterVertically) {
                    Box(
                        modifier = Modifier
                            .background(LawnGreen, RoundedCornerShape(10.dp))
                            .size(7.dp)
                    )
                    AddSpace(size = 7.dp)
                    Text(
                        text = "Online",
                        fontSize = 12.sp,
                        fontFamily = poppinsRegular,
                        color = BlackEel
                    )
                }
            }
            AddSpace(size = 60.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        reciveuid = userIdOther
                        openScreenvideocall?.invoke()
                        // state?.clear()
                    }, modifier = Modifier
                        .size(20.dp)
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .size(20.dp),
                        painter = painterResource(id = R.drawable.videosend),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(RipePlum),
                        contentScale = ContentScale.Fit
                    )
                }
            }


        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(GainsBoro)
        ) {

        }


        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            LazyColumn(
                state = scrollState,
                content = {
                    items(listMessage) { data ->
                        Log.d(
                            "TAG",
                            "ChatScreen2920: " + data.message + "-->" + data.type + "-->" + data.type.intType
                        )
                        if (data.type.intType == 2) {
                            //StringSenderType
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 90.dp),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Column(
                                    modifier = Modifier.padding(
                                        end = 30.dp,
                                        top = 10.dp,
                                        bottom = 10.dp
                                    ), horizontalAlignment = End
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .background(
                                                VioletBlue,
                                                RoundedCornerShape(
                                                    topEnd = 17.dp,
                                                    topStart = 17.dp,
                                                    bottomStart = 17.dp
                                                )
                                            )
                                            .padding(15.dp),
                                        verticalAlignment = CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = data.message,
                                            color = White,
                                            fontSize = 12.sp,
                                            fontFamily = poppinsRegular
                                        )
                                    }
                                    ShowDateDesign(data.time)
                                }
                            }

                        } else if (data.type.intType == 5) {
                            //AudioTypeSender
                            LaunchedEffect(key1 = true, block = {
                                val filePath = data.message
                                MediaPlayer.create(context, filePath.toUri()).apply {
                                    playerAudio = this

                                }
                            })
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Column(
                                    modifier = Modifier.padding(
                                        end = 30.dp,
                                        top = 10.dp,
                                        bottom = 10.dp
                                    ), horizontalAlignment = End
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .width(180.dp)
                                            .background(
                                                VioletBlue,
                                                RoundedCornerShape(
                                                    topEnd = 17.dp,
                                                    topStart = 17.dp,
                                                    bottomStart = 17.dp
                                                )
                                            )
                                            .padding(15.dp),
                                        verticalAlignment = CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        AudioSend(data.message, scope, compostion)
                                    }
                                    ShowDateDesign(data.time)
                                }

                            }
                        } else if (data.type.intType == 0) {
                            //datatype
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .padding(15.dp),
                                        verticalAlignment = CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = getTimeAgoFromDateOnly(data.date),
                                            color = OsloGrey,
                                            fontSize = 12.sp,
                                            fontFamily = poppinsRegular
                                        )
                                    }
                                }
                            }

                        } else if (data.type.intType == 1) {
                            //StringReceiverType
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = 90.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(
                                            start = 25.dp,
                                            top = 10.dp,
                                            bottom = 10.dp
                                        ),
                                    verticalAlignment = Alignment.Bottom
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .width(35.dp)
                                            .fillMaxHeight(),
                                        contentAlignment = BottomStart
                                    ) {

                                        AsyncImage(
                                            model = ImageRequest.Builder(LocalContext.current)
                                                .data(otherUserImage)
                                                .crossfade(true).build(),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .size(35.dp)
                                                .clip(
                                                    RoundedCornerShape(100.dp)
                                                ),
                                            contentScale = ContentScale.Crop
                                        )

                                    }
                                    AddSpace(size = 15.dp)
                                    Column {
                                        Text(
                                            modifier = Modifier
                                                .background(
                                                    TitanWhite,
                                                    RoundedCornerShape(
                                                        topEnd = 17.dp,
                                                        topStart = 17.dp,
                                                        bottomEnd = 17.dp
                                                    )
                                                )
                                                .padding(15.dp),
                                            text = data.message,
                                            color = Color.Black,
                                            fontSize = 12.sp,
                                            fontFamily = poppinsRegular
                                        )
                                        ShowDateDesign(data.time)
                                    }
                                }
                            }
                        } else if (data.type.intType == 9) {
                            //AudioTypeReceiver

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = 90.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {

                                Row(
                                    modifier = Modifier
                                        .padding(
                                            start = 25.dp,
                                            top = 10.dp,
                                            bottom = 10.dp
                                        ),
                                    verticalAlignment = Alignment.Bottom
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .padding(bottom = 20.dp)
                                            .width(35.dp)
                                            .fillMaxHeight(),
                                        contentAlignment = BottomStart
                                    ) {
                                        AsyncImage(
                                            model = ImageRequest.Builder(LocalContext.current)
                                                .data(otherUserImage)
                                                .crossfade(true).build(),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .size(35.dp)
                                                .clip(
                                                    RoundedCornerShape(100.dp)
                                                ),
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                    AddSpace(size = 15.dp)

                                    Column(
                                        modifier = Modifier.padding(
                                            end = 30.dp,
                                            top = 10.dp,
                                            bottom = 10.dp
                                        ), horizontalAlignment = Alignment.Start
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .width(180.dp)
                                                .background(
                                                    TitanWhite,
                                                    RoundedCornerShape(
                                                        topEnd = 17.dp,
                                                        topStart = 17.dp,
                                                        bottomEnd = 17.dp
                                                    )

                                                )
                                                .padding(15.dp),
                                            verticalAlignment = CenterVertically,
                                            horizontalArrangement = Arrangement.Center
                                        ) {

                                            AudioRecive(data.message, scope, compostionblack)

                                            /*Image(
                                                painter = if (isVisibilityAudio) painterResource(id = R.drawable.play) else painterResource(
                                                    id = R.drawable.attach
                                                ),
                                                contentDescription = "",
                                                modifier = Modifier
                                                    .size(18.dp)
                                                    .clickable {
                                                        isVisibilityAudio = !isVisibilityAudio
                                                        playerAudio?.start()

                                                    },
                                            )
                                            AddSpace(size = 10.dp)
                                            Image(
                                                painter = painterResource(id = R.drawable.playing),
                                                contentDescription = "",
                                                modifier = Modifier
                                                    .fillMaxWidth()

                                            )*/


                                        }
                                        ShowDateDesign(data.time)
                                    }
                                }

                            }

                        } else if (data.type.intType == 6) {
                            //SingleImageTypeSender

                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Column(
                                    modifier = Modifier.padding(
                                        end = 30.dp,
                                        top = 10.dp,
                                        bottom = 10.dp
                                    ), horizontalAlignment = End
                                ) {
                                    Row(
                                        modifier = Modifier
                                            /* .background(
                                                 VioletBlue,
                                                 RoundedCornerShape(
                                                     topEnd = 17.dp,
                                                     topStart = 17.dp,
                                                     bottomStart = 17.dp
                                                 )
                                             )*/
                                            .padding(1.dp),
                                        verticalAlignment = CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        AsyncImage(
                                            model = ImageRequest.Builder(LocalContext.current)
                                                .data(data.message)
                                                .crossfade(true).build(),
                                            contentDescription = "",
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .size(85.dp)
                                                .clickable {
                                                    //  chatImageData = data.message
                                                    // openScreen?.invoke(Route.SINGLE_MEDIA_ROUTE)
                                                }
                                                .border(
                                                    1.dp,
                                                    Platinum,
                                                    RoundedCornerShape(15.dp)
                                                )
                                                .padding(1.dp)
                                                .clip(
                                                    RoundedCornerShape(15.dp)
                                                )
                                        )

                                    }
                                    ShowDateDesign(data.time)
                                }

                            }


                        } else if (data.type.intType == 10) {
                            //SingleImageTypeReceiver
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = 90.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(
                                            start = 25.dp,
                                            top = 10.dp,
                                            bottom = 10.dp
                                        ),
                                    verticalAlignment = Alignment.Bottom
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .width(35.dp)
                                            .fillMaxHeight(),
                                        contentAlignment = BottomStart
                                    ) {
                                        AsyncImage(
                                            model = ImageRequest.Builder(LocalContext.current)
                                                .data(otherUserImage)
                                                .crossfade(true).build(),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .size(35.dp)
                                                .clip(
                                                    RoundedCornerShape(100.dp)
                                                ),
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                    AddSpace(size = 15.dp)
                                    Column {
                                        Row {
                                            AsyncImage(
                                                model = ImageRequest.Builder(LocalContext.current)
                                                    .data(data.message)
                                                    .crossfade(true).build(),
                                                contentDescription = "",
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier
                                                    .size(85.dp)
                                                    .clickable {
                                                        chatImageData = data.message
                                                        state?.clear()
                                                        openScreen?.invoke(Route.SINGLE_MEDIA_ROUTE)
                                                    }
                                                    .border(
                                                        1.dp,
                                                        Platinum,
                                                        RoundedCornerShape(
                                                            topStart = 15.dp,
                                                            topEnd = 15.dp,
                                                            bottomEnd = 15.dp
                                                        )
                                                    )
                                                    .padding(1.dp)
                                                    .clip(
                                                        RoundedCornerShape(
                                                            topStart = 15.dp,
                                                            topEnd = 15.dp,
                                                            bottomEnd = 15.dp
                                                        )
                                                    )
                                            )

                                        }
                                        ShowDateDesign(data.time)
                                    }
                                }
                            }


                        } else if (data.type.intType == 11) {
                            //image Grid recive

                            Log.d("TAG", "ChatScreenrecive: " + data.message)

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(
                                            start = 25.dp,
                                            top = 10.dp,
                                            bottom = 10.dp
                                        ),
                                    verticalAlignment = Alignment.Bottom
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .width(35.dp)
                                            .fillMaxHeight(),
                                        contentAlignment = BottomStart
                                    ) {
                                        AsyncImage(
                                            model = ImageRequest.Builder(LocalContext.current)
                                                .data(otherUserImage)
                                                .crossfade(true).build(),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .size(35.dp)
                                                .clip(
                                                    RoundedCornerShape(100.dp)
                                                ),
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                    AddSpace(size = 15.dp)
                                    Column {
                                        Row {
                                            ImageGridRecive(data.filesArray, openScreen, state)
                                        }
                                        ShowDateDesign(data.time)
                                    }
                                }
                            }


                        } else if (data.type.intType == 7) {
                            //image Grid sender

                            data.filesArray?.forEachIndexed { index, fileData ->

                                Log.d("TAG", "ChatScreen: " + fileData)
                                Log.d(
                                    "TAG",
                                    "ChatScreen321231: " + data.filesArray?.get(index)?.fileString
                                )

                            }

                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Column(
                                    modifier = Modifier.padding(
                                        end = 30.dp,
                                        top = 10.dp,
                                        bottom = 10.dp
                                    ), horizontalAlignment = End
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .padding(start = 160.dp),
                                        verticalAlignment = CenterVertically,
                                        horizontalArrangement = Arrangement.End
                                    ) {

                                        ImageGrid(data.filesArray, openScreen)

                                    }

                                    Log.d("TAG", "ChatScreen1321: " + data.filesArray?.size)



                                    ShowDateDesign(data.time)
                                }

                            }

                        }
                        LaunchedEffect(Unit) {
                            delay(1000)
                        }
                    }


                    /*
                                  chatMsgList.forEach {
                                      when (val dataItem = it) {
                                          is ChatItemObj.AudioSent -> {
                                              item {
                                                  Box(
                                                      modifier = Modifier.fillMaxWidth(),
                                                      contentAlignment = Alignment.CenterEnd
                                                  ) {
                                                      Column(
                                                          modifier = Modifier.padding(
                                                              end = 30.dp,
                                                              top = 10.dp,
                                                              bottom = 10.dp
                                                          ), horizontalAlignment = End
                                                      ) {
                                                          Row(
                                                              modifier = Modifier
                                                                  .width(180.dp)
                                                                  .background(
                                                                      VioletBlue,
                                                                      RoundedCornerShape(
                                                                          topEnd = 17.dp,
                                                                          topStart = 17.dp,
                                                                          bottomStart = 17.dp
                                                                      )
                                                                  )
                                                                  .padding(15.dp),
                                                              verticalAlignment = CenterVertically,
                                                              horizontalArrangement = Arrangement.Center
                                                          ) {
                                                              Image(
                                                                  painter = painterResource(id = R.drawable.play),
                                                                  contentDescription = "",
                                                                  modifier = Modifier.size(18.dp)
                                                              )
                                                              AddSpace(size = 10.dp)
                                                              Image(
                                                                  painter = painterResource(id = R.drawable.playing),
                                                                  contentDescription = "",
                                                                  modifier = Modifier.fillMaxWidth()
                                                              )

                                                          }
                                                          ShowDateDesign(dataItem.date)
                                                      }
                                                  }
                                              }
                                          }


                                          is ChatItemObj.Date -> {

                                              item {
                                                  Text(
                                                      text = dataItem.dateStr,
                                                      fontSize = 12.sp,
                                                      fontFamily = poppinsSemiBold,
                                                      color = SilverChalice,
                                                      modifier = Modifier
                                                          .fillMaxWidth()
                                                          .padding(10.dp),
                                                      textAlign = TextAlign.Center
                                                  )
                                              }
                                          }

                                          is ChatItemObj.ImagesReceived -> {
                                              item {
                                                  Box(
                                                      modifier = Modifier
                                                          .fillMaxWidth()
                                                          .padding(end = 90.dp),
                                                      contentAlignment = Alignment.CenterStart
                                                  ) {
                                                      Row(
                                                          modifier = Modifier
                                                              .padding(
                                                                  start = 25.dp,
                                                                  top = 10.dp,
                                                                  bottom = 10.dp
                                                              ),
                                                          verticalAlignment = Alignment.Bottom
                                                      ) {
                                                          Box(
                                                              modifier = Modifier
                                                                  .width(35.dp)
                                                                  .fillMaxHeight(),
                                                              contentAlignment = BottomStart
                                                          ) {
                                                              Image(
                                                                  painter = painterResource(id = dataItem.userImage),
                                                                  contentDescription = "",
                                                                  modifier = Modifier
                                                                      .size(35.dp)
                                                                      .clip(
                                                                          RoundedCornerShape(100.dp)
                                                                      ),
                                                                  contentScale = ContentScale.Crop
                                                              )
                                                          }
                                                          AddSpace(size = 15.dp)
                                                          Column {
                                                              Row {
                                                                  Image(
                                                                      painter = painterResource(id = R.drawable.userimg1),
                                                                      contentDescription = "",
                                                                      contentScale = ContentScale.Crop,
                                                                      modifier = Modifier
                                                                          .size(85.dp)
                                                                          .border(
                                                                              1.dp,
                                                                              Platinum,
                                                                              RoundedCornerShape(
                                                                                  topStart = 15.dp,
                                                                                  topEnd = 15.dp,
                                                                                  bottomEnd = 15.dp
                                                                              )
                                                                          )
                                                                          .padding(1.dp)
                                                                          .clip(
                                                                              RoundedCornerShape(
                                                                                  topStart = 15.dp,
                                                                                  topEnd = 15.dp,
                                                                                  bottomEnd = 15.dp
                                                                              )
                                                                          )
                                                                  )
                                                                  AddSpace(size = 5.dp)
                                                                  Image(
                                                                      painter = painterResource(id = R.drawable.userimg1),
                                                                      contentDescription = "",
                                                                      contentScale = ContentScale.Crop,
                                                                      modifier = Modifier
                                                                          .size(85.dp)
                                                                          .border(
                                                                              1.dp,
                                                                              Platinum,
                                                                              RoundedCornerShape(15.dp)
                                                                          )
                                                                          .padding(1.dp)
                                                                          .clip(
                                                                              RoundedCornerShape(15.dp)
                                                                          )
                                                                  )
                                                              }
                                                              ShowDateDesign(dataItem.date)
                                                          }
                                                      }
                                                  }
                                              }
                                          }


                                          is ChatItemObj.ImagesSent -> {

                                          }


                                          is ChatItemObj.MsgReceived -> {
                                              item {
                                                  Box(
                                                      modifier = Modifier
                                                          .fillMaxWidth()
                                                          .padding(end = 90.dp),
                                                      contentAlignment = Alignment.CenterStart
                                                  ) {
                                                      Row(
                                                          modifier = Modifier
                                                              .padding(
                                                                  start = 25.dp,
                                                                  top = 10.dp,
                                                                  bottom = 10.dp
                                                              ),
                                                          verticalAlignment = Alignment.Bottom
                                                      ) {
                                                          Box(
                                                              modifier = Modifier
                                                                  .width(35.dp)
                                                                  .fillMaxHeight(),
                                                              contentAlignment = BottomStart
                                                          ) {
                                                              Image(
                                                                  painter = painterResource(id = dataItem.image),
                                                                  contentDescription = "",
                                                                  modifier = Modifier
                                                                      .size(35.dp)
                                                                      .clip(
                                                                          RoundedCornerShape(100.dp)
                                                                      ),
                                                                  contentScale = ContentScale.Crop
                                                              )
                                                          }
                                                          AddSpace(size = 15.dp)
                                                          Column {
                                                              Text(
                                                                  modifier = Modifier
                                                                      .background(
                                                                          TitanWhite,
                                                                          RoundedCornerShape(
                                                                              topEnd = 17.dp,
                                                                              topStart = 17.dp,
                                                                              bottomEnd = 17.dp
                                                                          )
                                                                      )
                                                                      .padding(15.dp),
                                                                  text = dataItem.msg,
                                                                  color = Color.Black,
                                                                  fontSize = 12.sp,
                                                                  fontFamily = poppinsRegular
                                                              )
                                                              ShowDateDesign(dataItem.date)
                                                          }
                                                      }
                                                  }
                                              }
                                          }


                                          is ChatItemObj.MsgSent -> {
                                              item {
                                                  Box(
                                                      modifier = Modifier
                                                          .fillMaxWidth()
                                                          .padding(start = 90.dp),
                                                      contentAlignment = Alignment.CenterEnd
                                                  ) {
                                                      Column(
                                                          modifier = Modifier.padding(
                                                              end = 30.dp,
                                                              top = 10.dp,
                                                              bottom = 10.dp
                                                          ), horizontalAlignment = End
                                                      ) {
                                                          Row(
                                                              modifier = Modifier
                                                                  .background(
                                                                      VioletBlue,
                                                                      RoundedCornerShape(
                                                                          topEnd = 17.dp,
                                                                          topStart = 17.dp,
                                                                          bottomStart = 17.dp
                                                                      )
                                                                  )
                                                                  .padding(15.dp),
                                                              verticalAlignment = CenterVertically,
                                                              horizontalArrangement = Arrangement.Center
                                                          ) {
                                                              Text(
                                                                  text = dataItem.msg,
                                                                  color = White,
                                                                  fontSize = 12.sp,
                                                                  fontFamily = poppinsRegular
                                                              )
                                                          }
                                                          ShowDateDesign(dataItem.date)
                                                      }
                                                  }
                                              }
                                          }
                                      }
                                  }
              */

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, true)
            )

            AnimatedVisibility(visible = isVisibility, Modifier.background(Color.Transparent)) {

                Row(
                    Modifier
                        .padding(start = 170.dp)
                        .size(70.dp)
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.attachopen),
                        contentDescription = "",
                        modifier = Modifier
                            .size(70.dp)
                            .padding(3.dp)
                            .background(Color.Transparent)
                            .clickable {
                                // openImagePicker?.invoke()

                                multiFilePicker.startImagePicker()
                            }
                    )


                }


            }

            if (openRecoderDialog.value) {
                Dialog(
                    onDismissRequest = {
                        openRecoderDialog.value = false
                    }
                ) {
                    AudioRecordPopUpContent(viewModel, recorder, player) {
                        openRecoderDialog.value = false
                    }
                }
            }



            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalAlignment = CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Row(
                    Modifier
                        .weight(1f, true)
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                        .border(1.dp, Platinum, RoundedCornerShape(100.dp))
                        .background(AquaHaze, RoundedCornerShape(100.dp))
                        .padding(11.dp),
                    verticalAlignment = CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    /* Image(
                         painter = painterResource(id = R.drawable.happy),
                         contentDescription = "",
                         modifier = Modifier.size(22.dp)
                     )*/
                    AddSpace(size = 10.dp)
                    Box(Modifier.weight(1f, true)) {
                        Text(
                            text = if (message.value == "") "Type here" else "",
                            fontFamily = poppinsRegular,
                            fontSize = 14.sp,
                            color = OsloGrey,
                            modifier = Modifier.fillMaxWidth()
                        )
                        BasicTextField(
                            value = message.value, onValueChange = { message.value = it },
                            textStyle = TextStyle.Default.copy(
                                fontFamily = poppinsRegular,
                                fontSize = 14.sp,
                                color = Color.Black
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                    }
                    Image(
                        painter = if (isVisibility) painterResource(id = R.drawable.attach2) else painterResource(
                            id = R.drawable.attach
                        ),
                        contentDescription = "",
                        modifier = Modifier
                            .size(22.dp)
                            .padding(3.dp)
                            .clickable {
                                isVisibility = !isVisibility

                            }
                    )
                    AddSpace(size = 5.dp)

                    Image(
                        painter = painterResource(id = R.drawable.mic),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(SmokeyGrey),
                        modifier = Modifier
                            .size(35.dp)
                            .padding(3.dp)
                            .clickable {
                                //  isVisibility = !isVisibility
                                isVisibility = false

                                multiFilePicker.askruntimepermission() {
                                    Log.d("TAG", "ChatScrsfdfgsdffeen: " + it)
                                    if (it == true) {
                                        openRecoderDialog.value = true
                                    } else {

                                    }

                                }
                                /*Log.d("TAG", "ChatScreen:1321 " + counter1 + "-->" + counter3)
                                if (counter1 != 1) {
                                    context.showToast("Please Enable Permission App Setting")

                                }else{

                                }*/


                            }
                    )


                }

                IconButton(
                    enabled = message.value.isNotBlank(),
                    onClick = {
                        viewModel.sendMessage(message.value.trim())
                        isVisibility = false
                        message.value = ""

                    }, modifier = Modifier
                        .background(
                            VioletBlue,
                            RoundedCornerShape(100.dp)
                        )
                        .size(45.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.sendmsg),
                        contentDescription = "",
                        modifier = Modifier
                            .size(35.dp)
                    )
                }

            }
        }
    }


}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AudioRecive(data: String, scope: CoroutineScope, compostion: LottieComposition?) {
    var isPlaying by remember { mutableStateOf(false) }

    val progress by animateLottieCompositionAsState(
        composition = compostion,
        iterations = LottieConstants.IterateForever
    )
    val progress2 by animateLottieCompositionAsState(
        composition = compostion,
        isPlaying = false
    )

    /* DisposableEffect(data) {
         onDispose {
             playerAudio?.release()
         }
     }
     LaunchedEffect(key1 = true, block = {
         MediaPlayer.create(context, data.toUri()).apply {
             playerAudio = this

         }
     })*/

    Image(
        painter = if (isPlaying) painterResource(id = R.drawable.pause) else painterResource(
            id = R.drawable.play
        ),
        contentDescription = "",
        colorFilter = ColorFilter.tint(Black),
        modifier = Modifier
            .size(18.dp)
            .clickable {
                if (isPlaying) {
                    playerAudio?.pause()
                } else {
                    playAudio(data)
                }
                isPlaying = !isPlaying

            },
    )
    AddSpace(size = 10.dp)
    LottieAnimation(
        composition = compostion,
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp),
        contentScale = ContentScale.Crop,
        progress = if (isPlaying) progress else progress2
    )


    DisposableEffect(playerAudio) {
        val completionListener = MediaPlayer.OnCompletionListener {
            isPlaying = false
        }
        playerAudio?.setOnCompletionListener(completionListener)
        onDispose {
            playerAudio?.setOnCompletionListener(null)
        }
    }


}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AudioSend(data: String, scope: CoroutineScope, compostion: LottieComposition?) {
    val context = LocalContext.current
    var isPlaying by remember { mutableStateOf(false) }
    //  var waveformProgress by remember { mutableStateOf(0F) }

    val progress by animateLottieCompositionAsState(
        composition = compostion,
        iterations = LottieConstants.IterateForever
    )
    val progress2 by animateLottieCompositionAsState(
        composition = compostion,
        isPlaying = false
    )

    /* DisposableEffect(data) {
         onDispose {
             playerAudio?.release()
         }
     }*/

    /* LaunchedEffect(key1 = true, block = {
         MediaPlayer.create(context, data.toUri()).apply {
             playerAudio = this

         }
     })*/

    Image(
        painter = if (isPlaying) painterResource(id = R.drawable.pause) else painterResource(
            id = R.drawable.play
        ),
        contentDescription = "",
        modifier = Modifier
            .size(18.dp)
            .clickable {
                //   scope.launch {
                if (isPlaying) {
                    playerAudio?.pause()
                } else {
                    Log.d("TAG", "AudioSend: " + data)
                    playAudio(data)
                }
                isPlaying = !isPlaying

                //  }
            },
    )
    AddSpace(size = 10.dp)
    /* Image(
         painter = painterResource(id = R.drawable.playing),
         contentDescription = "",
         modifier = Modifier
             .fillMaxWidth()

     )*/

    /* AudioWaveform(
         modifier = Modifier
             .fillMaxWidth()
             .height(10.dp),
         amplitudes = amplitudes,
         progress = waveformProgress,
         onProgressChange = { waveformProgress = it }
     )*/


    LottieAnimation(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp),
        composition = compostion,
        contentScale = ContentScale.Crop,
        progress = if (isPlaying) progress else progress2
    )

    DisposableEffect(playerAudio) {
        val completionListener = MediaPlayer.OnCompletionListener {
            isPlaying = false
        }
        playerAudio?.setOnCompletionListener(completionListener)
        onDispose {
            playerAudio?.setOnCompletionListener(null)
        }
    }

}

@Composable
fun ImageGridRecive(
    filesArray: ArrayList<FileData>?,
    openScreen: ((String) -> Unit)?,
    state: java.util.ArrayList<ChatMessageObject>?
) {
    var tottlefile = remember {
        mutableStateOf(0)
    }

    val data = filesArray

    tottlefile.value = filesArray?.size?.minus(4) ?: 0

    Column(modifier = Modifier.fillMaxSize()) {
        val chunkedImages = filesArray?.take(4)?.chunked(2)

        chunkedImages?.forEachIndexed { index, rowImages ->
            Row(
                Modifier
                    .fillMaxWidth()

            ) {

                rowImages.forEachIndexed { index2, image ->
                    Column(
                        Modifier
                            .clickable {
                                imageDatas = data ?: listOf()
                                state?.clear()
                                openScreen?.invoke(Route.MEDIA_BASE)
                            }
                    ) {
                        Box {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(image.fileString)
                                    .crossfade(true).build(),
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(85.dp)
                                    .border(
                                        1.dp,
                                        Platinum,
                                        RoundedCornerShape(15.dp)
                                    )
                                    .padding(1.dp)
                                    .clip(
                                        RoundedCornerShape(15.dp)
                                    )
                            )

                            if (filesArray.size > 4 && index == 1 && index2 == 1) {

                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .size(85.dp)
                                        .border(
                                            1.dp,
                                            Platinum,
                                            RoundedCornerShape(15.dp)
                                        )
                                        .align(alignment = Alignment.Center)
                                        .background(
                                            TextBack,
                                            RoundedCornerShape(15.dp)
                                        )
                                        .padding(1.dp)
                                        .clip(
                                            RoundedCornerShape(15.dp)
                                        ),

                                    )
                                {
                                    Text(
                                        text = "+${tottlefile.value}",
                                        fontSize = 18.sp,
                                        fontFamily = poppinsBold,
                                        color = Color.Black,
                                        textAlign = TextAlign.Center,

                                        )
                                }
                            }


                        }


                    }


                }


            }
        }
    }


}

@Composable
fun ImageGrid(filesArray: List<FileData>?, openScreen: ((String) -> Unit)?) {

    var tottlefile = remember {
        mutableStateOf(0)
    }

    val data = filesArray

    tottlefile.value = filesArray?.size?.minus(4) ?: 0

    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        val chunkedImages = filesArray?.take(4)?.chunked(2)

        chunkedImages?.forEachIndexed { index, rowImages ->
            Row(
                Modifier
                    .fillMaxWidth()
                // .padding(start = 50.dp)

            ) {

                rowImages.forEachIndexed { index2, image ->
                    Column(
                        Modifier
                            .clickable {
                                imageDatas = data ?: listOf()
                                // openScreen?.invoke(Route.MEDIA_BASE, true)
                            }
                    ) {
                        Box {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(image.fileString)
                                    .crossfade(true).build(),
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(85.dp)
                                    .border(
                                        1.dp,
                                        Platinum,
                                        RoundedCornerShape(15.dp)
                                    )
                                    .padding(1.dp)
                                    .clip(
                                        RoundedCornerShape(15.dp)
                                    )
                            )

                            if (filesArray.size > 4 && index == 1 && index2 == 1) {

                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .size(85.dp)
                                        .border(
                                            1.dp,
                                            Platinum,
                                            RoundedCornerShape(15.dp)
                                        )
                                        .align(alignment = Alignment.Center)
                                        .background(
                                            TextBack,
                                            RoundedCornerShape(15.dp)
                                        )
                                        .padding(1.dp)
                                        .clip(
                                            RoundedCornerShape(15.dp)
                                        ),

                                    )
                                {
                                    Text(
                                        text = "+${tottlefile.value}",
                                        fontSize = 18.sp,
                                        fontFamily = poppinsBold,
                                        color = Color.Black,
                                        textAlign = TextAlign.Center,

                                        )
                                }
                            }


                        }


                    }


                }


            }
        }
    }


}


@Composable
fun ShowDateDesign(dateStr: String) {
    Text(
        text = dateStr, color = OsloGrey,
        fontFamily = poppinsRegular,
        fontSize = 11.sp
    )
}

private val chatMsgList = listOf(
    ChatItemObj.Date(
        "15 April"
    ),
    ChatItemObj.AudioSent(
        "jbndkjc",
        "11:30 am"
    ),
    ChatItemObj.MsgReceived(
        "I’ve resently find a 3D Artist. Whould you like to see his work?",
        R.drawable.userimg1,
        "11:30 am"
    ),
    ChatItemObj.Date(
        "Today"
    ),
    ChatItemObj.MsgSent(
        "Oh absolutly, i’d love to!!",
        "11:30 am"
    ),
    ChatItemObj.ImagesReceived(
        listOf(R.drawable.userimg1, R.drawable.userimg2),
        R.drawable.userimg3,
        "11:30 am"
    ),
    ChatItemObj.MsgSent(
        "\uD83D\uDC9C",
        "11:30 am"
    ),
)

sealed class ChatItemObj {
    class Date(val dateStr: String) : ChatItemObj()

    class MsgReceived(val msg: String, val image: Int, val date: String) : ChatItemObj()
    class ImagesReceived(val images: List<Int>, val userImage: Int, val date: String) :
        ChatItemObj()

    class MsgSent(val msg: String, val date: String) : ChatItemObj()
    class AudioSent(val urlStr: String, val date: String) : ChatItemObj()
    class ImagesSent(val images: List<Int>, val date: String) : ChatItemObj()
}


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun AudioRecordPopUpContent(
    viewmodel: GetMessageListViewModel,
    recoder: AndroidAudioRecorder,
    player: AndroidAudioPlayer,
    dismissDialog: ((Boolean) -> Unit)?,
) {
    val compostion by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.vave))
    var isPlaying by remember { mutableStateOf(true) }
    val progress by animateLottieCompositionAsState(
        composition = compostion,
        isPlaying = isPlaying
    )
    val context = LocalContext.current

    LaunchedEffect(key1 = progress) {
        if (progress == 0f) {
            isPlaying = false

        }
        if (progress == 1f) {
            isPlaying = true
        }
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 40.dp)
            .background(
                color = VioletBlue,
                RoundedCornerShape(20.dp)
            )
            .fillMaxWidth()
            .padding(top = 0.dp, bottom = 20.dp)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (viewmodel.seconds == "59") {
            context.showToast("audio limit is 1 minute only")
            viewmodel.pause()
            recoder.stop()
        }

        Row {
            val numberTransitionSpec: AnimatedContentScope<String>.() -> ContentTransform = {
                slideInVertically(
                    initialOffsetY = { it }
                ) + fadeIn() with slideOutVertically(
                    targetOffsetY = { -it }
                ) + fadeOut() using SizeTransform(
                    false
                )
            }

            CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.h3) {
                AnimatedContent(
                    targetState = viewmodel.minutes,
                    transitionSpec = numberTransitionSpec
                ) {
                    Text(text = it, fontSize = 15.sp, color = Color.White)
                }
                Text(text = ":", fontSize = 15.sp, color = Color.White)
                AnimatedContent(
                    targetState = viewmodel.seconds,
                    transitionSpec = numberTransitionSpec
                ) {
                    Text(text = it, fontSize = 15.sp, color = Color.White)
                }
            }
        }

        AddSpace(10.dp)
        Button(modifier = Modifier.size(140.dp, 40.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Merino
            ), onClick = {
                if (viewmodel.seconds == "59") {
                    // context.showToast("audio limit is 1 minute only")
                    Log.d("TAG", "AudiasdoRecordPopUpContent213: " + "audio limit is 1 minute only")

                } else {
                    viewmodel.start()
                    File(context.cacheDir, "audio.mp4").also {
                        recoder.start(it)
                        Log.d("TAG", "AudiasdoRecordPopUpContent: " + it)
                        audioFile = it
                    }
                }

            }) {
            Text(
                text = "Record Audio",
                fontSize = 10.sp,
                fontFamily = poppinsMedium,
                color = Color.Black
            )

        }

        AddSpace(10.dp)

        Button(modifier = Modifier.size(140.dp, 40.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Merino
            ), onClick = {
                viewmodel.pause()
                recoder.stop()
            }) {
            Text(
                text = "Stop recoding",
                fontSize = 10.sp,
                fontFamily = poppinsMedium,
                color = Color.Black
            )
        }
        AddSpace(10.dp)
        Button(modifier = Modifier.size(140.dp, 40.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Merino
            ), onClick = {
                viewmodel.sendAudio(
                    audio = audioFile?.toUri() ?: Uri.EMPTY,
                    GetMessageListViewModel.SendFileType.Audio,
                    context
                )
                viewmodel.stop()
                //viewmodel.seconds = "00"
                //viewmodel.minutes = "00"
                dismissDialog?.invoke(true)
            }) {
            Text(
                text = "Send recording",
                fontSize = 10.sp,
                fontFamily = poppinsMedium,
                color = Color.Black
            )
        }
        AddSpace(10.dp)
        Button(modifier = Modifier.size(140.dp, 40.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Merino
            ), onClick = {
                Log.d("TAG", "AudioRecordPopUpContent564: " + audioFile)
                isPlaying = true
                player.playFile(audioFile ?: return@Button)
            }) {
            Text(
                text = "Play",
                fontSize = 10.sp,
                fontFamily = poppinsMedium,
                color = Color.Black
            )
            LottieAnimation(
                modifier = Modifier.fillMaxWidth(),
                composition = compostion,
                progress = { progress }
            )

        }

    }
}























