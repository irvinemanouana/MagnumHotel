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
import android.project.esgi.fr.magnumhotel.model.Reservation;
import android.project.esgi.fr.magnumhotel.model.Room;
import android.project.esgi.fr.magnumhotel.others.Function;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by Sylvain on 15/07/15.
 */
public class RoomListActivity extends ListActivity {

    ArrayList<Room> roomArrayList;
    ArrayList<Reservation> reservationArrayList;

    Date departureDate,
         arrivalDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String arrivalDateSelected = getIntent().getStringExtra("arrivalDate");
        String departureDateSelected = getIntent().getStringExtra("departureDate");

        if(arrivalDateSelected != null && departureDateSelected != null) {
            arrivalDate = Function.stringToDate(arrivalDateSelected);
            departureDate = Function.stringToDate(departureDateSelected);
        }

        RoomDAO roomDAO = new RoomDAO(this);
        roomDAO.open();
        roomArrayList = roomDAO.getRoomList();
        reservationArrayList = roomDAO.getReservationRoomList();
        roomDAO.close();
        Iterator<Room> iterator = roomArrayList.iterator();
        // Verification de la disponibilité des chambres à une date
        if(roomArrayList != null && reservationArrayList != null){
            while (iterator.hasNext()) {
                Room room = iterator.next();
                for(Reservation reservation : reservationArrayList){
                    if(room.getId() == reservation.getRoomId()){
                        if(
                                (arrivalDate.after(reservation.getStartDate()) && arrivalDate.before(reservation.getEndDate()))
                                ||
                                (departureDate.after(reservation.getStartDate())  && departureDate.before(reservation.getEndDate()))
                           ){

                            iterator.remove();
                            break;

                        }else if(arrivalDate.equals(reservation.getStartDate()) && departureDate.equals(reservation.getEndDate())){
                            iterator.remove();
                            break;
                        }
                    }
                }

            }
            if(roomArrayList != null)setListAdapter(new RoomsListAdapter(this, roomArrayList));

        }

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
