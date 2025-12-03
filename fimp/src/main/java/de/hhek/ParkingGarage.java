package de.hhek;

import java.time.LocalDateTime;

public class ParkingGarage {
    private int id;
    private String name;
    private int maxCapacity;
    private int freeSpaces;
    private int status;
    private LocalDateTime timeStamp;
    private int tendency;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getMaxCapacity() {
        return maxCapacity;
    }
    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
    public int getFreeSpaces() {
        return freeSpaces;
    }
    public void setFreeSpaces(int freeSpaces) {
        this.freeSpaces = freeSpaces;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
    public int getTendency() {
        return tendency;
    }
    public void setTendency(int tendency) {
        this.tendency = tendency;
    }

    @Override
    public String toString() {
        return "ParkingGarage [id=" + id + ", name=" + name + ", maxCapacity=" + maxCapacity + ", freeSpaces="
                + freeSpaces + ", status=" + status + ", timeStamp=" + timeStamp + ", tendency=" + tendency + "]";
    }
}
