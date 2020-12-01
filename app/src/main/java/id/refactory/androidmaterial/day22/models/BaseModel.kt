package id.refactory.androidmaterial.day22.models

data class BaseModel<DATA>(val status: Boolean, val message: String, val data: DATA)