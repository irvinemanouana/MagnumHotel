package android.project.esgi.fr.magnumhotel.sqlitepackage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.project.esgi.fr.magnumhotel.Customer;
import android.project.esgi.fr.magnumhotel.Room;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Christopher on 03/07/2015.
 */
public class MySqlLite extends SQLiteOpenHelper {
    private static final  String db_name ="magnum";
    private static final String db_table_room = "room";
    private static final String db_table_customer="customer";
    private static String id_room = "id_room";
    private static String libelle_room ="libelle";
    private static String nb_place = "max_size";
    private static String description ="description";
    private static String id_customer = "id_customer";
    private static String price ="price";
    private static String name ="name";
    private static String lastname ="lastname";
    private static String email = "email";

    private static int version =1;
    private SQLiteDatabase db;

    public MySqlLite(Context context) {
        super(context, db_name, null, version);
    }
    public void deleteRoom(Room room){
        db = this.getWritableDatabase();
        db.delete(db_table_room, "id_room = ?", new String[]{String.valueOf(room.getId())});
        db.close();
    }
    public void deleteCustomer(Customer customer){
        db = this.getWritableDatabase();
        db.delete(db_table_customer, "id_room = ?", new String[]{String.valueOf(customer.getId())});
        db.close();
    }

    public void addRoom(Room room){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(libelle_room,room.getTitle());
        values.put(nb_place,room.getNbplace());
        values.put(description,room.getDescription());
        values.put(price, room.getPrice());
        db.insert(db_table_room, null, values);
        db.close();
    }

    public void addCustomer(Customer customer){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(name,customer.getFirstName());
        values.put(lastname,customer.getLastName());
        values.put(email,customer.getEmail());
        db.insert(db_table_customer, null, values);
        db.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table_room = "CREATE TABLE " + db_table_room+"(" + id_room + " INTEGER PRIMARY KEY, " +
                libelle_room + " TEXT, " +
                nb_place + " INTEGER , " +
                description+
                " TEXT," +
                price + " INTEGER)";

        String create_table_customer = "CREATE TABLE "+ db_table_customer + "(" + id_customer + " INTEGER PRIMARY KEY,"+
                name + " TEXT," +
                lastname + " TEXT," +
                email + " TEXT)";

        db.execSQL(create_table_room);
        db.execSQL(create_table_customer);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<Room> roomArrayList(){
        db = this.getWritableDatabase();
        ArrayList<Room> allRoomArrayList = new ArrayList();
        String request = "SELECT * FROM " + db_table_room;
        Cursor cursor = db.rawQuery(request,null);
        if (cursor.moveToFirst()){
            while (cursor.moveToNext()){
                Room room = new Room(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3),cursor.getInt(4));
                allRoomArrayList.add(room);
            }
        }
        return allRoomArrayList;
    }

    public ArrayList<Customer> customerArrayList(){
        db = this.getWritableDatabase();
        ArrayList<Customer> customers = new ArrayList<>();
        String request = "SELECT * FROM " + db_table_customer;
        Cursor cursor = db.rawQuery(request,null);
        if (cursor.moveToFirst()){
            do{
                Customer customer = new Customer(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
                customers.add(customer);
            } while (cursor.moveToNext());
        }
        return customers;
    }

    public void updateRoom(Room room){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(libelle_room,room.getTitle());
        values.put(nb_place,room.getNbplace());
        values.put(description,room.getDescription());
        values.put(price, room.getPrice());
        db.update(db_table_room,values,id_room + " = ?",new String[] {
                String.valueOf(room.getId())
        });
    }
}
