package com.soujunior.petjournal.ui.loginScreen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.soujunior.domain.setup.MainCoroutineRule
import com.soujunior.domain.usecase.auth.LoginUseCase
import com.soujunior.domain.usecase.base.DataResult
import com.soujunior.petjournal.setup.formLogin
import com.soujunior.petjournal.ui.accountManager.loginScreen.LoginViewModelImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch

class LoginMethodTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutineTesteRule = MainCoroutineRule()

    private val loginUseCase = mockk<LoginUseCase>(relaxed = true)
    private val viewModel = LoginViewModelImpl(loginUseCase)

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
    fun `when postForm in login is called, success should be updated correctly`() = runBlocking {
        coEvery { loginUseCase.execute(formLogin) } returns DataResult.Success("success")

        viewModel.submitData()

        val latch = CountDownLatch(1)
        viewModel.success.observeForever {
            assertThat(it).isEqualTo("success")
            latch.countDown()
        }

        latch.await()
    }

    @Test
    fun `when postForm in login is called and receive a exception, error should be updated correctly`() =
        runBlocking {
            coEvery { loginUseCase.execute(formLogin) } returns DataResult.Failure(Error("error message"))

            viewModel.submitData()

            val latch = CountDownLatch(1)
            viewModel.error.observeForever {
                assertThat(it).isEqualTo("error message")
                latch.countDown()
            }

            latch.await()
        }
}