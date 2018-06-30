import exceptions.AllParkingLotsIsFullException;
import exceptions.CarNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SuperParkingBoyTest {

    @Test
    void returnTicketWhenParkSuccess() throws Exception {
        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(new ParkingLot(10));

        SuperParkingBoy parkingBoy = new SuperParkingBoy(parkingLots);

        Ticket ticket = parkingBoy.park(new Car());

        assertThat(ticket).isNotNull();
    }

    @Test
    void containsCarWhenParkSuccessAndFirstParkingLotHasMostVacancyRate() throws Exception {
        List<ParkingLot> parkingLots = new ArrayList<>();

        ParkingLot firstParkingLot = new ParkingLot(3);
        firstParkingLot.receive(new Car());
        parkingLots.add(firstParkingLot);

        ParkingLot secondParkingLot = new ParkingLot(5);
        secondParkingLot.receive(new Car());
        secondParkingLot.receive(new Car());
        parkingLots.add(secondParkingLot);

        SuperParkingBoy parkingBoy = new SuperParkingBoy(parkingLots);

        Ticket ticket = parkingBoy.park(new Car());

        assertThat(firstParkingLot.contains(ticket)).isTrue();
        assertThat(secondParkingLot.contains(ticket)).isFalse();
    }

    @Test
    void containsCarWhenParkSuccessAndSecondParkingLotHasMostVacancyRate() throws Exception {
        List<ParkingLot> parkingLots = new ArrayList<>();

        ParkingLot firstParkingLot = new ParkingLot(5);
        firstParkingLot.receive(new Car());
        firstParkingLot.receive(new Car());
        parkingLots.add(firstParkingLot);

        ParkingLot secondParkingLot = new ParkingLot(3);
        secondParkingLot.receive(new Car());
        parkingLots.add(secondParkingLot);

        SuperParkingBoy parkingBoy = new SuperParkingBoy(parkingLots);

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

        SuperParkingBoy parkingBoy = new SuperParkingBoy(parkingLots);

        assertThatThrownBy(() -> parkingBoy.park(new Car())).isInstanceOf(AllParkingLotsIsFullException.class);
    }

    @Test
    void returnCarWhenTakeWithCorrectTicketAndCarInTheFirstParkingLot() throws Exception {
        List<ParkingLot> parkingLots = new ArrayList<>();

        ParkingLot firstParkingLot = new ParkingLot(3);
        firstParkingLot.receive(new Car());
        parkingLots.add(firstParkingLot);

        ParkingLot secondParkingLot = new ParkingLot(5);
        secondParkingLot.receive(new Car());
        secondParkingLot.receive(new Car());
        parkingLots.add(secondParkingLot);

        SuperParkingBoy parkingBoy = new SuperParkingBoy(parkingLots);

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

        ParkingLot firstParkingLot = new ParkingLot(5);
        firstParkingLot.receive(new Car());
        firstParkingLot.receive(new Car());
        parkingLots.add(firstParkingLot);

        ParkingLot secondParkingLot = new ParkingLot(3);
        secondParkingLot.receive(new Car());
        parkingLots.add(secondParkingLot);

        SuperParkingBoy parkingBoy = new SuperParkingBoy(parkingLots);

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

        SuperParkingBoy parkingBoy = new SuperParkingBoy(parkingLots);

        assertThatThrownBy(() -> parkingBoy.take(new Ticket())).isInstanceOf(CarNotFoundException.class);
    }
}
