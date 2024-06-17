package com.sanxingrenge.benben

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sanxingrenge.benben.constant.FileData
import com.sanxingrenge.benben.ui.theme.OsomateTheme
import dagger.hilt.android.AndroidEntryPoint


private const val TAG = "TestActivity"

@AndroidEntryPoint
class TestActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            OsomateTheme {
                FoodApp()

            }
        }

    }

}


@Composable
private fun FoodApp() {
}

fun NavBackStackEntry.getArg(str: String): String = arguments?.getString(str) ?: ""
fun NavBackStackEntry.getListArg(listData: List<FileData>?): List<FileData>? = arguments?.getSerializable("fileList") as? List<FileData>