package jp.craftman1take.stationinfoapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import androidx.compose.ui.unit.sp
import dagger.hilt.android.AndroidEntryPoint
import jp.craftman1take.stationinfoapp.data.Entity
import jp.craftman1take.stationinfoapp.data.PresentList
import jp.craftman1take.stationinfoapp.data.sampleAreaList
import jp.craftman1take.stationinfoapp.ui.composable.AreaList
import jp.craftman1take.stationinfoapp.ui.composable.PrefectureList
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
                            is Entity.Area -> viewModel.requestPrefecture(it)
                            else -> Unit
                        }
                    }
                }
            }
        }

        viewModel.requestArea()
    }

    override fun onBackPressed() {
        if (!viewModel.previousList()) {
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
            .padding(innerPadding)

        if (presentList != null) {
            when {
                presentList.prefectureList != null -> {
                    PrefectureList(
                        modifier = listModifier,
                        prefectureList = presentList.prefectureList,
                        onClick = { onClick(it) }
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