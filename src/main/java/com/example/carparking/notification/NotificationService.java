package com.example.carparking.notification;

import com.example.carparking.event.VehicleEnteredEvent;
import com.example.carparking.event.VehicleExitedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @EventListener
    public void notifyOnVehicleEntry(VehicleEnteredEvent event) {
        System.out.println("Notification: " + event.vehicleNumber() + " entered the parking, entered at: " + event.entryTime());
    }

    @EventListener
    public void notifyOnVehicleExit(VehicleExitedEvent event) {
        System.out.println("Notification: " + event.vehicleNumber() + " has exited, at: " + event.exitTime());
    }
}
