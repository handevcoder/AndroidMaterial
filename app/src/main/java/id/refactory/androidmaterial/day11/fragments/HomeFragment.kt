package id.refactory.androidmaterial.day11.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import id.refactory.androidmaterial.R
import id.refactory.androidmaterial.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            btnSend.setOnClickListener {
                if (tieData.text.isNullOrEmpty()) {
                    Toast.makeText(requireActivity(), "Data tidak boleh kosong", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    findNavController().navigate(
                        HomeFragmentDirections.actionHomeFragmentToDetailFragment(tieData.text.toString())
                    )
                }
            }
        }

        return binding.root
    }
}