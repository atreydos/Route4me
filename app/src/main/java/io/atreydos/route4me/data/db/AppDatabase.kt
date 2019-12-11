package io.atreydos.route4me.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.atreydos.route4me.data.db.AppDatabase.Companion.DB_VERSION
import io.atreydos.route4me.data.db.dao.ExchangeRatesDao
import io.atreydos.route4me.data.db.entity.ExchangeRate


@Database(
    version = DB_VERSION,
    entities = [ExchangeRate::class]
)
abstract class AppDatabase : RoomDatabase() {

    @Suppress("MemberVisibilityCanBePrivate")
    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "database.db"

        fun newInstance(context: Context): AppDatabase = Room
            .databaseBuilder(context, AppDatabase::class.java, DB_NAME)
            .allowMainThreadQueries()
            .build()
    }

    abstract fun exchangeRatesDao(): ExchangeRatesDao
}