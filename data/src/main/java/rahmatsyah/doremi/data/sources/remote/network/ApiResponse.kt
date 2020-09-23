package rahmatsyah.doremi.data.sources.remote.network

sealed class ApiResponse<out R> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val message: String) : ApiResponse<Nothing>()
    object Empty : ApiResponse<Nothing>()
}