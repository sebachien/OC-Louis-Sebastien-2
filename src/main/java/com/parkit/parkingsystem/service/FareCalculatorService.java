package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }
        
        int inDate = ticket.getInTime().getDate();
        int inHour = ticket.getInTime().getHours();
        int inMinute = ticket.getInTime().getMinutes();
        int inSecond = ticket.getInTime().getSeconds();

        int outDate = ticket.getOutTime().getDate();
        int outHour = ticket.getOutTime().getHours();
        int outMinute = ticket.getOutTime().getMinutes();
        int outSecond = ticket.getOutTime().getSeconds();


        //TODO: Some tests are failing here. Need to check if this logic is correct
        int duration = (outDate - inDate)*24 + (outHour - inHour) + (((outMinute - inMinute)+((outSecond - inSecond)/60))/60);
        System.out.println("You have been parked : " + duration + "Hours");

        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR);
                break;
            }
            case BIKE: {
                ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }
}