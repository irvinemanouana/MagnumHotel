package android.project.esgi.fr.magnumhotel.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.project.esgi.fr.magnumhotel.adapter.CustomersListAdapter;
import android.project.esgi.fr.magnumhotel.adapter.RoomsListAdapter;
import android.project.esgi.fr.magnumhotel.dao.CustomerDAO;
import android.project.esgi.fr.magnumhotel.dao.RoomDAO;
import android.project.esgi.fr.magnumhotel.model.Customer;
import android.project.esgi.fr.magnumhotel.model.Room;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Sylvain on 15/07/15.
 */
public class SelectRoomActivity extends ListActivity {

    ArrayList<Room> roomArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RoomDAO roomDAO = new RoomDAO(this);
        roomDAO.open();
        roomArrayList = roomDAO.getRoomList();
        setListAdapter(new RoomsListAdapter(this, roomArrayList));
        roomDAO.close();

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Room room = roomArrayList.get(position);
        Intent resultIntent = new Intent();
        resultIntent.putExtra("roomId", room.getId() );
        resultIntent.putExtra("roomTitle", room.getTitle() );
        // On retourne les informations de la chambre sélectionné au formualaire de reservation
        setResult(Activity.RESULT_OK, resultIntent);
        finish();

    }

}
