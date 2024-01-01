package com.strayalphaca.presentation.screens.settings.withdrawal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalphaca.travel_diary.domain.login.use_case.UseCaseWithdrawal
import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalphaca.presentation.models.event_flow.MutableEventFlow
import com.strayalphaca.presentation.models.event_flow.asEventFlow
import com.strayalphaca.travel_diary.core.presentation.logger.UserEventLogger
import com.strayalphaca.travel_diary.core.presentation.logger.UserLogEvent
import com.strayalphaca.travel_diary.diary.use_case.UseCaseGetDiaryCount
import com.strayalphaca.travel_diary.domain.auth.usecase.UseCaseClearToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WithdrawalViewModel @Inject constructor(
    private val useCaseWithdrawal: UseCaseWithdrawal,
    private val useCaseGetDiaryCount: UseCaseGetDiaryCount,
    private val useCaseClearToken: UseCaseClearToken,
    private val userEventLogger: UserEventLogger
) : ViewModel() {
    private val _totalDiaryCount = MutableStateFlow(0)
    val totalDiaryCount = _totalDiaryCount.asStateFlow()

    private val _initDataLoading = MutableStateFlow(true)
    val initDataLoading = _initDataLoading.asStateFlow()

    private val _deleteLoading = MutableStateFlow(false)
    val deleteLoading = _deleteLoading.asStateFlow()

    private val _toastMessage = MutableEventFlow<String>()
    val toastMessage = _toastMessage.asEventFlow()

    private val _withdrawalSuccess = MutableEventFlow<Boolean>()
    val withdrawalSuccess = _withdrawalSuccess.asEventFlow()

    private val _showCheckDialog = MutableStateFlow(false)
    val showCheckDialog = _showCheckDialog.asStateFlow()

    fun initDataLoading() {
        _initDataLoading.value = true
        viewModelScope.launch {
            val response = useCaseGetDiaryCount()
            if (response is BaseResponse.Success<Int>) {
                _totalDiaryCount.value = response.data
            }
            _initDataLoading.value = false
        }
    }

    fun withdrawal() {
        _initDataLoading.value = true
        viewModelScope.launch {
            val res = useCaseWithdrawal()
            if (res is BaseResponse.EmptySuccess) {
                useCaseClearToken()
                _deleteLoading.value = false
                _withdrawalSuccess.emit(true)
                userEventLogger.log(UserLogEvent.Withdrawal)
            } else {
                val errorMessage = (res as BaseResponse.Failure).errorMessage
                _deleteLoading.value = false
                _toastMessage.emit(errorMessage)
            }
        }
    }

    fun showDialog() {
        _showCheckDialog.value = true
    }

    fun closeDialog() {
        _showCheckDialog.value = false
    }
}