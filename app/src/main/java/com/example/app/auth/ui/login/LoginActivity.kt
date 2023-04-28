package com.example.app.auth.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.app.MainActivity
import com.example.app.R
import com.example.app.auth.data.LoginDataSource
import com.example.app.auth.ui.ViewModelFactory
import com.example.app.auth.ui.signup.SignupActivity
import com.example.app.databinding.ActivityLoginBinding
import com.example.app.extras.afterTextChanged
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {
    companion object {
        const val INTENT_PASS = "EXTRA_ID"
    }

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Click listener to go for signup
        binding.signupBtn.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }
        val email = binding.email
        val password = binding.password
        val login = binding.signinBtn
        val loading = binding.loading

        loginViewModel =
            ViewModelProvider(this, ViewModelFactory())[LoginViewModel::class.java]

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both email / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.emailError != null) {
                email.error = getString(loginState.emailError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        email.afterTextChanged {
            loginViewModel.loginDataChanged(
                email.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    email.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                        loginViewModel.login(
                            context,
                            email.text.toString(),
                            password.text.toString()
                        )
                    if (LoginDataSource.success.value == false) {
                        restartActivity()
                    }
                    else {
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.putExtra(INTENT_PASS, FirebaseAuth.getInstance().currentUser)
                        startActivity(intent)
                        finish()
                    }
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(context, email.text.toString(), password.text.toString())
                if (LoginDataSource.success.value == false) {
                    restartActivity()
                }
                else {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.putExtra(INTENT_PASS, FirebaseAuth.getInstance().currentUser)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun restartActivity() {
        binding.email.text = null
        binding.password.text = null
        binding.loading.visibility = View.GONE
        binding.signinBtn.isEnabled = false
    }

    /**
     * If user has already loggedIn
     */
    override fun onStart() {
        super.onStart()
        val currentUser = FirebaseAuth.getInstance().currentUser
        //email address is not verified
        if (currentUser?.isEmailVerified == false) {
            Toast.makeText(
                baseContext, "Please verify your email address.",
                Toast.LENGTH_SHORT
            ).show()
            currentUser.sendEmailVerification()
        } else {
            if (currentUser != null) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(INTENT_PASS, currentUser)
                startActivity(intent)
                finish()
            }
            else {
                showLoginFailed(R.string.login_failed)
            }
        }
    }


    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

}

