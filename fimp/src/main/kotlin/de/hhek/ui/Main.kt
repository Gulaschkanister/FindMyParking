package de.hhek.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
// ...existing code...
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import de.hhek.XmlParser
import java.awt.Color as AwtColor
import org.knowm.xchart.XYChart
import org.knowm.xchart.XYChartBuilder
import org.knowm.xchart.style.markers.SeriesMarkers
import org.knowm.xchart.BitmapEncoder
import org.knowm.xchart.BitmapEncoder.BitmapFormat
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.foundation.Image
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.io.ByteArrayOutputStream
import org.jetbrains.skia.Image

@Composable
fun LineChartParkhausXChart(history: Map<String, List<Float>>) {
    val chart = XYChartBuilder().width(600).height(300).title("Parkhaus Statistik").xAxisTitle("Zeit").yAxisTitle("Freie Plätze").build()
    val colors = listOf(java.awt.Color.RED, java.awt.Color.BLUE, java.awt.Color.GREEN, java.awt.Color.MAGENTA, java.awt.Color.CYAN, java.awt.Color.YELLOW, java.awt.Color.GRAY, java.awt.Color.BLACK)
    val names = history.keys.toList()
    names.forEachIndexed { idx, name ->
        val values = history[name] ?: emptyList()
        val xData = (0 until values.size).map { it.toDouble() }.toDoubleArray()
        val yData = values.map { it.toDouble() }.toDoubleArray()
        val series = chart.addSeries(name, xData, yData)
        series.lineColor = colors[idx % colors.size]
        series.marker = SeriesMarkers.NONE
    }
    val baos = ByteArrayOutputStream()
    BitmapEncoder.saveBitmap(chart, baos, BitmapFormat.PNG)
    val imageBitmap = Image.makeFromEncoded(baos.toByteArray()).asImageBitmap()
    Image(bitmap = imageBitmap, contentDescription = "Parkhaus Statistik", modifier = Modifier.size(600.dp, 300.dp))
}

@Composable
@Preview
fun App() {
    var tracking by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var history by remember { mutableStateOf<MutableMap<String, MutableList<Float>>>(mutableMapOf()) }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text("Parkhaus-Statistik", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(24.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = {
                if (!tracking) {
                    tracking = true
                    coroutineScope.launch {
                        while (tracking) {
                            val garages = XmlParser.loadParkingGarages()
                            garages.forEach { garage ->
                                val list = history.getOrPut(garage.getName()) { mutableListOf() }
                                list.add(garage.getFreeSpaces().toFloat())
                            }
                            // Nur die letzten 50 Werte behalten
                            history = history.mapValues { (_, v) -> v.takeLast(50).toMutableList() }.toMutableMap()
                            delay(1000)
                        }
                    }
                }
            }, enabled = !tracking) {
                Text("Start")
            }
            Button(onClick = { tracking = false }, enabled = tracking) {
                Text("Stopp")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (history.isNotEmpty()) {
            LineChartParkhausXChart(history.mapValues { it.value.toList() })
        } else {
            Text("Noch keine Daten erfasst.", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "FindMyParking - Compose Desktop"
    ) {
        App()
    }
}
