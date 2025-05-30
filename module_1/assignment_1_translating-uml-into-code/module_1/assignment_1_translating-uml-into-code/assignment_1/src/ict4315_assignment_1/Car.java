/* Bria Wright
 * 
 * ICT 4315
 * Week 1 Assignment: Translating UML into Code
 * April 6, 2025
 */

package ict4315_assignment_1;

import java.util.Objects;
import java.util.Properties;

public class Car {
	
	private CarType type; // Enum representing the car type (e.g., COMPACT, SUV)
	private String licensePlate;  // Car's license plate
	private Customer owner; // ID of the customer who owns the car


    // Constructor to initialize a new Car with license plate, type, and owner ID
    public Car(CarType type, String licensePlate, Customer owner) {
    	
        if (type == null || licensePlate == null || owner == null) {
            throw new IllegalArgumentException("Car properties cannot be null");
        }
    	
    	this.licensePlate = licensePlate;
        this.type = type; 
        this.owner = owner;
    }
    
    /*
     * Getters
     */	 
    
    /*
     * Retrieves the car's type.
     */
    public CarType getType() {
    	return type;
    }
    
    public String getLicensePlate() {
    	return licensePlate;
    }
    
    /*
     * Retrieves the owner's ID.
     */
    public Customer getOwner() {
        return owner;
    }
    
    /*
     * Setters
     */
    public void setType(CarType type) {
    	this.type = type;
    }
    
    public void setLicensePlate(String licensePlate) {
    	this.licensePlate = licensePlate;
    }
    
    public void setOwner(Customer owner) {
    	this.owner = owner;
    }	
    
    /*
     * Methods
     */	 
    public static Car create(Properties properties) {
        return new Car(
            CarType.valueOf(properties.getProperty("type")),
            properties.getProperty("licensePlate"),
            new Customer(properties.getProperty("ownerId"), "", "", "", null)
        );
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(type, car.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
    
    @Override
    public String toString() {
        return "Car{" +
                "Type='" + type + '\'' +
                ", License Plate='" + licensePlate +
                '}';
    }
}

