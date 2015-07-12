package android.project.esgi.fr.magnumhotel;

/**
 * Created by Sylvain on 03/07/15.
 */
public class Reservation {

    int id;
    Customer customer;
    Room room;
    String startDate;
    String endDate;

    public Reservation(int id, Customer customer, Room room, String startDate, String endDate) {
        this.id = id;
        this.customer = customer;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}