# FindMyParking - Gradle mit Compose Desktop

Das Projekt wurde erfolgreich von Maven auf Gradle umgestellt!

## Wichtige Befehle

### Projekt kompilieren:
```powershell
.\gradlew.bat build
```

### Anwendung ausführen:
```powershell
.\gradlew.bat run
```

### Natives Installationspaket erstellen:
```powershell
# Windows Installer (MSI)
.\gradlew.bat packageMsi

# Windows Executable (EXE)
.\gradlew.bat packageExe
```

### Tests ausführen:
```powershell
.\gradlew.bat test
```

### Projekt aufräumen:
```powershell
.\gradlew.bat clean
```

## Projektstruktur

```
fimp/
├── build.gradle.kts           # Gradle Build-Konfiguration
├── settings.gradle.kts        # Gradle Settings
├── gradle.properties          # Gradle Eigenschaften
├── gradlew.bat               # Gradle Wrapper für Windows
├── gradle/
│   └── wrapper/              # Gradle Wrapper Dateien
├── src/
│   └── main/
│       ├── java/             # Java Quellcode (Geschäftslogik)
│       │   └── de/hhek/
│       │       └── service/
│       │           └── ParkingService.java
│       └── kotlin/           # Kotlin Quellcode (UI)
│           └── de/hhek/ui/
│               └── Main.kt
```

## Entwicklung

### Java Code
Deine Geschäftslogik bleibt in **Java** in `src/main/java/`:
- `ParkingService.java` - Beispiel-Service

### Kotlin UI Code
Die Compose Desktop UI ist in **Kotlin** in `src/main/kotlin/`:
- `Main.kt` - UI und App-Einstiegspunkt

Kotlin kann problemlos Java-Klassen verwenden, daher funktioniert die Interoperabilität einwandfrei!

## IDE Integration

### IntelliJ IDEA
- Öffne das Projekt als Gradle-Projekt
- IntelliJ erkennt die Struktur automatisch
- Nutze die "Run" Konfiguration für schnelles Testen

### VS Code
- Installiere die "Gradle for Java" Extension
- Der Gradle Wrapper wird automatisch erkannt

## Vorteile von Gradle

✅ Volle Compose Desktop Unterstützung
✅ Schnellere Builds
✅ Bessere Kotlin-Integration
✅ Native Installer generieren
✅ Moderne Build-Tool Funktionen

## Tipps

- Nutze `.\gradlew.bat --info` für detaillierte Build-Informationen
- Nutze `.\gradlew.bat tasks` um alle verfügbaren Tasks zu sehen
- Der erste Build dauert länger (Dependencies werden heruntergeladen)
