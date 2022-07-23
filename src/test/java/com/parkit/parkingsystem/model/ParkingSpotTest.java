package com.parkit.parkingsystem.model;

import com.parkit.parkingsystem.constants.ParkingType;
import org.junit.Test;
import org.mockito.Mock;

import static com.parkit.parkingsystem.constants.ParkingType.CAR;
import static org.junit.Assert.assertEquals;

public class ParkingSpotTest {

    @Mock
    private static ParkingType parkingType;

    @Test
    public void getIdTest(){

        ParkingSpot parkingSpotTest = new ParkingSpot(1, CAR, true);

        assertEquals(1, parkingSpotTest.getId());
    }

    @Test
    public void getParkingTypeTest() {

        ParkingSpot parkingSpotTest = new ParkingSpot(1, CAR, true);
        assertEquals(CAR, parkingSpotTest.getParkingType());

    }

}
