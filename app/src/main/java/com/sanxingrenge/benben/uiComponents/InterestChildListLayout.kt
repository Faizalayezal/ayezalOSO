package com.sanxingrenge.benben.uiComponents

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sanxingrenge.benben.dataModel.UserDataObject
import com.sanxingrenge.benben.ui.theme.QuillGrey
import com.sanxingrenge.benben.ui.theme.Seashell
import com.sanxingrenge.benben.ui.theme.SilverChalice
import com.sanxingrenge.benben.ui.theme.VioletBlue
import com.sanxingrenge.benben.ui.theme.White
import com.sanxingrenge.benben.ui.theme.poppinsRegular
import com.sanxingrenge.benben.viewModel.EditInterestViewModel
import com.sanxingrenge.benben.viewModel.ProfileScreenViewModel
import kotlin.math.log


data class InterestChildListItem(
    val name: String,
    val imageResource: String,
    val childNameList: List<InterestNestedItem>,
    var childSelectedName: String = "",
)

data class InterestNestedItem(
    var name: String,
    var isSelected: Boolean = false,
)

private const val TAG = "InterestChildListLayout"

@SuppressLint("UnrememberedMutableState")
@Composable
fun InterestChildListLayout(
    listTop: List<InterestChildListItem>? = listOf(
        InterestChildListItem(
            name = "Alpha",
            imageResource = "",
            childNameList = listOf(
                InterestNestedItem("Beginner"),
                InterestNestedItem("Intermediate"),
                InterestNestedItem("Master"),
            ),
            childSelectedName = "Intermediate",
        )
    ), list: List<InterestGridItem>,
    selectedList: ((List<InterestChildListItem>) -> Unit)? = null
) {

    val viewModel: ProfileScreenViewModel = hiltViewModel()
    val viewModels: EditInterestViewModel = hiltViewModel()
    val context = LocalContext.current
    viewModel.collectUserData(context)
    val userLiveData = viewModel.podcastsLive.collectAsState().value
    val interestList: MutableState<ArrayList<InterestChildListItem>?> =
        remember {
            mutableStateOf(null)
        }


    var conditions: InterestNestedItem? = null
    val beginnerList1 = remember { mutableStateOf<ArrayList<InterestGridItem>>(arrayListOf()) }
    val intermediateList2 = remember { mutableStateOf<ArrayList<InterestGridItem>>(arrayListOf()) }
    val masterList3 = remember { mutableStateOf<ArrayList<InterestGridItem>>(arrayListOf()) }

    viewModel.getInterestList { beginnerList, intermediateList, masterList ->
        beginnerList1.value = beginnerList
        intermediateList2.value = intermediateList
        masterList3.value = masterList
    }





    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(100.dp, 1000.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            items(listTop?.size ?: 0) { pos ->

                val selectedData = remember {
                    mutableStateOf<InterestNestedItem?>(null)
                }


                // selectedList?.invoke(interestList.value?: listOf())
                Log.d(
                    TAG,
                    "InterestChildListLayout11:933 " + listTop?.getOrNull(pos)?.childNameList?.getOrNull(
                        pos
                    )
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .padding(18.dp)
                            .border(1.dp, QuillGrey, RoundedCornerShape(10.dp))
                            .fillMaxWidth()
                    ) {

                        Box(
                            modifier = Modifier.padding(
                                top = 40.dp,
                                start = 20.dp,
                                end = 20.dp,
                                bottom = 15.dp
                            )
                        ) {

                            ChipGroup2(
                                list,
                                listTop?.getOrNull(pos)?.childNameList,
                                selectedData.value,
                            ) { selectedItem ->
                                val itemFound =
                                    listTop?.getOrNull(pos)?.childNameList?.find { it == selectedItem }

                                listTop?.getOrNull(pos)?.childSelectedName =
                                    selectedItem?.name ?: ""

                                Log.d(
                                    TAG,
                                    "InterestChildListLayout: yesyFlowItem>>--->$itemFound >>${
                                        listTop?.getOrNull(pos)
                                    }"
                                )
                                Log.d(
                                    TAG,
                                    "InterestChildListLayout: yesyFlowItem>>: " + selectedItem
                                )
                                itemFound?.let { intNestedItem ->
                                    val itemIndex =
                                        listTop.getOrNull(pos)?.childNameList?.indexOf(intNestedItem)

                                    itemIndex?.let {

                                        Log.d(
                                            TAG,
                                            "InterestChildListLayout: test PREVIOUS DATA>>" + listTop.getOrNull(
                                                pos
                                            )?.childNameList!![it].isSelected
                                        )
                                        Log.d(
                                            TAG,
                                            "InterestChildListLayout123465:---> " + intNestedItem.name
                                        )
                                        Log.d(
                                            TAG,
                                            "InterestChildListLayout123465:---> " + intNestedItem.isSelected
                                        )
                                        Log.d(TAG, "InterestChildListLayout123465:--> " + it)

                                        listTop.getOrNull(pos)?.childNameList!![it].isSelected =
                                            !(selectedItem?.isSelected ?: false)
                                        Log.d(
                                            TAG,
                                            "InterestChildListLayout: test AFTER DATA>>" + listTop.getOrNull(
                                                pos
                                            )?.childNameList!![it].isSelected
                                        )
                                        selectedData.value =
                                            listTop.getOrNull(pos)?.childNameList!![it]

                                        selectedList?.invoke(listTop)
                                    }
                                }

                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .padding(start = 40.dp)
                            .border(1.dp, QuillGrey, RoundedCornerShape(10.dp))
                            .background(White, RoundedCornerShape(10.dp))
                            .height(50.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 22.dp)
                                .fillMaxHeight(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AddImage(listTop?.getOrNull(pos)?.imageResource ?: "", size = 25.dp)
                            AddSpace(size = 8.dp)
                            Text(
                                text = listTop?.getOrNull(pos)?.name ?: "",
                                fontFamily = poppinsRegular,
                                fontSize = 15.sp,
                                color = Color.Black
                            )
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun Chip(
    userLiveData: UserDataObject,
    cars: List<InterestNestedItem>?,
    data: InterestNestedItem?,
    isSelected: Boolean = false,
    beginnerList1: (ArrayList<InterestGridItem>)? = null,
    intermediateList2: (ArrayList<InterestGridItem>)? = null,
    masterList3: (ArrayList<InterestGridItem>)? = null,
    onSelectionChanged: (InterestNestedItem?) -> Unit = {},
) {

    Log.d(TAG, "Chip123546: " + data?.name)

    Surface(
        modifier = Modifier.padding(4.dp),
        elevation = 3.dp,
        shape = RoundedCornerShape(100.dp),
        color = if (isSelected) VioletBlue else Seashell
    ) {
        Row(modifier = Modifier
            .toggleable(
                value = isSelected,
                onValueChange = {
                    Log.d(TAG, "Chipdfsdfd: " + it)
                    onSelectionChanged(data)
                }
            )
        ) {
            Text(
                text = data?.name ?: "",
                fontFamily = poppinsRegular,
                fontSize = 12.sp,
                overflow = TextOverflow.Ellipsis,
                color = if (isSelected) Color.White else SilverChalice,
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 10.dp)
                    .fillMaxSize(),
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}

@Composable
fun ChipGroup(
    cars: List<InterestNestedItem>?,
    selectedCar: InterestNestedItem? = null,
    onSelectedChanged: (InterestNestedItem?) -> Unit = {},
) {
    if (cars == null) return
    val viewModel: ProfileScreenViewModel = hiltViewModel()
    val viewModels: EditInterestViewModel = hiltViewModel()
    val context = LocalContext.current
    viewModel.collectUserData(context)
    val beginnerList1 = remember { mutableStateOf<ArrayList<InterestGridItem>>(arrayListOf()) }
    val intermediateList2 = remember { mutableStateOf<ArrayList<InterestGridItem>>(arrayListOf()) }
    val masterList3 = remember { mutableStateOf<ArrayList<InterestGridItem>>(arrayListOf()) }
    val userLiveData = viewModel.podcastsLive.collectAsState().value


    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(7.dp)
    ) {


        viewModel.getInterestList { beginnerList, intermediateList, masterList ->
            beginnerList1.value = beginnerList
            intermediateList2.value = intermediateList
            masterList3.value = masterList
        }



        cars.forEach { singleItem ->
            Log.d(TAG, "ChipGroup:3411 " + singleItem)

            item {
                Chip(
                    userLiveData,
                    null,
                    data = singleItem,
                    isSelected = selectedCar?.name == singleItem.name,
                    beginnerList1.value, intermediateList2.value, masterList3.value,
                    onSelectionChanged = {
                        onSelectedChanged(it)
                    },
                )
            }
        }

    }
    /*Column(modifier = Modifier.padding(8.dp)) {
        LazyRow {
            items(cars.size) { pos ->

            }
        }
    }*/
}

@Composable
fun ChipGroup2(
    list: List<InterestGridItem>,
    cars: List<InterestNestedItem>?,
    selectedCar: InterestNestedItem? = null,
    onSelectedChanged: (InterestNestedItem?) -> Unit = {},
) {
    if (cars == null) return
    val viewModel: ProfileScreenViewModel = hiltViewModel()
    val viewModels: EditInterestViewModel = hiltViewModel()
    val context = LocalContext.current
    viewModel.collectUserData(context)
    val selectedName = remember { mutableStateOf<String>("") }
    val selectedBool = remember { mutableStateOf<Boolean>(false) }
    val beginnerList1 = remember { mutableStateOf<ArrayList<InterestGridItem>>(arrayListOf()) }
    val intermediateList2 = remember { mutableStateOf<ArrayList<InterestGridItem>>(arrayListOf()) }
    val masterList3 = remember { mutableStateOf<ArrayList<InterestGridItem>>(arrayListOf()) }
    val userLiveData = viewModel.podcastsLive.collectAsState().value
    val interestList: MutableState<ArrayList<InterestChildListItem>?> =
        remember {
            mutableStateOf(null)
        }
    viewModels.getSelectedInterestList2(context) {
        Log.d(TAG, "InterestChildListLayout9095644: " + it)
        interestList.value = it

    }
    var conditions = false

    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(7.dp)
    ) {


        viewModel.getInterestList { beginnerList, intermediateList, masterList ->
            beginnerList1.value = beginnerList
            intermediateList2.value = intermediateList
            masterList3.value = masterList
        }



        cars.forEachIndexed { index, singleItem ->

            //conditions=selectedName.value==singleItem.name

            Log.d(TAG, "ChipGroup2154:3966---3922> " + cars.getOrNull(index))
            Log.d(TAG, "ChipGroup2154:3966---39745> " + list.getOrNull(index))
            Log.d(TAG, "ChipGroup2154:3966---5> " + selectedCar?.name)
            Log.d(TAG, "ChipGroup2154:3966---405> " + singleItem.name)
//if(selectedCar?.name==null) conditions else selectedCar.name==singleItem.name
            interestList.value?.map {
                it.childNameList.map {
                    if (it.isSelected == true) {
                        Log.d(TAG, "ChipGroup2154:3966---398745> " + it.name)
                        selectedBool.value = it.isSelected
                    }
                }
            }
            Log.d(TAG, "ChipGroup2154:3966---407> " + selectedBool.value)

            item {
                Chip(
                    userLiveData,
                    cars,
                    data = singleItem,
                    isSelected = true,
                    beginnerList1.value, intermediateList2.value, masterList3.value,
                    onSelectionChanged = {
                        onSelectedChanged(it)
                    },
                )
            }
        }

    }
    //if(selectedCar?.name==null) selectedCar?.name==singleItem.name && singleItem.isSelected else singleItem.isSelected
    /*Column(modifier = Modifier.padding(8.dp)) {
        LazyRow {
            items(cars.size) { pos ->

            }
        }
    }*/
}


