package com.soujunior.petjournal.ui.util

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soujunior.data.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val userRepository = UserRepository()
    private val _userName = mutableStateOf("")
    val userName: State<String> = _userName

    fun loadUserData() {
        viewModelScope.launch {
            val userData = userRepository.getUserData()
            _userName.value = userData.userName
        }
    }
}