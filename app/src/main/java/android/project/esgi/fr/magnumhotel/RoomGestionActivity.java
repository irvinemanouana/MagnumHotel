package android.project.esgi.fr.magnumhotel;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.project.esgi.fr.magnumhotel.customList.RoomsListAdapter;
import android.project.esgi.fr.magnumhotel.sqlitepackage.MySqlLite;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;


public class RoomGestionActivity extends Activity {
    private MySqlLite database;
    private TextView textView;
    private ListView listView;
    private ActionBar actionBar;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_gestion);

        actionBar = getActionBar();
        actionBar.setIcon(R.drawable.ic_action_logo);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setSplitBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));

        listView = (ListView) findViewById(R.id.allRom);
        textView = (TextView)findViewById(R.id.nothing);
        database = new MySqlLite(getApplicationContext());
        ArrayList allRom = database.roomArrayList();
        int size = allRom.size();
        Log.d("size",String.valueOf(size));

       if (size <= 0){
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
                   Toast.makeText(getApplicationContext(),String.valueOf(room), Toast.LENGTH_SHORT).show();
               }
           });
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
                Toast.makeText(getBaseContext(), "You selected customers", Toast.LENGTH_SHORT).show();
                break;

            case R.id.bookings:
                Toast.makeText(getBaseContext(), "You selected bookings", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
