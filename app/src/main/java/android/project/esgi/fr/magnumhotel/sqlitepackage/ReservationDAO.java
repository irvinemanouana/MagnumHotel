package android.project.esgi.fr.magnumhotel.sqlitepackage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.project.esgi.fr.magnumhotel.model.Customer;
import android.project.esgi.fr.magnumhotel.model.Reservation;

import java.util.ArrayList;

/**
 * Created by Sylvain on 14/07/15.
 */
public class ReservationDAO {

    // Base de données utilisable
    private SQLiteDatabase database = null;

    // Base de données inutilisable
    private MySqlLite mySqlLite;

    private static final String TABLE_BOOKING = "booking";

    public ReservationDAO(Context context){
        mySqlLite = new MySqlLite(context);
    }

    /**
     * Permet à la base de données de faire des ajouts ou des suppressions
     * @return Une base de données modifiable (Écriture + lecture)
     */
    public SQLiteDatabase open() {
        this.database = mySqlLite.getWritableDatabase();
        return database;
    }

    /**
     * Permet de fermer la base de données
     */
    public void close() {
        mySqlLite.close();
    }

    public void addBooking(Reservation booking){
        ContentValues values = new ContentValues();
        values.put(MySqlLite.KEY_RESERVATION_CUSTOMER_ID, booking.getCustomerId());
        values.put(MySqlLite.KEY_RESERVATION_START_DAY, booking.getStartDate());
        values.put(MySqlLite.KEY_RESERVATION_END_DAY, booking.getEndDate());
        values.put(MySqlLite.KEY_RESERVATION_ROOM_ID, booking.getRoomId());
        database.insert(TABLE_BOOKING, null, values);
    }

    public ArrayList<Reservation> getBookingList(){
        ArrayList<Reservation> bookings = new ArrayList<>();
        String request = "SELECT * FROM " + TABLE_BOOKING;
        Cursor cursor = database.rawQuery(request,null);
        if (cursor.moveToFirst()){
            do{
                bookings.add(cursorToReservation(cursor));
            } while (cursor.moveToNext());
        }
        return bookings;
    }

    public void updateBooking(Reservation booking){
        ContentValues values = new ContentValues();
        values.put(MySqlLite.KEY_RESERVATION_CUSTOMER_ID, booking.getCustomerId());
        values.put(MySqlLite.KEY_RESERVATION_START_DAY, booking.getStartDate());
        values.put(MySqlLite.KEY_RESERVATION_END_DAY,booking.getEndDate());
        values.put(MySqlLite.KEY_RESERVATION_ROOM_ID,booking.getRoomId());
        database.update(TABLE_BOOKING, values, MySqlLite.KEY_RESERVATION_ID + " = ?", new String[] {
                String.valueOf(booking.getId())
        });
    }

    public void deleteBooking(Reservation booking){
        database.delete(TABLE_BOOKING, MySqlLite.KEY_RESERVATION_ID+" = ?", new String[]{String.valueOf(booking.getId())});
    }

    // Transformer un cursor en un objet Customer
    private Reservation cursorToReservation(Cursor cursor){
        Reservation booking = new Reservation() ;
        booking.setId(cursor.getInt(MySqlLite.POSITION_RESERVATION_CUSTOMER_ID));
        booking.setStartDate(cursor.getString(MySqlLite.POSITION_RESERVATION_START_DAY));
        booking.setEndDate(cursor.getString(MySqlLite.POSITION_RESERVATION_END_DAY));
        booking.setRoomId(cursor.getInt(MySqlLite.POSITION_RESERVATION_ROOM_ID));
        return booking;
    }
}
