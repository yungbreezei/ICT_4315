package ict4315_assignment_1;

import java.util.Date;
import java.util.Properties;
import java.util.UUID;

public class MainClass {

    public static void main(String[] args) {

        // 1. Create address for the office
        Address officeAddress = new Address("123 Main St", "Suite 1", "Metropolis", "NY", "10001");
        ParkingOffice office = new ParkingOffice("Metro Parking", officeAddress);

        // 2. Create and register a customer
        Address customerAddress = new Address("456 Elm St", "", "Metropolis", "NY", "10001");
        Customer customer = new Customer(null, "John", "Doe", "555-1234", customerAddress);
        String customerId = office.register(customer);
        customer.setId(customerId);  // Important: Ensure the ID is set
        System.out.println("Customer registered with ID: " + customerId);

        // 3. Prepare car registration properties
        Properties carProps = new Properties();
        carProps.setProperty("customerId", customerId);
        carProps.setProperty("licensePlate", "XYZ-123");
        carProps.setProperty("carType", "SEDAN");  // Assuming enum CarType.SEDAN

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
        ParkingLot lot = new ParkingLot("LOT1", "Downtown Lot", lotAddress);
        office.register(lot);

        // 6. Create a car manually for parking simulation
        Car car = new Car(CarType.COMPACT, "XYZ-123", customer);

        // 7. Create a permit for the car
        String permitId = UUID.randomUUID().toString();
        Date expiration = new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000); // 30 days from now
        ParkingPermit permit = new ParkingPermit(permitId, car, expiration);

        // 8. Simulate parking
        try {
            ParkingTransaction transaction = office.park(new Date(), permit, lot);
            System.out.println("Parking successful. Transaction ID: " + transaction.getTransactionId());
        } catch (Exception e) {
            System.out.println("Parking failed: " + e.getMessage());
        }

        // 9. Print parking charges
        Money charges = office.getParkingCharges(customer);
        System.out.println("Total parking charges: " + charges);
    }
}
