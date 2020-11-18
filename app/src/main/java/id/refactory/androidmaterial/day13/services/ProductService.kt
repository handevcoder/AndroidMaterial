package id.refactory.androidmaterial.day13.services

import id.refactory.androidmaterial.day13.models.ProductModel
import retrofit2.Call
import retrofit2.http.*

interface ProductService {
    @GET("products")
    fun getAllProduct(): Call<List<ProductModel>>

    @GET("products/{id}")
    fun getProductById(@Path("id") id: Int): Call<ProductModel>

    @POST("products")
    @Headers("Content-Type: application/json")
    fun insertProduct(@Body productModel: ProductModel): Call<ProductModel>

    @PUT("products/{id}")
    @Headers("Content-Type: application/json")
    fun updateProductById(@Path("id") id: Int, @Body productModel: ProductModel): Call<ProductModel>

    @DELETE("products/{id}")
    fun deleteProductById(@Path("id") id: Int): Call<ProductModel>
}