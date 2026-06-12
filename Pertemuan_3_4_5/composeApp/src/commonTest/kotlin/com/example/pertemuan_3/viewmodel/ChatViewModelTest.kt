package com.example.pertemuan_3.viewmodel

import app.cash.turbine.test
import com.example.pertemuan_3.data.AIRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class FakeAIRepository : AIRepository {
    var isChatCalled = false
    var isClearHistoryCalled = false
    var shouldReturnSuccess = true
    var fakeResponse = "Halo Ragil, ada yang bisa saya bantu?"
    var fakeErrorMessage = "Koneksi Gemini AI terputus"

    override suspend fun chat(message: String): Result<String> {
        isChatCalled = true
        return if (shouldReturnSuccess) {
            Result.success(fakeResponse)
        } else {
            Result.failure(Exception(fakeErrorMessage))
        }
    }

    override fun clearChatHistory() {
        isClearHistoryCalled = true
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class ChatViewModelTest {

    private lateinit var viewModel: ChatViewModel
    private lateinit var fakeAiRepository: FakeAIRepository
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        fakeAiRepository = FakeAIRepository()
        viewModel = ChatViewModel(fakeAiRepository)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testInitialStateIsEmpty() {
        val initialState = viewModel.uiState.value
        assertTrue(initialState.messages.isEmpty())
        assertFalse(initialState.isLoading)
        assertNull(initialState.error)
    }

    @Test
    fun testSendMessageBlankDoesNotTriggerRepository() {
        viewModel.sendMessage("   ")
        assertFalse(fakeAiRepository.isChatCalled)
        assertTrue(viewModel.uiState.value.messages.isEmpty())
    }

    @Test
    fun testClearHistoryResetsUiStateAndRepository() {
        viewModel.clearHistory()
        assertTrue(fakeAiRepository.isClearHistoryCalled)
        assertTrue(viewModel.uiState.value.messages.isEmpty())
        assertFalse(viewModel.uiState.value.isLoading)
    }

    @Test
    fun testSendMessageFailureUpdatesErrorState() = runTest(testDispatcher) {
        fakeAiRepository.shouldReturnSuccess = false
        viewModel.sendMessage("Halo AI")
        testDispatcher.scheduler.advanceUntilIdle()

        val finalState = viewModel.uiState.value
        assertFalse(finalState.isLoading)
        assertEquals(fakeAiRepository.fakeErrorMessage, finalState.error)
    }

    @Test
    fun testSendMessageTurbineEmissionsSequence() = runTest(testDispatcher) {
        fakeAiRepository.shouldReturnSuccess = true

        viewModel.uiState.test {
            val initialState = awaitItem()
            assertTrue(initialState.messages.isEmpty())

            viewModel.sendMessage("Halo")

            val loadingState = awaitItem()
            assertTrue(loadingState.isLoading)
            assertEquals(1, loadingState.messages.size)

            testDispatcher.scheduler.advanceUntilIdle()

            val successState = awaitItem()
            assertFalse(successState.isLoading)
            assertEquals(2, successState.messages.size)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun testClearHistoryTurbineEmission() = runTest(testDispatcher) {
        viewModel.sendMessage("Tes Obrolan")
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val currentState = awaitItem()
            assertEquals(2, currentState.messages.size)

            viewModel.clearHistory()

            val clearedState = awaitItem()
            assertTrue(clearedState.messages.isEmpty())
            assertFalse(clearedState.isLoading)
            assertNull(clearedState.error)

            cancelAndIgnoreRemainingEvents()
        }
    }
}