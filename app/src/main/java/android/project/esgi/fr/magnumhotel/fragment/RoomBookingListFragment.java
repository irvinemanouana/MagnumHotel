package android.project.esgi.fr.magnumhotel.fragment;

import android.app.ListFragment;
import android.os.Bundle;
import android.project.esgi.fr.magnumhotel.activities.DetailRoomActivity;
import android.project.esgi.fr.magnumhotel.adapter.BookingsListAdapter;
import android.project.esgi.fr.magnumhotel.dao.ReservationDAO;
import android.project.esgi.fr.magnumhotel.model.Reservation;
import android.project.esgi.fr.magnumhotel.model.Room;

import java.util.ArrayList;

/**
 * Created by Sylvain on 16/07/15.
 */
public class RoomBookingListFragment extends ListFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ReservationDAO reservationDAO = new ReservationDAO(getActivity());
        reservationDAO.open();
        ArrayList<Reservation> reservationArrayList = reservationDAO.getBookingListByRoomId(((DetailRoomActivity) getActivity()).getRoomId());
        if(reservationArrayList != null)
            setListAdapter(new BookingsListAdapter(getActivity(),reservationArrayList));
    }
}
