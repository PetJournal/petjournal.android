package com.soujunior.petjournal.ui.screensapp.screenTutor.tutorScreen

data class TutorState(
    val nameUser: String = "",
    val email: String = "",
    val isLoadingUserName: Boolean = false,
    val hasErrorOnNameUser: Boolean = false,
    val isFeedbackEnabled: Boolean = false,
)
