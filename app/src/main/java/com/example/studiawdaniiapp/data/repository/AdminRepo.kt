package com.example.studiawdaniiapp.data.repository

import com.example.studiawdaniiapp.data.firebase.FirebaseSource
import com.example.studiawdaniiapp.domain.models.DataToReceive
import com.example.studiawdaniiapp.domain.models.Resource
import com.example.studiawdaniiapp.domain.repository.IAdminRepo
import kotlinx.coroutines.tasks.await

class AdminRepo(private val firebaseSource: FirebaseSource) : IAdminRepo {

    override suspend fun getSentData(): Resource<List<DataToReceive>> {
        try {
            val list = mutableListOf<DataToReceive>()
            val result = firebaseSource.getFirebaseFirestoreCollection()
                .collection("user_data_to_send")
                .get()
                .await()
            for (doc in result) {
                val boolean: Boolean = doc.getBoolean("answered")!!
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
            .collection("user_data_to_send")
            .document(documentID)
            .update("answered", true)
            .await()


        firebaseSource.getFirebaseFirestoreCollection()
            .collection("step_one_status")
            .document(userID)
            .update(
                "status", status
            )
            .await()
    }

    private suspend fun getUserNameByUserID(userID: String): String {
        val result = firebaseSource.getFirebaseFirestoreCollection()
            .collection("users").document(userID).get().await()

        return result.getString("firstName")!!
    }
}