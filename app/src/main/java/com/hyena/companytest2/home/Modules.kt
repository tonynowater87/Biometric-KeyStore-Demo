package com.hyena.companytest2.home

import androidx.biometric.BiometricManager
import com.hyena.companytest2.home.biometric.BiometricHelper
import com.hyena.companytest2.home.biometric.keystore.KeyStoreHelper
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {

    viewModel { HomeViewModel(app = get(), biometricHelper = get()) }
}

val biometricModule = module {

    single { KeyStoreHelper(context = androidContext()) }

    single { BiometricManager.from(androidContext()) }

    single { BiometricHelper(context = get(), biometricManager = get(), keyStoreHelper = get()) }
}