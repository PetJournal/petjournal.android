package com.soujunior.petjournal.ui.loginScreen

import com.soujunior.data.model.request.LoginModel
import com.soujunior.data.repository.AuthRepositoryImpl
import com.soujunior.domain.use_case.auth.LoginUseCase
import com.soujunior.domain.use_case.auth.util.ValidationRepositoryImpl
import com.soujunior.domain.use_case.auth.util.ValidationResult
import com.soujunior.domain.use_case.base.DataResult
import com.soujunior.petjournal.ui.accountManager.loginScreen.LoginFormEvent
import com.soujunior.petjournal.ui.accountManager.loginScreen.LoginFormState
import com.soujunior.petjournal.ui.accountManager.loginScreen.LoginViewModelImpl
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class LoginViewModelImplTest {
    private val loginUseCase = mockk<LoginUseCase>(relaxed = true)
    private val validation = mockk<ValidationRepositoryImpl>(relaxed = true)
    private val viewModel = LoginViewModelImpl(
        loginUseCase = loginUseCase,
        validation = validation,
    )

    @Before
    fun setup() { Dispatchers.setMain(Dispatchers.Unconfined) }
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Update state with email and password error if validation fails`() {
        val emailResult = ValidationResult(false, listOf("Invalid email"))
        val passwordResult = ValidationResult(false, listOf("Invalid password"))

        coEvery { validation.validateEmail(any()) } returns emailResult
        coEvery { validation.validateField(any()) } returns passwordResult

        viewModel.state = LoginFormState(email = "petjournal@example.com", password = "password123")

        viewModel.submitData()

        assertEquals(emailResult.errorMessage, viewModel.state.emailError)
        assertEquals(passwordResult.errorMessage, viewModel.state.passwordError)
    }

    @Test
    fun `When onEvent is called, state email and password should update with the value`() {
        viewModel.onEvent(LoginFormEvent.EmailChanged("newemail@example.com"))
        viewModel.onEvent(LoginFormEvent.PasswordChanged("123password"))
        assertEquals("newemail@example.com", viewModel.state.email)
        assertEquals("123password", viewModel.state.password)
    }

    @Test
    fun `if failed() is call, sets error message`() {
        val errorMessage = "Test Error"
        viewModel.failed(Error(errorMessage))
        assertEquals(errorMessage, viewModel.message.value)
    }

    @Test
    fun `if success() is call, sets success message`() {
        val successMessage = "Test Success"
        viewModel.success(successMessage)
        assertEquals(successMessage, viewModel.message.value)
    }

    @Test
    fun `when submitData() is called, updates state and calls success`() {
        val emailResult = "petjournal@exemple.com"
        val passwordResult = "password123456"

        every { validation.validateEmail(emailResult) } returns ValidationResult(
            success = true,
            errorMessage = null
        )
        every { validation.validateField(passwordResult) } returns ValidationResult(
            success = true,
            errorMessage = null
        )

        coEvery {
            loginUseCase.execute(
                LoginModel(
                    email = emailResult,
                    password = passwordResult
                )
            )
        } returns DataResult.Success("Sucesso na requisição!")

        viewModel.state = LoginFormState(email = emailResult, password = passwordResult)
        viewModel.submitData()
        assertEquals("Sucesso na requisição!", viewModel.message.value)
    }
}

/*
override fun enableButton(): Boolean {
    val nameResult =        validation.validateName(state.name)
    val lastNameResult =    validation.validateLastName(state.lastName)
    val emailResult =       validation.validateEmail(state.email)
    val phoneResult =       validation.validatePhone(state.phone)
    val passwordResult =    validation.validatePassword(password = state.password)
    val repeatedPasswordResult =
                            validation.validateRepeatedPassword(
                                state.password,
                                state.repeatedPassword
                            )

    return

            state.privacyPolicy
}
*/