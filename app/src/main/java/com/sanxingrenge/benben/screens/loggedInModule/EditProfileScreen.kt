package com.sanxingrenge.benben.screens.loggedInModule

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import com.sanxingrenge.benben.constant.Constns.ImageIdRemove
import com.sanxingrenge.benben.constant.Constns.ImageShowEdit
import com.sanxingrenge.benben.constant.Constns.userPos1
import com.sanxingrenge.benben.dataModel.UserDataObject
import com.sanxingrenge.benben.screens.Route
import com.sanxingrenge.benben.screens.loginRegisterModule.BottomSheetType
import com.sanxingrenge.benben.ui.theme.*
import com.sanxingrenge.benben.uiComponents.*
import com.sanxingrenge.benben.viewModel.ProfileScreenViewModel
import kotlinx.coroutines.launch
import java.io.IOException


data class EditProfileListItem(
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
        "ADDRESS", true, ListReItemType.COUNTRY
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

enum class ListItemType {
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

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EditProfileScreen(
    openScreen: ((String) -> Unit)? = null,
    popBack: (() -> Unit)? = null, showLoading: (Boolean) -> Unit,
    openLocationPicker: (() -> Unit)? = null,
    openImagePicker: (() -> Unit)? = null,
    openImageCameraPicker: (() -> Unit)? = null,

    ) {

    val openRecoderDialog = remember {
        mutableStateOf(false)
    }
    var selectImageUris by remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )


    var Data = remember {
        mutableStateOf(UserDataObject())
    }
    var count = remember {
        mutableStateOf(0)
    }

    val imagePathOrUrl = remember { mutableStateOf("") }
    val selectedBio = remember { mutableStateOf("") }
    LaunchedEffect(key1 = true, block = {
        context.dataStoreGetUserData().collect {
            Log.d("TAG", "EditProfileScsdfsdfreen: " + it)
            selectedBio.value = it.bio ?: ""
            Data.value = it
            if (it.imageUrl != "") count.value += 1
            if (it.imageUrl22 != "") count.value += 1
            if (it.imageUrl33 != "") count.value += 1
            if (it.imageUrl44 != "") count.value += 1

        }

    })


    Log.d("TAG", "EditProfisdleScsdfsdfreen:165 " + count.value)


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


    val selectedName = remember { mutableStateOf(userLiveData.name ?: "") }
    val selectedBirthdate = remember { mutableStateOf(userLiveData.bday ?: "") }
    val selectedAddress = remember { mutableStateOf(userLiveData.address ?: "") }
    val selectedReligion = remember { mutableStateOf(religionName ?: "") }
    val selectedGender = remember { mutableStateOf(userLiveData.gender ?: "") }
    val selectedEducation = remember { mutableStateOf(educationName ?: "") }
    val selectedOccupation = remember { mutableStateOf(userLiveData.occupation ?: "") }
    val selectedHeight = remember { mutableStateOf(userLiveData.height ?: "") }
    val selectedWeight = remember { mutableStateOf(userLiveData.weight ?: "") }

    val selectedRelationship = remember { mutableStateOf(reletionShipName ?: "") }
    val selectedMaritalStatus = remember { mutableStateOf(maritalStatusName ?: "") }
    val selectedKids = remember { mutableStateOf(kindName ?: "") }

    val selectedZodiac = remember { mutableStateOf(zodiacName ?: "") }
    val selectedAnimal = remember { mutableStateOf(animalName ?: "") }

    Log.d("TAG", "EditProfileScreenass: " + Data.value)


    LaunchedEffect(key1 = true, block = {
        viewModel.getLocationSelected(context) {
            if (it.name.isNotEmpty() && it.lat.isNotEmpty() && it.long.isNotEmpty()) {

                val text = it.name

                //val commaIndex = text.indexOf(',')
                //val rajkotText = text.substring(0, commaIndex)
                //Log.d("TAG", "EditProfileScreefghfghn:259 " + it.name)
                //Log.d("TAG", "EditProfileScreefghfghn:260 " + rajkotText)

                selectedAddress.value = text

                showLoading.invoke(true)
                viewModel.updateUserDataLocal(
                    userLiveData.copy(
                        address = text,
                        latitude = it.lat,
                        longitude = it.long
                    ), context
                ) {
                    showLoading.invoke(false)
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

                        var zodicSign = Constns.getZodiacList(day.toIntOrNull() ?: 0, month)
                        var animalSign = Constns.calculateAnimalSign(year.toIntOrNull() ?: 0)


                        Log.d("TAG", "ZodiecFind: " + zodicSign)
                        Log.d("TAG", "dateOfBirth: " + date)
                        Log.d("TAG", "animalSignFind2: " + animalSign.name)
                        coroutineScope.launch {
                            if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        }
                        //show
                        showLoading.invoke(true)
                        viewModel.updateUserDataLocal(
                            userLiveData.copy(bday = date).copy(zodiacSign = zodicSign)
                                .copy(animalSign = animalSign.name),
                            context
                        ) {

                            showLoading.invoke(false)

                        }

                    }
                }

                BottomSheetType.Country -> {
                    CustomListItemPicker(
                        selectedAddress.value,
                        "Address",
                        MainCountryList.map { it.name ?: "" }) {
                        selectedAddress.value = it
                        coroutineScope.launch {
                            if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        }
                        //show
                        showLoading.invoke(true)
                        viewModel.updateUserDataLocal(
                            userLiveData.copy(address = it),
                            context
                        ) {
                            showLoading.invoke(false)
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
                            if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        }
                        //show
                        showLoading.invoke(true)
                        viewModel.updateUserDataLocal(
                            userLiveData.copy(religion = religion),
                            context
                        ) {
                            showLoading.invoke(false)
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
                            if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        }
                        //show
                        showLoading.invoke(true)
                        viewModel.updateUserDataLocal(
                            userLiveData.copy(education = education),
                            context
                        ) {
                            showLoading.invoke(false)

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
                            if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        }
                        //show
                        showLoading.invoke(true)
                        viewModel.updateUserDataLocal(
                            userLiveData.copy(maritalStatus = maritalStatus),
                            context
                        ) {
                            showLoading.invoke(false)
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
                            if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        }
                        //show
                        showLoading.invoke(true)
                        viewModel.updateUserDataLocal(
                            userLiveData.copy(relationship = relation),
                            context
                        ) {
                            showLoading.invoke(false)
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
                            if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        }
                        //show
                        showLoading.invoke(true)
                        viewModel.updateUserDataLocal(
                            userLiveData.copy(kids = kinds),
                            context
                        ) {
                            showLoading.invoke(false)

                        }

                    }
                }

                /*
                                    BottomSheetType.zodiac -> {
                                        CustomListItemPicker(
                                            selectedZodiac.value,
                                            "zodiac",
                                            ZodiacList.map { it.title ?: "" }) {
                                            selectedZodiac.value = it
                                            val zodic = ZodiacList.findIdOfItemTitle(selectedZodiac.value)


                                            coroutineScope.launch {
                                                if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                                    bottomSheetScaffoldState.bottomSheetState.collapse()
                                                }
                                            }
                                            //show
                                            showLoading.invoke(true)
                                            viewModel.updateUserDataLocal(
                                                userLiveData.copy(zodiacSign = zodic),
                                                context
                                            ) {
                                                showLoading.invoke(false)

                                            }

                                        }
                                    }
                */

                /*
                                    BottomSheetType.animal -> {
                                        CustomListItemPicker(
                                            selectedAnimal.value, "animal",
                                            AnimalList.map { it.title ?: "" }) {
                                            selectedAnimal.value = it

                                            val animal = AnimalList.findIdOfItemTitle(selectedAnimal.value)

                                            coroutineScope.launch {
                                                if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                                    bottomSheetScaffoldState.bottomSheetState.collapse()
                                                }
                                            }
                                            //show
                                            showLoading.invoke(true)
                                            viewModel.updateUserDataLocal(
                                                userLiveData.copy(animalSign = animal),
                                                context
                                            ) {
                                                showLoading.invoke(false)

                                            }

                                        }
                                    }
                */


                BottomSheetType.Gender -> {
                    GenderPicker(selectedGender.value) {
                        selectedGender.value = it
                        coroutineScope.launch {
                            if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        }
                        //show
                        showLoading.invoke(true)
                        viewModel.updateUserDataLocal(userLiveData.copy(gender = it), context) {
                            showLoading.invoke(false)

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
                            if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        }
                        //show
                        showLoading.invoke(true)
                        viewModel.updateUserDataLocal(
                            userLiveData.copy(
                                height = selectedHeight.value,
                                weight = selectedWeight.value
                            ),
                            context
                        ) {
                            showLoading.invoke(false)

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
                        selectedBio.value = it
                        coroutineScope.launch {
                            if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        }
                        //show
                        showLoading.invoke(true)
                        viewModel.updateUserDataLocal(userLiveData.copy(bio = it), context) {
                            showLoading.invoke(false)

                        }


                    }
                }

                BottomSheetType.Name -> {
                    CustomTextInputPicker(
                        selectedName.value, "name", "Name"
                    ) {
                        selectedName.value = it
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

                BottomSheetType.Occupation -> {
                    CustomTextInputPicker(
                        selectedOccupation.value, "occupation", "Occupation"
                    ) {
                        selectedOccupation.value = it
                        coroutineScope.launch {
                            if (!bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        }
                        //show
                        showLoading.invoke(true)
                        viewModel.updateUserDataLocal(
                            userLiveData.copy(occupation = it),
                            context
                        ) {
                            showLoading.invoke(false)

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
                    TobBar(viewModel, popBack, imagePathOrUrl, showLoading, userLiveData)
                }
                item {
                    //  ImageLayout(openImagePicker, imagePathOrUrl,singlePhotoPicker)
                    ImageLayout(openRecoderDialog, imagePathOrUrl)
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

                        /* ListItemType.ZODIAC -> {
                             EditProfileRow(
                                 itemList[pos], selectedZodiac, null,
                                 bottomSheetScaffoldState, bottomSheetType
                             )
                         }*/

                        /* ListItemType.ANIMAL -> {
                             EditProfileRow(
                                 itemList[pos], selectedAnimal, null,
                                 bottomSheetScaffoldState, bottomSheetType
                             )
                         }*/
                        else -> {}
                    }

                }

                item {
                    InterestEditDesign(
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
                        imagePathOrUrl.value,
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


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun InterestEditDesign(
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
    imagePath: String,
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(top = 15.dp)
            .fillMaxWidth()
    ) {
        // sdfsdf
        Row(
            modifier = Modifier
                .clickable {
                    if (ImageShowEdit == "") {
                        context.showToast("please Enter Your Image")

                    } else if (selectedBio.isEmpty()) {
                        context.showToast("please Enter Your Bio")

                    } else if (selectedName.isEmpty()) {
                        context.showToast("please Enter Your Name")

                    } else if (selectedBirthdate.isEmpty()) {
                        context.showToast("please Enter Your BirthDate")

                    } else if (selectedAddress.isEmpty()) {
                        context.showToast("please Enter Your Address")

                    } else if (selectedReligion.isEmpty()) {
                        context.showToast("please Enter Your Religion")

                    } else if (selectedGender.isEmpty()) {
                        context.showToast("Please Enter Your Gender")

                    } else if (selectedEducation.isEmpty()) {
                        context.showToast("Please Enter Your Education")

                    } else if (selectedOccupation.isEmpty()) {
                        context.showToast("Please Enter Your Occupation")

                    } else if (selectedHeight.isEmpty()) {
                        context.showToast("Please Enter Your Height")

                    } else if (selectedWeight.isEmpty()) {
                        context.showToast("Please Enter Your Weight")

                    } else if (selectedRelationship.isEmpty()) {
                        context.showToast("Please Enter Your Relationship")

                    } else if (selectedMaritalStatus.isEmpty()) {
                        context.showToast("Please Enter Your MaritalStatus")

                    } else if (selectedKids.isEmpty()) {
                        context.showToast("Please Enter Your Kids")
                    } else {
                        openScreen?.invoke(Route.EDIT_INTEREST_BASE)
                    }
                }
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // Box(modifier = Modifier.weight(1f, true)) {
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

            /* Pulsating {
                 Surface(
                     color = themtransperent,
                     shape = RoundedCornerShape(5.dp),
                     modifier = Modifier
                         .size(height = 60.dp, width = 440.dp)
                         .padding(bottom = 20.dp)
                         .padding(horizontal = 20.dp),
                     content = {

                     }
                 )
             }*/
            //  }


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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun ImageLayout(
    openRecoderDialog: MutableState<Boolean>,
    imagePathOrUrl: MutableState<String>,

    ) {
    val isError = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val viewModel: ProfileScreenViewModel = hiltViewModel()
    val userLiveData = viewModel.podcastsLive.collectAsState().value
    val tempObject = remember { mutableStateOf(UserDataObject()) }

    isError.value = imagePathOrUrl.value == ""
    var count = remember {
        mutableStateOf(0)
    }

    LaunchedEffect(key1 = true, block = {
        context.dataStoreGetUserData().collect {

            tempObject.value = it
            if ((it.imagePath ?: "") == "") {
                imagePathOrUrl.value = it.imageUrl ?: ""
            } else {
                imagePathOrUrl.value = it.imagePath ?: ""
            }

        }

    })


    Log.d("TAG", "ImageLayout1253: " + count.value)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(310.dp),
        /*.background(Seashell),*/
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

                    )
                    Log.d("TAG", "ImaddddgeLayout:1294 " + userLiveData.imageUrl)
                    Log.d("TAG", "ImaddddgeLayout:12978 " + tempObject.value.imageUrl)
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(tempObject.value.imageUrl).crossfade(true).build(),
                        contentDescription = "", contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(10.dp)
                            )
                            .fillMaxSize()
                            .clickable {
                                userPos1 = 1
                                ImageIdRemove = tempObject.value.imageId ?: ""
                                openRecoderDialog.value = true
                            }

                    )
                    Log.d("TAG", "ImageLayoutsfsd:1318 " + tempObject.value)
                    if (tempObject.value.imageUrl.isNullOrEmpty()) {

                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.closeimg2),
                            contentDescription = "",
                            modifier = Modifier
                                .size(23.dp)
                                .align(Alignment.BottomEnd)
                                .clickable {

                                    if (tempObject.value.imageUrl22.isNullOrEmpty() && tempObject.value.imageUrl33.isNullOrEmpty() || tempObject.value.imageUrl44.isNullOrEmpty()) {
                                        context.showToast("Minimum 1 images is required")
                                    } else {
                                        userPos1 = 1
                                        viewModel.removeImg(tempObject.value.imageId ?: "")
                                        when (userPos1) {
                                            1 -> tempObject.value.imageUrl = ""
                                            2 -> tempObject.value.imageUrl22 = ""
                                            3 -> tempObject.value.imageUrl33 = ""
                                            4 -> tempObject.value.imageUrl44 = ""

                                        }
                                        viewModel.swapNextImageViews(userPos1, context)

                                    }
                                    if (tempObject.value.imageId != null && tempObject.value.imageId22 != null && tempObject.value.imageId33 != null && tempObject.value.imageId44.isNullOrEmpty()) {
                                        context.showToast("Save to remove image")
                                    }
                                    /*if(tempObject.value.imageId44!=null)  {
                                        context.showToast("image remove")

                                         userPos1 = 1
                                         viewModel.removeImg(tempObject.value.imageId ?: "")
                                         when (userPos1) {
                                             1 -> tempObject.value.imageUrl = ""
                                             2 -> tempObject.value.imageUrl22 = ""
                                             3 -> tempObject.value.imageUrl33 = ""
                                             4 -> tempObject.value.imageUrl44 = ""

                                         }
                                         viewModel.swapNextImageViews(userPos1, context)
                                    }*/


                                }
                        )
                    }


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
                            .clickable {
                                userPos1 = 2
                                ImageIdRemove = tempObject.value.imageId22 ?: ""
                                openRecoderDialog.value = true
                            }

                    )
                    if (tempObject.value.imageUrl22.isNullOrEmpty()) {

                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.closeimg2),
                            contentDescription = "",
                            modifier = Modifier
                                .size(23.dp)
                                .align(Alignment.BottomEnd)
                                .clickable {
                                    userPos1 = 2

                                    Log.d("TAG", "ImageLayout:1395 " + tempObject.value.imageId)
                                    Log.d("TAG", "ImageLayout:1396 " + tempObject.value.imageId33)
                                    Log.d("TAG", "ImageLayout:1397 " + tempObject.value.imageId44)
                                    //   if (tempObject.value.imageUrl.isNullOrEmpty() && tempObject.value.imageUrl33.isNullOrEmpty() || tempObject.value.imageUrl44.isNullOrEmpty()) {
                                    //      context.showToast("Minimum 3 images is required")
                                    //  } else {
                                    viewModel.removeImg(tempObject.value.imageId22 ?: "")
                                    when (userPos1) {
                                        1 -> tempObject.value.imageUrl = ""
                                        2 -> tempObject.value.imageUrl22 = ""
                                        3 -> tempObject.value.imageUrl33 = ""
                                        4 -> tempObject.value.imageUrl44 = ""

                                    }
                                    viewModel.swapNextImageViews(userPos1, context)

                                    // }
                                    /* if (tempObject.value.imageId != null && tempObject.value.imageId33 != null && tempObject.value.imageId44.isNullOrEmpty()) {
                                         context.showToast("Save to remove image")
                                     }*/
                                }
                        )
                    }


                }


            }

            AddSpace(size = 30.dp)

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
                            .clickable {
                                userPos1 = 3
                                ImageIdRemove = tempObject.value.imageId33 ?: ""

                                openRecoderDialog.value = true
                            }

                    )

                    if (tempObject.value.imageUrl33.isNullOrEmpty()) {

                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.closeimg2),
                            contentDescription = "",
                            modifier = Modifier
                                .size(23.dp)
                                .align(Alignment.BottomEnd)
                                .clickable {
                                    userPos1 = 3

                                    Log.d("TAG", "ImageLayout1481: " + tempObject.value.imageId)
                                    Log.d("TAG", "ImageLayout1482: " + tempObject.value.imageId22)
                                    Log.d("TAG", "ImageLayout1483: " + tempObject.value.imageId44)
                                    /* if (tempObject.value.imageUrl.isNullOrEmpty() && tempObject.value.imageUrl22.isNullOrEmpty() || tempObject.value.imageUrl44.isNullOrEmpty()) {
                                         context.showToast("Minimum 3 images is required")
                                     } else if (tempObject.value.imageId != null && tempObject.value.imageId22 != null || tempObject.value.imageId44 == null) {
                                         context.showToast("Save to remove image")
                                     } */
                                    //else {
                                    viewModel.removeImg(tempObject.value.imageId33 ?: "")
                                    when (userPos1) {
                                        1 -> tempObject.value.imageUrl = ""
                                        2 -> tempObject.value.imageUrl22 = ""
                                        3 -> tempObject.value.imageUrl33 = ""
                                        4 -> tempObject.value.imageUrl44 = ""

                                    }
                                    viewModel.swapNextImageViews(userPos1, context)

                                    //  }

                                }
                        )
                    }


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
                            .clickable {
                                userPos1 = 4
                                Log.d("TAG", "ImageLayout: " + tempObject.value.imageId44)
                                ImageIdRemove = tempObject.value.imageId44 ?: ""
                                openRecoderDialog.value = true
                            }

                    )


                    if (tempObject.value.imageUrl44.isNullOrEmpty()) {

                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.closeimg2),
                            contentDescription = "",
                            modifier = Modifier
                                .size(23.dp)
                                .align(Alignment.BottomEnd)
                                .clickable {
                                    userPos1 = 4

                                    /* if (tempObject.value.imageUrl.isNullOrEmpty() && tempObject.value.imageUrl22.isNullOrEmpty() || tempObject.value.imageUrl33.isNullOrEmpty()) {
                                         context.showToast("Minimum 3 images is required")
                                     } else if (tempObject.value.imageId != null && tempObject.value.imageId22 != null && tempObject.value.imageId33 == null) {
                                         context.showToast("Save to remove image")
                                     }*/
                                    //else {
                                    viewModel.removeImg(tempObject.value.imageId44 ?: "")
                                    when (userPos1) {
                                        1 -> tempObject.value.imageUrl = ""
                                        2 -> tempObject.value.imageUrl22 = ""
                                        3 -> tempObject.value.imageUrl33 = ""
                                        4 -> tempObject.value.imageUrl44 = ""

                                    }
                                    viewModel.swapNextImageViews(userPos1, context)


                                    // }

                                }
                        )
                    }


                }

            }


        }

        /*  Box(
              modifier = Modifier.size(110.dp),
              contentAlignment = Alignment.BottomEnd
          ) {
              AsyncImage(
                  model = ImageRequest.Builder(LocalContext.current)
                      .data(R.drawable.placeholder).crossfade(true).build(),
                  contentDescription = "", contentScale = ContentScale.Crop,
                  modifier = Modifier
                      .clip(
                          CircleShape
                      )
                      .fillMaxSize()

              )
              AsyncImage(
                  model = ImageRequest.Builder(LocalContext.current)
                      .data(Constns.ImageShowEdit).crossfade(true).build(),
                  contentDescription = "", contentScale = ContentScale.Crop,
                  modifier = Modifier
                      .clip(
                          CircleShape
                      )
                      .fillMaxSize()
              )
              Log.d("TAG", "ImageLayoasaut: " + imagePathOrUrl.value)
              Image(
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
                          *//* singlePhotoPicker.launch(
                             PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                         )*//*
                    }
            )

            *//*showLoading.invoke(true)
            viewModel.updateUserDataLocal(userLiveData.copy(imageUrl = imagePathOrUrl.value
            viewModel.updateUserDataLocal(userLiveData.copy(religion = it), context) {
                showLoading.invoke(false)
            }*//*
        }*/

    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun TobBar(
    viewModel: ProfileScreenViewModel? = null,
    popBack: (() -> Unit)? = null,
    imagePathOrUrl: MutableState<String>,
    showLoading: (Boolean) -> Unit,
    userLiveDatas: UserDataObject
) {

    val context = LocalContext.current
    val userLiveData = viewModel?.podcastsLive?.collectAsState()?.value
    val tempObject = remember { mutableStateOf(UserDataObject()) }


    Log.d("TAG", "ImageLayoasaut213:1600 " + tempObject)

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
            text = "Edit Profile",
            fontSize = 22.sp,
            fontFamily = poppinsSemiBold,
            color = Color.Black,
            modifier = Modifier.weight(1f, true)
        )
        Text(
            text = "Save",
            fontSize = 18.sp,
            fontFamily = poppinsSemiBold,
            color = Amaranth,
            modifier = Modifier.clickable {


                if (userLiveData != null) {
                    showLoading.invoke(true)
                    viewModel.updateImagesUserDataLocal(
                        userLiveDatas.copy(imagePath = tempObject.value.imagePath ?: "")
                            .copy(imageUrl = tempObject.value.imageUrl ?: "")
                            .copy(imagePath22 = tempObject.value.imagePath22 ?: "")
                            .copy(imageUrl22 = tempObject.value.imageUrl22 ?: "")
                            .copy(imagePath33 = tempObject.value.imagePath33 ?: "")
                            .copy(imageUrl33 = tempObject.value.imageUrl33 ?: "")
                            .copy(imagePath44 = tempObject.value.imagePath44 ?: "")
                            .copy(imageUrl44 = tempObject.value.imageUrl44 ?: ""),
                        context
                    ) {

                        showLoading.invoke(false)
                        popBack?.invoke()
                    }
                }

            })

        //showLoading.invoke(true)
        /*viewModel.updateUserDataLocal(userLiveData.copy(imageUrl=userLiveData.imageUrl?:""), context) {
           // showLoading.invoke(false)
            popBack?.invoke()

        }*/

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


//heighlight mate
/*@Composable
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
}*/

