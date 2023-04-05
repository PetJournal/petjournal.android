package com.soujunior.petjournal.ui.registerScreen

import androidx.lifecycle.viewModelScope
import com.soujunior.domain.entities.auth.RegisterModel
import com.soujunior.domain.usecase.auth.RegisterUseCase
import kotlinx.coroutines.launch

class RegisterScreenViewModelImpl(
    private val registerUseCase: RegisterUseCase
) : RegisterScreenViewModel() {
    override fun postForm(form: RegisterModel) {
        viewModelScope.launch {
            var result = registerUseCase.execute(form)
            result.handleResult(::success, ::failed)
        }
    }

    override fun failed() {
        TODO("Not yet implemented")
    }

    override fun success(resultPostRegister: String) {
        TODO("Not yet implemented")
    }
}