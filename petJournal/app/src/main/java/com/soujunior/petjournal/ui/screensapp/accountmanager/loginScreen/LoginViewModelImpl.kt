package com.soujunior.petjournal.ui.screensapp.accountmanager.loginScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.model.request.LoginModel
import com.soujunior.domain.model.request.LoginPreferenceModel
import com.soujunior.domain.repository.validation.ValidationRepository
import com.soujunior.domain.use_case.auth.GetLoginPreferenceUseCase
import com.soujunior.domain.use_case.auth.LoginUseCase
import com.soujunior.domain.use_case.auth.SaveLoginPreferenceUseCase
import com.soujunior.domain.use_case.guardian.SaveGuardianContactUseCase
import com.soujunior.domain.use_case.util.ValidationResult
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModelImpl(
    private val loginUseCase: LoginUseCase,
    private val validation: ValidationRepository,
    private val saveLoginPreferenceUseCase: SaveLoginPreferenceUseCase,
    private val getLoginPreferenceUseCase: GetLoginPreferenceUseCase,
    private val saveGuardianContactUseCase: SaveGuardianContactUseCase,
) : LoginViewModel() {
    override var state by mutableStateOf(LoginFormState())
    override val validationEventChannel = Channel<ValidationEvent>()
    override val validationEvents = validationEventChannel.receiveAsFlow()

    override val message: StateFlow<String> get() = setMessage
    private val setMessage = MutableStateFlow("")

    private val _taskState: MutableStateFlow<TaskState> = MutableStateFlow(TaskState.Idle)
    override val taskState: StateFlow<TaskState> = _taskState

    init {
        viewModelScope.launch {
            val result = getLoginPreferenceUseCase.execute(Unit)
            result.handleResult({ preference ->
                if (preference != null) {
                    state =
                        state.copy(
                            email = preference.email,
                            password = preference.password,
                            rememberPassword = preference.isRemember,
                        )
                }
            }, {
                // Erro ao carregar as preferências
            })
        }
    }

    override fun failed(exception: Throwable?) {
        Log.e("PJ_LOGIN", "[ViewModel] login FAILED: ${exception?.message}", exception)
        setMessage.value =
            when (exception) {
                is java.net.SocketTimeoutException -> "Tempo de conexão esgotado. Verifique sua internet ou tente novamente mais tarde."
                is java.net.UnknownHostException -> "Não foi possível conectar ao servidor. Verifique sua conexão com a internet."
                is java.net.ConnectException -> "Falha ao conectar ao servidor. O serviço pode estar temporariamente indisponível."
                else -> exception?.message ?: "Erro desconhecido!"
            }
        viewModelScope.launch { validationEventChannel.send(ValidationEvent.Failed) }
    }

    override fun success(resulMessage: String) {
        Log.d("PJ_LOGIN", "[ViewModel] login SUCCESS: $resulMessage")
        setMessage.value = resulMessage
        viewModelScope.launch {
            passwordRemember()
            saveGuardianContactUseCase.execute(state.email)
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    override fun passwordRemember() {
        viewModelScope.launch {
            if (state.rememberPassword) {
                saveLoginPreferenceUseCase.execute(
                    LoginPreferenceModel(
                        email = state.email,
                        password = state.password,
                        isRemember = true,
                    ),
                )
            } else {
                saveLoginPreferenceUseCase.execute(
                    LoginPreferenceModel(
                        email = "",
                        password = "",
                        isRemember = false,
                    ),
                )
            }
        }
    }

    private fun hasError(result: ValidationResult): Boolean {
        return listOf(result).any { !it.success }
    }

    override fun enableButton(): Boolean {
        val emailResult = validation.validateEmail(state.email)
        val passwordResult = validation.validatePassword(password = state.password)
        return state.email.isNotBlank() &&
            state.password.isNotBlank() &&
            emailResult.errorMessage == null &&
            passwordResult.errorMessage == null
    }

    private fun change(
        email: String? = null,
        password: String? = null,
        isRemember: Boolean? = null,
    ) {
        when {
            email != null -> {
                state = state.copy(email = email)
                val emailResult = validation.validateEmail(state.email)
                state =
                    if (hasError(emailResult)) {
                        state.copy(emailError = emailResult.errorMessage)
                    } else {
                        state.copy(emailError = null)
                    }
            }

            password != null -> {
                state = state.copy(password = password)
                val passwordResult = validation.validatePassword(state.password)
                state =
                    if (hasError(passwordResult)) {
                        state.copy(passwordError = passwordResult.errorMessage)
                    } else {
                        state.copy(passwordError = null)
                    }
            }

            isRemember != null -> {
                state = state.copy(rememberPassword = isRemember)
            }
        }
    }

    override fun onEvent(event: LoginFormEvent) {
        when (event) {
            is LoginFormEvent.EmailChanged -> change(email = event.email)
            is LoginFormEvent.PasswordChanged -> change(password = event.password)
            is LoginFormEvent.RememberPassword -> change(isRemember = event.isRemember)
            is LoginFormEvent.Submit -> submitData()
        }
    }

    override fun submitData() {
        Log.d("PJ_LOGIN", "[ViewModel] submitData() chamado")
        val emailResult = validation.validateEmail(state.email)
        val passwordResult = validation.validateField(state.password)
        val hasError = listOf(emailResult, passwordResult).any { !it.success }

        if (hasError) {
            Log.w("PJ_LOGIN", "[ViewModel] validação falhou: email=${emailResult.errorMessage}, pass=${passwordResult.errorMessage}")
            state =
                state.copy(
                    emailError = emailResult.errorMessage,
                    passwordError = passwordResult.errorMessage,
                )
            return
        }

        Log.d("PJ_LOGIN", "[ViewModel] validação OK, iniciando login...")
        _taskState.value = TaskState.Loading
        viewModelScope.launch {
            try {
                val result =
                    loginUseCase.execute(
                        LoginModel(
                            email = state.email,
                            password = state.password,
                        ),
                    )

                Log.d("PJ_LOGIN", "[ViewModel] loginUseCase retornou: ${result::class.simpleName}")
                result.handleResult(::success, ::failed)
            } catch (e: Throwable) {
                Log.e("PJ_LOGIN", "[ViewModel] EXCEPTION não tratada no submitData", e)
                failed(e)
            } finally {
                _taskState.value = TaskState.Idle
            }
        }
    }
}
