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
import android.project.esgi.fr.magnumhotel.others.MenuHelper;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


public class RoomFormAddActivity extends Activity {

    // Element de vue
    private EditText titleField,
                     priceField,
                     descriptionField;

    private Spinner floorSpinner,
                    bedSpinner;

    private Button addButton;
    private ImageView image;

    // AUTRES
    private String picturePath = null;
    private final Integer floorList[] = new Integer[]{1, 2, 3};
    private final Integer bedList[] = new Integer[]{1, 2, 3, 4, 5, 6};

    // Contenu des champs
    private String title;
    private int capacity;
    private float price = 0;
    private String description;
    private int floor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_form);
        this.initialize(); // Initialisation des elements de la vue
        this.actionBarSettings(); // configuration de l'action bar

        ArrayAdapter<Integer> floorAdapter = new ArrayAdapter<>(this,R.layout.spinner_row,floorList);
        floorSpinner.setAdapter(floorAdapter);

        ArrayAdapter<Integer> bedAdapter = new ArrayAdapter<>(this,R.layout.spinner_row,bedList);
        bedSpinner.setAdapter(bedAdapter);

        image.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,1);
            }

        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkForm()){
                    Room room = new Room(title, capacity, price, description, floor, picturePath);
                    RoomDAO roomDAO = new RoomDAO(RoomFormAddActivity.this);
                    roomDAO.open();
                    roomDAO.addRoom(room);
                    roomDAO.close();
                    Intent intent = new Intent(getApplicationContext(), RoomGestionActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void initialize(){
        titleField = (EditText) findViewById(R.id.edit_title);
        floorSpinner = (Spinner) findViewById(R.id.select_floor);
        bedSpinner = (Spinner) findViewById(R.id.select_capacity);
        priceField = (EditText) findViewById(R.id.edit_price);
        descriptionField = (EditText) findViewById(R.id.edit_description);
        image = (ImageView) findViewById(R.id.room_picture);
        addButton = (Button) findViewById(R.id.submit_room);
    }

    private boolean checkForm(){

        boolean isCorrect = false;


        if(!priceField.getText().toString().equals("")){
            price = Float.parseFloat(priceField.getText().toString());
        }

        title = titleField.getText().toString(); // titre
        description = descriptionField.getText().toString(); // description
        floor = Integer.parseInt(floorSpinner.getSelectedItem().toString());
        capacity = Integer.parseInt(bedSpinner.getSelectedItem().toString());

        if(title.equals("")){
            titleField.setError(getResources().getString(R.string.required_field));
        }else if(priceField.getText().toString().equals("")){
            priceField.setError(getResources().getString(R.string.required_field));
        }else if (price <= 0){
            priceField.setError(getResources().getString(R.string.price_error));
        }else{
            isCorrect = true;
        }
        return isCorrect;
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
