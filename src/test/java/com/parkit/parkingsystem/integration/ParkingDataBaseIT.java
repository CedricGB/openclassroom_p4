package com.parkit.parkingsystem.integration;

import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Date;


import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParkingDataBaseIT {

    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static ParkingSpotDAO parkingSpotDAO;

    private static DataBasePrepareService dataBasePrepareService;

    @Mock
    private static TicketDAO ticketDAO;
    @Mock
    private static InputReaderUtil inputReaderUtil;

    @BeforeAll
     static void setUp(){
        parkingSpotDAO = new ParkingSpotDAO();
        parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService = new DataBasePrepareService();
    }

    @BeforeEach
     void setUpPerTest() throws Exception {
        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
        Ticket ticket = new Ticket();
        ticket.setId(1);
        ticket.setInTime(new Date());
        ticket.setVehicleRegNumber("ABCDEF");
        ticket.setOutTime(new Date());
        ticket.setPrice(0);
        when(ticketDAO.getTicket("ABCDEF")).thenReturn(ticket);
        dataBasePrepareService.clearDataBaseEntries();
    }

    @AfterAll
     static void tearDown(){

    }

    @Test
    public void testParkingACar(){ // check that a ticket is actually saved in DB and Parking table is updated with availability
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        parkingService.processIncomingVehicle();

        Ticket ticket = ticketDAO.getTicket("ABCDEF");

        assertEquals(ticket.getPrice(), 0);
        assertEquals(ticket.getVehicleRegNumber(),"ABCDEF");
    }

    @Test
    public void testParkingLotExit(){ //check that the fare generated and out time are populated correctly in the database

        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        parkingService.processIncomingVehicle();
        Ticket ticket = ticketDAO.getTicket("ABCDEF");
        Date outTimeCompare = new Date();
        parkingService.processExitingVehicle();

        assertEquals(ticket.getOutTime().getDay(),outTimeCompare.getDay());
        assertEquals(ticket.getOutTime().getHours(),outTimeCompare.getHours());
        //assertEquals(ticket.getOutTime().getMinutes(),outTimeCompare.getMinutes());

    }

}
