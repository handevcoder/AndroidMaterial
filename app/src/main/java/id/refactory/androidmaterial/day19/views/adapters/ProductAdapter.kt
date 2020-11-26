package id.refactory.androidmaterial.day19.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.refactory.androidmaterial.databinding.ItemProductLatestBinding
import id.refactory.androidmaterial.day19.models.ProductModel

class ProductAdapter(
    private val context: Context,
    private val listener: ProductListener
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    interface ProductListener {
        fun onClick(productModel: ProductModel)
        fun onDelete(productModel: ProductModel)
    }

    inner class ViewHolder(
        private val binding: ItemProductLatestBinding,
        private val listener: ProductListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(productModel: ProductModel) {
            binding.run {
                tvProduct.text = productModel.title
                tvPrice.text = "$${productModel.price}"

                Glide.with(binding.root).load(productModel.image).into(ivProduct)

                root.setOnClickListener { listener.onClick(productModel) }
                ivDelete.setOnClickListener { listener.onDelete(productModel) }
            }
        }
    }

    var list = mutableListOf<ProductModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemProductLatestBinding.inflate(LayoutInflater.from(context), parent, false),
            listener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(list[position])
    }

    override fun getItemCount(): Int = list.size
}