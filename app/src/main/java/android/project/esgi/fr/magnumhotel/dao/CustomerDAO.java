package android.project.esgi.fr.magnumhotel.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.project.esgi.fr.magnumhotel.model.Customer;

import java.util.ArrayList;

/**
 * Utilisation du patern DAO(Data Access Object)
 * Created by Sylvain on 14/07/15.
 */
public class CustomerDAO {

    // Base de données utilisable
    private SQLiteDatabase database = null;

    // Base de données inutilisable
    private DataBaseHandler dataBaseHandler;

    private static final String TABLE_CUSTOMER = "customer";

    // LES COLONNES
    private String[] allColumns = { DataBaseHandler.KEY_CUSTOMER_ID,
                                    DataBaseHandler.KEY_CUSTOMER_LASTNAME,
                                    DataBaseHandler.KEY_CUSTOMER_FIRSTNAME,
                                    DataBaseHandler.KEY_CUSTOMER_EMAIL};

    public CustomerDAO(Context context){
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

    public void addCustomer(Customer customer){
        ContentValues values = new ContentValues();
        values.put(DataBaseHandler.KEY_CUSTOMER_LASTNAME,customer.getLastName());
        values.put(DataBaseHandler.KEY_CUSTOMER_FIRSTNAME,customer.getFirstName());
        values.put(DataBaseHandler.KEY_CUSTOMER_EMAIL,customer.getEmail());
        database.insert(TABLE_CUSTOMER, null, values);
    }

    public ArrayList<Customer> getCustomerList(){
        ArrayList<Customer> customers = new ArrayList<>();
        String request = "SELECT * FROM " + TABLE_CUSTOMER;
        Cursor cursor = database.rawQuery(request,null);
        if (cursor.moveToFirst()){
            do{
                customers.add(cursorToCustomer(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return customers;
    }

    public int getCustomerCount(){

        String request = " SELECT "+DataBaseHandler.KEY_CUSTOMER_ID+
                         " FROM " + TABLE_CUSTOMER;
        Cursor cursor = database.rawQuery(request,null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public Customer getCustomer(int customerId){

        Customer customer = new Customer();

        Cursor cursor = database.query(TABLE_CUSTOMER, allColumns, DataBaseHandler.KEY_CUSTOMER_ID + " = " + customerId, null, null, null, null);
        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            customer = cursorToCustomer(cursor);
        }

        cursor.close();

        return customer;
    }

    public void updateCustomer(Customer customer){
        ContentValues values = new ContentValues();
        values.put(DataBaseHandler.KEY_CUSTOMER_LASTNAME, customer.getLastName());
        values.put(DataBaseHandler.KEY_CUSTOMER_FIRSTNAME, customer.getFirstName());
        values.put(DataBaseHandler.KEY_CUSTOMER_EMAIL,customer.getEmail());
        database.update(TABLE_CUSTOMER, values, DataBaseHandler.KEY_CUSTOMER_ID + " = ?", new String[] {
                String.valueOf(customer.getId())
        });
    }

    public void deleteCustomer(Customer customer){
        database.delete(TABLE_CUSTOMER, DataBaseHandler.KEY_CUSTOMER_ID+" = ?", new String[]{String.valueOf(customer.getId())});
    }

    // Transformer un cursor en un objet Customer
    private Customer cursorToCustomer(Cursor cursor){
        Customer customer = new Customer() ;
        customer.setId(cursor.getInt(DataBaseHandler.POSITION_CUSTOMER_ID));
        customer.setLastName(cursor.getString(DataBaseHandler.POSITION_CUSTOMER_LASTNAME));
        customer.setFirstName(cursor.getString(DataBaseHandler.POSITION_CUSTOMER_FIRSTNAME));
        customer.setEmail(cursor.getString(DataBaseHandler.POSITION_CUSTOMER_EMAIL));
        return customer;
    }
}
