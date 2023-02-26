package com.elvitalya.droiderhandbook.data.remote

import com.elvitalya.data.remote.api.FireBaseApi
import com.elvitalya.data.remote.source.FireBaseDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

//@OptIn(ExperimentalCoroutinesApi::class)
//class FireBaseDataSourceTest {
//
//    private lateinit var api: com.elvitalya.data.remote.api.FireBaseApi
//
//    private lateinit var dataSource: com.elvitalya.data.remote.source.FireBaseDataSource
//
//    @Before
//    fun prepare() {
//        api = FireBaseApiTestImpl()
//        dataSource = com.elvitalya.data.remote.source.FireBaseDataSource(api)
//    }
//
//    @Test
//    fun `get all returns list of questions`() = runTest {
//        val expectedList = FireBaseApiTestImpl.getFakeList()
//
//        val actualList = dataSource.getAllQuestions()
//
//        actualList.forEachIndexed { index, actual ->
//            assertEquals(expectedList[index], actual)
//        }
//
//    }
//
//    @Test(expected = Exception::class)
//    fun `empty login throws exception`() = runTest {
//        dataSource.login("", "world")
//    }
//
//    @Test(expected = Exception::class)
//    fun `empty pass throws exception`() = runTest {
//        dataSource.login("hello", "")
//    }
//
//    @Test(expected = Exception::class)
//    fun `short pass throws exception`() = runTest {
//        dataSource.login("", "world")
//    }
//
//    @Test(expected = Exception::class)
//    fun `invalid email throws exception`() = runTest {
//        dataSource.login("hello", "world")
//    }
//
//    @Test
//    fun `valid login not throws exception`() = runTest {
//        dataSource.login( "hello@mail.ru", "world123")
//    }
//
//
//
//}