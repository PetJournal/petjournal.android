package com.soujunior.petjournal.di

import com.soujunior.data.api.Service
import com.soujunior.data.model.MockService
import com.soujunior.data.repository.AuthRepositoryImpl
import com.soujunior.domain.repository.AuthRepository
import com.soujunior.domain.usecase.auth.LoginUseCase
import com.soujunior.domain.usecase.auth.RegisterUseCase
import com.soujunior.petjournal.ui.awaitingCodeScreen.AwaitingCodeScreenViewModel
import com.soujunior.petjournal.ui.awaitingCodeScreen.AwaitingCodeScreenViewModelImpl
import com.soujunior.petjournal.ui.detailScreen.DetailScreenViewModel
import com.soujunior.petjournal.ui.detailScreen.DetailScreenViewModelImpl
import com.soujunior.petjournal.ui.homeScreen.HomeScreenViewModel
import com.soujunior.petjournal.ui.homeScreen.HomeScreenViewModelImpl
import com.soujunior.petjournal.ui.loginScreen.LoginScreenViewModel
import com.soujunior.petjournal.ui.loginScreen.LoginScreenViewModelImpl
import com.soujunior.petjournal.ui.registerScreen.RegisterScreenViewModel
import com.soujunior.petjournal.ui.registerScreen.RegisterScreenViewModelImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val mainModule = module {
    factory { RegisterUseCase(get()) }
    factory { LoginUseCase(get()) }
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
    viewModel<LoginScreenViewModel> { LoginScreenViewModelImpl(get()) }
    viewModel<RegisterScreenViewModel> { RegisterScreenViewModelImpl(get()) }
    viewModel<AwaitingCodeScreenViewModel> { AwaitingCodeScreenViewModelImpl(get()) }
}