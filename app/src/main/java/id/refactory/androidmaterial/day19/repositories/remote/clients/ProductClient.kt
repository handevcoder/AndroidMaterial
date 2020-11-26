package id.refactory.androidmaterial.day19.repositories.remote.clients

import com.google.gson.GsonBuilder
import id.refactory.androidmaterial.day19.repositories.remote.services.ProductService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductClient {
    companion object {
        private const val BASE_URL = "https://fakestoreapi.com/"

        val service by lazy {
            val retrofit by lazy {
                Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(
                        GsonConverterFactory.create(GsonBuilder().setLenient().create())
                    )
                    .build()
            }

            retrofit.create(ProductService::class.java)
        }
    }
}