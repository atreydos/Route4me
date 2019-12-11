package io.atreydos.route4me.domain.common

open class ODResult<out T : Any> {
    class Loading() : ODResult<Nothing>()
    data class Success<out T : Any>(val data: T) : ODResult<T>()
    data class Error(val exception: Exception) : ODResult<Nothing>()
}