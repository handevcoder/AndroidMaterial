package id.refactory.androidmaterial.day19.repositories.remote.responses

import id.refactory.androidmaterial.day19.models.ProductModel

data class ProductResponse(
    val id: Int,
    var title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String
)

fun ProductResponse.toModel() = ProductModel(id, title, price, description, category, image)
