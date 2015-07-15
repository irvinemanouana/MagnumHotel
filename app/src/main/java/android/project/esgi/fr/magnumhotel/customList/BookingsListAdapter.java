package android.project.esgi.fr.magnumhotel.customList;

import android.content.Context;
import android.project.esgi.fr.magnumhotel.R;
import android.project.esgi.fr.magnumhotel.model.Customer;
import android.project.esgi.fr.magnumhotel.model.Reservation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Amélie on 15/07/2015.
 */
public class BookingsListAdapter extends ArrayAdapter {

    private List<Reservation> bookingsList;
    TextView customerName, customerFirstname, roomNumber, arrivalDay, departureDay;

    public BookingsListAdapter(Context context, List<Reservation> bookingsList) {
        super(context, R.layout.row_bookings, bookingsList);
        this.bookingsList = bookingsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.row_bookings, parent, false);
        customerName = (TextView) convertView.findViewById(R.id.customer_name);
        customerFirstname = (TextView) convertView.findViewById(R.id.customer_firstname);
        roomNumber = (TextView) convertView.findViewById(R.id.room_number);
        arrivalDay = (TextView) convertView.findViewById(R.id.arrival_day);
        departureDay = (TextView) convertView.findViewById(R.id.departure_day);

        final Reservation booking =  bookingsList.get(position);
        customerName.setText(String.valueOf(booking.getCustomer().getLastName()));
        customerFirstname.setText(String.valueOf(booking.getCustomer().getFirstName()));
        roomNumber.setText(String.valueOf(booking.getRoom().getTitle()));
        arrivalDay.setText(String.valueOf(booking.getStartDate()));
        departureDay.setText(String.valueOf(booking.getEndDate()));

        return convertView;
    }

}
