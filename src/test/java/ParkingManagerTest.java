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

        ParkingManager parkingManager = new ParkingManager(parkingLots, new ArrayList<>());

        Ticket ticket = parkingManager.park(new Car());

        assertThat(ticket).isNotNull();
    }

    @Test
    void containsCarWhenParkSuccessAndFirstParkingLotHasSpace() throws Exception {
        List<ParkingLot> parkingLots = new ArrayList<>();

        ParkingLot firstParkingLot = new ParkingLot(10);
        parkingLots.add(firstParkingLot);

        ParkingLot secondParkingLot = new ParkingLot(10);
        parkingLots.add(secondParkingLot);

        ParkingManager parkingManager = new ParkingManager(parkingLots, new ArrayList<>());

        Ticket ticket = parkingManager.park(new Car());

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

        ParkingManager parkingManager = new ParkingManager(parkingLots, new ArrayList<>());

        Ticket ticket = parkingManager.park(new Car());

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

        ParkingManager parkingManager = new ParkingManager(parkingLots, new ArrayList<>());

        assertThatThrownBy(() -> parkingManager.park(new Car())).isInstanceOf(AllParkingLotsIsFullException.class);
    }

    @Test
    void returnCarWhenTakeWithCorrectTicketAndCarInTheFirstParkingLot() throws Exception {
        List<ParkingLot> parkingLots = new ArrayList<>();

        ParkingLot firstParkingLot = new ParkingLot(10);
        parkingLots.add(firstParkingLot);

        ParkingLot secondParkingLot = new ParkingLot(10);
        parkingLots.add(secondParkingLot);

        ParkingManager parkingManager = new ParkingManager(parkingLots, new ArrayList<>());

        Car car = new Car();

        Ticket ticket = parkingManager.park(car);

        assertThat(firstParkingLot.contains(ticket)).isTrue();

        Car takenCar = parkingManager.take(ticket);

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

        ParkingManager parkingManager = new ParkingManager(parkingLots, new ArrayList<>());

        Car car = new Car();

        Ticket ticket = parkingManager.park(car);

        assertThat(secondParkingLot.contains(ticket)).isTrue();

        Car takenCar = parkingManager.take(ticket);

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

        ParkingManager parkingManager = new ParkingManager(parkingLots, new ArrayList<>());

        assertThatThrownBy(() -> parkingManager.take(new Ticket())).isInstanceOf(CarNotFoundException.class);
    }

    @Test
    void returnTicketWhenParkByParkerSuccess() throws Exception {
        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(new ParkingLot(10));

        Parker parker = new ParkingBoy(parkingLots);
        List<Parker> parkers = new ArrayList<>();
        parkers.add(parker);

        ParkingManager parkingManager = new ParkingManager(new ArrayList<>(), parkers);

        Ticket ticket = parkingManager.parkBy(parker, new Car());

        assertThat(ticket).isNotNull();
    }

    @Test
    void throwAllParkingLotsIsFullExceptionWhenParkersAllParkingLotsIsFull() {
        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(new ParkingLot(0));

        Parker parker = new ParkingBoy(parkingLots);
        List<Parker> parkers = new ArrayList<>();
        parkers.add(parker);

        ParkingManager parkingManager = new ParkingManager(new ArrayList<>(), parkers);

        assertThatThrownBy(() -> parkingManager.parkBy(parker, new Car())).isInstanceOf(AllParkingLotsIsFullException.class);
    }

    @Test
    void returnCarWhenTakeByParkerWithCorrectTicket() throws Exception {
        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(new ParkingLot(10));

        Parker parker = new ParkingBoy(parkingLots);
        List<Parker> parkers = new ArrayList<>();
        parkers.add(parker);

        ParkingManager parkingManager = new ParkingManager(new ArrayList<>(), parkers);

        Ticket ticket = parkingManager.parkBy(parker, new Car());

        Car takenCar = parkingManager.takeBy(ticket);

        assertThat(takenCar).isNotNull();
    }

    @Test
    void throwCarNotFoundExceptionWhenParkByParkerButCarNotFound() {
        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(new ParkingLot(10));

        Parker parker = new ParkingBoy(parkingLots);
        List<Parker> parkers = new ArrayList<>();
        parkers.add(parker);

        ParkingManager parkingManager = new ParkingManager(new ArrayList<>(), parkers);

        assertThatThrownBy(() -> parkingManager.takeBy(new Ticket())).isInstanceOf(CarNotFoundException.class);
    }
}
