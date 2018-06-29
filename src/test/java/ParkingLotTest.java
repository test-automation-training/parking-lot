import exceptions.CarNotFoundException;
import exceptions.ParkingLotIsFullException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ParkingLotTest {

    @Test
    void throwParkingLotIsFullExceptionWhenReceiveCarButNotHasSpace() {

        ParkingLot parkingLot = new ParkingLot(0);

        assertThatThrownBy(() -> parkingLot.receive(new Car())).isInstanceOf(ParkingLotIsFullException.class);
    }

    @Test
    void throwCarNotFoundExceptionWhenCarNotFound() {

        ParkingLot parkingLot = new ParkingLot(10);

        assertThatThrownBy(() -> parkingLot.retrieve(new Ticket())).isInstanceOf(CarNotFoundException.class);
    }
}