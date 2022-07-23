package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.Ticket;
import org.apache.commons.lang3.ObjectUtils;

public class FareCalculatorService {

    private TicketDAO ticketDAO = new TicketDAO();

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }
        //getTime();
        long inHour = ticket.getInTime().getTime() ;
        long outHour = ticket.getOutTime().getTime();
        double multiplicatorReduction = 1;
        double duration = (outHour - inHour) / 60.0 / 1000 / 60;

        if(ticketDAO.alreadyInDB(ticket.getVehicleRegNumber())) {
                multiplicatorReduction = 0.95;

        } else {
            multiplicatorReduction = 1;
        }

       if (duration <= 0.5 ){
            multiplicatorReduction = 0.00;}


        // Test si le vehicle number a déjà été dans la base de donnée, si oui , on fait 5%, sinon, on continue comme avant

        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {

                ticket.setPrice((duration * Fare.CAR_RATE_PER_HOUR) *  multiplicatorReduction);
                break;
            }
            case BIKE: {
                ticket.setPrice((duration * Fare.BIKE_RATE_PER_HOUR) * multiplicatorReduction);
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }
}