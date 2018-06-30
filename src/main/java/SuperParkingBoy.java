import exceptions.AllParkingLotsIsFullException;

import java.util.List;

import static java.util.Comparator.comparing;

public class SuperParkingBoy extends Parker {

    public SuperParkingBoy(List<ParkingLot> parkingLots) {
        super(parkingLots);
    }

    @Override
    public Ticket park(Car car) throws Exception {
        return parkingLots.stream()
                .filter(ParkingLot::hasSpace)
                .max(comparing(ParkingLot::getVacancyRate))
                .orElseThrow(AllParkingLotsIsFullException::new)
                .receive(car);
    }

}
