package jp.craftman1take.stationinfoapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.os.bundleOf
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import jp.craftman1take.stationinfoapp.data.Entity
import jp.craftman1take.stationinfoapp.ui.composable.StationCard
import jp.craftman1take.stationinfoapp.ui.composable.rememberMapViewWithLifecycle
import jp.craftman1take.stationinfoapp.ui.theme.StationInfoAppTheme

class StationMapActivity : ComponentActivity() {
    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val station = checkNotNull(intent.getParcelableExtra<Entity.Station>(KEY_STATION))

        setContent {
            StationInfoAppTheme {
                Surface {
                    val mapView = rememberMapViewWithLifecycle()

                    MainContent(
                        modifier = Modifier.fillMaxSize(),
                        station = station,
                        mapView = mapView,
                    ) {
                        map = it

                        val position = LatLng(station.latitude, station.longitude)
                        val zoom = 16.0f

                        it.addMarker(MarkerOptions().position(position).title("Target Position"))
                        it.moveCamera(CameraUpdateFactory.newCameraPosition(
                            CameraPosition.Builder().apply {
                                target(position)
                                zoom(zoom)
                            }.build()
                        ))
                    }
                }
            }
        }
    }

    companion object {
        private const val KEY_STATION = "KEY_STATION"

        fun launcherIntent(context: Context, station: Entity.Station) = Intent(context, StationMapActivity::class.java).apply {
            putExtras(bundleOf(KEY_STATION to station))
        }
    }
}

@Composable
fun MainContent(modifier: Modifier = Modifier, station: Entity.Station, mapView: MapView, onMapReady: (GoogleMap) -> Unit) {
    ConstraintLayout(modifier = modifier) {
        val (cardRef, mapRef) = createRefs()
        val guideline = createGuidelineFromTop(0.3f)

        StationCard(
            modifier = Modifier.constrainAs(cardRef) {
                width = Dimension.matchParent
                height = Dimension.wrapContent
                top.linkTo(parent.top)
                bottom.linkTo(guideline)
            }.padding(8.dp),
            station = station,
        )

        AndroidView(
            modifier = Modifier.constrainAs(mapRef) {
                width = Dimension.matchParent
                height = Dimension.fillToConstraints
                top.linkTo(guideline)
                bottom.linkTo(parent.bottom)
            },
            factory = { mapView },
        ) { view ->
            view.getMapAsync {
                onMapReady(it)
            }
        }
    }
}