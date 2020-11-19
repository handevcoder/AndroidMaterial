package id.refactory.androidmaterial.day14.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.refactory.androidmaterial.databinding.ItemCategoryBinding
import id.refactory.androidmaterial.databinding.ItemProductNewBinding
import id.refactory.androidmaterial.day14.models.ProductModel
import java.util.*

sealed class Product {
    data class Header(val category: String) : Product()
    data class Row(val item: ProductModel) : Product()
}

class ProductAdapter(
    private val context: Context, private val listener: ProductListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface ProductListener {
        fun onSelect(id: Int, isSelect: Boolean)
    }

    private val header = 0
    private val row = 1

    inner class RowViewHolder(
        private val binding: ItemProductNewBinding,
        private val listener: ProductListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun binData(productModel: ProductModel) {
            binding.run {
                tvProduct.text = productModel.title

                Glide.with(binding.root).load(productModel.image).into(ivProduct)

                cbCheck.setOnCheckedChangeListener { _, isChecked ->
                    listener.onSelect(
                        productModel.id,
                        isChecked
                    )
                }
            }
        }
    }

    inner class HeaderViewHolder(
        private val binding: ItemCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(category: String) {
            binding.tvCategory.text = category.toUpperCase(Locale.getDefault())
        }
    }

    var list = listOf<Product>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemViewType(position: Int): Int = when (list[position]) {
        is Product.Header -> header
        is Product.Row -> row
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            header -> HeaderViewHolder(
                ItemCategoryBinding.inflate(LayoutInflater.from(context), parent, false)
            )
            row -> RowViewHolder(
                ItemProductNewBinding.inflate(LayoutInflater.from(context), parent, false),
                listener
            )
            else -> throw IllegalArgumentException("Unsupported view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val product = list[position]

        if (product is Product.Row && holder is RowViewHolder) {
            holder.binData(product.item)
        } else if (product is Product.Header && holder is HeaderViewHolder) {
            holder.bindData(product.category)
        }
    }

    override fun getItemCount(): Int = list.size
}