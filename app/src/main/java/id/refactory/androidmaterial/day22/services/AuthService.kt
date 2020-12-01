package id.refactory.androidmaterial.day22.services

import id.refactory.androidmaterial.day22.models.AuthModel
import id.refactory.androidmaterial.day22.models.BaseModel
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("v1/signin")
    suspend fun login(@Body body: HashMap<String, Any>): BaseModel<AuthModel>
}