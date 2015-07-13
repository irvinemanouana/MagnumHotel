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


public class UpdateRoomActivity extends Activity {
    EditText Edtitle,EdDes,EdiPrice;
    Button buttonM;
    private MySqlLite database;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_room);

        actionBar = getActionBar();
        actionBar.setIcon(R.drawable.ic_action_logo);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setSplitBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));

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
        }
        return true;
    }
}
