package com.strayalphaca.travel_diary.core.presentation.logger

interface ScreenLogger {
    fun log(event : ScreenLogEvent)
}

data class ScreenLogEvent(val route : String)

interface UserEventLogger {
    fun log(event : UserLogEvent)
}

sealed class UserLogEvent(val eventName : String) {
    class CreateDiary(val imageCount : Int, val voiceFileExist : Boolean, val locationName : String) : UserLogEvent("create_diary")
    class ModifyDiary(val imageCount : Int, val voiceFileExist: Boolean, val locationName: String) : UserLogEvent("modify_diary")
    object RemoveDiary : UserLogEvent("delete_diary")
    object Signup : UserLogEvent("sign_up")
    object Login : UserLogEvent("login")
    object Logout : UserLogEvent("logout")
    object Withdrawal : UserLogEvent("withdrawal")
    class ChangeMonth(val year : Int, val month : Int) : UserLogEvent("change_month")
}