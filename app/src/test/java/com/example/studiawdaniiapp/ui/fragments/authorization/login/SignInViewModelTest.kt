package com.example.studiawdaniiapp.ui.fragments.authorization.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.studiawdaniiapp.domain.models.EmailPassword
import com.example.studiawdaniiapp.domain.models.Resource
import com.example.studiawdaniiapp.testing.FakeSignInRepo
import com.example.studiawdaniiapp.testing.MainDispatcherRule
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test

/**
 * Characterization tests: they pin down what SignInViewModel does today.
 * SignInFragment routes to the admin graph when isAdminLiveData is true, otherwise to home.
 */
class SignInViewModelTest {

    @get:Rule val instantTaskExecutor = InstantTaskExecutorRule()
    @get:Rule val mainDispatcher = MainDispatcherRule()

    private val repo = FakeSignInRepo()
    private val viewModel = SignInViewModel(repo, mainDispatcher.dispatcher)

    @Test
    fun `signIn passes credentials and posts success`() = runTest(mainDispatcher.dispatcher) {
        val credentials = EmailPassword("student@example.com", "secret")

        viewModel.signIn(credentials)
        advanceUntilIdle()

        assertEquals(listOf(credentials), repo.signInCalls)
        assertEquals(Resource.Success(true), viewModel.signInLiveData.value)
    }

    @Test
    fun `signIn posts the failure message unchanged`() = runTest(mainDispatcher.dispatcher) {
        repo.signInResult = Resource.Failure("wrong password")

        viewModel.signIn(EmailPassword("student@example.com", "bad"))
        advanceUntilIdle()

        assertEquals(Resource.Failure<Boolean>("wrong password"), viewModel.signInLiveData.value)
    }

    @Test
    fun `isAdmin posts true for an admin`() = runTest(mainDispatcher.dispatcher) {
        repo.isAdminResult = true

        viewModel.isAdmin()
        advanceUntilIdle()

        assertEquals(true, viewModel.isAdminLiveData.value)
    }

    @Test
    fun `isAdmin posts false for a student`() = runTest(mainDispatcher.dispatcher) {
        repo.isAdminResult = false

        viewModel.isAdmin()
        advanceUntilIdle()

        assertEquals(false, viewModel.isAdminLiveData.value)
    }

    @Test
    fun `nothing is posted before a call`() {
        assertNull(viewModel.signInLiveData.value)
        assertNull(viewModel.isAdminLiveData.value)
    }
}
