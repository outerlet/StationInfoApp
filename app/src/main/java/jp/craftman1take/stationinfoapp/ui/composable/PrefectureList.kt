package jp.craftman1take.stationinfoapp.ui.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.craftman1take.stationinfoapp.data.Entity
import jp.craftman1take.stationinfoapp.data.samplePrefectureList
import jp.craftman1take.stationinfoapp.ui.composable.common.SimpleTextList

@Composable
fun PrefectureList(
    modifier: Modifier = Modifier,
    prefectureList: List<Entity.Prefecture> = emptyList(),
    onClick: (Entity.Prefecture) -> Unit = {},
) {
    SimpleTextList(
        modifier = modifier,
        lineHeight = 72.dp,
        fontSize = 20.sp,
        textList = prefectureList.map { it.name },
        onClick = { onClick(prefectureList[it]) },
    )
}

@Preview(showBackground = true)
@Composable
fun PrefectureListPreview() {
    PrefectureList(
        modifier = Modifier.fillMaxSize(),
        prefectureList = samplePrefectureList,
    )
}