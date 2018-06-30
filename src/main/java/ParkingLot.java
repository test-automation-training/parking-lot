import exceptions.CarNotFoundException;
import exceptions.ParkingLotIsFullException;

import java.util.HashMap;
import java.util.Map;

public class ParkingLot {
    private int capacity;
    private Map<Ticket, Car> parkedCars = new HashMap<>();

    public ParkingLot(int capacity) {
        this.capacity = capacity;
    }

    public boolean contains(Ticket ticket) {
        return parkedCars.containsKey(ticket);
    }

    public boolean hasSpace() {
        return parkedCars.size() < capacity;
    }

    public Ticket receive(Car car) throws ParkingLotIsFullException {
        if (hasSpace()) {
            Ticket ticket = new Ticket();
            parkedCars.put(ticket, car);
            return ticket;
        }

        throw new ParkingLotIsFullException();
    }

    public Car retrieve(Ticket ticket) throws CarNotFoundException {
        if (contains(ticket)) {
            return parkedCars.remove(ticket);
        }
        throw new CarNotFoundException();
    }

    public int getRemainingSpace() {
        return capacity - parkedCars.size();
    }

    public double getVacancyRate() {
        return (double) getRemainingSpace() / capacity;
    }
}
