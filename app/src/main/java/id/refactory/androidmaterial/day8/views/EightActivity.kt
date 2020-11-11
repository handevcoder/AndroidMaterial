package id.refactory.androidmaterial.day8.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import id.refactory.androidmaterial.databinding.ActivityEightBinding
import id.refactory.androidmaterial.day8.adapters.UserAdapter
import id.refactory.androidmaterial.day8.models.UserModel
import id.refactory.androidmaterial.day8.presenters.MainPresenterImpl

class EightActivity : AppCompatActivity(), MainView {

    private val binding by lazy { ActivityEightBinding.inflate(layoutInflater) }
    private val presenter by lazy { MainPresenterImpl(this) }
    private val adapter by lazy { UserAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rvUser.adapter = adapter
        binding.etFilter.addTextChangedListener { text -> presenter.filterUserByName(text.toString()) }

        presenter.getAllUser()
    }

    override fun onSuccessGetAllUser(users: List<UserModel>) {
        adapter.setData(users)
    }

    override fun onSuccessFilterUserByName(users: List<UserModel>) {
        adapter.setData(users)
    }
}