package id.refactory.androidmaterial.day11.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import id.refactory.androidmaterial.databinding.FragmentTodoDetailBinding

class TodoDetailFragment : Fragment() {

    private lateinit var binding: FragmentTodoDetailBinding
    private val args by navArgs<TodoDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTodoDetailBinding.inflate(inflater, container, false).apply {
            tvTodo.text = args.todo
        }

        return binding.root
    }
}