package ict4315.parking.charges.strategy.tests;

import ict4315_assignment_1.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ict4315.parking.charges.strategy.FlatDailyRateStrategy;
import ict4315.parking.charges.strategy.FlatDailyRateWithCompactDiscountStrategy;
import ict4315.parking.charges.strategy.GraduationSurchargeStrategy;
import ict4315.parking.charges.strategy.ParkingChargeStrategy;
import ict4315.parking.charges.strategy.WeekendFreeStrategy;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;


class FlatDailyRateWithCompactDiscountStrategyTest {
	
    private FlatDailyRateWithCompactDiscountStrategy strategyCompact;
    private FlatDailyRateStrategy strategyFlat;
    private WeekendFreeStrategy weekendFreeStrategy;
    private Address address;
    private Customer customer;
    private Money baseRate;
    
    private static final double DAILY_RATE = 10.00;
    private static final LocalTime EARLY_CUTOFF = LocalTime.of(6, 0);
    private static final LocalTime LATE_CUTOFF = LocalTime.of(22, 0);
    private static final Money EVENT_SURCHARGE = new Money(2.00, "USD");
    private static final Money LATE_EARLY_SURCHARGE = new Money(2.50, "USD");
    private static final Money WEEKEND_SURCHARGE = new Money(3.00, "USD");
    
    
    /**
     * Initializes common test objects before each test.
     */
    
    @BeforeEach
    void setUp() {
    	strategyCompact = new FlatDailyRateWithCompactDiscountStrategy();
    	strategyFlat = new FlatDailyRateStrategy();
    	
    	// Base strategy: applies flat daily rate + compact discount + graduation surcharge
    	ParkingChargeStrategy base = new FlatDailyRateWithCompactDiscountStrategy();

    	// Decorate base with weekend-free logic & graduation logic
    	ParkingChargeStrategy wrapped = new WeekendFreeStrategy(new GraduationSurchargeStrategy(base));
    	    	
        address = new Address("123 Main St", "Apt 101", "City", "ST", "12345");
        customer = new Customer("1", "Test", "User", "555-555-5555", address);
        baseRate = new Money(10.0, "USD");
        
    	ParkingLot lotA = new ParkingLot("Lot A", "South" , address, strategyCompact, 10.0, 100);
    	lotA.setChargeStrategy(wrapped);
    }

    /**
     * Verifies that a compact car receives a 20% discount on the base daily rate.
     */
    @Test
    void testCompactCarGetsDiscount() {
        Car car = new Car(CarType.COMPACT, "ABC123", customer);
        ParkingPermit permit = new ParkingPermit("P001", car, LocalDateTime.now());

        LocalDateTime entry = LocalDateTime.of(2025, 4, 15, 8, 0);
        LocalDateTime exit = entry.plusDays(1);

        Money result = strategyCompact.calculateCharge(permit, entry, exit, baseRate);

        // Apply 20% discount to the daily rate
        double expectedCharge = DAILY_RATE * 0.80;  // 20% discount
        assertEquals(new Money(expectedCharge, "USD"), result);
    }
    
    /**
     * Verifies that an SUV receives no discount and is charged full base rate per day.
     */
    @Test
    void testSuvNoDiscount() {
        Car car = new Car(CarType.SUV, "XYZ789", customer);
        ParkingPermit permit = new ParkingPermit("P002", car, LocalDateTime.now());

        LocalDateTime entry = LocalDateTime.of(2025, 4, 10, 10, 0);
        LocalDateTime exit = entry.plusDays(2);

        Money result = strategyFlat.calculateCharge(permit, entry, exit, baseRate);

        // No discount for SUV, apply flat rate for 2 days
        double expectedCharge = DAILY_RATE * 2;
        assertEquals(new Money(expectedCharge, "USD"), result);
    }
    
    /**
     * Verifies that a compact car parked on a weekend receives a surcharge, then discounted.
     */
    @Test
    void testCompactOnWeekendWithDiscount() {
        Car car = new Car(CarType.COMPACT, "WEEKEND", customer);
        ParkingPermit permit = new ParkingPermit("P003", car, LocalDateTime.now());

        LocalDateTime entry = LocalDateTime.of(2025, 4, 19, 9, 0); // Saturday
        LocalDateTime exit = entry.plusHours(4);

        Money result = strategyCompact.calculateCharge(permit, entry, exit, baseRate);

        // Apply weekend surcharge and discount
        double expectedCharge = (DAILY_RATE * 0.8) + WEEKEND_SURCHARGE.getAmount();
        assertEquals(new Money(expectedCharge, "USD"), result);
    }
    /**
     * Verifies that a compact car parked on graduation day is 
     * charged event surcharge and discounted.
     */
    @Test
    void testCompactOnGraduationDay() {
        Car car = new Car(CarType.COMPACT, "GRAD", customer);
        ParkingPermit permit = new ParkingPermit("P004", car, LocalDateTime.now());

        // Setup wrapped strategy: base + graduation logic
        ParkingChargeStrategy base = new FlatDailyRateWithCompactDiscountStrategy();
        ParkingChargeStrategy strategy = new GraduationSurchargeStrategy(base);

        LocalDateTime entry = LocalDateTime.of(2025, 5, 10, 10, 0); // May 10, Graduation Day
        LocalDateTime exit = entry.plusHours(3);

        Money result = strategy.calculateCharge(permit, entry, exit, new Money(10.00, "USD"));
        
        Money expected = new Money(15.00, "USD");
        assertEquals(expected, result);
    }
    /**
     * Verifies surcharge for early entry and that compact discount is applied.
     */
    @Test
    void testCompactEarlyEntry() {
        Car car = new Car(CarType.COMPACT, "EARLY", customer);
        ParkingPermit permit = new ParkingPermit("P005", car, LocalDateTime.now());

        LocalDateTime entry = LocalDateTime.of(2025, 4, 16, 5, 30); // Before 6 AM
        LocalDateTime exit = entry.plusHours(2);

        Money result = strategyCompact.calculateCharge(permit, entry, exit, baseRate);

        // Apply early entry surcharge and discount
        double expectedCharge = (DAILY_RATE * 0.80) + LATE_EARLY_SURCHARGE.getAmount();
        assertEquals(new Money(expectedCharge, "USD"), result);
    }


    /**
     * Ensures that a compact car with zero duration is still charged the minimum one-day rate.
     */
    @Test
    void testZeroDurationCompactStillOneDay() {
        Car car = new Car(CarType.COMPACT, "ZERO", customer);
        ParkingPermit permit = new ParkingPermit("P007", car, LocalDateTime.now());

        LocalDateTime entry = LocalDateTime.of(2025, 4, 15, 14, 0);
        LocalDateTime exit = entry;  // Same entry and exit time

        Money result = strategyCompact.calculateCharge(permit, entry, exit, baseRate);

        // Minimum 1-day charge with discount for compact car
        double expectedCharge = DAILY_RATE * 0.80; // Discount applied
        assertEquals(new Money(expectedCharge, "USD"), result);
    }

    /**
     * Verifies combined surcharges (event, weekend, early) are summed 
     * before applying compact discount.
     */
    @Test
    void testAllSurchargesPlusCompactDiscount() {
        Car car = new Car(CarType.COMPACT, "ALL", customer);
        ParkingPermit permit = new ParkingPermit("P008", car, LocalDateTime.now());

        LocalDateTime entry = LocalDateTime.of(2025, 5, 10, 5, 45);  // Early entry on Saturday
        LocalDateTime exit = entry.plusHours(1);

        Money result = strategyCompact.calculateCharge(permit, entry, exit, baseRate);

        // Apply all surcharges and discount
        double expectedCharge = ((DAILY_RATE * 0.80)+ EVENT_SURCHARGE.getAmount() + 
        		WEEKEND_SURCHARGE.getAmount() + LATE_EARLY_SURCHARGE.getAmount());
        
        assertEquals(new Money(expectedCharge, "USD"), result);
    }
    
    @Test
    void testWeekendFreeForCompactCar() {
        ParkingChargeStrategy strategy = new WeekendFreeStrategy(
            new FlatDailyRateWithCompactDiscountStrategy());

        Car car = new Car(CarType.COMPACT, "WEEKENDCAR", customer);
        ParkingPermit permit = new ParkingPermit("P123", car, LocalDateTime.now());

        LocalDateTime entry = LocalDateTime.of(2025, 4, 19, 9, 0); // Saturday
        LocalDateTime exit = entry.plusHours(2);

        Money result = strategy.calculateCharge(permit, entry, exit, new Money(10.00, "USD"));

        assertEquals(new Money(0.00, "USD"), result); // Because it's a weekend!
    }
}
