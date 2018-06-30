import exceptions.AllParkingLotsIsFullException;
import exceptions.ParkingLotIsFullException;

import java.util.List;

public class ParkingBoy extends Parker {

    public ParkingBoy(List<ParkingLot> parkingLots) {
        super(parkingLots);
    }

    @Override
    public Ticket park(Car car) throws AllParkingLotsIsFullException, ParkingLotIsFullException {
        return parkingLots.stream()
                .filter(ParkingLot::hasSpace)
                .findFirst()
                .orElseThrow(AllParkingLotsIsFullException::new)
                .receive(car);
    }
}
