package com.soujunior.petjournal.di

import com.soujunior.petjournal.ui.detailScreen.DetailScreenViewModel
import com.soujunior.petjournal.ui.detailScreen.DetailScreenViewModelImpl
import com.soujunior.petjournal.ui.homeScreen.HomeScreenViewModel
import com.soujunior.petjournal.ui.homeScreen.HomeScreenViewModelImpl
import com.soujunior.petjournal.ui.loginScreen.LoginScreenViewModel
import com.soujunior.petjournal.ui.loginScreen.LoginScreenViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel<HomeScreenViewModel> { HomeScreenViewModelImpl() }
    viewModel<DetailScreenViewModel> { DetailScreenViewModelImpl() }
    viewModel<LoginScreenViewModel> { LoginScreenViewModelImpl() }
}