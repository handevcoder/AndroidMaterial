package id.refactory.androidmaterial.day10.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.refactory.androidmaterial.databinding.FragmentFirstBinding

class ThirdFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater, container, false).apply {
            tvName.text = "Fragment Three"
        }

        return binding.root
    }
}