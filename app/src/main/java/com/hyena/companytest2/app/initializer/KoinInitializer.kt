package com.hyena.companytest2.app.initializer

import android.content.Context
import androidx.startup.Initializer
import com.hyena.companytest2.home.biometricModule
import com.hyena.companytest2.home.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class KoinInitializer: Initializer<Unit> {

    override fun create(appContext: Context) {
        startKoin {
            androidLogger()
            androidContext(appContext)
            modules(
                listOf(
                    homeModule,
                    biometricModule
                )
            )
        }
        Timber.d("[DEBUG] KoinInitializer onCreate")
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = listOf(TimberInitializer::class.java)
}