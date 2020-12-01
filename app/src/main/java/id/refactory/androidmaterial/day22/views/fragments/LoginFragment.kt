package id.refactory.androidmaterial.day22.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import id.refactory.androidmaterial.R
import id.refactory.androidmaterial.databinding.FragmentLoginBinding
import id.refactory.androidmaterial.day22.utils.SessionUtil
import id.refactory.androidmaterial.day22.viewmodels.AuthState
import id.refactory.androidmaterial.day22.viewmodels.AuthViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
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

    private lateinit var binding: FragmentLoginBinding
    private val session by lazy { SessionUtil(requireContext()) }
    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false).apply {
            if (session.token.isNotEmpty()) {
                findNavController().navigate(R.id.action_loginFragment_to_contactFragment)
            }

            btnLogin.setOnClickListener {
                if (tieEmail.text.toString().isNotEmpty() &&
                    tiePassword.text.toString().isNotEmpty()
                ) {
                    authViewModel.login(tieEmail.text.toString(), tiePassword.text.toString())
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Email dan password tidak boleh kosong",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            authViewModel.state.observe(viewLifecycleOwner) {
                when (it) {
                    is AuthState.Login -> {
                        btnLogin.visibility = View.VISIBLE

                        session.token = it.data.token

                        findNavController().navigate(R.id.action_loginFragment_to_contactFragment)
                    }
                    is AuthState.Loading -> btnLogin.visibility = View.GONE
                    is AuthState.Error -> {
                        btnLogin.visibility = View.VISIBLE

                        Toast.makeText(requireContext(), it.exception.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}