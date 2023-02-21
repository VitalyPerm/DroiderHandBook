package com.elvitalya.droiderhandbook.data.local

import com.elvitalya.droiderhandbook.data.local.dao.QuestionsDao
import com.elvitalya.droiderhandbook.data.local.entity.QuestionEntity
import com.elvitalya.droiderhandbook.data.local.source.QuestionsDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class QuestionsDataSourceTest {

    private lateinit var dao: QuestionsDao
    private lateinit var dataSource: QuestionsDataSource

    @Before
    fun prepare() {
        dao = QuestionsDaoTestImpl()
        dataSource = QuestionsDataSource(dao)
    }


    @Test
    fun getAll() = runTest {
        val beforeAddReturnEmpty = dataSource.getAll().first().isEmpty()
        assertEquals(beforeAddReturnEmpty, true)

        val list = prepareExpectedList()
        dataSource.addQuestionList(list)

        val afterAddReturnsNotEmptyList = dataSource.getAll().first().isNotEmpty()
        assertEquals(afterAddReturnsNotEmptyList, true)
    }


    @Test
    fun `delete all clears list`() = runTest {
        val testList = mutableListOf<QuestionEntity>()

        repeat(3) {
            testList.add(QuestionEntity(it.toString(), it.toString(), it.toString()))
        }

        dataSource.addQuestionList(testList)
        val getAllIsNotEmpty = dataSource.getAll().first().isNotEmpty()
        assertEquals(getAllIsNotEmpty, true)

        dataSource.deleteAll()
        val getAllIsEmpty = dataSource.getAll().first().isEmpty()
        assertEquals(getAllIsEmpty, true)

    }

    @Test
    fun `get question by id`() = runTest {

        val testList = mutableListOf<QuestionEntity>()

        repeat(3) {
            testList.add(QuestionEntity(it.toString(), it.toString(), it.toString()))
        }

        dataSource.addQuestionList(testList)

        val expectedId = "2"
        val actualId = dataSource.getQuestionById("2").id

        assertEquals(actualId, expectedId)
    }

    @Test
    fun `add questions list`() = runTest {
        val expectedList = prepareExpectedList()

        dataSource.addQuestionList(expectedList)

        val actualList = dataSource.getAll().first()

        actualList.forEachIndexed { index, actual ->
            assertEquals(expectedList[index], actual)
        }
    }

    @Test
    fun `update question`() = runTest {
        val expectedList = prepareExpectedList()
        dataSource.addQuestionList(expectedList)
        val questionIdThatWillBeUpdated = "2"
        val exceptedQuestion = QuestionEntity("2", title = "Hello", "World")

        val expectedQuestionBeforeUpdate = dataSource.getQuestionById(questionIdThatWillBeUpdated)
        assertNotEquals(exceptedQuestion, expectedQuestionBeforeUpdate)

        dataSource.updateQuestion(exceptedQuestion)

        val expectedQuestionAfterUpdate = dataSource.getQuestionById(questionIdThatWillBeUpdated)
        assertEquals(exceptedQuestion, expectedQuestionAfterUpdate)

    }

    private fun prepareExpectedList(): MutableList<QuestionEntity> {
        val list = mutableListOf<QuestionEntity>()

        repeat(3) {
            list.add(QuestionEntity(it.toString(), it.toString(), it.toString()))
        }

        return list
    }

}