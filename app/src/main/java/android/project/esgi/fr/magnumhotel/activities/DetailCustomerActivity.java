package android.project.esgi.fr.magnumhotel.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.project.esgi.fr.magnumhotel.R;
import android.project.esgi.fr.magnumhotel.model.Customer;
import android.project.esgi.fr.magnumhotel.sqlitepackage.MySqlLite;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Amélie on 13/07/2015.
 */
public class DetailCustomerActivity extends Activity {
    private MySqlLite database;
    private TextView customerName, customerFirstname, customerEmail;
    private ActionBar actionBar;
    private ImageView updateCustomer, deleteCustomer;
    private final Context context = this;
    private Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_customer);

        //ActionBar Settings
        actionBar = getActionBar();
        actionBar.setIcon(R.drawable.ic_action_logo);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setSplitBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));

        database = new MySqlLite(getApplicationContext());
        customerName =(TextView)findViewById(R.id.customer_name);
        customerFirstname =(TextView)findViewById(R.id.customer_firstname);
        customerEmail =(TextView)findViewById(R.id.customer_email);
        updateCustomer = (ImageView)findViewById(R.id.update_customer);
        deleteCustomer = (ImageView)findViewById(R.id.delete_customer);

        final Intent intent = getIntent();
        customer = (Customer)intent.getSerializableExtra("Customer");
        customerName.setText(customer.getLastName());
        customerFirstname.setText(customer.getFirstName());
        customerEmail.setText(customer.getEmail());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch(item.getItemId()){
            case R.id.home:
                Intent home = new Intent(this, MainActivity.class);
                startActivity(home);
                break;

            case R.id.rooms:
                Intent rooms = new Intent(this, RoomGestionActivity.class);
                startActivity(rooms);
                break;

            case R.id.customers:
                Intent customers = new Intent(this, CustomerGestionActivity.class);
                startActivity(customers);
                break;

            case R.id.bookings:
                Toast.makeText(getBaseContext(), "You selected bookings", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
