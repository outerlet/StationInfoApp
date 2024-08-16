package jp.craftman1take.stationinfoapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
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
                    val presentList = viewModel.presentList.observeAsState()

                    MainContent(
                        modifier = Modifier.fillMaxSize(),
                        presentList = presentList.value,
                    ) {
                        when (it) {
                            is Entity.Area -> viewModel.requestPrefectures(it)
                            is Entity.Prefecture -> viewModel.requestLines(it)
                            is Entity.Line -> viewModel.requestStations(it)
                            is Entity.Station -> startActivity(Intent(this, StationMapActivity::class.java))
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    presentList: PresentList? = null,
    onClick: (Entity) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue,
                    titleContentColor = Color.White,
                ),
                title = {
                    Text(
                        fontSize = 24.sp,
                        text = presentList?.title ?: "",
                    )
                },
            )
        },
        modifier = modifier,
    ) { innerPadding ->
        val listModifier = Modifier
            .fillMaxSize()
            .padding(
                top = innerPadding.calculateTopPadding() + 8.dp,
                bottom = innerPadding.calculateBottomPadding(),
                start = innerPadding.calculateStartPadding(layoutDirection = LayoutDirection.Ltr) + 12.dp,
                end = innerPadding.calculateEndPadding(layoutDirection = LayoutDirection.Rtl) + 12.dp,
            )

        when {
            presentList == null -> Unit
            presentList.stationList != null -> {
                StationList(
                    modifier = listModifier,
                    stationList = presentList.stationList,
                    onClick = { onClick(it) },
                )
            }
            presentList.lineList != null -> {
                LineList(
                    modifier = listModifier,
                    lineList = presentList.lineList,
                    onClick = { onClick(it) },
                )
            }
            presentList.prefectureList != null -> {
                PrefectureList(
                    modifier = listModifier,
                    prefectureList = presentList.prefectureList,
                    onClick = { onClick(it) },
                )
            }
            presentList.areaList != null -> {
                AreaList(
                    modifier = listModifier,
                    areaList = presentList.areaList,
                    onClick = { onClick(it) },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StationInfoAppTheme {
        MainContent(
            modifier = Modifier.fillMaxSize(),
            presentList = PresentList(
                areaList = sampleAreaList,
            ),
        )
    }
}