package com.sanxingrenge.benben.screens.loggedInModule

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sanxingrenge.benben.R
import com.sanxingrenge.benben.constant.Constns.userDataObject
import com.sanxingrenge.benben.constant.dataStoreGetUserData
import com.sanxingrenge.benben.screens.OpenPopUpContent
import com.sanxingrenge.benben.screens.TransparentLayerWithBottomNavigation
import com.sanxingrenge.benben.screens.loggedInModule.settingFlow.RequestScreen
import com.sanxingrenge.benben.ui.theme.*
import com.sanxingrenge.benben.uiComponents.AddSpace
import com.sanxingrenge.benben.viewModel.DiscoverScreenViewModel
import com.valentinilk.shimmer.*


sealed class BottomNavItem(
    var title: String,
    var iconSelected: Int,
    var iconUnselected: Int,
    var screen_route: String
) {

    object Discover :
        BottomNavItem("Discover", R.drawable.home1_act, R.drawable.home1_deact, "discover")

    object MyNetwork : BottomNavItem(
        "My Network",
        R.drawable.home2_deact,
        R.drawable.home2_deact,
        "my_network"
    )

    object Messages :
        BottomNavItem("Messages", R.drawable.home3_act, R.drawable.home3_deact, "messages")

    object Profile :
        BottomNavItem("Profile", R.drawable.home4_act, R.drawable.home4_deact, "profile")

}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(openScreen: ((String) -> Unit)? = null, showLoading: (Boolean) -> Unit, popBack: (() -> Unit)? = null, viewModel: DiscoverScreenViewModel? = null,) {
    val navController = rememberNavController()
    val context = LocalContext.current

    Scaffold(
        bottomBar = { BottomNavigationFun(navController = navController, openScreen) },
    ) {
        NavigationGraph(navController = navController, openScreen, showLoading,popBack,viewModel)
        // openScreen?.invoke(Route.TRANSPERANT_BASE)
    }


    val activity = LocalContext.current as? Activity
    BackHandler(true) {
        activity?.finishAffinity()
    }
}

@Composable
fun BackHandler(enabled: Boolean = true, onBack: () -> Unit) {
    // Safely update the current `onBack` lambda when a new one is provided
    val currentOnBack by rememberUpdatedState(onBack)
    // Remember in Composition a back callback that calls the `onBack` lambda
    val backCallback = remember {
        object : OnBackPressedCallback(enabled) {
            override fun handleOnBackPressed() {
                currentOnBack()
            }
        }
    }
    // On every successful composition, update the callback with the `enabled` value
    SideEffect {
        backCallback.isEnabled = enabled
    }
    val backDispatcher = checkNotNull(LocalOnBackPressedDispatcherOwner.current) {
        "No OnBackPressedDispatcherOwner was provided via LocalOnBackPressedDispatcherOwner"
    }.onBackPressedDispatcher
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner, backDispatcher) {
        // Add callback to the backDispatcher
        backDispatcher.addCallback(lifecycleOwner, backCallback)
        // When the effect leaves the Composition, remove the callback
        onDispose {
            backCallback.remove()
        }
    }
}

@Composable
fun BottomNavigationFun(navController: NavController, openScreen: ((String) -> Unit)?) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true, block = {
        context.dataStoreGetUserData().collect {
            Log.d("TAG", "HomeSdfgdfgcreensfsfsdf:78 " + it)

            userDataObject=it
        }

    })

    val openRecoderDialog = remember {
        mutableStateOf(false)
    }
    val items = listOf(
        BottomNavItem.Discover,
        BottomNavItem.MyNetwork,
        BottomNavItem.Messages,
        BottomNavItem.Profile,
    )

    Surface(
        elevation = 15.dp,
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            BottomNavigation(
                elevation = 0.dp,
                backgroundColor = BottomColor,
                modifier = Modifier
                    .height(70.dp)
            ) {


                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val selectedTab = remember {
                    mutableStateOf(BottomNavItem.Discover.screen_route)
                }

                items.forEach { item ->

                    Log.d("TAG", "BottomNavigationFun:166 "+item.screen_route)
                    Log.d("TAG", "BottomNavigationFun:167 "+item.title)
                    Log.d("TAG", "BottomNavigationFun:168 "+item.iconSelected)
                    Log.d("TAG", "BottomNavigationFun:169 "+item.iconUnselected)
                    Log.d("TAG", "BottomNavigationFun:170 "+selectedTab.value)

                    BottomNavigationItem(
                        icon = {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Image(
                                    painterResource(
                                        id = if (selectedTab.value == item.screen_route)
                                            item.iconSelected
                                        else item.iconUnselected
                                    ), contentDescription = item.title,
                                    modifier = Modifier.size(20.dp)
                                )

                                AnimatedVisibility(visible = selectedTab.value == item.screen_route) {
                                    Column(
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        AddSpace(7.dp)
                                        Box(
                                            modifier = Modifier
                                                .size(7.dp)
                                                .background(
                                                    VioletBlue,
                                                    RoundedCornerShape(100.dp)
                                                )
                                        ) {

                                        }
                                    }
                                }

                            }
                        },
                        selected = currentRoute == item.screen_route,



                        onClick = {
                            if (userDataObject?.userId == null) {
                                openRecoderDialog.value = true

                            } else {
                                selectedTab.value = item.screen_route
                                navController.navigate(item.screen_route) {

                                    navController.graph.startDestinationRoute?.let { screen_route ->
                                        popUpTo(screen_route) {
                                            saveState = true
                                        }
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }

                        }
                    )
                }
            }
        }
    }

    if (openRecoderDialog.value) {
        Dialog(onDismissRequest = {
            openRecoderDialog.value = false
        }) {
            OpenPopUpContent(openScreen) {
                openRecoderDialog.value = false
            }
        }
    }
}


@Composable
fun NavigationGraph(
    navController: NavHostController,
    openScreen: ((String) -> Unit)? = null,
    showLoading: (Boolean) -> Unit,
    popBack: (() -> Unit)? = null,
    viewModel: DiscoverScreenViewModel? = null,
) {
    NavHost(navController, startDestination = BottomNavItem.Discover.screen_route) {
        composable(BottomNavItem.Discover.screen_route) {

            DiscoverScreen(openScreen)
            Log.d("TAG", "NavigationGraph321: " + userDataObject)
            if (userDataObject?.userId == null) {
                TransparentLayerWithBottomNavigation(openScreen)
            }

        }
        composable(BottomNavItem.MyNetwork.screen_route) {
            RequestScreen(openScreen, showLoading,popBack)
        }
        composable(BottomNavItem.Messages.screen_route) {
            MessagesScreen(openScreen)
        }
        composable(BottomNavItem.Profile.screen_route) {
            ProfileScreen(openScreen, showLoading)
        }
    }
}


data class MessageUserListObject(
    val imageId: Int,
    val name: String,
    val msg: String,
    val date: String,
    val count: String,
    val online: Boolean,
    val isMediaMsg: Boolean,
    val isCurrentUserMsg: Boolean,
)

val messageUserList = listOf(
    MessageUserListObject(
        R.drawable.userimg1,
        "Ash",
        "What are you up to?",
        "Just now",
        "3",
        online = true,
        isMediaMsg = false,
        isCurrentUserMsg = false
    ),
    MessageUserListObject(
        R.drawable.userimg2,
        "Jean",
        "Voice message",
        "02:30 pm",
        "1",
        online = true,
        isMediaMsg = true,
        isCurrentUserMsg = false
    ),
    MessageUserListObject(
        R.drawable.userimg3,
        "Ariana",
        "Looks great bro \uD83D\uDD25",
        "Yesterday",
        "",
        online = false,
        isMediaMsg = false,
        isCurrentUserMsg = true
    ),
    MessageUserListObject(
        R.drawable.userimg3,
        "Merry",
        "Sound good for me too!!",
        "2 Aug",
        "",
        online = false,
        isMediaMsg = false,
        isCurrentUserMsg = true
    ),
    MessageUserListObject(
        R.drawable.userimg4,
        "Osomate",
        "Welcome to Osomate",
        "28 Jul",
        "1",
        online = false,
        isMediaMsg = false,
        isCurrentUserMsg = false
    ),
)

