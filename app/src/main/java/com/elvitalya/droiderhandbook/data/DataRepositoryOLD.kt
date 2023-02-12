package com.elvitalya.droiderhandbook.data

//@Singleton
//class DataRepository @Inject constructor(
//    private val questionsDao: QuestionsDao
//) {
//
//    fun getQuestionsFlow(): Flow<List<QuestionEntity>> = questionsDao.getAll()
//
//    suspend fun loadQuestions() {
//        deleteAllQuestions()
//        val snapShot = Firebase.firestore.collection("questions")
//            .orderBy("id", Query.Direction.ASCENDING)
//            .get()
//            .await()
//        val questions: List<FirebaseQuestion> =
//            snapShot.documents.mapNotNull { documentSnapshot -> documentSnapshot?.toObject() }
//        questionsDao.addQuestionsList(questions.mapNotNull { it.mapToEntity() })
//    }
//
//    suspend fun login(
//        email: String,
//        pass: String
//    ): AuthResult = Firebase.auth.signInWithEmailAndPassword(email, pass).await()
//
//
//    suspend fun registration(
//        email: String,
//        pass: String
//    ): AuthResult = Firebase.auth.createUserWithEmailAndPassword(email, pass).await()
//
//    private suspend fun deleteAllQuestions() = questionsDao.deleteAll()
//
//    suspend fun getQuestionById(id: String): QuestionEntity = questionsDao.getQuestion(id)
//
//    suspend fun updateQuestion(question: QuestionEntity) = questionsDao.updateQuestion(question)
//
//
//}