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
import android.project.esgi.fr.magnumhotel.fragment.BookingListFragment;
import android.project.esgi.fr.magnumhotel.model.Room;
import android.project.esgi.fr.magnumhotel.dao.RoomDAO;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class RoomDetailActivity extends Activity {

    private TextView textView,
                     roomNumber,
                     detailprice,
                     detailSizeRoom;
    private final Context context=this;
    private Room room;
    private BookingListFragment bookingListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_detail);
        room = (Room) getIntent().getSerializableExtra("Room");
        this.actionBarSettings();
        this.initialize();

        textView.setText(room.getDescription());
        roomNumber.setText(Html.fromHtml(String.format(getResources().getString(R.string.room_title_detail),room.getTitle())));
        detailprice.setText(String.format(getResources().getString(R.string.room_price_detail),String.valueOf(room.getPrice())));
        detailSizeRoom.setText(String.format(context.getResources().getString(R.string.room_capacity_detail), room.getCapacity(),
                room.getCapacity() > 1 ? "s" : ""));
    }

    private void initialize(){
        textView =(TextView)findViewById(R.id.text);
        roomNumber = (TextView)findViewById(R.id.room_number);
        detailprice =(TextView)findViewById(R.id.dprice);
        detailSizeRoom =(TextView)findViewById(R.id.dnbp);
        bookingListFragment = (BookingListFragment) getFragmentManager().findFragmentById(R.id.room_bookings_list);
    }

    private void actionBarSettings(){
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setIcon(R.drawable.ic_action_logo);
            actionBar.setSplitBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void updateRoom(View view) {
        Intent intent = new Intent(getApplicationContext(),RoomUpdateActivity.class);
        intent.putExtra("room",room);
        startActivity(intent);
    }

    public void deleteRoom(View view) {
        new AlertDialog.Builder(context)
        .setTitle(getResources().getString(R.string.delete_room_title))
        .setMessage(getResources().getString(R.string.delete_customer_message))
        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                RoomDAO roomDAO = new RoomDAO(RoomDetailActivity.this);
                roomDAO.open();
                roomDAO.deleteRoom(room);
                roomDAO.close();
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

    public int getRoomId(){
        return room.getId();
    }
}
