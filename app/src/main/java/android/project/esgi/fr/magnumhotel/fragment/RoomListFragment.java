package android.project.esgi.fr.magnumhotel.fragment;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.project.esgi.fr.magnumhotel.activities.RoomDetailActivity;
import android.project.esgi.fr.magnumhotel.activities.SearchActivity;
import android.project.esgi.fr.magnumhotel.adapter.RoomsListAdapter;
import android.project.esgi.fr.magnumhotel.dao.RoomDAO;
import android.project.esgi.fr.magnumhotel.model.Reservation;
import android.project.esgi.fr.magnumhotel.model.Room;
import android.project.esgi.fr.magnumhotel.others.Function;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by Sylvain on 21/07/15.
 */
public class RoomListFragment extends ListFragment {

    ArrayList<Room> roomArrayList;
    ArrayList<Reservation> reservationArrayList;

    Date dateSelected;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dateSelected = Function.stringToDate(((SearchActivity) getActivity()).getDate());

        RoomDAO roomDAO = new RoomDAO(getActivity());
        roomDAO.open();
        roomArrayList = roomDAO.getRoomList();
        reservationArrayList = roomDAO.getReservationRoomList();
        roomDAO.close();

        // Verification de la disponibilité des chambres à une date
        if(roomArrayList != null && reservationArrayList != null){
            for(Room room : roomArrayList) {
                for(Reservation reservation : reservationArrayList){
                    if(room.getId() == reservation.getRoomId()){
                        if(dateSelected.after(reservation.getStartDate()) && dateSelected.before(reservation.getEndDate())){
                            room.setAvailable(false);
                            room.setReservationDayCount(Function.differenceDate(reservation.getStartDate(),reservation.getEndDate(),getActivity()));
                            break;
                        }else if(dateSelected.equals(reservation.getStartDate()) || dateSelected.equals(reservation.getEndDate())){
                            room.setAvailable(false);
                            room.setReservationDayCount(Function.differenceDate(reservation.getStartDate(),reservation.getEndDate(),getActivity()));
                            break;
                        }
                    }
                }
            }
            if(roomArrayList != null)setListAdapter(new RoomsListAdapter(getActivity(), roomArrayList));

        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Room room = roomArrayList.get(position);
        Intent intent = new Intent(getActivity(),RoomDetailActivity.class);
        intent.putExtra("Room",room);
        startActivity(intent);
    }
}
