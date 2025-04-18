package ict4315.parking.charges.strategy;

import ict4315_assignment_1.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * A parking charge strategy that applies a flat daily rate with a 20% discount for compact cars.
 */
public class FlatDailyRateWithCompactDiscountStrategy implements ParkingChargeStrategy {

    /**
     * Calculates the charge based on a flat daily rate, 
     * applying a 20% discount to compact cars.
     */
    @Override
    public Money calculateCharge(ParkingPermit permit, LocalDateTime entryTime, 
    		LocalDateTime exitTime, Money baseRate) {
    	
        Car car = permit.getCar();
        CarType type = car.getType();

        long daysParked = ChronoUnit.DAYS.between(entryTime.toLocalDate(), 
        		exitTime.toLocalDate());
        
        if (daysParked == 0) {
        	daysParked = 1; // Minimum one-day charge
        }

        Money dailyRate = baseRate;
        if (type == CarType.COMPACT) {
            dailyRate = baseRate.times(0.8); // 20% discount
        }

        return dailyRate.times(daysParked);
    }
}
