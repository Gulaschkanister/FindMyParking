package de.hhek.tobi;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParkingGarage {
    private int id;
    private String name;
    private int maxCapacity;
    private int freeSpaces;
    private int status;
    private LocalDateTime timeStamp;
    private int tendency;

    @Override
    public String toString() {
        return "ParkingGarage [id=" + id + ", name=" + name + ", maxCapacity=" + maxCapacity + ", freeSpaces="
                + freeSpaces + ", status=" + status + ", timeStamp=" + timeStamp + ", tendency=" + tendency + "]";
    }
}
