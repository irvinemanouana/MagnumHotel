package android.project.esgi.fr.magnumhotel.activities;

import android.app.Activity;
import android.os.Bundle;
import android.project.esgi.fr.magnumhotel.R;
import android.project.esgi.fr.magnumhotel.dao.CustomerDAO;
import android.project.esgi.fr.magnumhotel.dao.ReservationDAO;
import android.project.esgi.fr.magnumhotel.dao.RoomDAO;
import android.widget.TextView;

/**
 * Created by Sylvain on 23/07/15.
 */
public class StatisticsActivity extends Activity {

    private TextView roomCount,
                     customerCount,
                     bookingCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);
        initialize();

        CustomerDAO customerDAO = new CustomerDAO(this);
        customerDAO.open();
        customerCount.setText(String.valueOf(customerDAO.getCustomerCount()));
        customerDAO.close();

        RoomDAO roomDAO = new RoomDAO(this);
        roomDAO.open();
        roomCount.setText(String.valueOf(roomDAO.getRoomCount()));
        roomDAO.close();

        ReservationDAO reservationDAO = new ReservationDAO(this);
        reservationDAO.open();
        bookingCount.setText(String.valueOf(reservationDAO.getReservationCount()));
        reservationDAO.close();
    }

    private void initialize() {
        roomCount = (TextView) findViewById(R.id.room_count);
        customerCount = (TextView) findViewById(R.id.customer_count);
        bookingCount = (TextView) findViewById(R.id.booking_count);
    }
}
