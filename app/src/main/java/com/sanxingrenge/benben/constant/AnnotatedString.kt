package com.sanxingrenge.benben.constant

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.sanxingrenge.benben.ui.theme.*


val signInUsingPasswordText = buildAnnotatedString {
    withStyle(style = SpanStyle(fontFamily = poppinsMedium, color = Nobel, fontSize = 11.sp)) {
        append("Sign In using")
    }
    pushStringAnnotation(
        tag = "SignUp",// provide tag which will then be provided when you click the text
        annotation = "SignUp"
    )
    withStyle(
        style = SpanStyle(
            fontFamily = poppinsSemiBold, color = VioletBlue, fontSize = 11.sp
        )
    ) {
        append(" Password")
    }
    pop()
}
val forgotPasswordText = buildAnnotatedString {
    pushStringAnnotation(
        tag = "SignUp",// provide tag which will then be provided when you click the text
        annotation = "SignUp"
    )
    withStyle(
        style = SpanStyle(
            fontFamily = poppinsMedium, color = VioletBlue, fontSize = 11.sp
        )
    ) {
        append("Forgot Password ?")
    }
    pop()
}
val resendCodeText = buildAnnotatedString {
    pushStringAnnotation(
        tag = "SignUp",// provide tag which will then be provided when you click the text
        annotation = "SignUp"
    )
    withStyle(
        style = SpanStyle(
            fontFamily = poppinsMedium, color = VioletBlue, fontSize = 13.sp
        )
    ) {
        append("Resend Code ?")
    }
    pop()
}
val signUpHelpCondition = buildAnnotatedString {
    withStyle(
        style = SpanStyle(
            color = Nobel, fontFamily = poppinsRegular, fontSize = 12.sp
        )
    ) {
        append("New to OSOMATE? ")
    }
    pushStringAnnotation(
        tag = "UserClicked",// provide tag which will then be provided when you click the text
        annotation = "UserTerms"
    )
    withStyle(
        style = SpanStyle(
            color = VioletBlue, fontFamily = poppinsMedium, fontSize = 12.sp
        )
    ) {
        append("Sign Up")
    }
    pop()
}
val alreadyHaveAccountHelpCondition = buildAnnotatedString {
    withStyle(
        style = SpanStyle(
            color = Nobel, fontFamily = poppinsRegular, fontSize = 12.sp
        )
    ) {
        append("Already have an account ? ")
    }
    pushStringAnnotation(
        tag = "UserClicked",// provide tag which will then be provided when you click the text
        annotation = "UserTerms"
    )
    withStyle(
        style = SpanStyle(
            color = VioletBlue, fontFamily = poppinsMedium, fontSize = 12.sp
        )
    ) {
        append("Sign In")
    }
    pop()
}
val annotatedSimpleText = buildAnnotatedString {
    withStyle(style = SpanStyle(fontFamily = poppinsSemiBold)) {
        append("Here is your")
    }
    pushStringAnnotation(
        tag = "SignUp",// provide tag which will then be provided when you click the text
        annotation = "SignUp"
    )
    withStyle(style = SpanStyle(fontFamily = poppinsBold)) {
        append(" compass\n")
    }
    withStyle(style = SpanStyle(fontFamily = poppinsSemiBold)) {
        append("to find your")
    }
    withStyle(style = SpanStyle(fontFamily = poppinsBold)) {
        append(" mate")
    }
    withStyle(style = SpanStyle(fontFamily = poppinsSemiBold)) {
        append("!")
    }
    pop()
}
val annotatedColoredText = buildAnnotatedString {
    withStyle(style = SpanStyle(color = Color.Black, fontFamily = poppinsSemiBold)) {
        append("Here is your")
    }
    pushStringAnnotation(
        tag = "SignUp",// provide tag which will then be provided when you click the text
        annotation = "SignUp"
    )
    withStyle(style = SpanStyle(color = ElectricBlue, fontFamily = poppinsBold)) {
        append(" compass\n")
    }
    withStyle(style = SpanStyle(color = Color.Black, fontFamily = poppinsSemiBold)) {
        append("to find your")
    }
    withStyle(style = SpanStyle(color = RichCarmine, fontFamily = poppinsBold)) {
        append(" mate")
    }
    withStyle(style = SpanStyle(color = Color.Black, fontFamily = poppinsSemiBold)) {
        append("!")
    }
    pop()
}
private val fontSizeForTerms = 10.sp
val annotatedColoredTermsCondition = buildAnnotatedString {
    withStyle(
        style = SpanStyle(
            color = Nobel, fontFamily = poppinsRegular, fontSize = fontSizeForTerms
        )
    ) {
        append("By signing up, you agree to the ")
    }
    pushStringAnnotation(
        tag = "UserClicked",// provide tag which will then be provided when you click the text
        annotation = "UserTerms",
    )
    withStyle(
        style = SpanStyle(
            color = VioletBlue, fontFamily = poppinsMedium, fontSize = fontSizeForTerms
        )
    ) {
        append("User Terms")
    }
    withStyle(
        style = SpanStyle(
            color = Nobel, fontFamily = poppinsRegular, fontSize = fontSizeForTerms
        )
    ) {
        append(" & ")
    }
    pushStringAnnotation(
        tag = "UserClickedd",// provide tag which will then be provided when you click the text
        annotation = "UserPrivacy"
    )
    withStyle(
        style = SpanStyle(
            color = VioletBlue, fontFamily = poppinsMedium, fontSize = fontSizeForTerms
        )
    ) {
        append("Privacy Policy")
    }
    pop()
}

fun chineseDescriptionCustomString(str: String) = buildAnnotatedString {
    withStyle(
        style = SpanStyle(
            fontFamily = poppinsLight, fontSize = 13.sp, color = VioletBlue
        )
    ) { append("*") }
    pushStringAnnotation(tag = "SignUp", annotation = "SignUp")
    withStyle(
        style = SpanStyle(
            fontFamily = poppinsLight, fontSize = 11.sp, color = SilverChalice
        )
    ) { append(" $str") }
    pop()
}