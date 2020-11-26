package id.refactory.androidmaterial.day19.repositories

import id.refactory.androidmaterial.day19.repositories.remote.responses.ProductResponse
import retrofit2.Call

interface ProductRemoteRepository {
    fun getAllProduct(): Call<List<ProductResponse>>
    fun createProduct(product: ProductResponse): Call<ProductResponse>
    fun updateProductById(product: ProductResponse): Call<ProductResponse>
    fun deleteProductById(id: Int): Call<ProductResponse>
}