package android.project.esgi.fr.magnumhotel.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.project.esgi.fr.magnumhotel.R;
import android.project.esgi.fr.magnumhotel.adapter.CustomListMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        if(getIntent().getBooleanExtra("Exit", false)){
            finish();
        }


        this.actionBarSettings();

        ArrayList menuList = new ArrayList();
        Collections.addAll(menuList, getResources().getStringArray(R.array.menu));

        ArrayList<Integer> myImageList = new ArrayList<>();
        myImageList.add(R.drawable.ic_action_room);
        myImageList.add(R.drawable.ic_action_person);
        myImageList.add(R.drawable.ic_action_date);
        myImageList.add(android.R.drawable.ic_menu_search);
        myImageList.add(android.R.drawable.ic_menu_sort_by_size);

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

                    case 3:
                        intent = new Intent(MainActivity.this, SearchActivity.class);
                        startActivity(intent);
                        break;

                    case 4:
                        intent = new Intent(MainActivity.this, StatisticsActivity.class);
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
