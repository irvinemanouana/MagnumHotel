package android.project.esgi.fr.magnumhotel.adapter;

import android.content.Context;
import android.project.esgi.fr.magnumhotel.model.Customer;
import android.project.esgi.fr.magnumhotel.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Utilisation du pattern ViewHolder
 * Ce patern permet d'éviter l'appel fréquent du findViewById lors du defilement de la liste
 * Created by Amélie on 13/07/2015.
 */
public class CustomersListAdapter extends BaseAdapter {

    private List<Customer> customerList;
    ViewHolderCustomers viewHolderCustomers;
    private Context context;

    public CustomersListAdapter(Context context, List<Customer> customerList) {
        this.context = context;
        this.customerList = customerList;
    }

    @Override
    public int getCount() {
        return customerList.size();
    }

    @Override
    public Object getItem(int position) {
        return customerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.row_customer, parent, false);
            viewHolderCustomers = new ViewHolderCustomers();
            viewHolderCustomers.lastname = (TextView) convertView.findViewById(R.id.customer_lastname);
            viewHolderCustomers.firstname = (TextView) convertView.findViewById(R.id.customer_firstname);
            viewHolderCustomers.email = (TextView) convertView.findViewById(R.id.customer_email);
            convertView.setTag(viewHolderCustomers);

        }else{
            // Utilisation du viewHolder pour éviter de réutiliser le "findViewById"
            viewHolderCustomers = (ViewHolderCustomers) convertView.getTag();
        }

        viewHolderCustomers.lastname.setText(customerList.get(position).getLastName());
        viewHolderCustomers.firstname.setText(customerList.get(position).getFirstName());
        viewHolderCustomers.email.setText(customerList.get(position).getEmail());

        return convertView;
    }

    static class ViewHolderCustomers{
        TextView lastname,
                 firstname,
                 email;
    }
}
