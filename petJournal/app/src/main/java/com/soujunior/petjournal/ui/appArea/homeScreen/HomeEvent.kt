package com.soujunior.petjournal.ui.appArea.homeScreen

sealed class HomeEvent {

    data class GetUserName(val name: String) : HomeEvent()


}