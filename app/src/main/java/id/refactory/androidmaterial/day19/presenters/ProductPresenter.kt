package id.refactory.androidmaterial.day19.presenters

import id.refactory.androidmaterial.day19.models.ProductModel
import id.refactory.androidmaterial.day19.models.toResponse
import id.refactory.androidmaterial.day19.repositories.ProductRemoteRepository
import id.refactory.androidmaterial.day19.repositories.remote.responses.ProductResponse
import id.refactory.androidmaterial.day19.repositories.remote.responses.toModel
import id.refactory.androidmaterial.day19.views.contracts.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductPresenter(
    private val view: Product.View,
    private val repository: ProductRemoteRepository
) : Product.Presenter {

    override fun getAllProduct() {
        view.onLoading(true)

        repository.getAllProduct().enqueue(object : Callback<List<ProductResponse>> {
            override fun onResponse(
                call: Call<List<ProductResponse>>,
                response: Response<List<ProductResponse>>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        response.body()?.let {
                            view.onSuccessGetAllProduct(
                                it.asSequence().map { productResponse ->
                                    productResponse.toModel()
                                }.toList()
                            )
                        }
                    } else {
                        Throwable(message = "Responsenya adalah null").let { view.onError(it) }
                    }
                } else {
                    Throwable(message = response.message()).let { view.onError(it) }
                }

                view.onLoading(false)
            }

            override fun onFailure(call: Call<List<ProductResponse>>, t: Throwable) {
                onError(t)
            }
        })
    }

    fun onError(t: Throwable) {
        t.printStackTrace()

        view.onError(t)
        view.onLoading(false)
    }

    override fun createProduct(productModel: ProductModel) {
        view.onLoading(true)

        repository.createProduct(productModel.toResponse())
            .enqueue(object : Callback<ProductResponse> {
                override fun onResponse(
                    call: Call<ProductResponse>,
                    response: Response<ProductResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            response.body()?.let {
                                view.onSuccessCreateProduct(it.toModel())
                            }
                        } else {
                            Throwable(message = "Responsenya adalah null").let { view.onError(it) }
                        }
                    } else {
                        Throwable(message = response.message()).let { view.onError(it) }
                    }

                    view.onLoading(false)
                }

                override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                    onError(t)
                }
            })
    }

    override fun updateProduct(productModel: ProductModel) {
        view.onLoading(true)

        repository.updateProductById(productModel.toResponse())
            .enqueue(object : Callback<ProductResponse> {
                override fun onResponse(
                    call: Call<ProductResponse>,
                    response: Response<ProductResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            response.body()?.let {
                                view.onSuccessUpdateProduct(it.toModel())
                            }
                        } else {
                            Throwable(message = "Responsenya adalah null").let { view.onError(it) }
                        }
                    } else {
                        Throwable(message = response.message()).let { view.onError(it) }
                    }

                    view.onLoading(false)
                }


                override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                    onError(t)
                }
            })
    }

    override fun deleteProduct(productModel: ProductModel) {
        view.onLoading(true)

        repository.deleteProductById(productModel.id)
            .enqueue(object : Callback<ProductResponse> {
                override fun onResponse(
                    call: Call<ProductResponse>,
                    response: Response<ProductResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            response.body()?.let {
                                view.onSuccessDeleteProduct(it.toModel())
                            }
                        } else {
                            Throwable(message = "Responsenya adalah null").let { view.onError(it) }
                        }
                    } else {
                        Throwable(message = response.message()).let { view.onError(it) }
                    }

                    view.onLoading(false)
                }


                override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                    onError(t)
                }
            })
    }
}