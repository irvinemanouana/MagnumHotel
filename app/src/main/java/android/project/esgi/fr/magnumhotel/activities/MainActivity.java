package android.project.esgi.fr.magnumhotel.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.project.esgi.fr.magnumhotel.R;
import android.project.esgi.fr.magnumhotel.adapter.CustomListMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends Activity {

    TextView itemMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.actionBarSettings();

        ArrayList menuList = new ArrayList();
        Collections.addAll(menuList, getResources().getStringArray(R.array.menu));

        ArrayList<Integer> myImageList = new ArrayList<>();
        myImageList.add(R.drawable.ic_action_room);
        myImageList.add(R.drawable.ic_action_person);
        myImageList.add(R.drawable.ic_action_date);

        ArrayAdapter adapter = new CustomListMenu(getApplicationContext(),menuList,myImageList);
        ListView listView = (ListView) findViewById(R.id.list_choice);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch ((int) id) {

                    case 0:
                        intent = new Intent(MainActivity.this, RoomGestionActivity.class);
                        startActivity(intent);
                        break;

                    case 1:
                        intent = new Intent(MainActivity.this, CustomerGestionActivity.class);
                        startActivity(intent);
                        break;

                    case 2:
                        intent = new Intent(MainActivity.this, BookingGestionActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    private void actionBarSettings(){
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar = getActionBar();
            actionBar.setIcon(R.drawable.ic_action_logo);
            //On enlève le titre
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

}
