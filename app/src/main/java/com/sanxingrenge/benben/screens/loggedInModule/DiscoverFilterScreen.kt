package com.sanxingrenge.benben.screens.loggedInModule

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sanxingrenge.benben.R
import com.sanxingrenge.benben.constant.Constns.IsFilter
import com.sanxingrenge.benben.constant.showToast
import com.sanxingrenge.benben.dataModel.FilterDataObject
import com.sanxingrenge.benben.screens.Route
import com.sanxingrenge.benben.screens.zodiacSignModule.RecommendnedMainList
import com.sanxingrenge.benben.ui.theme.PearlBush
import com.sanxingrenge.benben.ui.theme.Seashell
import com.sanxingrenge.benben.ui.theme.SilverChalice
import com.sanxingrenge.benben.ui.theme.VioletBlue
import com.sanxingrenge.benben.ui.theme.poppinsBold
import com.sanxingrenge.benben.ui.theme.poppinsLight
import com.sanxingrenge.benben.ui.theme.poppinsMedium
import com.sanxingrenge.benben.ui.theme.poppinsRegular
import com.sanxingrenge.benben.ui.theme.poppinsSemiBold
import com.sanxingrenge.benben.uiComponents.AddSpace
import com.sanxingrenge.benben.uiComponents.InterestFilterChildListItem
import com.sanxingrenge.benben.uiComponents.InterestFilterChildListLayout
import com.sanxingrenge.benben.viewModel.FilterViewModel


@Composable
fun DiscoverFilterScreen(
    popBack: (() -> Unit)? = null,
    openScreen: ((String) -> Unit)? = null,
    showLoading: (Boolean) -> Unit,
    openLocationPicker: (() -> Unit)? = null
) {
    val viewModel: FilterViewModel = hiltViewModel()
    val context = LocalContext.current
    viewModel.collectUserData(context)

    val userLiveData = viewModel.podcastsLive.collectAsState().value
    val Uid = remember { mutableStateOf(0) }
    var filterDataObject: FilterDataObject? = null

    Log.d("TAG", "DiscoverFilterScreen: " + userLiveData)

    LaunchedEffect(key1 = true, block = {
        viewModel.getLocationSelected(context) {
            Log.d("TAG", "DiscoverFilterScreen:79 " + it)

            if (it.name.isNotEmpty() && it.lat.isNotEmpty() && it.long.isNotEmpty()) {

                Log.d("TAG", "DiscoverFilterScreen:83 " + it)

                // showLoading.invoke(true)
                viewModel.updateUserDataLocal(
                    userLiveData.copy(
                        address = it.name,
                        latitude = it.lat,
                        longitude = it.long
                    ), context
                ) {
                    // showLoading.invoke(false)
                }
            }
        }


    })


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            MainTitle(showLoading, popBack)
            TitleText("Select City")
            CitySelection(userLiveData.address, openLocationPicker)
            TitleText("Gender")
            SingleSelectorGender(listOf("Male", "Female", "N/A"))
            TitleText("Looking For")
            SingleSelectorLookingFor(listOf("Partner", "Friend", "N/A"))
            TitleText("Interests")
            AddInterestButton(openScreen)
            InterestList()
            ApplyButton(viewModel, showLoading, popBack)
        }

    }
}

@Composable
private fun CitySelection(
    selectedAddress: String?,
    openLocationPicker: (() -> Unit)? = null,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp, vertical = 15.dp)
            .clickable {
                openLocationPicker?.invoke()
                //openLocationPicker = openLocationPicker
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Log.d("TAG", "CitySelection: " + selectedAddress)
        val finalText = if (selectedAddress == "") "Select location" else selectedAddress
        Text(
            text = finalText ?: "",
            fontSize = 15.sp,
            fontFamily = poppinsSemiBold,
            color = Color.Black
        )
        Image(
            painter = painterResource(id = R.drawable.back_arrow),
            contentDescription = "", modifier = Modifier
                .size(25.dp)
                .rotate(180f)


        )
    }
}

@Composable
private fun MainTitle(
    showLoading: (Boolean) -> Unit,
    popBack: (() -> Unit)? = null
) {
    val viewModel: FilterViewModel = hiltViewModel()
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, end = 20.dp, start = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { popBack?.invoke() }, modifier = Modifier.size(50.dp)) {
            Image(
                painter = painterResource(id = R.drawable.back_arrow),
                contentDescription = "", modifier = Modifier.size(40.dp)
            )
        }
        AddSpace(5.dp)
        Text(
            text = "Filter",
            fontSize = 25.sp,
            fontFamily = poppinsBold,
            color = Color.Black
        )

        Spacer(Modifier.weight(1f))

        Text(
            text = "Reset",
            fontSize = 15.sp,
            fontFamily = poppinsBold,
            color = Color.Black,
            modifier = Modifier.clickable {
                showLoading.invoke(true)
                viewModel.userFilterApi(context) { list, status ->
                    if (status) {
                        showLoading.invoke(false)
                        viewModel.clierData(context)
                        RecommendnedMainList = if (!list.isNullOrEmpty()) list
                        else emptyList()
                        RecommendnedMainList.firstOrNull()?.isFilter = false
                        IsFilter = false
                        popBack?.invoke()


                    }


                }

            }
        )
    }
}

@Composable
private fun ApplyButton(
    viewModel: FilterViewModel,
    showLoading: (Boolean) -> Unit,
    popBack: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val userLiveData = viewModel.podcastsLive.collectAsState().value


    Button(
        onClick = {
            Log.d("TAG", "SingleSelectorddd:230 " + userLiveData.interestId1)
            Log.d("TAG", "SingleSelectorddd:236" + userLiveData.interestId2)
            Log.d("TAG", "SingleSelectorddd:237" + userLiveData.interestId3)
            Log.d("TAG", "SingleSelectorddd:238" + userLiveData.interestLevel1)
            Log.d("TAG", "SingleSelectorddd:239" + userLiveData.interestLevel2)
            Log.d("TAG", "SingleSelectorddd:240" + userLiveData.interestLevel3)

            if (userLiveData.interestId1 != "" || userLiveData.interestId2 != "" || userLiveData.interestId3 != "" ||
                userLiveData.interestId4 != "" || userLiveData.interestId5 != ""
            ) {
                if (userLiveData.interestLevel1 == "" /*|| userLiveData.interestLevel2 == ""
                    || userLiveData.interestLevel3 == "" || userLiveData.interestLevel4 == "" || userLiveData.interestLevel5 == ""*/
                ) {
                    context.showToast("Please select interest level")
                } else {
                    showLoading.invoke(true)
                    viewModel.userFilterApi(context) { list, status ->
                        Log.d("TAG", "ApplyButton2544: " + list)
                        if (status == true) {
                            showLoading.invoke(false)
                            RecommendnedMainList = if (!list.isNullOrEmpty()) list
                            else emptyList()
                            RecommendnedMainList.firstOrNull()?.isFilter = false
                            IsFilter = true
                            popBack?.invoke()
                        }

                    }
                }
            }else{
                showLoading.invoke(true)
                viewModel.userFilterApi(context) { list, status ->
                    Log.d("TAG", "ApplyButton2544: " + list)
                    if (status == true) {
                        showLoading.invoke(false)
                        RecommendnedMainList = if (!list.isNullOrEmpty()) list
                        else emptyList()
                        RecommendnedMainList.firstOrNull()?.isFilter = false
                        IsFilter = true
                        popBack?.invoke()
                    }

                }
            }


            /* showLoading.invoke(true)
             viewModel.userFilterApi(context) { list, status ->
                 showLoading.invoke(false)

                 Log.d("TAG", "ApplyButtosdsdn: " + status)

                 if (status) {

                     RecommendnedMainList = if (!list.isNullOrEmpty()) list
                     else emptyList()
                     RecommendnedMainList.firstOrNull()?.isFilter = true
                     popBack?.invoke()

                 }

             }*/

        }, shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 20.dp)
            .fillMaxWidth()
            .height(50.dp)

    ) {
        Text(
            text = "Apply Filter",
            color = Color.White,
            fontFamily = poppinsMedium,
            fontSize = 15.sp
        )
    }
}

@Composable
private fun AddInterestButton(openScreen: ((String) -> Unit)?) {
    OutlinedButton(
        onClick = { openScreen?.invoke(Route.FILTER_INTEREST_ROUTE) },
        border = BorderStroke(1.dp, VioletBlue),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(top = 10.dp, bottom = 30.dp, end = 25.dp, start = 25.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(top = 2.dp, bottom = 2.dp, end = 3.dp, start = 3.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.plus_circle), contentDescription = "",
                modifier = Modifier.size(20.dp)
            )
            AddSpace(size = 10.dp)
            Text(text = "Add", color = VioletBlue, fontSize = 14.sp, fontFamily = poppinsLight)
        }


    }
}

@Composable
private fun InterestList() {

    val viewModel: FilterViewModel = hiltViewModel()
    val context = LocalContext.current

    val interestList: MutableState<ArrayList<InterestFilterChildListItem>?> =
        remember {
            mutableStateOf(null)
        }

    viewModel.getSelectedInterestList(context) {
        interestList.value = it
    }

    interestList.value?.let { it ->
        InterestFilterChildListLayout(it) {
            Log.d("TAG", "InterestList: " + it)
            viewModel.updateChildInterestLevel(it, context)
        }
    }
    Log.d("TAG", "InsfasftesdsrestList: " + interestList.value?.size)


    for (sads in interestList.value!!) {
        Log.d("TAG", "InsfasfterestList: " + sads)

    }
    /* val list = listOf(
         InterestChildListItem(
             "Yoga", "", listOf(
                 InterestNestedItem("Beginner"),
                 InterestNestedItem("Intermediate"),
                 InterestNestedItem("Master"),
             )
         ),
         InterestChildListItem(
             "Drum", "", listOf(
                 InterestNestedItem("Beginner"),
                 InterestNestedItem("Intermediate"),
                 InterestNestedItem("Master"),
             )
         ),
         InterestChildListItem(
             "Bowling", "", listOf(
                 InterestNestedItem("Beginner"),
                 InterestNestedItem("Intermediate"),
                 InterestNestedItem("Master"),
             )
         ),
     )*/

    // InterestChildListLayout(interestList.value)


}

@Composable
private fun SingleSelectorGender(list: List<String>) {

    val viewModel: FilterViewModel = hiltViewModel()
    val context = LocalContext.current
    val userLiveData = viewModel.podcastsLive.collectAsState().value
    val position = list.indexOf(userLiveData.gender?.capitalize())
    Log.d("TAG", "SingleSelectorGender: " + position)

    val selected = remember {
        mutableStateOf(list.getOrNull(position) ?: "")
    }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, end = 25.dp, start = 25.dp)
    ) {
        items(list.size) { pos ->

            val dataItem = list[pos]
            Log.d("TAG", "SingleSelector: " + dataItem)
            Text(text = dataItem,
                fontFamily =
                if (selected.value == dataItem) poppinsSemiBold else
                    poppinsMedium,
                fontSize = 15.sp,
                color = if (selected.value == dataItem) Color.White else SilverChalice,
                modifier = Modifier
                    .background(
                        if (selected.value == dataItem) VioletBlue else
                            Seashell, RoundedCornerShape(100.dp)
                    )
                    .padding(
                        start = 20.dp,
                        top = 7.dp,
                        end = 20.dp,
                        bottom = 5.dp,
                    )
                    .clickable {
                        selected.value = dataItem
                        viewModel.updateUserDataLocal(
                            userLiveData.copy(gender = selected.value.lowercase()),
                            context
                        ) {

                        }
                    })

            AddSpace(size = 15.dp)

        }
    }
}

@Composable
private fun SingleSelectorLookingFor(list: List<String>) {

    val viewModel: FilterViewModel = hiltViewModel()
    val context = LocalContext.current
    val userLiveData = viewModel.podcastsLive.collectAsState().value
    Log.d("TAG", "SingleSelectorLookingFor22asdasd: " + userLiveData.relationship)
    Log.d("TAG", "SingleSelectorLookingFor22:423 " + list)
    /* val selected = remember {
         mutableStateOf(list.getOrNull(userLiveData.relationship?.minus(1) ?: 0) ?: "")
     }*/

    /*  val selected = remember {
        //  mutableStateOf(list.getOrNull(userLiveData.relationship?:0))
          mutableStateOf(list.getOrNull(if(userLiveData.relationship==null) -1
          else if(userLiveData.relationship==1) 0
          else if(userLiveData.relationship==2) 1
          else if(userLiveData.relationship==3) 2
          else userLiveData.relationship

          ))
      }*/
    val selected = remember {
        mutableStateOf(list.getOrNull(userLiveData.relationship?.toIntOrNull() ?: -1))
    }
    /* val selected = remember {
         mutableStateOf(list.getOrNull(userLiveData.relationship?.toInt()?.minus(1) ?:0)?:"")
     }*/

    Log.d("TAG", "SingleSelectorLookisdfdsfngFor: " + selected.value)
    if (selected.value == "Friend") {

    }
    when (userLiveData.relationship) {
        1.toString() -> selected.value = "Partner"
        2.toString() -> selected.value = "Friend"
        3.toString() -> selected.value = "N/A"
    }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, end = 25.dp, start = 25.dp)
    ) {
        items(list.size) { pos ->

            val dataItem = list[pos]
            Log.d("TAG", "SingleSelector21: " + dataItem)
            Log.d("TAG", "SingleSelector21:455 " + selected.value)
            Text(text = dataItem,
                fontFamily =
                if (selected.value == dataItem) poppinsSemiBold else
                    poppinsMedium,
                fontSize = 15.sp,
                color = if (selected.value == dataItem) Color.White else SilverChalice,
                modifier = Modifier
                    .background(
                        if (selected.value == dataItem) VioletBlue else
                            Seashell, RoundedCornerShape(100.dp)
                    )
                    .padding(
                        start = 20.dp,
                        top = 7.dp,
                        end = 20.dp,
                        bottom = 5.dp,
                    )
                    .clickable {
                        selected.value = dataItem
                        // Log.d("TAG", "SingleSelectorLookingFor:465 "+selected.value.get(pos))
                        Log.d("TAG", "SingleSelectorLookingFor466: " + pos + 1)
                        val position = list.indexOf(selected.value) + 1
                        var positionPlusOne = position
                        Log.d(
                            "TAG",
                            "SingleSelectorLookingFor4ds67: " + positionPlusOne
                        )

                        viewModel.updateUserDataLocal(
                            userLiveData.copy(relationship = positionPlusOne.toString()),
                            context
                        ) {

                        }

                        /*viewModel.updateUserDataLocal(
                            userLiveData.copy(relationship = if (positionPlusOne == 1) "2" else if (positionPlusOne == 2) "1" else positionPlusOne.toString()),
                            context
                        ) {

                        }*/
                    })

            AddSpace(size = 15.dp)

        }
    }
}


@Composable
private fun TitleText(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, end = 25.dp, start = 25.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title, fontSize = 20.sp, fontFamily = poppinsRegular, color = Color.Black,
            modifier = Modifier
                .fillMaxWidth(0.4f)
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth(1f)
                .height(1.dp)
                .background(PearlBush)
        )
    }
}
