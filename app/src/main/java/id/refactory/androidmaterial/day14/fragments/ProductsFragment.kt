package id.refactory.androidmaterial.day14.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.refactory.androidmaterial.databinding.DialogBulkDeleteBinding
import id.refactory.androidmaterial.databinding.FragmentProductsBinding
import id.refactory.androidmaterial.day14.adapters.Product
import id.refactory.androidmaterial.day14.adapters.ProductAdapter
import id.refactory.androidmaterial.day14.callbacks.DragAndDropCallback
import id.refactory.androidmaterial.day14.clients.ProductClient
import id.refactory.androidmaterial.day14.models.ProductModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProductsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductsFragment : Fragment(), ProductAdapter.ProductListener, ProductAdapter.DragListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var binding: FragmentProductsBinding
    private val adapter by lazy { ProductAdapter(requireContext(), this, this) }
    private val itemTouchHelper by lazy { ItemTouchHelper(DragAndDropCallback(adapter)) }
    private val list by lazy { mutableListOf<Int>() }
    private var products = mutableListOf<ProductModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductsBinding.inflate(inflater, container, false).apply {
            rvProduct.adapter = adapter
            rvProduct.layoutManager = LinearLayoutManager(requireContext())
            rvProduct.addItemDecoration(
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            )
            itemTouchHelper.attachToRecyclerView(rvProduct)

            fabAdd.setOnClickListener { deleteAll() }
        }

        ProductClient.service.getAllProduct().enqueue(object : Callback<List<ProductModel>> {
            override fun onResponse(
                call: Call<List<ProductModel>>,
                response: Response<List<ProductModel>>
            ) {
                response.body()?.let {
                    products = it.toMutableList()

                    generateProduct(it)
                }
            }

            override fun onFailure(call: Call<List<ProductModel>>, t: Throwable) {
                onError(t)
            }
        })

        return binding.root
    }

    private fun generateProduct(it: List<ProductModel>) {
        val list = mutableListOf<Product>()
        var temp = ""

        it.forEach { model ->
            if (!temp.equals(model.category, true)) {
                temp = model.category

                list.add(Product.Header(temp))
            }

            list.add(Product.Row(model))
        }

        adapter.list = list
    }

    private fun deleteAll() {
        val builder = AlertDialog.Builder(requireContext())
        val dialogBinding = DialogBulkDeleteBinding.inflate(layoutInflater)
        builder.setView(dialogBinding.root)
        val alertDialog = builder.create()

        dialogBinding.btnYes.setOnClickListener {
            list.forEach {
                ProductClient.service.deleteProductById(it)
                    .enqueue(object : Callback<ProductModel> {
                        override fun onResponse(
                            call: Call<ProductModel>,
                            response: Response<ProductModel>
                        ) {
                            if (response.isSuccessful) {
                                if (list.lastIndex == list.indexOf(it)) {
                                    val tempList = products.filter { product -> !list.contains(product.id) }

                                    generateProduct(tempList)

                                    alertDialog.dismiss()
                                }
                            }
                        }

                        override fun onFailure(call: Call<ProductModel>, t: Throwable) {
                            onError(t)
                        }
                    })
            }
        }

        dialogBinding.btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    private fun onError(t: Throwable) {
        t.printStackTrace()

        Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProductsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProductsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onSelect(id: Int, isSelect: Boolean) {
        val index = list.indexOf(id)

        if (index != -1) {
            if (!isSelect) {
                list.remove(id)
            }
        } else {
            if (isSelect) {
                list.add(id)
            }
        }
    }

    private fun toProductLayout(id: Int = 0) {
        findNavController().navigate(
            ProductsFragmentDirections.actionProductsFragment2ToProductFragment2(
                id
            )
        )
    }

    override fun requestDrag(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper.startDrag(viewHolder)
    }
}