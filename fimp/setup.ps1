# FindMyParking - Erstes Setup Script für Windows
# Dieses Script überprüft und bereitet alles für die erste Verwendung vor

Write-Host "==================================================" -ForegroundColor Cyan
Write-Host "   FindMyParking - Projekt Setup" -ForegroundColor Cyan
Write-Host "==================================================" -ForegroundColor Cyan
Write-Host ""

# Prüfe Java Installation
Write-Host "1. Prüfe Java Installation..." -ForegroundColor Yellow
try {
    $javaVersion = java -version 2>&1 | Select-String "version" | ForEach-Object { $_.Line }
    Write-Host "   ✓ Java gefunden: $javaVersion" -ForegroundColor Green
    
    # Prüfe Java Version (mindestens 17)
    $versionMatch = $javaVersion -match '\"(\d+)\.?'
    if ($versionMatch) {
        $majorVersion = [int]$matches[1]
        if ($majorVersion -lt 17) {
            Write-Host "   ⚠ Warnung: Java 17 oder höher wird empfohlen (gefunden: $majorVersion)" -ForegroundColor Yellow
        }
    }
} catch {
    Write-Host "   ✗ Java nicht gefunden!" -ForegroundColor Red
    Write-Host "   Bitte installiere Java 17 oder höher von:" -ForegroundColor Red
    Write-Host "   https://adoptium.net/" -ForegroundColor White
    exit 1
}

Write-Host ""

# Prüfe ob Gradle Wrapper vorhanden ist
Write-Host "2. Prüfe Gradle Wrapper..." -ForegroundColor Yellow
if (Test-Path ".\gradlew.bat") {
    Write-Host "   ✓ Gradle Wrapper gefunden" -ForegroundColor Green
} else {
    Write-Host "   ✗ Gradle Wrapper nicht gefunden!" -ForegroundColor Red
    exit 1
}

Write-Host ""

# Prüfe ob build.gradle.kts vorhanden ist
Write-Host "3. Prüfe Build-Konfiguration..." -ForegroundColor Yellow
if (Test-Path ".\build.gradle.kts") {
    Write-Host "   ✓ build.gradle.kts gefunden" -ForegroundColor Green
} else {
    Write-Host "   ✗ build.gradle.kts nicht gefunden!" -ForegroundColor Red
    exit 1
}

Write-Host ""

# Zeige Projektstruktur
Write-Host "4. Prüfe Projektstruktur..." -ForegroundColor Yellow
$hasJavaSource = Test-Path ".\src\main\java"
$hasKotlinSource = Test-Path ".\src\main\kotlin"

if ($hasJavaSource) {
    Write-Host "   ✓ Java Quellcode gefunden (src/main/java)" -ForegroundColor Green
} else {
    Write-Host "   ⚠ Keine Java Quellcode gefunden" -ForegroundColor Yellow
}

if ($hasKotlinSource) {
    Write-Host "   ✓ Kotlin Quellcode gefunden (src/main/kotlin)" -ForegroundColor Green
} else {
    Write-Host "   ⚠ Keine Kotlin Quellcode gefunden" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "==================================================" -ForegroundColor Cyan
Write-Host "   Setup abgeschlossen!" -ForegroundColor Green
Write-Host "==================================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Nächste Schritte:" -ForegroundColor Cyan
Write-Host ""
Write-Host "  1. Projekt bauen:" -ForegroundColor White
Write-Host "     .\gradlew.bat build" -ForegroundColor Gray
Write-Host ""
Write-Host "  2. Anwendung ausführen:" -ForegroundColor White
Write-Host "     .\gradlew.bat run" -ForegroundColor Gray
Write-Host ""
Write-Host "  3. Alle verfügbaren Tasks anzeigen:" -ForegroundColor White
Write-Host "     .\gradlew.bat tasks" -ForegroundColor Gray
Write-Host ""
Write-Host "Hinweis: Beim ersten Aufruf lädt Gradle automatisch" -ForegroundColor Yellow
Write-Host "alle benötigten Dependencies herunter. Das kann" -ForegroundColor Yellow
Write-Host "einige Minuten dauern." -ForegroundColor Yellow
Write-Host ""

# Frage ob User direkt bauen möchte
$response = Read-Host "Möchtest du das Projekt jetzt bauen? (j/n)"
if ($response -eq "j" -or $response -eq "J" -or $response -eq "ja") {
    Write-Host ""
    Write-Host "Starte Build-Prozess..." -ForegroundColor Cyan
    Write-Host ""
    & .\gradlew.bat build
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host ""
        Write-Host "==================================================" -ForegroundColor Green
        Write-Host "   Build erfolgreich!" -ForegroundColor Green
        Write-Host "==================================================" -ForegroundColor Green
        Write-Host ""
        Write-Host "Du kannst die Anwendung jetzt starten mit:" -ForegroundColor White
        Write-Host ".\gradlew.bat run" -ForegroundColor Gray
        Write-Host ""
    } else {
        Write-Host ""
        Write-Host "Build fehlgeschlagen. Prüfe die Fehlermeldungen oben." -ForegroundColor Red
        Write-Host ""
    }
} else {
    Write-Host ""
    Write-Host "Setup abgeschlossen. Du kannst jetzt mit der Entwicklung beginnen!" -ForegroundColor Green
    Write-Host ""
}
