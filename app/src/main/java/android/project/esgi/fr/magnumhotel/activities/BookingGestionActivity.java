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
import android.project.esgi.fr.magnumhotel.adapter.BookingsListAdapter;
import android.project.esgi.fr.magnumhotel.model.Reservation;
import android.project.esgi.fr.magnumhotel.dao.ReservationDAO;
import android.project.esgi.fr.magnumhotel.others.MenuHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

    private final Context context = this;
    private Reservation booking;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_gestion);

        this.initialize();
        this.actionBarSettings();

        booking = (Reservation)getIntent().getSerializableExtra("Booking");

        ReservationDAO reservationDAO = new ReservationDAO(this);
        reservationDAO.open();
        ArrayList<Reservation> allBooking = reservationDAO.getBookingList();
        reservationDAO.close();

        if(allBooking.size() <= 0) {
            emptyText.setText(getResources().getString(R.string.no_bookings));
        } else {
            emptyText.setVisibility(View.GONE);
            BookingsListAdapter adapter = new BookingsListAdapter(this, allBooking, "booking");
            bookingList.setAdapter(adapter);
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
        startActivity(new Intent(this, BookingFormAddActivity.class));
    }

    public void selectCustomer(View view){
        startActivityForResult(new Intent(this,CustomerListActivity.class), 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        return MenuHelper.handleOnItemSelected(item, this);
    }

    @Override // Fermer l'application lorsque l'on appuie sur le bouton "back"
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setCancelable(true);
        alertDialog.setMessage("Êtes vous sûr de vouloir quitter l'application ?");
        alertDialog.setPositiveButton("oui",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(BookingGestionActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Exit", true);
                startActivity(intent);
                finish();
            }
        });
        alertDialog.setNegativeButton("non", null);
        alertDialog.show();
    }

}
