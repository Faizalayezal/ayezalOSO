package com.sanxingrenge.benben.screens.loggedInModule


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.sanxingrenge.benben.constant.Constns.producatId
import com.sanxingrenge.benben.constant.dataStoreGetUserData
import com.sanxingrenge.benben.constant.showToast
import com.sanxingrenge.benben.ui.theme.*
import com.sanxingrenge.benben.uiComponents.AddSpace
import com.sanxingrenge.benben.viewModel.SubscriptionViewModel
import kotlinx.coroutines.flow.firstOrNull
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GetPremiumScreen(
    openScreen: ((String) -> Unit)? = null,
    popBack: (() -> Unit)? = null,
    showLoading: (Boolean) -> Unit,
    clickPlan: (() -> Unit)? = null,
) {
    /*val chooseSubscription = remember {
        ChooseSubscription(thisActvity)
    }

    LaunchedEffect(key1 = true) {
        chooseSubscription.billingSetup()
        chooseSubscription.hasSubscription()
    }

    val currentSubscription by chooseSubscription.subscriptions.collectAsState()
*/

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Title()
        Slider()
        BottomDesign(popBack, showLoading,clickPlan)
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun BottomDesign(
    popBack: (() -> Unit)? = null,
    showLoading: (Boolean) -> Unit,
    clickPlan: (() -> Unit)?,
) {
    val context = LocalContext.current
   // val viewModel: SubscriptionViewModel = hiltViewModel()
   // val startDate = LocalDate.now()
    var plan = remember { mutableStateOf("") }



    LaunchedEffect(true, block = {
        plan.value = context.dataStoreGetUserData().firstOrNull()?.plan_type ?: ""

    })

   // val lastMonth = startDate.plusMonths(1)
   // val que = startDate.plusMonths(4)
   // val years = startDate.plusMonths(12)
    val selectedItem = remember {
        mutableStateOf(1)
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {

        Column(
            modifier = Modifier
                .background(White, RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp))
                .border(1.dp, GainsBoro, RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp))
                .padding(horizontal = 30.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AddSpace(size = 30.dp)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {


                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (selectedItem.value == 1) VioletBlue
                            else VioletBlue.copy(alpha = 0.09f),
                            RoundedCornerShape(12.dp)
                        )
                        .weight(1f, true)
                        .clickable {
                            producatId.value="com.monthlyosomatenew"
                            selectedItem.value = 1
                        },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OptionDetails(
                        "$25.44",
                        "$9.99",
                        "Monthly",
                        selectedItem.value == 1
                    )
                }
                Spacer(modifier = Modifier.size(10.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (selectedItem.value == 2) VioletBlue else VioletBlue.copy(alpha = 0.09f),
                            RoundedCornerShape(12.dp)
                        )
                        .weight(1f, true)
                        .clickable {
                            producatId.value="com.quarterlyosomatenew"
                            selectedItem.value = 2

                        },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OptionDetails(
                        "$39.99",
                        "$19.99",
                        "Quarterly", selectedItem.value == 2
                    )
                }
                Spacer(modifier = Modifier.size(10.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (selectedItem.value == 3) VioletBlue
                            else VioletBlue.copy(alpha = 0.09f),
                            RoundedCornerShape(12.dp)
                        )
                        .weight(1f, true)
                        .clickable {
                            producatId.value="com.yearplanosomate"
                            selectedItem.value = 3

                        },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OptionDetails(
                        "$69.99",
                        "$39.99",
                        "Yearly", selectedItem.value == 3
                    )
                }

            }

            AddSpace(size = 30.dp)
            Log.d("TAG", "BottomDesign163: " + selectedItem.value)

            Button(
                onClick = {
                    Log.d("TAG", "BodfsdsdttomDesign: " + plan.value)
                    if (plan.value != "") {
                        context.showToast("Plan Already Purchased")

                    } else {
                        clickPlan?.invoke()
                        /*showLoading.invoke(true)
                        if (selectedItem.value == 1) {
                            viewModel.subscriptionPlanPurchesApi(
                                context,
                                startDate.toString(),
                                lastMonth.toString(),
                                "monthly",
                                "9.99"
                            ) { it, msg ->
                                showLoading.invoke(false)
                                if (it) {
                                    context.showToast(msg)
                                    popBack?.invoke()

                                }

                            }

                        }
                        if (selectedItem.value == 2) {

                            viewModel.subscriptionPlanPurchesApi(
                                context,
                                startDate.toString(),
                                que.toString(),
                                "quarterly",
                                "19.99"
                            ) { it, msg ->
                                showLoading.invoke(false)

                                if (it) {
                                    context.showToast(msg)
                                    popBack?.invoke()
                                }

                            }

                        }
                        if (selectedItem.value == 3) {

                            viewModel.subscriptionPlanPurchesApi(
                                context,
                                startDate.toString(),
                                years.toString(),
                                "yearly",
                                "39.99"
                            ) { it, msg ->
                                showLoading.invoke(false)

                                if (it) {
                                    context.showToast(msg)
                                    popBack?.invoke()
                                }

                            }

                        }*/
                    }


                }, modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = VioletBlue
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "Activate Now",
                    color = White,
                    fontSize = 15.sp,
                    fontFamily = poppinsMedium,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
            }
            Button(
                onClick = { popBack?.invoke() }, modifier = Modifier
                    .padding(vertical = 20.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = White
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "Maybe Later",
                    color = VioletBlue,
                    fontSize = 12.sp,
                    fontFamily = poppinsRegular,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
            }

        }


    }

}

@Composable
private fun OptionDetails(
    oldPrice: String,
    newPrice: String,
    timePeriod: String,
    isSelected: Boolean
) {
    AddSpace(size = 15.dp)
    Text(
        text = oldPrice,
        fontFamily = poppinsRegular,
        fontSize = 13.sp,
        color =
        if (isSelected) White else
            SmokeyGrey,
        textDecoration = TextDecoration.LineThrough
    )
    AddSpace(size = 7.dp)
    Text(
        text = newPrice,
        fontFamily = poppinsBold,
        fontSize = 22.sp,
        color = if (isSelected) White else DarkGrey
    )
    AddSpace(size = 7.dp)
    Text(
        text = timePeriod,
        fontFamily = poppinsMedium,
        fontSize = 12.sp,
        color = if (isSelected) BabyPink else VioletBlue
    )
    AddSpace(size = 15.dp)
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun Slider(
    list: List<String> = listOf(
        "Circle of friends",
        "Circle of friends",
        "Circle of friends",
        "Circle of friends",
    )
) {

    val state = rememberPagerState()
    HorizontalPager(
        modifier = Modifier
            .fillMaxWidth(),
        count = list.size, state = state,
        itemSpacing = (0).dp,
    ) { page ->

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ItemDesign(list[page])

        }

    }

    Box(
        modifier = Modifier
            .fillMaxWidth(), contentAlignment = Alignment.Center
    ) {
        DotsIndicator(
            totalDots = list.size,
            selectedIndex = state.currentPage,
            selectedColor = VioletBlue,
            unSelectedColor = QuillGrey
        )

    }


}

@Composable
fun DotsIndicator(
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color,
    unSelectedColor: Color,
) {

    LazyRow(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()

    ) {

        items(totalDots) { index ->
            if (index == selectedIndex) {
                Box(
                    modifier = Modifier
                        .size(7.dp)
                        .clip(CircleShape)
                        .background(selectedColor)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(7.dp)
                        .clip(CircleShape)
                        .background(unSelectedColor)
                )
            }

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
            }
        }
    }
}

@Composable
fun ItemDesign(item: String) {
    AddSpace(15.dp)
    Image(
        painter = painterResource(id = com.sanxingrenge.benben.R.drawable.hand),
        contentDescription = "",
        modifier = Modifier.size(90.dp)
    )
    AddSpace(20.dp)
    Text(text = item, fontFamily = poppinsSemiBold, fontSize = 18.sp, color = VioletBlue)
    AddSpace(5.dp)
    Text(
        text = "Enjoy the circle of friends and make\n" +
                "friends freely",
        fontFamily = poppinsRegular,
        fontSize = 11.sp,
        color = SilverChalice,
        textAlign = TextAlign.Center
    )
    AddSpace(20.dp)
}

@Composable
private fun Title(title: String = "Get Premium") {
    Text(
        text = title,
        fontFamily = poppinsSemiBold,
        fontSize = 20.sp,
        color = Color.Black,
        modifier = Modifier
            .padding(vertical = 20.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}
