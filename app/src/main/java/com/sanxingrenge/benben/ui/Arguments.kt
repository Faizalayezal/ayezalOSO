package com.sanxingrenge.benben.ui

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument

/*


val TermsPrivacyArgs = GenerateArguments(ArgType)
val MyPersonalityArgs = GenerateArguments(ArgSelectedPos)
val SignInArgs = GenerateArguments(ArgIsNewUser)
val RegisterArgs = GenerateArguments(ArgEmail ,ArgPwd)

class GenerateArguments(vararg string: String) {
    private var str = ""
    private val list = arrayListOf<NamedNavArgument>()
    private var listTop = listOf("")

    init {
        string.forEach {
            str = "$str/{$it}"
            list.add(navArgument(it) {
                type = NavType.StringType
            })
        }
        listTop = string.toList()
    }

    fun toRoute(): String = str

    fun toArguments(): List<NamedNavArgument> = list.toList()

    fun getArg(nav: NavBackStackEntry, argName: String, default: String? = ""): String =
        nav.arguments?.getString(argName) ?: default ?: ""
}*/
