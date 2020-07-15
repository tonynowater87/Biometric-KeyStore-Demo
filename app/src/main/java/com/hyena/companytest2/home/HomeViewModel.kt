package com.hyena.companytest2.home

import android.app.Application
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hyena.companytest2.R
import com.hyena.companytest2.home.biometric.BiometricAuthEvents
import com.hyena.companytest2.home.biometric.BiometricHelper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class HomeViewModel(
    app: Application,
    private val biometricHelper: BiometricHelper
) : ViewModel() {

    val keyFromCloud = MutableLiveData("")
    val encryptedData = MutableLiveData<String>()
    val decryptedData = MutableLiveData<String>()

    private val compositeDisposable = CompositeDisposable()
    private val observerForKeyChanged: (t: String) -> Unit = {
        encryptedData.postValue("")
        decryptedData.postValue("")
    }

    init {
        biometricHelper
            .events
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onNext = {
                Timber.d("[DEBUG] [BiometricAuthEvents] >>> $it")
                when (it) {
                    is BiometricAuthEvents.Authenticating -> {
                    }
                    is BiometricAuthEvents.AuthenticationSucceeded -> {
                    }
                    is BiometricAuthEvents.AuthenticationFailed -> {
                    }
                    is BiometricAuthEvents.UnSupportedBiometricAuth -> {
                        Toast.makeText(app, app.getString(R.string.un_support_message), Toast.LENGTH_SHORT).show()
                    }
                    is BiometricAuthEvents.AuthenticationError -> {
                        Toast.makeText(app, it.errorMessage, Toast.LENGTH_SHORT).show()
                    }
                    is BiometricAuthEvents.CryptographyError -> {
                        Toast.makeText(app, it.exception.message, Toast.LENGTH_SHORT).show()
                    }
                    is BiometricAuthEvents.EncryptCrypto -> {
                        encryptedData.postValue(it.encrypted)
                        decryptedData.postValue("")
                    }
                    is BiometricAuthEvents.DecryptCrypto -> {
                        decryptedData.postValue(it.decrypted)
                    }
                }
            })
            .also { compositeDisposable.add(it) }

        keyFromCloud.observeForever(observerForKeyChanged)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
        keyFromCloud.removeObserver(observerForKeyChanged)
    }

    fun encrypt(activity: FragmentActivity) {
        biometricHelper.authenticationWithCrypto(
            activity,
            keyFromCloud.value ?: "",
            BiometricHelper.Purpose.Encrypt
        )
    }

    fun decrypt(activity: FragmentActivity) {
        biometricHelper.authenticationWithCrypto(
            activity,
            encryptedData.value!!,
            BiometricHelper.Purpose.Decrypt
        )
    }
}