import exceptions.AllParkingLotsIsFullException;
import exceptions.CarNotFoundException;

import java.util.List;

public class ParkingBoy {
    private List<ParkingLot> parkingLots;

    public ParkingBoy(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
    }

    public Ticket park(Car car) throws Exception {
        for (ParkingLot parkingLot : parkingLots) {
            if (parkingLot.hasSpace()) {
                return parkingLot.receive(car);
            }
        }

        throw new AllParkingLotsIsFullException();
    }

    public Car take(Ticket ticket) throws CarNotFoundException {
        for (ParkingLot parkingLot : parkingLots) {
            if (parkingLot.contains(ticket)) {
                return parkingLot.retrieve(ticket);
            }
        }
        throw new CarNotFoundException();
    }
}
