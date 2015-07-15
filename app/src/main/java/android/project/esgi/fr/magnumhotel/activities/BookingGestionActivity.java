package android.project.esgi.fr.magnumhotel.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.project.esgi.fr.magnumhotel.R;
import android.project.esgi.fr.magnumhotel.adapter.BookingsListAdapter;
import android.project.esgi.fr.magnumhotel.model.Reservation;
import android.project.esgi.fr.magnumhotel.dao.ReservationDAO;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Amélie on 15/07/2015.
 */
public class BookingGestionActivity extends Activity {

    TextView emptyText;
    ListView bookingList;
    Button linkToAddBooking;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_gestion);

        this.initialize();
        this.actionBarSettings();

        ReservationDAO reservationDAO = new ReservationDAO(this);
        reservationDAO.open();
        ArrayList<Reservation> allBooking = reservationDAO.getBookingList();
        reservationDAO.close();

        if(allBooking.size() <= 0) {
            emptyText.setText(getResources().getString(R.string.no_bookings));
        } else {
            BookingsListAdapter adapter = new BookingsListAdapter(getApplicationContext(), allBooking);
            bookingList.setAdapter(adapter);
            bookingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Reservation booking = (Reservation) parent.getItemAtPosition(position);
                    Intent intent = new Intent(BookingGestionActivity.this, DetailBookingActivity.class);
                    intent.putExtra("bookingId", booking.getId());
                    startActivity(intent);
                }
            });
        }
    }

    private void initialize(){
        linkToAddBooking = (Button) findViewById(R.id.link_to_add_booking);
        bookingList = (ListView) findViewById(R.id.booking_list);
        emptyText = (TextView) findViewById(R.id.empty_booking_list_text);
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

    public void addNewBooking(View view) {
        startActivity(new Intent(this, AddBookingActivity.class));
    }

    public void selectCustomer(View view){
        startActivityForResult(new Intent(this,SelectCustomerActivity.class), 1);
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
