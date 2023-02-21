package com.elvitalya.droiderhandbook.data.remote

import android.os.Parcel
import com.google.firebase.auth.AdditionalUserInfo
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

class FireBaseAuthResultTestImpl(
    private val email: String,
    private val pass: String
) : AuthResult {

    private val existUserList = listOf(
        "hello@mail.ru" to "world123",
        "gato" to "puma",
        "android" to "top",
    )


    private val emailNotContainsDogChar get() = email.contains('@').not()
    private val emailIsEmpty get() = email.isBlank()
    private val passIsEmpty get() = pass.isBlank()
    private val passLengthNotEnough get() = pass.length < 6
    private val userNotExists = existUserList.contains(email to pass).not()

    override fun describeContents(): Int = 1

    override fun writeToParcel(p0: Parcel, p1: Int) = Unit

    override fun getAdditionalUserInfo(): AdditionalUserInfo? = null

    override fun getCredential(): AuthCredential? = null

    override fun getUser(): FirebaseUser? = null

    init {
        if (emailNotContainsDogChar) throw Exception()
        if (emailIsEmpty) throw Exception()
        if (passIsEmpty) throw Exception()
        if (passLengthNotEnough) throw Exception()
        if (userNotExists) throw Exception()
    }
}