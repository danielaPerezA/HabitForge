package com.example.habitforge.app.session

object AppSession {

    var username: String = ""
        private set

    var firstName: String = ""
        private set

    var lastName: String = ""
        private set

    var email: String = ""
        private set

    var isLoggedIn: Boolean = false
        private set

    fun login(
        username: String,
        firstName: String = "",
        lastName: String = "",
        email: String = ""
    ) {
        this.username = username.ifEmpty { firstName }
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.isLoggedIn = true
    }

    fun logout() {
        username = ""
        firstName = ""
        lastName = ""
        email = ""
        isLoggedIn = false
    }


    fun displayName(): String {
        return when {
            username.isNotEmpty()  -> username
            firstName.isNotEmpty() -> firstName
            else                   -> "Usuario"
        }
    }
}