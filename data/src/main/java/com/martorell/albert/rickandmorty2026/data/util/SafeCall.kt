package com.martorell.albert.rickandmorty2026.data.util

import com.martorell.albert.rickandmorty2026.domain.model.Result
import kotlinx.coroutines.CancellationException

/**
 * Its goal is to maange the error and wrapper it into a Result object.
 * On the other hand, It avoids to add repetitive try/catch blocks into each Repository function.
 */
inline fun <T> safeCall(block: () -> T): Result<T> {
    return try {
        Result.Success(block())
    } catch (e: CancellationException) {
        throw e // to avoid coroutine continues to be executed
    } catch (e: Exception) {
        Result.Error(e)
    }
}