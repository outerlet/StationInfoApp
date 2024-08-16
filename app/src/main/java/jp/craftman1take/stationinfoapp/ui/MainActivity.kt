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
import jp.craftman1take.stationinfoapp.ui.theme.StationInfoAppTheme
import jp.craftman1take.stationinfoapp.viewmodel.MainViewModel
import timber.log.Timber
import kotlin.reflect.typeOf

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
                        Timber.d("${it.name} is clicked.")
                    }
                }
            }
        }

        viewModel.requestArea()
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
        if (presentList != null) {
            when {
                presentList.areaList != null -> {
                    AreaList(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
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