package com.elvitalya.droiderhandbook.data.local

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class QuestionsDataSourceTest {

    private lateinit var dao: com.elvitalya.data.local.dao.QuestionsDao
    private lateinit var dataSource: com.elvitalya.data.local.source.LocalDataSource

    @Before
    fun prepare() {
        dao = QuestionsDaoTestImpl()
        dataSource = com.elvitalya.data.local.source.LocalDataSource(dao)
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
        val testList = mutableListOf<com.elvitalya.data.local.entity.QuestionEntity>()

        repeat(3) {
            testList.add(
                com.elvitalya.data.local.entity.QuestionEntity(
                    it.toString(),
                    it.toString(),
                    it.toString()
                )
            )
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

        val testList = mutableListOf<com.elvitalya.data.local.entity.QuestionEntity>()

        repeat(3) {
            testList.add(
                com.elvitalya.data.local.entity.QuestionEntity(
                    it.toString(),
                    it.toString(),
                    it.toString()
                )
            )
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
        val exceptedQuestion =
            com.elvitalya.data.local.entity.QuestionEntity("2", title = "Hello", "World")

        val expectedQuestionBeforeUpdate = dataSource.getQuestionById(questionIdThatWillBeUpdated)
        assertNotEquals(exceptedQuestion, expectedQuestionBeforeUpdate)

        dataSource.updateQuestion(exceptedQuestion)

        val expectedQuestionAfterUpdate = dataSource.getQuestionById(questionIdThatWillBeUpdated)
        assertEquals(exceptedQuestion, expectedQuestionAfterUpdate)

    }

    private fun prepareExpectedList(): MutableList<com.elvitalya.data.local.entity.QuestionEntity> {
        val list = mutableListOf<com.elvitalya.data.local.entity.QuestionEntity>()

        repeat(3) {
            list.add(
                com.elvitalya.data.local.entity.QuestionEntity(
                    it.toString(),
                    it.toString(),
                    it.toString()
                )
            )
        }

        return list
    }

}