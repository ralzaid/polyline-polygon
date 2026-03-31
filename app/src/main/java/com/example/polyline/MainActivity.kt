package com.example.polyline

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Q3Screen()
            }
        }
    }
}

@Composable
fun Q3Screen() {
    val trailPoints = listOf(
        LatLng(42.3550, -71.0650),
        LatLng(42.3560, -71.0680),
        LatLng(42.3575, -71.0710),
        LatLng(42.3590, -71.0730)
    )

    val parkPoints = listOf(
        LatLng(42.3610, -71.0700),
        LatLng(42.3620, -71.0740),
        LatLng(42.3590, -71.0760),
        LatLng(42.3580, -71.0710)
    )

    var message by remember { mutableStateOf("Tap the trail or park") }

    var trailPink by remember { mutableStateOf(true) }
    var parkPink by remember { mutableStateOf(true) }

    var trailWidth by remember { mutableFloatStateOf(12f) }
    var parkWidth by remember { mutableFloatStateOf(6f) }

    val trailColor = if (trailPink) Color(0xFFD81B60) else Color.Blue
    val parkFillColor = if (parkPink) Color(0x66FF69B4) else Color(0x6600FF00)
    val parkStrokeColor = if (parkPink) Color(0xFFFF69B4) else Color.Green

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(42.3595, -71.0715), 14f)
    }

    Column(modifier = Modifier.fillMaxSize()) {

        Text(
            text = message,
            color = Color(0xFFD81B60),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            Button(
                onClick = { trailPink = !trailPink },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text("Trail Color")
            }

            Button(
                onClick = { parkPink = !parkPink }
            ) {
                Text("Park Color")
            }
        }

        Text(
            text = "Trail Width: ${trailWidth.toInt()}",
            modifier = Modifier.padding(start = 16.dp, top = 12.dp)
        )

        Slider(
            value = trailWidth,
            onValueChange = { trailWidth = it },
            valueRange = 4f..20f,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Text(
            text = "Park Border Width: ${parkWidth.toInt()}",
            modifier = Modifier.padding(start = 16.dp, top = 12.dp)
        )

        Slider(
            value = parkWidth,
            onValueChange = { parkWidth = it },
            valueRange = 2f..15f,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            Polyline(
                points = trailPoints,
                color = trailColor,
                width = trailWidth,
                clickable = true,
                jointType = JointType.ROUND,
                onClick = {
                    message = "Hiking trail clicked"
                }
            )

            Polygon(
                points = parkPoints,
                fillColor = parkFillColor,
                strokeColor = parkStrokeColor,
                strokeWidth = parkWidth,
                clickable = true,
                onClick = {
                    message = "Park area clicked"
                }
            )

            Marker(
                state = MarkerState(position = trailPoints.first()),
                title = "Trail Start"
            )
        }
    }
}