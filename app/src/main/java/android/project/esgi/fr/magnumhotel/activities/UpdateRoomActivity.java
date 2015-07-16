package android.project.esgi.fr.magnumhotel.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.project.esgi.fr.magnumhotel.R;
import android.project.esgi.fr.magnumhotel.model.Room;
import android.project.esgi.fr.magnumhotel.dao.RoomDAO;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class UpdateRoomActivity extends Activity {

    // ELEMENT DE LA VUE
    TextView titleText;
    EditText titleField,
             descriptionField,
             priceField,
             capacityField;
    Button modifyButton;

    // AUTRES
    private String picturePath = null;
    private Room room;
    private ImageView image;


    // Contenu des champs
    String title;
    int capacity = 0;
    float price = 0;
    String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_form);

        this.initialize(); // Initialisation des elements de la vue
        this.actionBarSettings(); // configuration de l'action bar

        // Changement du titre et du bouton du formulaire
        titleText.setText(getResources().getString(R.string.update_room_title));
        modifyButton.setText(getResources().getString(R.string.update));

        room = (Room) getIntent().getSerializableExtra("room");

        // Mise à jour des champs avec les informations de la chambre
        titleField.setText(room.getTitle());
        capacityField.setText(String.valueOf(room.getCapacity()));
        descriptionField.setText(room.getDescription());
        priceField.setText(String.valueOf(room.getPrice()));
        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkForm()){
                    room = new Room(room.getId(), title, capacity, price, description, picturePath);
                    RoomDAO roomDAO = new RoomDAO(UpdateRoomActivity.this);
                    roomDAO.open();
                    roomDAO.updateRoom(room);
                    roomDAO.close();
                    Intent intent = new Intent(getApplicationContext(), RoomGestionActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

        image.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,1);
            }

        });
    }

    private void initialize(){
        // Layout "activity_update_room"
        titleText = (TextView) findViewById(R.id.room_form_title);
        titleField = (EditText) findViewById(R.id.edit_title);
        descriptionField = (EditText) findViewById(R.id.edit_description);
        capacityField = (EditText) findViewById(R.id.edit_capacity);
        priceField = (EditText) findViewById(R.id.edit_price);
        modifyButton = (Button) findViewById(R.id.submit_room);
        image = (ImageView) findViewById(R.id.room_picture);
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

    private boolean checkForm(){

        boolean isCorrect = false;

        if(!capacityField.getText().toString().equals("")){
            capacity = Integer.parseInt(capacityField.getText().toString());
        }
        if(!priceField.getText().toString().equals("")){
            price = Float.parseFloat(priceField.getText().toString());
        }

        title = titleField.getText().toString();
        description = descriptionField.getText().toString(); // description

        if(!(room.getTitle().equals(title) && room.getCapacity() == capacity && room.getPrice() == price && room.getDescription().equals(description))){
            if(title.equals("")){
                titleField.setError(getResources().getString(R.string.required_field));
            }else if(capacityField.getText().toString().equals("") ){
                capacityField.setError(getResources().getString(R.string.required_field));
            }else if(priceField.getText().toString().equals("")){
                priceField.setError(getResources().getString(R.string.required_field));
            } else if(capacity < 1 || capacity > 6 ){
                capacityField.setError("Une chambre ne peut contenir qu'entre 1 à 6 personnes");
            }else if (price <= 0){
                priceField.setError("Prix invalide");
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

            default:
                finish();
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {

            // Récupération des informations d'une photo sélectionné dans l'album
            if (requestCode == 1) {

                // RECUPERATION DE L'ADRESSE DE LA PHOTO
                Uri selectedImage = data.getData();

                String[] filePath = {MediaStore.Images.Media.DATA};

                Cursor c = this.getContentResolver().query(selectedImage, filePath, null, null, null);

                c.moveToFirst();

                int columnIndex = c.getColumnIndex(filePath[0]);

                picturePath = c.getString(columnIndex);
                // FIN DE LA RECUPERATION
                c.close();

                Bitmap thumbnail2 = (BitmapFactory.decodeFile(picturePath));

                // Changer la photo actuel avec la nouvelle
                image.setImageBitmap(thumbnail2);
            }
        }
    }

}
