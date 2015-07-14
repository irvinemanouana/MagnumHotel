package android.project.esgi.fr.magnumhotel.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.project.esgi.fr.magnumhotel.R;
import android.project.esgi.fr.magnumhotel.customList.RoomsListAdapter;
import android.project.esgi.fr.magnumhotel.model.Room;
import android.project.esgi.fr.magnumhotel.sqlitepackage.MySqlLite;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class RoomGestionActivity extends Activity {
    MySqlLite database;
    TextView textView;
    ListView listView;
    ActionBar actionBar;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_gestion);

        this.actionBarSettings();

        listView = (ListView) findViewById(R.id.allRom);
        textView = (TextView)findViewById(R.id.nothing);
        database = new MySqlLite(getApplicationContext());
        ArrayList allRom = database.getRoomList();
        int size = allRom.size();
        Log.d("size",String.valueOf(size));

       if(size <= 0){
           textView.setText(getResources().getString(R.string.text_nothing));
       } else {
           ArrayAdapter adapter = new RoomsListAdapter(getApplicationContext(),allRom);
           listView.setAdapter(adapter);
           listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   //String s =String.valueOf(parent.getItemAtPosition(position)) ;
                   Room room = (Room) parent.getItemAtPosition(position);
                   Intent intent = new Intent(getApplicationContext(),DetailRoomActivity.class);
                   intent.putExtra("Room",room);
                   startActivity(intent);
               }
           });
       }
    }

    public void addNewRoom(View view) {
        Intent addNewRoom = new Intent(this, AddRoomActivity.class);
        startActivity(addNewRoom);
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
