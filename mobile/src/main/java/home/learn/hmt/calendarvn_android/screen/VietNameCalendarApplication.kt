package home.learn.hmt.calendarvn_android.screen

import android.app.Application
import home.learn.hmt.calendarvn_android.di.getApiModule
import home.learn.hmt.calendarvn_android.di.getAppModule
import home.learn.hmt.calendarvn_android.di.getViewModelModule
import org.koin.android.ext.android.startKoin

class VietNameCalendarApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(
                getApiModule(),
                getAppModule(),
                getViewModelModule
        ))
    }
}