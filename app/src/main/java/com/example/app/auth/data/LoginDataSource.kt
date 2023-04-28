package com.example.app.auth.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.example.app.auth.data.model.LoggedInUser
import com.example.app.auth.data.model.User
import java.util.UUID

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {
    companion object{
        val success = MutableLiveData<Boolean>()
    }
    private val firebaseAuth = FirebaseAuth.getInstance()

    fun login(context: Context, email: String, password: String){
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("SingInEmail", "signInWithEmail:success")
                    success.value = true
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("SingInEmail", "signInWithEmail:failure", task.exception)
                    Toast.makeText(context, "Error logging in", Toast.LENGTH_SHORT).show()
                    success.value = false
                }
            }
    }

    fun logout() {
        firebaseAuth.signOut()
    }
}