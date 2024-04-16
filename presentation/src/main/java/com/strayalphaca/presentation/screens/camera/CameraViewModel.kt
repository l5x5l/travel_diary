package com.strayalphaca.presentation.screens.camera

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalphaca.presentation.models.event_flow.MutableEventFlow
import com.strayalphaca.presentation.models.event_flow.asEventFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class CameraViewModel @Inject constructor(

) : ViewModel() {

    private val _screenState = MutableStateFlow<CameraScreenState>(CameraScreenState.Camera)
    val screenState : StateFlow<CameraScreenState> = _screenState.asStateFlow()

    private val _moveToBackStack = MutableEventFlow<String?>()
    val moveToBackStack = _moveToBackStack.asEventFlow()

    // 사진 확인 화면에서 이전 카메라 화면으로 돌아가는 경우
    fun moveToCameraState() {
        _screenState.update {
            if (it is CameraScreenState.Camera) { it }
            else { CameraScreenState.Camera }
        }
    }
    
    fun onBackPressed() {
        when (screenState.value) {
            is CameraScreenState.Camera -> {
                viewModelScope.launch {
                    _moveToBackStack.emit(null)
                }
            }
            is CameraScreenState.PhotoConfirmation -> {
                moveToCameraState()
            }
        }
    }

    // 사진 확인에서 확정 버튼 클릭
    fun confirmPhoto() {

    }

    // 카메라 화면에서 사진 촬영 클릭
    fun takePhoto(bitmap : Bitmap) {
        _screenState.update {
            CameraScreenState.PhotoConfirmation(bitmap)
        }
    }
}

sealed interface CameraScreenState {
    object Camera : CameraScreenState
    data class PhotoConfirmation(val bitmap: Bitmap) : CameraScreenState // bitmap이 아닐 수도?
}