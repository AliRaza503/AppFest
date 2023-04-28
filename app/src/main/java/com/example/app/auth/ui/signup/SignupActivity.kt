package com.example.app.auth.ui.signup

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.app.MainActivity
import com.example.app.R
import com.example.app.auth.ui.ViewModelFactory
import com.example.app.auth.ui.login.LoginActivity
import com.example.app.databinding.ActivitySignupBinding
import com.example.app.extras.afterTextChanged
import com.google.firebase.auth.FirebaseAuth


class SignupActivity : AppCompatActivity() {
    companion object {
        const val INTENT_PASS = "EXTRA_ID"
    }

    private lateinit var signupViewModel: SignupViewModel
    private lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Click listener to go for signin
        binding.signinBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        val email = binding.email
        val password = binding.password
        val signup = binding.signupBtn
        val confPass = binding.confPassword
        val loading = binding.loading

        signupViewModel =
            ViewModelProvider(this, ViewModelFactory())[SignupViewModel::class.java]

        signupViewModel.signupFormState.observe(this@SignupActivity, Observer {
            val signupState = it ?: return@Observer

            // disable login button unless both email / password is valid
            signup.isEnabled = signupState.isDataValid

            if (signupState.emailError != null) {
                email.error = getString(signupState.emailError)
            } else if (signupState.passwordError != null) {
                password.error = getString(signupState.passwordError)
            } else if (signupState.confirmPasswordError != null) {
                confPass.error = getString(signupState.confirmPasswordError)
            }
        })


        email.afterTextChanged {
            signupViewModel.signupDataChanged(
                email.text.toString(),
                password.text.toString(),
                confPass.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                signupViewModel.signupDataChanged(
                    email.text.toString(),
                    password.text.toString(),
                    confPass.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        signup(
                            email.text.toString(),
                            password.text.toString(),
                            this@SignupActivity
                        )
                }
                false
            }

            signup.setOnClickListener {
                loading.visibility = View.VISIBLE
                signup(
                    email.text.toString(),
                    password.text.toString(),
                    this@SignupActivity
                )
            }
        }

        confPass.apply {
            afterTextChanged {
                signupViewModel.signupDataChanged(
                    email.text.toString(),
                    password.text.toString(),
                    confPass.text.toString()
                )
            }
            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        signup(
                            email.text.toString(),
                            password.text.toString(),
                            this@SignupActivity
                        )
                }
                false
            }

            signup.setOnClickListener {
                loading.visibility = View.VISIBLE
                signup(
                    email.text.toString(),
                    password.text.toString(),
                    this@SignupActivity
                )
            }
        }
    }

    private fun signup(email: String, password: String, context: Context) {
        //Send verification email
        signupViewModel.signup(email, password, context)
        signupViewModel.success.observe(this, Observer {
            if (it == true) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(INTENT_PASS, FirebaseAuth.getInstance().currentUser)
                startActivity(intent)
                finish()
            }
            else {
                restartActivity()
                showOnSignupFailed(R.string.signup_failed)
            }
        })
    }

    private fun restartActivity() {
        binding.email.text = null
        binding.password.text = null
        binding.loading.visibility = View.GONE
        binding.signupBtn.isEnabled = false
        binding.confPassword.text = null
    }

    private fun showOnSignupFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}