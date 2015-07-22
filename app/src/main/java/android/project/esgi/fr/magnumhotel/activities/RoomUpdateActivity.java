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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class RoomUpdateActivity extends Activity {

    // ELEMENT DE LA VUE
    TextView titleText;
    EditText titleField,
             descriptionField,
             priceField;
    Spinner floorSpinner,
            bedSpinner;
    Button modifyButton;

    // AUTRES
    private String picturePath = null;
    private Room room;
    private ImageView image;
    private final Integer floorList[] = new Integer[]{1, 2, 3};
    private final Integer bedList[] = new Integer[]{1, 2, 3, 4, 5, 6};

    // Contenu des champs
    String title;
    int capacity = 0;
    float price = 0;
    String description;
    private int floor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_form);
        this.initialize(); // Initialisation des elements de la vue
        this.actionBarSettings(); // configuration de l'action bar

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this,R.layout.spinner_row,floorList);
        floorSpinner.setAdapter(adapter);

        ArrayAdapter<Integer> bedAdapter = new ArrayAdapter<>(this,R.layout.spinner_row,bedList);
        bedSpinner.setAdapter(bedAdapter);

        // Changement du titre et du bouton du formulaire
        titleText.setText(getResources().getString(R.string.update_room_title));
        modifyButton.setText(getResources().getString(R.string.update));

        room = (Room) getIntent().getSerializableExtra("room");

        // Mise à jour des champs avec les informations de la chambre
        titleField.setText(room.getTitle());
        //bedSpinner.setText(String.valueOf(room.getCapacity()));
        descriptionField.setText(room.getDescription());
        priceField.setText(String.valueOf(room.getPrice()));
        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkForm()){
                    room = new Room(room.getId(), title, capacity, price, description, floor, picturePath);
                    RoomDAO roomDAO = new RoomDAO(RoomUpdateActivity.this);
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
        titleText = (TextView) findViewById(R.id.room_form_title); // Titre du formulaire
        floorSpinner = (Spinner) findViewById(R.id.select_floor);
        bedSpinner = (Spinner) findViewById(R.id.select_capacity);
        titleField = (EditText) findViewById(R.id.edit_title);
        descriptionField = (EditText) findViewById(R.id.edit_description);
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

        /*if(!capacityField.getText().toString().equals("")){
            capacity = Integer.parseInt(capacityField.getText().toString());
        }*/
        if(!priceField.getText().toString().equals("")){
            price = Float.parseFloat(priceField.getText().toString());
        }

        title = titleField.getText().toString();
        description = descriptionField.getText().toString(); // description
        floor = Integer.parseInt(floorSpinner.getSelectedItem().toString());
        capacity = Integer.parseInt(bedSpinner.getSelectedItem().toString());

        if(!(room.getTitle().equals(title) && room.getCapacity() == capacity && room.getPrice() == price && room.getDescription().equals(description))){
            if(title.equals("")){
                titleField.setError(getResources().getString(R.string.required_field));
            }else if(priceField.getText().toString().equals("")){
                priceField.setError(getResources().getString(R.string.required_field));
            }else if (price <= 0){
                priceField.setError(getResources().getString(R.string.price_error));
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
                Intent bookings = new Intent(this, BookingGestionActivity.class);
                startActivity(bookings);
                break;

            case R.id.search:
                Intent search = new Intent(this, SearchActivity.class);
                startActivity(search);
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
