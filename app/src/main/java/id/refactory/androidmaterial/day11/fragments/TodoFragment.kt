package id.refactory.androidmaterial.day11.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import id.refactory.androidmaterial.databinding.FragmentTodoBinding
import id.refactory.androidmaterial.day11.adapters.TodoAdapter
import id.refactory.androidmaterial.day11.adapters.TodoListener
import id.refactory.androidmaterial.day11.viewmodels.State
import id.refactory.androidmaterial.day11.viewmodels.TodoViewModel

class TodoFragment : Fragment(), TodoListener {

    private lateinit var binding: FragmentTodoBinding
    private val adapter by lazy { TodoAdapter(requireActivity(), this) }
    private val viewModel by viewModels<TodoViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodoBinding.inflate(inflater, container, false).apply {
            rvTodo.adapter = adapter
            btnAdd.setOnClickListener {
                if (tieTodo.text.toString().isEmpty()) {
                    Toast.makeText(
                        requireActivity(),
                        "Todo tidak boleh kosong!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    viewModel.getTodo()

                    tieTodo.setText("")
                }
            }
        }

        viewModel.getTodo()
        viewModel.todoSate.observe(viewLifecycleOwner, {
            when (it) {
                is State.Loading -> {
                    showLoading(true)
                }
                is State.Success -> {
                    showLoading(false)

                    adapter.setData(it.data)
                }
                is State.Error -> {
                    showLoading(false)

                    Toast.makeText(
                        requireActivity(),
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

        return binding.root
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbLoading.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
        binding.rvTodo.visibility = if (!isLoading) View.VISIBLE else View.INVISIBLE
    }

    override fun onClick(todo: String) {
        findNavController().navigate(
            TodoFragmentDirections.actionTodoFragmentToTodoDetailFragment(todo)
        )
    }

    override fun onDelete(todo: String) {
        adapter.deleteData(todo)
    }
}