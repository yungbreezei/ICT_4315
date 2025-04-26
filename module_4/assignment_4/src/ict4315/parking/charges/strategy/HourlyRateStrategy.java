package ict4315.parking.charges.strategy;

import ict4315_assignment_1.ParkingPermit;
import ict4315_assignment_1.CarType;
import ict4315_assignment_1.Money;

import java.time.LocalDateTime;
import java.time.Duration;

public class HourlyRateStrategy implements ParkingChargeStrategy {
	
	// Discount multiplier applied for compact vehicles (20% off)
    private static final double COMPACT_DISCOUNT = 0.8; 

    /**
     * Calculates the parking charge based on the total hours parked and applies
     * a discount if the vehicle is compact
     */
    
    @Override
    public Money calculateCharge(ParkingPermit permit, LocalDateTime entryTime, 
    		LocalDateTime exitTime, Money baseRate) {
    	
    	// Calculate total hours, rounding up if there are any leftover minutes
        long hours = Duration.between(entryTime, exitTime).toHours();
        
        // Round up if there's any remaining minutes
        if (Duration.between(entryTime, exitTime).toMinutes() % 60 != 0) {
            hours++;
        }

        // Calculate the base charge for the total hours parked
        Money total = baseRate.times(hours);
        
        // Apply 20% discount for compact cars
        if (permit.getCar().getType() == CarType.COMPACT) {
            total = total.times(COMPACT_DISCOUNT);
        }

        return total;   
    }

    // for HourlyRateStrategy
	@Override
	public boolean isHourlyBased() {
		return true;
	}
}
