package com.example.starbucs_clone

import com.example.starbucs_clone.data.Menu
import com.example.starbucs_clone.data.PurchaseDetail
import com.example.starbucs_clone.data.User
import com.example.starbucs_clone.data.menusList
import com.example.starbucs_clone.data.purchaseDetailsList
import com.example.starbucs_clone.data.syrupList
import com.example.starbucs_clone.data.usersList

object UserSessionManager {
    var loggedInUserId: Int? = 1

    fun getLoggedInUser(): User? {
        return loggedInUserId?.let { id ->
            if (id in usersList.indices) usersList[id] else null
        }
    }
    fun login(email: String, password: String): Int {
        val index = usersList.indexOfFirst { it.email == email && it.password == password }
        loggedInUserId = index
        return index
    }
    fun isUniqueEmail(email: String): Boolean {
        val exists = usersList.any { it.email == email }
        if (exists) {
            return false
        }
        return true
    }
    fun register(
        email: String,
        password: String,
        namaDepan: String,
        namaBelakang: String,
        noTelp: String,
        dob: String
    ): Int {
        val newUser = User(
            email = email,
            password = password,
            nama_depan = namaDepan,
            nama_belakang = namaBelakang,
            noTelp = noTelp,
            DoB = dob
        )

        usersList.add(newUser)
        loggedInUserId = usersList.lastIndex
        return loggedInUserId!!
    }

    fun logout() {
        loggedInUserId = null
    }
    fun updateLoggedInUser(updatedUser: User) {
        val index = loggedInUserId!! - 1
        if (index in usersList.indices) {
            usersList[index] = updatedUser
        }
    }
}

object MenuSessionManager {
    fun getMenuDetail(menuId: Int): Menu {
        return menusList[menuId]
    }
}

object PurchaseSessionManager {
    var selectedLocationId: Int = 2
    fun setLocationId(locId: Int) {
        selectedLocationId = locId
    }
    fun getSyrupDetail(syrupId: Int): String {
        if (syrupId >= 0) return syrupList[syrupId]
        return ""
    }
    fun getItemCount(): Int {
        return purchaseDetailsList.size
    }
    fun addItem(item: PurchaseDetail) {
        purchaseDetailsList.add(item)
    }
    fun removeItem(item: PurchaseDetail) {
        purchaseDetailsList.remove(item)
    }
    fun getItems(): List<PurchaseDetail> {
        return purchaseDetailsList
    }
    fun checkOut(totalPrice: Int, method: String) {

    }
}