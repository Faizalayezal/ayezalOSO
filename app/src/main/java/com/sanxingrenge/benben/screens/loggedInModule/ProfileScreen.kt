package com.sanxingrenge.benben.screens.loggedInModule

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sanxingrenge.benben.R
import com.sanxingrenge.benben.constant.Constns.ImageShowEdit
import com.sanxingrenge.benben.screens.Route
import com.sanxingrenge.benben.ui.theme.BondiBlue
import com.sanxingrenge.benben.ui.theme.DawnPink
import com.sanxingrenge.benben.ui.theme.Merino
import com.sanxingrenge.benben.ui.theme.Porcelain
import com.sanxingrenge.benben.ui.theme.SilverChalice
import com.sanxingrenge.benben.ui.theme.VioletBlue
import com.sanxingrenge.benben.ui.theme.White
import com.sanxingrenge.benben.ui.theme.poppinsBold
import com.sanxingrenge.benben.ui.theme.poppinsRegular
import com.sanxingrenge.benben.ui.theme.themtransperent
import com.sanxingrenge.benben.uiComponents.AddSpace
import com.sanxingrenge.benben.viewModel.ProfileScreenViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen(openScreen: ((String) -> Unit)? = null,showLoading: (Boolean) -> Unit,) {

    val viewModel: ProfileScreenViewModel = hiltViewModel()
    val context = LocalContext.current
    viewModel.collectUserData(context)
    val userLiveData = viewModel.podcastsLive.collectAsState().value
    val sign = remember { mutableStateOf("") }
    val zoditit = remember { mutableStateOf("") }
    val zodiimg = remember { mutableStateOf("") }
    val anitit = remember { mutableStateOf("") }
    val aniimg = remember { mutableStateOf("") }
    ImageShowEdit=userLiveData.imageUrl?:""
    viewModel.getUserProfile(userLiveData.userId ?: 0, context) { status, signn,zodictit,zodicimg,animaltit,animalimg ->
        showLoading.invoke(true)

        if (status == true) {
            showLoading.invoke(false)

            sign.value = signn
            zoditit.value = zodictit
            zodiimg.value = zodicimg
            anitit.value = animaltit
            aniimg.value = animalimg
            Log.d("TAG", "ProfileScreen64:80 " + zodictit)
            Log.d("TAG", "ProfileScreen64:81 " + zodicimg)
        }
    }

    Log.d("TAG", "ProfidfsdfleScresdfsdfen: " + userLiveData)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {

        //Title and Setting button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 20.dp, end = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {


            Text(
                text = "Profile",
                fontSize = 30.sp,
                fontFamily = poppinsBold,
                color = Color.Black
            )
            IconButton(onClick = {
                openScreen?.invoke(Route.MAIN_SETTING_BASE)
            }) {
                Image(
                    painter = painterResource(id = R.drawable.setting),
                    contentDescription = "",
                    modifier = Modifier.size(25.dp)
                )
            }
        }

        AddSpace(size = 20.dp)

        //User Image And Details
        //Edit Profile Button
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 28.dp)
                    .padding(bottom = 18.dp)
                    .border(1.dp, Merino, RoundedCornerShape(20.dp))
                    .padding(horizontal = 20.dp)
                    .padding(top = 15.dp, bottom = 30.dp)
            ) {

                Box(contentAlignment = Alignment.BottomEnd) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(userLiveData.imageUrl ?: "").crossfade(true).build(),
                        contentDescription = "",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(200.dp))
                            .border(7.dp, DawnPink, RoundedCornerShape(200.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .padding(9.dp)
                    ) {
                        when (sign.value) {
                            "omega" -> {
                                Image(
                                    painter = painterResource(id = R.drawable.omega),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(30.dp)
                                        .background(Porcelain, RoundedCornerShape(100.dp))
                                        .padding(9.dp)
                                )
                            }

                            "beta" -> {
                                Image(
                                    painter = painterResource(id = R.drawable.betashn),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(30.dp)
                                        .background(Porcelain, RoundedCornerShape(100.dp))
                                        .padding(9.dp)
                                )
                            }

                            "alpha" -> {
                                Image(
                                    painter = painterResource(id = R.drawable.alpha),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(30.dp)
                                        .background(Porcelain, RoundedCornerShape(100.dp))
                                        .padding(9.dp)
                                )
                            }
                        }

                    }
                }
                AddSpace(size = 20.dp)
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = "${userLiveData.name ?: ""}, ${userLiveData.age ?: ""}",
                        fontFamily = poppinsBold,
                        color = Color.Black,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(start = 5.dp)
                    )
                    AddSpace(size = 0.dp)
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AddSpace(size = 7.dp)

                        val imageId = if (userLiveData.gender == "male") R.drawable.male
                        else R.drawable.female

                        Image(
                            painter = painterResource(id = imageId),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(BondiBlue),
                            modifier = Modifier
                                .rotate(-45f)
                                .size(13.dp), contentScale = ContentScale.FillBounds
                        )
                        AddSpace(size = 10.dp)
                        Text(
                            text = "${userLiveData.gender ?: "female"}",
                            fontFamily = poppinsRegular,
                            fontSize = 13.sp,
                            color = SilverChalice
                        )
                    }

                    AddSpace(size = 0.dp)

                    Box{
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable {
                                openScreen?.invoke(Route.NORMAL_ZODIAC_BASE)
                            }
                        ) {
                            AddSpace(size = 6.dp)
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(zodiimg.value).crossfade(true).build(),
                                contentDescription = "",
                                modifier = Modifier.size(17.dp),
                                contentScale = ContentScale.FillBounds
                            )
                            AddSpace(size = 7.dp)
                            Text(
                                text = zoditit.value,
                                fontFamily = poppinsRegular,
                                fontSize = 13.sp,
                                color = SilverChalice
                            )
                        }
                        Pulsating {
                            Surface(
                                color = themtransperent,
                                shape = RoundedCornerShape(5.dp),
                                modifier = Modifier
                                    .size(height = 20.dp, width = 90.dp).clickable {
                                        openScreen?.invoke(Route.NORMAL_ZODIAC_BASE)
                                    },
                                //.padding(bottom = 20.dp)
                                //.padding(horizontal = 20.dp),
                                content = {

                                }
                            )
                        }
                    }


                    AddSpace(size = 0.dp)

                    Box{
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable {
                                openScreen?.invoke(Route.CHINESE_ZODIAC_BASE)
                            }
                        ) {
                            AddSpace(size = 6.dp)
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(aniimg.value).crossfade(true).build(),
                                contentDescription = "",
                                modifier = Modifier.size(17.dp),
                                contentScale = ContentScale.FillBounds
                            )
                            AddSpace(size = 7.dp)
                            Text(
                                text = anitit.value,
                                fontFamily = poppinsRegular,
                                fontSize = 13.sp,
                                color = SilverChalice
                            )
                        }

                        Pulsating {
                            Surface(
                                color = themtransperent,
                                shape = RoundedCornerShape(5.dp),
                                modifier = Modifier
                                    .size(height = 20.dp, width = 90.dp).clickable {
                                        openScreen?.invoke(Route.CHINESE_ZODIAC_BASE)
                                    },
                                //.padding(bottom = 20.dp)
                                //.padding(horizontal = 20.dp),
                                content = {

                                }
                            )
                        }
                    }



                }


            }

            Button(
                onClick = {
                    openScreen?.invoke(Route.EDIT_PROFILE_BASE)
                },
                shape = RoundedCornerShape(100.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = VioletBlue
                ),
                modifier = Modifier
                    .size(150.dp, 35.dp)
                    .padding(top = 0.dp),
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.pencil),
                        contentDescription = "",
                        modifier = Modifier.size(15.dp)
                    )
                    AddSpace(size = 10.dp)
                    Text(
                        text = "Edit Profile",
                        fontSize = 15.sp,
                        fontFamily = poppinsRegular,
                        color = White
                    )
                }

            }
        }

        AddSpace(size = 35.dp)

        OutlinedButton(
            onClick = {
                openScreen?.invoke(Route.PERSONALITY_TEST_BASE)
            }, shape = RoundedCornerShape(10.dp), border = BorderStroke(
                1.dp,
                SilverChalice
            ), modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(horizontal = 30.dp)
        ) {
            Text(
                text = "Test Your Personality",
                fontFamily = poppinsRegular,
                color = Color.Black,
                fontSize = 14.sp
            )
        }
        AddSpace(size = 15.dp)

        Image(
            painter = painterResource(id = R.drawable.primium), contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(horizontal = 30.dp)
                .clickable {
                    openScreen?.invoke(Route.GET_PREMIUM_BASE)
                }, contentScale = ContentScale.FillBounds
        )

        AddSpace(size = 20.dp)

        /* Text(
             text = "My Posts", fontFamily = poppinsBold, color = Color.Black, fontSize = 17.sp,
             modifier = Modifier
                 .padding(horizontal = 30.dp)
         )*/

        /*Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(PearlBush)
        )
*/
        // val list = listOf("28 Jul 2022, 11:30 am", "01 Jan 2023, 11:11 am")


        /* LazyColumn(modifier = Modifier
             .heightIn(0.dp, 1000.dp)
             .padding(bottom = 90.dp), content = {
             items(list.size) { pos ->

                 val itemData = list[pos]
                 Column(
                     modifier = Modifier
                         .fillMaxWidth()
                 ) {
                     AddSpace(size = 20.dp)
                     Column(
                         modifier = Modifier
                             .fillMaxWidth()
                             .padding(horizontal = 30.dp)
                             .background(
                                 WhiteSmoke,
                                 RoundedCornerShape(10.dp)
                             )
                     ) {
                         Row(
                             horizontalArrangement = Arrangement.SpaceBetween,
                             verticalAlignment = Alignment.CenterVertically,
                             modifier = Modifier
                                 .padding(horizontal = 20.dp)
                                 .padding(top = 10.dp, bottom = 5.dp)
                                 .fillMaxWidth()
                         ) {
                             Text(
                                 text = itemData,
                                 fontFamily = poppinsRegular,
                                 color = SilverChalice,
                                 fontSize = 11.sp
                             )
                             IconButton(onClick = { *//*TODO*//* }, Modifier.size(20.dp)) {
                                Image(
                                    painter = painterResource(id = R.drawable.delete),
                                    contentDescription = "",
                                    Modifier.size(15.dp)
                                )
                            }
                        }
                        AddSpace(size = 5.dp)
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .padding(horizontal = 20.dp)
                                .background(PearlBush)

                        )
                        AddSpace(size = 15.dp)
                        Text(
                            text = "You can regret a lot of things but you’ll never regret being kind.",
                            fontSize = 12.sp,
                            fontFamily = poppinsRegular,
                            color = Color.Black, modifier = Modifier
                                .padding(horizontal = 20.dp)
                        )
                        AddSpace(size = 10.dp)
                    }
                }

            }
        })*/
    }
}

@Composable
fun Pulsating(pulseFraction: Float = 1.2f, content: @Composable () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition()

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = pulseFraction,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(modifier = Modifier.scale(scale)) {
        content()
    }
}

