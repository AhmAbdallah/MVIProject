package com.example.mviproject

sealed class MainViewState {
    //idle
    object Idle: MainViewState()
    //number
    data class Number(val number: Int): MainViewState()
    //Error
    data class Error(val error: String): MainViewState()
}