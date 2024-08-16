package jp.craftman1take.stationinfoapp.ui.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.craftman1take.stationinfoapp.data.Entity
import jp.craftman1take.stationinfoapp.data.sampleStation
import jp.craftman1take.stationinfoapp.ui.composable.common.BoxText
import jp.craftman1take.stationinfoapp.ui.theme.StationInfoAppTheme

@Composable
fun StationCard(modifier: Modifier = Modifier, station: Entity.Station) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        border = BorderStroke(width = 1.dp, color = Color.Black),
    ) {
        BoxText(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp),
            contentAlignment = Alignment.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textColor = Color.Black,
            text = station.name,
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
                .background(color = station.line.prefecture.area.stationLineColor ?: Color.DarkGray)
                .padding(horizontal = 12.dp),
        ) {
            val previousName = when (station) {
                is Entity.Station.Normal -> station.previousName
                is Entity.Station.Terminal -> station.previousName
                else -> null
            }

            if (previousName != null) {
                Text(
                    modifier = Modifier.align(Alignment.CenterStart),
                    fontSize = 14.sp,
                    color = Color.White,
                    text = previousName,
                )
            }

            val nextName = when (station) {
                is Entity.Station.Normal -> station.nextName
                is Entity.Station.Starting -> station.nextName
                else -> null
            }

            if (nextName != null) {
                Text(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    fontSize = 14.sp,
                    color = Color.White,
                    text = nextName,
                )
            }
        }

        BoxText(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            contentAlignment = Alignment.Center,
            fontSize = 16.sp,
            textColor = Color.Black,
            text = "(${station.latitude}, ${station.longitude})",
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StationCardPreview() {
    StationInfoAppTheme {
        Surface {
            StationCard(
                modifier = Modifier.fillMaxWidth(),
                station = sampleStation,
            )
        }
    }
}