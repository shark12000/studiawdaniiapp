package com.example.studiawdaniiapp.testing

import com.example.studiawdaniiapp.domain.IAdmin
import com.example.studiawdaniiapp.domain.IProgramme
import com.example.studiawdaniiapp.domain.IStatus
import com.example.studiawdaniiapp.domain.IUser
import com.example.studiawdaniiapp.domain.models.DataToReceive
import com.example.studiawdaniiapp.domain.models.DataToSend
import com.example.studiawdaniiapp.domain.models.EmailPassword
import com.example.studiawdaniiapp.domain.models.ProfileData
import com.example.studiawdaniiapp.domain.models.Programme
import com.example.studiawdaniiapp.domain.models.Resource
import com.example.studiawdaniiapp.domain.models.StepOneStatus
import com.example.studiawdaniiapp.domain.repository.IUserSignInRepo

fun programme(id: String) = Programme(
    programmeID = id,
    universityID = "uni-1",
    programmeName = "Programme $id",
    city = "Warsaw",
    programmeType = "full-time",
    programmeLength = "3 years",
    description = ""
)

class FakeSignInRepo : IUserSignInRepo {
    var signInResult: Resource<Boolean> = Resource.Success(true)
    var isAdminResult = false
    val signInCalls = mutableListOf<EmailPassword>()

    override suspend fun signIn(emailPassword: EmailPassword): Resource<Boolean> {
        signInCalls += emailPassword
        return signInResult
    }

    override suspend fun isAdmin(): Boolean = isAdminResult
}

class FakeProgramme : IProgramme {
    var byUniversityResult: Resource<List<Programme>> = Resource.Success(emptyList())
    var byUserResult: Resource<List<Programme>> = Resource.Success(emptyList())
    var selectResult = true
    var sendResult: Resource<Boolean> = Resource.Success(true)

    val universityIdCalls = mutableListOf<String>()
    val selectCalls = mutableListOf<String>()
    val removeCalls = mutableListOf<String>()
    val sendCalls = mutableListOf<DataToSend>()

    override suspend fun getProgrammesByUniversityID(universityID: String): Resource<List<Programme>> {
        universityIdCalls += universityID
        return byUniversityResult
    }

    override suspend fun getProgrammesByUserID(): Resource<List<Programme>> = byUserResult

    override suspend fun selectProgramme(programmeID: String): Boolean {
        selectCalls += programmeID
        return selectResult
    }

    override suspend fun removeProgramme(programmeID: String) {
        removeCalls += programmeID
    }

    override suspend fun sendData(dataToSend: DataToSend): Resource<Boolean> {
        sendCalls += dataToSend
        return sendResult
    }
}

class FakeStatus : IStatus {
    var statusResult: Resource<StepOneStatus> = Resource.Failure("not set")
    val setStatusCalls = mutableListOf<String>()

    override suspend fun getStatus(): Resource<StepOneStatus> = statusResult

    override suspend fun setStatus(status: String) {
        setStatusCalls += status
    }
}

class FakeAdmin : IAdmin {
    data class SetStatusCall(val status: String, val userID: String, val documentID: String)

    var sentDataResult: Resource<List<DataToReceive>> = Resource.Success(emptyList())
    val setStatusCalls = mutableListOf<SetStatusCall>()

    override suspend fun getSentData(): Resource<List<DataToReceive>> = sentDataResult

    override suspend fun setStatus(status: String, userID: String, documentID: String) {
        setStatusCalls += SetStatusCall(status, userID, documentID)
    }
}

class FakeUser : IUser {
    var userResult: Resource<ProfileData> = Resource.Failure("not set")
    var signedIn = true
    var logoutCalls = 0
    val updateCalls = mutableListOf<ProfileData>()

    override suspend fun getUser(): Resource<ProfileData> = userResult

    override suspend fun updateUser(profileData: ProfileData) {
        updateCalls += profileData
    }

    override suspend fun checkIfUserIsSignedIn(): Boolean = signedIn

    override suspend fun logout(): Boolean {
        logoutCalls++
        return true
    }
}
