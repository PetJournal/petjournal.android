package com.soujunior.petjournal.ui.screensapp.screenHome.homeScreenV2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import com.soujunior.petjournal.ui.model.TagOption
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [33], instrumentedPackages = ["androidx.loader.content"], qualifiers = RobolectricDeviceQualifiers.Pixel5)
class TagSectionTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun testLoadingState_showsShimmer_and_hidesHeader() {
        composeTestRule.setContent {
            TagSection(
                isLoadingListTag = true,
                listTag = emptyList(),
                hasErrorOnListTag = false,
                onReload = {},
                onTagClick = {},
            )
        }

        composeTestRule.onNodeWithText("Saiba mais").assertDoesNotExist()

        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun testErrorState_showsReloadButton_and_Header() {
        var reloaded = false
        composeTestRule.setContent {
            TagSection(
                isLoadingListTag = false,
                listTag = emptyList(),
                hasErrorOnListTag = true,
                onReload = { reloaded = true },
                onTagClick = {},
            )
        }

        composeTestRule.onNode(hasClickAction()).performClick()

        assertTrue(reloaded)

        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun testSuccessState_showsTagsAndCanBeClicked() {
        var clickedTag = ""
        val mockTags =
            listOf(
                TagOption(id = "tag_vacina", label = "VacinasMock", icon = Icons.Default.Star, color = Color.Red),
                TagOption(id = "tag_banho", label = "BanhoMock", icon = Icons.Default.Star, color = Color.Blue),
            )

        composeTestRule.setContent {
            TagSection(
                isLoadingListTag = false,
                listTag = mockTags,
                hasErrorOnListTag = false,
                onReload = {},
                onTagClick = { tagId -> clickedTag = tagId },
            )
        }

        composeTestRule.onNodeWithText("VacinasMock").assertExists().performClick()
        assertEquals("tag_vacina", clickedTag)

        composeTestRule.onNodeWithText("BanhoMock").assertExists().performClick()
        assertEquals("tag_banho", clickedTag)

        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun testEmptyState_hidesEverything() {
        composeTestRule.setContent {
            Box(
                modifier =
                    Modifier
                        .size(100.dp)
                        .background(Color.White),
            ) {
                TagSection(
                    isLoadingListTag = false,
                    listTag = emptyList(),
                    hasErrorOnListTag = false,
                    onReload = {},
                    onTagClick = {},
                )
            }
        }

        composeTestRule.onNodeWithText("Saiba mais").assertDoesNotExist()

        composeTestRule.onRoot().captureRoboImage()
    }
}
