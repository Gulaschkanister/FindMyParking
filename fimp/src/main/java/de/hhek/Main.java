package de.hhek;

import java.util.List;

import de.hhek.tobi.ParkingGarage;
import de.hhek.tobi.XmlParser;

public class Main {
    public static void main(String[] args) {
        List<ParkingGarage> garages = XmlParser.loadParkingGarages();
        System.out.println("Gefundene Parkhäuser: " + garages.size());
        for (ParkingGarage garage : garages) {
            System.out.println(garage.toString());
        }
    }
}