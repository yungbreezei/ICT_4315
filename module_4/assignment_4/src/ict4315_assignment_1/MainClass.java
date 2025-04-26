package ict4315_assignment_1;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

import ict4315.parking.charges.factory.DefaultParkingChargeStrategyFactory;
import ict4315.parking.charges.factory.ParkingChargeStrategyFactory;
import ict4315.parking.charges.strategy.HourlyRateStrategy;

public class MainClass {

    public static void main(String[] args) {
        ParkingOffice office = createParkingOffice();
        Customer customer = registerCustomer(office);
        registerCar(office, customer);

        ParkingLot lot = registerParkingLot(office);
        ParkingPermit permit = retrievePermit(office);

        if (permit == null) {
            System.out.println("No permit was registered. Cannot proceed with parking.");
            return;
        }

        performParking(office, permit, lot);
        printParkingCharges(office, customer);
    }

    private static ParkingOffice createParkingOffice() {
        Address officeAddress = new Address("123 Main St", "Suite 1", "Metropolis", "NY", "10001");
        ParkingChargeStrategyFactory strategyFactory = new DefaultParkingChargeStrategyFactory();
        return new ParkingOffice("Metro Parking", officeAddress, strategyFactory);
    }

    private static Customer registerCustomer(ParkingOffice office) {
        Address customerAddress = new Address("456 Elm St", "", "Metropolis", "NY", "10001");
        Customer customer = new Customer(null, "John", "Doe", "555-1234", customerAddress);
        String customerId = office.register(customer);
        customer.setId(customerId);
        System.out.println("Customer registered with ID: " + customerId);
        return customer;
    }

    private static void registerCar(ParkingOffice office, Customer customer) {
        Properties carProps = new Properties();
        carProps.setProperty("customerId", customer.getId());
        carProps.setProperty("licensePlate", "XYZ-123");
        carProps.setProperty("carType", "SEDAN");

        RegisterCarCommand registerCarCommand = new RegisterCarCommand(office);
        try {
            String result = registerCarCommand.execute(carProps);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("Car registration failed: " + e.getMessage());
        }
    }

    private static ParkingLot registerParkingLot(ParkingOffice office) {
        Address lotAddress = new Address("789 Oak St", "", "Metropolis", "NY", "10001");
        ParkingLot lot = new ParkingLot("LOT1", "Downtown Lot", lotAddress, new HourlyRateStrategy(), 7.5, 100);
        office.register(lot);
        return lot;
    }

    private static ParkingPermit retrievePermit(ParkingOffice office) {
        List<ParkingPermit> permits = office.getPermitManager().getPermits();
        if (permits.isEmpty()) {
            return null;
        }
        return permits.get(0); // Assuming only one car registered
    }

    private static void performParking(ParkingOffice office, ParkingPermit permit, ParkingLot lot) {
        try {
            LocalDateTime entryTime = LocalDateTime.now();
            LocalDateTime exitTime = entryTime.plusHours(2);
            ParkingTransaction transaction = office.park(entryTime, exitTime, permit, lot);
            System.out.println("Parking successful. Transaction ID: " + transaction.getTransactionId());
        } catch (Exception e) {
            System.out.println("Parking failed: " + e.getMessage());
        }
    }

    private static void printParkingCharges(ParkingOffice office, Customer customer) {
        try {
            Money charges = office.getParkingCharges(customer);
            System.out.println("Total parking charges: " + charges);
        } catch (Exception e) {
            System.out.println("Failed to retrieve parking charges: " + e.getMessage());
        }
    }
}
