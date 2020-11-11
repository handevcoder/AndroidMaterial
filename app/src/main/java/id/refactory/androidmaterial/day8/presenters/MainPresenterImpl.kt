package id.refactory.androidmaterial.day8.presenters

import id.refactory.androidmaterial.day8.models.UserModel
import id.refactory.androidmaterial.day8.views.MainView

class MainPresenterImpl(private val view: MainView) : MainPresenter {

    private val users by lazy { (1..1000).toList().map { UserModel(it, "User $it") } }

    override fun getAllUser() {
        view.onSuccessGetAllUser(users)
    }

    override fun filterUserByName(s: String) {
        view.onSuccessFilterUserByName(
            users.asSequence().filter { it.name.contains(s, false) }.toList()
        )
    }
}