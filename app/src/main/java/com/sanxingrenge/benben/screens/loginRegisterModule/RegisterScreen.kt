package com.sanxingrenge.benben.screens.loginRegisterModule

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sanxingrenge.benben.R
import com.sanxingrenge.benben.constant.*
import com.sanxingrenge.benben.constant.Constns.flow
import com.sanxingrenge.benben.dataModel.UserDataObject
import com.sanxingrenge.benben.screens.Route
import com.sanxingrenge.benben.ui.theme.*
import com.sanxingrenge.benben.uiComponents.*
import com.sanxingrenge.benben.viewModel.RegisterViewModel
import kotlinx.coroutines.launch


private const val TAG = "RegisterScreen"

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun RegisterScreen(
    topEmail: String = "",
    topPwd: String = "",
    openScreen: ((String) -> Unit)? = null, popBack: (() -> Unit)? = null,
    openImagePicker: (() -> Unit)? = null, showLoading: ((Boolean) -> Unit)? = null,
    viewModel: RegisterViewModel? = null, openLocationPicker: (() -> Unit)?=null
) {
    val liveUserData = viewModel?.podcastsLive?.collectAsState()?.value
    viewModel?.updateUserData(
        liveUserData?.copy(
            email = topEmail,
            pwd = topPwd,
        )
    )

    val bottomSheetType = remember { mutableStateOf(BottomSheetType.Birthdate) }

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState, sheetContent = {
            BottomSheetDesign(
                bottomSheetType = bottomSheetType,
                bottomSheetScaffoldState = bottomSheetScaffoldState,
                userData = liveUserData ?: UserDataObject()
            ) {
                viewModel?.updateUserData(it)
            }
        }, sheetPeekHeight = 0.dp, sheetShape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Log.d(TAG, "RegisterScreen: testRERUNN>>${liveUserData?.bday}")
            ThisScreen(bottomSheetScaffoldState = bottomSheetScaffoldState,
                bottomSheetType = bottomSheetType,
                openScreen = openScreen,
                popBack = popBack,
                userData = liveUserData ?: UserDataObject(),
                onUpdated = {
                    viewModel?.updateUserData(it)
                }, openLocationPicker = openLocationPicker,
                showLoading = showLoading,
                openImagePicker = {
                    openImagePicker?.invoke()
                })
            FadingViewForPopup(bottomSheetScaffoldState)
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BottomSheetDesign(
    bottomSheetType: MutableState<BottomSheetType>,

    bottomSheetScaffoldState: BottomSheetScaffoldState,
    userData: UserDataObject,
    onUpdated: (UserDataObject) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    when (bottomSheetType.value) {
        BottomSheetType.Birthdate -> {
            BirthdatePicker(userData.bday ?: "") { data,month,day,year ->
                onUpdated.invoke(userData.copy(bday = data))
                coroutineScope.launch {
                    if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }
            }
        }
        BottomSheetType.Country -> {
            CustomListItemPicker(
                "",
                "Country",
                MainCountryList.map { it.name ?: "" }) { item ->
                coroutineScope.launch {
                    if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }
            }
        }
        BottomSheetType.Religion -> {
            CustomListItemPicker(
                userData.religion ?: "",
                "Religion",
                MainReligionList.map { it.name ?: "" }) { item ->

                onUpdated.invoke(userData.copy(religion = item))

                coroutineScope.launch {
                    if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }
            }
        }
        BottomSheetType.Education -> {
            CustomListItemPicker(
                userData.education ?: "",
                "Education",
                MainEducationList.map { it.name ?: "" }) { item ->
                onUpdated.invoke(userData.copy(education = item))


                coroutineScope.launch {
                    if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }
            }
        }
        BottomSheetType.Relationship -> {
            CustomListItemPicker(
                userData.relationship ?: "",
                "Relationship",
                MainRelationList.map { it.name ?: "" }) { item ->
                onUpdated.invoke(userData.copy(relationship = item))


                coroutineScope.launch {
                    if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }
            }
        }
        BottomSheetType.MaritalStatus -> {
            CustomListItemPicker(userData.maritalStatus ?: "",
                "Marital status",
                MainMaritalList.map { it.name ?: "" }) { item ->

                onUpdated.invoke(userData.copy(maritalStatus = item))

                coroutineScope.launch {
                    if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }
            }
        }
        BottomSheetType.Kids -> {
            CustomListItemPicker(
                userData.kids ?: "",
                "Kids",
                MainKidsList.map { it.name ?: "" }) { item ->

                onUpdated.invoke(userData.copy(kids = item))

                coroutineScope.launch {
                    if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }
            }
        }

        BottomSheetType.zodiac -> {
            CustomListItemPicker(
                userData.zodiacSign ?: "",
                "Zodiac",
                ZodiacList.map { it.title ?: "" }) { item ->

                onUpdated.invoke(userData.copy(zodiacSign = item))

                coroutineScope.launch {
                    if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }
            }
        }


        BottomSheetType.animal -> {
            CustomListItemPicker(
                userData.animalSign ?: "",
                "Animal",
                AnimalList.map { it.title ?: "" }) { item ->

                onUpdated.invoke(userData.copy(animalSign = item))

                coroutineScope.launch {
                    if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }
            }
        }
        BottomSheetType.Gender -> Unit
        BottomSheetType.HeightWeight -> Unit
        else -> Unit
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun FadingViewForPopup(bottomSheetScaffoldState: BottomSheetScaffoldState) {
    val coroutineScope = rememberCoroutineScope()
    AnimatedVisibility(
        visible = bottomSheetScaffoldState.bottomSheetState.isExpanded,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(modifier = Modifier
            .background(SemiTransparent)
            .fillMaxSize()
            .clickable {
                coroutineScope.launch {
                    if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }
            }) {}
    }
}

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
private fun ThisScreen(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    bottomSheetType: MutableState<BottomSheetType>,
    viewModel: RegisterViewModel = hiltViewModel(),
    openScreen: ((String) -> Unit)? = null,
    userData: UserDataObject,
    onUpdated: (UserDataObject) -> Unit,
    openImagePicker: () -> Unit,
    showLoading: ((Boolean) -> Unit)? = null,
    popBack: (() -> Unit)? = null,
    openLocationPicker: (() -> Unit)?=null
) {
    val context = LocalContext.current
    val screenState = viewModel.uiState.collectAsState().value


    Log.d(TAG, "ThisScreen: testNEWRUN>>255>>${screenState}")
    BackHandler {
        when (screenState) {
            ScreenStateRegisterScreen.CreateYourProfile -> popBack?.invoke()
            ScreenStateRegisterScreen.GenderSelect -> viewModel.updateUiState(
                ScreenStateRegisterScreen.CreateYourProfile
            )
            ScreenStateRegisterScreen.WorkLife -> viewModel.updateUiState(ScreenStateRegisterScreen.GenderSelect)
            ScreenStateRegisterScreen.BasicHeightWeight -> viewModel.updateUiState(
                ScreenStateRegisterScreen.WorkLife
            )
            ScreenStateRegisterScreen.DescribeYourself -> viewModel.updateUiState(
                ScreenStateRegisterScreen.BasicHeightWeight
            )
            ScreenStateRegisterScreen.AddPhoto -> viewModel.updateUiState(ScreenStateRegisterScreen.DescribeYourself)
            ScreenStateRegisterScreen.SelectYourInterestParent -> viewModel.updateUiState(
                ScreenStateRegisterScreen.AddPhoto
            )
            ScreenStateRegisterScreen.SelectYourInterestChild -> viewModel.updateUiState(
                ScreenStateRegisterScreen.SelectYourInterestParent
            )
        }
    }
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
            AddSpace(size = 20.dp)
            Box(Modifier.width(320.dp)) {
                IconButton(onClick = {
                    //back button
                    when (screenState) {
                        ScreenStateRegisterScreen.CreateYourProfile -> popBack?.invoke()
                        ScreenStateRegisterScreen.GenderSelect -> viewModel.updateUiState(
                            ScreenStateRegisterScreen.CreateYourProfile
                        )
                        ScreenStateRegisterScreen.WorkLife -> viewModel.updateUiState(
                            ScreenStateRegisterScreen.GenderSelect
                        )
                        ScreenStateRegisterScreen.BasicHeightWeight -> viewModel.updateUiState(
                            ScreenStateRegisterScreen.WorkLife
                        )
                        ScreenStateRegisterScreen.DescribeYourself -> viewModel.updateUiState(
                            ScreenStateRegisterScreen.BasicHeightWeight
                        )
                        ScreenStateRegisterScreen.AddPhoto -> viewModel.updateUiState(
                            ScreenStateRegisterScreen.DescribeYourself
                        )
                        ScreenStateRegisterScreen.SelectYourInterestParent -> viewModel.updateUiState(
                            ScreenStateRegisterScreen.AddPhoto
                        )
                        ScreenStateRegisterScreen.SelectYourInterestChild -> viewModel.updateUiState(
                            ScreenStateRegisterScreen.SelectYourInterestParent
                        )
                    }
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.back_arrow),
                        contentDescription = "",
                        modifier = Modifier.size(40.dp),
                    )
                }
            }
            AddSpace(size = 11.dp)

            val showAllError = remember { mutableStateOf(false) }

            Box {
                androidx.compose.animation.AnimatedVisibility(
                    visible = screenState == ScreenStateRegisterScreen.CreateYourProfile,
                    enter = slideInEnterAnimation(),
                    exit = slideInExitAnimation()
                ) {

                    CreateProfileFlow(
                        bottomSheetScaffoldState = bottomSheetScaffoldState,
                        bottomSheetType = bottomSheetType,
                        showAllError = showAllError,
                        liveUserData = userData,
                        viewModel=viewModel,
                        openLocationPicker = openLocationPicker,
                        onUpdated=onUpdated
                    )
                }

                androidx.compose.animation.AnimatedVisibility(
                    visible = screenState == ScreenStateRegisterScreen.GenderSelect,
                    enter = slideInEnterAnimation(),
                    exit = slideInExitAnimation()
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        GenderSelectDesign(userData.gender ?: "", onUpdated = { item ->
                            onUpdated.invoke(userData.copy(gender = item))
                        })
                    }
                }
                androidx.compose.animation.AnimatedVisibility(
                    visible = screenState == ScreenStateRegisterScreen.WorkLife,
                    enter = slideInEnterAnimation(),
                    exit = slideInExitAnimation()
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        WorkLifeDesign(bottomSheetScaffoldState = bottomSheetScaffoldState,
                            bottomSheetType = bottomSheetType,
                            showAllError = showAllError,
                            liveUserData = userData,
                            onUpdated = { data ->
                                if (!data.education.isNullOrEmpty()) onUpdated.invoke(
                                    userData.copy(
                                        education = data.education,
                                        educationIsError = data.educationIsError
                                    )
                                )
                                if (!data.occupation.isNullOrEmpty()) onUpdated.invoke(
                                    userData.copy(
                                        occupation = data.occupation,
                                        occupationIsError = data.occupationIsError
                                    )
                                )
                            })
                    }
                }
                androidx.compose.animation.AnimatedVisibility(
                    visible = screenState == ScreenStateRegisterScreen.BasicHeightWeight,
                    enter = slideInEnterAnimation(),
                    exit = slideInExitAnimation()
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        WelcomeText("Basic about you")
                        AddSpace(size = 60.dp)
                        CustomHeightSelector {
                            Log.d(TAG, "ThisScreen: height>>$it")
                            onUpdated.invoke(userData.copy(height = it))
                        }
                        AddSpace(size = 60.dp)
                        CustomWeightPicker {
                            onUpdated.invoke(userData.copy(weight = it))
                        }
                        AddSpace(size = 80.dp)
                    }
                }
                androidx.compose.animation.AnimatedVisibility(
                    visible = screenState == ScreenStateRegisterScreen.DescribeYourself,
                    enter = slideInEnterAnimation(),
                    exit = slideInExitAnimation()
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        DescribeYourselfDesign(bottomSheetScaffoldState = bottomSheetScaffoldState,
                            bottomSheetType = bottomSheetType,
                            showAllError = showAllError,
                            liveUserData = userData,
                            onUpdated = {
                                onUpdated.invoke(it)
                            })
                    }
                }
                androidx.compose.animation.AnimatedVisibility(
                    visible = screenState == ScreenStateRegisterScreen.AddPhoto,
                    enter = slideInEnterAnimation(),
                    exit = slideInExitAnimation()
                ) {
                    ImagePickerDesign { openImagePicker.invoke() }
                }

                androidx.compose.animation.AnimatedVisibility(
                    visible = screenState == ScreenStateRegisterScreen.SelectYourInterestParent,
                    enter = slideInEnterAnimation(),
                    exit = slideInExitAnimation()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        WelcomeText("Select your Interests")
                        AddSpace(size = 60.dp)
                        InterestGridLayout(MainInterestLevelList.map {
                            InterestGridItem(it.name ?: "", it.image ?: "")
                        }, 4, clickable = true, openPopupOnClick = {

                            viewModel.updateInterestList(it, context)

                        })

                        val tempError = remember {
                            mutableStateOf("")
                        }
                        AnimatedContent(targetState = tempError.value) { target ->
                            Text(
                                text = target,
                                color = RichCarmine,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                        LaunchedEffect(key1 = true, block = {
                            tempError.value = "Please select at least one interest."
                        })

                        AddSpace(size = 40.dp)
                    }
                }
                androidx.compose.animation.AnimatedVisibility(
                    visible = screenState == ScreenStateRegisterScreen.SelectYourInterestChild,
                    enter = slideInEnterAnimation(),
                    exit = slideInExitAnimation()
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        WelcomeText("Set interests level")
                        AddSpace(size = 40.dp)

                        /*mutableStateOf(arrayListOf(InterestChildListItem(
                                    name = "Alpha",
                                            imageResource = "",
                                            childNameList = listOf(
                                                InterestNestedItem("Beginner Beginner "),
                                                InterestNestedItem("Intermediate Intermediate "),
                                                InterestNestedItem("Master Master "),
                                            ),
                                            childSelectedName="Intermediate",
                                )))*/

                        val interestList: MutableState<ArrayList<InterestChildListItem>?> =
                            remember {
                                mutableStateOf( null)
                            }
                        viewModel.getSelectedInterestList(context) {
                            interestList.value = it
                        }

                        interestList.value?.let {
                            InterestChildListLayout(it, list = emptyList()) {
                                viewModel.updateChildInterestLevel(it, context)
                            }
                        }

                        val tempError = remember {
                            mutableStateOf("")
                        }
                        AnimatedContent(targetState = tempError.value) { target ->
                            Text(
                                text = target,
                                color = RichCarmine,
                                modifier = Modifier
                                    .padding(bottom = 20.dp)
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                        LaunchedEffect(key1 = true, block = {
                            tempError.value = "Please select level for all interests."
                        })
                    }
                }

            }

            val locationData = remember {
                mutableStateOf("")
            }
            viewModel.getLocationSelected(context){
                locationData.value = it.name
            }

            SolidButton("Continue", poppinsMedium, onclick = {

                //if (userData == null) showAllError.value = true
                when (screenState) {
                    ScreenStateRegisterScreen.CreateYourProfile -> {

                        if(locationData.value=="") {
                            showAllError.value = true
                        }else if (userData.nameIsError == false && userData.bdayIsError == false && userData.religionIsError == false && userData.name != "" && userData.bday != "" && userData.religion != "") {
                            showAllError.value = false
                            viewModel.updateUiState(ScreenStateRegisterScreen.GenderSelect)
                            viewModel.updateLocationToUserObject(context)
                        } else {
                            showAllError.value = true
                        }
                    }
                    ScreenStateRegisterScreen.GenderSelect -> viewModel.updateUiState(
                        ScreenStateRegisterScreen.WorkLife
                    )
                    ScreenStateRegisterScreen.WorkLife -> {
                        if (userData.educationIsError == false && userData.occupationIsError == false && userData.education != "" && userData.occupation != "") {
                            showAllError.value = false
                            viewModel.updateUiState(ScreenStateRegisterScreen.BasicHeightWeight)
                        } else {
                            showAllError.value = true
                        }
                    }
                    ScreenStateRegisterScreen.BasicHeightWeight -> viewModel.updateUiState(
                        ScreenStateRegisterScreen.DescribeYourself
                    )
                    ScreenStateRegisterScreen.DescribeYourself -> {
                        if (userData.relationshipIsError == false && userData.maritalStatusIsError == false && userData.kidsIsError == false && userData.zodiacSignError == false && userData.animalSignError == false && userData.relationship != "" && userData.maritalStatus != "" && userData.kids != "" && userData.zodiacSign != "" && userData.animalSign != "") {
                            showAllError.value = false
                            viewModel.updateUiState(ScreenStateRegisterScreen.AddPhoto)
                        } else {
                            showAllError.value = true
                        }

                    }
                    ScreenStateRegisterScreen.AddPhoto ->{
                        if (userData.imagePath != "" && userData.imageUrl != "" ) {
                            showAllError.value = false
                            viewModel.updateUiState(ScreenStateRegisterScreen.SelectYourInterestParent)
                        } else {
                            showAllError.value = true
                        }
                    }
                    ScreenStateRegisterScreen.SelectYourInterestParent -> {

                        Log.d(TAG, "ThisScreen: testAllData>>count=" + userData.count)

                        viewModel.updateUiState(ScreenStateRegisterScreen.SelectYourInterestChild)
                    }
                    ScreenStateRegisterScreen.SelectYourInterestChild -> {

                        if (viewModel.isChildListDataValid) {
                            viewModel.saveLocally(context)
                            showLoading?.invoke(true)


                            viewModel.sendEmailOtp(context,"","") { status: Boolean, msg: String, otp: String ->
                                showLoading?.invoke(false)
                                if (status) {
                                    Log.d(TAG, "ThisScreen435: "+msg)
                                    if(msg=="Registration otp is sent successfully"){
                                        context.showToast("Registration OTP is Sent Successfully")
                                    }
                                   // context.showToast(otp)
                                    openScreen?.invoke(Route.SIGN_IN_BASE + "/${SignInScreenState.VerifyOtp.name}")
                                } else context.showToast(msg)
                            }
                        } else {
                            context.showToast("Please Select Level Of All Items To Continue.")
                        }

                    }
                }
            })
            AddSpace(size = 20.dp)
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun CreateProfileFlow(bottomSheetScaffoldState: BottomSheetScaffoldState?=null,
                      bottomSheetType: MutableState<BottomSheetType>?=null,
                      viewModel: RegisterViewModel?=null,
                      showAllError: MutableState<Boolean>?=null,
                      liveUserData: UserDataObject?=null,
                      selectedData: ((UserDataObject) -> Unit)?=null,
                      openLocationPicker: (() -> Unit)? = null,
                      onUpdated: ((UserDataObject) -> Unit)?=null) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CreateProfileDesign(bottomSheetScaffoldState = bottomSheetScaffoldState,
            bottomSheetType = bottomSheetType,
            showAllError = showAllError,
            liveUserData = liveUserData,
            viewModel=viewModel,
            openLocationPicker = openLocationPicker,
            selectedData = { data ->
                if (!data.name.isNullOrEmpty()) onUpdated?.invoke(
                    liveUserData?.copy(
                        name = data.name, nameIsError = data.nameIsError
                    )?: UserDataObject()
                )
                if (!data.bday.isNullOrEmpty()) onUpdated?.invoke(
                    liveUserData?.copy(
                        bday = data.bday, bdayIsError = data.bdayIsError
                    )?:UserDataObject()
                )
                if (!data.religion.isNullOrEmpty()) onUpdated?.invoke(
                    liveUserData?.copy(
                        religion = data.religion,
                        religionIsError = data.religionIsError
                    )?:UserDataObject()
                )
            })
    }
}

@Composable
fun WelcomeText(title: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp),
        text = title,
        fontSize = 25.sp,
        fontFamily = poppinsBold,
        color = Color.Black,
        textAlign = TextAlign.Center
    )
}

enum class ScreenStateRegisterScreen {
    CreateYourProfile, GenderSelect, WorkLife, BasicHeightWeight, DescribeYourself, AddPhoto, SelectYourInterestParent, SelectYourInterestChild,
}

enum class BottomSheetType {
    Bio,Name, Occupation, Birthdate, Country, Religion, Education, Relationship, MaritalStatus, Kids,zodiac,animal, Gender, HeightWeight, InterestLevel,
}
