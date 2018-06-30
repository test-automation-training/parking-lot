import exceptions.AllParkingLotsIsFullException;
import exceptions.CarNotFoundException;

import java.util.List;

public class ParkingManager {
    private List<ParkingLot> parkingLots;
    private List<Parker> parkerList;

    public ParkingManager(List<ParkingLot> parkingLots, List<Parker> parkerList) {
        this.parkingLots = parkingLots;
        this.parkerList = parkerList;
    }

    public Ticket park(Car car) throws Exception {
        return parkingLots.stream()
                .filter(ParkingLot::hasSpace)
                .findFirst()
                .orElseThrow(AllParkingLotsIsFullException::new)
                .receive(car);
    }

    public Car take(Ticket ticket) throws Exception {
        return parkingLots.stream()
                .filter(parkingLot -> parkingLot.contains(ticket))
                .findFirst()
                .orElseThrow(CarNotFoundException::new)
                .retrieve(ticket);
    }
}
