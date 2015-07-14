package android.project.esgi.fr.magnumhotel.customList;

import android.content.Context;
import android.project.esgi.fr.magnumhotel.model.Customer;
import android.project.esgi.fr.magnumhotel.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Amélie on 13/07/2015.
 */
public class CustomersListAdapter extends ArrayAdapter {

    private List<Customer> customerList;
    private TextView textView,textView2,textView3;

    public CustomersListAdapter(Context context, List<Customer> customerList) {
        super(context, R.layout.row_customer, customerList);
        this.customerList = customerList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.row_customer, parent, false);
        textView = (TextView) convertView.findViewById(R.id.customer_name);
        textView2 = (TextView) convertView.findViewById(R.id.customer_firstname);
        textView3 = (TextView) convertView.findViewById(R.id.customer_email);

        final Customer customer =  customerList.get(position);
        textView.setText(String.valueOf(customer.getLastName()));
        textView2.setText(String.valueOf(customer.getFirstName()));
        textView3.setText(String.valueOf(customer.getEmail()));

        return convertView;
    }
}
