package id.refactory.androidmaterial.day19.views.contracts

import id.refactory.androidmaterial.day19.models.ProductModel

class Product {
    interface View {
        fun onSuccessGetAllProduct(products: List<ProductModel>)
        fun onSuccessCreateProduct(product: ProductModel)
        fun onSuccessUpdateProduct(product: ProductModel)
        fun onSuccessDeleteProduct(product: ProductModel)
        fun onError(t: Throwable)
        fun onLoading(isLoading: Boolean)
    }

    interface Presenter {
        fun getAllProduct()
        fun createProduct(productModel: ProductModel)
        fun updateProduct(productModel: ProductModel)
        fun deleteProduct(productModel: ProductModel)
    }
}