package android.project.esgi.fr.magnumhotel.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.project.esgi.fr.magnumhotel.R;
import android.project.esgi.fr.magnumhotel.customList.CustomersListAdapter;
import android.project.esgi.fr.magnumhotel.model.Customer;
import android.project.esgi.fr.magnumhotel.sqlitepackage.CustomerDAO;
import android.project.esgi.fr.magnumhotel.sqlitepackage.MySqlLite;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Amélie on 13/07/2015.
 */
public class CustomerGestionActivity extends Activity {

    TextView textView;
    ListView listView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_gestion);

        this.initialize();
        this.actionBarSettings();

        CustomerDAO customerDAO = new CustomerDAO(this);
        customerDAO.open();
        final ArrayList<Customer> allCustomer = customerDAO.getCustomerList();
        customerDAO.close();
        int size = allCustomer.size();

        if(size <= 0) {
            textView.setText(getResources().getString(R.string.no_customer));
        } else {
            ArrayAdapter adapter = new CustomersListAdapter(getApplicationContext(), allCustomer);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Customer customer = (Customer) parent.getItemAtPosition(position);
                    Intent intent = new Intent(getApplicationContext(), DetailCustomerActivity.class);
                    intent.putExtra("Customer", customer);
                    startActivity(intent);
                }
            });
        }
    }

    private void initialize(){
        listView = (ListView) findViewById(R.id.allCustomer);
        textView = (TextView)findViewById(R.id.no_customer);
    }

    private void actionBarSettings(){
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setIcon(R.drawable.ic_action_logo);
            actionBar.setSplitBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void addNewCustomer(View view) {
        Intent addNewCustomer = new Intent(this, AddCustomerActivity.class);
        startActivity(addNewCustomer);
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

            default:
                finish();
        }
        return true;
    }
}
