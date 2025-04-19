package ict4315_assignment_1;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

import ict4315.parking.charges.strategy.HourlyRateStrategy;

public class MainClass {

    public static void main(String[] args) {

        // 1. Create address for the office
        Address officeAddress = new Address("123 Main St", "Suite 1", "Metropolis", "NY", "10001");
        ParkingOffice office = new ParkingOffice("Metro Parking", officeAddress);

        // 2. Create and register a customer
        Address customerAddress = new Address("456 Elm St", "", "Metropolis", "NY", "10001");
        Customer customer = new Customer(null, "John", "Doe", "555-1234", customerAddress);
        String customerId = office.register(customer);
        customer.setId(customerId);  // Set the assigned ID
        System.out.println("Customer registered with ID: " + customerId);

        // 3. Prepare car registration properties
        Properties carProps = new Properties();
        carProps.setProperty("customerId", customerId);
        carProps.setProperty("licensePlate", "XYZ-123");
        carProps.setProperty("carType", "SEDAN");

        // 4. Register the car using command
        RegisterCarCommand registerCarCommand = new RegisterCarCommand(office);
        try {
            String result = registerCarCommand.execute(carProps);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("Car registration failed: " + e.getMessage());
        }

        // 5. Create parking lot and register it
        Address lotAddress = new Address("789 Oak St", "", "Metropolis", "NY", "10001");
        ParkingLot lot = new ParkingLot("LOT1", "Downtown Lot", lotAddress, new HourlyRateStrategy(), 7.5, 100);
        office.register(lot);

        // 6. Get the permit created from registration
        List<ParkingPermit> permits = office.getPermitManager().getPermits();
        if (permits.isEmpty()) {
            System.out.println("No permit was registered. Cannot proceed with parking.");
            return;
        }
        ParkingPermit permit = permits.get(0);  // Assume one car was registered

        // 7. Simulate parking
        try {
            LocalDateTime entryTime = LocalDateTime.now();
            LocalDateTime exitTime = entryTime.plusHours(2);  // Simulate 2-hour parking
            ParkingTransaction transaction = office.park(entryTime, exitTime, permit, lot);
            System.out.println("Parking successful. Transaction ID: " + transaction.getTransactionId());
        } catch (Exception e) {
            System.out.println("Parking failed: " + e.getMessage());
        }

        // 8. Print parking charges
        try {
            Money charges = office.getParkingCharges(customer);
            System.out.println("Total parking charges: " + charges);
        } catch (Exception e) {
            System.out.println("Failed to retrieve parking charges: " + e.getMessage());
        }
    }
}
