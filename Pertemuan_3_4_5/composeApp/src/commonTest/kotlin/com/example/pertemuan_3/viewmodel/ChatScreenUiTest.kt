package com.example.pertemuan_3.viewmodel

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

@OptIn(ExperimentalCoroutinesApi::class)
class ChatScreenUiTest {

    private lateinit var fakeAiRepository: FakeAIRepository
    private lateinit var viewModel: ChatViewModel
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
    fun testChatScreen_InitialState_ShouldRenderEmptyListAndInputField() {
        val state = viewModel.uiState.value
        assertTrue(state.messages.isEmpty(), "Komponen LazyColumn (chat_list) harusnya kosong di awal")
        assertFalse(state.isLoading, "TypingIndicator (loading) tidak boleh dirender di awal")
        assertNull(state.error, "Komponen Error Text tidak boleh muncul di layar")
    }

    @Test
    fun testChatScreen_TypingAndSending_ShouldRenderUserBubbleAndLoading() = runTest(testDispatcher) {
        val pesanInput = "Halo Gemini, tes UI"

        viewModel.sendMessage(pesanInput)
        testDispatcher.scheduler.advanceUntilIdle()

        val stateSesudahKirim = viewModel.uiState.value
        assertEquals(2, stateSesudahKirim.messages.size)
        assertTrue(stateSesudahKirim.messages.first().isUser, "Bubble chat pertama yang dirender harus bertipe User")
    }

    @Test
    fun testChatScreen_AiResponding_ShouldRenderAiBubbleAndStopLoading() = runTest(testDispatcher) {
        fakeAiRepository.shouldReturnSuccess = true
        fakeAiRepository.fakeResponse = "Jawaban Robot AI"

        viewModel.sendMessage("Pertanyaan User")
        testDispatcher.scheduler.advanceUntilIdle()

        val stateAkhir = viewModel.uiState.value
        assertEquals(2, stateAkhir.messages.size)
        assertEquals("Jawaban Robot AI", stateAkhir.messages[1].text, "Teks di dalam Bubble AI harus cocok")
        assertFalse(stateAkhir.messages[1].isUser, "Bubble kedua yang dirender harus bertipe AI (bukan user)")
        assertFalse(stateAkhir.isLoading, "TypingIndicator harus berhenti dirender setelah respons AI masuk")
    }

    @Test
    fun testChatScreen_ErrorOccurred_ShouldRenderErrorTextComponent() = runTest(testDispatcher) {
        fakeAiRepository.shouldReturnSuccess = false
        fakeAiRepository.fakeErrorMessage = "Jaringan Bermasalah 404"

        viewModel.sendMessage("Tes Jaringan")
        testDispatcher.scheduler.advanceUntilIdle()

        val stateEror = viewModel.uiState.value
        assertEquals("Jaringan Bermasalah 404", stateEror.error)
        assertFalse(stateEror.isLoading, "TypingIndicator harus hilang jika terjadi eror")
    }
}