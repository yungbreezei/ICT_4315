package ict4315.parking.charges.strategy;

import ict4315_assignment_1.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class WeekendFreeStrategy implements ParkingChargeStrategy {
	
    private final ParkingChargeStrategy wrappedStrategy;

    public WeekendFreeStrategy(ParkingChargeStrategy wrappedStrategy) {
        this.wrappedStrategy = wrappedStrategy;
    }

    @Override
    public Money calculateCharge(ParkingPermit permit, LocalDateTime entryTime,
                                 LocalDateTime exitTime, Money baseRate) {
        DayOfWeek day = entryTime.getDayOfWeek();
        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            return new Money(0.00, "USD");
        }

        return wrappedStrategy.calculateCharge(permit, entryTime, exitTime, baseRate);
    }
}

