package id.refactory.androidmaterial.day14.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import id.refactory.androidmaterial.databinding.FragmentProductBinding
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
 * Use the [ProductFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductFragment : Fragment() {
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

    private lateinit var binding: FragmentProductBinding
    private val args by navArgs<ProductFragmentArgs>()
    private var product: ProductModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductBinding.inflate(inflater, container, false).apply {
            if (args.id != 0) {
                btnSubmit.text = "UBAH PRODUK"

                ProductClient.service.getProductById(args.id).enqueue(object :
                    Callback<ProductModel> {
                    override fun onResponse(
                        call: Call<ProductModel>,
                        response: Response<ProductModel>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.let {
                                product = it

                                tieProduct.setText(it.title)
                                tieCategory.setText(it.category)
                                tiePrice.setText(it.price.toString())
                                tieDescription.setText(it.description)

                                if (it.image.isNotEmpty()) {
                                    Glide.with(requireActivity()).load(it.image).into(ivProduct)
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<ProductModel>, t: Throwable) {
                        onError(t)
                    }
                })

                btnSubmit.setOnClickListener {
                    product?.let {
                        updateProduct(
                            it.copy(
                                category = tieCategory.text.toString(),
                                price = tiePrice.text.toString().toDoubleOrNull() ?: 0.0,
                                title = tieProduct.text.toString(),
                                description = tieDescription.text.toString()
                            )
                        )
                    }
                }
            } else {
                btnSubmit.setOnClickListener {
                    val productModel = ProductModel(
                        id = 0,
                        image = "",
                        price = tiePrice.text.toString().toDoubleOrNull() ?: 0.0,
                        title = tieProduct.text.toString(),
                        category = tieProduct.text.toString(),
                        description = tieDescription.text.toString()
                    )

                    insertProduct(productModel)
                }
            }
        }

        return binding.root
    }

    private fun insertProduct(productModel: ProductModel) {
        ProductClient.service.insertProduct(productModel).enqueue(object : Callback<ProductModel> {
            override fun onResponse(call: Call<ProductModel>, response: Response<ProductModel>) {
                if (response.isSuccessful) Toast.makeText(
                    requireActivity(),
                    "Produk berhasil ditambahkan",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onFailure(call: Call<ProductModel>, t: Throwable) {
                onError(t)
            }
        })
    }

    private fun onError(t: Throwable) {
        t.printStackTrace()

        Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
    }

    private fun updateProduct(productModel: ProductModel) {
        ProductClient.service.updateProductById(productModel.id, productModel).enqueue(object :
            Callback<ProductModel> {
            override fun onResponse(call: Call<ProductModel>, response: Response<ProductModel>) {
                if (response.isSuccessful) Toast.makeText(
                    requireActivity(),
                    "Produk berhasil diubah",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onFailure(call: Call<ProductModel>, t: Throwable) {
                onError(t)
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProductFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProductFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}