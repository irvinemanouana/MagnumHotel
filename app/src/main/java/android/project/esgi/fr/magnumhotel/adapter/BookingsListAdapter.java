package android.project.esgi.fr.magnumhotel.adapter;

import android.content.Context;
import android.project.esgi.fr.magnumhotel.R;
import android.project.esgi.fr.magnumhotel.model.Customer;
import android.project.esgi.fr.magnumhotel.model.Reservation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Amélie on 15/07/2015.
 */
public class BookingsListAdapter extends BaseAdapter {

    private List<Reservation> bookingsList;
    private Context context;
    ViewHolderBookings viewHolderBookings;

    public BookingsListAdapter(Context context, List<Reservation> bookingsList) {
        this.context = context;
        this.bookingsList = bookingsList;
    }

    @Override
    public int getCount() {
        return bookingsList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookingsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            viewHolderBookings = new ViewHolderBookings();
            convertView = inflater.inflate(R.layout.row_bookings, parent, false);
            viewHolderBookings.customerName = (TextView) convertView.findViewById(R.id.customer_name);
            viewHolderBookings.customerFirstname = (TextView) convertView.findViewById(R.id.customer_firstname);
            viewHolderBookings.roomNumber = (TextView) convertView.findViewById(R.id.room_number);
            viewHolderBookings.arrivalDay = (TextView) convertView.findViewById(R.id.arrival_day);
            viewHolderBookings.departureDay = (TextView) convertView.findViewById(R.id.departure_day);
            convertView.setTag(convertView);
        }else{
            viewHolderBookings = (ViewHolderBookings) convertView.getTag();
        }

        final Reservation booking =  bookingsList.get(position);
        viewHolderBookings.customerName.setText(String.valueOf(booking.getCustomer().getLastName()));
        viewHolderBookings.customerFirstname.setText(String.valueOf(booking.getCustomer().getFirstName()));
        viewHolderBookings.roomNumber.setText(String.valueOf(booking.getRoom().getTitle()));
        viewHolderBookings.arrivalDay.setText(String.valueOf(booking.getStartDate()));
        viewHolderBookings.departureDay.setText(String.valueOf(booking.getEndDate()));

        return convertView;
    }

    static class ViewHolderBookings{
        TextView customerName,
                 customerFirstname,
                 roomNumber,
                 arrivalDay,
                 departureDay;
    }

}
