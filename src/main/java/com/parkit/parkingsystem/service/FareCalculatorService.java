package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }
        
		double inDate = ticket.getInTime().getDate();
		double inHour = ticket.getInTime().getHours();
		double inMinute = ticket.getInTime().getMinutes();
		double inSecond = ticket.getInTime().getSeconds();

		double outDate = ticket.getOutTime().getDate();
		double outHour = ticket.getOutTime().getHours();
        double outMinute = ticket.getOutTime().getMinutes();
        double outSecond = ticket.getOutTime().getSeconds();


        //TODO: Some tests are failing here. Need to check if this logic is correct
        double duration = (outDate - inDate)*24 + (outHour - inHour) + (((outMinute - inMinute)+((outSecond - inSecond)/60))/60);
        System.out.println("You have been parked : " + duration + " Hours");

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