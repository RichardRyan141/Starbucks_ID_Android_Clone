package com.example.starbucs_clone

import com.example.starbucs_clone.data.User
import com.example.starbucs_clone.data.usersList

object SessionManager {
    var loggedInUserId: Int? = null

    fun getLoggedInUser(): User? {
        return loggedInUserId?.let { id ->
            if (id in usersList.indices) usersList[id] else null
        }
    }
    fun logout() {
        loggedInUserId = null
    }
}
