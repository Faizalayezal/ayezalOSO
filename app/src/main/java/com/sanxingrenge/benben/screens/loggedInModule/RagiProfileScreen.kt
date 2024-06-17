package com.sanxingrenge.benben.screens.loggedInModule

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sanxingrenge.benben.R
import com.sanxingrenge.benben.constant.*
import com.sanxingrenge.benben.constant.Constns.ImageShowEdit
import com.sanxingrenge.benben.dataModel.UserDataObject
import com.sanxingrenge.benben.screens.Route
import com.sanxingrenge.benben.screens.loginRegisterModule.BottomSheetType
import com.sanxingrenge.benben.ui.theme.*
import com.sanxingrenge.benben.uiComponents.*
import com.sanxingrenge.benben.viewModel.ProfileScreenViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch


data class RegiProfileListItem(
    val title: String,
    val isPopup: Boolean,
    val itemType: ListReItemType,
)

private val itemList = listOf(
    RegiProfileListItem(
        "BIO", false, ListReItemType.BIO
    ),
    RegiProfileListItem(
        "NAME", false, ListReItemType.NAME
    ),
    RegiProfileListItem(
        "BIRTHDATE", true, ListReItemType.BIRTHDATE
    ),
    RegiProfileListItem(
        "LOCATION", true, ListReItemType.COUNTRY
    ),
    RegiProfileListItem(
        "RELIGION", true, ListReItemType.RELIGION
    ),
    RegiProfileListItem(
        "GENDER", true, ListReItemType.GENDER
    ),
    RegiProfileListItem(
        "EDUCATION", true, ListReItemType.EDUCATION
    ),
    RegiProfileListItem(
        "OCCUPATION", false, ListReItemType.OCCUPATION
    ),
    RegiProfileListItem(
        "HEIGHT - WEIGHT", true, ListReItemType.HEIGHT_WEIGHT
    ),
    RegiProfileListItem(
        "MARITAL STATUS", true, ListReItemType.MARITAL_STATUS
    ),
    RegiProfileListItem(
        "LOOKING FOR", true, ListReItemType.RELATIONSHIP
    ),

    RegiProfileListItem(
        "KIDS", true, ListReItemType.KIDS
    ),
    RegiProfileListItem(
        "ZODIAC SIGN", true, ListReItemType.ZODIAC
    ),
    RegiProfileListItem(
        "ANIMAL SIGN", true, ListReItemType.ANIMAL
    ),
)

enum class ListReItemType {
    BIO,
    NAME,
    BIRTHDATE,
    COUNTRY,
    RELIGION,
    GENDER,
    EDUCATION,
    OCCUPATION,
    HEIGHT_WEIGHT,
    MARITAL_STATUS,
    RELATIONSHIP,
    KIDS,
    ZODIAC,
    ANIMAL,
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RagiProfileScreen(
    openScreen: ((String) -> Unit)? = null,
    popBack: (() -> Unit)? = null, showLoading: (Boolean) -> Unit,
    openLocationPicker: (() -> Unit)? = null,
    openImagePicker: (() -> Unit)? = null,
    openImageCameraPicker: (() -> Unit)? = null,
    // faceDatect: ((Bitmap,File) -> Unit)? = null,

) {


    val openRecoderDialog = remember {
        mutableStateOf(false)
    }
    var selectImageUris by remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current


    /* val singlePhotoPicker= rememberLauncherForActivityResult(
         contract = ActivityResultContracts.PickVisualMedia(),
         onResult ={uri ->
             selectImageUris = uri
             val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
             val newFilePath = context.filesDir.path + "/${System.currentTimeMillis()}.png"
             if (bitmap != null) {
                 try {
                     FileOutputStream(newFilePath).use { out ->
                         bitmap.compress(
                             Bitmap.CompressFormat.PNG,
                             100,
                             out
                         ) // bmp is your Bitmap instance
                     }
                 } catch (e: IOException) {
                     e.printStackTrace()
                 }

             }

             val newFile = detectFaceIn(bitmap, File(newFilePath))

             val userObj = dataStoreGetUserData().firstOrNull() ?: return@launch

             dataStoreSetUserData(userObj.copy(imagePath = newFile.toString()), "380")

         }
     )*/
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )


    var Data = remember {
        mutableStateOf(UserDataObject())
    }

    val imagePathOrUrl = remember { mutableStateOf("") }
    LaunchedEffect(key1 = true, block = {
        context.dataStoreGetUserData().collect {

            Data.value = it

        }

    })


    val bottomSheetType =
        remember { mutableStateOf(BottomSheetType.Birthdate) }

    val viewModel: ProfileScreenViewModel = hiltViewModel()

    viewModel.collectUserData(context)

    val userLiveData = viewModel.podcastsLive.collectAsState().value

    val religionName = MainReligionList.find {
        (it.id ?: 0) == (userLiveData.religion?.toIntOrNull() ?: 0)

    }?.name


    val educationName = MainEducationList.find {
        (it.id ?: 0) == (userLiveData.education?.toIntOrNull() ?: 0)

    }?.name

    val reletionShipName = MainRelationList.find {
        (it.id ?: 0) == (userLiveData.relationship?.toIntOrNull() ?: 0)

    }?.name

    val maritalStatusName = MainMaritalList.find {
        (it.id ?: 0) == (userLiveData.maritalStatus?.toIntOrNull() ?: 0)

    }?.name

    val kindName = MainKidsList.find {
        (it.id ?: 0) == (userLiveData.kids?.toIntOrNull() ?: 0)

    }?.name

    val zodiacName = ZodiacList.find {
        (it.id ?: 0) == (userLiveData.zodiacSign?.toIntOrNull() ?: 0)

    }?.title

    val animalName = AnimalList.find {
        (it.id ?: 0) == (userLiveData.animalSign?.toIntOrNull() ?: 0)

    }?.title

    val beginnerList1 = remember { mutableStateOf<ArrayList<InterestGridItem>>(arrayListOf()) }
    val intermediateList2 = remember { mutableStateOf<ArrayList<InterestGridItem>>(arrayListOf()) }
    val masterList3 = remember { mutableStateOf<ArrayList<InterestGridItem>>(arrayListOf()) }

    viewModel.getInterestList { beginnerList, intermediateList, masterList ->
        beginnerList1.value = beginnerList
        intermediateList2.value = intermediateList
        masterList3.value = masterList
    }

    Log.d("TAG", "intrest1: " + userLiveData.interestId1)
    Log.d("TAG", "intrestLavel1: " + userLiveData.interestLevel1)


    val selectedName = remember {
        mutableStateOf(if (userLiveData.name != "") userLiveData.name ?: "" else "Enter Name")
    }
    val selectedBio = remember {
        mutableStateOf(if (userLiveData.bio != "") userLiveData.bio ?: "" else "Enter Bio")
    }
    val selectedBirthdate = remember {
        mutableStateOf(
            if (userLiveData.bday != "") userLiveData.bday ?: "" else "Select Birthdate"
        )
    }
    val selectedAddress = remember {
        mutableStateOf(
            if (userLiveData.address != "") userLiveData.address ?: "" else "Select Location"
        )
    }
    val selectedReligion = remember {
        mutableStateOf(
            if (userLiveData.religion != "") religionName ?: "" else "Select Religion"
        )
    }
    val selectedGender = remember {
        mutableStateOf(
            if (userLiveData.gender != "") userLiveData.gender ?: "" else "Select Gender"
        )
    }
    val selectedEducation = remember {
        mutableStateOf(
            if (userLiveData.education != "") educationName ?: "" else "Select Education"
        )
    }
    val selectedOccupation = remember {
        mutableStateOf(
            if (userLiveData.occupation != "") userLiveData.occupation ?: "" else "Enter Occupation"
        )
    }
    val selectedHeight = remember {
        mutableStateOf(
            if (userLiveData.height != "") userLiveData.height ?: "" else "Select Height"
        )
    }
    val selectedWeight = remember {
        mutableStateOf(
            if (userLiveData.weight != "") userLiveData.weight ?: "" else "- Weight"
        )
    }

    val selectedRelationship = remember {
        mutableStateOf(
            if (userLiveData.relationship != "") reletionShipName ?: "" else "Select Looking For"
        )
    }
    val selectedMaritalStatus = remember {
        mutableStateOf(
            if (userLiveData.maritalStatus != "") maritalStatusName
                ?: "" else "Select Marital Status"
        )
    }
    val selectedKids =
        remember { mutableStateOf(if (userLiveData.kids != "") kindName ?: "" else "Select Kids") }

    val selectedZodiac = remember { mutableStateOf(zodiacName ?: "") }
    val selectedAnimal = remember { mutableStateOf(animalName ?: "") }

    Log.d("TAG", "EditProfileScreenass: " + Data.value)

    LaunchedEffect(key1 = true, block = {
        viewModel.getLocationSelected(context) {
            if (it.name.isNotEmpty() && it.lat.isNotEmpty() && it.long.isNotEmpty()) {


                val text = it.name
               /* val commaIndex = text.indexOf(',')
                val rajkotText = text.substring(0, commaIndex)
                Log.d("TAG", "EditProfileScreefghfghn:259 " + it.name)
                Log.d("TAG", "EditProfileScreefghfghn:260 " + rajkotText)*/

                selectedAddress.value = text


                coroutineScope.launch {
                    val userObj = context.dataStoreGetUserData().firstOrNull() ?: return@launch
                    context.dataStoreSetUserData(
                        userObj.copy(address = text).copy(latitude = it.lat)
                            .copy(longitude = it.long), "380"
                    )
                }

            }
        }
    })
    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {

            when (bottomSheetType.value) {
                BottomSheetType.Birthdate -> {
                    BirthdatePicker(selectedBirthdate.value) { date, month, day, year ->
                        selectedBirthdate.value = date

                        val zodicSign = Constns.getZodiacList(day.toIntOrNull() ?: 0, month)
                        val animalSign = Constns.calculateAnimalSign(year.toIntOrNull() ?: 0)


                        coroutineScope.launch {
                            val userObj =
                                context.dataStoreGetUserData().firstOrNull() ?: return@launch
                            context.dataStoreSetUserData(
                                userObj.copy(bday = selectedBirthdate.value)
                                    .copy(zodiacSign = zodicSign)
                                    .copy(animalSign = animalSign.name),
                                "380"
                            )
                        }
                        coroutineScope.launch {
                            if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }


                        }

                    }
                }

                BottomSheetType.Country -> {
                    CustomListItemPicker(
                        selectedAddress.value,
                        "Location",
                        MainCountryList.map { it.name ?: "" }) {
                        selectedAddress.value = it
                        coroutineScope.launch {
                            val userObj =
                                context.dataStoreGetUserData().firstOrNull() ?: return@launch
                            context.dataStoreSetUserData(
                                userObj.copy(address = selectedAddress.value),
                                "380"
                            )
                        }
                        coroutineScope.launch {
                            if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        }
                    }
                }

                BottomSheetType.Religion -> {
                    CustomListItemPicker(
                        selectedReligion.value,
                        "Religion",
                        MainReligionList.map { it.name ?: "" }

                    ) { name ->
                        selectedReligion.value = name

                        val religion = MainReligionList.findIdOfItem(selectedReligion.value)
                        coroutineScope.launch {
                            val userObj =
                                context.dataStoreGetUserData().firstOrNull() ?: return@launch
                            context.dataStoreSetUserData(
                                userObj.copy(religion = religion),
                                "380"
                            )
                        }

                        coroutineScope.launch {
                            if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        }


                    }
                }

                BottomSheetType.Education -> {
                    CustomListItemPicker(
                        selectedEducation.value,
                        "Education",
                        MainEducationList.map { it.name ?: "" }) {
                        selectedEducation.value = it
                        val education = MainEducationList.findIdOfItem(selectedEducation.value)

                        coroutineScope.launch {
                            val userObj =
                                context.dataStoreGetUserData().firstOrNull() ?: return@launch
                            context.dataStoreSetUserData(
                                userObj.copy(education = education),
                                "380"
                            )
                        }

                        coroutineScope.launch {
                            if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        }


                    }
                }

                BottomSheetType.MaritalStatus -> {
                    CustomListItemPicker(selectedMaritalStatus.value, "Marital status",
                        MainMaritalList.map { it.name ?: "" }
                    ) {
                        selectedMaritalStatus.value = it
                        val maritalStatus =
                            MainMaritalList.findIdOfItem(selectedMaritalStatus.value)

                        coroutineScope.launch {
                            val userObj =
                                context.dataStoreGetUserData().firstOrNull() ?: return@launch
                            context.dataStoreSetUserData(
                                userObj.copy(maritalStatus = maritalStatus),
                                "380"
                            )
                        }

                        coroutineScope.launch {
                            if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        }


                    }
                }


                BottomSheetType.Relationship -> {

                    CustomListItemPicker(
                        selectedRelationship.value,
                        "Looking For",
                        MainRelationList.map { it.name ?: "" }
                    ) {
                        selectedRelationship.value = it
                        val relation = MainRelationList.findIdOfItem(selectedRelationship.value)

                        coroutineScope.launch {
                            val userObj =
                                context.dataStoreGetUserData().firstOrNull() ?: return@launch
                            context.dataStoreSetUserData(
                                userObj.copy(relationship = relation),
                                "380"
                            )
                        }


                        coroutineScope.launch {
                            if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        }

                    }
                }


                BottomSheetType.Kids -> {
                    CustomListItemPicker(
                        selectedKids.value, "Kids",
                        MainKidsList.map { it.name ?: "" }) {
                        selectedKids.value = it

                        val kinds = MainKidsList.findIdOfItem(selectedKids.value)

                        coroutineScope.launch {
                            val userObj =
                                context.dataStoreGetUserData().firstOrNull() ?: return@launch
                            context.dataStoreSetUserData(userObj.copy(kids = kinds), "380")
                        }

                        coroutineScope.launch {
                            if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        }


                    }
                }


                BottomSheetType.Gender -> {
                    GenderPicker(selectedGender.value) {
                        selectedGender.value = it

                        coroutineScope.launch {
                            val userObj =
                                context.dataStoreGetUserData().firstOrNull() ?: return@launch
                            context.dataStoreSetUserData(
                                userObj.copy(gender = selectedGender.value),
                                "380"
                            )
                        }
                        coroutineScope.launch {
                            if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        }

                    }
                }

                BottomSheetType.HeightWeight -> {
                    HeightWeightPicker(
                        selectedWeight.value,
                        selectedHeight.value
                    ) { weight, height ->
                        selectedWeight.value = weight
                        selectedHeight.value = height

                        Log.d("TAG", "EditProfileScreeddddn: " + selectedHeight)
                        Log.d("TAG", "EditProfileScreeddddn: " + selectedWeight)
                        coroutineScope.launch {
                            val userObj =
                                context.dataStoreGetUserData().firstOrNull() ?: return@launch
                            context.dataStoreSetUserData(
                                userObj.copy(height = selectedHeight.value)
                                    .copy(weight = selectedWeight.value), "380"
                            )
                        }
                        coroutineScope.launch {
                            if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        }


                    }
                }

                BottomSheetType.InterestLevel -> {
                    val selectedInterestLevel = remember {
                        mutableStateOf("")
                    }
                    CustomListItemPicker(
                        selectedInterestLevel.value,
                        "Interest Level",
                        MainInterestLevelList.map { it.name ?: "" }
                    ) {
                        selectedInterestLevel.value = it

                        coroutineScope.launch {
                            if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        }
                        //show
                        showLoading.invoke(true)
                        viewModel.updateUserDataLocal(userLiveData.copy(name = it), context) {
                            showLoading.invoke(false)

                        }

                    }
                }

                BottomSheetType.Bio -> {

                    CustomTextInputPicker(
                        selectedBio.value, "Bio", "Bio"

                    ) {
                        Log.d("TAG", "RagiProfileScreen:604 "+it)
                        selectedBio.value = it
                        Log.d("TAG", "RagiProfileScreen:606 "+ selectedBio.value)

                        coroutineScope.launch {
                            val userObj =
                                context.dataStoreGetUserData().firstOrNull() ?: return@launch
                            context.dataStoreSetUserData(
                                userObj.copy(bio = selectedBio.value),
                                "610"
                            )
                        }
                        coroutineScope.launch {
                            if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        }


                    }
                }

                BottomSheetType.Name -> {
                    CustomTextInputPicker(
                        selectedName.value, "Name", "Name"

                    ) {
                        selectedName.value = it
                        coroutineScope.launch {
                            val userObj =
                                context.dataStoreGetUserData().firstOrNull() ?: return@launch
                            context.dataStoreSetUserData(
                                userObj.copy(name = selectedName.value),
                                "380"
                            )
                        }
                        coroutineScope.launch {
                            if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        }


                    }
                }

                BottomSheetType.Occupation -> {
                    CustomTextInputPicker(
                        selectedOccupation.value, "Occupation", "Occupation"
                    ) {
                        selectedOccupation.value = it

                        coroutineScope.launch {
                            val userObj =
                                context.dataStoreGetUserData().firstOrNull() ?: return@launch
                            context.dataStoreSetUserData(
                                userObj.copy(occupation = selectedOccupation.value),
                                "380"
                            )
                        }
                        coroutineScope.launch {
                            if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        }


                    }
                }

                else -> {}
            }

        }, sheetPeekHeight = 0.dp,
        sheetShape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp)
    ) {

        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.fillMaxSize(), content = {

                item {
                    TobBar(viewModel, popBack, imagePathOrUrl)
                }
                item {
                    //  ImageRegisterLayout(openImagePicker,singlePhotoPicker)
                    ImageRegisterLayout(openRecoderDialog)
                }
                //Common Similar items
                items(itemList.size) { pos ->

                    when (itemList[pos].itemType) {

                        ListReItemType.BIO -> {

                            EditProfileRow(
                                itemList[pos], selectedBio, null,
                                bottomSheetScaffoldState, bottomSheetType
                            )

                        }

                        ListReItemType.NAME -> {
                            EditProfileRow(
                                itemList[pos], selectedName, null,
                                bottomSheetScaffoldState, bottomSheetType
                            )
                        }

                        ListReItemType.BIRTHDATE -> {
                            EditProfileRow(
                                itemList[pos], selectedBirthdate, null,
                                bottomSheetScaffoldState, bottomSheetType
                            )
                        }

                        ListReItemType.COUNTRY -> {
                            EditProfileRow(
                                itemList[pos], selectedAddress, null,
                                bottomSheetScaffoldState, bottomSheetType,
                                openLocationPicker = openLocationPicker
                            )
                        }

                        ListReItemType.RELIGION -> {
                            EditProfileRow(
                                itemList[pos], selectedReligion, null,
                                bottomSheetScaffoldState, bottomSheetType
                            )
                        }

                        ListReItemType.GENDER -> {
                            EditProfileRow(
                                itemList[pos], selectedGender, null,
                                bottomSheetScaffoldState, bottomSheetType
                            )
                        }

                        ListReItemType.EDUCATION -> {
                            EditProfileRow(
                                itemList[pos], selectedEducation, null,
                                bottomSheetScaffoldState, bottomSheetType
                            )
                        }

                        ListReItemType.OCCUPATION -> {
                            EditProfileRow(
                                itemList[pos], selectedOccupation, null,
                                bottomSheetScaffoldState, bottomSheetType
                            )
                        }

                        ListReItemType.HEIGHT_WEIGHT -> {
                            EditProfileRow(
                                itemList[pos], selectedHeight, selectedWeight,
                                bottomSheetScaffoldState, bottomSheetType
                            )
                        }

                        ListReItemType.MARITAL_STATUS -> {
                            EditProfileRow(
                                itemList[pos], selectedMaritalStatus, null,
                                bottomSheetScaffoldState, bottomSheetType
                            )
                        }

                        ListReItemType.RELATIONSHIP -> {
                            EditProfileRow(
                                itemList[pos], selectedRelationship, null,
                                bottomSheetScaffoldState, bottomSheetType
                            )
                        }

                        ListReItemType.KIDS -> {
                            EditProfileRow(
                                itemList[pos], selectedKids, null,
                                bottomSheetScaffoldState, bottomSheetType
                            )
                        }

                        /*ListReItemType.ZODIAC -> {
                            EditProfileRow(
                                itemList[pos], selectedZodiac, null,
                                bottomSheetScaffoldState, bottomSheetType
                            )
                        }*/

                        /*ListReItemType.ANIMAL -> {
                            EditProfileRow(
                                itemList[pos], selectedAnimal, null,
                                bottomSheetScaffoldState, bottomSheetType
                            )
                        }*/
                        else -> {}
                    }

                }

                item {
                    InterestDesign(
                        bottomSheetScaffoldState, bottomSheetType, openScreen,
                        beginnerList1.value,
                        intermediateList2.value,
                        masterList3.value,
                        selectedBio.value,
                        selectedName.value,
                        selectedBirthdate.value,
                        selectedAddress.value,
                        selectedReligion.value,
                        selectedGender.value,
                        selectedEducation.value,
                        selectedOccupation.value,
                        selectedHeight.value,
                        selectedWeight.value,
                        selectedRelationship.value,
                        selectedMaritalStatus.value,
                        selectedKids.value,
                    )
                    Log.d(
                        "TAG",
                        "EditProfileScreen: " + beginnerList1.value + "111" + intermediateList2.value + "222" + masterList3.value
                    )
                }
            })

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
    }
    //  }

    if (openRecoderDialog.value) {
        Dialog(
            onDismissRequest = {
                openRecoderDialog.value = false
            }
        ) {
            ImagePopUpContent(openImagePicker, openImageCameraPicker) {
                openRecoderDialog.value = false
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun InterestDesign(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    bottomSheetType: MutableState<BottomSheetType>,
    openScreen: ((String) -> Unit)? = null,
    beginnerList1: (ArrayList<InterestGridItem>)? = null,
    intermediateList2: (ArrayList<InterestGridItem>)? = null,
    masterList3: (ArrayList<InterestGridItem>)? = null,
    selectedBio: String,
    selectedName: String,
    selectedBirthdate: String,
    selectedAddress: String,
    selectedReligion: String,
    selectedGender: String,
    selectedEducation: String,
    selectedOccupation: String,
    selectedHeight: String,
    selectedWeight: String,
    selectedRelationship: String,
    selectedMaritalStatus: String,
    selectedKids: String,
) {
    val context = LocalContext.current
    val tempObject = remember { mutableStateOf(UserDataObject()) }
    LaunchedEffect(key1 = true, block = {
        context.dataStoreGetUserData().collect {
            tempObject.value = it

        }
    })
    var count = 0
    if (tempObject.value.imageUrl!="") count += 1
    if (tempObject.value.imageUrl22!="") count += 1
    if (tempObject.value.imageUrl33!="") count += 1
    if (tempObject.value.imageUrl44!="") count += 1
    Column(
        modifier = Modifier
            .padding(top = 15.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .clickable {
                    Log.d("TAG", "InterestDesign: " + selectedName)
                    Log.d("TAG", "InterestDesign:1 " + selectedBirthdate)
                    Log.d("TAG", "InterestDesign:2 " + selectedAddress)
                    Log.d("TAG", "InterestDesign:3 " + selectedReligion)
                    Log.d("TAG", "InterestDesign:4 " + selectedGender)
                    Log.d("TAG", "InterestDesign:5 " + selectedEducation)
                    Log.d("TAG", "InterestDesign:6 " + selectedOccupation)
                    Log.d("TAG", "InterestDesign:7 " + selectedHeight)
                    Log.d("TAG", "InterestDesign:8 " + selectedWeight)
                    Log.d("TAG", "InterestDesign:9 " + selectedRelationship)
                    Log.d("TAG", "InterestDesign:910 " + count)
                    Log.d("TAG", "InterestDesign:6755 " + tempObject.value.imageUrl)
                    if (tempObject.value.imageUrl.isNullOrEmpty() &&
                        tempObject.value.imageUrl22.isNullOrEmpty() &&
                        tempObject.value.imageUrl33.isNullOrEmpty() &&
                        tempObject.value.imageUrl44.isNullOrEmpty()
                    ) {
                        context.showToast("Please Select Profile Image")

                    }/* else if (count < 3) {
                        context.showToast("Please Select Minimum 3 Images to Continue")

                    }*/ else if (selectedBio == "Enter Bio") {
                        context.showToast("Please Enter Your Bio")

                    } else if (selectedName == "Enter Name") {
                        context.showToast("Please Enter Your Name")

                    } else if (selectedBirthdate == "Select Birthdate") {
                        context.showToast("Please Select Your BirthDate")

                    } else if (selectedAddress == "Select Location") {
                        context.showToast("Please Enter Your Location")

                    } else if (selectedReligion == "Select Religion") {
                        context.showToast("Please Select Religion")

                    } else if (selectedGender == "Select Gender") {
                        context.showToast("Please Select Gender")

                    } else if (selectedEducation == "Select Education") {
                        context.showToast("Please Select Education")

                    } else if (selectedOccupation == "Enter Occupation") {
                        context.showToast("Please Enter Your Occupation")

                    } else if (selectedHeight == "Select Height") {
                        context.showToast("Please Select Height")

                    } else if (selectedWeight == "- Weight" || selectedWeight == "0kg") {
                        context.showToast("Please Select Weight")

                    } else if (selectedMaritalStatus == "Select Marital Status") {
                        context.showToast("Please Select Marital Status")

                    } else if (selectedRelationship == "Select Looking For") {
                        context.showToast("Please Select Looking For")

                    } else if (selectedKids == "Select Kids") {
                        context.showToast("Please Select Kids")
                    } else {

                        openScreen?.invoke(Route.REGISTER_INTEREST_BASE)
                    }
                }
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.weight(1f, true),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "INTERESTS",
                    fontFamily = poppinsMedium,
                    fontSize = 15.sp,
                    color = Color.Black
                )
            }
            Image(
                painter = painterResource(id = R.drawable.back_arrow),
                contentDescription = "",
                modifier = Modifier
                    .rotate(180f)
                    .size(27.dp)
            )
        }

        if (masterList3?.isEmpty() == false) {
            InterestChildItem(
                "Master", masterList3,
                bottomSheetScaffoldState, bottomSheetType
            )
        }
        if (intermediateList2?.isEmpty() == false) {
            InterestChildItem(
                "Intermediate", intermediateList2,
                bottomSheetScaffoldState, bottomSheetType
            )
        }
        if (beginnerList1?.isEmpty() == false) {
            InterestChildItem(
                "Beginner", beginnerList1,
                bottomSheetScaffoldState, bottomSheetType
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun InterestChildItem(
    title: String, intrimList: List<InterestGridItem>,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    bottomSheetType: MutableState<BottomSheetType>,
) {

    val coroutineScope = rememberCoroutineScope()
    Text(
        text = title, color = SilverChalice, fontFamily = poppinsRegular, fontSize = 13.sp,
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(top = 10.dp)
    )
    Spacer(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(top = 5.dp)
            .fillMaxWidth()
            .height(1.dp)
            .background(PearlBush)
    )
    AddSpace(size = 10.dp)
    InterestGridLayout(intrimList, 5, clickable = false, openPopupOnClick = {
        bottomSheetType.value = BottomSheetType.InterestLevel
        coroutineScope.launch {
            if (bottomSheetScaffoldState.bottomSheetState.isCollapsed)
                bottomSheetScaffoldState.bottomSheetState.expand()
            else bottomSheetScaffoldState.bottomSheetState.collapse()
        }
    })
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun EditProfileRow(
    dataItem: RegiProfileListItem,
    selectedDesc: MutableState<String>,
    selectedHight: MutableState<String>? = null,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    bottomSheetType: MutableState<BottomSheetType>,
    openLocationPicker: (() -> Unit)? = null
) {

    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp)
        .clickable {

            when (dataItem.itemType) {

                ListReItemType.BIO -> {
                    bottomSheetType.value = BottomSheetType.Bio
                    coroutineScope.launch {
                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed)
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        else bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }

                ListReItemType.NAME -> {
                    bottomSheetType.value = BottomSheetType.Name
                    coroutineScope.launch {
                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed)
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        else bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }

                ListReItemType.BIRTHDATE -> {
                    bottomSheetType.value = BottomSheetType.Birthdate
                    coroutineScope.launch {
                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed)
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        else bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }

                ListReItemType.COUNTRY -> {
                    openLocationPicker?.invoke()
                    /*bottomSheetType.value = BottomSheetType.Country
                    coroutineScope.launch {
                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed)
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        else bottomSheetScaffoldState.bottomSheetState.collapse()
                    }*/
                }

                ListReItemType.RELIGION -> {
                    bottomSheetType.value = BottomSheetType.Religion
                    coroutineScope.launch {
                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed)
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        else bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }

                ListReItemType.GENDER -> {
                    bottomSheetType.value = BottomSheetType.Gender
                    coroutineScope.launch {
                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed)
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        else bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }

                ListReItemType.EDUCATION -> {
                    bottomSheetType.value = BottomSheetType.Education
                    coroutineScope.launch {
                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed)
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        else bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }

                ListReItemType.OCCUPATION -> {
                    bottomSheetType.value = BottomSheetType.Occupation
                    coroutineScope.launch {
                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed)
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        else bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }

                ListReItemType.HEIGHT_WEIGHT -> {
                    bottomSheetType.value = BottomSheetType.HeightWeight
                    coroutineScope.launch {
                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed)
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        else bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }

                ListReItemType.RELATIONSHIP -> {
                    bottomSheetType.value = BottomSheetType.Relationship
                    coroutineScope.launch {
                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed)
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        else bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }

                ListReItemType.MARITAL_STATUS -> {
                    bottomSheetType.value = BottomSheetType.MaritalStatus
                    coroutineScope.launch {
                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed)
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        else bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }

                ListReItemType.KIDS -> {
                    bottomSheetType.value = BottomSheetType.Kids
                    coroutineScope.launch {
                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed)
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        else bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }

                /*ListItemType.ZODIAC -> {
                    bottomSheetType.value = BottomSheetType.zodiac
                    coroutineScope.launch {
                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed)
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        else bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }*/

                /* ListItemType.ANIMAL -> {
                    bottomSheetType.value = BottomSheetType.animal
                    coroutineScope.launch {
                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed)
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        else bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }*/
                else -> {}
            }


        }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.weight(1f, true),
                verticalArrangement = Arrangement.Center
            ) {
                Log.d("TAG", "EditProfileRow: " + dataItem.title)
                if (dataItem.title == "BIO") {
                    Row {
                        Text(
                            text = dataItem.title,
                            fontFamily = poppinsMedium,
                            fontSize = 15.sp,
                            color = Color.Black
                        )
                        Image(
                            painter = painterResource(id = R.drawable.editicon),
                            contentDescription = "", contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(
                                    CircleShape
                                )
                                .padding(all = 5.dp)
                                .size(12.dp)
                                .clickable {
                                }
                        )
                    }

                } else {
                    Text(
                        text = dataItem.title,
                        fontFamily = poppinsMedium,
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                }
                AddSpace(size = 0.dp)
                if (selectedHight?.value != null && selectedHight.value != "") {
                    Text(
                        text = selectedDesc.value + " / " + selectedHight.value,
                        fontFamily = poppinsRegular,
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                } else {
                    Log.d("TAG", "EditProfileRow321: " + selectedDesc.value)
                    Text(
                        text = selectedDesc.value,
                        fontFamily = poppinsRegular,
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                }
            }

            if (dataItem.isPopup) {

                Image(
                    painter = painterResource(id = R.drawable.back_arrow),
                    contentDescription = "",
                    modifier = Modifier
                        .rotate(180f)
                        .size(27.dp)
                )

            }

        }
        Log.d("TAG", "EditProfileRowddf:1229 " + dataItem)
        Log.d("TAG", "EditProfileRowddf:1230 " + selectedDesc)
        if (dataItem.itemType == ListReItemType.BIO) {

        } else {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(PearlBush)

            )
        }
    }
}

@Composable
private fun ImageRegisterLayout(
    openRecoderDialog: MutableState<Boolean>,
    //  singlePhotoPicker: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>
) {
    val isError = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val imageFromLocalPath = remember { mutableStateOf("") }

    val tempObject = remember { mutableStateOf(UserDataObject()) }
    isError.value = imageFromLocalPath.value == ""

    Log.d("TAG", "ImageRegisterLayout: " + ImageShowEdit)

    LaunchedEffect(key1 = true, block = {
        context.dataStoreGetUserData().collect {
            tempObject.value = it
            imageFromLocalPath.value = it.imagePath ?: ""
        }
    })

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp),
        /*
                    .background(Seashell),
        */
        contentAlignment = Alignment.Center
    ) {

        Column {
            Row {
                Box(
                    modifier = Modifier
                        .size(110.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(R.drawable.placeholederimg).crossfade(true).build(),
                        contentDescription = "", contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(10.dp)
                            )
                            .fillMaxSize()
                            .clickable {
                                Constns.userPos1Register = 1
                                openRecoderDialog.value = true
                            }

                    )
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(tempObject.value.imageUrl).crossfade(true).build(),
                        contentDescription = "", contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(10.dp)
                            )
                            .fillMaxSize()

                    )

                }

                AddSpace(size = 35.dp)

                Box(
                    modifier = Modifier
                        .size(110.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(R.drawable.placeholederimg).crossfade(true).build(),
                        contentDescription = "", contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(10.dp)
                            )
                            .fillMaxSize()
                            .clickable {
                                Constns.userPos1Register = 2

                                openRecoderDialog.value = true
                            }

                    )
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(tempObject.value.imageUrl22).crossfade(true).build(),
                        contentDescription = "", contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(10.dp)
                            )
                            .fillMaxSize()

                    )

                }
            }

            AddSpace(size = 35.dp)

            Row {
                Box(
                    modifier = Modifier
                        .size(110.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(R.drawable.placeholederimg).crossfade(true).build(),
                        contentDescription = "", contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(10.dp)
                            )
                            .fillMaxSize()
                            .clickable {
                                Constns.userPos1Register = 3

                                openRecoderDialog.value = true
                            }

                    )
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(tempObject.value.imageUrl33).crossfade(true).build(),
                        contentDescription = "", contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(10.dp)
                            )
                            .fillMaxSize()

                    )

                }

                AddSpace(size = 35.dp)

                Box(
                    modifier = Modifier
                        .size(110.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(R.drawable.placeholederimg).crossfade(true).build(),
                        contentDescription = "", contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(10.dp)
                            )
                            .fillMaxSize()
                            .clickable {
                                Constns.userPos1Register = 4
                                openRecoderDialog.value = true
                            }

                    )
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(tempObject.value.imageUrl44).crossfade(true).build(),
                        contentDescription = "", contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(10.dp)
                            )
                            .fillMaxSize()

                    )

                }
            }


        }






        Log.d("TAG", "ImageLayoasaut: " + imageFromLocalPath.value)


        /* Image(
             painter = painterResource(id = R.drawable.editicon),
             contentDescription = "", contentScale = ContentScale.Crop,
             modifier = Modifier
                 .clip(
                     CircleShape
                 )
                 .padding(horizontal = 15.dp, vertical = 0.dp)
                 .size(22.dp)
                 .clickable {

                     openRecoderDialog.value = true
                     // fgdg
                     // openImagePicker?.invoke()
                     *//* singlePhotoPicker.launch(
                             PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                         )*//*
                    }
            )*/

        /*showLoading.invoke(true)
        viewModel.updateUserDataLocal(userLiveData.copy(imageUrl = imagePathOrUrl.value
        viewModel.updateUserDataLocal(userLiveData.copy(religion = it), context) {
            showLoading.invoke(false)
        }*/
    }

}


@Composable
private fun TobBar(
    viewModel: ProfileScreenViewModel? = null,
    popBack: (() -> Unit)? = null,
    imagePathOrUrl: MutableState<String>
) {

    val context = LocalContext.current
    val userLiveData = viewModel?.podcastsLive?.collectAsState()?.value

    val isImagePath = (userLiveData?.imagePath ?: "") != ""

    Log.d("TAG", "ImageLayoasaut: " + imagePathOrUrl.value)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .padding(vertical = 10.dp)
            .padding(start = 10.dp, end = 15.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.back_arrow), contentDescription = "",
            modifier = Modifier
                .size(40.dp)
                .clickable { popBack?.invoke() }
        )
        AddSpace(5.dp)
        Text(
            text = "Register Profile",
            fontSize = 22.sp,
            fontFamily = poppinsSemiBold,
            color = Color.Black,
            modifier = Modifier.weight(1f, true)
        )
        /* Text(
             text = "Save",
             fontSize = 18.sp,
             fontFamily = poppinsSemiBold,
             color = Amaranth,
             modifier = Modifier.clickable {

                 if (isImagePath && userLiveData != null) {

                     viewModel.updateUserDataLocal(
                         userLiveData,
                         context
                     ) {
                         // showLoading.invoke(false)
                         popBack?.invoke()

                     }
                 }*/

        //showLoading.invoke(true)
        /*viewModel.updateUserDataLocal(userLiveData.copy(imageUrl=userLiveData.imageUrl?:""), context) {
           // showLoading.invoke(false)
            popBack?.invoke()

        }*/

        // })
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
private fun ImagePopUpContent(
    openImagePicker: (() -> Unit)? = null,
    openImageCameraPicker: (() -> Unit)? = null,
    dismissDialog: ((Boolean) -> Unit)?,
) {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(horizontal = 40.dp)
            .background(
                color = White,
                RoundedCornerShape(20.dp)
            )
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 40.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row {
            Image(
                painter = painterResource(id = R.drawable.camera),
                contentDescription = "",
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        openImageCameraPicker?.invoke()
                        dismissDialog?.invoke(true)

                    }
            )



            AddSpace(size = 30.dp)


            Image(
                painter = painterResource(id = R.drawable.gallery),
                contentDescription = "",
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        openImagePicker?.invoke()
                        dismissDialog?.invoke(true)
                    }
            )


        }


    }
}
