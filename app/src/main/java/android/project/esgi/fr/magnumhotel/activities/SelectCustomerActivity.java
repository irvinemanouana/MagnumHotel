package android.project.esgi.fr.magnumhotel.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.project.esgi.fr.magnumhotel.adapter.CustomersListAdapter;
import android.project.esgi.fr.magnumhotel.dao.CustomerDAO;
import android.project.esgi.fr.magnumhotel.model.Customer;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Sylvain on 15/07/15.
 */
public class SelectCustomerActivity extends ListActivity {

    ArrayList<Customer> customerArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CustomerDAO customerDAO = new CustomerDAO(this);
        customerDAO.open();
        customerArrayList = customerDAO.getCustomerList();
        setListAdapter(new CustomersListAdapter(this, customerArrayList));
        customerDAO.close();

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Customer customer = customerArrayList.get(position);
        Intent resultIntent = new Intent();
        resultIntent.putExtra("customerId", customer.getId() );
        resultIntent.putExtra("customerLastName", customer.getLastName() );
        resultIntent.putExtra("customerFirstName", customer.getFirstName() );
        // On retourne les informations de l'utilisateur sélectionné au formualaire de reservation
        setResult(Activity.RESULT_OK, resultIntent);

    }
}
