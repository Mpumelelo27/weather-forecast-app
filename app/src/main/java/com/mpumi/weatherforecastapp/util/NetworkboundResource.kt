package com.mpumi.weatherforecastapp.util

import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType> networkBoundResource(
   crossinline query: () -> Flow<ResultType>,
   crossinline fetch: suspend () -> RequestType,
   crossinline  saveFetchResult: suspend (RequestType) -> Unit,
   crossinline  shouldFetchData: (ResultType) -> Boolean = { true }
) = flow {
    val data = query().first()

    val flow = if (shouldFetchData(data)) {
        emit(Resource.Loading(data))
        try {
            //We have this catch in case the retrofit network request fails or we get an error from the server
            saveFetchResult(fetch())
            query().map { Resource.Success(it) }
        } catch (throwable: Throwable) {
            query().map { Resource.Error(throwable, it) }
        }
    } else {
        query().map {
            Resource.Success(it)
        }
    }
    emitAll(flow)
}