import exceptions.AllParkingLotsIsFullException;
import exceptions.CarNotFoundException;
import exceptions.ParkingLotIsFullException;

public interface Parkable {
    Ticket park(Car car) throws AllParkingLotsIsFullException, ParkingLotIsFullException;

    Car take(Ticket ticket) throws CarNotFoundException;
}
