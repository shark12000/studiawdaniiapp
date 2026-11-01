package com.example.studiawdaniiapp.data.repository

import com.example.studiawdaniiapp.data.firebase.FirebaseSource
import com.example.studiawdaniiapp.domain.models.*
import com.example.studiawdaniiapp.domain.repository.IProgrammeCatalogRepo
import kotlinx.coroutines.tasks.await

/** Reads programmes: the list offered by a university and single programme documents. */
class ProgrammeCatalogRepo(private val firebaseSource: FirebaseSource) : IProgrammeCatalogRepo {

    override suspend fun getProgrammesByUniversityID(universityID: String): Resource<List<Programme>> {
        return try {
            val list: MutableList<Programme> = mutableListOf()
            val col = firebaseSource.getFirebaseFirestoreCollection()
                .collection("junction_programme_university")
                .whereEqualTo("universityID", universityID)
                .get().await()
            for (document in col) {
                val programmeID = document.getString("programmeID")!!
                val programme = getProgrammeByID(programmeID)
                list.add(programme)
            }
            Resource.Success(list)
        } catch (e: Exception) {
            Resource.Failure(e.message.toString())
        }
    }

    override suspend fun getProgrammeByID(programmeID: String): Programme {
        val data = firebaseSource.getFirebaseFirestoreCollection().collection("progrmmes")
            .document(programmeID).get().await()
        return Programme(
            programmeID = programmeID,
            programmeName = data.getString("programmeName")!!,
            city = data.getString("programmeCity")!!,
            universityID = data.getString("universityID")!!,
            description = data.getString("description")!!,
            programmeLength = data.getString("programmeLength")!!,
            programmeType = data.getString("programmeType")!!
        )
    }
}
