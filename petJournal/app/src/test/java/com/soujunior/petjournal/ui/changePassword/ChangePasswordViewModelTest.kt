package com.soujunior.petjournal.ui.changePassword

import android.annotation.SuppressLint
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import assertk.assertThat
import com.soujunior.domain.entities.auth.PasswordModel
import com.soujunior.domain.usecase.auth.ChangePasswordUseCase
import com.soujunior.domain.usecase.auth.util.ValidationRepositoryImpl
import com.soujunior.domain.usecase.auth.util.ValidationResult
import com.soujunior.domain.usecase.base.DataResult
import com.soujunior.petjournal.ui.util.ValidationEvent
import com.soujunior.petjournal.ui.accountManager.changePasswordScreen.ChangePasswordFormEvent
import com.soujunior.petjournal.ui.accountManager.changePasswordScreen.ChangePasswordFormState
import com.soujunior.petjournal.ui.accountManager.changePasswordScreen.ChangePasswordViewModelImpl
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.spyk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ChangePasswordViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    private val validation = mockk<ValidationRepositoryImpl>(relaxed = true)
    private val changeUseCase = mockk<ChangePasswordUseCase>(relaxed = true)

    private val passwordValidationResult = ValidationResult(true)
    private val repeatedPasswordValidationResult = ValidationResult(true)

    private val viewModel = spyk(
        ChangePasswordViewModelImpl(
            changeUseCase,
            validation
        )
    )

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @SuppressLint("CheckResult")
    @Test
    fun `success should update success value and send ValidationEvent Success`() {

        val result = "Success Result"
        coEvery { viewModel.validationEventChannel.send(ValidationEvent.Success) } just Runs
        coEvery { viewModel.disconnectOtherDevices() } just Runs
        coEvery { viewModel.changePassword.execute(any()) } returns DataResult.Success(String())

        viewModel.success(result)

        coVerify { viewModel.validationEventChannel.send(ValidationEvent.Success) }
        TestCase.assertEquals(result, viewModel.success.value)
    }

    @Test
    fun `success should not send ValidationEvent Success when an exception is thrown`() {

        val result = "Success Result"
        coEvery { viewModel.validationEventChannel.send(ValidationEvent.Success) } just Runs
        coEvery { viewModel.disconnectOtherDevices() } just Runs
        coEvery { viewModel.changePassword.execute(any()) } returns DataResult.Failure(Exception())

        viewModel.success(result)

        coVerify { viewModel.validationEventChannel.send(ValidationEvent.Success) }
        TestCase.assertEquals(result, viewModel.success.value)
    }

    @Test
    fun `failed should send ValidationEvent Failed and update error value with exception message`() {

        val exceptionMessage = "lançar um erro aqui"
        val exception = Exception(exceptionMessage)
        coEvery { viewModel.validationEventChannel.send(ValidationEvent.Failed) } just Runs

        viewModel.failed(exception)

        coVerify { viewModel.validationEventChannel.send(ValidationEvent.Failed) }
        TestCase.assertEquals(exceptionMessage, viewModel.error.value)
    }

    @Test
    fun `enableButton should return true when all conditions are met`() {

        every { validation.validatePassword(any()) } returns passwordValidationResult
        every {
            validation.validateRepeatedPassword(
                any(),
                any()
            )
        } returns repeatedPasswordValidationResult

        viewModel.state.password = "123456"
        viewModel.state.repeatedPassword = "123456"

        val result = viewModel.enableButton()

        TestCase.assertEquals(true, result)
    }

    @Test
    fun `enableButton should return false when password is blank`() {

        every { validation.validatePassword(any()) } returns passwordValidationResult
        every {
            validation.validateRepeatedPassword(
                any(),
                any()
            )
        } returns repeatedPasswordValidationResult

        viewModel.state.password = ""
        viewModel.state.repeatedPassword = "123456"

        val result = viewModel.enableButton()

        TestCase.assertEquals(false, result)
    }

    @Test
    fun `enableButton should return false when repeated password is blank`() {

        every { validation.validatePassword(any()) } returns passwordValidationResult
        every {
            validation.validateRepeatedPassword(
                any(),
                any()
            )
        } returns repeatedPasswordValidationResult

        viewModel.state.password = "123456"
        viewModel.state.repeatedPassword = ""

        val result = viewModel.enableButton()

        TestCase.assertEquals(false, result)
    }

    @Test
    fun `enableButton should return false when password validation fails`() {

        val passwordValidationResult = ValidationResult(false)

        every { validation.validatePassword(any()) } returns passwordValidationResult
        every { validation.validateRepeatedPassword(any(), any()) } returns ValidationResult(false)

        viewModel.state.password = "123"
        viewModel.state.repeatedPassword = "123456"

        val result = viewModel.enableButton()

        TestCase.assertEquals(false, !result)
    }

    @Test
    fun `enableButton should return false when repeated password validation fails`() {

        val repeatedPasswordValidationResult = ValidationResult(true)

        every { validation.validatePassword(any()) } returns ValidationResult(true)
        every {
            validation.validateRepeatedPassword(
                any(),
                any()
            )
        } returns repeatedPasswordValidationResult

        viewModel.state.password = "123456"
        viewModel.state.repeatedPassword = "654321"

        val result = viewModel.enableButton()

        TestCase.assertEquals(false, !result)
    }

    @Test
    fun `change should update password and passwordError when password is not null`() {

        val password = "newPassword"
        every { validation.validatePassword(any()) } returns ValidationResult(true)
        val currentState = viewModel.state.copy(password = "newPassword")
        every { viewModel.state } returns currentState

        viewModel.change(password = password)

        TestCase.assertEquals(
            currentState.copy(password = password, passwordError = null),
            viewModel.state
        )
    }

    @Test
    fun `change should update repeatedPassword and repeatedPasswordError when repeatedPassword is not null`() {

        val repeatedPassword = "newRepeatedPassword"
        every { validation.validateRepeatedPassword(any(), any()) } returns ValidationResult(true)
        val currentState =
            viewModel.state.copy(repeatedPassword = "newRepeatedPassword", password = "password")
        every { viewModel.state } returns currentState

        viewModel.change(repeatedPassword = repeatedPassword)

        TestCase.assertEquals(
            currentState.copy(
                repeatedPassword = repeatedPassword,
                passwordError = null
            ), viewModel.state
        )
    }

    @Test
    fun `change should update disconnectOtherDevices when disconnect is not null`() {

        val currentState = viewModel.state.copy(disconnectOtherDevices = true)
        every { viewModel.state } returns currentState

        viewModel.change(disconnect = true)

        TestCase.assertEquals(currentState.copy(disconnectOtherDevices = true), viewModel.state)
    }

    @Test
    fun `onEvent should call change with password when PasswordChanged event is received`() {

        val password = "newPassword"
        val event = ChangePasswordFormEvent.PasswordChanged(password)
        every { viewModel.change(password = any()) } just Runs
        every { viewModel.state } returns ChangePasswordFormState()

        viewModel.onEvent(event)

        assertThat(viewModel.change(password = password))
    }

    @Test
    fun `onEvent should call change with repeatedPassword when ConfirmPasswordChanged event is received`() {

        val confirmPassword = "newConfirmPassword"
        val event = ChangePasswordFormEvent.ConfirmPasswordChanged(confirmPassword)
        every { viewModel.change(repeatedPassword = any()) } just Runs
        every { viewModel.state } returns ChangePasswordFormState()

        viewModel.onEvent(event)

        assertThat(viewModel.change(repeatedPassword = confirmPassword))
    }

    @Test
    fun `onEvent should call change with disconnect when DisconnectOtherDevices event is received`() {

        val disconnect = true
        val event = ChangePasswordFormEvent.DisconnectOtherDevices(disconnect)
        every { viewModel.change(disconnect = any()) } just Runs
        every { viewModel.state } returns ChangePasswordFormState()

        viewModel.onEvent(event)

        assertThat(viewModel.change(disconnect = disconnect))
    }

    @Test
    fun `submitNewPassword should call disconnectOtherDevices, execute changePassword, and handleResult`() {

        val newPassword = "newPassword"
        val currentState = ChangePasswordFormState(password = newPassword)
        val passwordModel = PasswordModel(password = newPassword)
        val successResult = DataResult.Success("Success")
        coEvery { viewModel.changePassword.execute(passwordModel) } returns successResult
        every { viewModel.success(any()) } just Runs
        every { viewModel.failed(any()) } just Runs
        every { viewModel.state } returns currentState

        runBlocking {
            viewModel.submitNewPassword()
        }

        coVerifyOrder {
            viewModel.disconnectOtherDevices()
            viewModel.changePassword.execute(passwordModel)
            viewModel.success("Success")
        }
        coVerify(exactly = 0) { viewModel.failed(any()) }
    }

    @Test
    fun `submitNewPassword should call disconnectOtherDevices, execute changePassword, and call failed when an error occurs`() {

        val newPassword = "newPassword"
        val currentState = ChangePasswordFormState(password = newPassword)
        val passwordModel = PasswordModel(password = newPassword)
        val errorResult = DataResult.Failure(Exception("Error"))
        coEvery { viewModel.changePassword.execute(passwordModel) } returns errorResult
        every { viewModel.failed(any()) } just Runs
        every { viewModel.state } returns currentState

        runBlocking {
            viewModel.submitNewPassword()
        }

        coVerifyOrder {
            viewModel.disconnectOtherDevices()
            viewModel.changePassword.execute(passwordModel)
            viewModel.failed(any())
        }
        coVerify(exactly = 0) { viewModel.success(any()) }
    }
}