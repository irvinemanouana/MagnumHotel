package android.project.esgi.fr.magnumhotel.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.project.esgi.fr.magnumhotel.R;
import android.project.esgi.fr.magnumhotel.adapter.RoomsListAdapter;
import android.project.esgi.fr.magnumhotel.model.Reservation;
import android.project.esgi.fr.magnumhotel.model.Room;
import android.project.esgi.fr.magnumhotel.dao.RoomDAO;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class RoomGestionActivity extends Activity {

    // Element de vue
    TextView emptyListText;
    ListView roomList;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_gestion);

        this.initialize();
        this.actionBarSettings();

        RoomDAO roomDAO = new RoomDAO(RoomGestionActivity.this);
        roomDAO.open();
        ArrayList<Reservation> allReservation = roomDAO.getReservationRoomList();
        ArrayList<Room> allRom = roomDAO.getRoomList();
        roomDAO.close();

        Date today = new Date();


        if(allRom.size() <= 0){
            emptyListText.setText(getResources().getString(R.string.text_nothing));
        } else {

            for(Room room : allRom){
                for(Reservation reservation : allReservation){
                    if(room.getId() == reservation.getRoomId()){
                        Log.e("test chambre ", room.getId() + "   " + reservation.getRoomId());
                        if(today.after(reservation.getStartDate()) && today.before(reservation.getEndDate())){
                            room.setAvailable(false);
                            break;
                        }
                    }
                }
            }

            roomList.setAdapter(new RoomsListAdapter(RoomGestionActivity.this,allRom));
            roomList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   //String s = String.valueOf(parent.getItemAtPosition(position)) ;
                   Room room = (Room) parent.getItemAtPosition(position);
                   Intent intent = new Intent(getApplicationContext(),RoomDetailActivity.class);
                   intent.putExtra("Room",room);
                   startActivity(intent);
               }
           });
        }
    }

    private void initialize(){
        emptyListText = (TextView) findViewById(R.id.empty_room_list_text);
        roomList = (ListView) findViewById(R.id.room_list);
    }

    public void addNewRoom(View view) {
        Intent addNewRoom = new Intent(this, RoomFormAddActivity.class);
        startActivity(addNewRoom);
    }

    private void actionBarSettings(){
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setIcon(R.drawable.ic_action_logo);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setSplitBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));
            actionBar.setDisplayHomeAsUpEnabled(true);
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
