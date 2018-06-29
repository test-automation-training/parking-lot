import exceptions.AllParkingLotsIsFullException;
import exceptions.CarNotFoundException;

import java.util.List;

public class SmartParkingBoy {
    private List<ParkingLot> parkingLots;

    public SmartParkingBoy(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
    }

    public Ticket park(Car car) throws Exception {
        return parkingLots.stream()
                .filter(ParkingLot::hasSpace)
                .min((p1, p2) -> p2.getRemainingSpace() - p1.getRemainingSpace())
                .orElseThrow(AllParkingLotsIsFullException::new)
                .receive(car);
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
