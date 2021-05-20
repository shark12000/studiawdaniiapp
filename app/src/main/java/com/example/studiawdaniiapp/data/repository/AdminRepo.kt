package com.example.studiawdaniiapp.data.repository

import com.example.studiawdaniiapp.data.firebase.FirebaseSource
import com.example.studiawdaniiapp.domain.models.DataToReceive
import com.example.studiawdaniiapp.domain.models.Resource
import com.example.studiawdaniiapp.domain.repository.IAdminRepo
import kotlinx.coroutines.tasks.await

class AdminRepo(private val firebaseSource: FirebaseSource) : IAdminRepo {

    companion object {
        private const val DATA_TO_SEND_COLLECTION = "user_data_to_send"
        private const val STEP_ONE_STATUS_COLLECTION = "step_one_status"
        private const val USER_COLLECTION = "users"
        private const val ANSWERED = "answered"
        private const val STATUS_FIELD = "status"
        private const val FIRST_NAME_FIELD = "firstName"
    }

    override suspend fun getSentData(): Resource<List<DataToReceive>> {
        try {
            val list = mutableListOf<DataToReceive>()
            val result = firebaseSource.getFirebaseFirestoreCollection()
                .collection(DATA_TO_SEND_COLLECTION)
                .get()
                .await()
            for (doc in result) {
                val boolean: Boolean = doc.getBoolean(ANSWERED)!!
                if (!boolean) {
                    val data = doc.toObject(DataToReceive::class.java)
                    val dataToReceive = DataToReceive(
                        userName = getUserNameByUserID(userID = data.userID),
                        userID = data.userID,
                        programmes = data.programmes,
                        education = data.education,
                        id = doc.id
                    )
                    list.add(dataToReceive)
                }
            }
            return Resource.Success(list)
        } catch (e: Exception) {
            return Resource.Failure(e.message.toString())
        }
    }

    override suspend fun setStatus(status: String, userID: String, documentID: String) {

        firebaseSource.getFirebaseFirestoreCollection()
            .collection(DATA_TO_SEND_COLLECTION)
            .document(documentID)
            .update(ANSWERED, true)
            .await()


        firebaseSource.getFirebaseFirestoreCollection()
            .collection(STEP_ONE_STATUS_COLLECTION)
            .document(userID)
            .update(
                STATUS_FIELD, status
            )
            .await()
    }

    private suspend fun getUserNameByUserID(userID: String): String {
        val result = firebaseSource.getFirebaseFirestoreCollection()
            .collection(USER_COLLECTION).document(userID).get().await()

        return result.getString(FIRST_NAME_FIELD)!!
    }
}