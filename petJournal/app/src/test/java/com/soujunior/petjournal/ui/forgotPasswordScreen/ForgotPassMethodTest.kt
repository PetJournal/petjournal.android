package com.soujunior.petjournal.ui.forgotPasswordScreen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.soujunior.domain.setup.MainCoroutineRule
import com.soujunior.domain.usecase.auth.ForgotPasswordUseCase
import com.soujunior.domain.usecase.base.DataResult
import com.soujunior.petjournal.setup.formForgot
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch

class ForgotPassMethodTest() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutineTestRule = MainCoroutineRule()

    private val forgotPasswordUseCase = mockk<ForgotPasswordUseCase>(relaxed = true)
    private val viewModel = ForgotPasswordScreenViewModelImpl(forgotPasswordUseCase)

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `when postForm in register is called, success should be updated correctly`() = runBlocking {
        coEvery { forgotPasswordUseCase.execute(formForgot) } returns DataResult.Success("success")

        viewModel.sendRequestToChangePassword(formForgot)

        val latch = CountDownLatch(1)
        viewModel.success.observeForever {
            assertThat(it).isEqualTo("success")
            latch.countDown()
        }

        withContext(Dispatchers.IO) {
            latch.await()
        }
    }


    @Test
    fun `when postForm in forgotPassword is called and receive a exception, error should be updated correctly`() = runBlocking {
        coEvery { forgotPasswordUseCase.execute(formForgot) } returns DataResult.Failure(Error("error message"))

        viewModel.sendRequestToChangePassword(formForgot)

        val latch = CountDownLatch(1)
        viewModel.error.observeForever {
            assertThat(it).isEqualTo("error message")
            latch.countDown()
        }

        withContext(Dispatchers.IO) {
            latch.await()
        }
    }
}


