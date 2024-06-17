package com.sanxingrenge.benben.uiComponents

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sanxingrenge.benben.ui.theme.*
import com.sanxingrenge.benben.viewModel.FilterViewModel


data class InterestFilterChildListItem(
    val name: String,
    val imageResource: String,
    val childNameList: List<InterestFilterNestedItem>,
    var childSelectedName: String = "",
)

data class InterestFilterNestedItem(
    val name: String,
    var isSelected: Boolean = false,
)

private const val TAG = "InterestChildListLayout"

@Preview
@Composable
fun InterestFilterChildListLayout(listTop: List<InterestFilterChildListItem>?=listOf(
    InterestFilterChildListItem(
        name = "Alpha",
        imageResource = "",
        childNameList = listOf(
            InterestFilterNestedItem("Beginner"),
            InterestFilterNestedItem("Intermediate"),
            InterestFilterNestedItem("Master"),
        ),
        childSelectedName="Intermediate",
    )
),selectedList:((List<InterestFilterChildListItem>)->Unit)?=null) {
    val viewModel: FilterViewModel = hiltViewModel()
    val context = LocalContext.current
    viewModel.collectUserData(context)
    val userLiveData = viewModel.podcastsLive.collectAsState().value

    Log.d(TAG, "InterestFilterChildListLayout321: "+userLiveData)


    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(100.dp, 1000.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            items(listTop?.size?:0) { pos ->

                val selectedData = remember {
                    mutableStateOf<InterestFilterNestedItem?>(null)
                }

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

                            ChipGroup(
                                listTop?.getOrNull(pos)?.childNameList,
                                selectedData.value
                            ) { selectedItem ->
                                val itemFound =
                                    listTop?.getOrNull(pos)?.childNameList?.find { it == selectedItem }

                                listTop?.getOrNull(pos)?.childSelectedName = selectedItem?.name?:""

                                Log.d(TAG, "InterestChildListLayout: yesyFlowItem>>$itemFound >>${listTop?.getOrNull(pos)}")
                                
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
    data: InterestFilterNestedItem?,
    isSelected: Boolean = false,
    onSelectionChanged: (InterestFilterNestedItem?) -> Unit = {},
) {

    Log.d(
        TAG,
        "Chip: ChipGroup: testInside>> >>>>" + data?.name + ">>" + data?.isSelected + ">>" + isSelected
    )


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
    cars: List<InterestFilterNestedItem>?,
    selectedCar: InterestFilterNestedItem? = null,
    onSelectedChanged: (InterestFilterNestedItem?) -> Unit = {},
) {
    if (cars == null) return

    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(7.dp)
    ) {

        cars.forEach { singleItem ->
            item {

                Log.d(TAG, "ChipGroup: testInside>>1>>${selectedCar?.name}>>${singleItem.name}")

                Chip(
                    data = singleItem,
                    isSelected = selectedCar?.name == singleItem.name,
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