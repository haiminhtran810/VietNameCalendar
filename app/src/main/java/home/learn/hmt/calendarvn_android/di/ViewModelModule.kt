package home.learn.hmt.calendarvn_android.di

import androidx.lifecycle.ViewModel
import home.learn.hmt.calendarvn_android.screen.MainViewModel
import home.learn.hmt.calendarvn_android.screen.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val getViewModelModule = module(override = true) {
    viewModel { HomeViewModel() }
    viewModel { MainViewModel() }
}