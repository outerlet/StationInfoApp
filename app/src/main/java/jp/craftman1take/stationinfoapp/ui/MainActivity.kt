package jp.craftman1take.stationinfoapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dagger.hilt.android.AndroidEntryPoint
import jp.craftman1take.stationinfoapp.data.Entity
import jp.craftman1take.stationinfoapp.data.PresentList
import jp.craftman1take.stationinfoapp.data.sampleAreaList
import jp.craftman1take.stationinfoapp.ui.composable.AreaList
import jp.craftman1take.stationinfoapp.ui.composable.LineList
import jp.craftman1take.stationinfoapp.ui.composable.PrefectureList
import jp.craftman1take.stationinfoapp.ui.composable.StationList
import jp.craftman1take.stationinfoapp.ui.composable.common.PlainTopBar
import jp.craftman1take.stationinfoapp.ui.theme.StationInfoAppTheme
import jp.craftman1take.stationinfoapp.viewmodel.MainViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            StationInfoAppTheme {
                Surface {
                    val isLoading = viewModel.isLoading.observeAsState(initial = false)
                    val presentList = viewModel.presentList.observeAsState(initial = PresentList())

                    MainContent(
                        modifier = Modifier.fillMaxSize(),
                        isLoading = isLoading.value,
                        presentList = presentList.value,
                    ) {
                        when (it) {
                            is Entity.Area -> viewModel.requestPrefectures(it)
                            is Entity.Prefecture -> viewModel.requestLines(it)
                            is Entity.Line -> viewModel.requestStations(it)
                            is Entity.Station -> startActivity(StationMapActivity.launcherIntent(this, it))
                        }
                    }
                }
            }
        }

        viewModel.requestAreas()
    }

    @Deprecated("This method has been deprecated at Android 13")
    @Suppress("Deprecation")
    override fun onBackPressed() {
        // FIXME Compose Navigationのバックスタックでこのハンドリングを巧くやりたい
        if (!viewModel.previous()) {
            super.onBackPressed()
        }
    }
}

@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    presentList: PresentList = PresentList(),
    onClick: (Entity) -> Unit = {}
) {
    Scaffold(
        topBar = {
            PlainTopBar(
                containerColor = Color.Blue,
                titleColor = Color.White,
                fontSize = 24.sp,
                text = presentList.title,
            )
        },
        modifier = modifier,
    ) { innerPadding ->
        ComposePresentList(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding() + 8.dp,
                    bottom = innerPadding.calculateBottomPadding() + 8.dp,
                    start = innerPadding.calculateStartPadding(layoutDirection = LayoutDirection.Ltr) + 12.dp,
                    end = innerPadding.calculateEndPadding(layoutDirection = LayoutDirection.Rtl) + 12.dp,
                ),
            presentList = presentList,
            onClick = onClick,
        )

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(color = Color(0xCC000000)),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun ComposePresentList(
    modifier: Modifier = Modifier,
    presentList: PresentList,
    onClick: (Entity) -> Unit,
) {
    when {
        presentList.stationList != null -> {
            StationList(
                modifier = modifier,
                stationList = presentList.stationList,
                onClick = { onClick(it) },
            )
        }
        presentList.lineList != null -> {
            LineList(
                modifier = modifier,
                lineList = presentList.lineList,
                onClick = { onClick(it) },
            )
        }
        presentList.prefectureList != null -> {
            PrefectureList(
                modifier = modifier,
                prefectureList = presentList.prefectureList,
                onClick = { onClick(it) },
            )
        }
        presentList.areaList != null -> {
            AreaList(
                modifier = modifier,
                areaList = presentList.areaList,
                onClick = { onClick(it) },
            )
        }
        else -> Unit
    }
}

@Preview(showBackground = true)
@Composable
fun MainContentPreview() {
    StationInfoAppTheme {
        MainContent(
            modifier = Modifier.fillMaxSize(),
            presentList = PresentList(
                areaList = sampleAreaList,
            ),
        )
    }
}