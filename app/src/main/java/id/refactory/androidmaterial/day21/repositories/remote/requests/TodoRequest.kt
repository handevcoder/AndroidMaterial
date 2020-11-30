package id.refactory.androidmaterial.day21.repositories.remote.requests

data class TodoRequest(val status: Boolean = false, val task: String = "", val date: String = "")
