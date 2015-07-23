package android.project.esgi.fr.magnumhotel.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.project.esgi.fr.magnumhotel.others.Function;
import android.project.esgi.fr.magnumhotel.R;
import android.project.esgi.fr.magnumhotel.model.Customer;
import android.project.esgi.fr.magnumhotel.dao.CustomerDAO;
import android.project.esgi.fr.magnumhotel.others.MenuHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Amélie on 13/07/2015.
 */
public class CustomerFormAddActivity extends Activity {

    // ELEMENT DE VUE
    EditText lastnameField, firstnameField, emailField;
    Button addButton;

    // CONTENU DES CHAMPS
    String lastname;
    String firstname;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_form);

        this.initialize(); // Initialisation des elements de la vue
        this.actionBarSettings(); // configuration de l'action bar

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkForm()){
                    final Customer customer = new Customer(lastname, firstname, email);
                    CustomerDAO customerDAO = new CustomerDAO(CustomerFormAddActivity.this);
                    customerDAO.open();
                    customerDAO.addCustomer(customer);
                    customerDAO.close();
                    Intent intent = new Intent(getApplicationContext(), CustomerGestionActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void initialize(){
        lastnameField = (EditText) findViewById(R.id.customer_lastname);
        firstnameField = (EditText) findViewById(R.id.customer_firstname);
        emailField = (EditText) findViewById(R.id.customer_email);
        addButton = (Button) findViewById(R.id.submit_customer);
    }

    private void actionBarSettings(){
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setIcon(R.drawable.ic_action_logo);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setSplitBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Verifie si le formulaire est valide et qu'il a été modifié
     * @return vrai si le formulaire est correct sinon faux
     */
    private boolean checkForm(){

        boolean isCorrect = false;
        lastname = lastnameField.getText().toString();
        firstname = firstnameField.getText().toString();
        email = emailField.getText().toString();

        if(lastname.equals("")){
            lastnameField.setError(getResources().getString(R.string.required_field));
        }else if(firstname.equals("")){
            firstnameField.setError(getResources().getString(R.string.required_field));
        }else if(email.equals("")){
            emailField.setError(getResources().getString(R.string.required_field));
        }else if(!Function.isString(lastname)){
            // Nom non conforme
            lastnameField.setError(getResources().getString(R.string.improper_field));
        }else if(!Function.isString(firstname)){
            // Prénom non conforme
            firstnameField.setError(getResources().getString(R.string.improper_field));
        }else if(!Function.isEmailAddress(email)){
            // Email non conforme
            emailField.setError(getResources().getString(R.string.improper_field));
        }else{
            isCorrect = true;
        }

        return isCorrect;
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
}
