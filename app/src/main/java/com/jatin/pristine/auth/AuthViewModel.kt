package com.jatin.pristine.auth

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jatin.pristine.activities.MainActivity
import com.jatin.pristine.data_classes.User

class AuthViewModel(private val state: SavedStateHandle) : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()


    var email: String
        get() = state["email"] ?: ""
        set(value) {
            state["email"] = value
        }


    var password: String
        get() = state["password"] ?: ""
        set(value) {
            state["password"] = value
        }


//    private val _user = MutableLiveData<Boolean>()
//    val user: LiveData<Boolean> get() = _user
//
//
//    private val _error = MutableLiveData<String>()
//    val error: LiveData<String> get() =  _error


    fun login(onResult: (Boolean, String) -> Unit) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    onResult(true, null.toString())
                } else {
                    onResult(false, task.exception?.message.toString())
                }

            }
    }

    fun createUser(onResult: (Boolean, String) -> Unit) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null.toString())
                    addUser(email)
                } else {
                    onResult(false, task.exception?.message.toString())
                }
            }

    }

    private fun addUser(email: String) {


        val userId = auth.currentUser!!.uid
        val user = User(userId,"",email,"","","","")

        db.collection("users")
            .document(userId)
            .set(user)
            .addOnCompleteListener {
                Log.d("User created successfully",userId)
            }
            .addOnFailureListener { exception->
                Log.e("Not able to create user",exception.toString())
            }


    }

}