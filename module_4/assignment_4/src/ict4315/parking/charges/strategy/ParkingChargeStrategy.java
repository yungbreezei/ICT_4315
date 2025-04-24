package ict4315.parking.charges.strategy;

import ict4315_assignment_1.Money;
import ict4315_assignment_1.ParkingPermit;

import java.time.LocalDateTime;

/**
 * Defines the contract for calculating parking charges based on a parking permit,
 * entry/exit time, and the base rate of the parking lot.
 * 
 * Interface is used to implement different charging strategies,
 * such as hourly, daily, or special event rates
 * 
 * Strategy Pattern
 */

public interface ParkingChargeStrategy {

    /**
     * Calculates the parking charge for a vehicle based on the given permit,
     * entry and exit times, and the base rate of the parking lot
     */
	
	Money calculateCharge(ParkingPermit permit, LocalDateTime entryTime, 
			LocalDateTime exitTime, Money baseRate);

	boolean isHourlyBased();
	
}
