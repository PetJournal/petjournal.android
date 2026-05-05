package com.soujunior.petjournal.di

import androidx.room.Room
import com.petjournal.database.database.db.AppDatabase
import com.petjournal.database.repository.AppInfoDataBaseImpl
import com.petjournal.database.repository.LocalDataSourceImpl
import com.soujunior.data.remote.AuthDataSource
import com.soujunior.data.remote.RemoteDataSource
import com.soujunior.data.remote.adapters.internal.NetworkResultCallAdapterFactory
import com.soujunior.data.repository.AppInfoDataImpl
import com.soujunior.data.repository.AuthRepositoryImpl
import com.soujunior.data.repository.PreferenceRepositoryImpl
import com.soujunior.data.repository.RepositoryImpl
import com.soujunior.domain.repository.PreferenceRepository
import com.soujunior.domain.repository.api.AuthRepository
import com.soujunior.domain.repository.api.Repository
import com.soujunior.domain.repository.appinfo.AppInfoDatabase
import com.soujunior.domain.repository.appinfo.AppInfoDatabaseRepository
import com.soujunior.domain.repository.database.LocalDataSource
import com.soujunior.domain.repository.task.TaskReminderScheduler
import com.soujunior.domain.repository.validation.ValidationRepository
import com.soujunior.domain.use_case.auth.AwaitingCodeUseCase
import com.soujunior.domain.use_case.auth.ChangePasswordUseCase
import com.soujunior.domain.use_case.auth.CheckLoginStatusUseCase
import com.soujunior.domain.use_case.auth.ForgotPasswordUseCase
import com.soujunior.domain.use_case.auth.GetLoginPreferenceUseCase
import com.soujunior.domain.use_case.auth.LoginUseCase
import com.soujunior.domain.use_case.auth.LogoutUseCase
import com.soujunior.domain.use_case.auth.SaveLoginPreferenceUseCase
import com.soujunior.domain.use_case.auth.SignUpUseCase
import com.soujunior.domain.use_case.guardian.GetGuardianEmailUseCase
import com.soujunior.domain.use_case.guardian.GetGuardianNameUseCase
import com.soujunior.domain.use_case.guardian.GetPetRegistrationWentLive
import com.soujunior.domain.use_case.guardian.SaveGuardianContactUseCase
import com.soujunior.domain.use_case.guardian.SetPetRegistrationWentLive
import com.soujunior.domain.use_case.information.GetListBreedUseCase
import com.soujunior.domain.use_case.information.GetListPetRacesUseCase
import com.soujunior.domain.use_case.information.GetListPetSizesUseCase
import com.soujunior.domain.use_case.pet.CreatePetUseCase
import com.soujunior.domain.use_case.pet.DeletePetByIdUseCase
import com.soujunior.domain.use_case.pet.GetListPetUseCaseV1
import com.soujunior.domain.use_case.pet.GetListPetUseCaseV2
import com.soujunior.domain.use_case.pet.GetListSizeUseCase
import com.soujunior.domain.use_case.pet.GetPetByIdUseCase
import com.soujunior.domain.use_case.pet.GetPetInformationUseCase
import com.soujunior.domain.use_case.pet.SavePetInformationUseCase
import com.soujunior.domain.use_case.pet.UpdatePetInformationUseCase
import com.soujunior.domain.use_case.preference.CheckNotificationPermissionRequestedUseCase
import com.soujunior.domain.use_case.preference.GetDarkModePreferenceUseCase
import com.soujunior.domain.use_case.preference.SaveDarkModePreferenceUseCase
import com.soujunior.domain.use_case.preference.SetNotificationPermissionRequestedUseCase
import com.soujunior.domain.use_case.tag.CreateTagUseCase
import com.soujunior.domain.use_case.tag.DeleteTagUseCase
import com.soujunior.domain.use_case.tag.GetListTagUseCase
import com.soujunior.domain.use_case.tag.UpdateTagUseCase
import com.soujunior.domain.use_case.task.CreateTaskUseCase
import com.soujunior.domain.use_case.task.GetListCurrentDateTaskUseCase
import com.soujunior.domain.use_case.task.GetListCurrentMonthTaskUseCase
import com.soujunior.domain.use_case.task.GetListCurrentWeekTaskUseCase
import com.soujunior.domain.use_case.task.GetLocalTasksByPeriodUseCase
import com.soujunior.domain.use_case.util.ValidationRepositoryImpl
import com.soujunior.petjournal.ui.screensapp.accountmanager.awaitingCodeScreen.AwaitingCodeViewModel
import com.soujunior.petjournal.ui.screensapp.accountmanager.awaitingCodeScreen.AwaitingCodeViewModelImpl
import com.soujunior.petjournal.ui.screensapp.accountmanager.changePasswordScreen.ChangePasswordViewModel
import com.soujunior.petjournal.ui.screensapp.accountmanager.changePasswordScreen.ChangePasswordViewModelImpl
import com.soujunior.petjournal.ui.screensapp.accountmanager.forgotPasswordScreen.ForgotPasswordViewModel
import com.soujunior.petjournal.ui.screensapp.accountmanager.forgotPasswordScreen.ForgotPasswordViewModelImpl
import com.soujunior.petjournal.ui.screensapp.accountmanager.loginScreen.LoginViewModel
import com.soujunior.petjournal.ui.screensapp.accountmanager.loginScreen.LoginViewModelImpl
import com.soujunior.petjournal.ui.screensapp.accountmanager.registerScreen.RegisterViewModel
import com.soujunior.petjournal.ui.screensapp.accountmanager.registerScreen.RegisterViewModelImpl
import com.soujunior.petjournal.ui.screensapp.screenHome.homeScreenV2.HomeScreenViewModel
import com.soujunior.petjournal.ui.screensapp.screenHome.homeScreenV2.HomeScreenViewModelImpl
import com.soujunior.petjournal.ui.screensapp.screenTasks.registerTaskScreen.viewmodel.RegisterTaskViewModel
import com.soujunior.petjournal.ui.screensapp.screenTasks.registerTaskScreen.viewmodel.RegisterTaskViewModelImpl
import com.soujunior.petjournal.ui.screensapp.screenTasks.taskListScreen.TaskListViewModel
import com.soujunior.petjournal.ui.screensapp.screenTasks.taskListScreen.TaskListViewModelImpl
import com.soujunior.petjournal.ui.screensapp.screenTutor.config.notifyScreen.SettingsViewModel
import com.soujunior.petjournal.ui.screensapp.screenTutor.tutorScreen.TutorViewModel
import com.soujunior.petjournal.ui.screensapp.screenTutor.tutorScreen.TutorViewModelImpl
import com.soujunior.petjournal.ui.screensapp.screensApresentation.splashScreen.SplashViewModel
import com.soujunior.petjournal.ui.screensapp.screenspets.introRegisterPetScreen.IntroRegisterPetViewModel
import com.soujunior.petjournal.ui.screensapp.screenspets.petBirthDateScreen.BirthDateViewModel
import com.soujunior.petjournal.ui.screensapp.screenspets.petBirthDateScreen.BirthDateViewModelImpl
import com.soujunior.petjournal.ui.screensapp.screenspets.petListScreen.PetListViewModel
import com.soujunior.petjournal.ui.screensapp.screenspets.petListScreen.PetListViewModelImpl
import com.soujunior.petjournal.ui.screensapp.screenspets.petNameAndGenderScreen.ViewModelNameGender
import com.soujunior.petjournal.ui.screensapp.screenspets.petNameAndGenderScreen.ViewModelNameGenderImpl
import com.soujunior.petjournal.ui.screensapp.screenspets.petRaceAndSizeScreen.ViewModelRaceSize
import com.soujunior.petjournal.ui.screensapp.screenspets.petRaceAndSizeScreen.ViewModelRaceSizeImpl
import com.soujunior.petjournal.ui.screensapp.screenspets.registerPetScreen.PetRegisterViewModel
import com.soujunior.petjournal.ui.screensapp.screenspets.registerPetScreen.PetRegisterViewModelImpl
import com.soujunior.petjournal.ui.screensapp.screenspets.speciesChoiceScreen.ViewModelChoiceSpecies
import com.soujunior.petjournal.ui.screensapp.screenspets.speciesChoiceScreen.ViewModelChoiceSpeciesImpl
import com.soujunior.petjournal.ui.util.timeoutObserverInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val mainModule =
    module {

        // Repositories
        single<ValidationRepository> { ValidationRepositoryImpl() }
        single<AuthRepository> { AuthRepositoryImpl(get(), get(), get()) }
        single<Repository> { RepositoryImpl(get(), get(), get(), get()) }
        single<AppInfoDatabaseRepository> { AppInfoDataImpl(get()) }
        single<LocalDataSource> { LocalDataSourceImpl(get(), get(), get(), get(), get(), get()) }
        single<AppInfoDatabase> { AppInfoDataBaseImpl(get()) }
        single<PreferenceRepository> { PreferenceRepositoryImpl(get()) }
        single<TaskReminderScheduler> { com.soujunior.petjournal.infrastructure.reminder.AndroidTaskScheduler(androidContext()) }

        single {
            Room.databaseBuilder(
                androidContext(),
                AppDatabase::class.java,
                "app_database",
            )
                .fallbackToDestructiveMigration()
                .build()
        }
        single { get<AppDatabase>().guardianProfileDao() }
        single { get<AppDatabase>().applicationDao() }
        single { get<AppDatabase>().petDetailsDao() }
        single { get<AppDatabase>().tagDao() }
        single { get<AppDatabase>().taskDao() }

        // UseCases
        factory { SignUpUseCase(get()) }
        factory { LoginUseCase(get()) }
        factory { ForgotPasswordUseCase(get()) }
        factory { AwaitingCodeUseCase(get()) }
        factory { ChangePasswordUseCase(get()) }
        factory { CheckLoginStatusUseCase(get()) }
        factory { GetLoginPreferenceUseCase(get()) }
        factory { SaveLoginPreferenceUseCase(get()) }
        factory { SaveGuardianContactUseCase(get()) }
        factory { GetGuardianEmailUseCase(get()) }
        factory { GetGuardianNameUseCase(get()) }
        factory { LogoutUseCase(get(), get()) }
        factory { AppInfoDataBaseImpl(get()) }
        factory { GetPetRegistrationWentLive(get()) }
        factory { SetPetRegistrationWentLive(get()) }
        factory { SavePetInformationUseCase(get()) }
        factory { GetPetInformationUseCase(get()) }
        factory { UpdatePetInformationUseCase(get()) }
        factory { GetListPetSizesUseCase(get()) }
        factory { GetListPetRacesUseCase(get()) }
        factory { GetListPetUseCaseV1(get()) }
        factory { GetListPetUseCaseV2(get()) }
        factory { CreatePetUseCase(get()) }
        factory { DeletePetByIdUseCase(get()) }
        factory { GetPetByIdUseCase(get()) }
        factory { GetListBreedUseCase(get()) }
        factory { GetListSizeUseCase(get()) }
        factory { GetListTagUseCase(get()) }
        factory { CreateTagUseCase(get()) }
        factory { UpdateTagUseCase(get()) }
        factory { DeleteTagUseCase(get()) }
        factory { CreateTaskUseCase(get()) }
        factory { GetListCurrentDateTaskUseCase(get()) }
        factory { GetListCurrentWeekTaskUseCase(get()) }
        factory { GetListCurrentMonthTaskUseCase(get()) }
        factory { GetLocalTasksByPeriodUseCase(get()) }
        factory { GetDarkModePreferenceUseCase(get()) }
        factory { SaveDarkModePreferenceUseCase(get()) }
        factory { CheckNotificationPermissionRequestedUseCase(get()) }
        factory { SetNotificationPermissionRequestedUseCase(get()) }

        single<AuthDataSource> { get<Retrofit>().create(AuthDataSource::class.java) }
        single<RemoteDataSource> { get<Retrofit>().create(RemoteDataSource::class.java) }

        single {
            Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        }

        single {
            OkHttpClient.Builder()
                .addInterceptor(timeoutObserverInterceptor)
                .connectTimeout(45, TimeUnit.SECONDS)
                .readTimeout(45, TimeUnit.SECONDS)
                .writeTimeout(45, TimeUnit.SECONDS)
                .build()
        }

        single {
            Retrofit.Builder()
                .baseUrl("https://pet-journal.app/")
                .client(get())
                .addConverterFactory(MoshiConverterFactory.create(get()))
                .addCallAdapterFactory(NetworkResultCallAdapterFactory.create())
                .build()
        }

        viewModel<HomeScreenViewModel> {
            HomeScreenViewModelImpl(
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
            )
        }

        viewModel<IntroRegisterPetViewModel> {
            com.soujunior.petjournal.ui.screensapp.screenspets.introRegisterPetScreen.IntroRegisterPetViewModelImpl(
                get(),
                get(),
                get(),
            )
        }
        viewModel<LoginViewModel> { LoginViewModelImpl(get(), get(), get(), get(), get()) }
        viewModel<RegisterViewModel> { RegisterViewModelImpl(get(), get()) }
        viewModel<AwaitingCodeViewModel> { AwaitingCodeViewModelImpl(get(), get(), get()) }
        viewModel<ForgotPasswordViewModel> { ForgotPasswordViewModelImpl(get(), get()) }
        viewModel<ChangePasswordViewModel> { ChangePasswordViewModelImpl(get(), get()) }
        viewModel { SplashViewModel(get()) }
        viewModel<ViewModelChoiceSpecies> { ViewModelChoiceSpeciesImpl(get(), get(), get()) }
        viewModel<PetListViewModel> { PetListViewModelImpl(get(), get()) }

        viewModel<PetRegisterViewModel> {
            PetRegisterViewModelImpl(
                savedStateHandle = get(),
                createPetUseCase = get(),
                getListBreedUseCase = get(),
                getListSizeUseCase = get(),
                getPetUseCase = get(),
                updatePetUseCase = get(),
            )
        }

        viewModel<ViewModelNameGender> {
            ViewModelNameGenderImpl(
                validation = get(),
                getPetInformationUseCase = get(),
                updatePetInformationUseCase = get(),
            )
        }

        viewModel<BirthDateViewModel> { BirthDateViewModelImpl(get(), get(), get(), get()) }
        viewModel<ViewModelRaceSize> { ViewModelRaceSizeImpl(get(), get(), get(), get(), get()) }
        viewModel<RegisterTaskViewModel> { RegisterTaskViewModelImpl(get(), get(), get(), get(), get(), get(), androidContext()) }
        viewModel<TaskListViewModel> { TaskListViewModelImpl(get(), get(), get()) }
        viewModel<TutorViewModel> { TutorViewModelImpl(get(), get(), get()) }
        viewModel { SettingsViewModel(get(), get()) }
    }
