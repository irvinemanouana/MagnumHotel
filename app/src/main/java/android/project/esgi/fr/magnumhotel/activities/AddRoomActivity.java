package android.project.esgi.fr.magnumhotel.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.project.esgi.fr.magnumhotel.R;
import android.project.esgi.fr.magnumhotel.model.Room;
import android.project.esgi.fr.magnumhotel.sqlitepackage.MySqlLite;
import android.project.esgi.fr.magnumhotel.sqlitepackage.RoomDAO;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AddRoomActivity extends Activity {

    // Element de vue
    private EditText titleField,
                     capacityField,
                     priceField,
                     descriptionField;
    private Button addButton;

    private MySqlLite mySqlLite;

    // Contenu des champs
    private String title;
    private int capacity = 0;
    private float price = 0;
    private String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_form);

        this.initialize(); // Initialisation des elements de la vue
        this.actionBarSettings(); // configuration de l'action bar

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkForm()){
                    Room room = new Room(title, capacity, price, description);
                    RoomDAO roomDAO = new RoomDAO(AddRoomActivity.this);
                    roomDAO.open();
                    roomDAO.addRoom(room);
                    roomDAO.close();
                    Intent intent = new Intent(getApplicationContext(), RoomGestionActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

    private void initialize(){
        titleField = (EditText) findViewById(R.id.edit_title);
        descriptionField = (EditText) findViewById(R.id.edit_description);
        priceField = (EditText) findViewById(R.id.edit_price);
        capacityField = (EditText) findViewById(R.id.edit_capacity);
        addButton = (Button) findViewById(R.id.submit_room);
    }

    private boolean checkForm(){

        boolean isCorrect = false;

        if(!capacityField.getText().toString().equals("")){
            capacity = Integer.parseInt(capacityField.getText().toString());
        }
        if(!priceField.getText().toString().equals("")){
            price = Float.parseFloat(priceField.getText().toString());
        }

        title = titleField.getText().toString();
        description = descriptionField.getText().toString(); // description

        if(title.equals("")){
            titleField.setError(getResources().getString(R.string.required_field));
        }else if(capacityField.getText().toString().equals("") ){
            capacityField.setError(getResources().getString(R.string.required_field));
        }else if(priceField.getText().toString().equals("")){
            priceField.setError(getResources().getString(R.string.required_field));
        } else if(capacity < 1 || capacity > 6 ){
            capacityField.setError(getResources().getString(R.string.capacity_room_error));
        }else if (price <= 0){
            priceField.setError("Prix invalide");
        }else{
            isCorrect = true;
        }
        return isCorrect;
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
                Toast.makeText(getBaseContext(), "You selected bookings", Toast.LENGTH_SHORT).show();
                break;

            default:
                finish();
        }
        return true;
    }
}
