package de.hhek.service;

/**
 * Beispiel-Service in Java für Geschäftslogik
 */
public class ParkingService {
    
    private int availableSpots;
    
    public ParkingService() {
        this.availableSpots = 100;
    }
    
    public int getAvailableSpots() {
        return availableSpots;
    }
    
    public boolean parkCar() {
        if (availableSpots > 0) {
            availableSpots--;
            return true;
        }
        return false;
    }
    
    public void removeCar() {
        availableSpots++;
    }
    
    public void resetSpots() {
        availableSpots = 100;
    }
    
    public String getStatusMessage() {
        if (availableSpots == 0) {
            return "Parking ist voll!";
        } else if (availableSpots < 10) {
            return "Nur noch wenige Plätze verfügbar!";
        } else {
            return "Genügend Parkplätze verfügbar";
        }
    }
}
