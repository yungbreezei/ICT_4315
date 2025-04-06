/* Bria Wright
 * 
 * ICT 4315
 * Week 1 Assignment: Translating UML into Code
 * April 6, 2025
 */

package assignment_1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
import java.util.Date;

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
        parkingLot = new ParkingLot("PL001", "Downtown Parking", address);

        // Register the car to get a parking permit from PermitManager
        PermitManager permitManager = new PermitManager();
        permit = permitManager.register(car);

        // Initialize the TransactionManager
        transactionManager = new TransactionManager();
    }


    @Test
    public void testParkAndCharge() {
        // Simulate parking the car
        Date date = new Date();
        ParkingTransaction transaction = transactionManager.park(date, permit, parkingLot);

        // Verify the transaction is created correctly
        assertNotNull(transaction);
        assertEquals(parkingLot, transaction.getParkingLot());
        assertEquals(permit, transaction.getPermit());
    }

    @Test
    public void testGetParkingChargesForPermit() {
        // Simulate parking the car
        Date date = new Date();
        transactionManager.park(date, permit, parkingLot);

        // Get total charges for the permit
        Money totalCharges = transactionManager.getParkingCharges(permit);

        // Assuming the rate for an SUV is 15.00 USD
        assertEquals(15.00, totalCharges.getAmount());
    }

    @Test
    public void testGetParkingChargesForCustomer() {
        // Simulate parking the car
        Date date = new Date();
        transactionManager.park(date, permit, parkingLot);

        // Get total charges for the customer
        Money totalCharges = transactionManager.getParkingCharges(customer);

        // Assuming the rate for an SUV is 15.00 USD
        assertEquals(15.00, totalCharges.getAmount());
    }

    @Test
    public void testDuplicateTransaction() {
        // First transaction for the permit
        Date date = new Date();
        transactionManager.park(date, permit, parkingLot);

        // Attempting a second parking transaction with the same permit should add a new transaction
        ParkingTransaction transaction = transactionManager.park(date, permit, parkingLot);

        // Verify that the second transaction was successfully added
        assertNotNull(transaction);
        assertEquals(2, transactionManager.getTransactions().size());  // Check that there are now 2 transactions
    }

    @Test
    public void testTransactionChargesByCustomer() {
        // Simulate parking the car
        Date date = new Date();
        transactionManager.park(date, permit, parkingLot);

        // Get total charges for the customer
        Money totalCharges = transactionManager.getParkingCharges(customer);

        // Assert that the customer is charged correctly
        assertEquals(15.00, totalCharges.getAmount());  // SUV parking rate is 15.00 USD
    }
}
