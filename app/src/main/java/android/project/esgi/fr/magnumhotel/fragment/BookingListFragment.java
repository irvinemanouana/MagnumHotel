package android.project.esgi.fr.magnumhotel.fragment;

import android.app.ListFragment;
import android.os.Bundle;
import android.project.esgi.fr.magnumhotel.activities.CustomerDetailActivity;
import android.project.esgi.fr.magnumhotel.activities.RoomDetailActivity;
import android.project.esgi.fr.magnumhotel.adapter.BookingsListAdapter;
import android.project.esgi.fr.magnumhotel.dao.ReservationDAO;
import android.project.esgi.fr.magnumhotel.model.Reservation;

import java.util.ArrayList;

/**
 * Created by Sylvain on 16/07/15.
 */
public class BookingListFragment extends ListFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ReservationDAO reservationDAO = new ReservationDAO(getActivity());
        reservationDAO.open();

        if(getActivity() instanceof RoomDetailActivity){ // Liste de réservation d'une chambre
            ArrayList<Reservation> reservationArrayList = reservationDAO.getBookingListByRoomId(((RoomDetailActivity) getActivity()).getRoomId());
            if(reservationArrayList != null)
                setListAdapter(new BookingsListAdapter(getActivity(), reservationArrayList, true));
        }
        else{ // Liste de réservation d'un client
            ArrayList<Reservation> reservationArrayList = reservationDAO.getBookingListByCustomerId(((CustomerDetailActivity) getActivity()).getCustomerId());
            if(reservationArrayList != null)
                setListAdapter(new BookingsListAdapter(getActivity(), reservationArrayList, false));
        }

    }
}
