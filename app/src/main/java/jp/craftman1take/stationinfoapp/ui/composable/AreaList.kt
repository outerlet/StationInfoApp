package jp.craftman1take.stationinfoapp.ui.composable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.craftman1take.stationinfoapp.data.Entity
import jp.craftman1take.stationinfoapp.data.sampleAreaList

@Composable
fun AreaList(
    modifier: Modifier = Modifier,
    areaList: List<Entity.Area> = emptyList(),
    onClick: (Entity.Area) -> Unit = {},
) {
    SimpleTextList(
        modifier = modifier,
        lineHeight = 72.dp,
        fontSize = 20.sp,
        textList = areaList.map { it.name },
        onClick = { onClick(areaList[it]) },
    )
}

@Preview(showBackground = true)
@Composable
fun AreaListPreview() {
    AreaList(
        modifier = Modifier.fillMaxSize(),
        areaList = sampleAreaList,
    )
}