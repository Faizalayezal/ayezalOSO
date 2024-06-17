package com.sanxingrenge.benben.screens.loggedInModule

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sanxingrenge.benben.R
import com.sanxingrenge.benben.constant.MainInterestLevelList
import com.sanxingrenge.benben.ui.theme.*
import com.sanxingrenge.benben.uiComponents.AddSpace
import com.sanxingrenge.benben.uiComponents.InterestGridItem
import com.sanxingrenge.benben.uiComponents.InterestGridLayout
import com.sanxingrenge.benben.viewModel.FilterViewModel

/*private val list = listOf(
    InterestGridItem("Yoga", R.drawable.interest_1),
    InterestGridItem("Swimming", R.drawable.interest_2),
    InterestGridItem("Cricket", R.drawable.interest_3),
    InterestGridItem("Chess", R.drawable.interest_4),
    InterestGridItem("Painting", R.drawable.interest_5),
    InterestGridItem("Juggling", R.drawable.interest_6),
    InterestGridItem("Football", R.drawable.interest_7),
    InterestGridItem("Puzzle", R.drawable.interest_8),
    InterestGridItem("Direct Hit", R.drawable.interest_9),
    InterestGridItem("Bowling", R.drawable.interest_10),
    InterestGridItem("Violin", R.drawable.interest_11),
    InterestGridItem("Trumpet", R.drawable.interest_12),
    InterestGridItem("Guitar", R.drawable.interest_13),
    InterestGridItem("Drum", R.drawable.interest_14),
    InterestGridItem("Theater", R.drawable.interest_15),
    InterestGridItem("Singing", R.drawable.interest_16),
)*/

private enum class FilterInterestScreenType {
    MainInterest,

}

@Preview
@Composable
fun FilterInterestActivity(popBack: (() -> Unit)? = null) {
    val viewModel: FilterViewModel = hiltViewModel()
    val context= LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {

        val screenState = remember {
            mutableStateOf(FilterInterestScreenType.MainInterest)
        }

        MainTitle(
            when (screenState.value) {
                FilterInterestScreenType.MainInterest -> "Filter Interests"
            }, screenState, popBack
        )

        when (screenState.value) {
            FilterInterestScreenType.MainInterest -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ){
                    item {
                        InterestGridLayout(MainInterestLevelList.map {
                            InterestGridItem(it.name ?: "", it.image ?: "")
                        }, 4, clickable = true, openPopupOnClick = {
                            viewModel.updateInterestList(it, context)
                        })
                        Button(
                            modifier = Modifier
                                .padding(horizontal = 30.dp, vertical = 20.dp)
                                .height(50.dp)
                                .fillMaxWidth(),
                            onClick = {
                                // screenState.value = popBack?.invoke()
                                when (screenState.value) {
                                    FilterInterestScreenType.MainInterest -> popBack?.invoke()
                                }

                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = VioletBlue,
                            )
                        ) {
                            Text(text = "Continue", fontFamily = poppinsMedium, color = White)
                        }
                    }
                }
            }
        }

    }

}

@Composable
private fun MainTitle(
    title: String,
    screenState: MutableState<FilterInterestScreenType>,
    popBack: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, end = 20.dp, start = 5.dp, bottom = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            when (screenState.value) {
                FilterInterestScreenType.MainInterest -> popBack?.invoke()

            }
        }, modifier = Modifier.size(50.dp)) {
            Image(
                painter = painterResource(id = R.drawable.back_arrow),
                contentDescription = "", modifier = Modifier.size(40.dp)
            )
        }
        AddSpace(5.dp)
        Text(
            text = title,
            fontSize = 20.sp,
            fontFamily = poppinsBold,
            color = Color.Black
        )
    }
    Spacer(
        modifier = Modifier
            .padding(bottom = 30.dp)
            .fillMaxWidth()
            .height(1.dp)
            .background(GainsBoro)
    )
}
