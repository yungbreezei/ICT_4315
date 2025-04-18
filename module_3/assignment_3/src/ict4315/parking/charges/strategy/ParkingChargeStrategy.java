package ict4315.parking.charges.strategy;

import ict4315_assignment_1.Money;
import ict4315_assignment_1.ParkingPermit;

import java.time.LocalDateTime;


public interface ParkingChargeStrategy {

	Money calculateCharge(ParkingPermit permit, LocalDateTime entryTime, 
			LocalDateTime exitTime, Money baseRate);
	
}
