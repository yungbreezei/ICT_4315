package ict4315.parking.charges.strategy.tests;

import ict4315_assignment_1.*;
import org.junit.jupiter.api.Test;

import ict4315.parking.charges.strategy.FlatDailyRateWithCompactDiscountStrategy;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


class FlatDailyRateWithCompactDiscountStrategyTest {

    @Test
    void testCompactCarGetsDiscount() {
        FlatDailyRateWithCompactDiscountStrategy strategy = new FlatDailyRateWithCompactDiscountStrategy();

        Address address = new Address("123 Main St", "Apt 101", "City", "ST", "12345");
        Customer customer = new Customer("1", "Test User", "Test Last Name", "555-555-5555", address);
        Car car = new Car(CarType.COMPACT, "ABC123", customer);
        
        // Convert LocalDateTime to java.util.Date
        LocalDateTime expirationLocal = LocalDateTime.of(2025, 12, 31, 23, 59);
        Date expiration = Date.from(expirationLocal.atZone(ZoneId.systemDefault()).toInstant());
        
        ParkingPermit permit = new ParkingPermit("P001", car, LocalDateTime.now());
        Money baseRate = new Money(10.0, "USD");

        LocalDateTime entry = LocalDateTime.of(2025, 4, 15, 8, 0);
        LocalDateTime exit = LocalDateTime.of(2025, 4, 16, 7, 59); // 1 day

        Money result = strategy.calculateCharge(permit, entry, exit, baseRate);
        assertEquals(new Money(8.0, "USD"), result); // 20% discount
    }

    @Test
    void testSuvNoDiscount() {
        FlatDailyRateWithCompactDiscountStrategy strategy = new FlatDailyRateWithCompactDiscountStrategy();

        Address address = new Address("123 Main St", "Apt 101", "City", "ST", "12345");
        Customer customer = new Customer("1", "Test User", "Test Last Name", "555-555-5555", address);
        Car car = new Car(CarType.SUV, "ABC123", customer);

        // Create expiration date (for example, set it a month from the entry date)
        LocalDateTime expirationDate = LocalDateTime.of(2025, 5, 10, 8, 0);
        Date expiration = java.sql.Timestamp.valueOf(expirationDate); // Convert to Date object
        
        ParkingPermit permit = new ParkingPermit("P001", car, LocalDateTime.now());
        Money baseRate = new Money(15.0, "USD");

        LocalDateTime entry = LocalDateTime.of(2025, 4, 10, 10, 0);
        LocalDateTime exit = LocalDateTime.of(2025, 4, 12, 9, 59); // 2 days

        Money result = strategy.calculateCharge(permit, entry, exit, baseRate);
        assertEquals(new Money(30.0, "USD"), result);
    }
}
