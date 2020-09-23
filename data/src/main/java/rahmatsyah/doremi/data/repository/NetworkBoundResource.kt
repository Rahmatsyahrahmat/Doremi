package rahmatsyah.doremi.data.repository

import kotlinx.coroutines.flow.*
import rahmatsyah.doremi.data.sources.remote.network.ApiResponse
import rahmatsyah.doremi.domain.common.Result

abstract class NetworkBoundResource<ResultType,RequestType>{

    private val result:Flow<Result<ResultType>> = flow {
        val dbSource = loadFromDB().first()
        emit(Result.Loading(dbSource))
        if (shouldFetch(dbSource)){
            when(val apiResponse = createCall().first()){
                is ApiResponse.Success->{
                    saveCallResult(apiResponse.data)
                    emitAll(loadFromDB().map {
                        Result.Success(
                            it
                        )
                    })
                }
                is ApiResponse.Empty ->{
                    emitAll(loadFromDB().map {
                        Result.Success(
                            it
                        )
                    })
                }
                is ApiResponse.Error ->{
                    onFetchFailed()
                    emit(
                        Result.Error<ResultType>(
                            apiResponse.message
                        )
                    )
                }
            }
        }
        else{
            emitAll(loadFromDB().map {
                Result.Success(it)
            })
        }
    }

    protected abstract fun loadFromDB():Flow<ResultType>

    protected abstract fun shouldFetch(data:ResultType?):Boolean

    protected abstract suspend fun createCall():Flow<ApiResponse<RequestType>>

    protected abstract suspend fun saveCallResult(data:RequestType)

    protected open fun onFetchFailed(){}

    fun asFlow() = result

}