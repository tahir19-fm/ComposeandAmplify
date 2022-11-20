package com.team.authwithcomposeandamplify


data class SignUpState(
    var username: String = "",
    var email: String = "",
    var password: String = ""
)

data class LoginState(
    var username: String = "",
    var password: String = ""
)

data class VerificationCodeState(
    var username: String = "",
    var code: String = ""
)