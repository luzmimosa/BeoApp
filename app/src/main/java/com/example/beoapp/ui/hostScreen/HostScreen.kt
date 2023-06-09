package com.example.beoapp.ui.hostScreen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.beoapp.R
import com.example.beoapp.model.Routes
import com.example.beoapp.network.ServerApi

@Composable
fun HostScreen(hostScreenViewModel: HostScreenViewModel, navHostController: NavHostController) {
    val serverHostInput: String by hostScreenViewModel.serverHostInput.observeAsState("")

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text( modifier = Modifier.fillMaxWidth(0.6f), text = stringResource(id = R.string.host_screen_title), textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = serverHostInput,
                onValueChange = { hostScreenViewModel.setServerHostInput(it) },
                modifier = Modifier.fillMaxWidth(0.8f),
                placeholder = { Text(stringResource(id = R.string.input_placeholder_host)) },
                label = { Text(stringResource(id = R.string.input_label_host)) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (hostScreenViewModel.handleSubmitButton()) {
                        navHostController.navigate(Routes.AvaiableLevelsScreen.route)
                    } else {
                        hostScreenViewModel.onServerHostError()
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = stringResource(id = R.string.button_submit))
            }
        }
    }
}
