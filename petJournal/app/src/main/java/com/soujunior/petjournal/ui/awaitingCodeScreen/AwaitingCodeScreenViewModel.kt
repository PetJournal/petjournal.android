package com.soujunior.petjournal.ui.loginScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.soujunior.domain.entities.auth.LoginModel

abstract class AwaitingCodeScreenViewModel : ViewModel() {

    abstract val success: LiveData<String>
    abstract val error: LiveData<String>

}
