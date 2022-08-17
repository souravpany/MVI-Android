package com.example.androidmviapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidmviapp.data.models.UserApiResponse
import com.example.androidmviapp.data.repository.GetUserRepository
import com.example.androidmviapp.ui.intent.MainIntent
import com.example.androidmviapp.ui.viewstate.MainViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getUserRepository: GetUserRepository) :
    ViewModel() {

    val mainIntent: Channel<MainIntent> = Channel(Channel.UNLIMITED)

    private val _state = MutableStateFlow<MainViewState>(MainViewState.Empty)
    val state: StateFlow<MainViewState>
        get() = _state


    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            mainIntent.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.GetUsers -> fetchUsers()
                }
            }
        }
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            _state.value = MainViewState.Loading
            try {
                _state.value =
                    MainViewState.Success(
                        data = getUserRepository.getUsersApiDetails().first()
                    )
            } catch (e: Exception) {
                _state.value = MainViewState.Error(message = e.message.toString())
            }
        }
    }

}