package com.soujunior.petjournal.ui.screensapp.screenTutor.tutorScreen

data class TutorState(
    val nameUser: String = "",
    val isLoadingUserName: Boolean = false,
    val hasErrorOnNameUser: Boolean = false,
)
