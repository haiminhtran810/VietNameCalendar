package home.learn.hmt.calendarvn_android.di

import android.app.Application
import android.content.Context
import android.content.res.Resources
import home.learn.hmt.calendarvn_android.screen.VietNameCalendarApplication
import org.koin.dsl.module.module

fun getAppModule() = module(override = true) {
    single { provideApplication(get()) }
    single { provideResource(get()) }
    single { provideContext(get()) }
}

fun provideApplication(mainApplication: VietNameCalendarApplication): VietNameCalendarApplication = mainApplication

fun provideResource(application: Application): Resources = application.resources

fun provideContext(application: Application): Context = application