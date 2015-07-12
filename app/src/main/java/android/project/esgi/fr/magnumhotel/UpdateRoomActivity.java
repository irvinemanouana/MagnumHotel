package android.project.esgi.fr.magnumhotel;

import android.app.Activity;
import android.content.Intent;
import android.project.esgi.fr.magnumhotel.sqlitepackage.MySqlLite;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class UpdateRoomActivity extends Activity {
    EditText Edtitle,EdDes,EdiPrice;
    Button buttonM;
    private MySqlLite database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_room);
        final Intent intent = getIntent();
        final Room room = (Room) intent.getSerializableExtra("room");
        database = new MySqlLite(getApplicationContext());
        Edtitle = (EditText) findViewById(R.id.title);
        Edtitle.setText(room.getTitle());
        EdDes =(EditText) findViewById(R.id.description);
        EdDes.setText(room.getDescription());
        EdiPrice = (EditText) findViewById(R.id.prix);
        EdiPrice.setText(String.valueOf(room.getPrice()));
        buttonM = (Button) findViewById(R.id.modifier);
        buttonM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = Edtitle.getText().toString();
                String desc = EdDes.getText().toString();
                String price = EdiPrice.getText().toString();
                Room room1 = new Room(room.getId(),title,2,desc,Integer.parseInt(price));
                database.updateRoom(room1);
                Intent intent1 = new Intent(getApplicationContext(),RoomGestionActivity.class);
                startActivity(intent1);
                finish();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update_room, menu);
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
