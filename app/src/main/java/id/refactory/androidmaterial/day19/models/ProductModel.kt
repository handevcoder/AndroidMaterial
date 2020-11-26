package id.refactory.androidmaterial.day19.models

import android.os.Parcelable
import id.refactory.androidmaterial.day19.repositories.remote.responses.ProductResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductModel(
    val id: Int,
    var title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String
) : Parcelable

fun ProductModel.toResponse() = ProductResponse(id, title, price, description, category, image)