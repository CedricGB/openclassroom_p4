package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParkingServiceTest {

    @InjectMocks // Pour créer une instance ParkingService et y injecter les mocks ci-dessous
    ParkingService parkingService;

    @Mock
    private static InputReaderUtil inputReaderUtil;
    @Mock
    private static ParkingSpotDAO parkingSpotDAO;
    @Mock
    private static TicketDAO ticketDAO;

    Ticket ticket;

    @Test
    public void processIncomingCarTest_WithSpotAvailable() throws Exception{

        // Arrange - thenReturn


        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(parkingSpotDAO.getNextAvailableSlot(any(ParkingType.class))).thenReturn(1);
        when(ticketDAO.saveTicket(any(Ticket.class))).thenReturn(true);

        // Act
        parkingService.processIncomingVehicle();
        // Assert
        verify(parkingSpotDAO, times(1)).updateParking(any(ParkingSpot.class));
        verify(ticketDAO, times(1)).saveTicket(any(Ticket.class));

    }

    @Test
    public void processIncomingCarTest_WithNoSpotAvailable() throws Exception {
        try{
            when(inputReaderUtil.readSelection()).thenReturn(2);
            when(parkingSpotDAO.getNextAvailableSlot(any(ParkingType.class))).thenReturn(0);
            // Act

            parkingService.processIncomingVehicle();

            // Assert

            verify(parkingSpotDAO, times(0)).updateParking(any(ParkingSpot.class));
            verify(ticketDAO, times(0)).saveTicket(any(Ticket.class));
        } catch(Exception e){
            e.printStackTrace();
            throw  new RuntimeException("Failed to set up test mock objects");
        }


    }
    @Test
    public void processExitingCarTest(){
        try{
            // WHEN
            when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
            ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

            Ticket ticket = new Ticket();
            ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
            ticket.setParkingSpot(parkingSpot);
            ticket.setVehicleRegNumber("ABCDEF");

            when(ticketDAO.getTicket(anyString())).thenReturn(ticket);
            when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);
            when(parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);
            parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);


        } catch(Exception e){
            e.printStackTrace();
            throw  new RuntimeException("Failed to set up test mock objects");
        }


        parkingService.processExitingVehicle();
        verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
    }

    @Test
    public void processExitingBikeTest(){
        try{

            // WHEN
            when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");

            ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

            Ticket ticket = new Ticket();
            ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
            ticket.setParkingSpot(parkingSpot);
            ticket.setVehicleRegNumber("ABCDEF");

            when(ticketDAO.getTicket(anyString())).thenReturn(ticket);
            when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);
            when(parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);
            parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);


        } catch(Exception e){
            e.printStackTrace();
            throw  new RuntimeException("Failed to set up test mock objects");
        }

        //
        parkingService.processExitingVehicle();
        verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
    }

    @Test
    public void processExitingWithWrongPlateNumber(){

        try{
            when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");


            ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);
            ticket = new Ticket();
            ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
            ticket.setOutTime(new Date(System.currentTimeMillis()));
            ticket.setVehicleRegNumber("ABCDEF");
            ticket.setId(1);
            ticket.setParkingSpot(parkingSpot);


            // Arrange
            when(ticketDAO.getTicket(anyString())).thenReturn(ticket);
            when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(false);



            parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);


        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Fail to setup mock");
        }

        // Act

        parkingService.processExitingVehicle();

        // Assert
        verify(parkingSpotDAO, times(0)).updateParking(any(ParkingSpot.class));
    }



}
