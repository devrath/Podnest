package com.istudio.podnest.core.common.result

/**
 * A generic wrapper around any asynchronous/data operation result.
 *
 * Every repository function in Podnest returns a `Flow<AppResult<T>>` (see
 * [com.istudio.podnest.core.common.flow.asAppResultFlow]) so every ViewModel in every feature
 * module handles loading/success/error in exactly the same shape, with zero
 * feature-specific boilerplate.
 */
sealed interface AppResult<out T> {
    data object Loading : AppResult<Nothing>
    data class Success<T>(val data: T) : AppResult<T>
    data class Error(val message: String, val cause: Throwable? = null) : AppResult<Nothing>
}

inline fun <T> AppResult<T>.onSuccess(action: (T) -> Unit): AppResult<T> {
    if (this is AppResult.Success) action(data)
    return this
}

inline fun <T> AppResult<T>.onError(action: (String, Throwable?) -> Unit): AppResult<T> {
    if (this is AppResult.Error) action(message, cause)
    return this
}

fun <T> AppResult<T>.dataOrNull(): T? = (this as? AppResult.Success)?.data
