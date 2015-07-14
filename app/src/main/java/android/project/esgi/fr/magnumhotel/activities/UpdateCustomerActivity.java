package android.project.esgi.fr.magnumhotel.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.project.esgi.fr.magnumhotel.others.Function;
import android.project.esgi.fr.magnumhotel.R;
import android.project.esgi.fr.magnumhotel.model.Customer;
import android.project.esgi.fr.magnumhotel.sqlitepackage.MySqlLite;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Amélie on 14/07/2015.
 */
public class UpdateCustomerActivity extends Activity {

    // ELEMENT DE VUE
    TextView titleText;
    EditText lastnameField, firstnameField, emailField;
    Button updateButton;

    private MySqlLite database;
    private Customer customer;

    // CONTENU DES CHAMPS
    String lastname;
    String firstname;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_form);

        this.initialize(); // Initialisation des elements de la vue
        this.actionBarSettings(); // configuration de l'action bar

        // Changement du titre et du bouton du formulaire
        titleText.setText(getResources().getString(R.string.update_customer));
        updateButton.setText(getResources().getString(R.string.update));

        // Récuperation des informations du client
        customer = (Customer) getIntent().getSerializableExtra("customer");

        // On met à jour les champs avec les informations de l'utilisateur
        lastnameField.setText(customer.getLastName());
        firstnameField.setText(customer.getFirstName());
        emailField.setText(customer.getEmail());

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verification de la conformité du formulaire
                if(checkForm()){
                    // Modification du client
                    customer = new Customer(customer.getId(), lastname, firstname, email);
                    database = new MySqlLite(UpdateCustomerActivity.this);
                    database.updateCustomer(customer);
                    Intent intent1 = new Intent(getApplicationContext(), CustomerGestionActivity.class);
                    startActivity(intent1);
                    finish();
                }
            }
        });
    }

    private void initialize(){
        titleText = (TextView) findViewById(R.id.customer_form_title);
        lastnameField = (EditText) findViewById(R.id.customer_lastname);
        firstnameField = (EditText) findViewById(R.id.customer_firstname);
        emailField = (EditText) findViewById(R.id.customer_email);
        updateButton = (Button) findViewById(R.id.submit_customer);
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

        if(!(lastname.equals(customer.getLastName()) && firstname.equals(customer.getFirstName()) && email.equals(customer.getEmail()))){
            // S'il y a eu du changement dans le formulaire on vérifie la conformité des champs si on retourne faux
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

        switch(item.getItemId()){
            case R.id.home:
                Intent home = new Intent(this, MainActivity.class);
                startActivity(home);
                break;

            case R.id.rooms:
                Intent rooms = new Intent(this, RoomGestionActivity.class);
                startActivity(rooms);
                break;

            case R.id.customers:
                Intent customers = new Intent(this, CustomerGestionActivity.class);
                startActivity(customers);
                break;

            case R.id.bookings:
                Toast.makeText(getBaseContext(), "You selected bookings", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

}
