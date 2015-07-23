package android.project.esgi.fr.magnumhotel.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.project.esgi.fr.magnumhotel.R;
import android.project.esgi.fr.magnumhotel.adapter.CustomersListAdapter;
import android.project.esgi.fr.magnumhotel.model.Customer;
import android.project.esgi.fr.magnumhotel.dao.CustomerDAO;
import android.project.esgi.fr.magnumhotel.others.MenuHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Amélie on 13/07/2015.
 */
public class CustomerGestionActivity extends Activity {

    TextView emptyText;
    ListView customerList;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_gestion);

        this.initialize();
        this.actionBarSettings();

        CustomerDAO customerDAO = new CustomerDAO(this);
        customerDAO.open();
        final ArrayList<Customer> allCustomer = customerDAO.getCustomerList();
        customerDAO.close();
        int size = allCustomer.size();

        if(size <= 0) {
            emptyText.setText(getResources().getString(R.string.no_customer));
        } else {
            emptyText.setVisibility(View.GONE);
            CustomersListAdapter adapter = new CustomersListAdapter(CustomerGestionActivity.this, allCustomer);
            customerList.setAdapter(adapter);
            customerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Customer customer = (Customer) parent.getItemAtPosition(position);
                    Intent intent = new Intent(getApplicationContext(), CustomerDetailActivity.class);
                    intent.putExtra("Customer", customer);
                    startActivity(intent);
                }
            });
        }
    }

    private void initialize(){
        customerList = (ListView) findViewById(R.id.customer_list);
        emptyText = (TextView)findViewById(R.id.empty_customer_list_text);
    }

    private void actionBarSettings(){
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setIcon(R.drawable.ic_action_logo);
            actionBar.setSplitBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void addNewCustomer(View view) {
        startActivity(new Intent(this, CustomerFormAddActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        return MenuHelper.handleOnItemSelected(item, this);
    }

    @Override // Fermer l'application lorsque l'on appuie sur le bouton "back"
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setCancelable(true);
        alertDialog.setMessage("Êtes vous sûr de vouloir quitter l'application ?");
        alertDialog.setPositiveButton("oui",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(CustomerGestionActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Exit", true);
                startActivity(intent);
                finish();
            }
        });
        alertDialog.setNegativeButton("non", null);
        alertDialog.show();
    }
}
