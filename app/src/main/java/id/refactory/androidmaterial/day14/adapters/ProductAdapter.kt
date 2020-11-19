package id.refactory.androidmaterial.day14.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.refactory.androidmaterial.databinding.ItemCategoryBinding
import id.refactory.androidmaterial.databinding.ItemProductNewBinding
import id.refactory.androidmaterial.day14.callbacks.DragAndDropCallback
import id.refactory.androidmaterial.day14.models.ProductModel
import java.util.*

sealed class Product {
    data class Header(val category: String) : Product()
    data class Row(val item: ProductModel) : Product()
}

class ProductAdapter(
    private val context: Context,
    private val listener: ProductListener,
    private val dragListener: DragListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), DragAndDropCallback.DragAndDropListener {

    interface DragListener {
        fun requestDrag(viewHolder: RecyclerView.ViewHolder)
    }

    interface ProductListener {
        fun onSelect(id: Int, isSelect: Boolean)
    }

    private val header = 0
    private val row = 1

    inner class RowViewHolder(
        private val binding: ItemProductNewBinding,
        private val listener: ProductListener,
        private val dragListener: DragListener
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("ClickableViewAccessibility")

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

                root.setOnTouchListener { view, event ->
                    view.performClick()

                    if (event.action == MotionEvent.ACTION_DOWN) dragListener.requestDrag(this@RowViewHolder)

                    false
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
                listener,
                dragListener
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
    override fun onRowMoved(from: RecyclerView.ViewHolder, to: RecyclerView.ViewHolder) {
        val fromProduct = list[from.adapterPosition]
        val toProduct = list[to.adapterPosition]

        if (from is RowViewHolder && to is RowViewHolder && fromProduct is Product.Row && toProduct is Product.Row) {
            if (fromProduct.item.category == toProduct.item.category) {
                if (from.adapterPosition < to.adapterPosition) {
                    for (i in from.adapterPosition until to.adapterPosition) {
                        Collections.swap(list, i, i + 1)
                    }
                } else {
                    for (i in from.adapterPosition downTo to.adapterPosition + 1) {
                        Collections.swap(list, i, i - 1)
                    }
                }

                notifyItemMoved(from.adapterPosition, to.adapterPosition)
            }
        }
    }
}