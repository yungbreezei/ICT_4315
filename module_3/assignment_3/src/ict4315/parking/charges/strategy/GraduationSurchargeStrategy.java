package ict4315.parking.charges.strategy;

import java.time.LocalDateTime;
import java.time.Month;

import ict4315_assignment_1.Money;
import ict4315_assignment_1.ParkingPermit;

public class GraduationSurchargeStrategy implements ParkingChargeStrategy {
    
	private static final Money GRADUATION_SURCHARGE = new Money(2.00, "USD");
    private final ParkingChargeStrategy graduationStrategy;

    public GraduationSurchargeStrategy(ParkingChargeStrategy graduationStrategy) {
        this.graduationStrategy = graduationStrategy;
    }
    
    @Override
    public Money calculateCharge(ParkingPermit permit, LocalDateTime entry, 
    		LocalDateTime exit, Money baseRate) {
    	
        Money charge = graduationStrategy.calculateCharge(permit, entry, exit, baseRate);
        if (isGraduationDay(entry)) {
            charge = charge.add(GRADUATION_SURCHARGE);
        }
        return charge;
    }

    private boolean isGraduationDay(LocalDateTime date) {
        return date.getMonth() == Month.MAY && date.getDayOfMonth() == 10 && 
        		date.getYear() == 2025;
    }
}

