package id.refactory.androidmaterial.day8.presenters

import id.refactory.androidmaterial.day8.models.UserModel
import id.refactory.androidmaterial.day8.views.MainView

class MainPresenterImpl(private val view: MainView) : MainPresenter {
    override fun getAllUser() {
        val users = (1..100).toList().map { UserModel(it, "User $it") }
        view.onSuccessGetAllUser(users)
    }
}