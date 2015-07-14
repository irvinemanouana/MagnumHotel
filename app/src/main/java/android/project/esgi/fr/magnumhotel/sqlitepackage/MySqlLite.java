package android.project.esgi.fr.magnumhotel.sqlitepackage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.project.esgi.fr.magnumhotel.model.Customer;
import android.project.esgi.fr.magnumhotel.model.Room;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Christopher on 03/07/2015.
 */
public class MySqlLite extends SQLiteOpenHelper {

    // NOM DES TABLES
    private static final String DATABASE_NAME = "magnum";
    private static final String TABLE_ROOM = "room";
    private static final String TABLE_CUSTOMER = "customer";

    // CHAMPS DE LA TABLE ROOM
    private static String id_room = "id_room";
    private static String libelle_room ="libelle";
    private static String price ="price";
    private static String nb_place = "max_size";
    private static String description ="description";

    // CHAMPS DE LA TABLE CUSTOMER
    private static String id_customer = "id_customer";
    private static String lastname ="lastname";
    private static String firstname ="name";
    private static String email = "email";

    private static int version =1;

    private SQLiteDatabase db;

    public MySqlLite(Context context) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override // CREATION DES TABLES
    public void onCreate(SQLiteDatabase db) {
        String create_table_room = "CREATE TABLE " + TABLE_ROOM + "(" +
                id_room + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                libelle_room + " TEXT, " +
                nb_place + " INTEGER, " +
                price + " REAL, " +
                description + " TEXT)";

        String create_table_customer = "CREATE TABLE "+ TABLE_CUSTOMER + "(" +
                id_customer + " INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                lastname + " TEXT," +
                firstname + " TEXT," +
                email + " TEXT)";

        db.execSQL(create_table_room);
        db.execSQL(create_table_customer);

    }

    // CLIENT ---------------------

    public void addCustomer(Customer customer){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(lastname,customer.getLastName());
        values.put(firstname,customer.getFirstName());
        values.put(email,customer.getEmail());
        db.insert(TABLE_CUSTOMER, null, values);
        db.close();
    }

    public ArrayList<Customer> getCustomerList(){
        db = this.getWritableDatabase();
        ArrayList<Customer> customers = new ArrayList<>();
        String request = "SELECT * FROM " + TABLE_CUSTOMER;
        Cursor cursor = db.rawQuery(request,null);
        if (cursor.moveToFirst()){
            do{
                customers.add(cursorToCurstomer(cursor));
            } while (cursor.moveToNext());
        }
        return customers;
    }

    public void updateCustomer(Customer customer){
        Log.i("MySqlLite",customer.toString());
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(lastname, customer.getLastName());
        values.put(firstname, customer.getFirstName());
        values.put(email,customer.getEmail());
        db.update(TABLE_CUSTOMER, values, id_customer + " = ?", new String[] {
                String.valueOf(customer.getId())
        });
    }

    public void deleteCustomer(Customer customer){
        db = this.getWritableDatabase();
        db.delete(TABLE_CUSTOMER, "id_customer = ?", new String[]{String.valueOf(customer.getId())});
        db.close();
    }

    private Customer cursorToCurstomer(Cursor cursor){
        Customer customer = new Customer() ;
        customer.setId(cursor.getInt(0));
        customer.setLastName(cursor.getString(1));
        customer.setFirstName(cursor.getString(2));
        customer.setEmail(cursor.getString(3));
        return customer;
    }

    // CHAMBRE ---------------

    public void addRoom(Room room){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(libelle_room,room.getTitle());
        values.put(nb_place,room.getCapacity());
        values.put(price, room.getPrice());
        values.put(description,room.getDescription());
        db.insert(TABLE_ROOM, null, values);
        db.close();
    }

    public ArrayList<Room> getRoomList(){
        db = this.getWritableDatabase();
        ArrayList<Room> allRoomArrayList = new ArrayList<>();
        String request = "SELECT * FROM " + TABLE_ROOM;
        Cursor cursor = db.rawQuery(request,null);
        if (cursor.moveToFirst()){
            while (cursor.moveToNext()){
                allRoomArrayList.add(cursorToRoom(cursor));
            }
        }
        return allRoomArrayList;
    }

    public void updateRoom(Room room){
        Log.i("MySqlLite",room.toString());
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(libelle_room,room.getTitle());
        values.put(nb_place,room.getCapacity());
        values.put(description,room.getDescription());
        values.put(price, room.getPrice());
        db.update(TABLE_ROOM,values,id_room + " = ?",new String[] {
                String.valueOf(room.getId())
        });
    }

    public void deleteRoom(Room room){
        db = this.getWritableDatabase();
        db.delete(TABLE_ROOM, "id_room = ?", new String[]{String.valueOf(room.getId())});
        db.close();
    }

    private Room cursorToRoom(Cursor cursor){
        Room room = new Room() ;
        room.setId(cursor.getInt(0));
        room.setTitle(cursor.getString(1));
        room.setCapacity(cursor.getInt(2));
        room.setPrice(cursor.getFloat(3));
        room.setDescription(cursor.getString(4));

        return room;
    }

}
