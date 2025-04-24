package ict4315.parking.charges.factory;

import ict4315.parking.charges.strategy.FlatDailyRateStrategy;
import ict4315.parking.charges.strategy.FlatDailyRateWithCompactDiscountStrategy;
import ict4315.parking.charges.strategy.GraduationSurchargeStrategy;
import ict4315.parking.charges.strategy.HourlyRateStrategy;
import ict4315.parking.charges.strategy.ParkingChargeStrategy;
import ict4315.parking.charges.strategy.WeekendFreeStrategy;
import ict4315_assignment_1.ParkingLot;

public class DefaultParkingChargeStrategyFactory implements ParkingChargeStrategyFactory{

    @Override
    public ParkingChargeStrategy getStrategyFor(ParkingLot parkingLot) {
        // Select strategy based on parking lot attributes, such as name or type
        
        // Using the name or some attribute of the parking lot
        String lotName = parkingLot.getName().toLowerCase();
        
        if (lotName.contains("weekend")) {
            return new WeekendFreeStrategy(null);
            
        } else if (lotName.contains("hourly")) {
            return new HourlyRateStrategy();
            
        } else if (lotName.contains("graduation")) {
            return new GraduationSurchargeStrategy(null);
            
        } else if (lotName.contains("compact")) {
            return new FlatDailyRateWithCompactDiscountStrategy();
            
        } else {
            return new FlatDailyRateStrategy();
        }
    }
}
