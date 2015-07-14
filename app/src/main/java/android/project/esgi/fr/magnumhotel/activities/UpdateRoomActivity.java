package android.project.esgi.fr.magnumhotel.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.project.esgi.fr.magnumhotel.R;
import android.project.esgi.fr.magnumhotel.model.Room;
import android.project.esgi.fr.magnumhotel.sqlitepackage.MySqlLite;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class UpdateRoomActivity extends Activity {

    // ELEMENT DE LA VUE
    TextView titleText;
    EditText titleField, descriptionField, priceField, capacityField;
    Button modifyButton;

    private MySqlLite database;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_form);

        this.initialize(); // Initialisation des elements de la vue
        this.actionBarSettings(); // configuration de l'action bar

        // Changement du titre et du bouton du formulaire
        titleText.setText(getResources().getString(R.string.update_room_title));
        modifyButton.setText(getResources().getString(R.string.update));

        final Intent intent = getIntent();
        final Room room = (Room) intent.getSerializableExtra("room");
        database = new MySqlLite(getApplicationContext());

        // Mise à jour des champs avec les informations de la chambre
        titleField.setText(room.getTitle());
        descriptionField.setText(room.getDescription());
        priceField.setText(String.valueOf(room.getPrice()));
        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String roomNumber = null; // Numero de chambre
                float roomPrice = 0; // prix
                int roomCapacity = 0; // nombre maximum de personne
                String description = descriptionField.getText().toString(); // description

                int countError = 0; // nombre d'erreur
/*
                if (titleField.getText().toString().equals("")) {
                    titleField.setError("Champ obligatoire");
                    countError++;
                } else {
                    roomNumber = titleField.getText().toString();
                }

                if (priceField.getText().toString().equals("")) {
                    priceField.setError("Champ obligatoire");
                    countError++;
                } else {
                    roomPrice = Integer.parseInt(inpprice.getText().toString());
                }

                if (!inpnbperson.getText().toString().equals("")) {
                    if (Integer.parseInt(inpnbperson.getText().toString()) > 6) {
                        inpnbperson.setError("Un chambre ne peut contenir plus de 6 personnes");
                        countError++;
                    } else if (Integer.parseInt(inpnbperson.getText().toString()) < 1) {
                        inpnbperson.setError("Un chambre doit contenir au moins une personne");
                        countError++;
                    } else {
                        roomCapacity = Integer.parseInt(inpnbperson.getText().toString());
                    }
                } else {
                    inpnbperson.setError("Champ obligatoire");
                    countError++;
                }

                if (countError <= 0) {
                    Room room = new Room(0, roomNumber, roomCapacity, description, roomPrice);
                    mySqlLite = new MySqlLite(getApplicationContext());
                    mySqlLite.addRoom(room);
                    Intent intent = new Intent(getApplicationContext(), RoomGestionActivity.class);
                    startActivity(intent);
                    finish();
                }


                String title = Edtitle.getText().toString();
                String desc = EdDes.getText().toString();
                String price = EdiPrice.getText().toString();
                Room room1 = new Room(room.getId(), title, 2, desc, Integer.parseInt(price));
                database.updateRoom(room1);
                Intent intent1 = new Intent(getApplicationContext(), RoomGestionActivity.class);
                startActivity(intent1);
                finish();
                */
            }
        });
    }

    private void initialize(){
        // Layout "activity_update_room"
        titleText = (TextView) findViewById(R.id.room_form_title);
        titleField = (EditText) findViewById(R.id.edit_title);
        descriptionField =(EditText) findViewById(R.id.edit_description);
        capacityField = (EditText) findViewById(R.id.edit_capacity);
        priceField = (EditText) findViewById(R.id.edit_price);
        modifyButton = (Button) findViewById(R.id.submit_room);

    }

    private void actionBarSettings(){
        actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setIcon(R.drawable.ic_action_logo);
        }
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
        actionBar.setSplitBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));
        actionBar.setDisplayHomeAsUpEnabled(true);
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
