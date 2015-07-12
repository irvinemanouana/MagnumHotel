package android.project.esgi.fr.magnumhotel;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.project.esgi.fr.magnumhotel.sqlitepackage.MySqlLite;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class DetailRoomActivity extends Activity {
    private MySqlLite database;
    private TextView textView,detailprice,detailSizeRoom;
    private ActionBar actionBar;
    private final Context context=this;
    private Room room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_room);
        database = new MySqlLite(getApplicationContext());
        textView =(TextView)findViewById(R.id.text);
        detailprice =(TextView)findViewById(R.id.dprice);
        detailSizeRoom =(TextView)findViewById(R.id.dnbp);
        final Intent intent =getIntent();
        room = (Room) intent.getSerializableExtra("Room");
        textView.setText(room.getDescription());
        detailprice.setText(String.valueOf(room.getPrice())+" euros la nuit");
        detailSizeRoom.setText(String.valueOf(room.getNbplace()) + " personne(s)");
        actionBar= getActionBar();
        actionBar.setTitle(room.getTitle());

    }


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
                    .setMessage("Êtes-vous sûr de vouloir supprimer  cette chambre?")
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
        }if (id == R.id.editIcon){
            Intent intent = new Intent(getApplicationContext(),UpdateRoomActivity.class);
            intent.putExtra("room",room);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
