package android.project.esgi.fr.magnumhotel.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.project.esgi.fr.magnumhotel.R;
import android.project.esgi.fr.magnumhotel.fragment.RoomListFragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sylvain on 21/07/15.
 */
public class SearchActivity extends Activity {

    // Autres variables
    int year,
        month,
        day;

    private EditText dateEditField;
    private RoomListFragment roomListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        this.actionBarSettings(); // configuration de l'action bar

        initialize();

        // Date actuel
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        dateEditField.setText(day + "/" + (month + 1) + "/" + year);

        roomListFragment = new RoomListFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.room_list_fragment,roomListFragment);
        ft.commit();

    }

    private void initialize() {
        dateEditField = (EditText) findViewById(R.id.search_date_field);
        roomListFragment = (RoomListFragment) getFragmentManager().findFragmentById(R.id.room_list_fragment);
    }

    public void selectDate(View view) {
        final DatePickerDialog datePicker = new DatePickerDialog(this, 2, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            }
        },year, month, day);
        datePicker.setButton(DatePickerDialog.BUTTON_POSITIVE,"VALIDER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                day = datePicker.getDatePicker().getDayOfMonth();
                month = datePicker.getDatePicker().getMonth();
                year = datePicker.getDatePicker().getYear();
                dateEditField.setText(day + "/" + (month + 1) + "/" + year);

                // Actualiser le fragment
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(roomListFragment);
                ft.attach(roomListFragment);
                ft.commit();
            }
        });

        datePicker.setButton(DatePickerDialog.BUTTON_NEGATIVE,"CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        datePicker.show();
    }

    public String getDate(){
        return dateEditField.getText().toString();
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

            case R.id.search:
                Intent search = new Intent(this, SearchActivity.class);
                startActivity(search);
                break;

            default:
                finish();
        }
        return true;
    }

}
