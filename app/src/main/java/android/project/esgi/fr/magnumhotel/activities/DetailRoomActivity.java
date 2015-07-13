package android.project.esgi.fr.magnumhotel.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.project.esgi.fr.magnumhotel.R;
import android.project.esgi.fr.magnumhotel.model.Room;
import android.project.esgi.fr.magnumhotel.sqlitepackage.MySqlLite;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class DetailRoomActivity extends Activity {

    private MySqlLite database;
    private TextView textView, roomNumber, detailprice, detailSizeRoom;
    private ActionBar actionBar;
    private ImageView updateRoom, deleteRoom;
    private final Context context=this;
    private Room room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_room);

        database = new MySqlLite(getApplicationContext());
        textView =(TextView)findViewById(R.id.text);
        roomNumber = (TextView)findViewById(R.id.room_number);
        detailprice =(TextView)findViewById(R.id.dprice);
        detailSizeRoom =(TextView)findViewById(R.id.dnbp);
        updateRoom = (ImageView)findViewById(R.id.update_room);
        deleteRoom = (ImageView)findViewById(R.id.delete_room);

        final Intent intent = getIntent();
        room = (Room) intent.getSerializableExtra("Room");
        textView.setText(room.getDescription());
        roomNumber.setText("Chambre N°" + room.getTitle());
        detailprice.setText(String.valueOf(room.getPrice()) + " euros la nuit");
        detailSizeRoom.setText(String.valueOf(room.getNbplace()) + " personne(s) maximum");

        actionBar = getActionBar();
        actionBar.setIcon(R.drawable.ic_action_logo);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setSplitBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));
    }

    public void updateRoom(View view) {
        Intent intent = new Intent(getApplicationContext(),UpdateRoomActivity.class);
        intent.putExtra("room",room);
        startActivity(intent);
    }

    public void deleteRoom(View view) {
        new AlertDialog.Builder(context)
        .setTitle("Supprimer la chambre " + room.getTitle())
        .setMessage("Etes-vous sûr de vouloir supprimer  cette chambre?")
        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // continue with delete
                database.deleteRoom(room);
                Intent back = new Intent(getApplicationContext(), RoomGestionActivity.class);
                startActivity(back);
            }
        })
        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
            }
        })
        .setIcon(android.R.drawable.ic_dialog_alert)
        .show();
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_room, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.trashIcon) {
            new AlertDialog.Builder(context)
                    .setTitle("Supprimer la chambre "+ room.getTitle())
                    .setMessage("Etes-vous sûr de vouloir supprimer  cette chambre?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            database.deleteRoom(room);
                            Intent back = new Intent(getApplicationContext(),RoomGestionActivity.class);
                            startActivity(back);
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        if (id == R.id.editIcon){
            Intent intent = new Intent(getApplicationContext(),UpdateRoomActivity.class);
            intent.putExtra("room",room);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    */

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
