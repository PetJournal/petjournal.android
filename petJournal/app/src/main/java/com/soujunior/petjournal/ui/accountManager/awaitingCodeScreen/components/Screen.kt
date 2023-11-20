package com.soujunior.petjournal.ui.accountManager.awaitingCodeScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.accountManager.awaitingCodeScreen.AwaitingCodeViewModel

@Composable
fun Screen(navController: NavController, viewModel: AwaitingCodeViewModel) {
    Box(modifier = Modifier.navigationBarsPadding().background(MaterialTheme.colorScheme.background)){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .navigationBarsPadding()
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .padding(horizontal = 16.dp)
                .padding(top = 32.dp)
                .background(MaterialTheme.colorScheme.background),
            content = {
                Header()
                VerificationCodeInput(viewModel)
                Footer(navController, viewModel)
            }
        )
    }
}