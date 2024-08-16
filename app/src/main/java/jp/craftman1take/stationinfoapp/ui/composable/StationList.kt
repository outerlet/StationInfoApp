package jp.craftman1take.stationinfoapp.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.craftman1take.stationinfoapp.data.Entity
import jp.craftman1take.stationinfoapp.data.sampleStationList

@Composable
fun StationList(
    modifier: Modifier = Modifier,
    stationList: List<Entity.Station> = emptyList(),
    onClick: (Entity.Station) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        itemsIndexed(
            items = stationList,
            key = { _, station -> station.name },
        ) { index, station ->
            StationCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick(stationList[index]) },
                station = station,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StationListPreview() {
    StationList(
        modifier = Modifier.fillMaxSize(),
        stationList = sampleStationList,
    )
}