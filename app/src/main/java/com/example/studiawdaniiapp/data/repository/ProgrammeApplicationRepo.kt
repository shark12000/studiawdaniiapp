package com.example.studiawdaniiapp.data.repository

import com.example.studiawdaniiapp.data.firebase.FirebaseSource
import com.example.studiawdaniiapp.domain.models.*
import com.example.studiawdaniiapp.domain.repository.IProgrammeApplicationRepo
import com.example.studiawdaniiapp.domain.repository.IProgrammeCatalogRepo
import kotlinx.coroutines.tasks.await

/** A student's application: selected programmes, and sending them for review. */
class ProgrammeApplicationRepo(
    private val firebaseSource: FirebaseSource,
    private val catalog: IProgrammeCatalogRepo
) : IProgrammeApplicationRepo {

    override suspend fun getProgrammesByUserID(): Resource<List<Programme>> {
        return try {
            val user = firebaseSource.getFirebaseAuth().currentUser!!
            val list: MutableList<Programme> = mutableListOf()
            val col = firebaseSource.getFirebaseFirestoreCollection()
                .collection("user_programmes_university").whereEqualTo("userID", user.uid)
                .get().await()
            for (document in col) {
                val programmeID = document.getString("programmeID")!!
                val programme = catalog.getProgrammeByID(programmeID)
                list.add(programme)
            }
            Resource.Success(list)
        } catch (e: Exception) {
            Resource.Failure(e.message.toString())
        }
    }

    override suspend fun selectProgramme(programmeID: String): Boolean {
        val user = firebaseSource.getFirebaseAuth().currentUser!!
        val userProgramme = UserProgramme(
            userID = user.uid,
            programmeID = programmeID
        )
        val boolean = isOneProgrammeRelationship(userProgramme)
        return if (boolean) {
            firebaseSource.getFirebaseFirestoreCollection().collection("user_programmes_university")
                .document().set(userProgramme).await()
            boolean
        } else {
            false
        }
    }

    override suspend fun removeProgramme(programmeID: String) {
        val user = firebaseSource.getFirebaseAuth().currentUser!!
        val result = firebaseSource.getFirebaseFirestoreCollection()
            .collection("user_programmes_university")
            .whereEqualTo("programmeID", programmeID)
            .whereEqualTo("userID", user.uid)
            .get()
            .await()
        for (doc in result) {
            doc.reference.delete()
        }
    }

    override suspend fun sendData(dataToSend: DataToSend): Resource<Boolean> {
        try {
            val user = firebaseSource.getFirebaseAuth().currentUser!!
            val finalDataToSend = FinalDataToSend(
                dataToSend = dataToSend,
                userID = user.uid,
                answered = false
            )
            val boolean = isOneDataSendRelationship(finalDataToSend)
            if(boolean) {
                firebaseSource.getFirebaseFirestoreCollection()
                    .collection("user_data_to_send")
                    .document()
                    .set(finalDataToSend)
                    .await()
            }
           return Resource.Success(boolean)
        } catch (e: Exception) {
            return Resource.Failure(e.message.toString())
        }
    }

    private suspend fun isOneDataSendRelationship(dataToSend: FinalDataToSend): Boolean {
        val result = firebaseSource.getFirebaseFirestoreCollection()
            .collection("user_data_to_send")
            .whereEqualTo("userID", dataToSend.userID)
            .get()
            .await()
        val count = result.size()
        return count < 1
    }

    private suspend fun isOneProgrammeRelationship(userProgramme: UserProgramme): Boolean {
        val result = firebaseSource.getFirebaseFirestoreCollection()
            .collection("user_programmes_university")
            .whereEqualTo("userID", userProgramme.userID)
            .whereEqualTo("programmeID", userProgramme.programmeID)
            .get()
            .await()
        val count = result.size()
        return count < 1
    }
}
