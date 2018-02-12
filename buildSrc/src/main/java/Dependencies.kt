object Versions {
    val kotlin = "1.2.21"
    val android_gradle_plugin = "3.0.1"
    val dexcount_plugin = "0.8.2"

    val androidKtxVersion = "0.1"
    val supportLibraryVersion = "27.0.2"
    val constraintLayoutVersion = "1.1.0-beta5"
    val archComponentsVersion = "1.1.0"
    val archComponentsPagingVersion = "1.0.0-alpha5"
    val roomVersion = "1.1.0-alpha1"
    val daggerVersion = "2.14.1"
    val rxKotlinVersion = "2.2.0"
    val rxAndroidVersion = "2.0.1"
    val rxBindingVersion = "2.1.0"
}

object Plugins {
    val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jre8:${Versions.kotlin}"
    val kotlin_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    val android_kotlin_extension_plugin = "org.jetbrains.kotlin:kotlin-android-extensions:${Versions.kotlin}"
    val android_gradle_plugin = "com.android.tools.build:gradle:${Versions.android_gradle_plugin}"
    val dexcount_plugin = "com.getkeepsafe.dexcount:dexcount-gradle-plugin:${Versions.dexcount_plugin}"
}

object Deps {
    val androidKtx = "androidx.core:core-ktx:${Versions.androidKtxVersion}"

    val supportAnnotation = "com.android.support:support-annotations:${Versions.supportLibraryVersion}"
    val supportAppCompat = "com.android.support:appcompat-v7:${Versions.supportLibraryVersion}"
    val supportDesign = "com.android.support:design:${Versions.supportLibraryVersion}"
    val supportRecyclerView = "com.android.support:recyclerview-v7:${Versions.supportLibraryVersion}"
    val supportConstraintLayout = "com.android.support.constraint:constraint-layout:${Versions.constraintLayoutVersion}"

    val androidArchLifecycleRuntime = "android.arch.lifecycle:runtime:${Versions.archComponentsVersion}"
    val androidArchLifecycleExtensions = "android.arch.lifecycle:extensions:${Versions.archComponentsVersion}"
    val androidArchLifecycleRx = "android.arch.lifecycle:reactivestreams:${Versions.archComponentsVersion}"
    val androidArchLifecycleCompiler = "android.arch.lifecycle:compiler:${Versions.archComponentsVersion}"
    val androidArchPaging = "android.arch.paging:runtime:${Versions.archComponentsPagingVersion}"
    val androidArchRoom = "android.arch.persistence.room:runtime:${Versions.roomVersion}"
    val androidArchRoomRx = "android.arch.persistence.room:rxjava2:${Versions.roomVersion}"
    val androidArchRoomCompiler = "android.arch.persistence.room:compiler:${Versions.roomVersion}"

    val daggerAndroid = "com.google.dagger:dagger-android:${Versions.daggerVersion}"
    val daggerAndroidSupport = "com.google.dagger:dagger-android-support:${Versions.daggerVersion}"
    val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.daggerVersion}"
    val daggerAndroidProcessor = "com.google.dagger:dagger-android-processor:${Versions.daggerVersion}"

    val rxKotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxKotlinVersion}"
    val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroidVersion}"
    val rxBindingKotlin = "com.jakewharton.rxbinding2:rxbinding-kotlin:${Versions.rxBindingVersion}"
    val rxBindingSupport = "com.jakewharton.rxbinding2:rxbinding-support-v4-kotlin:${Versions.rxBindingVersion}"
}