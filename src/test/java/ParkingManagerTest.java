import exceptions.AllParkingLotsIsFullException;
import exceptions.CarNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ParkingManagerTest {

    @Test
    void returnTicketWhenParkSuccess() throws Exception {
        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(new ParkingLot(10));

        ParkingManager parkingBoy = new ParkingManager(parkingLots, new ArrayList<>());

        Ticket ticket = parkingBoy.park(new Car());

        assertThat(ticket).isNotNull();
    }

    @Test
    void containsCarWhenParkSuccessAndFirstParkingLotHasSpace() throws Exception {
        List<ParkingLot> parkingLots = new ArrayList<>();

        ParkingLot firstParkingLot = new ParkingLot(10);
        parkingLots.add(firstParkingLot);

        ParkingLot secondParkingLot = new ParkingLot(10);
        parkingLots.add(secondParkingLot);

        ParkingManager parkingBoy = new ParkingManager(parkingLots, new ArrayList<>());

        Ticket ticket = parkingBoy.park(new Car());

        assertThat(firstParkingLot.contains(ticket)).isTrue();
        assertThat(secondParkingLot.contains(ticket)).isFalse();
    }

    @Test
    void containsCarWhenParkSuccessAndFirstParkingLotIsFullButSecondParkingLotHasSpace() throws Exception {
        List<ParkingLot> parkingLots = new ArrayList<>();

        ParkingLot firstParkingLot = new ParkingLot(0);
        parkingLots.add(firstParkingLot);

        ParkingLot secondParkingLot = new ParkingLot(10);
        parkingLots.add(secondParkingLot);

        ParkingManager parkingBoy = new ParkingManager(parkingLots, new ArrayList<>());

        Ticket ticket = parkingBoy.park(new Car());

        assertThat(firstParkingLot.contains(ticket)).isFalse();
        assertThat(secondParkingLot.contains(ticket)).isTrue();
    }

    @Test
    void throwAllParkingLotsIsFullExceptionWhenAllParkingLotsIsFull() {
        List<ParkingLot> parkingLots = new ArrayList<>();

        ParkingLot firstParkingLot = new ParkingLot(0);
        parkingLots.add(firstParkingLot);

        ParkingLot secondParkingLot = new ParkingLot(0);
        parkingLots.add(secondParkingLot);

        ParkingManager parkingBoy = new ParkingManager(parkingLots, new ArrayList<>());

        assertThatThrownBy(() -> parkingBoy.park(new Car())).isInstanceOf(AllParkingLotsIsFullException.class);
    }

    @Test
    void returnCarWhenTakeWithCorrectTicketAndCarInTheFirstParkingLot() throws Exception {
        List<ParkingLot> parkingLots = new ArrayList<>();

        ParkingLot firstParkingLot = new ParkingLot(10);
        parkingLots.add(firstParkingLot);

        ParkingLot secondParkingLot = new ParkingLot(10);
        parkingLots.add(secondParkingLot);

        ParkingManager parkingBoy = new ParkingManager(parkingLots, new ArrayList<>());

        Car car = new Car();

        Ticket ticket = parkingBoy.park(car);

        assertThat(firstParkingLot.contains(ticket)).isTrue();

        Car takenCar = parkingBoy.take(ticket);

        assertThat(takenCar).isEqualTo(car);

        assertThat(firstParkingLot.contains(ticket)).isFalse();
    }

    @Test
    void returnCarWhenTakeWithCorrectTicketAndCarInTheSecondParkingLot() throws Exception {
        List<ParkingLot> parkingLots = new ArrayList<>();

        ParkingLot firstParkingLot = new ParkingLot(0);
        parkingLots.add(firstParkingLot);

        ParkingLot secondParkingLot = new ParkingLot(10);
        parkingLots.add(secondParkingLot);

        ParkingManager parkingBoy = new ParkingManager(parkingLots, new ArrayList<>());

        Car car = new Car();

        Ticket ticket = parkingBoy.park(car);

        assertThat(secondParkingLot.contains(ticket)).isTrue();

        Car takenCar = parkingBoy.take(ticket);

        assertThat(takenCar).isEqualTo(car);

        assertThat(secondParkingLot.contains(ticket)).isFalse();
    }

    @Test
    void throwCarNotFoundExceptionWhenCarNotFound() {
        List<ParkingLot> parkingLots = new ArrayList<>();

        ParkingLot firstParkingLot = new ParkingLot(10);
        parkingLots.add(firstParkingLot);

        ParkingLot secondParkingLot = new ParkingLot(10);
        parkingLots.add(secondParkingLot);

        ParkingManager parkingBoy = new ParkingManager(parkingLots, new ArrayList<>());

        assertThatThrownBy(() -> parkingBoy.take(new Ticket())).isInstanceOf(CarNotFoundException.class);
    }
}
