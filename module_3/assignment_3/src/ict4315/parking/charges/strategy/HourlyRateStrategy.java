package ict4315.parking.charges.strategy;

import ict4315_assignment_1.ParkingPermit;
import ict4315_assignment_1.CarType;
import ict4315_assignment_1.Money;

import java.time.LocalDateTime;
import java.time.Duration;

public class HourlyRateStrategy implements ParkingChargeStrategy {
	
    private static final double COMPACT_DISCOUNT = 0.95; // 5% off

    @Override
    public Money calculateCharge(ParkingPermit permit, LocalDateTime entryTime, LocalDateTime exitTime, Money baseRate) {
        long hours = Duration.between(entryTime, exitTime).toHours();
        if (Duration.between(entryTime, exitTime).toMinutes() % 60 != 0) {
            hours++; // Round up partial hour
        }

        Money total = baseRate.times(hours);

        if (permit.getCar().getType() == CarType.COMPACT) {
            total = total.times(COMPACT_DISCOUNT);
        }

        return total;
    }
}
