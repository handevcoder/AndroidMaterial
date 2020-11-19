package id.refactory.androidmaterial.day14.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.refactory.androidmaterial.databinding.ItemProductBinding
import id.refactory.androidmaterial.day13.models.ProductModel

class ProductAdapter(
    private val context: Context, private val listener: ProductListener
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    interface ProductListener {
        fun onSelect(id: Int)
        fun onDelete(id: Int)
    }

    inner class ViewHolder(
        private val binding: ItemProductBinding,
        private val listener: ProductListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun binData(productModel: ProductModel) {
            binding.run {
                tvProduct.text = productModel.title

                Glide.with(binding.root).load(productModel.image).into(ivProduct)

                root.setOnClickListener { listener.onSelect(productModel.id) }
                btnDelete.setOnClickListener { listener.onDelete(productModel.id) }
            }
        }
    }

    var list = listOf<ProductModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun deleteProductById(id: Int) {
        val index = list.indexOfFirst { it.id == id }

        if (index != -1) {
            notifyItemRemoved(index)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        return ViewHolder(
            ItemProductBinding.inflate(LayoutInflater.from(context), parent, false),
            listener
        )
    }

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        holder.binData(list[position])
    }

    override fun getItemCount(): Int = list.size
}