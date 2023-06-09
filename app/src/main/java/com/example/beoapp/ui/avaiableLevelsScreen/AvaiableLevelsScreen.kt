package com.example.beoapp.ui.avaiableLevelsScreen

import android.content.Context
import android.util.Log
import android.widget.Button
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.beoapp.model.Routes
import com.example.beoapp.R

@Composable
fun AvaiableLevelsScreen(viewModel: AvaiableLevelsViewModel, navHostController: NavHostController) {

    val levels by viewModel.avaiableLevels.observeAsState(null)
    val errorVisible by viewModel.errorVisible.observeAsState(false)
    val emptyListVisible by viewModel.emptyListVisible.observeAsState(false)

    LaunchedEffect(key1 = null, block = {
        viewModel.fetchAvaiableLevels()
    })

    Column {
        if (errorVisible) {
            Text(text = stringResource(id = R.string.error_fetching_levels))
        } else if (levels == null) {
            CircularProgressIndicator()
        } else if (emptyListVisible) {
            Text(text = stringResource(id = R.string.empty_level_list))
        } else {
            StringListButtons(values = levels ?: emptyArray(), onItemClick = { navHostController.navigate(Routes.LevelScreen.route(it)) })
        }
    }
}

@Composable
private fun StringListButtons(values: Array<String>, onItemClick: (String) -> Unit) {
    LazyColumn {
        items(values) { value ->
            Button(
                onClick = { onItemClick(value) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .height(48.dp)
            ) {
                Text(text = value)
            }
        }
    }
}
