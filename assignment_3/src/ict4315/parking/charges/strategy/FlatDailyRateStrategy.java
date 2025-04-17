package ict4315.parking.charges.strategy;

import ict4315_assignment_1.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


/**
 * Calculates the total parking charge for a given parking session.
 */

public class FlatDailyRateStrategy implements ParkingChargeStrategy{
 
    @Override
    public Money calculateCharge(ParkingPermit permit, LocalDateTime entryTime,
                                  LocalDateTime exitTime, Money baseRate) {
    	// Calculate duration in days
        long daysParked = ChronoUnit.DAYS.between(entryTime.toLocalDate(), 
        		exitTime.toLocalDate());
        if (daysParked == 0) daysParked = 1;

     // Apply the base rate for the daily charge
        return baseRate.times(daysParked);
    }
}
