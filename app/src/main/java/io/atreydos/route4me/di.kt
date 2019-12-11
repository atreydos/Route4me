package io.atreydos.route4me

import io.atreydos.route4me.data.RatesRepositoryImpl
import io.atreydos.route4me.data.db.AppDatabase
import io.atreydos.route4me.data.net.ApiService
import io.atreydos.route4me.data.net.createGsonConverterFactory
import io.atreydos.route4me.data.net.createOkHttpClient
import io.atreydos.route4me.data.net.createWebService
import io.atreydos.route4me.data.prefs.AppPrefs
import io.atreydos.route4me.data.prefs.EncryptedAppPrefsImpl
import io.atreydos.route4me.domain.RatesRepository
import io.atreydos.route4me.presentation.details.RateHistoryViewModel
import io.atreydos.route4me.presentation.rates.RatesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Converter
import org.koin.androidx.viewmodel.dsl.viewModel

val appModule = module {
    single { RatesRepositoryImpl(get(), get(), get()) as RatesRepository }

    viewModel { RatesViewModel(get()) }
    viewModel { RateHistoryViewModel(get()) }
}

val apiModule = module {
    single { createOkHttpClient() }
    single<Converter.Factory> { createGsonConverterFactory() }
    factory<ApiService> { createWebService(get(), get(), BuildConfig.API_ENTRY_POINT) }
}

val dbModule = module {
    single { AppDatabase.newInstance(androidContext()) }
}

val preferencesModule = module {
    single { EncryptedAppPrefsImpl(androidContext()) as AppPrefs }
}

val applicationGlobalModule = listOf(appModule, apiModule, dbModule, preferencesModule)