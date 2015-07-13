package android.project.esgi.fr.magnumhotel;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.project.esgi.fr.magnumhotel.sqlitepackage.MySqlLite;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Amélie on 13/07/2015.
 */
public class NewCustomerActivity extends Activity {
    private MySqlLite mySqlLite;
    ActionBar actionBar;
    private EditText customerName, customerFirstName, customerEmail;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer);

        //ActionBar Settings
        actionBar = getActionBar();
        actionBar.setIcon(R.drawable.ic_action_logo);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setSplitBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));

        customerName = (EditText)findViewById(R.id.name);
        customerFirstName = (EditText)findViewById(R.id.firstname);
        customerEmail = (EditText)findViewById(R.id.email);
        addButton = (Button) findViewById(R.id.add_customer);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = customerName.getText().toString();
                String firstname = customerFirstName.getText().toString();
                String phoneNumber = customerEmail.getText().toString();
                Customer customer = new Customer(0, name, firstname, phoneNumber);
                mySqlLite = new MySqlLite(getApplicationContext());
                mySqlLite.addCustomer(customer);
                Intent intent = new Intent(getApplicationContext(), CustomerGestionActivity.class);
                startActivity(intent);
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
                Toast.makeText(getBaseContext(), "You selected customers", Toast.LENGTH_SHORT).show();
                break;

            case R.id.bookings:
                Toast.makeText(getBaseContext(), "You selected bookings", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
