package id.refactory.androidmaterial.day8.views

import id.refactory.androidmaterial.day8.models.UserModel

interface MainView {
    fun onSuccessGetAllUser(users: List<UserModel>)
    fun onSuccessFilterUserByName(users: List<UserModel>)
}