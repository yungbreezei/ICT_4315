package ict4315.parking.charges.strategy;

import java.time.LocalDateTime;
import java.time.Month;

import ict4315_assignment_1.Money;
import ict4315_assignment_1.ParkingPermit;

/**
 * A parking charge strategy decorator that adds a fixed surcharge to the base
 * parking fee if the entry date is on the university's graduation day
 */

public class GraduationSurchargeStrategy implements ParkingChargeStrategy {
   
	// The surcharge applied on graduation day
	private static final Money GRADUATION_SURCHARGE = new Money(2.00, "USD");
	
	// Wrapped parking charge strategy to apply the base calculation
    private final ParkingChargeStrategy graduationStrategy;

    /**
     * Constructs a new GraduationSurchargeStrategythat wraps another
     * ParkingChargeStrategy.
     */ 
    public GraduationSurchargeStrategy(ParkingChargeStrategy graduationStrategy) {
        this.graduationStrategy = graduationStrategy;
    }
    
    /**
     * Calculates the parking charge using the wrapped strategy and adds a graduation
     * surcharge if the entry date is May 10, 2025
     */ 
    @Override
    public Money calculateCharge(ParkingPermit permit, LocalDateTime entry, 
    		LocalDateTime exit, Money baseRate) {
    	
        Money charge = graduationStrategy.calculateCharge(permit, entry, exit, baseRate);
        if (isGraduationDay(entry)) {
            charge = charge.add(GRADUATION_SURCHARGE);
        }
        // Total parking charge, including any applicable surcharge
        return charge;
    }

    /**
     * Determines whether the specified date is the university's graduation day
     */
    private boolean isGraduationDay(LocalDateTime date) {
    	// check the date & return if the date is May 10th
        return date.getMonth() == Month.MAY && date.getDayOfMonth() == 10 && 
        		date.getYear() == 2025;
    }
    
    // for HourlyRateStrategy
	@Override
	public boolean isHourlyBased() {
		return true;
	}
}

