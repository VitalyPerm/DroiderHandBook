package com.elvitalya.droiderhandbook.data.remote

import com.elvitalya.droiderhandbook.data.remote.api.FireBaseApi
import com.elvitalya.droiderhandbook.data.remote.model.FirebaseQuestion
import com.elvitalya.droiderhandbook.data.remote.source.FireBaseDataSource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FireBaseDataSourceTest {

    private lateinit var fireBaseDataSource: FireBaseDataSource
    @RelaxedMockK private lateinit var api: FireBaseApi

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        fireBaseDataSource = FireBaseDataSource(api)
    }


    @Test
    fun `get all questions returns list of questions`() = runTest {
        // given
        coEvery {
            api.getAllQuestions()
        } returns listOf(FirebaseQuestion("0", "0", "0"))

        // when
        api.getAllQuestions()

        //then
        coVerify { fireBaseDataSource.getAllQuestions() }
    }

}