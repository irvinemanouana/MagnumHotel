package android.project.esgi.fr.magnumhotel.model;

import java.io.Serializable;

/**
 * Created by Sylvain on 03/07/2015.
 * cette classe donne la description d'une chambre
 */
public class Room implements Serializable {

    private int id;
    private String title;
    private String description;
    private int capacity;
    private float price;

    public Room(int id, String title, int capacity, String description, float price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.price = price;

    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public float getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
}
