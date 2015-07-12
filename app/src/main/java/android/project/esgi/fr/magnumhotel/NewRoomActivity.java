package android.project.esgi.fr.magnumhotel;

import android.app.Activity;
import android.content.Intent;
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

    private EditText inptitle,inpdes,inpprice,inpnbperson;
    private Button addButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_room);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_room, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
