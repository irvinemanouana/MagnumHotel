package android.project.esgi.fr.magnumhotel;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.project.esgi.fr.magnumhotel.sqlitepackage.MySqlLite;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class NewRoomActivity extends Activity {
    private MySqlLite mySqlLite;
    private ActionBar actionBar;
    private EditText inptitle,inpdes,inpprice,inpnbperson;
    private Button addButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_room);

        actionBar = getActionBar();
        actionBar.setIcon(R.drawable.ic_action_logo);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setSplitBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));

        inptitle = (EditText) findViewById(R.id.title);
        inpdes = (EditText) findViewById(R.id.description);
        inpprice = (EditText) findViewById(R.id.prix);
        inpnbperson = (EditText) findViewById(R.id.place);
        addButton = (Button) findViewById(R.id.ajouter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titre = inptitle.getText().toString();
                String descriprion = inpdes.getText().toString();
                String pricetxt = inpprice.getText().toString();
                String placeRoom = inpnbperson.getText().toString();
                Room room = new Room(0, titre,Integer.parseInt(placeRoom), descriprion,Integer.parseInt(pricetxt));
                mySqlLite = new MySqlLite(getApplicationContext());
                mySqlLite.addRoom(room);
                Intent intent = new Intent(getApplicationContext(),RoomGestionActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Nouvelle chambre " + titre , Toast.LENGTH_SHORT);
                finish();

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
            case R.id.rooms:
                Toast.makeText(getBaseContext(), "You selected rooms", Toast.LENGTH_SHORT).show();
                break;

            case R.id.customers:
                Toast.makeText(getBaseContext(), "You selected customers", Toast.LENGTH_SHORT).show();
                break;

            case R.id.bookings:
                Toast.makeText(getBaseContext(), "You selected bookings", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
