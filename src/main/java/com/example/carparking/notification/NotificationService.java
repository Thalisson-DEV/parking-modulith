package com.example.carparking.notification;

import com.example.carparking.event.PaymentSuccessfulEvent;
import com.example.carparking.event.VehicleEnteredEvent;
import com.example.carparking.event.VehicleExitedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class NotificationService {

    private static final Logger LOGGER = Logger.getLogger(NotificationService.class.getName());

    @EventListener
    public void notifyOnVehicleEntry(VehicleEnteredEvent event) {
        LOGGER.info("Notification: " + event.vehicleNumber() + " entered the parking, entered at: " + event.entryTime());
    }

    @EventListener
    public void notifyOnVehicleExit(VehicleExitedEvent event) {
        LOGGER.info("Notification: " + event.vehicleNumber() + " exited the parking, exited at: " + event.exitTime());
    }

    @EventListener
    public void notifyOnPaymentSuccessful(PaymentSuccessfulEvent event) {
        LOGGER.info("Payment successful for vehicle: " + event.vehicleNumber() + ", amount: " + event.amount());
    }
}
