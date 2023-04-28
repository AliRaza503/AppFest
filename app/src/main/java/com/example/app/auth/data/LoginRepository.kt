package com.example.app.auth.data

import android.content.Context
import com.example.app.auth.data.model.LoggedInUser

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
class LoginRepository(val dataSource: LoginDataSource) {

    private var isLoggedIn: Boolean = false
    fun logout() {
        LoggedInUser.user = null
        isLoggedIn = false
        dataSource.logout()
    }

    fun login(context: Context, username: String, password: String){
        // handle login
        dataSource.login(context, username, password)
    }
}