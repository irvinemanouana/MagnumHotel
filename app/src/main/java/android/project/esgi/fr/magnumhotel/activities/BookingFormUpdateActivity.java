package android.project.esgi.fr.magnumhotel.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.project.esgi.fr.magnumhotel.R;
import android.project.esgi.fr.magnumhotel.dao.ReservationDAO;
import android.project.esgi.fr.magnumhotel.model.Customer;
import android.project.esgi.fr.magnumhotel.model.Reservation;
import android.project.esgi.fr.magnumhotel.model.Room;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Date;

/**
 * Created by Amélie on 17/07/2015.
 */
public class BookingFormUpdateActivity extends Activity {

    // ELEMENT DE VUE
    TextView titleText;
    EditText arrivalDayField, departureDayField, addCustomerField, addRoomField;
    Button updateButton;

    private Reservation booking;

    // CONTENU DES CHAMPS
    Customer arrivalDay;
    Room departureDay;
    Date addCustomer;
    Date addRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookings_form);

        this.initialize(); // Initialisation des elements de la vue
        this.actionBarSettings(); // configuration de l'action bar

        // Changement du titre et du bouton du formulaire
        titleText.setText(getResources().getString(R.string.update_customer));
        updateButton.setText(getResources().getString(R.string.update));

        // Récuperation des informations du client
        booking = (Reservation) getIntent().getSerializableExtra("booking");

        // On met à jour les champs avec les informations de l'utilisateur
        addCustomerField.setText(booking.getCustomer().getFirstName() + booking.getCustomer().getLastName());
        addRoomField.setText(booking.getRoom().getTitle());

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verification de la conformité du formulaire
                if(checkForm()){
                    // Modification du client
                    booking = new Reservation(booking.getId(), arrivalDay, departureDay, addCustomer, addRoom);
                    ReservationDAO bookingDAO = new ReservationDAO(BookingFormUpdateActivity.this);
                    bookingDAO.open();
                    bookingDAO.updateBooking(booking);
                    bookingDAO.close();
                    Intent intent = new Intent(BookingFormUpdateActivity.this, BookingGestionActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void initialize(){
        titleText = (TextView) findViewById(R.id.booking_form_title);
        arrivalDayField = (EditText) findViewById(R.id.arrival_date_field);
        departureDayField = (EditText) findViewById(R.id.departure_date_field);
        addCustomerField = (EditText) findViewById(R.id.choose_customer_text);
        addRoomField = (EditText) findViewById(R.id.choose_room_text);
        updateButton = (Button) findViewById(R.id.submit_booking);
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

    /**
     * Verifie si le formulaire est valide et qu'il a été modifié
     * @return vrai si le formulaire est correct sinon faux
     */
    private boolean checkForm(){

        boolean isCorrect = false;

        if(!(arrivalDay.equals(booking.getStartDate()) && departureDay.equals(booking.getEndDate()) && addCustomer.equals(booking.getCustomer().getFirstName() + booking.getCustomer().getLastName()) &&  addRoom.equals(booking.getRoom().getTitle()))){
            // S'il y a eu du changement dans le formulaire on vérifie la conformité des champs si on retourne faux
            if(arrivalDay.equals("")){
                arrivalDayField.setError(getResources().getString(R.string.required_field));
            }else if(departureDay.equals("")){
                departureDayField.setError(getResources().getString(R.string.required_field));
            } else if(addCustomer.equals("")){
                addCustomerField.setError(getResources().getString(R.string.required_field));
            } else if(addRoom.equals("")){
                addRoomField.setError(getResources().getString(R.string.required_field));
            } else {
                isCorrect = true;
            }
        }
        return isCorrect;
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
                Intent bookings = new Intent(this, BookingGestionActivity.class);
                startActivity(bookings);
                break;
        }
        return true;
    }

}
