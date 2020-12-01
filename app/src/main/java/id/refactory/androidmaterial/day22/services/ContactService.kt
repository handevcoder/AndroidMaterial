package id.refactory.androidmaterial.day22.services

import id.refactory.androidmaterial.day22.models.BaseModel
import id.refactory.androidmaterial.day22.models.ContactModel
import okhttp3.RequestBody
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PartMap

interface ContactService {
    @Multipart
    @POST("v1/contacts")
    fun insertContact(
        @Header("Authorization") token: String,
        @PartMap map: HashMap<String, RequestBody>
    ): BaseModel<ContactModel>
}