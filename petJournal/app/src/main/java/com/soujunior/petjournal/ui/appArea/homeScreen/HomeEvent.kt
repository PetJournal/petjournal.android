package com.soujunior.petjournal.ui.appArea.homeScreen

sealed class HomeEvent {
    //TODO: (Gelson) remova os métdos não utilizados
    data class GetUserName(val name: String) : HomeEvent()


}