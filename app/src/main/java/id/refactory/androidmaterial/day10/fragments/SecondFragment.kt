package id.refactory.androidmaterial.day10.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.refactory.androidmaterial.R
import id.refactory.androidmaterial.databinding.FragmentFirstBinding

class SecondFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater, container, false).apply {
            tvName.text = "Fragment Two"
        }

        return binding.root
    }
}