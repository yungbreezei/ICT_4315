+package ict4315.parking.charges.strategy;

import ict4315_assignment_1.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;


/**
 * A ParkingChargeStrategy implementation that provides free parking
 * on weekends (Saturday and Sunday).
 */
public class WeekendFreeStrategy implements ParkingChargeStrategy {
	
    // The wrapped strategy to delegate to when not a weekend.
    private final ParkingChargeStrategy wrappedStrategy;
  
    /**
     * Constructs a WeekendFreeStrategy that wraps another charge strategy.
     */
    public WeekendFreeStrategy(ParkingChargeStrategy wrappedStrategy) {
        this.wrappedStrategy = wrappedStrategy;
    }

    /**
     * Calculates the parking charge based on the entry day
     * If the entry is on a weekend (Saturday or Sunday), the charge is $0.00
     * Otherwise, the wrapped strategy is used to determine the charge.
     */ 
    @Override
    public Money calculateCharge(ParkingPermit permit, LocalDateTime entryTime,
                                 LocalDateTime exitTime, Money baseRate) {
        DayOfWeek day = entryTime.getDayOfWeek();
        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            return new Money(0.00, "USD");
        }

        return wrappedStrategy.calculateCharge(permit, entryTime, exitTime, baseRate);
    }

	@Override
	public boolean isHourlyBased() {
		return true;
	}
}

