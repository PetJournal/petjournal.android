package com.soujunior.petjournal.di

import com.soujunior.data.api.Service
import com.soujunior.data.model.MockService
import com.soujunior.data.repository.AuthRepositoryImpl
import com.soujunior.domain.repository.AuthRepository
import com.soujunior.domain.repository.ValidationRepository
import com.soujunior.domain.usecase.auth.AwaitingCodeUseCase
import com.soujunior.domain.usecase.auth.ChangePasswordUseCase
import com.soujunior.domain.usecase.auth.ForgotPasswordUseCase
import com.soujunior.domain.usecase.auth.LoginUseCase
import com.soujunior.domain.usecase.auth.RegisterUseCase
import com.soujunior.domain.usecase.auth.util.ValidationRepositoryImpl
import com.soujunior.petjournal.ui.accountManager.awaitingCodeScreen.AwaitingCodeScreenViewModel
import com.soujunior.petjournal.ui.accountManager.awaitingCodeScreen.AwaitingCodeScreenViewModelImpl
import com.soujunior.petjournal.ui.accountManager.changePasswordScreen.ChangePasswordViewModel
import com.soujunior.petjournal.ui.accountManager.changePasswordScreen.ChangePasswordViewModelImpl
import com.soujunior.petjournal.ui.accountManager.forgotPasswordScreen.ForgotPasswordScreenViewModel
import com.soujunior.petjournal.ui.accountManager.forgotPasswordScreen.ForgotPasswordScreenViewModelImpl
import com.soujunior.petjournal.ui.accountManager.loginScreen.LoginScreenViewModel
import com.soujunior.petjournal.ui.accountManager.loginScreen.LoginScreenViewModelImpl
import com.soujunior.petjournal.ui.accountManager.registerScreen.RegisterScreenViewModel
import com.soujunior.petjournal.ui.accountManager.registerScreen.RegisterScreenViewModelImpl
import com.soujunior.petjournal.ui.appArea.detailScreen.DetailScreenViewModel
import com.soujunior.petjournal.ui.appArea.detailScreen.DetailScreenViewModelImpl
import com.soujunior.petjournal.ui.appArea.homeScreen.HomeScreenViewModel
import com.soujunior.petjournal.ui.appArea.homeScreen.HomeScreenViewModelImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val mainModule = module {
    single<ValidationRepository> { ValidationRepositoryImpl() }
    factory { RegisterUseCase(get()) }
    factory { LoginUseCase(get()) }
    factory { ForgotPasswordUseCase(get()) }
    factory { AwaitingCodeUseCase(get()) }
    factory { ChangePasswordUseCase(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl("https://api.com/")
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }
    //single<Service> { get<Retrofit>().create(Service::class.java) } //TODO:<- desativado para usar mock
    single<Service> { MockService() } //todo <- mock

    viewModel<HomeScreenViewModel> { HomeScreenViewModelImpl() }
    viewModel<DetailScreenViewModel> { DetailScreenViewModelImpl() }
    viewModel<LoginScreenViewModel> { LoginScreenViewModelImpl(get(), get()) }
    viewModel<RegisterScreenViewModel> { RegisterScreenViewModelImpl(get()) }
    viewModel<AwaitingCodeScreenViewModel> { AwaitingCodeScreenViewModelImpl(get()) }
    viewModel<ForgotPasswordScreenViewModel> { ForgotPasswordScreenViewModelImpl(get()) }
    viewModel<ChangePasswordViewModel> { ChangePasswordViewModelImpl(get()) }
}