package com.soujunior.petjournal.ui.util

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soujunior.data.remote.AuthService
import com.soujunior.data.repository.AuthRepositoryImpl
import com.soujunior.domain.repository.AuthRepository
import kotlinx.coroutines.launch

class UserViewModel (authApi: AuthService, context :  Context ): ViewModel() {

    private val repository = AuthRepositoryImpl(authApi, context)
    private val _userName = mutableStateOf("")
    val userName: State<String> = _userName

    fun loadUserData(firstName : String) {
        viewModelScope.launch {
            val userData = repository.getGuardianName(firstName)
            _userName.value = userData.firstName
        }

    }
}