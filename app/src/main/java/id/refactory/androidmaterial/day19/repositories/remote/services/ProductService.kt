package id.refactory.androidmaterial.day19.repositories.remote.services

import id.refactory.androidmaterial.day19.repositories.remote.responses.ProductResponse
import retrofit2.Call
import retrofit2.http.*

interface ProductService {
    @GET("products")
    fun getAllProduct(): Call<List<ProductResponse>>

    @POST("products")
    fun createProduct(product: ProductResponse): Call<ProductResponse>

    @PUT("products/{id}")
    fun updateProductById(@Path("id") id: Int, product: ProductResponse): Call<ProductResponse>

    @DELETE("products/{id}")
    fun deleteProductById(@Path("id") id: Int): Call<ProductResponse>
}