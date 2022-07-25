package com.example.mviproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class AddNumberViewModel: ViewModel() {
    //Create Intent Channel--- Taking items from MainActivity By Using Coroutines's Channel
    val intentChannel = Channel<MainIntent>(Channel.UNLIMITED)

    //Send items from ViewModel to MainActivity using flow
    private val _viewState = MutableStateFlow<MainViewState>(MainViewState.Idle)

    val state: StateFlow<MainViewState> get() = _viewState

    private var number = 0

    init {
        processIntent()
    }

    //Process
    private fun processIntent() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.AddNumber -> addNumber()
                }
            }
        }
    }
    //Reduce
    private  fun addNumber() {
        viewModelScope.launch {
            _viewState.value = try {
                MainViewState.Number(++ number)
            } catch (e: Exception) {
                MainViewState.Error(e.message!!)
            }
        }
    }

}