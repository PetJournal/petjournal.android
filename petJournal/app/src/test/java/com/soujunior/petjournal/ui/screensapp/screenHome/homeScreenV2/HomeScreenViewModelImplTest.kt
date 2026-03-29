package com.soujunior.petjournal.ui.screensapp.screenHome.homeScreenV2

import android.graphics.Color.parseColor
import androidx.compose.ui.graphics.Color
import com.soujunior.domain.model.response.tag.TagModel
import com.soujunior.domain.use_case.auth.LogoutUseCase
import com.soujunior.domain.use_case.base.DataResult
import com.soujunior.domain.use_case.guardian.GetGuardianNameUseCase
import com.soujunior.domain.use_case.pet.GetListPetUseCaseV1
import com.soujunior.domain.use_case.tag.GetListTagUseCase
import com.soujunior.domain.use_case.task.GetListCurrentWeekTaskUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class) // Android framework methods dependecy -> required for android.graphics.Color.parseColor
@Config(sdk = [33])
class HomeScreenViewModelImplTest {
    private val getGuardianNameUseCase: GetGuardianNameUseCase = mockk()
    private val getPetListUseCase: GetListPetUseCaseV1 = mockk()
    private val getListCurrentWeekTaskUseCase: GetListCurrentWeekTaskUseCase = mockk()
    private val logoutUseCase: LogoutUseCase = mockk(relaxed = true)
    private val getListTagUseCase: GetListTagUseCase = mockk()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        // Mock default behavior for init
        coEvery { getGuardianNameUseCase.execute(Unit) } returns DataResult.Failure(Exception())
        coEvery { getPetListUseCase.execute(Unit) } returns DataResult.Failure(Exception())
        coEvery { getListCurrentWeekTaskUseCase.execute(Unit) } returns DataResult.Failure(Exception())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        stopKoin()
    }

    private fun createViewModel() =
        HomeScreenViewModelImpl(
            getGuardianNameUseCase,
            getPetListUseCase,
            getListCurrentWeekTaskUseCase,
            logoutUseCase,
            getListTagUseCase,
        )

    @Test
    fun `getTags emite Loading = false e hasError = true quando usecase falha`() =
        runTest {
            coEvery { getListTagUseCase.execute(Unit) } returns DataResult.Failure(Exception("Network error"))

            val viewModel = createViewModel()

            assertFalse(viewModel.state.value.isLoadingListTag)
            assertTrue(viewModel.state.value.hasErrorOnListTag)
            assertTrue(viewModel.state.value.listTag.isEmpty())
        }

    @Test
    fun `getTags carrega tags mapeadas corretamente com cores validas`() =
        runTest {
            coEvery { getListTagUseCase.execute(Unit) } returns
                DataResult.Success(
                    listOf(TagModel(id = "valid1", name = "Tag Valida", color = "#FF0000")),
                )

            val viewModel = createViewModel()

            assertFalse(viewModel.state.value.isLoadingListTag)
            assertFalse(viewModel.state.value.hasErrorOnListTag)

            val tags = viewModel.state.value.listTag
            assertEquals(1, tags.size)
            assertEquals("valid1", tags[0].id)

            val expectedColor = Color(parseColor("#FF0000"))
            assertEquals(expectedColor, tags[0].color)
        }

    @Test
    fun `getTags com cores corrompidas e nulas deflete para Color Gray sem quebrar compilacao`() =
        runTest {
            val tagCorrupted = TagModel(id = "err1", name = "Test1", color = "corDeMorango")
            val tagNullColor = TagModel(id = "err2", name = "Test2", color = null)

            coEvery { getListTagUseCase.execute(Unit) } returns DataResult.Success(listOf(tagCorrupted, tagNullColor))

            val viewModel = createViewModel()

            val tags = viewModel.state.value.listTag
            assertEquals(2, tags.size)

            // Color fallback assert logic
            assertEquals(Color.Gray, tags[0].color)
            assertEquals(Color.Gray, tags[1].color)
        }
}
