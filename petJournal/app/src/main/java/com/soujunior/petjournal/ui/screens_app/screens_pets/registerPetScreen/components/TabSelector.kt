package com.soujunior.petjournal.ui.screens_app.screens_pets.registerPetScreen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.soujunior.petjournal.ui.screens_app.screens_pets.taskListScreen.DateFilter
import com.soujunior.petjournal.ui.theme.PetJournalTheme
import com.soujunior.petjournal.R

@Composable
fun TabSelector(
    selectedFilter : DateFilter,
    onFilterSelected: (DateFilter) -> Unit,
    modifier: Modifier = Modifier
){
    val tabs = listOf(
        DateFilter.DAILY,
        DateFilter.WEEKLY,
        DateFilter.MONTHLY
    )
    val selectedIndex = tabs.indexOf(selectedFilter)
    TabRow(
        selectedTabIndex = selectedIndex,
        modifier = modifier,
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.primary,
        indicator = {
            tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.tabIndicatorOffset(tabPositions[selectedIndex]),
                color = MaterialTheme.colorScheme.primary
            )
        }
    ) {
        tabs.forEachIndexed {
            index, filter ->
            Tab(
                selected = selectedIndex == index,
                onClick = { onFilterSelected(filter)},
                text = {
                    val textRes = when(filter){
                        DateFilter.DAILY -> R.string.daily_tasks
                        DateFilter.WEEKLY -> R.string.weekly_tasks
                        DateFilter.MONTHLY -> R.string.monthly_tasks
                    }
                    Text(
                        text = stringResource(id = textRes),
                        fontWeight = if (selectedIndex == index) FontWeight.Bold else FontWeight.Normal,
                        color = if (selectedIndex == index) MaterialTheme.colorScheme.primary else Color.Gray
                    )
                },
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = Color.Gray
            )
        }
    }
}

@Preview
@Composable
fun PreviewTabSelector(){
    TabSelector(
        selectedFilter = DateFilter.DAILY,
        onFilterSelected = {},
        modifier = Modifier.fillMaxWidth()
    )
}