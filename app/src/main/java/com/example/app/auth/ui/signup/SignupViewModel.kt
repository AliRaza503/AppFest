package com.example.app.auth.ui.signup

import android.app.ProgressDialog.show
import android.content.Context
import android.content.Intent
import android.util.Patterns
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.app.R
import com.google.firebase.auth.FirebaseAuth

class SignupViewModel : ViewModel() {
    private lateinit var auth: FirebaseAuth
    private val _signupForm = MutableLiveData<SignupFormState>()
    val signupFormState: MutableLiveData<SignupFormState> = _signupForm
    val success = MutableLiveData<Boolean>()
    fun signup(email: String, password: String, context: Context){
        auth = FirebaseAuth.getInstance()
        // If email is verified then login
        auth.createUserWithEmailAndPassword(email, password)
            .apply {
                addOnSuccessListener {
                    Toast.makeText(context, "Account created successfully", Toast.LENGTH_SHORT)
                        .show()
//                        if (auth.currentUser?.isEmailVerified == false) {
//                            auth.currentUser?.sendEmailVerification()?.apply {
//                                addOnSuccessListener {
//                                    addOnCompleteListener {
//                                        Toast.makeText(context, "Verification email sent", Toast.LENGTH_SHORT)
//                                            .show()
//                                    }
//                                    success = true
//                                }
//                                addOnCompleteListener {
//                                    Toast.makeText(context, "Verification email sent", Toast.LENGTH_SHORT)
//                                        .show()
//                                }
//                            }
//                        }
                    success.value = true
                }
                addOnFailureListener {
                    Toast.makeText(context, "Account creation failed", Toast.LENGTH_SHORT)
                        .show()
                    success.value = false
                }
            }
    }


// A placeholder email validation check
private fun isEmailValid(email: String): Boolean {
    return if (email.contains('@')) {
        Patterns.EMAIL_ADDRESS.matcher(email).matches()
    } else {
        email.isNotBlank()
    }
}

// A placeholder password validation check
private fun isPasswordValid(password: String): Boolean {
    return password.length > 5
}

//Is confirm password valid
private fun isConfPassValid(cPass: String, password: String): Boolean {
    return cPass == password
}

fun signupDataChanged(email: String, password: String, confPass: String) {
    if (!isEmailValid(email)) {
        _signupForm.value = SignupFormState(emailError = R.string.invalid_email)
    } else if (!isPasswordValid(password)) {
        _signupForm.value = SignupFormState(passwordError = R.string.invalid_password)
    } else if (!isConfPassValid(confPass, password)) {
        _signupForm.value = SignupFormState(confirmPasswordError = R.string.invalid_conf_pass)
    } else {
        _signupForm.value = SignupFormState(isDataValid = true)
    }
}
}