package io.atreydos.route4me.data.db.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update


interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg obj: T): Array<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: List<T>): Array<Long>

    @Update
    fun update(obj: T)

    @Update
    fun update(vararg obj: T)

    @Update
    fun update(objects: List<T>)

    @Delete
    fun delete(obj: T)

    @Delete
    fun delete(vararg obj: T)

    @Delete
    fun delete(objects: List<T>)
}