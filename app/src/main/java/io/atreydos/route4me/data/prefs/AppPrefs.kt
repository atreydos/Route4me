package io.atreydos.route4me.data.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

interface AppPrefs {
    var exchangeRatesTimestamp: Long
}

class EncryptedAppPrefsImpl(context: Context) : AppPrefs {

    companion object {
        private const val PREFERENCES_FILENAME = "AppPreferences"

        private const val KEY_EXCHANGE_RATES_TIMESTAMP = "exchange_rates_timestamp"
    }

    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = EncryptedSharedPreferences.create(
            PREFERENCES_FILENAME,
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override var exchangeRatesTimestamp: Long
        get() = sharedPreferences.getLong(KEY_EXCHANGE_RATES_TIMESTAMP, 0)
        set(value) = sharedPreferences.edit().putLong(KEY_EXCHANGE_RATES_TIMESTAMP, value).apply()

}