package android.project.esgi.fr.magnumhotel.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.project.esgi.fr.magnumhotel.R;
import android.project.esgi.fr.magnumhotel.model.Customer;
import android.project.esgi.fr.magnumhotel.model.Reservation;
import android.project.esgi.fr.magnumhotel.others.Function;
import android.project.esgi.fr.magnumhotel.sqlitepackage.CustomerDAO;
import android.project.esgi.fr.magnumhotel.sqlitepackage.ReservationDAO;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Amélie on 14/07/2015.
 */
public class AddBookingActivity extends Activity {

    // ELEMENT DE VUE
    TextView customerName, customerFirstname;
    DatePicker arrivalDay, departureDay;
    Spinner addRoom;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings_form);

        this.initialize(); // Initialisation des elements de la vue
        this.actionBarSettings(); //Configuration de l'action bar

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Reservation booking = new Reservation(1, 2, arrivalDay.getDayOfMonth() + "/" + arrivalDay.getMonth() + "/" + arrivalDay.getYear(), departureDay.getDayOfMonth() + "/" + departureDay.getMonth() + "/" + departureDay.getYear());
                ReservationDAO bookingDAO = new ReservationDAO(AddBookingActivity.this);
                bookingDAO.open();
                bookingDAO.addBooking(booking);
                bookingDAO.close();
                Intent intent = new Intent(AddBookingActivity.this, BookingGestionActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initialize(){
        customerName = (TextView) findViewById(R.id.customer_name);
        customerFirstname = (TextView) findViewById(R.id.customer_firstname);
        arrivalDay = (DatePicker)findViewById(R.id.arrival_day);
        departureDay = (DatePicker)findViewById(R.id.departure_day);
        addRoom = (Spinner)findViewById(R.id.choose_room);
        addButton = (Button) findViewById(R.id.submit_booking);
    }

    private void actionBarSettings(){
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setIcon(R.drawable.ic_action_logo);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setSplitBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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
