package android.project.esgi.fr.magnumhotel.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.project.esgi.fr.magnumhotel.R;
import android.project.esgi.fr.magnumhotel.activities.BookingGestionActivity;
import android.project.esgi.fr.magnumhotel.activities.UpdateBookingActivity;
import android.project.esgi.fr.magnumhotel.dao.CustomerDAO;
import android.project.esgi.fr.magnumhotel.dao.ReservationDAO;
import android.project.esgi.fr.magnumhotel.dao.RoomDAO;
import android.project.esgi.fr.magnumhotel.model.Customer;
import android.project.esgi.fr.magnumhotel.model.Reservation;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amélie on 15/07/2015.
 */
public class BookingsListAdapter extends BaseAdapter {

    private ArrayList<Reservation> bookingsList;
    private Context context;
    private int bookingPosition;
    ViewHolderBookings viewHolderBookings;

    public BookingsListAdapter(Context context, ArrayList<Reservation> bookingsList) {
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
            viewHolderBookings.customerLastname = (TextView) convertView.findViewById(R.id.customer_lastname_reservation);
            viewHolderBookings.roomNumber = (TextView) convertView.findViewById(R.id.room_number_reservation);
            viewHolderBookings.arrivalDay = (TextView) convertView.findViewById(R.id.arrival_day_booking);
            viewHolderBookings.departureDay = (TextView) convertView.findViewById(R.id.end_day_booking);
            viewHolderBookings.updateBooking = (ImageView) convertView.findViewById(R.id.update_booking);
            viewHolderBookings.deleteBooking = (ImageView) convertView.findViewById(R.id.delete_booking);
            convertView.setTag(viewHolderBookings);
        } else {
            viewHolderBookings = (ViewHolderBookings) convertView.getTag();
        }

        final Reservation booking =  bookingsList.get(position);

        viewHolderBookings.customerLastname.setText(Html.fromHtml(String.format(context.getResources().getString(R.string.booking_customer), booking.getCustomer().getFirstName() + " " + booking.getCustomer().getLastName())));
        viewHolderBookings.roomNumber.setText(Html.fromHtml(String.format(context.getResources().getString(R.string.room_title_detail),booking.getRoom().getTitle())));
        viewHolderBookings.arrivalDay.setText(Html.fromHtml(String.format(context.getResources().getString(R.string.arrival_day),String.valueOf(booking.getStartDate()))));
        viewHolderBookings.departureDay.setText(Html.fromHtml(String.format(context.getResources().getString(R.string.departure_day), String.valueOf(booking.getEndDate()))));

        bookingPosition = position;

        viewHolderBookings.deleteBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                .setTitle("Supprimer la réservation ? ")
                .setMessage(context.getResources().getString(R.string.delete_booking_message))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ReservationDAO bookingDAO = new ReservationDAO(context);
                        bookingDAO.open();
                        bookingDAO.deleteBooking(bookingsList.get(bookingPosition));
                        bookingDAO.close();
                        Intent back = new Intent(context.getApplicationContext(), BookingGestionActivity.class);
                        context.startActivity(back);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
            }
        });

        viewHolderBookings.updateBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), UpdateBookingActivity.class);
                intent.putExtra("bookingId", bookingsList.get(bookingPosition));
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    static class ViewHolderBookings{
        TextView customerLastname,
                 customerFirstname,
                 roomNumber,
                 arrivalDay,
                 departureDay;

        ImageView updateBooking, deleteBooking;
    }

}
