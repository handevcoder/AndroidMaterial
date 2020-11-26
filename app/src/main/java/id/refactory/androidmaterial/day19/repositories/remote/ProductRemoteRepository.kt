package id.refactory.androidmaterial.day19.repositories.remote

import id.refactory.androidmaterial.day19.repositories.ProductRemoteRepository
import id.refactory.androidmaterial.day19.repositories.remote.responses.ProductResponse
import id.refactory.androidmaterial.day19.repositories.remote.services.ProductService
import retrofit2.Call

class ProductRemoteRepository(private val service: ProductService):
    ProductRemoteRepository {
    override fun getAllProduct(): Call<List<ProductResponse>> {
        return service.getAllProduct()
    }

    override fun createProduct(product: ProductResponse): Call<ProductResponse> {
        return service.createProduct(product)
    }

    override fun updateProductById(product: ProductResponse): Call<ProductResponse> {
        return service.updateProductById(product.id, product)
    }

    override fun deleteProductById(id: Int): Call<ProductResponse> {
        return service.deleteProductById(id)
    }
}