package com.elvitalya.droiderhandbook.data.remote

import android.os.Parcel
import com.elvitalya.droiderhandbook.data.remote.api.FireBaseApi
import com.elvitalya.droiderhandbook.data.remote.model.FirebaseQuestion
import com.elvitalya.droiderhandbook.data.remote.source.FireBaseDataSource
import com.google.firebase.auth.AdditionalUserInfo
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FireBaseDataSourceTest {

    private lateinit var api: FireBaseApi

    private lateinit var dataSource: FireBaseDataSource

    @Before
    fun prepare() {
        api = FireBaseTestApi()
        dataSource = FireBaseDataSource(api)
    }

    @Test
    fun getAll() = runTest {

        val expectedList = prepareTestList()

        val actualList = dataSource.getAllQuestions()

        actualList.forEachIndexed { index, actual ->
            assertEquals(expectedList[index], actual)
        }

    }

    @Test
    fun login() = runTest {
        val expected = authResultTestImpl
        val actual = dataSource.login("", "")
        assertEquals(actual, expected)
    }

    @Test
    fun registration() = runTest {
        val expected = authResultTestImpl
        val actual = dataSource.registration("", "")
        assertEquals(actual, expected)
    }

    private inner class FireBaseTestApi : FireBaseApi {
        override suspend fun getAllQuestions(): List<FirebaseQuestion> = prepareTestList()

        override suspend fun login(email: String, pass: String): AuthResult = authResultTestImpl

        override suspend fun registration(email: String, pass: String): AuthResult =
            authResultTestImpl
    }

    private fun prepareTestList(): List<FirebaseQuestion> {
        val list = mutableListOf<FirebaseQuestion>()
        repeat(3) {
            list.add(FirebaseQuestion(it.toString(), it.toString(), it.toString()))
        }
        return list
    }

    private val authResultTestImpl = object : AuthResult {
        override fun describeContents(): Int = 1

        override fun writeToParcel(p0: Parcel, p1: Int) = Unit

        override fun getAdditionalUserInfo(): AdditionalUserInfo? = null

        override fun getCredential(): AuthCredential? = null

        override fun getUser(): FirebaseUser? = null
    }

}