#!/bin/bash

# FindMyParking - Erstes Setup Script für Linux/macOS
# Dieses Script überprüft und bereitet alles für die erste Verwendung vor

echo "=================================================="
echo "   FindMyParking - Projekt Setup"
echo "=================================================="
echo ""

# Farben für Output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
CYAN='\033[0;36m'
NC='\033[0m' # No Color

# Prüfe Java Installation
echo -e "${YELLOW}1. Prüfe Java Installation...${NC}"
if command -v java &> /dev/null; then
    JAVA_VERSION=$(java -version 2>&1 | head -n 1)
    echo -e "${GREEN}   ✓ Java gefunden: $JAVA_VERSION${NC}"
    
    # Prüfe Java Version (mindestens 17)
    VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | cut -d'.' -f1)
    if [ "$VERSION" -lt 17 ]; then
        echo -e "${YELLOW}   ⚠ Warnung: Java 17 oder höher wird empfohlen (gefunden: $VERSION)${NC}"
    fi
else
    echo -e "${RED}   ✗ Java nicht gefunden!${NC}"
    echo -e "${RED}   Bitte installiere Java 17 oder höher von:${NC}"
    echo -e "${NC}   https://adoptium.net/${NC}"
    exit 1
fi

echo ""

# Prüfe ob Gradle Wrapper vorhanden ist
echo -e "${YELLOW}2. Prüfe Gradle Wrapper...${NC}"
if [ -f "./gradlew" ]; then
    echo -e "${GREEN}   ✓ Gradle Wrapper gefunden${NC}"
    
    # Mache gradlew ausführbar falls nötig
    if [ ! -x "./gradlew" ]; then
        echo -e "${YELLOW}   → Mache gradlew ausführbar...${NC}"
        chmod +x ./gradlew
        echo -e "${GREEN}   ✓ Berechtigungen gesetzt${NC}"
    fi
else
    echo -e "${RED}   ✗ Gradle Wrapper nicht gefunden!${NC}"
    exit 1
fi

echo ""

# Prüfe ob build.gradle.kts vorhanden ist
echo -e "${YELLOW}3. Prüfe Build-Konfiguration...${NC}"
if [ -f "./build.gradle.kts" ]; then
    echo -e "${GREEN}   ✓ build.gradle.kts gefunden${NC}"
else
    echo -e "${RED}   ✗ build.gradle.kts nicht gefunden!${NC}"
    exit 1
fi

echo ""

# Zeige Projektstruktur
echo -e "${YELLOW}4. Prüfe Projektstruktur...${NC}"
if [ -d "./src/main/java" ]; then
    echo -e "${GREEN}   ✓ Java Quellcode gefunden (src/main/java)${NC}"
else
    echo -e "${YELLOW}   ⚠ Keine Java Quellcode gefunden${NC}"
fi

if [ -d "./src/main/kotlin" ]; then
    echo -e "${GREEN}   ✓ Kotlin Quellcode gefunden (src/main/kotlin)${NC}"
else
    echo -e "${YELLOW}   ⚠ Keine Kotlin Quellcode gefunden${NC}"
fi

echo ""
echo -e "${CYAN}==================================================${NC}"
echo -e "${GREEN}   Setup abgeschlossen!${NC}"
echo -e "${CYAN}==================================================${NC}"
echo ""
echo -e "${CYAN}Nächste Schritte:${NC}"
echo ""
echo -e "${NC}  1. Projekt bauen:${NC}"
echo -e "     ${YELLOW}./gradlew build${NC}"
echo ""
echo -e "${NC}  2. Anwendung ausführen:${NC}"
echo -e "     ${YELLOW}./gradlew run${NC}"
echo ""
echo -e "${NC}  3. Alle verfügbaren Tasks anzeigen:${NC}"
echo -e "     ${YELLOW}./gradlew tasks${NC}"
echo ""
echo -e "${YELLOW}Hinweis: Beim ersten Aufruf lädt Gradle automatisch${NC}"
echo -e "${YELLOW}alle benötigten Dependencies herunter. Das kann${NC}"
echo -e "${YELLOW}einige Minuten dauern.${NC}"
echo ""

# Frage ob User direkt bauen möchte
read -p "Möchtest du das Projekt jetzt bauen? (j/n) " -n 1 -r
echo ""
if [[ $REPLY =~ ^[JjYy]$ ]]; then
    echo ""
    echo -e "${CYAN}Starte Build-Prozess...${NC}"
    echo ""
    ./gradlew build
    
    if [ $? -eq 0 ]; then
        echo ""
        echo -e "${GREEN}==================================================${NC}"
        echo -e "${GREEN}   Build erfolgreich!${NC}"
        echo -e "${GREEN}==================================================${NC}"
        echo ""
        echo -e "${NC}Du kannst die Anwendung jetzt starten mit:${NC}"
        echo -e "${YELLOW}./gradlew run${NC}"
        echo ""
    else
        echo ""
        echo -e "${RED}Build fehlgeschlagen. Prüfe die Fehlermeldungen oben.${NC}"
        echo ""
    fi
else
    echo ""
    echo -e "${GREEN}Setup abgeschlossen. Du kannst jetzt mit der Entwicklung beginnen!${NC}"
    echo ""
fi
