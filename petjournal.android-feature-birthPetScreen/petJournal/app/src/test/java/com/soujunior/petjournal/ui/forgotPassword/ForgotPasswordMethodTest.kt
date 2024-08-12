package com.soujunior.petjournal.ui.forgotPassword

import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.soujunior.domain.use_case.auth.ForgotPasswordUseCase
import com.soujunior.domain.use_case.util.ValidationRepositoryImpl
import com.soujunior.domain.use_case.util.ValidationResult
import com.soujunior.petjournal.ui.accountManager.forgotPasswordScreen.ForgotPasswordFormEvent
import com.soujunior.petjournal.ui.accountManager.forgotPasswordScreen.ForgotPasswordFormState
import com.soujunior.petjournal.ui.accountManager.forgotPasswordScreen.ForgotPasswordViewModelImpl
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class ForgotPasswordMethodTest {

    private val forgotPassword = mockk<ForgotPasswordUseCase>(relaxed = true)
    private val validation = mockk<ValidationRepositoryImpl>(relaxed = true)
    private val viewModel =
        ForgotPasswordViewModelImpl(
            forgotPasswordUseCase = forgotPassword,
            validation = validation
        )

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `if failed() is call, sets error message`() {
        val errorMessage = "Test Error"
        viewModel.failed(Error(errorMessage))
        TestCase.assertEquals(errorMessage, viewModel.message.value)
    }

    @Test
    fun `if success() is call, sets success message`() {
        val successMessage = "Test Success"
        viewModel.success(successMessage)
        TestCase.assertEquals(successMessage, viewModel.message.value)
    }

    @Test
    fun `test onEvent EmailChanged`() {
        val email = "test@example.com"
        val event = ForgotPasswordFormEvent.EmailChanged(email = email)
        viewModel.onEvent(event)
        TestCase.assertEquals(email, viewModel.state.email)
    }

    @Test
    fun `test change email`() {
        val email = "test@petjournal.com"
        val validationResult = ValidationResult(success = true)
        every { validation.validateEmail(email) } returns validationResult
        viewModel.change(email = email)
        TestCase.assertEquals(email, viewModel.state.email)
        TestCase.assertEquals(null, viewModel.state.emailError)
    }

    @Test
    fun `When enable() button is called make sure field email is filled and it return true`() {
        every { this@ForgotPasswordMethodTest.validation.validateEmail(any()) } returns ValidationResult(
            success = true
        )

        viewModel.state = ForgotPasswordFormState(email = "john.doe@example.com")
        val enableButton = viewModel.enableButton()
        assertk.assertThat(enableButton).isTrue()
    }

    @Test
    fun `When enable() button is called make sure field email is filled and it return false if not`() {
        every { this@ForgotPasswordMethodTest.validation.validateEmail(any()) } returns ValidationResult(
            success = false
        )
        viewModel.state = ForgotPasswordFormState(email = "")
        val enableButton = viewModel.enableButton()
        assertk.assertThat(enableButton).isFalse()
    }
}