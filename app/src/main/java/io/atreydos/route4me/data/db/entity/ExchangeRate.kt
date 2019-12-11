package io.atreydos.route4me.data.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "rates", indices = [Index(value = ["currency_symbol"], unique = true)])
data class ExchangeRate(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "currency_symbol") var symbol: String = "",
    @ColumnInfo(name = "rate") var rate: Double = .0
) : Parcelable