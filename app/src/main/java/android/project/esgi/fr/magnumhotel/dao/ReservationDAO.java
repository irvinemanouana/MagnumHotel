package android.project.esgi.fr.magnumhotel.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.project.esgi.fr.magnumhotel.model.Customer;
import android.project.esgi.fr.magnumhotel.model.Reservation;
import android.project.esgi.fr.magnumhotel.model.Room;
import android.project.esgi.fr.magnumhotel.others.Function;

import java.util.ArrayList;

/**
 * Created by Sylvain on 14/07/15.
 */
public class ReservationDAO {

    // Base de données utilisable
    private SQLiteDatabase database = null;

    // Base de données inutilisable
    private DataBaseHandler dataBaseHandler;

    public static final String TABLE_RESERVATION = "reservation";

    public ReservationDAO(Context context){
        dataBaseHandler = new DataBaseHandler(context);
    }

    /**
     * Permet à la base de données de faire des ajouts ou des suppressions
     * @return Une base de données modifiable (Écriture + lecture)
     */
    public SQLiteDatabase open() {
        this.database = dataBaseHandler.getWritableDatabase();
        return database;
    }

    /**
     * Permet de fermer la base de données
     */
    public void close() {
        dataBaseHandler.close();
    }

    public void addBooking(Reservation booking){
        ContentValues values = new ContentValues();
        values.put(DataBaseHandler.KEY_RESERVATION_CUSTOMER_ID, booking.getCustomerId());
        values.put(DataBaseHandler.KEY_RESERVATION_START_DAY, Function.dateToString(booking.getStartDate()));
        values.put(DataBaseHandler.KEY_RESERVATION_END_DAY, Function.dateToString(booking.getEndDate()));
        values.put(DataBaseHandler.KEY_RESERVATION_ROOM_ID, booking.getRoomId());
        database.insert(TABLE_RESERVATION, null, values);
    }

    public ArrayList<Reservation> getBookingList(){
        Reservation reservation;
        ArrayList<Reservation> bookings = new ArrayList<>();
        String request = "SELECT * FROM "+ TABLE_RESERVATION + ","+ DataBaseHandler.TABLE_CUSTOMER + ","+ DataBaseHandler.TABLE_ROOM +
                         " WHERE "+ TABLE_RESERVATION+"."+DataBaseHandler.KEY_RESERVATION_CUSTOMER_ID +
                                    " = "+ DataBaseHandler.TABLE_CUSTOMER + "."+DataBaseHandler.KEY_CUSTOMER_ID +
                         " AND "+ TABLE_RESERVATION+"."+DataBaseHandler.KEY_RESERVATION_ROOM_ID +
                                    " = "+ DataBaseHandler.TABLE_ROOM+ "."+DataBaseHandler.KEY_ROOM_ID;

        Cursor cursor = database.rawQuery(request, null);
        if (cursor.moveToFirst()){
            do{
                reservation = cursorToReservation(cursor);
                reservation.setCustomer(cursorToCustomer(cursor));
                reservation.setRoom(cursorToRoom(cursor));
                bookings.add(reservation);
            } while (cursor.moveToNext());
        }
        return bookings;
    }

    public ArrayList<Reservation> getBookingListByRoomId(int roomId){
        Reservation reservation;
        ArrayList<Reservation> bookings = new ArrayList<>();
        String request = "SELECT * FROM "+ TABLE_RESERVATION + ","+ DataBaseHandler.TABLE_CUSTOMER + ","+ DataBaseHandler.TABLE_ROOM +
                " WHERE "+ TABLE_RESERVATION+"."+DataBaseHandler.KEY_RESERVATION_CUSTOMER_ID +
                " = "+ DataBaseHandler.TABLE_CUSTOMER + "."+DataBaseHandler.KEY_CUSTOMER_ID +
                " AND "+ TABLE_RESERVATION+"."+DataBaseHandler.KEY_RESERVATION_ROOM_ID +
                " = "+ DataBaseHandler.TABLE_ROOM+ "."+DataBaseHandler.KEY_ROOM_ID +
                " AND " + TABLE_RESERVATION+"."+DataBaseHandler.KEY_RESERVATION_ROOM_ID +
                " = " + roomId;

        Cursor cursor = database.rawQuery(request, null);
        if (cursor.moveToFirst()){
            do{
                reservation = cursorToReservation(cursor);
                reservation.setCustomer(cursorToCustomer(cursor));
                reservation.setRoom(cursorToRoom(cursor));
                bookings.add(reservation);
            } while (cursor.moveToNext());
        }
        return bookings;
    }

    public ArrayList<Reservation> getBookingDateListByRoomId(int roomId){
        Reservation reservation;
        ArrayList<Reservation> bookings = new ArrayList<>();
        String request = "SELECT "+ DataBaseHandler.POSITION_RESERVATION_START_DAY+"," +DataBaseHandler.POSITION_RESERVATION_END_DAY+
                " FROM "+ TABLE_RESERVATION + ","+ DataBaseHandler.TABLE_ROOM +
                " WHERE "+ TABLE_RESERVATION+"."+DataBaseHandler.KEY_RESERVATION_ROOM_ID +
                " = "+ DataBaseHandler.TABLE_ROOM+ "."+DataBaseHandler.KEY_ROOM_ID +
                " AND " + TABLE_RESERVATION+"."+DataBaseHandler.KEY_RESERVATION_ROOM_ID +
                " = " + roomId;

        Cursor cursor = database.rawQuery(request, null);
        if (cursor.moveToFirst()){
            do{
                reservation = cursorToReservation(cursor);
                reservation.setRoom(cursorToRoom(cursor));
                bookings.add(reservation);
            } while (cursor.moveToNext());
        }
        return bookings;
    }

    public ArrayList<Reservation> getBookingListByCustomerId(int customerId){
        Reservation reservation;
        ArrayList<Reservation> bookings = new ArrayList<>();
        String request = "SELECT * FROM "+ TABLE_RESERVATION + ","+ DataBaseHandler.TABLE_CUSTOMER + ","+ DataBaseHandler.TABLE_ROOM +
                " WHERE "+ TABLE_RESERVATION+"."+DataBaseHandler.KEY_RESERVATION_CUSTOMER_ID +
                " = "+ DataBaseHandler.TABLE_CUSTOMER + "."+DataBaseHandler.KEY_CUSTOMER_ID +
                " AND "+ TABLE_RESERVATION+"."+DataBaseHandler.KEY_RESERVATION_ROOM_ID +
                " = "+ DataBaseHandler.TABLE_ROOM+ "."+DataBaseHandler.KEY_ROOM_ID +
                " AND " + TABLE_RESERVATION+"."+DataBaseHandler.KEY_RESERVATION_CUSTOMER_ID +
                " = " + customerId;

        Cursor cursor = database.rawQuery(request, null);
        if (cursor.moveToFirst()){
            do{
                reservation = cursorToReservation(cursor);
                reservation.setCustomer(cursorToCustomer(cursor));
                reservation.setRoom(cursorToRoom(cursor));
                bookings.add(reservation);
            } while (cursor.moveToNext());
        }
        return bookings;
    }

    public void updateBooking(Reservation booking){
        ContentValues values = new ContentValues();
        values.put(DataBaseHandler.KEY_RESERVATION_CUSTOMER_ID, booking.getCustomerId());
        values.put(DataBaseHandler.KEY_RESERVATION_START_DAY, Function.dateToString(booking.getStartDate()));
        values.put(DataBaseHandler.KEY_RESERVATION_END_DAY, Function.dateToString(booking.getEndDate()));
        values.put(DataBaseHandler.KEY_RESERVATION_ROOM_ID,booking.getRoomId());
        database.update(TABLE_RESERVATION, values, DataBaseHandler.KEY_RESERVATION_ID + " = ?", new String[] {
                String.valueOf(booking.getId())
        });
    }

    public void deleteBooking(Reservation booking){
        database.delete(TABLE_RESERVATION, DataBaseHandler.KEY_RESERVATION_ID + " = ?", new String[]{String.valueOf(booking.getId())});
    }

    // Transformer un cursor en un objet Customer
    private Reservation cursorToReservation(Cursor cursor){
        Reservation booking = new Reservation() ;
        booking.setId(cursor.getInt(DataBaseHandler.POSITION_RESERVATION_ID));
        booking.setStartDate(Function.stringToDate(cursor.getString(DataBaseHandler.POSITION_RESERVATION_START_DAY)));
        booking.setEndDate(Function.stringToDate(cursor.getString(DataBaseHandler.POSITION_RESERVATION_END_DAY)));
        booking.setRoomId(cursor.getInt(DataBaseHandler.POSITION_RESERVATION_ROOM_ID));
        booking.setCustomerId(cursor.getInt(DataBaseHandler.POSITION_RESERVATION_CUSTOMER_ID));
        return booking;
    }

    private Customer cursorToCustomer(Cursor cursor){
        Customer customer = new Customer();
        customer.setId(cursor.getInt(5));
        customer.setLastName(cursor.getString(6));
        customer.setFirstName(cursor.getString(7));
        customer.setEmail(cursor.getString(8));
        return customer;
    }

    private Room cursorToRoom(Cursor cursor){
        Room room = new Room() ;
        room.setId(cursor.getInt(9));
        room.setTitle(cursor.getString(10));
        room.setCapacity(cursor.getInt(11));
        room.setPrice(cursor.getFloat(12));
        room.setDescription(cursor.getString(13));
        room.setFloor(cursor.getInt(14));
        room.setImageLink(cursor.getString(15));
        return room;
    }
}
