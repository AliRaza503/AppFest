package com.example.app.auth.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import java.io.IOException
import com.example.app.auth.data.model.LoggedInUser
import com.example.app.auth.data.model.User
import java.util.UUID

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {
    private val firebaseAuth = FirebaseAuth.getInstance()
    fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("SingInEmail", "signInWithEmail:success")
                    LoggedInUser.user = User(
                        UUID.fromString(firebaseAuth.currentUser?.uid.toString()),
                        firebaseAuth.currentUser?.displayName.toString(),
                        firebaseAuth.currentUser?.email.toString()
                    )
                } else {
                    // If sign in fails, display a message to the user.
                    throw IOException("Error logging in", task.exception)
                }
            }
    }

    fun logout() {
        firebaseAuth.signOut()
    }
}