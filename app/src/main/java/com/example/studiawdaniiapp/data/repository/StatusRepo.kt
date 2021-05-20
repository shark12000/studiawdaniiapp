package com.example.studiawdaniiapp.data.repository

import com.example.studiawdaniiapp.data.firebase.FirebaseSource
import com.example.studiawdaniiapp.domain.models.Resource
import com.example.studiawdaniiapp.domain.models.StepOneStatus
import com.example.studiawdaniiapp.domain.repository.IStatusRepo
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class StatusRepo(private val firebaseSource: FirebaseSource) : IStatusRepo {
    override suspend fun getStatus() : Resource<StepOneStatus> {
        return try {
            val user = firebaseSource.getFirebaseAuth().currentUser!!
            val result = firebaseSource.getFirebaseFirestoreCollection()
                .collection("step_one_status")
                .whereEqualTo("userID", user.uid)
                .limit(1)
                .get()
                .await()

            val stepOneStatus: StepOneStatus = result.documents[0]
                .toObject(StepOneStatus::class.java)!!
            Resource.Success(stepOneStatus)
        } catch (e: Exception) {
            Resource.Failure(e.message.toString())
        }
    }

    override suspend fun setStatus(status: String) {
        val user = firebaseSource.getFirebaseAuth().currentUser!!
//        val data = firebaseSource.getFirebaseFirestoreCollection()
//            .collection("step_one_status")
//            .whereEqualTo("userID", user.uid)
//            .get()
//            .await()


        firebaseSource.getFirebaseFirestoreCollection()
            .collection("step_one_status")
            .document(user.uid).set(StepOneStatus(
                userID = user.uid,
                status = status
            ))
            .await()
    }
}