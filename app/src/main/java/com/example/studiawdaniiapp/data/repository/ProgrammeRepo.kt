package com.example.studiawdaniiapp.data.repository

import android.util.Log
import com.example.studiawdaniiapp.data.firebase.FirebaseSource
import com.example.studiawdaniiapp.domain.models.Programme
import com.example.studiawdaniiapp.domain.repository.IProgrammeRepo
import kotlinx.coroutines.tasks.await

class ProgrammeRepo(private val firebaseSource: FirebaseSource) : IProgrammeRepo {


    private val TAG = "ProgrammeRepo"

    override suspend fun getProgramme(programmeID: String) : List<Programme> {
        val list: MutableList<Programme> = mutableListOf()
        Log.d(TAG, "5")
        val data =
            firebaseSource.getFirebaseFirestoreCollection("progrmmes").whereEqualTo(
                "programmeID",
                programmeID
            ).get().await()
        for(d in data) {

            val programme = Programme(
                programmeId = d.id,
                programmeName = d.getString("programmeName")!!,
                city = d.getString("programmeCity")!!,
            )
            list.add(
                programme
            )
            Log.d(TAG, programme.programmeName)
        }
        return list
    }

    override suspend fun getProgrammesId(universityId: String): List<String> {
        val list: MutableList<String> = mutableListOf()
        val col = firebaseSource.getFirebaseFirestoreCollection("junction_programme_university")
            .whereEqualTo("universityID", universityId).get().await()
        for (document in col) {
            val programmeID = document.getString("programmeID")
            list.add(programmeID!!)
        }
        return list
    }
}
