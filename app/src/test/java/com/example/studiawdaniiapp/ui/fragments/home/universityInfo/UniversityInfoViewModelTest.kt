package com.example.studiawdaniiapp.ui.fragments.home.universityInfo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.example.studiawdaniiapp.domain.models.Resource
import com.example.studiawdaniiapp.testing.FakeProgramme
import com.example.studiawdaniiapp.testing.MainDispatcherRule
import com.example.studiawdaniiapp.testing.programme
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

/** Characterization tests for listing a university's programmes and selecting one. */
class UniversityInfoViewModelTest {

    @get:Rule val instantTaskExecutor = InstantTaskExecutorRule()
    @get:Rule val mainDispatcher = MainDispatcherRule()

    private val programmes = FakeProgramme()
    private val viewModel = UniversityInfoViewModel(SavedStateHandle(), programmes, mainDispatcher.dispatcher)

    @Test
    fun `loads programmes for the given university`() = runTest(mainDispatcher.dispatcher) {
        val list = listOf(programme("p1"), programme("p2"))
        programmes.byUniversityResult = Resource.Success(list)

        viewModel.getUserProfileData("uni-1")
        advanceUntilIdle()

        assertEquals(listOf("uni-1"), programmes.universityIdCalls)
        assertEquals(Resource.Success(list), viewModel.programmeLiveData.value)
    }

    @Test
    fun `selectProgramme forwards the id and posts true when saved`() = runTest(mainDispatcher.dispatcher) {
        programmes.selectResult = true

        viewModel.selectProgramme("p1")
        advanceUntilIdle()

        assertEquals(listOf("p1"), programmes.selectCalls)
        assertEquals(true, viewModel.booleanLiveData.value)
    }

    @Test
    fun `selectProgramme posts false when the programme was already selected`() = runTest(mainDispatcher.dispatcher) {
        programmes.selectResult = false

        viewModel.selectProgramme("p1")
        advanceUntilIdle()

        assertEquals(false, viewModel.booleanLiveData.value)
    }

    @Test
    fun `each tap calls the use case again`() = runTest(mainDispatcher.dispatcher) {
        viewModel.selectProgramme("p1")
        viewModel.selectProgramme("p1")
        advanceUntilIdle()

        assertEquals(listOf("p1", "p1"), programmes.selectCalls)
    }
}
