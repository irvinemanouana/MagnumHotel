package android.project.esgi.fr.magnumhotel.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.project.esgi.fr.magnumhotel.R;
import android.project.esgi.fr.magnumhotel.customList.BookingsListAdapter;
import android.project.esgi.fr.magnumhotel.customList.CustomersListAdapter;
import android.project.esgi.fr.magnumhotel.model.Customer;
import android.project.esgi.fr.magnumhotel.model.Reservation;
import android.project.esgi.fr.magnumhotel.sqlitepackage.CustomerDAO;
import android.project.esgi.fr.magnumhotel.sqlitepackage.ReservationDAO;
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
 * Created by Amélie on 15/07/2015.
 */
public class BookingGestionActivity extends Activity {

    TextView textView;
    ListView listView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_gestion);

        this.initialize();
        this.actionBarSettings();

        ReservationDAO reservationDAO = new ReservationDAO(this);
        reservationDAO.open();
        final ArrayList<Reservation> allBooking = reservationDAO.getBookingList();
        reservationDAO.close();
        int size = allBooking.size();

        if(size <= 0) {
            textView.setText(getResources().getString(R.string.no_bookings));
        } else {
            ArrayAdapter adapter = new BookingsListAdapter(getApplicationContext(), allBooking);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Reservation booking = (Reservation) parent.getItemAtPosition(position);
                    Intent intent = new Intent(getApplicationContext(), DetailBookingActivity.class);
                    intent.putExtra("bookingId", booking.getId());
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
