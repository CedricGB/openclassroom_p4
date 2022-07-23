package com.parkit.parkingsystem.DAO;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Date;
import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TicketDAOTest {

    public DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static DataBasePrepareService dataBasePrepareService;
    private static ParkingSpot parkingSpot;
    private static ParkingSpotDAO parkingSpotDAO;
    private static Ticket ticketTest;
    private static InputReaderUtil inputReaderUtil;
    private TicketDAO ticketDAO;

    @BeforeAll
    private static void setUp() {
        parkingSpotDAO = new ParkingSpotDAO();
        dataBasePrepareService = new DataBasePrepareService();



    }

    @BeforeEach
    private void setUpPerTest() {
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService.clearDataBaseEntries();

    }

    @Test

    public void getTicketTest() throws Exception {
        String test = "ABCDEF";
        ticketTest = new Ticket();
        ticketTest.setId(1);
        ticketTest.setParkingSpot(new ParkingSpot(1, ParkingType.CAR, false));
        ticketTest.setVehicleRegNumber(test);
        ticketTest.setInTime(new Date(System.currentTimeMillis() - (60*60*1000)));
        ticketDAO.saveTicket(ticketTest);

        assertEquals(ticketTest.getId(), ticketDAO.getTicket(test).getId());
    }






}
