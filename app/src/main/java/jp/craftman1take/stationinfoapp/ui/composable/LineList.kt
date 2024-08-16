package jp.craftman1take.stationinfoapp.ui.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.craftman1take.stationinfoapp.data.Entity
import jp.craftman1take.stationinfoapp.data.sampleLineList
import jp.craftman1take.stationinfoapp.ui.composable.common.SimpleTextList

@Composable
fun LineList(
    modifier: Modifier = Modifier,
    lineList: List<Entity.Line> = emptyList(),
    onClick: (Entity.Line) -> Unit = {},
) {
    SimpleTextList(
        modifier = modifier,
        lineHeight = 72.dp,
        fontSize = 20.sp,
        textList = lineList.map { it.name },
        onClick = { onClick(lineList[it]) },
    )
}

@Preview(showBackground = true)
@Composable
fun LineListPreview() {
    LineList(
        modifier = Modifier.fillMaxSize(),
        lineList = sampleLineList,
    )
}