package android.project.esgi.fr.magnumhotel.others;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.project.esgi.fr.magnumhotel.R;
import android.project.esgi.fr.magnumhotel.activities.BookingGestionActivity;
import android.project.esgi.fr.magnumhotel.activities.CustomerGestionActivity;
import android.project.esgi.fr.magnumhotel.activities.MainActivity;
import android.project.esgi.fr.magnumhotel.activities.RoomGestionActivity;
import android.project.esgi.fr.magnumhotel.activities.SearchActivity;
import android.view.MenuItem;

import static android.project.esgi.fr.magnumhotel.R.*;
import static android.project.esgi.fr.magnumhotel.R.anim.*;

/**
 * Created by Sylvain on 23/07/15.
 */
public class MenuHelper {

    public static boolean handleOnItemSelected(MenuItem item, Context c){

        Intent intent = null;

        switch(item.getItemId()){
            case id.home:
                intent = new Intent(c, MainActivity.class);
                break;

            case id.rooms:
                intent = new Intent(c, RoomGestionActivity.class);
                break;

            case id.customers:
                intent = new Intent(c, CustomerGestionActivity.class);
                break;

            case id.bookings:
                intent = new Intent(c, BookingGestionActivity.class);
                break;

            case id.search:
                intent = new Intent(c, SearchActivity.class);
                break;

            default:
                ((Activity) c).finish();
        }
        if (intent != null) {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        c.startActivity(intent);

        ((Activity) c).overridePendingTransition(0, c.getResources().getAnimation(anim.abc_fade_out).getAttributeCount());
        return true;

    }

}
