package ict4315.parking.charges.strategy.tests;

import ict4315_assignment_1.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ict4315.parking.charges.strategy.FlatDailyRateStrategy;
import ict4315.parking.charges.strategy.WeekendFreeStrategy;
import ict4315.parking.charges.strategy.ParkingChargeStrategy;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class FlatDailyRateStrategyTest {
	
    private FlatDailyRateStrategy strategy;
    private WeekendFreeStrategy weekendFreeStrategy;

    private Car testCar;
    private ParkingPermit testPermit;
    private Money baseRate;

    @BeforeEach
    void setUp() {
        strategy = new FlatDailyRateStrategy();
        Customer customer = new Customer("1", "Jane", "Doe", "555-5555", 
                        new Address("1", "2", "City", "ST", "00000"));
        testCar = new Car(CarType.COMPACT, "TEST123", customer);
        testPermit = new ParkingPermit("P1", testCar, LocalDateTime.now().plusYears(1));
        baseRate = new Money(10.00, "USD");
        
        // Chain the strategies to include weekend surcharge
        ParkingChargeStrategy base = new FlatDailyRateStrategy();
        weekendFreeStrategy = new WeekendFreeStrategy(base);
    }

    @Test
    void testCalculateChargeFlatDailyRate() {
        LocalDateTime entry = LocalDateTime.of(2025, 4, 15, 10, 0); // Regular weekday
        LocalDateTime exit = entry.plusHours(5); // Less than 24 hours

        Money charge = weekendFreeStrategy.calculateCharge(testPermit, entry, exit, baseRate);
        assertEquals(new Money(10.00, "USD"), charge); // No surcharge
    }
    
    
    
    @Test
    void testMultiDayCharge() {
        FlatDailyRateStrategy strategy = new FlatDailyRateStrategy();

        Address address = new Address("123 Main St", "Apt 101", "City", "ST", "12345");
        Customer customer = new Customer("1", "Test User", "Test Last Name", "555-555-5555", address);
        Car car = new Car(CarType.SUV, "ABC123", customer);
        
        // Create expiration date (for example, set it a month from the entry date)
        LocalDateTime expirationDate = LocalDateTime.of(2025, 5, 10, 8, 0);
        Date expiration = java.sql.Timestamp.valueOf(expirationDate); // Convert to Date object
        
        // Create the permit with expiration date
        ParkingPermit permit = new ParkingPermit("P001", car, LocalDateTime.now());
        
        Money baseRate = new Money(10.0, "USD");

        LocalDateTime entry = LocalDateTime.of(2025, 4, 10, 8, 0);
        LocalDateTime exit = LocalDateTime.of(2025, 4, 13, 9, 0); // 3 days

        Money result = strategy.calculateCharge(permit, entry, exit, baseRate);
        assertEquals(new Money(30.0, "USD"), result);
    }
    
    @Test
    void testStandardWeekdayNoSurcharge() {
        LocalDateTime entry = LocalDateTime.of(2025, 4, 15, 10, 0); // Tuesday
        LocalDateTime exit = entry.plusHours(5);

        Money charge = strategy.calculateCharge(testPermit, entry, exit, baseRate);
        assertEquals(new Money(10.00, "USD"), charge);
    }

    @Test
    void testWeekendSurcharge() {
        LocalDateTime entry = LocalDateTime.of(2025, 4, 19, 10, 0); // Saturday
        LocalDateTime exit = entry.plusHours(3); // Less than 24 hours

        Money charge = weekendFreeStrategy.calculateCharge(testPermit, entry, exit, baseRate);
        assertEquals(new Money(0.00, "USD"), charge); // $10 + $3 surcharge
    }
    
    @Test
    void testEarlyEntrySurcharge() {
        LocalDateTime entry = LocalDateTime.of(2025, 4, 15, 5, 59); // Tuesday before 6 AM
        LocalDateTime exit = entry.plusHours(2);

        Money charge = strategy.calculateCharge(testPermit, entry, exit, baseRate);
        assertEquals(new Money(12.50, "USD"), charge); // $10 + $2.50
    }
    
    @Test
    void testLateEntrySurcharge() {
        LocalDateTime entry = LocalDateTime.of(2025, 4, 15, 22, 1); // Tuesday after 10 PM
        LocalDateTime exit = entry.plusHours(1);

        Money charge = strategy.calculateCharge(testPermit, entry, exit, baseRate);
        assertEquals(new Money(12.50, "USD"), charge);
    }
    
    @Test
    void testGraduationDaySurcharge() {
        LocalDateTime entry = LocalDateTime.of(2025, 5, 10, 14, 0); // May 10 = Graduation Day
        LocalDateTime exit = entry.plusHours(1); // 1 hour parked

        Money charge = weekendFreeStrategy.calculateCharge(testPermit, entry, exit, baseRate);
        assertEquals(new Money(0.00, "USD"), charge); // $10 base + $2 event surcharge
    }
    
    @Test
    void testMultipleDayCharge() {
        LocalDateTime entry = LocalDateTime.of(2025, 4, 15, 14, 0);
        LocalDateTime exit = entry.plusDays(2).plusHours(1); // 2 full days

        Money charge = strategy.calculateCharge(testPermit, entry, exit, baseRate);
        assertEquals(new Money(20.00, "USD"), charge); // 2 days * $10
    }
    @Test
    void testAllSurchargesApplied() {
        // May 10, 2025 is Saturday, entry before 6AM
        LocalDateTime entry = LocalDateTime.of(2025, 5, 10, 5, 30); 
        LocalDateTime exit = entry.plusHours(1);

        Money charge = strategy.calculateCharge(testPermit, entry, exit, baseRate);
        assertEquals(new Money(12.50, "USD"), charge);
    }
    
    @Test
    void testZeroDurationStillChargesOneDay() {
        LocalDateTime entry = LocalDateTime.of(2025, 4, 15, 12, 0);
        LocalDateTime exit = entry;

        Money charge = strategy.calculateCharge(testPermit, entry, exit, baseRate);
        assertEquals(new Money(10.00, "USD"), charge);
    }
}
