package id.refactory.androidmaterial.day21.repositories.remote.responses

data class BaseResponse<T>(val status: Boolean, val message: String, val data: T)
