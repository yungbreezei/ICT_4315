package assignment_1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ict4315.parking.charges.factory.DefaultParkingChargeStrategyFactory;
import ict4315.parking.charges.factory.ParkingChargeStrategyFactory;
import ict4315.parking.charges.strategy.FlatDailyRateStrategy;
import ict4315.parking.charges.strategy.HourlyRateStrategy;
import ict4315.parking.charges.strategy.ParkingChargeStrategy;
import ict4315_assignment_1.Address;
import ict4315_assignment_1.Car;
import ict4315_assignment_1.CarType;
import ict4315_assignment_1.Customer;
import ict4315_assignment_1.Money;
import ict4315_assignment_1.ParkingLot;
import ict4315_assignment_1.ParkingPermit;
import ict4315_assignment_1.ParkingTransaction;
import ict4315_assignment_1.PermitManager;
import ict4315_assignment_1.TransactionManager;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

public class TransactionManagerTest {

    private TransactionManager transactionManager;
    private ParkingLot parkingLot;
    private ParkingPermit permit;
    private Car car;
    private Customer customer;


    @BeforeEach
    public void setUp() {
    	
        // Create an Address object using the updated constructor
        Address address = new Address("1234 Parking St", "Apt 101", "City", "State", "12345");

        // Create a Customer object using the updated constructor
        customer = new Customer("CUST001", "John", "Doe", "123-456-7890", address);

        // Create a Car object with the customer and valid details
        car = new Car(CarType.SUV, "ABC123", customer);

        // Create a ParkingLot with valid details
        parkingLot = new ParkingLot("PL001", "Downtown Parking", address, 
        		new HourlyRateStrategy(), 7.5, 100);

        // Register the car to get a parking permit from PermitManager
        PermitManager permitManager = new PermitManager();
        permit = permitManager.register(car);

        // Create a ParkingChargeStrategyFactory and TransactionManager
        ParkingChargeStrategyFactory strategyFactory = new ParkingChargeStrategyFactory() {
            @Override
            public ParkingChargeStrategy getStrategyFor(ParkingLot lot) {
                return new HourlyRateStrategy();
            }
        };
        transactionManager = new TransactionManager(strategyFactory);
    }


    @Test
    public void testParkAndCharge() {
        // Simulate parking the car with LocalDateTime entry and exit times
        LocalDateTime entryTime = LocalDateTime.now();
        LocalDateTime exitTime = entryTime.plusHours(2);  // Assuming 2 hours parking duration
        
        ParkingTransaction transaction = transactionManager.park(entryTime, exitTime, 
        		permit, parkingLot);

        // Verify the transaction is created correctly
        assertNotNull(transaction);
        assertEquals(parkingLot, transaction.getParkingLot());
        assertEquals(permit, transaction.getPermit());
    }


    @Test
    public void testGetParkingChargesForPermit() {
        // Simulate parking the car with a 4-hour stay
        LocalDateTime entryTime = LocalDateTime.now();
        LocalDateTime exitTime = entryTime.plusHours(4); // 4 hours
        transactionManager.park(entryTime, exitTime, permit, parkingLot);

        // Get total charges for the permit
        Money totalCharges = transactionManager.getParkingCharges(permit);

        // If base rate is $15.0/hour, 4 hours = 30.00
        assertEquals(30.00, totalCharges.getAmount());
    }

    @Test
    public void testGetParkingChargesForCustomer() {
        // Simulate parking the car with LocalDateTime entry and exit times
        LocalDateTime entryTime = LocalDateTime.now();
        LocalDateTime exitTime = entryTime.plusHours(2);  // Assuming 2 hours parking duration
        transactionManager.park(entryTime, exitTime, permit, parkingLot);

        // Get total charges for the customer
        Money totalCharges = transactionManager.getParkingCharges(customer);

        // Assuming the rate for an SUV is 15.00 USD
        assertEquals(15.00, totalCharges.getAmount());
    }


    @Test
    public void testDuplicateTransaction() {
        // First transaction for the permit
        LocalDateTime entryTime = LocalDateTime.now();
        LocalDateTime exitTime = entryTime.plusHours(2);  // Assuming 2 hours parking duration
        transactionManager.park(entryTime, exitTime, permit, parkingLot);

        // Attempting a second parking transaction with the same permit should add a new transaction
        ParkingTransaction transaction = transactionManager.park(entryTime, exitTime, 
        		permit, parkingLot);

        // Verify that the second transaction was successfully added
        assertNotNull(transaction);
        assertEquals(2, transactionManager.getTransactions().size());  // Check that there are now 2 transactions
    }

    @Test
    public void testTransactionChargesByCustomer() {
        // Simulate parking the car with LocalDateTime entry and exit times
        LocalDateTime entryTime = LocalDateTime.now();
        LocalDateTime exitTime = entryTime.plusHours(2);  // Assuming 2 hours parking duration
        transactionManager.park(entryTime, exitTime, permit, parkingLot);

        // Get total charges for the customer
        Money totalCharges = transactionManager.getParkingCharges(customer);

        // Assert that the customer is charged correctly
        assertEquals(15.00, totalCharges.getAmount());  // SUV parking rate is 15.00 USD
    }
    
    @Test
    public void testHourlyRateWithDiscountForCompactCar() {
        // Setup entry and exit times
        LocalDateTime entryTime = LocalDateTime.now();
        LocalDateTime exitTime = entryTime.plusHours(3); // 3-hour parking
        
        // Create a compact car with a parking permit
        Car compactCar = new Car(CarType.COMPACT, "XYZ123", customer);
        ParkingPermit compactPermit = new PermitManager().register(compactCar);

        // Set the base hourly rate
        Money baseRate = new Money(7.5, "USD"); // $7.50 per hour

        // Create the HourlyRateStrategy and calculate the charge
        HourlyRateStrategy hourlyStrategy = new HourlyRateStrategy();
        Money charge = hourlyStrategy.calculateCharge(compactPermit, entryTime, exitTime, baseRate);

        // Expected calculation with discount: 3 hours * 7.50 * 0.80 = $18.00
        assertEquals(18.00, charge.getAmount());
    }
    
}
