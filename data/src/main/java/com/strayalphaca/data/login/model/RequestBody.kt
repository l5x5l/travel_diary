package com.strayalphaca.data.login.model

data class IssueAuthCodeBody(val email : String)

data class SignUpRequestBody(val email : String, val password : String)

data class LoginRequestBody(val email : String, val password : String)