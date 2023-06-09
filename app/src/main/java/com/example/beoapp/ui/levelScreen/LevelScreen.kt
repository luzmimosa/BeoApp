package com.example.beoapp.ui.levelScreen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.beoapp.network.model.CustomStatRecord
import com.example.beoapp.ui.components.SimplePopup
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun LevelScreen(levelScreenViewModel: LevelScreenViewModel) {

    val selectedStat by levelScreenViewModel.selectedStat.observeAsState(null)
    val statRecords by levelScreenViewModel.statRecords.observeAsState(null)
    val errorVisible by levelScreenViewModel.errorVisible.observeAsState(false)

    LaunchedEffect(key1 = null, block = {
        levelScreenViewModel.fetchStatRecords()
    })

    Column {

        if (errorVisible) {
            Text(text = "No se han podido cargar las estadísticas")
            return@Column;
        }

        if (selectedStat != null) {

            Log.i("LevelScreen", "selectedStat: $selectedStat")

            Row {
                val stat = selectedStat!!

                var mapString = ""

                for (key in stat.map.keys) {
                    val value = stat.map[key]
                    mapString += "$key: $value\n"
                }

                SimplePopup(
                    title = stat.orbName,
                    message = mapString
                ) {
                    levelScreenViewModel.resetSelectedStat()
                }
            }
        }

        if (statRecords != null) {
            Row {
                val records = statRecords!!

                for (record in records) {
                    StatCard(record) {
                        levelScreenViewModel.handleStatPress(it)
                    }
                }
            }
        } else {
            CircularProgressIndicator()
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StatCard(stat: CustomStatRecord, onClick: (stat: CustomStatRecord) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = 8.dp,
        onClick = {
            Log.i("StatCard", "StatCard clicked")
            onClick(stat)
        }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stat.orbName,
                style = MaterialTheme.typography.h6
            )
            Text(
                text = formatDate(stat.dateTime),
                style = MaterialTheme.typography.body2
            )
            Text(
                text = "Registros: ${stat.map.size}",
                style = MaterialTheme.typography.body2
            )
        }
    }
}

private fun formatDate(date: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    return date.format(formatter)
}