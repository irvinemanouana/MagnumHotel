package android.project.esgi.fr.magnumhotel.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.project.esgi.fr.magnumhotel.R;
import android.project.esgi.fr.magnumhotel.model.Customer;
import android.project.esgi.fr.magnumhotel.dao.CustomerDAO;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Amélie on 13/07/2015.
 */
public class DetailCustomerActivity extends Activity {
    private TextView customerName, customerFirstname, customerEmail;
    private ImageView updateCustomer, deleteCustomer;
    private final Context context = this;
    private Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_customer);


        this.initialize();
        this.actionBarSettings();

        customer = (Customer)getIntent().getSerializableExtra("Customer");
        customerName.setText(customer.getLastName());
        customerFirstname.setText(customer.getFirstName());
        customerEmail.setText(customer.getEmail());
    }

    private void initialize(){
        customerName = (TextView)findViewById(R.id.customer_lastname);
        customerFirstname = (TextView)findViewById(R.id.customer_firstname);
        customerEmail = (TextView)findViewById(R.id.customer_email);
        updateCustomer = (ImageView)findViewById(R.id.update_customer);
        deleteCustomer = (ImageView)findViewById(R.id.delete_customer);
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

    public void updateCustomer(View view) {
        Intent intent = new Intent(getApplicationContext(), UpdateCustomerActivity.class);
        intent.putExtra("customer", customer);
        startActivity(intent);
    }

    public void deleteCustomer(View view) {
        new AlertDialog.Builder(context)
        .setTitle("Supprimer le client "+ customer.getLastName() + " " + customer.getFirstName())
        .setMessage(getResources().getString(R.string.delete_customer_message))
        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                CustomerDAO customerDAO = new CustomerDAO(DetailCustomerActivity.this);
                customerDAO.open();
                customerDAO.deleteCustomer(customer);
                customerDAO.close();
                Intent back = new Intent(getApplicationContext(), CustomerGestionActivity.class);
                startActivity(back);
            }
        })
        .setNegativeButton(android.R.string.no, null)
        .setIcon(android.R.drawable.ic_dialog_alert)
        .show();
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

    public int getCustomerId(){
        return customer.getId();
    }
}
