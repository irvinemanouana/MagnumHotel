package android.project.esgi.fr.magnumhotel.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.project.esgi.fr.magnumhotel.R;
import android.project.esgi.fr.magnumhotel.model.Reservation;
import android.project.esgi.fr.magnumhotel.dao.ReservationDAO;
import android.project.esgi.fr.magnumhotel.others.Function;
import android.project.esgi.fr.magnumhotel.others.MenuHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Amélie on 14/07/2015.
 */
public class BookingFormAddActivity extends Activity {

    // ELEMENT DE VUE
    EditText customerSelected,
             roomSelected,
             arrivalDateField,
             departureDateField;

    Button selectCustomer,
           selectRoom,
           addBookingButton;

    // Autres variables
    int customerId,
        roomId,
        arrivalYear,
        arrivalMonth,
        arrivalDay,
        departureYear,
        departureMonth,
        departureDay;


    Date arrivalDate,
         departureDate,
         actualyDate,
         yesterday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookings_form);

        this.initialize(); // Initialisation des elements de la vue
        this.actionBarSettings(); //Configuration de l'action bar
        actualyDate = new Date();
        // Date actuel
        Calendar c = Calendar.getInstance();
        arrivalYear = c.get(Calendar.YEAR);
        departureYear = c.get(Calendar.YEAR);
        arrivalMonth = c.get(Calendar.MONTH);
        departureMonth = c.get(Calendar.MONTH);
        arrivalDay = c.get(Calendar.DAY_OF_MONTH);
        departureDay = c.get(Calendar.DAY_OF_MONTH);

        actualyDate.setTime(c.getTimeInMillis());

        arrivalDateField.setText(arrivalDay+"/"+(arrivalMonth+ 1)+"/"+arrivalYear);

        c.add(Calendar.DATE,1); // Ajout d'un jour

        departureDateField.setText(c.get(Calendar.DAY_OF_MONTH)+"/"+( c.get(Calendar.MONTH)+ 1)+"/"+c.get(Calendar.YEAR));

    }

    private boolean checkForm(){

        boolean isCorrect = false;

        arrivalDate = Function.stringToDate(arrivalDateField.getText().toString());
        departureDate = Function.stringToDate(departureDateField.getText().toString());

         if(customerSelected.getText().toString().equals("")){
             customerSelected.setError(getResources().getString(R.string.required_field));
         }else if(roomSelected.getText().toString().equals("")){
             roomSelected.setError(getResources().getString(R.string.required_field));
         }else if(arrivalDate.after(departureDate)) {
             Toast.makeText(this, getResources().getString(R.string.before_date_error), Toast.LENGTH_LONG).show();
         }else if(arrivalDate.equals(departureDate)){
             Toast.makeText(this, getResources().getString(R.string.equal_date_error), Toast.LENGTH_LONG).show();
         }else{
            isCorrect = true;
         }

        return isCorrect;
    }

    private void initialize(){
        customerSelected = (EditText) findViewById(R.id.choose_customer_text);
        roomSelected = (EditText) findViewById(R.id.choose_room_text);
        selectCustomer = (Button) findViewById(R.id.choose_customer_button);
        selectRoom = (Button) findViewById(R.id.choose_room_button);
        addBookingButton = (Button) findViewById(R.id.submit_booking);
        arrivalDateField = (EditText) findViewById(R.id.arrival_date_field);
        departureDateField = (EditText) findViewById(R.id.departure_date_field);
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
        return MenuHelper.handleOnItemSelected(item, this);
    }

    public void selectCustomer(View view) {
        startActivityForResult(new Intent(this,CustomerListActivity.class),1);
    }

    public void selectRoom(View view) {

        arrivalDate = Function.stringToDate(arrivalDateField.getText().toString());
        departureDate = Function.stringToDate(departureDateField.getText().toString());
        if(arrivalDate.after(departureDate)) {
            Toast.makeText(this, getResources().getString(R.string.before_date_error), Toast.LENGTH_LONG).show();
        }else if(arrivalDate.equals(departureDate)){
            Toast.makeText(this, getResources().getString(R.string.equal_date_error), Toast.LENGTH_LONG).show();
        }else{
            Intent intent = new Intent(this, RoomListActivity.class);
            intent.putExtra("arrivalDate",arrivalDateField.getText().toString());
            intent.putExtra("departureDate",departureDateField.getText().toString());
            startActivityForResult(intent, 2);
        }
    }

    public void selectArrivalDate(View view) {
        final DatePickerDialog datePicker = new DatePickerDialog(this,2, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            }
        },arrivalYear,arrivalMonth,arrivalDay);
        datePicker.setButton(DatePickerDialog.BUTTON_POSITIVE,"VALIDER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                arrivalDay = datePicker.getDatePicker().getDayOfMonth();
                arrivalMonth = datePicker.getDatePicker().getMonth();
                arrivalYear = datePicker.getDatePicker().getYear();
                arrivalDateField.setText(arrivalDay+"/"+(arrivalMonth+ 1)+"/"+arrivalYear);
                roomSelected.setText("");

            }
        });
        datePicker.setButton(DatePickerDialog.BUTTON_NEGATIVE,"CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        datePicker.show();
    }

    public void selectDepartureDate(View view) {
        // CREATION DE LA BOITE DE DIALOGUE qui étend la classe DatePickerDialog
        // INITIALISER LA DATE AVEC CELUI DE LA DATE

        final DatePickerDialog datePicker = new DatePickerDialog(this,2,null,departureYear,departureMonth,departureDay+1);

        // configuration du bouton "valider" qui permet de changer le contenu de l'edittext de la date de naissance avec la date sélectionner
        datePicker.setButton(DatePickerDialog.BUTTON_POSITIVE,"VALIDER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                departureDay = datePicker.getDatePicker().getDayOfMonth();
                departureMonth = datePicker.getDatePicker().getMonth();
                departureYear = datePicker.getDatePicker().getYear();
                departureDateField.setText(departureDay+"/"+(departureMonth+ 1)+"/"+departureYear);
                roomSelected.setText("");

            }
        });

        // configuration du bouton "cancel" qui fermer la boite de dialog
        datePicker.setButton(DatePickerDialog.BUTTON_NEGATIVE,"CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                datePicker.dismiss();

            }
        });

        datePicker.show();
    }

    public void addReservation(View view) {
        if(checkForm()){
            final Reservation booking = new Reservation(customerId, roomId, arrivalDate, departureDate);
            ReservationDAO bookingDAO = new ReservationDAO(BookingFormAddActivity.this);
            bookingDAO.open();
            bookingDAO.addBooking(booking);
            bookingDAO.close();
            Intent intent = new Intent(BookingFormAddActivity.this, BookingGestionActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Mise à jour du champs client après selection de celui ci dans la liste
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                customerId = data.getIntExtra("customerId",0);
                customerSelected.setText(data.getStringExtra("customerFirstname")+" "+data.getStringExtra("customerLastname"));
            }
            // Mise à jour du champs chambre après selection de celui ci dans la liste
        }else if(requestCode == 2){
            if(resultCode == RESULT_OK){
                roomId = data.getIntExtra("roomId",0);
                roomSelected.setText(String.format(getResources().getString(R.string.room_number_field), data.getStringExtra("roomTitle")));
            }
        }
    }

}
