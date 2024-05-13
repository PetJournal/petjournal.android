package com.soujunior.petjournal.ui.awaitingCodeScreen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import com.soujunior.domain.repository.ValidationRepository
import com.soujunior.domain.use_case.auth.AwaitingCodeUseCase
import com.soujunior.domain.use_case.util.ValidationRepositoryImpl
import com.soujunior.petjournal.ui.util.ValidationEvent
import com.soujunior.petjournal.ui.accountManager.awaitingCodeScreen.AwaitingCodeFormEvent
import com.soujunior.petjournal.ui.accountManager.awaitingCodeScreen.AwaitingCodeFormState
import com.soujunior.petjournal.ui.accountManager.awaitingCodeScreen.AwaitingCodeViewModel
import com.soujunior.petjournal.ui.accountManager.awaitingCodeScreen.AwaitingCodeViewModelImpl
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
//
//class AwaitingCodeTest {
//
//    @get:Rule
//    val instantExecutorRule = InstantTaskExecutorRule()
//
//    lateinit var awaitingCodeUseCase: AwaitingCodeUseCase
//    lateinit var repository: ValidationRepository
//    lateinit var viewModel: AwaitingCodeViewModel
//
//    @Before
//    fun setup() {
//        awaitingCodeUseCase = mockk<AwaitingCodeUseCase>(relaxed = true)
//        repository = ValidationRepositoryImpl()
//        viewModel = AwaitingCodeViewModelImpl(repository, awaitingCodeUseCase)
//
//        Dispatchers.setMain(Dispatchers.Unconfined)
//    }
//
//    @After
//    fun tearDown() {
//        Dispatchers.resetMain()
//    }
//
//    @Test
//    fun `When receive null then it should update event channel and emit default error`() = runTest {
//        val errorObserver = mockk<Observer<String>>(relaxed = true)
//        viewModel.error.observeForever(errorObserver)
//
//        try {
//            viewModel.failed(null)
//
//            assertThat(viewModel.validationEventChannel.receive()).isEqualTo(ValidationEvent.Failed)
//            verify { errorObserver.onChanged("Erro desconhecido!") }
//        } finally {
//            viewModel.error.removeObserver(errorObserver)
//        }
//    }
//
//    @Test
//    fun `When receive error then it should update event channel and emit message error`() = runTest {
//        val errorObserver = mockk<Observer<String>>(relaxed = true)
//        val fakeError = Error("Fake Error")
//        viewModel.error.observeForever(errorObserver)
//
//        try {
//            viewModel.failed(fakeError)
//
//            assertThat(viewModel.validationEventChannel.receive()).isEqualTo(ValidationEvent.Failed)
//            verify { errorObserver.onChanged("Fake Error") }
//        } finally {
//            viewModel.error.removeObserver(errorObserver)
//        }
//    }
//
//    @Test
//    fun `When receive result then it should update event channel and emit result`() = runTest {
//        val successObserver = mockk<Observer<String>>(relaxed = true)
//        viewModel.success.observeForever(successObserver)
//
//        try {
//            viewModel.success("200")
//
//            assertThat(viewModel.validationEventChannel.receive()).isEqualTo(ValidationEvent.Success)
//            verify { successObserver.onChanged("200") }
//        } finally {
//            viewModel.success.removeObserver(successObserver)
//        }
//    }
//
//    @Test
//    fun `When receive (correct codeOPT) event then it should update the state`() {
//        val fakeEvent = AwaitingCodeFormEvent.CodeOTPChanged("012345")
//        viewModel.onEvent(fakeEvent)
//        assertThat(viewModel.state.codeOTP).isEqualTo("012345")
//        assertThat(viewModel.state.codeOTPError).isNull()
//    }
//
//    @Test
//    fun `When receive (incorrect codeOPT - not enough characters) event then it should show error`() {
//        val fakeEvent = AwaitingCodeFormEvent.CodeOTPChanged("01234")
//        viewModel.onEvent(fakeEvent)
//        assertThat(viewModel.state.codeOTPError).isEqualTo(listOf("O campo precisa ter 6 digitos.."))
//    }
//
//    @Test
//    fun `When receive (incorrect codeOPT - invalid characters) event then it should show error`() {
//        val fakeEvent = AwaitingCodeFormEvent.CodeOTPChanged("0A23*5")
//        viewModel.onEvent(fakeEvent)
//        assertThat(viewModel.state.codeOTPError).isEqualTo(listOf("Caracteres especiais e letras não são permitidos!"))
//    }
//
//    @Test
//    fun `When receive (incorrect codeOPT - blank string) event then it should show error`() {
//        val fakeEvent = AwaitingCodeFormEvent.CodeOTPChanged("")
//        viewModel.onEvent(fakeEvent)
//        assertThat(viewModel.state.codeOTPError).isEqualTo(listOf("O campo não pode ficar em branco!"))
//    }
//
//    @Test
//    fun `When receive (correct email) event then it should update the state`() {
//        val fakeEvent = AwaitingCodeFormEvent.EmailChanged("pet_journal@gmail.com")
//        viewModel.onEvent(fakeEvent)
//        assertThat(viewModel.state.email).isEqualTo("pet_journal@gmail.com")
//        assertThat(viewModel.state.emailError).isEqualTo(null)
//    }
//
//    @Test
//    fun `When receive (incorrect email - blank string) event then it should show error`() {
//        val fakeEvent = AwaitingCodeFormEvent.EmailChanged("")
//        viewModel.onEvent(fakeEvent)
//        assertThat(viewModel.state.emailError).isEqualTo(listOf("O campo não pode estar em branco!"))
//    }
//
//    @Test
//    fun `When receive (incorrect email - invalid format) event then it should show error`() {
//        val fakeEvent = AwaitingCodeFormEvent.EmailChanged("pet_journal.gmail.com")
//        viewModel.onEvent(fakeEvent)
//        assertThat(viewModel.state.emailError).isEqualTo(listOf("Formato de email incorreto"))
//    }
//
//    @Test
//    fun `When call enableButton() with correct state then it should return true`() {
//        viewModel.state = AwaitingCodeFormState(
//            email = "pet_journal@gmail.com",
//            codeOTP = "012345"
//        )
//
//        assertThat(viewModel.enableButton()).isEqualTo(true)
//    }
//
//    @Test
//    fun `When call enableButton() with (incorrect state - not enough characters) then it should return false`() {
//        viewModel.state = AwaitingCodeFormState(
//            email = "pet_journal@gmail.com",
//            codeOTP = "01234"
//        )
//
//        assertThat(viewModel.enableButton()).isEqualTo(false)
//    }
//
//    @Test
//    fun `When call enableButton() with (incorrect state - blank codeOTP) then it should return false`() {
//        viewModel.state = AwaitingCodeFormState(
//            email = "pet_journal@gmail.com",
//            codeOTP = ""
//        )
//
//        assertThat(viewModel.enableButton()).isEqualTo(false)
//    }
//
//}