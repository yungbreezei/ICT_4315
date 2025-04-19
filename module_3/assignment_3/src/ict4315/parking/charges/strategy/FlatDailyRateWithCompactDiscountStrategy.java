package ict4315.parking.charges.strategy;

import ict4315_assignment_1.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;

/**
 * A parking charge strategy that applies a flat daily rate with a 20% discount for compact cars.
 */
public class FlatDailyRateWithCompactDiscountStrategy implements ParkingChargeStrategy {

    private static final double DISCOUNT_RATE = 0.80;
    private static final LocalTime EARLY_CUTOFF = LocalTime.of(6, 0);
    private static final LocalTime LATE_CUTOFF = LocalTime.of(22, 0);
    private static final Money LATE_EARLY_SURCHARGE = new Money(2.50, "USD"); // Before 6 AM or after 10 PM

	
	/**
     * Calculates the charge based on a flat daily rate, 
     * applying a 20% discount to compact cars.
     */
    @Override
    public Money calculateCharge(ParkingPermit permit, LocalDateTime entryTime, 
    		LocalDateTime exitTime, Money baseRate) {
    	
        if (baseRate == null) {
            baseRate = new Money(0.00, "USD"); // default to zero if baseRate is null
        }
    	
        CarType type = permit.getCar().getType();

        long daysParked = ChronoUnit.DAYS.between(entryTime.toLocalDate(), 
        		exitTime.toLocalDate());
        
        if (daysParked == 0) {
        	daysParked = 1; // Minimum one-day charge
        }

        Money dailyRate = baseRate;
        
        // Apply compact discount
        if (type == CarType.COMPACT) {
            dailyRate = baseRate.times(DISCOUNT_RATE); // 20% off
        }
        
        // Base charge for days
        Money total = dailyRate.times(daysParked);
        
        // Surcharge: Early/Late
        LocalTime entry = entryTime.toLocalTime();
        if (entry.isBefore(EARLY_CUTOFF) || entry.isAfter(LATE_CUTOFF)) {
            total = total.add(LATE_EARLY_SURCHARGE);
        }
        
        // Apply the base rate for the daily charge
        return total;// Use the times method to multiply by days
    }
    
    /**
     * Checks if the given date is Graduation Day.
     * Hardcoded as May 10, 2025.
     */
    private boolean isGraduationDay(LocalDateTime date) {
        return date.getMonth() == Month.MAY && date.getDayOfMonth() == 10 && date.getYear() == 2025;
    }
}
