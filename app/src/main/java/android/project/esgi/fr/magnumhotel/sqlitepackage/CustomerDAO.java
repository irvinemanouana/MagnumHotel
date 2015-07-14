package android.project.esgi.fr.magnumhotel.sqlitepackage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.project.esgi.fr.magnumhotel.model.Customer;
import android.util.Log;

import java.util.ArrayList;

/**
 * Utilisation du patern DAO(Data Access Object)
 * Created by Sylvain on 14/07/15.
 */
public class CustomerDAO {

    // Base de données utilisable
    private SQLiteDatabase database = null;

    // Base de données inutilisable
    private MySqlLite mySqlLite;

    private static final String TABLE_CUSTOMER = "customer";

    public CustomerDAO(Context context){
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

    public void addCustomer(Customer customer){
        ContentValues values = new ContentValues();
        values.put(MySqlLite.KEY_CUSTOMER_LASTNAME,customer.getLastName());
        values.put(MySqlLite.KEY_CUSTOMER_FIRSTNAME,customer.getFirstName());
        values.put(MySqlLite.KEY_CUSTOMER_EMAIL,customer.getEmail());
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
        return customers;
    }

    public void updateCustomer(Customer customer){
        ContentValues values = new ContentValues();
        values.put(MySqlLite.KEY_CUSTOMER_LASTNAME, customer.getLastName());
        values.put(MySqlLite.KEY_CUSTOMER_FIRSTNAME, customer.getFirstName());
        values.put(MySqlLite.KEY_CUSTOMER_EMAIL,customer.getEmail());
        database.update(TABLE_CUSTOMER, values, MySqlLite.KEY_CUSTOMER_ID + " = ?", new String[] {
                String.valueOf(customer.getId())
        });
    }

    public void deleteCustomer(Customer customer){
        database.delete(TABLE_CUSTOMER, MySqlLite.KEY_CUSTOMER_ID+" = ?", new String[]{String.valueOf(customer.getId())});
    }

    // Transformer un cursor en un objet Customer
    private Customer cursorToCustomer(Cursor cursor){
        Customer customer = new Customer() ;
        customer.setId(cursor.getInt(MySqlLite.POSITION_CUSTOMER_ID));
        customer.setLastName(cursor.getString(MySqlLite.POSITION_CUSTOMER_LASTNAME));
        customer.setFirstName(cursor.getString(MySqlLite.POSITION_CUSTOMER_FIRSTNAME));
        customer.setEmail(cursor.getString(MySqlLite.POSITION_CUSTOMER_EMAIL));
        return customer;
    }
}
