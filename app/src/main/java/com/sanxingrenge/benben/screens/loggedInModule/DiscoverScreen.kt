package com.sanxingrenge.benben.screens.loggedInModule

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sanxingrenge.benben.R
import com.sanxingrenge.benben.constant.Constns
import com.sanxingrenge.benben.constant.Constns.ApiTime
import com.sanxingrenge.benben.constant.Constns.FBName
import com.sanxingrenge.benben.constant.Constns.SocialLoginMessage
import com.sanxingrenge.benben.constant.Constns.chatToProfile
import com.sanxingrenge.benben.constant.Constns.emailLoginStore
import com.sanxingrenge.benben.constant.Constns.emailLoginUserExist
import com.sanxingrenge.benben.constant.Constns.isFirstTime
import com.sanxingrenge.benben.constant.dataStoreGetUserData
import com.sanxingrenge.benben.constant.showToast
import com.sanxingrenge.benben.dataModel.UserDataObject
import com.sanxingrenge.benben.responseModel.DiscoverResponseData
import com.sanxingrenge.benben.screens.Route
import com.sanxingrenge.benben.screens.zodiacSignModule.RecommendnedMainList
import com.sanxingrenge.benben.ui.theme.*
import com.sanxingrenge.benben.uiComponents.AddSpace
import com.sanxingrenge.benben.uiComponents.shimmerRotation
import com.sanxingrenge.benben.viewModel.DiscoverScreenViewModel
import com.sanxingrenge.benben.viewModel.FilterViewModel
import com.valentinilk.shimmer.LocalShimmerTheme
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer


enum class DiscoverTabs {
    Recommended, Nearby, Friends,
}

private val recommendedList = listOf(
    RecommendedListObject(
        R.drawable.userimg1, "Matthew", "22", "10082 mi", "male", true, "44.0%"
    ),
    RecommendedListObject(
        R.drawable.userimg2, "Ash", "25", "12234 mi", "female", false, "36.0%"
    ),
    RecommendedListObject(
        R.drawable.userimg3, "Muke", "20", "13282 mi", "male", false, "29.0%"
    ),
    RecommendedListObject(
        R.drawable.userimg4, "Fariha", "23", "19328 mi", "female", true, "32.0%"
    ),
    RecommendedListObject(
        R.drawable.userimg1, "Matthew", "22", "10082 mi", "male", true, "44.0%"
    ),
    RecommendedListObject(
        R.drawable.userimg2, "Ash", "25", "12234 mi", "female", false, "36.0%"
    ),
    RecommendedListObject(
        R.drawable.userimg3, "Muke", "20", "13282 mi", "male", false, "29.0%"
    ),
    RecommendedListObject(
        R.drawable.userimg4, "Fariha", "23", "19328 mi", "female", true, "32.0%"
    ),
)

data class RecommendedListObject(
    val imageId: Int,
    val name: String,
    val age: String,
    val distance: String,
    val gender: String,
    val online: Boolean,
    val percent: String,
)

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("SuspiciousIndentation")
@Composable
fun DiscoverScreen(
    openScreen: ((String) -> Unit)? = null
) {
    emailLoginStore = ""
    FBName = ""
    emailLoginUserExist = 2
    isFirstTime ==true
    val context = LocalContext.current
    val viewModel: DiscoverScreenViewModel = hiltViewModel()
    val viewModelFilter: FilterViewModel = hiltViewModel()
    val isFilterApply = RecommendnedMainList.firstOrNull()?.isFilter
    SocialLoginMessage = ""
    val showLoading = remember {
        mutableStateOf(true)
    }

    var Data = remember {
        mutableStateOf(UserDataObject())
    }

    var reltion by remember {
        mutableStateOf("")
    }
    /*LaunchedEffect(key1 = true, block = {
        context.filterDataStoreGetUserData().collect {
        // Log.d("TAG", "DiscsdoverScreen: " + it)
        }

    })*/
    LaunchedEffect(key1 = true, block =  {
        context.dataStoreGetUserData().collect {

            Data.value = it
            // Log.d("TAG", "DiscoverScreen132: " + it.userId)

            Log.d("TAG", "DiscoverScreen132:132 " + it.userId)
            Log.d("TAG", "DiscoverScreen132:133 " + it)
            if (it.userId == null) {
                if (Constns.IsFilter == false && ApiTime == true) {
                    ApiTime = false
                    viewModel.DiscoverDummyUser(onListReceived = { list, isSuccssfull ->
                        RecommendnedMainList = if (!list.isNullOrEmpty()) list
                        else emptyList()

                        showLoading.value = false
                        Log.d("TAG", "DiscoverScreen187: " + list)

                        ApiTime = true

                    }, context)


                } else {
                    showLoading.value = false
                }
            } else {
                if (Constns.IsFilter == false && ApiTime == true) {
                    ApiTime = false
                    viewModel.DiscoverRecommendedUser(onListReceived = { list, isSuccssfull ->
                        RecommendnedMainList = if (!list.isNullOrEmpty()) list
                        else emptyList()

                        showLoading.value = false
                        ApiTime = true

                    }, context)


                } else {
                    showLoading.value = false
                }
            }
        }
    })

    val selectedInsideTab = remember {
        mutableStateOf(DiscoverTabs.Recommended)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 20.dp, end = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Browse", fontSize = 30.sp, fontFamily = poppinsBold, color = Color.Black
            )


            Row {
                IconButton(onClick = {
                    openScreen?.invoke(Route.SEARCH_USER_BASE)
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.search_black),
                        contentDescription = "",
                        modifier = Modifier.size(22.dp)
                    )
                }
                IconButton(onClick = {
                    openScreen?.invoke(Route.DISCOVER_FILTER_BASE)
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.setting_black),
                        contentDescription = "",
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }
        AddSpace(size = 25.dp)

        Row(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
            Column {
                Text(text = "Recommended",
                    color = if (selectedInsideTab.value == DiscoverTabs.Recommended) VioletBlue else StarDust,
                    fontFamily = if (selectedInsideTab.value == DiscoverTabs.Recommended) poppinsMedium else poppinsRegular,
                    fontSize = 16.sp,
                    modifier = Modifier.clickable {
                        selectedInsideTab.value = DiscoverTabs.Recommended

                        Log.d("Tegg", "DiscoverScreen:2229 "+Constns.IsFilter)
                        if(Constns.IsFilter == false){
                            showLoading.value = true

                            viewModel.DiscoverRecommendedUser(onListReceived = { list, isSuccssfull ->

                                RecommendnedMainList = if (!list.isNullOrEmpty()) list
                                else emptyList()

                                showLoading.value = false

                            }, context)
                        }else{
                            showLoading.value = true
                            viewModelFilter.userFilterApi(context) { list, status ->
                                if (status) {
                                    showLoading.value = false
                                    RecommendnedMainList = if (!list.isNullOrEmpty()) list
                                    else emptyList()
                                    RecommendnedMainList.firstOrNull()?.isFilter = false
                                    Constns.IsFilter = true

                                }


                            }
                        }



                    })

                AnimatedVisibility(visible = selectedInsideTab.value == DiscoverTabs.Recommended) {
                    Box(
                        modifier = Modifier
                            .size(30.dp, 3.dp)
                            .background(
                                VioletBlue, RoundedCornerShape(100.dp)
                            )
                    )
                }

            }
            AddSpace(size = 15.dp)
            Column {
                Text(text = "Nearby",
                    color = if (selectedInsideTab.value == DiscoverTabs.Nearby) VioletBlue else StarDust,
                    fontFamily = if (selectedInsideTab.value == DiscoverTabs.Nearby) poppinsMedium else poppinsRegular,
                    fontSize = 16.sp,
                    modifier = Modifier.clickable {
                        Log.d("TAG", "DiscoverScreen: " + "djhsdgf")
                        selectedInsideTab.value = DiscoverTabs.Nearby
                        showLoading.value = true
                        viewModel.DiscoverNearByUser(onListReceived = { list, isSuccssfull ->
                            showLoading.value = false

                            RecommendnedMainList = if (!list.isNullOrEmpty()) list
                            else emptyList()

                            if (isSuccssfull == false) {
                                context.showToast("Please Purchase Any Plan For Access This Future")
                                return@DiscoverNearByUser
                            }


                        }, context)

                    })

                AnimatedVisibility(visible = selectedInsideTab.value == DiscoverTabs.Nearby) {

                    Box(
                        modifier = Modifier
                            .size(30.dp, 3.dp)
                            .background(
                                VioletBlue, RoundedCornerShape(100.dp)
                            )
                    )
                }
            }/* AddSpace(size = 15.dp)
             Column {
                 Text(
                     text = "Friends",
                     color = if (selectedInsideTab.value == DiscoverTabs.Friends) VioletBlue else StarDust,
                     fontFamily =
                     if (selectedInsideTab.value == DiscoverTabs.Friends) poppinsMedium else poppinsRegular,
                     fontSize = 16.sp,
                     modifier = Modifier.clickable {
                         selectedInsideTab.value = DiscoverTabs.Friends
                     }
                 )

                 AnimatedVisibility(visible = selectedInsideTab.value == DiscoverTabs.Friends) {
                     Box(
                         modifier = Modifier
                             .size(30.dp, 3.dp)
                             .background(
                                 VioletBlue,
                                 RoundedCornerShape(100.dp)
                             )
                     )
                 }
             }*/
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(PearlBush)
        ) {

        }

        LazyVerticalGrid(modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp, start = 20.dp, end = 20.dp, bottom = 70.dp),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            content = {

                if (showLoading.value) {
                    items(4) {
                        DiscoverItemDesignShimmer()
                    }
                } else {
                    items(RecommendnedMainList.size) { pos ->

                        DiscoverItemDesign(
                            RecommendnedMainList[pos].id ?: 0, pos, openScreen, RecommendnedMainList
                        )
                    }
                }

            })

        /*val lazyListState = rememberLazyGridState()
        LazyVerticalGrid(state = lazyListState,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 30.dp, start = 20.dp, end = 20.dp, bottom = 70.dp),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            content = {

                if (showLoading.value) {
                    items(4) {
                        DiscoverItemDesignShimmer()
                    }
                } else {
                    items(RecommendnedMainList.size) { pos ->


                        if (pos == RecommendnedMainList.lastIndex && lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == pos) {

                            RecommendnedMainList += ( RecommendnedMainList.size until RecommendnedMainList.size + 10).map {

                                "Item $it"

                            }
                        }



                        DiscoverItemDesign(
                            RecommendnedMainList[pos].id ?: 0,
                            pos,
                            openScreen,
                            RecommendnedMainList
                        )
                    }
                }

            })*/

    }


    //openRecoderDialog.value = true

}


private val modifier2 = Modifier
    .background(
        VioletBlue.copy(alpha = 0.35f), RoundedCornerShape(200.dp)
    )
    .padding(
        start = 10.dp, end = 10.dp, top = 5.dp, bottom = 3.dp
    )
private val modifier1 = Modifier
    .height(200.dp)
    .fillMaxWidth()
    .clip(RoundedCornerShape(8.dp))
    .background(shimmerBaseColor, RoundedCornerShape(8.dp))

@Composable
private fun DiscoverItemDesignShimmer() {
    val shimmer = rememberShimmer(
        shimmerBounds = ShimmerBounds.View, theme = LocalShimmerTheme.current.copy(
            rotation = shimmerRotation
        )
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shimmer(shimmer)
    ) {

        Box(
            modifier = modifier1
        )
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 10.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = "            ",
                    fontSize = 10.sp,
                    fontFamily = poppinsMedium,
                    color = VioletBlue,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .background(
                            shimmerTopColor, RoundedCornerShape(200.dp)
                        )
                        .padding(
                            start = 10.dp, end = 10.dp, top = 5.dp, bottom = 3.dp
                        )
                )
            }

            Column(
                modifier = Modifier.padding(
                    horizontal = 10.dp, vertical = 10.dp
                )
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "                   ",
                        fontFamily = poppinsSemiBold,
                        color = Color.White,
                        fontSize = 17.sp,
                        modifier = Modifier.background(shimmerTopColor, RoundedCornerShape(5.dp))
                    )
                    AddSpace(size = 7.dp)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.transparent),
                        contentDescription = "",
                        Modifier
                            .background(
                                Color.White.copy(alpha = 0.15f), RoundedCornerShape(100.dp)
                            )
                            .size(25.dp)
                            .padding(6.dp)
                    )
                    AddSpace(size = 10.dp)
                    Box(
                        Modifier
                            .height(10.dp)
                            .width(1.dp)
                            .background(Color.White)

                    )

                    AddSpace(size = 10.dp)

                    Text(
                        text = "                  ",
                        fontSize = 12.sp,
                        fontFamily = poppinsRegular,
                        color = Color.White,
                        modifier = Modifier.background(shimmerTopColor, RoundedCornerShape(5.dp))
                    )
                }

            }
        }

    }
}

@Composable
private fun DiscoverItemDesign(
    userId: Int,
    pos: Int,
    openScreen: ((String) -> Unit)? = null,
    RecommendnedMainList: List<DiscoverResponseData>
) {
    val openLogoutDialog = remember {
        mutableStateOf(false)
    }

    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            /* if (gsgdsdg) {


            } else {
            }*/
            chatToProfile = ""
            openScreen?.invoke(Route.PROFILE_DETAIL_BASE + "/${userId}")

        }) {
        //painter = painterResource(id = recommendedList[pos].imageId),
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(RecommendnedMainList[pos].image)
                .crossfade(true).build(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = modifier1
        )

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 10.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = RecommendnedMainList[pos].user_total_points.toString() + " %",
                    fontSize = 10.sp,
                    fontFamily = poppinsMedium,
                    color = VioletBlue,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .background(
                            VioletBlue.copy(alpha = 0.35f), RoundedCornerShape(200.dp)
                        )
                        .padding(
                            start = 10.dp, end = 10.dp, top = 5.dp, bottom = 3.dp
                        )
                )
            }

            LazyRow {
                item {
                    Column(
                        modifier = Modifier.padding(
                            horizontal = 10.dp, vertical = 10.dp
                        )
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = RecommendnedMainList[pos].name + ", " + RecommendnedMainList[pos].age,
                                fontFamily = poppinsSemiBold,
                                color = Color.White,
                                fontSize = 14.sp,
                                modifier = Modifier
                            )
                            AddSpace(size = 5.dp)
                            Box(
                                Modifier
                                    .height(10.dp)
                                    .width(1.dp)
                                    .background(Color.White)
                            )
                            AddSpace(size = 4.dp)
                            /* val imageRes =
                                 if (RecommendnedMainList[pos].gender.equals("male", true)) R.drawable.male
                                 else R.drawable.female*/

                            /*Image(
                                painter = painterResource(id = imageRes),
                                contentDescription = "",
                                colorFilter = ColorFilter.tint(RichCarmine),
                                modifier = Modifier
                                    .size(15.dp)
                            )*/

                            if (RecommendnedMainList[pos].personality == "omega") {
                                Image(
                                    painter = painterResource(id = R.drawable.omega),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(15.dp)
                                        .background(Porcelain, RoundedCornerShape(100.dp))
                                        .padding(3.dp)

                                )
                            } else if (RecommendnedMainList[pos].personality == "beta") {
                                Image(
                                    painter = painterResource(id = R.drawable.betashn),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(15.dp)
                                        .background(Porcelain, RoundedCornerShape(100.dp))
                                        .padding(3.dp)

                                )
                            } else if (RecommendnedMainList[pos].personality == "alpha") {
                                Image(
                                    painter = painterResource(id = R.drawable.alpha),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(15.dp)
                                        .background(Porcelain, RoundedCornerShape(100.dp))
                                        .padding(3.dp)

                                )
                            }
                            AddSpace(size = 4.dp)

                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(RecommendnedMainList[pos].animal_sign?.image_full_path)
                                    .crossfade(true).build(),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(15.dp)
                            )
                            AddSpace(size = 4.dp)

                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(RecommendnedMainList[pos].zodiac_sign?.image_full_path)
                                    .crossfade(true).build(),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(15.dp)
                            )


                            /*
                                                if (RecommendnedMainList[pos].status == "active") {
                                                    Box(
                                                        modifier = Modifier
                                                            .size(6.dp)
                                                            .background(
                                                                LawnGreen,
                                                                RoundedCornerShape(100.dp)
                                                            )
                                                    )
                                                }
                            */
                        }

                        /* val imageRes =
                             if (RecommendnedMainList[pos].gender.equals("male", true))
                                 R.drawable.male
                             else R.drawable.female*/

                        /* Row(
                             verticalAlignment = Alignment.CenterVertically,
                             horizontalArrangement = Arrangement.Center
                         ) {
                             Image(
                                 painter = painterResource(id = imageRes),
                                 contentDescription = "",
                                 Modifier
                                     .background(
                                         Color.White.copy(alpha = 0.15f),
                                         RoundedCornerShape(100.dp)
                                     )
                                     .size(25.dp)
                                     .padding(6.dp)
                             )
                             AddSpace(size = 10.dp)
                             Box(
                                 Modifier
                                     .height(10.dp)
                                     .width(1.dp)
                                     .background(Color.White)

                             )

                             AddSpace(size = 10.dp)

                             Text(
                                 text = RecommendnedMainList[pos].distance ?: "",
                                 fontSize = 12.sp,
                                 fontFamily = poppinsRegular,
                                 color = Color.White,
                                 modifier = Modifier
                             )
                         }*/

                    }
                }
            }


        }

    }
}







