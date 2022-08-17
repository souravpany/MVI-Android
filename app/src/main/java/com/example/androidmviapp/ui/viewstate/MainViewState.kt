package com.example.androidmviapp.ui.viewstate

import com.example.androidmviapp.data.models.UserApiResponse

sealed class MainViewState {
    object Empty : MainViewState()
    object Loading : MainViewState()
    class Error(val message: String) : MainViewState()
    class Success(val data: List<UserApiResponse>) : MainViewState()
}
