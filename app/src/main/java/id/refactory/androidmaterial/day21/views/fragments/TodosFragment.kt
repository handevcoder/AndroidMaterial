package id.refactory.androidmaterial.day21.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.refactory.androidmaterial.databinding.FragmentTodosBinding
import id.refactory.androidmaterial.day21.models.TodoModel
import id.refactory.androidmaterial.day21.repositories.TodoLocalRepository
import id.refactory.androidmaterial.day21.repositories.TodoRemoteRepository
import id.refactory.androidmaterial.day21.repositories.local.TodoLocalRepositoryImpl
import id.refactory.androidmaterial.day21.repositories.local.daos.TodoDao
import id.refactory.androidmaterial.day21.repositories.local.databases.LocalDatabase
import id.refactory.androidmaterial.day21.repositories.remote.TodoRemoteRepositoryImpl
import id.refactory.androidmaterial.day21.repositories.remote.clients.TodoClient
import id.refactory.androidmaterial.day21.repositories.remote.services.TodoService
import id.refactory.androidmaterial.day21.viewmodels.TodoViewModel
import id.refactory.androidmaterial.day21.viewmodels.TodoViewModelFactory
import id.refactory.androidmaterial.day21.views.adapter.TodoAdapter
import id.refactory.androidmaterial.day21.views.states.TodoState
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TodosFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@Suppress("UNCHECKED_CAST")
class TodosFragment : Fragment(), TodoAdapter.TodoListener {
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

    private lateinit var binding: FragmentTodosBinding

    private val adapter by lazy { TodoAdapter(requireActivity(), this) }
    private val service: TodoService by lazy { TodoClient.service }
    private val dao: TodoDao by lazy { LocalDatabase.getDatabase(requireContext()).dao() }
    private val localRepository: TodoLocalRepository by lazy { TodoLocalRepositoryImpl(dao) }
    private val remoteRepository: TodoRemoteRepository by lazy { TodoRemoteRepositoryImpl(service) }
    private val viewModelFactory by lazy { TodoViewModelFactory(remoteRepository, localRepository) }
    private val viewModel by viewModels<TodoViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodosBinding.inflate(inflater, container, false).apply {
            rvTodo.adapter = adapter

            viewModel.state.observe(viewLifecycleOwner) {
                when (it) {
                    is TodoState.Loading -> showLoading(true)
                    is TodoState.Error -> {
                        showLoading(false)
                        showMessage(it.exception.message ?: "Oops something went wrong")
                    }
                    is TodoState.SuccessGetAllTodo -> {
                        showLoading(false)
                        adapter.list = it.list.toMutableList()
                    }
                    is TodoState.SuccessInsertTodo -> {
                        showLoading(false)
                        adapter.insertTodo(it.todo)
                        tieTodo.setText("")
                    }
                    is TodoState.SuccessUpdateTodo -> {
                        showLoading(false)
                        adapter.updateTodo(it.todo)
                    }
                    is TodoState.SuccessDeleteTodo -> {
                        showLoading(false)
                        adapter.deleteTodo(it.todo)
                    }
                    is TodoState.SuccessInsertFavorite -> {
                        showMessage("Todo dengan id ${it.todo.id} berhasil ditambahkan di favorite")
                    }
                    is TodoState.SuccessDeleteFavorite -> {
                        showMessage("Todo dengan id ${it.todo.id} berhasil dihapus dari favorite")
                    }
                    else -> throw Exception("Unsupported state type")
                }
            }

            btnSubmit.setOnClickListener {
                if (tieTodo.text.toString().isNotEmpty()) {
                    viewModel.insertTodo(
                        TodoModel(
                            task = tieTodo.text.toString(),
                            date = Calendar.getInstance().time.toString()
                        )
                    )
                } else {
                    showMessage("Todo tidak boleh kosong")
                }
            }
        }

        return binding.root
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbLoading.visibility = if (isLoading) View.VISIBLE else View.GONE

        binding.rvTodo.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.tilTodo.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.btnSubmit.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    override fun onResume() {
        super.onResume()

        viewModel.getAllTodo()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TodosFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TodosFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onChange(todoModel: TodoModel) {
        todoModel.status = !todoModel.status
        viewModel.updateTodo(todoModel)
    }

    override fun onDelete(todoModel: TodoModel) {
        viewModel.deleteTodo(todoModel)
    }

    override fun onFavorite(todoModel: TodoModel) {
        viewModel.submitFavorite(todoModel)
    }
}