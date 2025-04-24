package ict4315.parking.charges.strategy.tests;

import static org.junit.jupiter.api.Assertions.*;

import ict4315.parking.charges.strategy.ParkingChargeStrategy;
import ict4315.parking.charges.strategy.HourlyRateStrategy;
import ict4315.parking.charges.strategy.WeekendFreeStrategy;
import ict4315_assignment_1.ParkingLot;
import ict4315.parking.charges.strategy.FlatDailyRateStrategy;
import ict4315.parking.charges.strategy.FlatDailyRateWithCompactDiscountStrategy;
import ict4315.parking.charges.strategy.GraduationSurchargeStrategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ict4315.parking.charges.factory.DefaultParkingChargeStrategyFactory;

class DefaultParkingChargeStrategyFactoryTest {

	private DefaultParkingChargeStrategyFactory strategyFactory;

    @BeforeEach
    void setUp() {
        strategyFactory = new DefaultParkingChargeStrategyFactory();
    }

    @Test
    void testGetStrategyForHourlyLot() {
        ParkingLot hourlyLot = new ParkingLot("LOT1", "Hourly Lot", null, new HourlyRateStrategy(), 7.5, 100);
        ParkingChargeStrategy strategy = strategyFactory.getStrategyFor(hourlyLot);
        assertTrue(strategy instanceof HourlyRateStrategy, "Expected HourlyRateStrategy for Hourly Lot");
    }

    @Test
    void testGetStrategyForWeekendFreeLot() {
        ParkingLot weekendFreeLot = new ParkingLot("LOT2", "Weekend Free Lot", null, new WeekendFreeStrategy(null), 10.0, 200);
        ParkingChargeStrategy strategy = strategyFactory.getStrategyFor(weekendFreeLot);
        assertTrue(strategy instanceof WeekendFreeStrategy, "Expected WeekendFreeStrategy for Weekend Free Lot");
    }

    @Test
    void testGetStrategyForGraduationSurchargeLot() {
        ParkingLot graduationLot = new ParkingLot("LOT3", "Graduation Lot", null, new GraduationSurchargeStrategy(null), 12.0, 150);
        ParkingChargeStrategy strategy = strategyFactory.getStrategyFor(graduationLot);
        assertTrue(strategy instanceof GraduationSurchargeStrategy, "Expected GraduationSurchargeStrategy for Graduation Lot");
    }

    @Test
    void testGetStrategyForFlatDailyRateLot() {
        ParkingLot flatDailyLot = new ParkingLot("LOT4", "Flat Daily Rate Lot", null, new FlatDailyRateStrategy(), 15.0, 300);
        ParkingChargeStrategy strategy = strategyFactory.getStrategyFor(flatDailyLot);
        assertTrue(strategy instanceof FlatDailyRateStrategy, "Expected FlatDailyRateStrategy for Flat Daily Rate Lot");
    }

    @Test
    void testGetStrategyForFlatDailyRateWithCompactDiscountLot() {
        ParkingLot flatDailyWithDiscountLot = new ParkingLot("LOT5", "Flat Daily Rate with Compact Discount", null, new FlatDailyRateWithCompactDiscountStrategy(), 10.0, 250);
        ParkingChargeStrategy strategy = strategyFactory.getStrategyFor(flatDailyWithDiscountLot);
        assertTrue(strategy instanceof FlatDailyRateWithCompactDiscountStrategy, "Expected FlatDailyRateWithCompactDiscountStrategy for Flat Daily Rate with Discount Lot");
    }

}
