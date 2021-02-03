package com.parkit.parkingsystem.integration;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

import java.util.Date;

@ExtendWith(MockitoExtension.class)
public class ParkingDataBaseIT {

	private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
	private static ParkingSpotDAO parkingSpotDAO;
	private static TicketDAO ticketDAO;
	private static DataBasePrepareService dataBasePrepareService;

	@Mock
	private static InputReaderUtil inputReaderUtil;
	private static FareCalculatorService fareCalculatorService = new FareCalculatorService();

	@BeforeAll
	private static void setUp() throws Exception {
		parkingSpotDAO = new ParkingSpotDAO();
		parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
		ticketDAO = new TicketDAO();
		ticketDAO.dataBaseConfig = dataBaseTestConfig;
		dataBasePrepareService = new DataBasePrepareService();
	}

	@BeforeEach
	private void setUpPerTest() throws Exception {
		when(inputReaderUtil.readSelection()).thenReturn(1);
		when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
		dataBasePrepareService.clearDataBaseEntries();
	}

	@AfterAll
	private static void tearDown() {

	}

	/*@Test
	public void testParkingACar() {
		ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		parkingService.processIncomingVehicle();
		// TODO: check that a ticket is actualy saved in DB and Parking table is updated
		// with availability
		Ticket ticket = ticketDAO.getTicket("ABCDEF");
		assertNotEquals(null, ticket);

		int nextSlot = parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR);
		assertEquals(2, nextSlot);
	}

	@Test
	public void testParkingLotExit() {
		testParkingACar();
		ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		parkingService.processExitingVehicle();
		// TODO: check that the fare generated and out time are populated correctly in
		// the database
		Ticket ticket = ticketDAO.getTicket("ABCDEF");
		assertNotEquals(null, ticket.getPrice());

		assertNotEquals(null, ticket.getOutTime());
	}

	*/
	@Test
	public void testParkingLotExitTwoTime() {
		Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - ( 3 * 60 * 60 * 1000) );
        
		Date inTime2 = new Date();
        inTime2.setTime( System.currentTimeMillis() - ( 2 * 60 * 60 * 1000) );
        
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        
		parkingService.processIncomingVehicle();
        Ticket ticket = ticketDAO.getTicket("ABCDEF");
        ticket.setInTime(inTime);
        ticketDAO.updateInTime(ticket);
        parkingService.processExitingVehicle();
        
        parkingService.processIncomingVehicle();
        Ticket ticket2 = ticketDAO.getTicket("ABCDEF");
        ticket2.setInTime(inTime2);
        ticketDAO.updateInTime(ticket2);
        parkingService.processExitingVehicle();
		
	
        /*
		testParkingACar();
		Ticket ticket = ticketDAO.getTicket("ABCDEF");
        ticket.setInTime(inTime);
		fareCalculatorService.calculateFare(ticket);
		double beforeDiscountPrice = ticket.getPrice();
		double afterDiscountPrice = beforeDiscountPrice; //(beforeDiscountPrice - ((beforeDiscountPrice * 5) / 100));
		parkingService.processExitingVehicle();
		testParkingLotExit();

		assertEquals(afterDiscountPrice, ticket.getPrice());¨*/

	}
}
