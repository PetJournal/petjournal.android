package com.soujunior.petjournal.ui.accountManager.registerScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.entities.auth.RegisterModel
import com.soujunior.domain.repository.ValidationRepository
import com.soujunior.domain.usecase.auth.RegisterUseCase
import com.soujunior.domain.usecase.auth.util.ValidationResult
import com.soujunior.petjournal.ui.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegisterScreenViewModelImpl(
    private val registerUseCase: RegisterUseCase,
    private val validation: ValidationRepository
) : RegisterScreenViewModel() {
    override var state by mutableStateOf(RegisterFormState())
    override val validationEventChannel = Channel<ValidationEvent>()
    override val validationEvents = validationEventChannel.receiveAsFlow()
    override val success = MutableLiveData<String>()
    override val error = MutableLiveData<String>()

    override fun success(resultPostRegister: String) {
        this.success.value = resultPostRegister
    }

    override fun failed(exception: Throwable?) {
        if (exception is Error) {
            this.error.value = exception.message
        } else {
            this.error.value = "lançar um erro aqui"
        }
    }

    private fun hasError(result: ValidationResult): Boolean {
        return listOf(result).any { !it.success }
    }

    override fun enableButton(): Boolean {
        val nameResult = validation.validateName(state.name)
        val lastNameResult = validation.validateLastName(state.lastName)
        val emailResult = validation.validateEmail(state.email)
        val phoneResult = validation.validatePhone(state.phone)
        val passwordResult =
            validation.validatePassword(password = state.password, newPassword = state.confirmPassword)
        val newPasswordResult = validation.validateNewPassword(state.password, state.confirmPassword)

        return state.name.isNotBlank() &&
                state.lastName.isNotBlank() &&
                state.email.isNotBlank() &&
                state.password.isNotBlank() &&
                state.confirmPassword.isNotBlank() &&
                nameResult.errorMessage == null &&
                lastNameResult.errorMessage == null &&
                emailResult.errorMessage == null &&
                phoneResult.errorMessage == null &&
                passwordResult.errorMessage == null &&
                newPasswordResult.errorMessage == null &&
                state.privacyPolicy
    }

    private fun change(
        name: String? = null,
        lastName: String? = null,
        email: String? = null,
        phone: String? = null,
        password: String? = null,
        newPassword: String? = null,
        privacy: Boolean? = null
    ) {
        when {
            name != null -> {
                state = state.copy(name = name)
                val nameResult = validation.validateName(state.name)
                state = if (hasError(nameResult)) state.copy(nameError = nameResult.errorMessage)
                else state.copy(nameError = null)
            }

            lastName != null -> {
                state = state.copy(lastName = lastName)
                val lastNameResult = validation.validateLastName(state.lastName)
                state =
                    if (hasError(lastNameResult)) state.copy(lastNameError = lastNameResult.errorMessage)
                    else state.copy(lastNameError = null)
            }

            email != null -> {
                state = state.copy(email = email)
                val emailResult = validation.validateEmail(state.email)
                state = if (hasError(emailResult)) state.copy(emailError = emailResult.errorMessage)
                else state.copy(emailError = null)
            }

            phone != null -> {
                state = state.copy(phone = phone)
                val phoneResult = validation.validatePhone(state.phone)
                state = if (hasError(phoneResult)) state.copy(phoneError = phoneResult.errorMessage)
                else state.copy(phoneError = null)
            }

            password != null -> {
                state = state.copy(password = password)
                val passwordResult = validation.validatePassword(
                    password = state.password,
                    newPassword = state.confirmPassword
                )
                state =
                    if (hasError(passwordResult)) state.copy(passwordError = passwordResult.errorMessage)
                    else state.copy(passwordError = null)
                change(newPassword = state.confirmPassword)
            }

            newPassword != null -> {
                state = state.copy(confirmPassword = newPassword)
                val newPasswordResult = validation.validateNewPassword(
                    newPassword = state.confirmPassword,
                    password = state.password
                )
                state =
                    if (hasError(newPasswordResult)) state.copy(confirmPasswordError = newPasswordResult.errorMessage)
                    else state.copy(confirmPasswordError = null)
            }

            privacy != null -> {
                state = state.copy(privacyPolicy = privacy)
                val privacyPolicyResult =
                    validation.validatePrivacyPolicy(value = state.privacyPolicy)
                state =
                    if (hasError(privacyPolicyResult)) state.copy(privacyPolicy = privacyPolicyResult.success)
                    else state.copy(confirmPasswordError = null)
            }
        }
    }

    override fun onEvent(event: RegisterFormEvent) {
        when (event) {
            is RegisterFormEvent.NameChanged -> change(name = event.name)
            is RegisterFormEvent.LastNameChanged -> change(lastName = event.lastName)
            is RegisterFormEvent.EmailChanged -> change(email = event.email)
            is RegisterFormEvent.PhoneChanged -> change(phone = event.phone)
            is RegisterFormEvent.PasswordChanged -> change(password = event.password)
            is RegisterFormEvent.ConfirmPasswordChanged -> change(newPassword = event.confirmPassword)
            is RegisterFormEvent.PrivacyPolicyChanged -> change(privacy = event.privacyPolicy)
            is RegisterFormEvent.Submit -> submitData()
        }
    }

    override fun submitData() {
        viewModelScope.launch {
            val result = registerUseCase.execute(
                RegisterModel(
                    name = state.name,
                    lastName = state.lastName,
                    email = state.email,
                    phoneNumber = state.phone,
                    password = state.password,
                    privacyPolicy = state.privacyPolicy
                )
            )
            result.handleResult(::success, ::failed)
        }
    }
}