package com.example.bookbuzz.screens.login

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookbuzz.model.MUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class LoginScreenViewModel @Inject constructor() :ViewModel() {
    val loadingState= MutableStateFlow(LoadingState.IDLE)
    private val auth:FirebaseAuth=Firebase.auth
    private val _loading=MutableLiveData(false)
    val loading:LiveData<Boolean> =_loading

    fun signInWithEmailAndPassword(email: String, password: String, home: () -> Unit,error:(String)->Unit)
            = viewModelScope.launch{
        _loading.value=true
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        _loading.value=false
                        Log.d("FireBSe", "signInWithEmailAndPassword: Yayayay! ${task.result}")
                        //Todo: take them home
                        home()
                    }
                }
                .addOnFailureListener{
                    _loading.value=false
                    Log.d("FireBSe", "signInWithEmailAndPassword: $it")
                    it.localizedMessage?.let { it1 -> error(it1) }
                }

        }catch (ex: Exception){
            _loading.value=false
            ex.localizedMessage?.let { error(it) }
            Log.d("FireBSe", "signInWithEmailAndPassword: ${ex.message}")
        }


    }

    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        home: () -> Unit,
    error: (String) -> Unit) {
        if (_loading.value == false) {
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        //me
                        val displayName = task.result?.user?.email?.split('@')?.get(0)
                        createUser(displayName)
                        home()
                    }else {
                        error(task.result)

                    }
                    _loading.value = false


                }
                .addOnFailureListener{
                    _loading.value=false
                    it.localizedMessage?.let { it1 -> error(it1) }
                }
        }


    }

    private fun createUser(displayName: String?) {
        val userId = auth.currentUser?.uid

        val user = MUser(userId = userId.toString(),
            displayName = displayName.toString(),
            avatarUrl = "",
            quote = "Life is great",
            profession = "Android Developer",
            id = null).toMap()


        FirebaseFirestore.getInstance().collection("users")
            .add(user)

    }



}