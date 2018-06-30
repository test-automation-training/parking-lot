import exceptions.CarNotFoundException;

import java.util.List;

public abstract class Parker implements Parkable {
    protected List<ParkingLot> parkingLots;

    public Parker(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
    }

    @Override
    public Car take(Ticket ticket) throws CarNotFoundException {
        return parkingLots.stream()
                .filter(parkingLot -> parkingLot.contains(ticket))
                .findFirst()
                .orElseThrow(CarNotFoundException::new)
                .retrieve(ticket);
    }

    public boolean hasCar(Ticket ticket) {
        return parkingLots.stream().anyMatch(parkingLot -> parkingLot.contains(ticket));
    }
}
