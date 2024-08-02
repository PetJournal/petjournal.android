package com.soujunior.petjournal.di

import androidx.lifecycle.SavedStateHandle
import androidx.room.Room
import com.petjournal.database.database.db.AppDatabase
import com.petjournal.database.repository.AppInfoDataBaseImpl
import com.petjournal.database.repository.GuardianLocalDataSourceImpl
import com.soujunior.data.remote.AuthService
import com.soujunior.data.remote.GuardianService
import com.soujunior.data.remote.adapters.internal.NetworkResultCallAdapterFactory
import com.soujunior.data.repository.AppInfoDataImpl
import com.soujunior.data.repository.AuthRepositoryImpl
import com.soujunior.data.repository.GuardianRepositoryImpl
import com.soujunior.domain.repository.AppInfoDataBase
import com.soujunior.domain.repository.AppInfoDataBaseRepository
import com.soujunior.domain.repository.AuthRepository
import com.soujunior.domain.repository.GuardianLocalDataSource
import com.soujunior.domain.repository.GuardianRepository
import com.soujunior.domain.repository.ValidationRepository
import com.soujunior.domain.use_case.auth.AwaitingCodeUseCase
import com.soujunior.domain.use_case.auth.ChangePasswordUseCase
import com.soujunior.domain.use_case.auth.CheckLoginStatusUseCase
import com.soujunior.domain.use_case.auth.ForgotPasswordUseCase
import com.soujunior.domain.use_case.auth.GetSavedPasswordUseCase
import com.soujunior.domain.use_case.auth.LoginUseCase
import com.soujunior.domain.use_case.auth.LogoutUseCase
import com.soujunior.domain.use_case.auth.SavePasswordUseCase
import com.soujunior.domain.use_case.auth.SignUpUseCase
import com.soujunior.domain.use_case.guardian.GetGuardianNameUseCase
import com.soujunior.domain.use_case.guardian.GetPetRegistrationWentLive
import com.soujunior.domain.use_case.guardian.SetPetRegistrationWentLive
import com.soujunior.domain.use_case.pet.DeletePetInformationUseCase
import com.soujunior.domain.use_case.pet.GetAllPetInformationUseCase
import com.soujunior.domain.use_case.pet.CreatePetInformationApiUseCase
import com.soujunior.domain.use_case.pet.GetListPetRacesUseCase
import com.soujunior.domain.use_case.pet.GetListPetSizesUseCase
import com.soujunior.domain.use_case.pet.GetPetInformationUseCase
import com.soujunior.domain.use_case.pet.SavePetInformationUseCase
import com.soujunior.domain.use_case.pet.UpdatePetInformationUseCase
import com.soujunior.domain.use_case.util.ValidationRepositoryImpl
import com.soujunior.petjournal.ui.screens_app.screens_pets.registeredPetScreen.ViewModelRegisteredPets
import com.soujunior.petjournal.ui.screens_app.screens_pets.registeredPetScreen.ViewModelRegisteredPetsImpl
import com.soujunior.petjournal.ui.screens_app.screen_home.homeScreen.HomeScreenViewModel
import com.soujunior.petjournal.ui.screens_app.screen_home.homeScreen.HomeScreenViewModelImpl
import com.soujunior.petjournal.ui.screens_app.screens_pets.introRegisterPetScreen.IntroRegisterPetViewModel
import com.soujunior.petjournal.ui.screens_app.screens_pets.petBirthDateScreen.BirthDateViewModel
import com.soujunior.petjournal.ui.screens_app.screens_pets.petBirthDateScreen.BirthDateViewModelImpl
import com.soujunior.petjournal.ui.screens_app.screens_pets.petNameAndGenderScreen.ViewModelNameGender
import com.soujunior.petjournal.ui.screens_app.screens_pets.petNameAndGenderScreen.ViewModelNameGenderImpl
import com.soujunior.petjournal.ui.screens_app.screens_pets.petRaceAndSizeScreen.ViewModelRaceSize
import com.soujunior.petjournal.ui.screens_app.screens_pets.petRaceAndSizeScreen.ViewModelRaceSizeImpl
import com.soujunior.petjournal.ui.screens_app.screens_pets.speciesChoiceScreen.ViewModelChoiceSpecies
import com.soujunior.petjournal.ui.screens_app.screens_pets.speciesChoiceScreen.ViewModelChoiceSpeciesImpl
import com.soujunior.petjournal.ui.screens_app.screens_apresentation.splashScreen.SplashViewModel
import com.soujunior.petjournal.ui.screens_app.account_manager.awaitingCodeScreen.AwaitingCodeViewModel
import com.soujunior.petjournal.ui.screens_app.account_manager.awaitingCodeScreen.AwaitingCodeViewModelImpl
import com.soujunior.petjournal.ui.screens_app.account_manager.changePasswordScreen.ChangePasswordViewModel
import com.soujunior.petjournal.ui.screens_app.account_manager.changePasswordScreen.ChangePasswordViewModelImpl
import com.soujunior.petjournal.ui.screens_app.account_manager.forgotPasswordScreen.ForgotPasswordViewModel
import com.soujunior.petjournal.ui.screens_app.account_manager.forgotPasswordScreen.ForgotPasswordViewModelImpl
import com.soujunior.petjournal.ui.screens_app.account_manager.loginScreen.LoginViewModel
import com.soujunior.petjournal.ui.screens_app.account_manager.loginScreen.LoginViewModelImpl
import com.soujunior.petjournal.ui.screens_app.account_manager.registerScreen.RegisterViewModel
import com.soujunior.petjournal.ui.screens_app.account_manager.registerScreen.RegisterViewModelImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val mainModule = module {

    // Repositories
    single<ValidationRepository> { ValidationRepositoryImpl() }
    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get()) }
    single<GuardianRepository> { GuardianRepositoryImpl(get(), get(), get()) }
    single<AppInfoDataBaseRepository> { AppInfoDataImpl(get()) }
    single<GuardianLocalDataSource> { GuardianLocalDataSourceImpl(get(), get()) }
    single<AppInfoDataBase> { AppInfoDataBaseImpl(get()) }

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
    single { get<AppDatabase>().guardianProfileDao() }
    single { get<AppDatabase>().applicationDao() }

    // UseCases
    factory { SignUpUseCase(get()) }
    factory { LoginUseCase(get()) }
    factory { ForgotPasswordUseCase(get()) }
    factory { AwaitingCodeUseCase(get()) }
    factory { ChangePasswordUseCase(get()) }
    factory { CheckLoginStatusUseCase(get()) }
    factory { GetSavedPasswordUseCase(get()) }
    factory { SavePasswordUseCase(get()) }
    factory { GetGuardianNameUseCase(get()) }
    factory { LogoutUseCase(get()) }
    factory { AppInfoDataBaseImpl(get()) }
    factory { GetPetRegistrationWentLive(get()) }
    factory { SetPetRegistrationWentLive(get()) }
    factory { SavePetInformationUseCase(get()) }
    factory { DeletePetInformationUseCase(get()) }
    factory { GetPetInformationUseCase(get()) }
    factory { GetAllPetInformationUseCase(get()) }
    factory { UpdatePetInformationUseCase(get()) }
    factory { SavedStateHandle() }
    factory { GetListPetSizesUseCase(get()) }
    factory { GetListPetRacesUseCase(get()) }
    factory { CreatePetInformationApiUseCase(get()) }

    single<AuthService> { get<Retrofit>().create(AuthService::class.java) }
    single<GuardianService> { get<Retrofit>().create(GuardianService::class.java) }

    // Moshi Converter
    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    // Retrofit Service
    single {
        Retrofit.Builder()
            .baseUrl("https://petjournal-api-z9gs.onrender.com/")
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .addCallAdapterFactory(NetworkResultCallAdapterFactory.create())
            .build()
    }

//        viewModel { (handle: SavedStateHandle) -> CleanerTaskViewModel(savedStateHandle = handle) }

    // ViewModels
    viewModel<HomeScreenViewModel> { HomeScreenViewModelImpl(get(), get()) }
    viewModel<IntroRegisterPetViewModel> {
        com.soujunior.petjournal.ui.screens_app.screens_pets.introRegisterPetScreen.IntroIntroRegisterPetViewModelImpl(
            get(),
            get(),
            get()
        )
    }
    viewModel<LoginViewModel> { LoginViewModelImpl(get(), get(), get(), get()) }
    viewModel<RegisterViewModel> { RegisterViewModelImpl(get(), get()) }
    viewModel<ViewModelRegisteredPets> { ViewModelRegisteredPetsImpl(get(), get(), get()) }
    viewModel<AwaitingCodeViewModel> { AwaitingCodeViewModelImpl(get(), get(), get()) }
    viewModel<ForgotPasswordViewModel> { ForgotPasswordViewModelImpl(get(), get()) }
    viewModel<ChangePasswordViewModel> { ChangePasswordViewModelImpl(get(), get()) }
    viewModel { SplashViewModel(get()) }
    viewModel<ViewModelChoiceSpecies> { ViewModelChoiceSpeciesImpl(get(), get(), get()) }

    //viewModel<ViewModelNameGender> { (handle: SavedStateHandle) -> ViewModelNameGenderImpl(get(), get(), get(), handle) }
    viewModel<ViewModelNameGender> {// (handle: SavedStateHandle) ->
        ViewModelNameGenderImpl(
            validation = get(),
            getPetInformationUseCase = get(),
            updatePetInformationUseCase = get(),
            //savedStateHandle = get()
        )
    }

    viewModel<BirthDateViewModel> { BirthDateViewModelImpl(get(), get(), get(), get()) }
    viewModel<ViewModelRaceSize> { ViewModelRaceSizeImpl(get(), get(), get(), get(), get()) }
}
