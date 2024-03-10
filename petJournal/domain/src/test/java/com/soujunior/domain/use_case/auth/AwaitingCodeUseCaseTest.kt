package com.soujunior.domain.use_case.auth
//
//import assertk.assertThat
//import assertk.assertions.isEqualTo
//import com.soujunior.domain.model.request.AwaitingCodeModel
//import com.soujunior.domain.model.response.AccessTokenResponse
//import com.soujunior.domain.network.NetworkResult
//import com.soujunior.domain.repository.AuthRepository
//import com.soujunior.domain.setup.MainCoroutineRule
//import com.soujunior.domain.use_case.base.DataResult
//import io.mockk.coEvery
//import io.mockk.mockk
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.runTest
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//
//@ExperimentalCoroutinesApi
//class AwaitingCodeUseCaseTest {
//
//    @get:Rule
//    var coroutineTestRule = MainCoroutineRule()
//
//    private lateinit var authRepository: AuthRepository
//    private lateinit var awaitingCodeModel: AwaitingCodeModel
//
///*
//
//            -- Request Body --
//    {
//        "email": "johndoe@email.com",
//        "verificationToken": "123456"
//    }
//
//
//              -- Response --
//
//    201 -> Success
//    {
//      "email": "johndoe@email.com",
//      "verificationToken": "123456"
//    }
//
//    400 -> Invalid Request
//    {
//        "error": "Invalid param: password"
//    }
//
//    401 ->
//
//    409 -> Conflict request
//    {
//        "error": "Phone or Email already registered"
//    }
//
//    500 -> Internal server error
//    {
//        "error": "Internal server error. An unexpected error happened. Please try again in a moment."
//    }
//
// */
//
//
//    @Before
//    fun setup() {
//        authRepository = mockk<AuthRepository>(relaxed = true)
//        awaitingCodeModel = mockk<AwaitingCodeModel>(relaxed = true)
//    }
//
//    @Test
//    fun `When the api result code is in the range 100 - 199 then it should returns Server Processing status`() = runTest {
//        val awaitingCodeUseCase = AwaitingCodeUseCase(authRepository)
//
//        coEvery {
//            authRepository.awaitingCode(any())
//        } returns NetworkResult.Success(AccessTokenResponse("4a43-gkd4-asmb-rasb"))
//
//        val dataResult = (awaitingCodeUseCase.execute(awaitingCodeModel) as? DataResult.Success<*>)?.data as? AccessTokenResponse
//        assertThat(dataResult?.accessToken).isEqualTo("4a43-gkd4-asmb-rasb")
//    }
//
//    @Test
//    fun `When the api result code is in the range 200 - 299 then it should returns Success status`() = runTest {
//        val awaitingCodeUseCase = AwaitingCodeUseCase(authRepository)
//
//        coEvery { authRepository.awaitingCode(any()) } returns ApiResponseCode(200, "Success")
//
//        val dataResult = (awaitingCodeUseCase.execute(awaitingCodeModel) as? DataResult.Success)?.data
//        assertThat(dataResult).isEqualTo("Success")
//    }
//
//    @Test
//    fun `When the api result code is in the range 300 - 399 then it should returns Missing some data in the Form Error`() = runTest {
//        val awaitingCodeUseCase = AwaitingCodeUseCase(authRepository)
//
//        coEvery {
//            authRepository.awaitingCode(any())
//        } returns ApiResponseCode(300, "There is some data missing from the form")
//
//        val dataResult = (awaitingCodeUseCase.execute(awaitingCodeModel) as? DataResult.Failure)?.throwable?.message
//        assertThat(dataResult).isEqualTo("Esta faltando alguma dado no formulário")
//    }
//
//    @Test
//    fun `When the api result code is in the range 400 - 499 then it should returns Client Error`() = runTest {
//        val awaitingCodeUseCase = AwaitingCodeUseCase(authRepository)
//
//        coEvery { authRepository.awaitingCode(any()) } returns ApiResponseCode(400, "Bad Request")
//
//        val dataResult = (awaitingCodeUseCase.execute(awaitingCodeModel) as? DataResult.Failure)?.throwable?.message
//        assertThat(dataResult).isEqualTo("Erro do Client")
//    }
//
//    @Test
//    fun `When the api result code is in the range 500 - 599 then it should returns Processing Request Error`() = runTest {
//        val awaitingCodeUseCase = AwaitingCodeUseCase(authRepository)
//
//        coEvery {
//            authRepository.awaitingCode(any())
//        } returns ApiResponseCode(500, "Error processing request")
//
//        val dataResult = (awaitingCodeUseCase.execute(awaitingCodeModel) as? DataResult.Failure)?.throwable?.message
//        assertThat(dataResult).isEqualTo("Erro ao processar requisição")
//    }
//
//    @Test
//    fun `When the api result code is out of range then it should returns Unknown Error`() = runTest {
//        val awaitingCodeUseCase = AwaitingCodeUseCase(authRepository)
//
//        coEvery {
//            authRepository.awaitingCode(any())
//        } returns ApiResponseCode(1001, "Internal server error")
//
//        val dataResult = (awaitingCodeUseCase.execute(awaitingCodeModel) as? DataResult.Failure)?.throwable?.message
//        assertThat(dataResult).isEqualTo("Erro desconhecido")
//    }
//
//    @Test
//    fun `When the api result is null then it should returns An unknown error has occurred`() = runTest {
//        val awaitingCodeUseCase = AwaitingCodeUseCase(authRepository)
//
//        val dataResult = (awaitingCodeUseCase.execute(null) as? DataResult.Failure)?.throwable?.message
//        assertThat(dataResult).isEqualTo("Houve um erro desconhecido!")
//    }
//}