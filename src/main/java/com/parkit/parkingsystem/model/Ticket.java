package com.parkit.parkingsystem.model;

import java.util.Date;

public class Ticket {
    private int id;
    private ParkingSpot parkingSpot;
    private String vehicleRegNumber;
    private double price;
    private Date inTime;
    private Date outTime;


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public String getVehicleRegNumber() {
        return vehicleRegNumber;
    }

    public void setVehicleRegNumber(String vehicleRegNumber) {
        this.vehicleRegNumber = vehicleRegNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * getter incoming time
     * @return incoming time
     */
    public Date getInTime() {
        final Date inTime = this.inTime;
        return inTime;
    }

    /**
     * setter incoming time
     * @param inTime incoming time
     */
    public void setInTime(Date inTime) {
        this.inTime = inTime == null ? null : (Date) inTime.clone();
    }

    /**
     * getter outcoming time
     * @return outcoming time
     */
    public Date getOutTime() {
        final Date outTime = this.outTime;
        return outTime;
    }

    /**
     * setter outcoming time
     * @param outTime outcoming time
     */
    public void setOutTime(Date outTime) {
        this.outTime = outTime == null ? null : (Date) outTime.clone();
    }





}
