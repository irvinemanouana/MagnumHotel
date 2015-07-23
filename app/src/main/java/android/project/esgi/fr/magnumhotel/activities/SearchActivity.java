package android.project.esgi.fr.magnumhotel.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.project.esgi.fr.magnumhotel.R;
import android.project.esgi.fr.magnumhotel.fragment.RoomListFragment;
import android.project.esgi.fr.magnumhotel.others.MenuHelper;
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
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
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
