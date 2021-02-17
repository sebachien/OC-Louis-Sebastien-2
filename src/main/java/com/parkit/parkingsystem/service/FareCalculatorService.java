package com.parkit.parkingsystem.service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

	public void calculateFare(Ticket ticket) {
		
		if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
			throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
		}

		double diff = ticket.getOutTime().getTime() - ticket.getInTime().getTime();
		double diffHours = (((diff / 1000) / 60) / 60);
		
		DecimalFormat df = new DecimalFormat("########.00");
		String str = df.format(diffHours);
		diffHours = Double.parseDouble(str.replace(',', '.'));
		
		// TODO: Some tests are failing here. Need to check if this logic is correct

		System.out.println("You have been parked : " + diffHours + " Hours");
		
		//30 min free
		if (diffHours > 0.5) {
		diffHours -= 0.5;
		System.out.println("You have been parked more than 30 min, you have pay juste for : " + diffHours + " Hours");
		} 
		

		switch (ticket.getParkingSpot().getParkingType()) {
		case CAR: {
			ticket.setPrice(diffHours * Fare.CAR_RATE_PER_HOUR);
			break;
		}
		case BIKE: {
			ticket.setPrice(diffHours * Fare.BIKE_RATE_PER_HOUR);
			break;
		}
		default:
			throw new IllegalArgumentException("Unkown Parking Type");
		}
	}
}