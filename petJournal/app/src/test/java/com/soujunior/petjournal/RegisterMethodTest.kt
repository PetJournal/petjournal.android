package com.soujunior.petjournal

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.soujunior.domain.setup.MainCoroutineRule
import com.soujunior.domain.usecase.auth.RegisterUseCase
import com.soujunior.domain.usecase.base.DataResult
import com.soujunior.petjournal.setup.formRegister
import com.soujunior.petjournal.ui.registerScreen.RegisterScreenViewModelImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch

class RegisterMethodTest() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutineTesteRule = MainCoroutineRule()

    private val registerUseCase = mockk<RegisterUseCase>(relaxed = true)
    private val viewModel = RegisterScreenViewModelImpl(registerUseCase)

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
    fun `when postForm is called, success should be updated correctly`() = runBlocking {
        coEvery { registerUseCase.execute(formRegister) } returns DataResult.Success("success")

        viewModel.postForm(formRegister)

        val latch = CountDownLatch(1)
        viewModel.success.observeForever {
            assertThat(it).isEqualTo("success")
            latch.countDown()
        }

        latch.await()
    }


    @Test
    fun `when postForm is called and receive a exception, error should be updated correctly`() = runBlocking {
        coEvery { registerUseCase.execute(formRegister) } returns DataResult.Failure(Error("error message"))

        viewModel.postForm(formRegister)

        val latch = CountDownLatch(1)
        viewModel.error.observeForever {
            assertThat(it).isEqualTo("error message")
            latch.countDown()
        }

        latch.await()
    }
}