package com.soujunior.petjournal.di

import com.soujunior.data.mock.MockAuthDataSource
import com.soujunior.data.mock.MockAuthRepositoryImpl
import com.soujunior.data.mock.MockGuardianLocalDataSourceImpl
import com.soujunior.data.mock.MockGuardianRepositoryImpl
import com.soujunior.data.mock.MockRemoteDataSource
import com.soujunior.data.remote.AuthDataSource
import com.soujunior.data.remote.RemoteDataSource
import com.soujunior.domain.repository.api.AuthRepository
import com.soujunior.domain.repository.api.Repository
import com.soujunior.domain.repository.database.LocalDataSource
import com.soujunior.domain.use_case.auth.CheckLoginStatusUseCase
import com.soujunior.domain.use_case.auth.LogoutUseCase
import com.soujunior.domain.use_case.guardian.GetGuardianEmailUseCase
import com.soujunior.domain.use_case.guardian.GetGuardianNameUseCase
import com.soujunior.domain.use_case.guardian.SaveGuardianContactUseCase
import com.soujunior.domain.use_case.pet.GetListPetUseCaseV1
import com.soujunior.petjournal.ui.screensapp.screenHome.homeScreenV2.FakeHomeViewModel
import com.soujunior.petjournal.ui.screensapp.screenHome.homeScreenV2.HomeScreenViewModel
import com.soujunior.petjournal.ui.screensapp.screensApresentation.splashScreen.SplashViewModel
import com.soujunior.petjournal.ui.screensapp.screenspets.petListScreenV2.PetListViewModel
import com.soujunior.petjournal.ui.screensapp.screenspets.petListScreenV2.PetListViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mockModule =
    module {
        single<AuthDataSource> { MockAuthDataSource() }
        single<AuthRepository> { MockAuthRepositoryImpl() }
        factory { CheckLoginStatusUseCase(get()) }
        single<RemoteDataSource> { MockRemoteDataSource() }
    }

val mockViewmodel =
    module {
        viewModel { SplashViewModel(get()) }
        viewModel<PetListViewModel> { PetListViewModelImpl(get(), get()) }
        viewModel<HomeScreenViewModel> { FakeHomeViewModel() }
    }

val mockUsercase =
    module {
        factory { CheckLoginStatusUseCase(get()) }
        factory { GetGuardianNameUseCase(get()) }
        factory { GetGuardianEmailUseCase(get()) }
        factory { SaveGuardianContactUseCase(get()) }
        factory { LogoutUseCase(get(), get()) }
        factory { GetListPetUseCaseV1(get()) }
    }

val mockData =
    module {
        single<Repository> { MockGuardianRepositoryImpl() }
        single<LocalDataSource> { MockGuardianLocalDataSourceImpl() }
        single { MockRemoteDataSource() }
    }
