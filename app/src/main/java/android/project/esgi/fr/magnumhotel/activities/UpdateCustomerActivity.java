package android.project.esgi.fr.magnumhotel.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.project.esgi.fr.magnumhotel.R;
import android.project.esgi.fr.magnumhotel.activities.CustomerGestionActivity;
import android.project.esgi.fr.magnumhotel.activities.MainActivity;
import android.project.esgi.fr.magnumhotel.activities.RoomGestionActivity;
import android.project.esgi.fr.magnumhotel.model.Customer;
import android.project.esgi.fr.magnumhotel.sqlitepackage.MySqlLite;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Amélie on 14/07/2015.
 */
public class UpdateCustomerActivity extends Activity {

    EditText editName, editFirstname, editEmail;
    Button updateCustomer;
    private MySqlLite database;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_customer);

        actionBar = getActionBar();
        actionBar.setIcon(R.drawable.ic_action_logo);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setSplitBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));

        final Intent intent = getIntent();
        final Customer customer = (Customer) intent.getSerializableExtra("customer");
        database = new MySqlLite(getApplicationContext());
        editName = (EditText) findViewById(R.id.name);
        editName.setText(customer.getLastName());
        editFirstname = (EditText) findViewById(R.id.firstname);
        editFirstname.setText(customer.getFirstName());
        editEmail = (EditText) findViewById(R.id.email);
        editEmail.setText(customer.getEmail());
        updateCustomer = (Button) findViewById(R.id.update_customer);

        updateCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();
                String firstName = editFirstname.getText().toString();
                String email = editEmail.getText().toString();
                Customer customer1 = new Customer(customer.getId(), name, firstName, email);
                database.updateCustomer(customer1);
                Intent intent1 = new Intent(getApplicationContext(), CustomerGestionActivity.class);
                startActivity(intent1);
                finish();
            }
        });
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
