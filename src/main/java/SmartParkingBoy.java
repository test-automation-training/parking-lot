import exceptions.AllParkingLotsIsFullException;

import java.util.List;

public class SmartParkingBoy extends Parker {

    public SmartParkingBoy(List<ParkingLot> parkingLots) {
        super(parkingLots);
    }

    @Override
    public Ticket park(Car car) throws Exception {
        return parkingLots.stream()
                .filter(ParkingLot::hasSpace)
                .min((p1, p2) -> p2.getRemainingSpace() - p1.getRemainingSpace())
                .orElseThrow(AllParkingLotsIsFullException::new)
                .receive(car);
    }

}
