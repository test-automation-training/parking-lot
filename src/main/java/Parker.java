import exceptions.CarNotFoundException;

import java.util.List;

public abstract class Parker {
    protected List<ParkingLot> parkingLots;

    public Parker(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
    }

    public abstract Ticket park(Car car) throws Exception;

    public Car take(Ticket ticket) throws CarNotFoundException {
        return parkingLots.stream()
                .filter(parkingLot -> parkingLot.contains(ticket))
                .findFirst()
                .orElseThrow(CarNotFoundException::new)
                .retrieve(ticket);
    }
}
