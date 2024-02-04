package com.soujunior.petjournal.ui.registerScreen

import androidx.lifecycle.viewModelScope
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.soujunior.domain.model.mapper.User
import com.soujunior.domain.use_case.auth.SignUpUseCase
import com.soujunior.domain.use_case.util.ValidationRepositoryImpl
import com.soujunior.domain.use_case.util.ValidationResult
import com.soujunior.petjournal.ui.accountManager.registerScreen.RegisterFormEvent
import com.soujunior.petjournal.ui.accountManager.registerScreen.RegisterFormState
import com.soujunior.petjournal.ui.accountManager.registerScreen.RegisterViewModelImpl
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RegisterMethodTest {

    @get:Rule
    //val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: RegisterViewModelImpl
    private val signUpUseCase = mockk<SignUpUseCase>(relaxed = true)
    private val validation = mockk<ValidationRepositoryImpl>(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = RegisterViewModelImpl(signUpUseCase, validation)
    }

    @After
    fun tearDown() {
        viewModel.viewModelScope.cancel()
    }

    @Test
    fun `if success() is call, set success message`() = runTest {
        val successMessage = User(
            "123456",
            "John",
            "Doe",
            "john.doe@example.com",
            "11998018914"
        )
        viewModel.success(successMessage)
        assertEquals(successMessage, viewModel.message.value)
    }

    @Test
    fun `if failed() is call, sets error message`() {
        val errorMessage = "Test Error"
        viewModel.failed(Error(errorMessage))
        assertEquals(errorMessage, viewModel.message.value)
    }

    @Test
    fun `When enable() button is called make sure all fields are filled and it returns true`() {
        every { this@RegisterMethodTest.validation.validateName(any()) } returns ValidationResult(
            success = true
        )
        every { this@RegisterMethodTest.validation.validateLastName(any()) } returns ValidationResult(
            success = true
        )
        every { this@RegisterMethodTest.validation.validateEmail(any()) } returns ValidationResult(
            success = true
        )
        every { this@RegisterMethodTest.validation.validatePhone(any()) } returns ValidationResult(
            success = true
        )
        every { this@RegisterMethodTest.validation.validatePassword(any()) } returns ValidationResult(
            success = true
        )
        every {
            this@RegisterMethodTest.validation.validateRepeatedPassword(
                any(),
                any()
            )
        } returns ValidationResult(success = true)
        viewModel.state = RegisterFormState(
            name = "John",
            lastName = "Doe",
            email = "john.doe@example.com",
            phone = "123456789",
            password = "password",
            repeatedPassword = "password",
            privacyPolicy = true
        )
        val enableButton = viewModel.enableButton()
        assertk.assertThat(enableButton).isTrue()
    }

    @Test
    fun `When enableButton is called with empty name, it returns false`() {
        every { this@RegisterMethodTest.validation.validateName(any()) } returns ValidationResult(
            success = true
        )
        every { this@RegisterMethodTest.validation.validateLastName(any()) } returns ValidationResult(
            success = true
        )
        every { this@RegisterMethodTest.validation.validateEmail(any()) } returns ValidationResult(
            success = true
        )
        every { this@RegisterMethodTest.validation.validatePhone(any()) } returns ValidationResult(
            success = true
        )
        every { this@RegisterMethodTest.validation.validatePassword(any()) } returns ValidationResult(
            success = true
        )
        every {
            this@RegisterMethodTest.validation.validateRepeatedPassword(
                any(),
                any()
            )
        } returns ValidationResult(success = true)
        viewModel.state = RegisterFormState(
            name = "",
            lastName = "Doe",
            email = "john.doe@example.com",
            phone = "123456789",
            password = "password",
            repeatedPassword = "password",
            privacyPolicy = true
        )
        val enableButton = viewModel.enableButton()
        assertk.assertThat(enableButton).isFalse()
    }

    @Test
    fun `When enableButton is called with empty lastName, it returns false`() {
        viewModel.state = RegisterFormState(
            name = "John",
            lastName = "",
            email = "john.doe@example.com",
            phone = "123456789",
            password = "password",
            repeatedPassword = "password",
            privacyPolicy = true
        )
        val enableButton = viewModel.enableButton()
        assertk.assertThat(enableButton).isFalse()
    }

    @Test
    fun `When enableButton is called with empty email, it returns false`() {
        viewModel.state = RegisterFormState(
            name = "John",
            lastName = "Doe",
            email = "",
            phone = "123456789",
            password = "password",
            repeatedPassword = "password",
            privacyPolicy = true
        )
        val enableButton = viewModel.enableButton()
        assertk.assertThat(enableButton).isFalse()
    }

    @Test
    fun `When enableButton is called with empty phone, it returns true`() {
        every { this@RegisterMethodTest.validation.validateName(any()) } returns ValidationResult(
            success = true
        )
        every { this@RegisterMethodTest.validation.validateLastName(any()) } returns ValidationResult(
            success = true
        )
        every { this@RegisterMethodTest.validation.validateEmail(any()) } returns ValidationResult(
            success = true
        )
        every { this@RegisterMethodTest.validation.validatePhone(any()) } returns ValidationResult(
            success = true
        )
        every { this@RegisterMethodTest.validation.validatePassword(any()) } returns ValidationResult(
            success = true
        )
        every {
            this@RegisterMethodTest.validation.validateRepeatedPassword(
                any(),
                any()
            )
        } returns ValidationResult(success = true)
        viewModel.state = RegisterFormState(
            name = "John",
            lastName = "Doe",
            email = "john.doe@example.com",
            phone = "",
            password = "password",
            repeatedPassword = "password",
            privacyPolicy = true
        )
        val enableButton = viewModel.enableButton()
        assertk.assertThat(enableButton).isTrue()
    }

    @Test
    fun `When enableButton is called with empty password, it returns false`() {
        viewModel.state = RegisterFormState(
            name = "John",
            lastName = "Doe",
            email = "john.doe@example.com",
            phone = "123456789",
            password = "",
            repeatedPassword = "password",
            privacyPolicy = true
        )
        val enableButton = viewModel.enableButton()
        assertk.assertThat(enableButton).isFalse()
    }

    @Test
    fun `When enableButton is called with empty repeatedPassword, it returns false`() {
        viewModel.state = RegisterFormState(
            name = "John",
            lastName = "Doe",
            email = "john.doe@example.com",
            phone = "123456789",
            password = "password",
            repeatedPassword = "",
            privacyPolicy = true
        )
        val enableButton = viewModel.enableButton()
        assertk.assertThat(enableButton).isFalse()
    }

    @Test
    fun `When enableButton is called with privacyPolicy set to false, it returns false`() {
        viewModel.state = RegisterFormState(
            name = "John",
            lastName = "Doe",
            email = "john.doe@example.com",
            phone = "123456789",
            password = "password",
            repeatedPassword = "password",
            privacyPolicy = false
        )
        val enableButton = viewModel.enableButton()
        assertk.assertThat(enableButton).isFalse()
    }

    @Test
    fun `test change name`() {
        val newName = "John Doe"
        val validationResult = ValidationResult(success = true)

        every { validation.validateName(newName) } returns validationResult
        viewModel.change(name = newName)
        assertEquals(newName, viewModel.state.name)
        assertEquals(null, viewModel.state.nameError)
    }

    @Test
    fun `test change lastName`() {
        val newLastName = "Smith"
        val validationResult = ValidationResult(success = true)

        every { validation.validateLastName(newLastName) } returns validationResult

        viewModel.change(lastName = newLastName)

        assertEquals(newLastName, viewModel.state.lastName)
        assertEquals(null, viewModel.state.lastNameError)
    }

    @Test
    fun `test change password`() {
        val newPassword = "newPassword"
        val validationResult = ValidationResult(success = true)

        every { validation.validatePassword(newPassword) } returns validationResult

        viewModel.change(password = newPassword)

        assertEquals(newPassword, viewModel.state.password)
        assertEquals(null, viewModel.state.passwordError)
    }

    @Test
    fun `test change email`() {
        val newEmail = "test@petjournal.com"
        val validationResult = ValidationResult(success = true)
        every { validation.validateEmail(newEmail) } returns validationResult
        viewModel.change(email = newEmail)
        assertEquals(newEmail, viewModel.state.email)
        assertEquals(null, viewModel.state.emailError)
    }

    @Test
    fun `test change phone`() {
        val newPhone = "1234567890"
        val validationResult = ValidationResult(success = true)

        every { validation.validatePhone(newPhone) } returns validationResult

        viewModel.change(phone = newPhone)

        assertEquals(newPhone, viewModel.state.phone)
        assertEquals(null, viewModel.state.phoneError)
    }

    @Test
    fun `test change repeatedPassword`() {
        val newRepeatedPassword = "newPassword"
        val validationResult = ValidationResult(success = true)

        every {
            validation.validateRepeatedPassword(
                newRepeatedPassword,
                viewModel.state.password
            )
        } returns validationResult

        viewModel.change(repeatedPassword = newRepeatedPassword)

        assertEquals(newRepeatedPassword, viewModel.state.repeatedPassword)
        assertEquals(null, viewModel.state.repeatedPasswordError)
    }

    @Test
    fun `test change privacy`() {
        val newPrivacy = true
        val validationResult = ValidationResult(success = true)

        every { validation.validatePrivacyPolicy(newPrivacy) } returns validationResult

        viewModel.change(privacy = newPrivacy)

        assertEquals(newPrivacy, viewModel.state.privacyPolicy)
        assertEquals(null, viewModel.state.repeatedPasswordError)
    }

    @Test
    fun `test onEvent NameChanged`() {
        val name = "John Doe"
        val event = RegisterFormEvent.NameChanged(name = name)
        viewModel.onEvent(event)
        assertEquals(name, viewModel.state.name)
    }

    @Test
    fun `test onEvent LastNameChanged`() {
        val lastName = "Smith"
        val event = RegisterFormEvent.LastNameChanged(lastName = lastName)
        viewModel.onEvent(event)
        assertEquals(lastName, viewModel.state.lastName)
    }

    @Test
    fun `test onEvent EmailChanged`() {
        val email = "test@example.com"
        val event = RegisterFormEvent.EmailChanged(email = email)
        viewModel.onEvent(event)
        assertEquals(email, viewModel.state.email)
    }

    @Test
    fun `test onEvent PhoneChanged`() {
        val phone = "1234567890"
        val event = RegisterFormEvent.PhoneChanged(phone = phone)
        viewModel.onEvent(event)
        assertEquals(phone, viewModel.state.phone)
    }

    @Test
    fun `test onEvent PasswordChanged`() {
        val password = "newPassword"
        val event = RegisterFormEvent.PasswordChanged(password = password)
        viewModel.onEvent(event)
        assertEquals(password, viewModel.state.password)
    }


    @Test
    fun `test onEvent ConfirmPasswordChanged`() {
        val confirmPassword = "newPassword"
        val event = RegisterFormEvent.ConfirmPasswordChanged(confirmPassword = confirmPassword)
        viewModel.onEvent(event)
        assertEquals(confirmPassword, viewModel.state.repeatedPassword)
    }

    @Test
    fun `test onEvent PrivacyPolicyChanged`() {
        val privacyPolicy = true
        val event = RegisterFormEvent.PrivacyPolicyChanged(privacyPolicy = privacyPolicy)
        viewModel.onEvent(event)
        assertEquals(privacyPolicy, viewModel.state.privacyPolicy)
    }
}