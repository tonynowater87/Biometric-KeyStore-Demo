object Versions {
    const val COMPILE_SDK = 29
    const val BUILD_TOOL = "29.0.3"
    const val MIN_SDK = 21
    const val TARGET_SDK = 29
    const val CODE = 1
    const val NAME = "0.1.0"
}

object Root {
    const val GRADLE = "com.android.tools.build:gradle:3.6.3"
    const val KOTLIN_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.71"
    const val KOTLIN_STD_LIB = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.71"
    const val FIREBASE_CRASHLYTICS_PLUGIN = "com.google.firebase:firebase-crashlytics-gradle:2.1.1" // Firebase Crashlytics Gradle plugin.
    const val NAVIGATION_SAFE_ARGS_PLUGIN = "androidx.navigation:navigation-safe-args-gradle-plugin:2.3.0-rc01"
}

// Google
// https://developer.android.com/jetpack/androidx/versions
// https://github.com/material-components/material-components-android/tags
// ==========
object Google {

    object Versions {
        const val navigationVer = "2.3.0-rc01"
        const val lifecycleVer = "2.2.0"
        const val roomVer = "2.2.5"
        const val workManagerVer = "2.3.4"
    }

    const val APP_COMPAT = "androidx.appcompat:appcompat:1.1.0"
    const val ANNOTATION = "androidx.annotation:annotation:1.1.0"
    const val RECYCLER_VIEW = "androidx.recyclerview:recyclerview:1.1.0"
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:1.1.3"
    const val BROADCAST_MANAGER = "androidx.localbroadcastmanager:localbroadcastmanager:1.0.0"
    const val MULTIDEX = "androidx.multidex:multidex:2.0.1"
    const val KTX_CORE = "androidx.core:core-ktx:1.3.0"
    const val KTX_FRAGMENT = "androidx.fragment:fragment-ktx:1.2.5"
    const val SECURITY_CRYPTO = "androidx.security:security-crypto:1.0.0-rc02"
    const val MATERIAL = "com.google.android.material:material:1.1.0"
    const val ARCH_TEST = "androidx.arch.core:core-testing:2.1.0"
    const val STARTUP = "androidx.startup:startup-runtime:1.0.0-alpha01"
    const val BIOMETRIC = "androidx.biometric:biometric:1.0.1"

    const val LIFECYCLE_COMPILER = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycleVer}" // Annotation processor
    const val LIFECYCLE_VIEW_MODEL_SAVE_STATE = "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycleVer}" // Saved state module for ViewModel

    const val NAVIGATION_FRAGMENT = "androidx.navigation:navigation-fragment-ktx:${Versions.navigationVer}"
    const val NAVIGATION_UI = "androidx.navigation:navigation-ui-ktx:${Versions.navigationVer}"
    const val NAVIGATION_TEST = "androidx.navigation:navigation-testing:${Versions.navigationVer}"

    const val ROOM = "androidx.room:room-runtime:${Versions.roomVer}"
    const val ROOM_COMPILER = "androidx.room:room-compiler:${Versions.roomVer}"
    const val ROOM_RXJAVA2 = "androidx.room:room-rxjava2:${Versions.roomVer}" // optional - RxJava support for Room
    const val ROOM_KTX = "androidx.room:room-ktx:${Versions.roomVer}" // optional - Kotlin Extensions and Coroutines support for Room
    const val ROOM_TEST = "androidx.room:room-testing:${Versions.roomVer}"

    const val WORK_MANAGER = "androidx.work:work-runtime-ktx:${Versions.workManagerVer}"
    const val WORK_MANAGER_RX = "androidx.work:work-rxjava2:${Versions.workManagerVer}"
    const val WORK_MANAGER_TESTING = "androidx.work:work-testing:${Versions.workManagerVer}"
}

// Firebase
// https://firebase.google.com/docs/android/setup#add-sdks
// ==========
object Firebase {
    const val ANALYTICS = "com.google.firebase:firebase-analytics:17.4.1"
    const val MESSAGING = "com.google.firebase:firebase-messaging:20.1.7"
    const val CRASHLYTICS = "com.google.firebase:firebase-crashlytics:17.0.0"
}

// Koin
// https://github.com/InsertKoinIO/koin
// ==========
object Koin {
    private const val version = "2.0.1"
    const val ANDROID = "org.koin:koin-android:$version"
    const val SCOPE = "org.koin:koin-android-scope:$version"
    const val VIEWMODEL = "org.koin:koin-android-viewmodel:$version"
}

// RxJava
// https://github.com/ReactiveX/RxJava
// https://github.com/ReactiveX/RxAndroid
// https://github.com/ReactiveX/RxKotlin
// ==========
object RxJava {
    const val CORE = "io.reactivex.rxjava3:rxjava:3.0.3"
    const val ANDROID = "io.reactivex.rxjava3:rxandroid:3.0.0"
    const val KOTLIN = "io.reactivex.rxjava3:rxkotlin:3.0.0"
}

// Retrofit
// https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor
// https://github.com/square/retrofit
// ==========
object Retrofit {
    private const val version = "2.9.0"
    const val CORE = "com.squareup.retrofit2:retrofit:$version"
    const val RXJAVA2 = "com.squareup.retrofit2:adapter-rxjava3:$version"
    const val MOSHI = "com.squareup.retrofit2:converter-moshi:$version"
    const val OKHTTP_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:3.9.0"
}

// Moshi
// https://github.com/square/moshi
object Moshi {
    private const val version = "1.9.3"
    const val MOSHI_CORE = "com.squareup.moshi:moshi-kotlin:$version"
}

// Glide
// https://github.com/bumptech/glide
// ==========
object Glide {
    private const val version = "4.11.0"
    const val CORE = "com.github.bumptech.glide:glide:$version"
    const val COMPILER = "com.github.bumptech.glide:compiler:$version"
}

// Utils
// https://github.com/razir/ProgressButton
// https://github.com/afollestad/material-dialogs
// https://github.com/JakeWharton/timber
// https://github.com/dlew/joda-time-android
// ==========
object Utils {
    const val PROGRESS_BUTTON = "com.github.razir.progressbutton:progressbutton:1.0.1"
    const val MATERIAL_DIALOG = "com.afollestad.material-dialogs:core:3.3.0"
    const val TIMBER = "com.jakewharton.timber:timber:4.7.1"
    const val JODA_TIME = "net.danlew:android.joda:2.10.6"
}

// Test
// https://github.com/junit-team/junit4
// https://github.com/nhaarman/mockito-kotlin
// https://github.com/LachlanMcKee/timber-junit-rule
// https://github.com/robolectric/robolectric
// ==========
object TestDep {
    const val JUNIT = "junit:junit:4.13"
    const val JUNIT_TIMBER = "net.lachlanmckee:timber-junit-rule:1.0.1"
    const val ROBOLECTRIC = "org.robolectric:robolectric:4.3.1"
    const val MOCKITO = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0"
}

object AndroidTestDep {
    const val JUNIT = "androidx.test.ext:junit:1.1.1"
    const val ESPRESSO = "androidx.test.espresso:espresso-core:3.2.0"
}