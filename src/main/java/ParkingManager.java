import exceptions.AllParkingLotsIsFullException;
import exceptions.CarNotFoundException;
import exceptions.ParkingLotIsFullException;

import java.util.List;

public class ParkingManager implements Parkable {
    private List<ParkingLot> parkingLots;
    private List<Parker> parkers;

    public ParkingManager(List<ParkingLot> parkingLots, List<Parker> parkers) {
        this.parkingLots = parkingLots;
        this.parkers = parkers;
    }

    @Override
    public Ticket park(Car car) throws AllParkingLotsIsFullException, ParkingLotIsFullException {
        return parkingLots.stream()
                .filter(ParkingLot::hasSpace)
                .findFirst()
                .orElseThrow(AllParkingLotsIsFullException::new)
                .receive(car);
    }

    @Override
    public Car take(Ticket ticket) throws CarNotFoundException {
        return parkingLots.stream()
                .filter(parkingLot -> parkingLot.contains(ticket))
                .findFirst()
                .orElseThrow(CarNotFoundException::new)
                .retrieve(ticket);
    }
}
