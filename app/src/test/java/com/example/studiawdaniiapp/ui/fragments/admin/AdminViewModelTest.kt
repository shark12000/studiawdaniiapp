package com.example.studiawdaniiapp.ui.fragments.admin

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.studiawdaniiapp.domain.models.DataToReceive
import com.example.studiawdaniiapp.domain.models.Resource
import com.example.studiawdaniiapp.testing.FakeAdmin
import com.example.studiawdaniiapp.testing.FakeUser
import com.example.studiawdaniiapp.testing.MainDispatcherRule
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

/** Characterization tests for the admin list and accept/refuse. */
class AdminViewModelTest {

    @get:Rule val instantTaskExecutor = InstantTaskExecutorRule()
    @get:Rule val mainDispatcher = MainDispatcherRule()

    private val admin = FakeAdmin()
    private val user = FakeUser()
    private val viewModel = AdminViewModel(admin, user, mainDispatcher.dispatcher)

    @Test
    fun `loads unanswered applications`() = runTest(mainDispatcher.dispatcher) {
        val sent = listOf(DataToReceive(id = "doc-1", education = "Bachelor", userName = "Ann", userID = "u1"))
        admin.sentDataResult = Resource.Success(sent)

        viewModel.getAdminData()
        advanceUntilIdle()

        assertEquals(Resource.Success(sent), viewModel.dataLiveData.value)
    }

    @Test
    fun `setStatus forwards status, user and document ids in order`() = runTest(mainDispatcher.dispatcher) {
        viewModel.setStatus("Accepted", "u1", "doc-1")
        advanceUntilIdle()

        assertEquals(listOf(FakeAdmin.SetStatusCall("Accepted", "u1", "doc-1")), admin.setStatusCalls)
    }

    @Test
    fun `refusing uses the Cancelled status string`() = runTest(mainDispatcher.dispatcher) {
        viewModel.setStatus("Cancelled", "u2", "doc-2")
        advanceUntilIdle()

        assertEquals("Cancelled", admin.setStatusCalls.single().status)
    }

    @Test
    fun `signOut logs the user out once`() = runTest(mainDispatcher.dispatcher) {
        viewModel.signOut()
        advanceUntilIdle()

        assertEquals(1, user.logoutCalls)
    }
}
