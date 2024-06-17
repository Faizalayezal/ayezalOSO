package com.sanxingrenge.benben.screens.loggedInModule

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.sanxingrenge.benben.R
import com.sanxingrenge.benben.constant.Constns
import com.sanxingrenge.benben.constant.Constns.chatToProfile
import com.sanxingrenge.benben.constant.MainEducationList
import com.sanxingrenge.benben.constant.MainKidsList
import com.sanxingrenge.benben.constant.MainMaritalList
import com.sanxingrenge.benben.constant.MainRelationList
import com.sanxingrenge.benben.constant.MainReligionList
import com.sanxingrenge.benben.constant.dataStoreGetUserData
import com.sanxingrenge.benben.constant.showToast
import com.sanxingrenge.benben.responseModel.UpdateDataResponse
import com.sanxingrenge.benben.screens.Route
import com.sanxingrenge.benben.ui.theme.*
import com.sanxingrenge.benben.uiComponents.AddSpace
import com.sanxingrenge.benben.uiComponents.InterestGridItem
import com.sanxingrenge.benben.uiComponents.InterestGridLayout
import com.sanxingrenge.benben.viewModel.DiscoverScreenViewModel
import kotlinx.coroutines.flow.firstOrNull

@OptIn(ExperimentalPagerApi::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun ProfileDetailScreen(
    userId: String,
    openScreen: ((String) -> Unit)? = null,
    popBack: (() -> Unit)? = null,
    showLoading: (Boolean) -> Unit,

    ) {
    val viewModel: DiscoverScreenViewModel = hiltViewModel()
    val context = LocalContext.current
    val currantPlan = remember { mutableStateOf("") }

    LaunchedEffect(true, block = {
        val userData = context.dataStoreGetUserData().firstOrNull()
        currantPlan.value = userData?.plan_type ?: ""


    })

    var UpdateDataResponseList: UpdateDataResponse? = null


    val userLiveData = viewModel.userLiveData.collectAsState().value

    Log.d("TAG", "ProfileDetailScreen654: " + userLiveData)

    val imageUrlsAll = remember { mutableStateOf("") }
    var imageItemSize = remember { mutableStateOf(0) }
    val bio = remember { mutableStateOf("") }

    val userImage = remember { mutableStateOf(userLiveData?.image ?: "") }

    val userName = remember { mutableStateOf(userLiveData?.name ?: "") }
    val userPlans = remember { mutableStateOf(userLiveData?.subscribe_type ?: "") }
    val userAge = remember { mutableStateOf(userLiveData?.age ?: "") }
    val userGender = remember { mutableStateOf(userLiveData?.gender ?: "") }
    val userAddress = remember { mutableStateOf(userLiveData?.address ?: "") }

    // val userReligion = remember { mutableStateOf(userLiveData?.religion_id ?: "") }
    val userheight = remember { mutableStateOf(userLiveData?.height ?: "") }
    val userwight = remember { mutableStateOf(userLiveData?.height ?: "") }
    val userstatus = remember { mutableStateOf(userLiveData?.marital_status_id ?: "") }

    val userEducation = remember { mutableStateOf("") }
    val userReligion = remember { mutableStateOf("") }
    val user_points_percentage = remember { mutableStateOf("") }
    val userOccupation = remember { mutableStateOf("") }
    val userReletion = remember { mutableStateOf("") }
    val userChildran = remember { mutableStateOf("") }
    val userZodicTit = remember { mutableStateOf("") }
    val userZodicImg = remember { mutableStateOf("") }
    val userAnimTit = remember { mutableStateOf("") }
    val userAnimImg = remember { mutableStateOf("") }
    val personality = remember { mutableStateOf("") }
    val otherId = remember { mutableStateOf(0) }

    val beginnerList1 = remember { mutableStateOf<ArrayList<InterestGridItem>>(arrayListOf()) }
    val intermediateList2 = remember { mutableStateOf<ArrayList<InterestGridItem>>(arrayListOf()) }
    val masterList3 = remember { mutableStateOf<ArrayList<InterestGridItem>>(arrayListOf()) }




    LaunchedEffect(key1 = true, block = {
        showLoading.invoke(true)

        viewModel.getUserProfile(context, { list ->
            userName.value = list.name ?: ""
            userImage.value = list.image ?: ""
            userAge.value = list.age
            userGender.value = list.gender ?: ""
            userAddress.value = list.address ?: ""
            userReligion.value = list.religion_id ?: ""
            userEducation.value = list.education_id ?: ""
            userheight.value = list.height ?: ""
            userwight.value = list.weight ?: ""
            userstatus.value = list.marital_status_id ?: ""
            userOccupation.value = list.occupation ?: ""
            userReletion.value = list.relationship_id ?: ""
            userChildran.value = list.kid_id ?: ""
            userZodicTit.value = list.zodiac_sign?.title ?: ""
            userZodicImg.value = list.zodiac_sign?.image_full_path ?: ""
            userAnimTit.value = list.animal_sign?.title ?: ""
            userAnimImg.value = list.animal_sign?.image_full_path ?: ""
            userPlans.value = list.subscribe_type ?: ""
            personality.value = list.personality ?: ""
            bio.value = list.bio ?: ""
            user_points_percentage.value = list.user_total_points ?: ""
            otherId.value = list.id ?: 0
            imageItemSize.value = list.user_photos?.size ?: 0
            // imageUrlsAll.value = UpdateDataResponseList.user_photos?.get(i)?.image_url ?: ""


        }, { stats -> showLoading.invoke(false) }, userId.toIntOrNull() ?: 0)
    })


    viewModel.getInterestList { beginnerList, intermediateList, masterList ->
        beginnerList1.value = beginnerList
        intermediateList2.value = intermediateList
        masterList3.value = masterList

    }

    var EducationName = remember { mutableStateOf("") }
    var RelegionName = remember { mutableStateOf("") }
    var StatusName = remember { mutableStateOf("") }
    var ReletionName = remember { mutableStateOf("") }
    var KindsName = remember { mutableStateOf("") }

    EducationName.value = MainEducationList.find {
        (it.id ?: 0) == (userEducation.value.toIntOrNull() ?: 0)

    }?.name ?: ""

    RelegionName.value = MainReligionList.find {
        (it.id ?: 0) == (userReligion.value.toIntOrNull() ?: 0)

    }?.name ?: ""
    StatusName.value = MainMaritalList.find {
        (it.id ?: 0) == (userstatus.value.toIntOrNull() ?: 0)

    }?.name ?: ""

    ReletionName.value = MainRelationList.find {
        (it.id ?: 0) == (userReletion.value.toIntOrNull() ?: 0)

    }?.name ?: ""

    KindsName.value = MainKidsList.find {
        (it.id ?: 0) == (userChildran.value.toIntOrNull() ?: 0)

    }?.name ?: ""


    val heightInCm = userheight.value.replace("cm", "").trim().toIntOrNull() ?: 0
    val heightInInches = heightInCm.div(2.54)
    val feet = heightInInches.toInt().div(12)
    val inches = heightInInches.toInt().rem(12)
    val formattedHeight = "$feet'${inches}\""
    val state = rememberPagerState()


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        /*  AsyncImage(
              model = ImageRequest.Builder(LocalContext.current)
                  .data(userImage.value)
                  .crossfade(true)
                  .build(),
              contentDescription = "sdsds",
              contentScale = ContentScale.Crop,
              modifier = Modifier
                  .fillMaxWidth()
                  .height(370.dp)
          )*/


        Column {
            Box(modifier = Modifier.fillMaxWidth()) {
                SliderView(state, imageItemSize.value, userLiveData)
                DotsIndicator(
                    totalDots = imageItemSize.value,
                    selectedIndex = state.currentPage
                )
            }

        }






        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, end = 20.dp, start = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { popBack?.invoke() }, modifier = Modifier.size(50.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.back_arrow),
                    contentDescription = "", modifier = Modifier.size(40.dp)
                )
            }
            Text(
                text = user_points_percentage.value + "%",
                fontSize = 10.sp,
                fontFamily = poppinsMedium,
                color = VioletBlue,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(
                        VioletBlue.copy(alpha = 0.35f),
                        RoundedCornerShape(200.dp)
                    )
                    .padding(
                        start = 10.dp,
                        end = 10.dp,
                        top = 5.dp,
                        bottom = 3.dp
                    )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 350.dp)
                .background(
                    Color.White,
                    RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp)
                )
                .padding(top = 20.dp, bottom = 20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = userName.value + "," + userAge.value,
                    fontFamily = poppinsBold,
                    color = Color.Black,
                    fontSize = 25.sp
                )
                if (personality.value == "omega") {
                    Image(
                        painter = painterResource(id = R.drawable.omega),
                        contentDescription = "",
                        modifier = Modifier
                            .size(25.dp)
                            .padding(5.dp)
                            .background(Porcelain, RoundedCornerShape(100.dp))
                    )

                } else if (personality.value == "beta") {
                    Image(
                        painter = painterResource(id = R.drawable.betashn),
                        contentDescription = "",
                        modifier = Modifier
                            .size(25.dp)
                            .padding(5.dp)
                            .background(Porcelain, RoundedCornerShape(100.dp))
                    )

                } else if (personality.value == "alpha") {
                    Image(
                        painter = painterResource(id = R.drawable.alpha),
                        contentDescription = "",
                        modifier = Modifier
                            .size(25.dp)
                            .padding(5.dp)
                            .background(Porcelain, RoundedCornerShape(100.dp))
                    )

                }

            }
            AddSpace(size = 5.dp)

            val imageRes =
                if (userGender.value.equals("male", true))
                    R.drawable.male
                else R.drawable.female
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 20.dp)
            ) {
                AddSpace(size = 6.dp)
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(BondiBlue),
                    modifier = Modifier
                        .rotate(-45f)
                        .size(13.dp), contentScale = ContentScale.FillBounds
                )
                AddSpace(size = 10.dp)
                Text(
                    text = userGender.value /*+ " | " + "13353 mi"*/,
                    fontFamily = poppinsRegular,
                    fontSize = 13.sp,
                    color = SilverChalice
                )
            }
            AddSpace(size = 2.dp)

            Box {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable {
                            openScreen?.invoke(Route.NORMAL_ZODIAC_BASE)
                        }
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 20.dp)
                ) {
                    AddSpace(size = 6.dp)
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(userZodicImg.value)
                            .crossfade(true)
                            .build(),
                        contentDescription = "sdsds",
                        modifier = Modifier.size(17.dp),
                        contentScale = ContentScale.FillBounds
                    )
                    AddSpace(size = 7.dp)
                    Text(
                        text = userZodicTit.value,
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
                            .size(height = 20.dp, width = 90.dp)
                            .clickable {
                                openScreen?.invoke(Route.NORMAL_ZODIAC_BASE)
                            }
                            //.padding(bottom = 20.dp)
                            .padding(start = 15.dp),
                        content = {

                        }
                    )
                }

            }


            AddSpace(size = 2.dp)
            Box {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable {
                            openScreen?.invoke(Route.CHINESE_ZODIAC_BASE)
                        }
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 20.dp)
                ) {
                    AddSpace(size = 6.dp)
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(userAnimImg.value)
                            .crossfade(true)
                            .build(),
                        contentDescription = "sdsds",
                        modifier = Modifier.size(17.dp),
                        contentScale = ContentScale.FillBounds
                    )
                    AddSpace(size = 7.dp)
                    Text(
                        text = userAnimTit.value,
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
                            .size(height = 20.dp, width = 90.dp)
                            .clickable {
                                openScreen?.invoke(Route.CHINESE_ZODIAC_BASE)
                            }
                            //.padding(bottom = 20.dp)
                            .padding(start = 15.dp),
                        content = {

                        }
                    )
                }
            }


            AddSpace(25.dp)
            Text(
                text = bio.value,
                fontFamily = poppinsRegular,
                fontSize = 13.sp,
                color = SilverChalice, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
            )
            AddSpace(30.dp)
            SubTitle("About me")
            AddSpace(20.dp)


            val list = listOf(
                // AboutMeDataItem(userAddress.value, R.drawable.planet),
                // AboutMeDataItem("5'55''", R.drawable.measure),
                // AboutMeDataItem("157 lb", R.drawable.bag),
                // AboutMeDataItem(EducationName.value, R.drawable.education),
                // AboutMeDataItem("religionName?:", R.drawable.pray),
                // AboutMeDataItem("Merried", R.drawable.borderheart),
                AboutMeDataItem("Photographer", R.drawable.probag),
            )

            Column {
                Row(
                    modifier = Modifier.padding(start = 15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color(0xFFF0F0F0),
                                shape = RoundedCornerShape(50.dp)
                            )
                            .padding(10.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.planet),
                            contentDescription = null,
                            modifier = Modifier.size(17.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = userAddress.value,
                            fontSize = 13.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(start = 20.dp)

                        )

                    }
                    Spacer(modifier = Modifier.width(10.dp))

                    Box(
                        modifier = Modifier
                            .background(
                                color = Color(0xFFF0F0F0),
                                shape = RoundedCornerShape(50.dp)
                            )
                            .padding(10.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.measure),
                            contentDescription = null,
                            modifier = Modifier.size(17.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = formattedHeight,
                            fontSize = 13.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(start = 20.dp)

                        )

                    }

                    Spacer(modifier = Modifier.width(10.dp))


                    Box(
                        modifier = Modifier
                            .background(
                                color = Color(0xFFF0F0F0),
                                shape = RoundedCornerShape(50.dp)
                            )
                            .padding(10.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.bag),
                            contentDescription = null,
                            modifier = Modifier.size(17.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = userwight.value,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(start = 20.dp)

                        )

                    }

                }
                Spacer(modifier = Modifier.height(5.dp))



                Row(
                    modifier = Modifier.padding(start = 15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color(0xFFF0F0F0),
                                shape = RoundedCornerShape(50.dp)
                            )
                            .padding(10.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.education),
                            contentDescription = null,
                            modifier = Modifier.size(17.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = EducationName.value,
                            fontSize = 13.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(start = 20.dp)

                        )

                    }
                    Spacer(modifier = Modifier.width(10.dp))

                    Box(
                        modifier = Modifier
                            .background(
                                color = Color(0xFFF0F0F0),
                                shape = RoundedCornerShape(50.dp)
                            )
                            .padding(10.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.pray),
                            contentDescription = null,
                            modifier = Modifier.size(17.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = RelegionName.value,
                            fontSize = 13.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(start = 20.dp)

                        )

                    }

                    Spacer(modifier = Modifier.width(10.dp))


                    Box(
                        modifier = Modifier
                            .background(
                                color = Color(0xFFF0F0F0),
                                shape = RoundedCornerShape(50.dp)
                            )
                            .padding(10.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.borderheart),
                            contentDescription = null,
                            modifier = Modifier.size(17.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = StatusName.value,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(start = 20.dp)

                        )

                    }

                }

                Spacer(modifier = Modifier.height(5.dp))

                Row(
                    modifier = Modifier.padding(start = 15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color(0xFFF0F0F0),
                                shape = RoundedCornerShape(50.dp)
                            )
                            .padding(10.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.probag),
                            contentDescription = null,
                            modifier = Modifier.size(17.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = userOccupation.value,
                            fontSize = 13.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(start = 20.dp)

                        )

                    }
                    Spacer(modifier = Modifier.width(10.dp))

                    Box(
                        modifier = Modifier
                            .background(
                                color = Color(0xFFF0F0F0),
                                shape = RoundedCornerShape(50.dp)
                            )
                            .padding(10.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.reltion),
                            contentDescription = null,
                            modifier = Modifier.size(17.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = ReletionName.value,
                            fontSize = 13.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(start = 20.dp)

                        )

                    }

                }

                Spacer(modifier = Modifier.height(5.dp))

                Row(
                    modifier = Modifier.padding(start = 15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color(0xFFF0F0F0),
                                shape = RoundedCornerShape(50.dp)
                            )
                            .padding(10.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.child),
                            contentDescription = null,
                            modifier = Modifier.size(17.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = KindsName.value,
                            fontSize = 13.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(start = 20.dp)

                        )

                    }

                }

            }


            /* AndroidView(
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(start = 20.dp, end = 20.dp),
                   factory = { context ->
                       val view = LayoutInflater.from(context)
                           .inflate(R.layout.chip_group_layout, null, false)
                       val chipGroup = view.findViewById<ChipGroup>(R.id.chipGroup)

                       Log.d("TAG", "ProfileDetailScreen:300 "+list)
                       for (i in list) {

                           val childView = LayoutInflater.from(context)
                               .inflate(R.layout.chip_item, null)
                           val imgChip = childView.findViewById<ImageView>(R.id.imgChip)
                           val txtChip = childView.findViewById<TextView>(R.id.txtChip)



                           imgChip.setImageDrawable(
                               ContextCompat.getDrawable(
                                   context,
                                   i.image
                               )
                           )
                           txtChip.text = i.name

                           chipGroup.addView(childView)
                       }

                       view
                   },
                   update = {

                   })*/



            AddSpace(20.dp)
            SubTitle("Interests")
            AddSpace(20.dp)

            LazyColumn(
                modifier = Modifier
                    .heightIn(0.dp, 1000.dp)
                    .fillMaxWidth(),
                content = {

                    item {
                        InterestLayout(
                            beginnerList1.value,
                            intermediateList2.value,
                            masterList3.value,
                        )
                    }
                    /* item {
                         InterestLayout("Intermediate", interestGridList2)
                     }
                     item {
                         InterestLayout("Beginner", interestGridList3)
                     }*/

                })

            val plan = userPlans.value

            Log.d("TAG", "ProfileDetailScreen700: " + currantPlan.value + chatToProfile)
            if (chatToProfile == "1") {
                Log.d("TAG", "ProfileDetailScreen700: " + currantPlan.value + chatToProfile)
            } else {
                if (currantPlan.value != "") {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        OutlinedButton(
                            onClick = {
                                val data = userImage.value
                                Constns.otherUserImage = data
                                openScreen?.invoke("${Route.CHAT_BASE}/${otherId.value}/${userName.value}")
                            },
                            border = BorderStroke(1.dp, VioletBlue),
                            shape = RoundedCornerShape(100.dp),
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .height(45.dp)
                        ) {
                            Text(text = "Send Message")
                        }
                        IconButton(
                            onClick = {
                                val data = userImage.value
                                Constns.otherUserImage = data
                                openScreen?.invoke("${Route.CHAT_BASE}/${otherId.value}/${userName.value}")
                            },
                            modifier = Modifier
                                .padding(start = 20.dp)
                                .size(25.dp)
                                .background(VioletBlue, RoundedCornerShape(100.dp))
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.send),
                                modifier = Modifier
                                    .size(25.dp)
                                    .padding(bottom = 2.dp),
                                contentDescription = "",
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        OutlinedButton(
                            onClick = {
                                showLoading.invoke(true)
                                viewModel.SendFriendRequest(
                                    context,
                                    userId.toIntOrNull() ?: 0
                                ) { isSuccess, msg ->

                                    if (isSuccess == true) {
                                        showLoading.invoke(false)
                                        if (msg == "Friend request has been sent successfully") {
                                            context.showToast("Friend Request Has Been Sent Successfully")
                                        }
                                        popBack?.invoke()
                                    }

                                }
                            },
                            border = BorderStroke(1.dp, VioletBlue),
                            shape = RoundedCornerShape(100.dp),
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .height(45.dp)
                        ) {
                            Text(text = "Send Friend Request")
                        }
                        IconButton(
                            onClick = {
                                showLoading.invoke(true)

                                viewModel.SendFriendRequest(
                                    context,
                                    userId.toIntOrNull() ?: 0
                                ) { isSuccess, msg ->

                                    if (isSuccess == true) {
                                        showLoading.invoke(false)
                                        if (msg == "Friend request has been sent successfully") {
                                            context.showToast("Friend Request Has Been Sent Successfully")
                                        }
                                        popBack?.invoke()
                                    }


                                }

                            },
                            modifier = Modifier
                                .padding(start = 20.dp)
                                .size(25.dp)
                                .background(VioletBlue, RoundedCornerShape(100.dp))
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.send),
                                modifier = Modifier
                                    .size(25.dp)
                                    .padding(bottom = 2.dp),
                                contentDescription = "",
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }


        }
    }

}


/*@Composable
private fun manyList(list: List<AboutMeDataItem>) {

    list.forEachIndexed { index, aboutMeDataItem ->
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp),
            factory = { context ->
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.chip_group_layout, null, false)
                val chipGroup = view.findViewById<ChipGroup>(R.id.chipGroup)

                val childView = LayoutInflater.from(context)
                        .inflate(R.layout.chip_item, null)
                    val imgChip = childView.findViewById<ImageView>(R.id.imgChip)
                    val txtChip = childView.findViewById<TextView>(R.id.txtChip)

                    imgChip.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            aboutMeDataItem.image
                        )
                    )
                    txtChip.text = aboutMeDataItem.name

                    chipGroup.addView(childView)


                view
            },
            update = {

            })

    }


}*/


@Composable
private fun InterestLayout(
    beginnerList1: (ArrayList<InterestGridItem>)? = null,
    intermediateList2: (ArrayList<InterestGridItem>)? = null,
    masterList3: (ArrayList<InterestGridItem>)? = null,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        /* Row(
             modifier = Modifier
                 .fillMaxWidth()
                 .padding(horizontal = 20.dp),
             horizontalArrangement = Arrangement.Center,
             verticalAlignment = Alignment.CenterVertically
         ) {
             Spacer(
                 modifier = Modifier
                     .weight(1f)
                     .height(1.dp)
                     .background(QuillGrey)
             )
             Text(
                 text = title,
                 fontFamily = poppinsMedium,
                 fontSize = 12.sp,
                 color = Color.Black,
                 modifier = Modifier
                     .border(1.dp, QuillGrey, RoundedCornerShape(100.dp))
                     .padding(horizontal = 20.dp)
                     .padding(top = 4.dp, bottom = 2.dp)
             )
             Spacer(
                 modifier = Modifier
                     .weight(1f)
                     .height(1.dp)
                     .background(QuillGrey)
             )
         }*/
        AddSpace(size = 20.dp)
        if (masterList3?.isEmpty() == false) {
            InterestChildItem(
                "Master", masterList3,
            )
        }
        if (intermediateList2?.isEmpty() == false) {
            InterestChildItem(
                "Intermediate", intermediateList2,
            )
        }
        if (beginnerList1?.isEmpty() == false) {
            InterestChildItem(
                "Beginner", beginnerList1,
            )
        }
        AddSpace(size = 20.dp)
    }
}

/*@Composable
private fun InterestLayout(title: String, intrimList: List<InterestGridItem>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
                    .background(QuillGrey)
            )
            Text(
                text = title,
                fontFamily = poppinsMedium,
                fontSize = 12.sp,
                color = Color.Black,
                modifier = Modifier
                    .border(1.dp, QuillGrey, RoundedCornerShape(100.dp))
                    .padding(horizontal = 20.dp)
                    .padding(top = 4.dp, bottom = 2.dp)
            )
            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
                    .background(QuillGrey)
            )
        }
        AddSpace(size = 20.dp)
        InterestGridLayout(intrimList, 5, clickable = false, openPopupOnClick = {

        })
        // InterestGridLayout(intrimList, 5, clickable = false, openPopupOnClick = null)
        AddSpace(size = 20.dp)
    }
}*/

@Composable
private fun InterestChildItem(
    title: String, intrimList: List<InterestGridItem>,
) {
    Log.d("TAG", "InterestsfsdfChildItem: $intrimList")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            modifier = Modifier
                .weight(1f)
                .height(1.dp)
                .background(QuillGrey)
        )
        Text(
            text = title,
            fontFamily = poppinsMedium,
            fontSize = 12.sp,
            color = Color.Black,
            modifier = Modifier
                .border(1.dp, QuillGrey, RoundedCornerShape(100.dp))
                .padding(horizontal = 20.dp)
                .padding(top = 4.dp, bottom = 2.dp)
        )
        Spacer(
            modifier = Modifier
                .weight(1f)
                .height(1.dp)
                .background(QuillGrey)
        )
    }
    AddSpace(size = 10.dp)
    InterestGridLayout(intrimList, 5, clickable = false, openPopupOnClick = {})
}


private val interestGridList1 = listOf(
    InterestGridItem("Yoga", ""),
    InterestGridItem("Swimming", ""),
    InterestGridItem("Cricket", ""),
    InterestGridItem("Chess", ""),
    InterestGridItem("Painting", ""),
)
private val interestGridList2 = listOf(
    InterestGridItem("Football", ""),
    InterestGridItem("Puzzle", ""),
    InterestGridItem("Direct Hit", ""),
    InterestGridItem("Bowling", ""),
)
private val interestGridList3 = listOf(
    InterestGridItem("Trumpet", ""),
    InterestGridItem("Guitar", ""),
    InterestGridItem("Drum", ""),
    InterestGridItem("Theater", ""),
)

@Composable
private fun SubTitle(name: String) {
    Text(
        text = name,
        fontFamily = poppinsBold,
        color = Color.Black,
        fontSize = 22.sp, modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SliderView(
    state: PagerState,
    imageSize: Int,
    userLiveData: UpdateDataResponse?,
) {
    /*userLiveData?.user_photos?.forEachIndexed { index, usersphoto ->

    }*/

    HorizontalPager(
        state = state,
        count = imageSize, modifier = Modifier
            .fillMaxWidth()
    ) { page ->

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(contentAlignment = Alignment.BottomCenter) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(userLiveData?.user_photos?.get(page)?.image_url)
                        .placeholder(R.drawable.placeholder)
                        .scale(coil.size.Scale.FILL)
                        .crossfade(true)
                        .build(),
                    contentDescription = "sdsds",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(370.dp)
                )

            }

        }


    }

}


@Composable
fun DotsIndicator(
    totalDots: Int,
    selectedIndex: Int
) {
    //  .wrapContentHeight()
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 160.dp, top = 300.dp)
        /* horizontalArrangement = Arrangement.Center,*/
    ) {

        items(totalDots) { index ->
            if (index == selectedIndex) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(color = VioletBlue)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(color = Color.LightGray)
                )
            }

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}


data class AboutMeDataItem(
    var name: String,
    var image: Int,
)









