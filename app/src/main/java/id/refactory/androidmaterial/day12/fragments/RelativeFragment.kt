package id.refactory.androidmaterial.day12.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.refactory.androidmaterial.R
import id.refactory.androidmaterial.databinding.FragmentRelativeBinding

class RelativeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentRelativeBinding.inflate(inflater, container, false).root
    }
}