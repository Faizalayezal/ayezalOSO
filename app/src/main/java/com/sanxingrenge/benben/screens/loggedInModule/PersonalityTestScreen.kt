package com.sanxingrenge.benben.screens.loggedInModule

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.sanxingrenge.benben.R
import com.sanxingrenge.benben.constant.Constns
import com.sanxingrenge.benben.constant.Constns.backepersonality
import com.sanxingrenge.benben.constant.Constns.store
import com.sanxingrenge.benben.constant.showToast
import com.sanxingrenge.benben.responseModel.GetQuestionsListDataResponse
import com.sanxingrenge.benben.screens.Route
import com.sanxingrenge.benben.screens.zodiacSignModule.questionList
import com.sanxingrenge.benben.ui.theme.*
import com.sanxingrenge.benben.viewModel.PersonalityTestViewModel

data class StoreId(var id: Int)
data class StorePositionId(var id: Int, var answere: String)

@Composable
fun PersonalityTestScreen(
    openScreen: ((String) -> Unit)? = null,
    popBack: (() -> Unit)? = null,
    showLoading: (Boolean) -> Unit
) {

   if(backepersonality =="1"){
        popBack?.invoke()
        backepersonality =""
    }else{

    }

    val viewModel: PersonalityTestViewModel = hiltViewModel()
    LaunchedEffect(key1 = true, block = {
        showLoading.invoke(true)
        viewModel.getPersonalityQuestionList { list, status ->

            if (status) {
                showLoading.invoke(false)
                questionList = list.reversed()

            }

        }
    })


    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(id = R.drawable.chat_screen_bg),
            contentDescription = "",
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Log.d("TAG", "PersonalityTestScreen:71 " + questionList)
            TitleBar(popBack)
            Sliderr(openScreen = openScreen, questionList, popBack, showLoading)
        }
    }
}


@Composable
private fun TitleBar(popBack: (() -> Unit)? = null) {
    Box(
        modifier = Modifier
            .padding(top = 20.dp)
            .padding(horizontal = 10.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.back_arrow), contentDescription = "",
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier
                    .size(35.dp)
                    .clickable {
                        popBack?.invoke()
                    }
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 30.dp),
                text = "Take a Personality Test",
                fontSize = 22.sp,
                fontFamily = poppinsSemiBold,
                color = Color.White
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Sliderr(
    /* list: List<String> = listOf(
         "What Kind of life do you want?",
         "Are you happy with your live?",
         "Where you want to travel next?"
     ),*/
    openScreen: ((String) -> Unit)? = null,
    questionListt: List<GetQuestionsListDataResponse>,
    popBack: (() -> Unit)?,
    showLoading: (Boolean) -> Unit,

    ) {
    Log.d(
        "TAG",
        "PersonalityTestScreen:127 " + com.sanxingrenge.benben.screens.zodiacSignModule.questionList
    )

    val state = rememberPagerState()

    HorizontalPager(
        modifier = Modifier
            .fillMaxSize(),
        count = questionListt.size, state = state,
        itemSpacing = (0).dp,
        contentPadding = PaddingValues(horizontal = 35.dp),
    ) { page ->

        Box(
            modifier = Modifier
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {

            PageItemMain(
                page,
                state,
                questionListt[page],
                questionListt.size,
                openScreen,
                questionListt,
                popBack,
                showLoading
            )

        }

    }

    /*DotsIndicator(
        totalDots = 3,
        selectedIndex = state.currentPage,
        selectedColor = Purple500,
        unSelectedColor = GreyLight2
    )*/

}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PageItemMain(
    page: Int,
    state: PagerState,
    selectedData: GetQuestionsListDataResponse,
    maxSize: Int,
    openScreen: ((String) -> Unit)? = null,
    questionListt: List<GetQuestionsListDataResponse>,
    popBack: (() -> Unit)?,
    showLoading: (Boolean) -> Unit
) {
    val viewModel: PersonalityTestViewModel = hiltViewModel()
    val context = LocalContext.current
    val offsetAnimation: Dp by animateDpAsState(
        if (state.currentPage == page) 500.dp else 460.dp,
        tween(500, easing = LinearOutSlowInEasing)
    )
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 70.dp)
            .fillMaxWidth()
            .height(offsetAnimation)
            .background(Color.White, RoundedCornerShape(10.dp))
            .padding(horizontal = 20.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${page + 1}",
                fontFamily = poppinsSemiBold,
                fontSize = 22.sp,
                color = VioletBlue
            )
            Text(
                text = "/$maxSize",
                fontFamily = poppinsRegular,
                fontSize = 17.sp,
                color = SilverChalice
            )
        }

        Text(
            text = selectedData.question ?: "",
            fontFamily = poppinsSemiBold,
            fontSize = 22.sp,
            color = Color.Black
        )

        val selectedOption = remember {
            mutableStateOf(-1)
        }

        LazyColumn() {
            item {
                OptionItem(
                    selectedOption,
                    selectedData.answer1 ?: "",
                    0,
                    page + 1,
                    openScreen,
                    selectedData,

                    )
                OptionItem(
                    selectedOption,
                    selectedData.answer2 ?: "",
                    1,
                    page + 1, openScreen,
                    selectedData
                )
                OptionItem(
                    selectedOption,
                    selectedData.answer3 ?: "",
                    2,
                    page + 1,
                    openScreen,
                    selectedData
                )
                Spacer(modifier = Modifier.padding(top = 20.dp))

                if (questionListt.size == page + 1) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 65.dp)
                    ) {
                        Button(
                            onClick = {
                                val id = store?.id
                                if (selectedData.selectedId != null) {
                                    if ((selectedData.selectedId
                                            ?: 0) > 0 || selectedData.selectedId == 0
                                    ) {
                                        showLoading.invoke(true)
                                        viewModel.getUserPersonality(context) { status, str ->
                                            Log.d("TAG", "PageItemMainsdf: " + str)
                                            showLoading.invoke(false)
                                            if (status && str == "beta") {
                                                // openScreen?.invoke(Route.MY_PERSONALITY_BASE)
                                                openScreen?.invoke("${Route.MY_PERSONALITY_BASE}/3")


                                            } else if (status && str == "omega") {
                                                // openScreen?.invoke(Route.MY_PERSONALITY_BASE)
                                                openScreen?.invoke("${Route.MY_PERSONALITY_BASE}/2")

                                            } else if (status && str == "alpha") {
                                                // openScreen?.invoke(Route.MY_PERSONALITY_BASE)
                                                openScreen?.invoke("${Route.MY_PERSONALITY_BASE}/1")

                                            } else if (str == "") {
                                                // openScreen?.invoke(Route.MY_PERSONALITY_BASE)
                                                popBack?.invoke()

                                            }

                                        }
                                    }

                                } else {
                                    context.showToast("Please do Selected")
                                }


                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = VioletBlue
                            ),
                            shape = RoundedCornerShape(30),
                            modifier = Modifier
                                .size(95.dp, 40.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Text(
                                    text = "Submit",
                                    fontSize = 13.sp,
                                    fontFamily = poppinsRegular,
                                    color = White
                                )
                            }

                        }
                    }


                }
            }
        }


    }
}


@Composable
fun OptionItem(
    selectedOption: MutableState<Int>,
    text: String,
    currentPos: Int,
    parentPos: Int,
    openScreen: ((String) -> Unit)? = null,
    selectedData: GetQuestionsListDataResponse,

    ) {

    Log.d("TAG", "OptionItemsdfsdf: " + parentPos + "--->" + selectedData.selectedId)

    val viewModel: PersonalityTestViewModel = hiltViewModel()
    val context = LocalContext.current


    Text(text = text,
        fontFamily =
        if (selectedOption.value == currentPos) poppinsMedium else
            poppinsRegular, fontSize = 12.sp, color =
        if (selectedOption.value == currentPos) Color.White else
            SilverChalice,
        modifier = Modifier
            .padding(horizontal = 0.dp, vertical = 7.dp)
            .fillMaxWidth()
            .background(
                if (selectedOption.value == currentPos || selectedData.selectedId == currentPos) VioletBlue else
                    Seashell, RoundedCornerShape(7.dp)
            )
            .clickable {
                selectedOption.value = currentPos
                var answereId = ""
                when (currentPos) {
                    0 -> answereId = "alpha"
                    1 -> answereId = "beta"
                    2 -> answereId = "omega"
                }

                selectedData.selectedId = currentPos
                store = StoreId(selectedData.id ?: 0)
                StorePositionId(selectedData.id ?: 0, answereId)


                viewModel.addQuestionAnswerApi(context, selectedData.id ?: 0, answere = answereId) {
                    //parentPos

                }


                //  if (currentPos == 2) {
                // openScreen?.invoke("${Route.MY_PERSONALITY_BASE}/$parentPos")

                //  }

            }
            .padding(horizontal = 10.dp, vertical = 12.dp)
    )


}





