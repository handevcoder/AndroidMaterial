package id.refactory.androidmaterial.day21.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import id.refactory.androidmaterial.R
import id.refactory.androidmaterial.databinding.FragmentFavoritesBinding
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
import id.refactory.androidmaterial.day21.views.adapter.FavoriteAdapter
import id.refactory.androidmaterial.day21.views.adapter.TodoAdapter
import id.refactory.androidmaterial.day21.views.states.TodoState

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavoritesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoritesFragment : Fragment(), FavoriteAdapter.FavoriteListener {
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

    private lateinit var binding: FragmentFavoritesBinding

    private val adapter by lazy { FavoriteAdapter(requireActivity(), this) }
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
        binding = FragmentFavoritesBinding.inflate(inflater, container, false).apply {
            rvFavorite.adapter = adapter

            viewModel.state.observe(viewLifecycleOwner) {
                when (it) {
                    is TodoState.SuccessGetAllFavorite -> {
                        adapter.list = it.list.toMutableList()
                    }
                    is TodoState.SuccessInsertFavorite -> {
                        adapter.insertTodo(it.todo)
                    }
                    is TodoState.SuccessDeleteFavorite -> {
                        adapter.deleteTodo(it.todo)
                    }
                    else -> throw Exception("Unsupported state type")
                }
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        viewModel.getAllFavorite()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FavoritesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavoritesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(todoModel: TodoModel) {
        viewModel.submitFavorite(todoModel)
    }
}