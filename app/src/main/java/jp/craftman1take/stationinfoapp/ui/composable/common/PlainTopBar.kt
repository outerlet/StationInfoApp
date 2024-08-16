package jp.craftman1take.stationinfoapp.ui.composable.common

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import jp.craftman1take.stationinfoapp.ui.theme.StationInfoAppTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun PlainTopBar(
    containerColor: Color,
    titleColor: Color,
    fontSize: TextUnit,
    text: String,
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor,
            titleContentColor = titleColor,
        ),
        title = {
            Text(
                fontSize = fontSize,
                text = text,
            )
        },
    )
}

@Preview(showBackground = true)
@Composable
fun PlainTopBarPreview() {
    StationInfoAppTheme {
        Surface {
            PlainTopBar(
                containerColor = Color.Blue,
                titleColor = Color.White,
                fontSize = 24.sp,
                text = "タイトル",
            )
        }
    }
}