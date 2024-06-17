package com.sanxingrenge.benben.constant

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.ui.Modifier
import com.sanxingrenge.benben.R
import com.sanxingrenge.benben.responseModel.AllListItem
import com.vdx.designertoast.DesignerToast
import java.util.regex.Pattern

const val EMAIL_TOO_SHORT = "Email Too Short"
const val ENTER_EMAIL = "Please Enter Email"
const val EMAIL_NOT_VALID = "Email Not Valid"
const val PWD_TOO_SHORT = "Please Enter Minimum 6 Character"
const val ENTER_PWD = "Please Enter Password"
const val ENTER_PWDc = "Please Enter Confirm Password"
const val ENTER_NPWDc = "Please Enter New Password"
const val ENTER_CPWD = "Please Enter Current Password"
const val TOO_SHORT = " Too Short"
const val PWD_NOT_MATCH = "Password Does Not Match"
const val PLEASE_SELECT = "Please Select"

//"Japan", "China", "Germany", "United Kingdom", "United States", "France", "India"
var MainCountryList = listOf<AllListItem>()

//"Buddhist", "Catholic", "Christian", "Muslim", "Hindu", "Jewish", "Nothing"
var MainReligionList = listOf<AllListItem>()

//"Doctor", "University", "Master", "High School", "Nothing"
var MainEducationList = listOf<AllListItem>()

//"Partner", "Friend"
var MainRelationList = listOf<AllListItem>()

//"Married", "Divorce", "Single", "Widowed"
var MainMaritalList = listOf<AllListItem>()

//"No kids", "Have Kids", "Want Kids", "N/A"
var MainKidsList = listOf<AllListItem>()
var ZodiacList = listOf<AllListItem>()
var AnimalList = listOf<AllListItem>()

//"Beginner", "Intermediate", "Master"
var MainInterestLevelList = listOf<AllListItem>()

fun isValidEmailId(email: String): Boolean {
    return Pattern.compile(
        "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
    ).matcher(email).matches()
}

fun List<AllListItem>.findIdOfItem(name: String): String = find { it.name == name }?.id.toString()
fun List<AllListItem>.findIdOfItemTitle(name: String): String = find { it.title == name }?.id.toString()

fun Modifier.conditional(condition: Boolean, modifier: Modifier.() -> Modifier): Modifier {
    return if (condition) {
        then(modifier(Modifier))
    } else {
        this
    }
}

fun Context.showToast(msg: String?) {
    msg?.let {
       // Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
       /* Toasty.custom(
            this,
            it,
            R.drawable.betashn,
            R.color.toest,
            3000,
            true,
            true
        ).show()*/
        DesignerToast.Custom(this,it, Gravity.CENTER,Toast.LENGTH_SHORT,
            R.drawable.circletoeast,12,"#FFFFFFFF",R.drawable.osotoeaast, 80, 15);

    }
}

private const val animationDuration = 200
fun slideInEnterAnimation(): EnterTransition {
    return slideInHorizontally(
        animationSpec = tween(durationMillis = animationDuration),
        initialOffsetX = { fullHeight -> fullHeight + fullHeight })
}

fun slideInExitAnimation(): ExitTransition {
    return slideOutHorizontally(
        animationSpec = tween(durationMillis = animationDuration),
        targetOffsetX = { fullHeight -> 0 - fullHeight })
}

