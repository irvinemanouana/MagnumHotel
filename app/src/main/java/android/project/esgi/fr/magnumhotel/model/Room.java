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
    private int nbplace;
    private int price;

    public Room(int id, String title, int nbplace, String description,int price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.nbplace = nbplace;
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
    public int getNbplace() {
        return nbplace;
    }
    public void setNbplace(int nbplace) {
        this.nbplace = nbplace;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
}
