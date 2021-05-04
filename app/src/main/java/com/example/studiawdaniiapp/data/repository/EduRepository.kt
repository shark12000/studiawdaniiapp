package com.example.studiawdaniiapp.data.repository

import com.example.studiawdaniiapp.data.models.EducationalInstitution
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.lang.Exception

class EduRepository(private var onFirestoreTaskComplete: OnFirestoreTaskComplete) {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val uniRef: CollectionReference = db.collection("university_info")

    fun getUniData() {
        uniRef.get().addOnCompleteListener(object : OnCompleteListener<QuerySnapshot> {
            override fun onComplete(task: Task<QuerySnapshot>) {
                if (task.isSuccessful()) {
                    task.result?.let {
                        onFirestoreTaskComplete.eduDataAdded(
                            it.toObjects(
                                EducationalInstitution::class.java
                            )
                        )
                    }
                } else {
                   onFirestoreTaskComplete.onError(task.exception!!)
                }
            }
        })
    }

    interface OnFirestoreTaskComplete {
        fun eduDataAdded(eduList: List<EducationalInstitution>);
        fun onError(e: Exception)
    }
}

