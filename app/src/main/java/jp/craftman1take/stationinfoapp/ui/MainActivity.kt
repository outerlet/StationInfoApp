package jp.craftman1take.stationinfoapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import dagger.hilt.android.AndroidEntryPoint
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
                    val title = viewModel.title.observeAsState(initial = "")
                    val areaList = viewModel.areaList.observeAsState(initial = emptyList())

                    MainContent(
                        modifier = Modifier.fillMaxSize(),
                        title = title.value,
                        textList = areaList.value.map { it.name },
                    )
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
    title: String = "",
    textList: List<String> = emptyList(),
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
                        text = title,
                    )
                },
            )
        },
        modifier = modifier,
    ) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val textRef = createRef()

            LazyColumn(
                modifier = Modifier.constrainAs(textRef) {
                    width = Dimension.matchParent
                    height = Dimension.fillToConstraints
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(textList, key = { it }) {
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .height(72.dp)
                            .padding(horizontal = 12.dp),
                    ) {
                        Text(
                            fontSize = 20.sp,
                            text = it,
                        )
                    }
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
            title = "Sample",
            textList = (1..10).map { "Number is %02d".format(it) },
        )
    }
}