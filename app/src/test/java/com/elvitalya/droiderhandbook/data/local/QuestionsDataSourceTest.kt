package com.elvitalya.droiderhandbook.data.local

import com.elvitalya.droiderhandbook.data.local.dao.QuestionsDao
import com.elvitalya.droiderhandbook.data.local.entity.QuestionEntity
import com.elvitalya.droiderhandbook.data.local.source.QuestionsDataSource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class QuestionsDataSourceTest {


    @RelaxedMockK
    private lateinit var dao: QuestionsDao

    private lateinit var source: QuestionsDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        source = QuestionsDataSource(dao)
    }

    @Test
    fun `getItems calls dao`() {
        // Given
        coEvery { dao.getAll() } returns flowOf(emptyList())

        // When
        source.getAll()

        // Then
        verify { dao.getAll() }
    }


    @Test
    fun `add questions list calls dao`() = runTest {
        // given
        coEvery { dao.addQuestionsList(any()) } returns Unit

        // when
        source.addQuestionList(listOf())

        // then
        coVerify { dao.addQuestionsList(listOf()) }
    }

    @Test
    fun `get question calls dao`() = runTest {
        // given
        coEvery { dao.getQuestion("1") } returns QuestionEntity("1", "test", "test")

        // when
        source.getQuestionById("1")

        // then
        coVerify { dao.getQuestion("1") }
    }

    @Test
    fun `update question calls dao`() = runTest {
        // given
        coEvery { dao.updateQuestion(QuestionEntity("1", "test", "test")) } returns Unit

        //when
        dao.updateQuestion(QuestionEntity("1", "test", "test"))

        //then
        coVerify { dao.updateQuestion(QuestionEntity("1", "test", "test")) }
    }

    @Test
    fun `delete all calls dao`() = runTest {
        // given
        coEvery { dao.deleteAll() } returns Unit

        //when
        dao.deleteAll()

        //then
        coVerify { dao.deleteAll() }
    }
}