package com.elvitalya.droiderhandbook.data.local

import com.elvitalya.droiderhandbook.data.local.dao.QuestionsDao
import com.elvitalya.droiderhandbook.data.local.entity.QuestionEntity
import com.elvitalya.droiderhandbook.data.local.source.QuestionsDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
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
        dao = QuestionsTestDao()
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
        val dao = QuestionsTestDao()
        val dataSource = QuestionsDataSource(dao)

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


private class QuestionsTestDao : QuestionsDao {

    val data = mutableListOf<QuestionEntity>()

    override fun getAll(): Flow<List<QuestionEntity>> = flowOf(data)

    override suspend fun getQuestion(id: String): QuestionEntity =
        data.find { it.id == id } ?: QuestionEntity("", "", "")

    override suspend fun addQuestionsList(questions: List<QuestionEntity>) {
        data.addAll(questions)
    }

    override suspend fun deleteAll() {
        data.clear()
    }

    override suspend fun updateQuestion(question: QuestionEntity) {
        val oldIndex = data.indexOfFirst { it.id == question.id }
        if (oldIndex != -1) {
            data.removeAt(oldIndex)
            data.add(oldIndex, question)
        }
    }

}