package com.jerryokafor.smshare.core.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

/**
 * Defines the possible outcomes of a request for a resource.
 * Success, with the requested data, or Failure, with an error response.
 */
sealed class Outcome<out T> {
    fun fold(
        onSuccess: (T) -> Unit,
        onFailure: (String) -> Unit,
    ) {
        when (this) {
            is Success -> onSuccess(data)
            is Failure -> onFailure(errorResponse)
        }
    }
}

/**
 * Called when the given request fails to make a request
 *
 * @property errorResponse is the  error message returned
 * @property errorCode is the HTTP error code
 *
 * @property throwable exception stack trace if the failure resulted from an exception
 */
data class Failure(
    val errorResponse: String,
    val errorCode: Int = -1,
    val throwable: Throwable? = null,
) : Outcome<Nothing>()


/**
 * Called when the given request is successful.
 *
 * @param T is object Type
 * @property data is the object requested from backend
 */
open class Success<out T>(val data: T) : Outcome<T>() {
    operator fun invoke(): T = data

    override fun equals(other: Any?): Boolean {
        return (other as? Success<*>)?.data?.equals(this.data) == true
    }

    override fun hashCode(): Int {
        return data?.hashCode() ?: 0
    }
}


/**
 * Filters and maps a [Flow<Outcome<T>>] to T value, ignoring any failures.
 */
fun <T> Flow<Outcome<T>>.success(): Flow<T> = this
    .filter { it is Success }
    .map { (it as Success).invoke() }

/**
 * Maps a [Flow<Outcome<T>>] to a [Flow<OutCome<R>>]. Allows any successful Outcome to be mutated.
 */
inline fun <T, reified R> Flow<Outcome<T>>.mapSuccess(
    crossinline onSuccess: (T) -> R,
): Flow<Outcome<R>> = this
    .map {
        when (it) {
            is Success -> Success(onSuccess(it()))
            is Failure -> it
        }
    }

/**
 * Maps a [Flow<Outcome<T>>]  to a [Flow<Outcome<R>>]. Successful response can be mapped to any [Outcome]
 */
inline fun <T, R> Flow<Outcome<T>>.mapOutcome(
    crossinline onSuccess: (Success<T>) -> Outcome<R>,
): Flow<Outcome<R>> = this
    .map {
        when (it) {
            is Success -> onSuccess(it)
            is Failure -> it
        }
    }

inline fun <T> Flow<Outcome<T>>.onSuccess(
    crossinline action: suspend (data: T) -> Unit,
): Flow<Outcome<T>> = this
    .onEach { if (it is Success) action(it.data) }

inline fun <T> Flow<Outcome<T>>.onFailure(
    crossinline action: suspend (
        errorResponse: String,
        errorCode: Int,
        throwable: Throwable?,
    ) -> Unit,
): Flow<Outcome<T>> = this
    .onEach { if (it is Failure) action(it.errorResponse, it.errorCode, it.throwable) }
