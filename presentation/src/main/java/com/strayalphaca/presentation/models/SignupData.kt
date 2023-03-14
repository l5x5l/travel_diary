package com.strayalphaca.presentation.models

class SignupData private constructor(
    private var email : String = ""
) {
    companion object {
        private var signupData : SignupData ?= null

        fun getInstance() : SignupData {
            if (signupData == null) {
                signupData = SignupData()
            }
            return signupData!!
        }

        fun clearAndGetInstance() : SignupData {
            clearInstance()
            return getInstance()
        }

        fun clearInstance() {
            signupData = null
        }
    }

    fun setEmail(inputEmail : String) {
        email = inputEmail
    }

    fun getEmail() = email

    fun clearEmail() {
        email = ""
    }
}