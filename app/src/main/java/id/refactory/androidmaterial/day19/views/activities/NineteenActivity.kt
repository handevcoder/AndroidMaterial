package id.refactory.androidmaterial.day19.views.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import id.refactory.androidmaterial.databinding.ActivityNineteenBinding
import id.refactory.androidmaterial.day19.models.ProductModel
import id.refactory.androidmaterial.day19.presenters.ProductPresenter
import id.refactory.androidmaterial.day19.repositories.remote.ProductRemoteRepository
import id.refactory.androidmaterial.day19.repositories.remote.clients.ProductClient
import id.refactory.androidmaterial.day19.repositories.remote.services.ProductService
import id.refactory.androidmaterial.day19.views.adapters.ProductAdapter
import id.refactory.androidmaterial.day19.views.contracts.Product

class NineteenActivity : AppCompatActivity(), Product.View, ProductAdapter.ProductListener {

    private val binding by lazy { ActivityNineteenBinding.inflate(layoutInflater) }
    private val adapter by lazy { ProductAdapter(this, this) }
    private val service: ProductService by lazy { ProductClient.service }
    private val repository: ProductRemoteRepository by lazy { ProductRemoteRepository(service) }
    private val presenter: Product.Presenter by lazy { ProductPresenter(this, repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rvProduct.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        presenter.getAllProduct()
    }

    override fun onSuccessGetAllProduct(products: List<ProductModel>) {
        adapter.list = products.toMutableList()
    }

    override fun onSuccessCreateProduct(product: ProductModel) {
        showMessage("Berhasil menambahkan product")
    }

    override fun onSuccessUpdateProduct(product: ProductModel) {
        showMessage("Berhasil mengubah product")
    }

    override fun onSuccessDeleteProduct(product: ProductModel) {
        showMessage("Berhasil menghapus product")
    }

    override fun onError(t: Throwable) {
        showMessage(t.message ?: "Oops something went wrong")
    }

    private fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onLoading(isLoading: Boolean) {
        binding.run {
            pbLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
            rvProduct.visibility = if (!isLoading) View.VISIBLE else View.GONE
        }
    }

    override fun onClick(productModel: ProductModel) {

    }

    override fun onDelete(productModel: ProductModel) {
        presenter.deleteProduct(productModel)
    }
}