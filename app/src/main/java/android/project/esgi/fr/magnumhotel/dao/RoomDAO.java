package android.project.esgi.fr.magnumhotel.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.project.esgi.fr.magnumhotel.model.Room;

import java.util.ArrayList;

/**
 * Created by Sylvain on 14/07/15.
 */
public class RoomDAO {

    // Base de données utilisable
    private SQLiteDatabase database = null;

    // Base de données inutilisable
    private DataBaseHandler dataBaseHandler;

    private static final String TABLE_ROOM = "room";

    public RoomDAO(Context context){
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

    public void addRoom(Room room){
        ContentValues values = new ContentValues();
        values.put(DataBaseHandler.KEY_ROOM_TITLE,room.getTitle());
        values.put(DataBaseHandler.KEY_ROOM_CAPACITY,room.getCapacity());
        values.put(DataBaseHandler.KEY_ROOM_PRICE, room.getPrice());
        values.put(DataBaseHandler.KEY_ROOM_DESCRIPTION, room.getDescription());
        values.put(DataBaseHandler.KEY_ROOM_FLOOR, room.getImageLink());
        values.put(DataBaseHandler.KEY_ROOM_PICTURE, room.getImageLink());

        database.insert(TABLE_ROOM, null, values);
    }

    public ArrayList<Room> getRoomList(){
        ArrayList<Room> allRoomArrayList = new ArrayList<>();
        String request = "SELECT * FROM " + TABLE_ROOM;
        Cursor cursor = database.rawQuery(request,null);
        if (cursor.moveToFirst()){
            do{
                allRoomArrayList.add(cursorToRoom(cursor));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return allRoomArrayList;
    }


    public void updateRoom(Room room){

        ContentValues values = new ContentValues();
        values.put(DataBaseHandler.KEY_ROOM_TITLE,room.getTitle());
        values.put(DataBaseHandler.KEY_ROOM_CAPACITY,room.getCapacity());
        values.put(DataBaseHandler.KEY_ROOM_PRICE, room.getPrice());
        values.put(DataBaseHandler.KEY_ROOM_DESCRIPTION,room.getDescription());
        values.put(DataBaseHandler.KEY_ROOM_FLOOR,room.getImageLink());
        values.put(DataBaseHandler.KEY_ROOM_PICTURE,room.getImageLink());
        database.update(TABLE_ROOM,values, DataBaseHandler.KEY_ROOM_ID + " = ?",new String[] {
                String.valueOf(room.getId())
        });
    }

    public void deleteRoom(Room room){
        database.delete(TABLE_ROOM, "ROOM_ID = ?", new String[]{String.valueOf(room.getId())});
    }

    private Room cursorToRoom(Cursor cursor){
        Room room = new Room() ;
        room.setId(cursor.getInt(DataBaseHandler.POSITION_ROOM_ID));
        room.setTitle(cursor.getString(DataBaseHandler.POSITION_ROOM_TITLE));
        room.setCapacity(cursor.getInt(DataBaseHandler.POSITION_ROOM_CAPACITY));
        room.setPrice(cursor.getFloat(DataBaseHandler.POSITION_ROOM_PRICE));
        room.setDescription(cursor.getString(DataBaseHandler.POSITION_ROOM_DESCRIPTION));
        room.setFloor(cursor.getInt(DataBaseHandler.POSITION_ROOM_FLOOR));
        room.setImageLink(cursor.getString(DataBaseHandler.POSITION_ROOM_PICTURE));
        return room;
    }

}
