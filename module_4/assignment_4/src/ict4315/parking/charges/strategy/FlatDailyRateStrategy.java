package ict4315.parking.charges.strategy;

import ict4315_assignment_1.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;


/**
 * Calculates the total parking charge for a given parking session.
 * Considers day of week, time of entry, and special events.
 */
public class FlatDailyRateStrategy implements ParkingChargeStrategy{
	
    private static final double DAILY_RATE = 10.00;
    private static final LocalTime EARLY_CUTOFF = LocalTime.of(6, 0);
    private static final LocalTime LATE_CUTOFF = LocalTime.of(22, 0);
    private static final Money LATE_EARLY_SURCHARGE = new Money(2.50, "USD"); // Before 6 AM or after 10 PM +$2.50
 
    @Override
    public Money calculateCharge(ParkingPermit permit, LocalDateTime entryTime,
                                  LocalDateTime exitTime, Money baseRate) {
    	
        if (baseRate == null) {
            baseRate = new Money(0.00, "USD"); // default to zero if baseRate is null
        }
    	
    	// Calculate duration in days
        long daysParked = ChronoUnit.DAYS.between(entryTime.toLocalDate(), 
        		exitTime.toLocalDate());
        
        // Minimum of 1 day charge
        if (daysParked == 0) {
        	daysParked = 1;
        }
        
        // Calculate the total charge based on the daily rate
        double totalCharge = daysParked * DAILY_RATE;
        
        Money dailyRate = baseRate;

        // Factor 2: Early or late entry surcharge
        LocalTime entryTimeOnly = entryTime.toLocalTime();
        if (entryTimeOnly.isBefore(EARLY_CUTOFF) || entryTimeOnly.isAfter(LATE_CUTOFF)) {
            dailyRate = dailyRate.add(LATE_EARLY_SURCHARGE);
        }

        
        // Apply the base rate for the daily charge
        return dailyRate.times(daysParked); // Use the times method to multiply by days

    }

    // for HourlyRateStrategy
	@Override
	public boolean isHourlyBased() {
		return true;
	}
    
}

