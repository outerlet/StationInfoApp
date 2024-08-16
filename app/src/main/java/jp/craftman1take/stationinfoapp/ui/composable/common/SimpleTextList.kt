package jp.craftman1take.stationinfoapp.ui.composable.common

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import jp.craftman1take.stationinfoapp.ui.theme.StationInfoAppTheme

@Composable
fun SimpleTextList(
    modifier: Modifier = Modifier,
    lineHeight: Dp = Dp.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    textAlignment: Alignment.Horizontal = Alignment.Start,
    textList: List<String> = emptyList(),
    onClick: (Int) -> Unit = {},
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(textList, key = { index, item -> "${index}_$item" }) { index, item ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(lineHeight)
                    .clickable { onClick(index) },
                contentAlignment = when (textAlignment) {
                    Alignment.Start -> Alignment.CenterStart
                    Alignment.CenterHorizontally -> Alignment.Center
                    Alignment.End -> Alignment.CenterEnd
                    else -> throw IllegalArgumentException("textAlignment must be Start or CenterHorizontally or End")
                },
            ) {
                Text(
                    fontSize = fontSize,
                    text = item,
                )
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SimpleTextListPreview() {
    StationInfoAppTheme {
        Surface {
            SimpleTextList(
                modifier = Modifier.fillMaxSize(),
                textList = listOf(
                    "三重県",
                    "滋賀県",
                    "京都府",
                    "大阪府",
                    "兵庫県",
                    "奈良県",
                    "和歌山県",
                ),
            )
        }
    }
}