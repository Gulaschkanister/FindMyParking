package de.hhek;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

public class XmlParser {
    public static List<ParkingGarage> loadParkingGarages() {
        List<ParkingGarage> garages = new ArrayList<>();
        try {
            Document doc = loadXml();
            if (doc.getFirstChild().getNodeName().equals("parkhaeuser")) {
                NodeList garageNodes = doc.getFirstChild().getChildNodes();
                for (int i = 0; i < garageNodes.getLength(); i++) {
                    if (garageNodes.item(i).getNodeName().equals("parkhaus")) {
                        NodeList garageDetails = garageNodes.item(i).getChildNodes();
                        List<Element> elements = new ArrayList<>();

                        for (int j = 0; j < garageDetails.getLength(); j++) {
                            Node node = garageDetails.item(j);
                            if (node.getNodeType() == Node.ELEMENT_NODE) {
                                elements.add((Element) node);
                            }
                        }

                        ParkingGarage garage = new ParkingGarage();
                        garage.setId(Integer.parseInt(elements.get(0).getTextContent()));
                        garage.setName(elements.get(1).getTextContent());
                        garage.setMaxCapacity(Integer.parseInt(elements.get(2).getTextContent()));
                        garage.setFreeSpaces(Integer.parseInt(elements.get(3).getTextContent()));
                        garage.setStatus(Integer.parseInt(elements.get(4).getTextContent()));
                        garage.setTimeStamp(getTimestampFromString(elements.get(5).getTextContent()));
                        garage.setTendency(Integer.parseInt(elements.get(6).getTextContent()));
                        garages.add(garage);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return garages;
    }

    private static Document loadXml() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.bcp-bonn.de/stellplatz/bcpext.xml"))
                .GET()
                .build();

        HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Fehler beim Laden der XML-Datei: HTTP " + response.statusCode());
        }
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
        DocumentBuilder db = dbf.newDocumentBuilder();

        try (ByteArrayInputStream input = new ByteArrayInputStream(response.body())) {
            Document doc = db.parse(input);
            doc.getDocumentElement().normalize();
            return doc;
        }
    }

    private static LocalDateTime getTimestampFromString(String timestamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return LocalDateTime.parse(timestamp, formatter);
    }
}
