package com.istudio.podnest.core.common.flow

import com.istudio.podnest.core.common.result.AppResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

/**
 * Wraps any [Flow] of domain data into a `Flow<AppResult<T>>`, emitting
 * [AppResult.Loading] first, then [AppResult.Success] for every subsequent
 * emission, and [AppResult.Error] if the upstream flow throws.
 *
 * Repository implementations (offline-first: Room emits first, network
 * refresh triggers a second emission) plug straight into this so every
 * ViewModel across every feature module consumes results identically.
 */
fun <T> Flow<T>.asAppResultFlow(): Flow<AppResult<T>> =
    map<T, AppResult<T>> { AppResult.Success(it) }
        .onStart { emit(AppResult.Loading) }
        .catch { throwable ->
            emit(AppResult.Error(throwable.message ?: "Something went wrong", throwable))
        }
