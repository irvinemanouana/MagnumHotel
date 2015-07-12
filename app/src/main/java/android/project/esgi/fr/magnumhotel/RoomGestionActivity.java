package android.project.esgi.fr.magnumhotel;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_room_gestion);
        listView = (ListView) findViewById(R.id.allRom);
        textView = (TextView)findViewById(R.id.nothing);
        database = new MySqlLite(getApplicationContext());
        ArrayList allRom = database.roomArrayList();
        int size = allRom.size();
        Log.d("size",String.valueOf(size));
       if (size<=0){
           textView.setText(getResources().getString(R.string.text_nothing));
       }else{
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_room_gestion, menu);
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
        }if(id == R.id.action_new){
            Intent intent = new Intent(RoomGestionActivity.this,NewRoomActivity.class);
            startActivity(intent);
            finish();
        }


        return super.onOptionsItemSelected(item);
    }
}
