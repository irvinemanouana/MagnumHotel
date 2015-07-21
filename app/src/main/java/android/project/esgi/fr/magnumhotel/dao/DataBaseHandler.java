package android.project.esgi.fr.magnumhotel.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Christopher on 03/07/2015.
 */
public class DataBaseHandler extends SQLiteOpenHelper {

    // BASE DE DONNEES
    private static final String DATABASE_NAME = "magnum";

    // NOM DES TABLES
    public static final String TABLE_ROOM = "room";
    public static final String TABLE_CUSTOMER = "customer";
    public static final String TABLE_RESERVATION = "reservation";

    // LES CHAMPS

        // CHAMBRE
        public static final String KEY_ROOM_ID = "room_id";
        public static final String KEY_ROOM_TITLE ="room_title";
        public static final String KEY_ROOM_PRICE ="room_price";
        public static final String KEY_ROOM_CAPACITY = "room_capacity";
        public static final String KEY_ROOM_DESCRIPTION ="room_description";
        public static final String KEY_ROOM_FLOOR ="room_floor";
        public static final String KEY_ROOM_PICTURE ="room_picture";

        // CLIENT
        public static final String KEY_CUSTOMER_ID = "customer_id";
        public static final String KEY_CUSTOMER_LASTNAME = "customer_lastname";
        public static final String KEY_CUSTOMER_FIRSTNAME = "customer_firstname";
        public static final String KEY_CUSTOMER_EMAIL = "customer_email";

        // RESERVATION
        public static final String KEY_RESERVATION_ID = "reservation_id";
        public static final String KEY_RESERVATION_START_DAY = "reservation_start_day";
        public static final String KEY_RESERVATION_END_DAY = "reservation_end_date";
        public static final String KEY_RESERVATION_ROOM_ID = "reservation_room_id";
        public static final String KEY_RESERVATION_CUSTOMER_ID = "reservation_customer_id";

    // POSITION DES CHAMPS DANS LEURS TABLES

        // CHAMBRE
        public static final int POSITION_ROOM_ID = 0;
        public static final int POSITION_ROOM_TITLE = 1;
        public static final int POSITION_ROOM_CAPACITY = 2;
        public static final int POSITION_ROOM_PRICE = 3;
        public static final int POSITION_ROOM_DESCRIPTION = 4;
        public static final int POSITION_ROOM_FLOOR = 5;
        public static final int POSITION_ROOM_PICTURE = 6;

        // CLIENT
        public static final int POSITION_CUSTOMER_ID = 0;
        public static final int POSITION_CUSTOMER_LASTNAME = 1;
        public static final int POSITION_CUSTOMER_FIRSTNAME = 2;
        public static final int POSITION_CUSTOMER_EMAIL = 3;

        // RESERVATION
        public static final int POSITION_RESERVATION_ID = 0;
        public static final int POSITION_RESERVATION_START_DAY = 1;
        public static final int POSITION_RESERVATION_END_DAY = 2;
        public static final int POSITION_RESERVATION_ROOM_ID = 3;
        public static final int POSITION_RESERVATION_CUSTOMER_ID = 4;

    private static final int VERSION = 1;

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // SUPPRIMER lES ANCIENNES TABLES s'il en existe
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROOM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESERVATION);

        // RECRÉER LA TABLE
        onCreate(db);
    }

    @Override // CREATION DES TABLES
    public void onCreate(SQLiteDatabase db) {

        // CHAMBRE
        final String create_table_room = "CREATE TABLE " + TABLE_ROOM + "(" +
                KEY_ROOM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                KEY_ROOM_TITLE + " TEXT, " +
                KEY_ROOM_CAPACITY + " INTEGER, " +
                KEY_ROOM_PRICE + " REAL, " +
                KEY_ROOM_DESCRIPTION + " TEXT, " +
                KEY_ROOM_FLOOR + " INTEGER, " +
                KEY_ROOM_PICTURE + " TEXT)";

        // CLIENT
        final String create_table_customer = "CREATE TABLE "+ TABLE_CUSTOMER + "(" +
                KEY_CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                KEY_CUSTOMER_LASTNAME + " TEXT," +
                KEY_CUSTOMER_FIRSTNAME + " TEXT," +
                KEY_CUSTOMER_EMAIL + " TEXT)";

        // RÉSERVATION
        final String create_table_booking = "CREATE TABLE "+ TABLE_RESERVATION + "(" +
                KEY_RESERVATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                KEY_RESERVATION_START_DAY + " TEXT," +
                KEY_RESERVATION_END_DAY + " TEXT," +
                KEY_RESERVATION_ROOM_ID + " INTEGER," +
                KEY_RESERVATION_CUSTOMER_ID + " INTEGER," +
                "FOREIGN KEY (" + KEY_RESERVATION_CUSTOMER_ID + ") REFERENCES " +
                TABLE_CUSTOMER + "(" + KEY_CUSTOMER_ID + ")," +
                "FOREIGN KEY (" + KEY_RESERVATION_ROOM_ID + ") REFERENCES " +
                TABLE_ROOM + "(" + KEY_ROOM_ID + "))";

        db.execSQL(create_table_room);
        db.execSQL(create_table_customer);
        db.execSQL(create_table_booking);

    }

}
