package com.rago.androidgraphql.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.rago.LaunchListQuery

@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {
    val launchList by homeViewModel.launchList.collectAsState()
    LaunchedEffect(Unit) {
        homeViewModel.getLaunchesList()
    }
    HomeContent(homeUiState = launchList)
}

@Composable
fun HomeContent(homeUiState: HomeUiState) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (homeUiState) {
            HomeUiState.NoData -> {
                Text(text = "Not Data")
            }
            is HomeUiState.Error -> {
                Text(text = "Error ${homeUiState.msg}")
            }
            HomeUiState.Loading -> {
                CircularProgressIndicator()
            }
            HomeUiState.NotStatus -> {

            }
            is HomeUiState.Success -> {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(homeUiState.data) { launch ->
                        launch?.let {
                            RowLaunch(it)
                            Divider(color = Color.Gray, thickness = 1.dp)
                        }
                    }
                }
            }
        }
    }
}

//{
//          "id": "109",
//          "site": "CCAFS SLC 40",
//          "mission": {
//            "name": "Starlink-15 (v1.0)",
//            "missionPatch": "https://images2.imgbox.com/9a/96/nLppz9HW_o.png"
//          }
//        }

@Preview(showBackground = true)
@Composable
fun RowLaunch(
    data: LaunchListQuery.Launch = LaunchListQuery.Launch(
        id = "109",
        site = "CCAFS SLC 40",
        mission = LaunchListQuery.Mission(
            name = "Starlink-15 (v1.0)",
            missionPatch = "https://i.imgur.com/Ehe9AgY.png"
        )

    )
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxSize()) {
            Column(
                Modifier
                    .fillMaxSize()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    modifier = Modifier.size(60.dp, 60.dp),
                    model = data.mission!!.missionPatch,
                    contentDescription = null
                )
            }
            Column(
                Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(horizontal = 15.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = data.mission!!.name!!)
                Text(text = data.site ?: "")
            }
        }
    }
}