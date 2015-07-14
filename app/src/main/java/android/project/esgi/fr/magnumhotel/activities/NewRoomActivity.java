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
import android.widget.Toast;


public class NewRoomActivity extends Activity {

    private MySqlLite mySqlLite;
    ActionBar actionBar;
    private EditText inptitle, inpdes, inpprice, inpnbperson;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_room);

        //ActionBar Settings
        actionBar = getActionBar();
        actionBar.setIcon(R.drawable.ic_action_logo);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setSplitBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));
        actionBar.setDisplayHomeAsUpEnabled(true);

        inptitle = (EditText) findViewById(R.id.title);
        inpdes = (EditText) findViewById(R.id.description);
        inpprice = (EditText) findViewById(R.id.prix);
        inpnbperson = (EditText) findViewById(R.id.place);
        addButton = (Button) findViewById(R.id.ajouter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String roomNumber = null; // Numero de chambre
                float roomPrice = 0; // prix
                int roomCapacity = 0; // nombre maximum de personne
                String description = inpdes.getText().toString(); // description

                int countError = 0; // nombre d'erreur

                if(inptitle.getText().toString().equals("")){
                    inptitle.setError("Champ obligatoire");
                    countError++;
                }else{
                    roomNumber = inptitle.getText().toString();
                }

                if(inpprice.getText().toString().equals("")){
                    inpprice.setError("Champ obligatoire");
                    countError++;
                }else{
                    roomPrice = Integer.parseInt(inpprice.getText().toString());
                }

                if(!inpnbperson.getText().toString().equals("")){
                    if(Integer.parseInt(inpnbperson.getText().toString()) > 6){
                        inpnbperson.setError("Un chambre ne peut contenir plus de 6 personnes");
                        countError++;
                    }else if(Integer.parseInt(inpnbperson.getText().toString()) < 1){
                        inpnbperson.setError("Un chambre doit contenir au moins une personne");
                        countError++;
                    }else{
                        roomCapacity = Integer.parseInt(inpnbperson.getText().toString());
                    }
                }else{
                    inpnbperson.setError("Champ obligatoire");
                    countError++;
                }

                if(countError <= 0){
                    Room room = new Room(0, roomNumber, roomCapacity, description, roomPrice);
                    mySqlLite = new MySqlLite(getApplicationContext());
                    mySqlLite.addRoom(room);
                    Intent intent = new Intent(getApplicationContext(), RoomGestionActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
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
