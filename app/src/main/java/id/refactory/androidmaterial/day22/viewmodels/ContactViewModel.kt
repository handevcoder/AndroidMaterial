package id.refactory.androidmaterial.day22.viewmodels

import android.app.Application
import androidx.lifecycle.*
import id.refactory.androidmaterial.day22.clients.ContactClient
import id.refactory.androidmaterial.day22.models.ContactModel
import id.refactory.androidmaterial.day22.utils.SessionUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

sealed class ContactState {
    data class Loading(val message: String = "Loading...") : ContactState()
    data class Error(val exception: Exception) : ContactState()
    data class Create(val data: ContactModel) : ContactState()
}

class ContactViewModel(private val app: Application) : AndroidViewModel(app) {
    private val contactService by lazy { ContactClient.service }
    private val mutableState by lazy { MutableLiveData<ContactState>() }
    private val token by lazy { SessionUtil(app.applicationContext).token }
    val state: LiveData<ContactState> get() = mutableState

    fun insertContact(phone: String, name: String, image: String) {
        val body = hashMapOf<String, RequestBody>()
        body["phone"] = phone.toRequestBody(MultipartBody.FORM)
        body["name"] = name.toRequestBody(MultipartBody.FORM)
        body["image"] = File(image).asRequestBody(MultipartBody.FORM)

        mutableState.value = ContactState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val contactModel = contactService.insertContact(token, body).data
                mutableState.postValue(ContactState.Create(contactModel))
            } catch (exc: Exception) {
                exc.printStackTrace()
                mutableState.postValue(ContactState.Error(exc))
            }
        }
    }
}