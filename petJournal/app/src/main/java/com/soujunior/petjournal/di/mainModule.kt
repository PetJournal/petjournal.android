package com.soujunior.petjournal.di

import com.soujunior.petjournal.ui.detailScreen.DetailScreenViewModel
import com.soujunior.petjournal.ui.detailScreen.DetailScreenViewModelImpl
import com.soujunior.petjournal.ui.homeScreen.HomeScreenViewModel
import com.soujunior.petjournal.ui.homeScreen.HomeScreenViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel<HomeScreenViewModel> { HomeScreenViewModelImpl() }
    viewModel<DetailScreenViewModel> { DetailScreenViewModelImpl() }
}