package com.example.studiawdaniiapp.ui.fragments.home.educationInfo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.studiawdaniiapp.domain.models.DataToSend
import com.example.studiawdaniiapp.domain.models.Resource
import com.example.studiawdaniiapp.testing.FakeProgramme
import com.example.studiawdaniiapp.testing.FakeStatus
import com.example.studiawdaniiapp.testing.MainDispatcherRule
import com.example.studiawdaniiapp.testing.programme
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

/**
 * Characterization tests for the apply flow. EducationInfoFragment calls sendData, and on
 * Resource.Success it calls setStatus("Sent"); these tests pin the ViewModel half of that.
 */
class EducationInfoViewModelTest {

    @get:Rule val instantTaskExecutor = InstantTaskExecutorRule()
    @get:Rule val mainDispatcher = MainDispatcherRule()

    private val programmes = FakeProgramme()
    private val status = FakeStatus()
    private val viewModel = EducationInfoViewModel(programmes, status, mainDispatcher.dispatcher)

    @Test
    fun `loads the student's selected programmes`() = runTest(mainDispatcher.dispatcher) {
        val list = listOf(programme("p1"))
        programmes.byUserResult = Resource.Success(list)

        viewModel.getProgrammeListData()
        advanceUntilIdle()

        assertEquals(Resource.Success(list), viewModel.educationLiveData.value)
    }

    @Test
    fun `sendData forwards the application and posts success`() = runTest(mainDispatcher.dispatcher) {
        val application = DataToSend(education = "Bachelor", programmes = listOf(programme("p1")))

        viewModel.sendData(application)
        advanceUntilIdle()

        assertEquals(listOf(application), programmes.sendCalls)
        assertEquals(Resource.Success(true), viewModel.booleanLiveData.value)
    }

    @Test
    fun `sendData posts the failure when an application already exists`() = runTest(mainDispatcher.dispatcher) {
        programmes.sendResult = Resource.Failure("already sent")

        viewModel.sendData(DataToSend("Bachelor", emptyList()))
        advanceUntilIdle()

        assertEquals(Resource.Failure<Boolean>("already sent"), viewModel.booleanLiveData.value)
    }

    @Test
    fun `sendData does not set the status by itself`() = runTest(mainDispatcher.dispatcher) {
        viewModel.sendData(DataToSend("Bachelor", emptyList()))
        advanceUntilIdle()

        assertTrue(status.setStatusCalls.isEmpty())
    }

    @Test
    fun `setStatus forwards Sent`() = runTest(mainDispatcher.dispatcher) {
        viewModel.setStatus("Sent")
        advanceUntilIdle()

        assertEquals(listOf("Sent"), status.setStatusCalls)
    }

    @Test
    fun `removeItem forwards the programme id`() = runTest(mainDispatcher.dispatcher) {
        viewModel.removeItem("p1")
        advanceUntilIdle()

        assertEquals(listOf("p1"), programmes.removeCalls)
    }
}
