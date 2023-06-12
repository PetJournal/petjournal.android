package com.soujunior.petjournal.ui.awaitingCodeScreen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.soujunior.domain.setup.MainCoroutineRule
import com.soujunior.domain.usecase.auth.AwaitingCodeUseCase
import com.soujunior.domain.usecase.base.DataResult
import com.soujunior.petjournal.setup.sendCode
import com.soujunior.petjournal.ui.accountManager.awaitingCodeScreen.AwaitingCodeViewModelImpl
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

class AwaitingCodeMethodTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutineTesteRule = MainCoroutineRule()

    private val awaitingCodeUseCase = mockk<AwaitingCodeUseCase>(relaxed = true)
    private val viewModel = AwaitingCodeViewModelImpl(awaitingCodeUseCase)

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
    fun `when postOtpVerification is called, succeses should be update correctly`() = runBlocking {
        coEvery { awaitingCodeUseCase.execute(sendCode) } returns DataResult.Success("success")

        viewModel.postOtpVerification()

        val latch = CountDownLatch(1)
        viewModel.success.observeForever {
            assertThat(it).isEqualTo("success")
            latch.countDown()
        }

        latch.await()
    }


    @Test
    fun `hen postOtpVerification is called and receive a exception, error should be update correctly`() = runBlocking {
        coEvery { awaitingCodeUseCase.execute(sendCode) } returns DataResult.Failure(Error("error message"))

        viewModel.postOtpVerification()

        val latch = CountDownLatch(1)
        viewModel.error.observeForever {
            assertThat(it).isEqualTo("error message")
            latch.countDown()
        }

        latch.await()
    }

}
