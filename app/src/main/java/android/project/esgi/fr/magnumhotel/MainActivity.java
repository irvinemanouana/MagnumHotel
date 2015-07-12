package android.project.esgi.fr.magnumhotel;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.project.esgi.fr.magnumhotel.customList.CustomListMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends Activity {
    private ActionBar actionBar;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getActionBar();
        actionBar.setIcon(R.drawable.ic_action_logo);
        //On enlève le titre
        actionBar.setDisplayShowTitleEnabled(false);

        ArrayList menuList = new ArrayList();
        String[]menu =getResources().getStringArray(R.array.menu);
        for (int i =0;i<menu.length;i++){
            menuList.add(menu[i]);
        }
        ArrayList<Integer> myImageList = new ArrayList<>();
        myImageList.add(R.drawable.ic_action_room);
        myImageList.add(R.drawable.ic_action_person);
        myImageList.add(R.drawable.ic_action_date);

        ArrayAdapter adapter = new CustomListMenu(getApplicationContext(),menuList,myImageList);
        listView =(ListView) findViewById(R.id.list_choice);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                String item = String.valueOf(parent.getItemAtPosition(position));
                switch ((int) id){
                    case 0:
                        intent = new Intent(getApplicationContext(),RoomGestionActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.title_activity_room_gestion),Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(),item,Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(),item,Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */
}
