package com.parkit.parkingsystem.DAO;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.DBConstantsTest;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class ParkingSpotDAOTest {

    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static ParkingSpotDAO parkingSpotDAO;
    private static DataBasePrepareService dataBasePrepareService;
    private ParkingSpot parkingSpot;
    @Mock
    private static TicketDAO ticketDAO;

    @BeforeAll
    private static void setUp() {
        parkingSpotDAO = new ParkingSpotDAO();
        dataBasePrepareService = new DataBasePrepareService();
    }

    @BeforeEach
    private void setUpPerTest() {
        parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService.clearDataBaseEntries();
    }

    @Test
    public void getNextAvailableSlotForCar() {
        assertEquals(1, parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR));
    }

    @Test
    public void getNextAvailableSlotForBike() {
        assertEquals(4, parkingSpotDAO.getNextAvailableSlot(ParkingType.BIKE));
    }

    @Test

    public void noAvailableSlotForCar() throws SQLException, ClassNotFoundException {

        try (Connection con = dataBaseTestConfig.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DBConstantsTest.SET_AVAILABLE_FALSE_CAR);
            ps.executeUpdate();
            assertEquals(0, parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR));
            assertEquals(4, parkingSpotDAO.getNextAvailableSlot(ParkingType.BIKE));

        }


    }

    @Test

    public void noAvailableSlotForBike() throws SQLException, ClassNotFoundException {

        try (Connection con = dataBaseTestConfig.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DBConstantsTest.SET_AVAILABLE_FALSE_BIKE);
            ps.executeUpdate();
            assertEquals(0, parkingSpotDAO.getNextAvailableSlot(ParkingType.BIKE));
            assertEquals(1, parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR));

        }


    }

    @Test

    public void updateParkingTestCar() throws SQLException, ClassNotFoundException {

        parkingSpot = new ParkingSpot(1, ParkingType.CAR, true);

        assertTrue(parkingSpotDAO.updateParking(parkingSpot));

    }


    @Test

    public void updateParkingTestBike() throws SQLException, ClassNotFoundException {

        parkingSpot = new ParkingSpot(4, ParkingType.BIKE, true);
        assertTrue(parkingSpotDAO.updateParking(parkingSpot));

    }



}