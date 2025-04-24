package ict4315.parking.charges.factory;

import ict4315.parking.charges.strategy.ParkingChargeStrategy;
import ict4315_assignment_1.ParkingLot;

public interface ParkingChargeStrategyFactory {

	ParkingChargeStrategy getStrategyFor(ParkingLot lot);
}
