package de.hhek.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import de.hhek.service.ParkingService

@Composable
@Preview
fun App() {
    // Java Service wird hier verwendet
    val parkingService = remember { ParkingService() }
    var availableSpots by remember { mutableStateOf(parkingService.availableSpots) }
    var statusMessage by remember { mutableStateOf(parkingService.statusMessage) }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "FindMyParking",
                    style = MaterialTheme.typography.headlineLarge
                )

                Spacer(modifier = Modifier.height(32.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Verfügbare Parkplätze",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "$availableSpots",
                            style = MaterialTheme.typography.displayLarge,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = statusMessage,
                            style = MaterialTheme.typography.bodyMedium,
                            color = when {
                                availableSpots == 0 -> MaterialTheme.colorScheme.error
                                availableSpots < 10 -> MaterialTheme.colorScheme.tertiary
                                else -> MaterialTheme.colorScheme.onSurface
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = {
                            if (parkingService.parkCar()) {
                                availableSpots = parkingService.availableSpots
                                statusMessage = parkingService.statusMessage
                            }
                        },
                        enabled = availableSpots > 0
                    ) {
                        Text("Auto parken")
                    }

                    Button(
                        onClick = {
                            parkingService.removeCar()
                            availableSpots = parkingService.availableSpots
                            statusMessage = parkingService.statusMessage
                        }
                    ) {
                        Text("Auto entfernen")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    onClick = {
                        parkingService.resetSpots()
                        availableSpots = parkingService.availableSpots
                        statusMessage = parkingService.statusMessage
                    }
                ) {
                    Text("Zurücksetzen")
                }
            }
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
