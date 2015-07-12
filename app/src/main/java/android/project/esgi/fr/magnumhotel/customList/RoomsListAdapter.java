package android.project.esgi.fr.magnumhotel.customList;

import android.content.Context;
import android.project.esgi.fr.magnumhotel.R;
import android.project.esgi.fr.magnumhotel.Room;
import android.project.esgi.fr.magnumhotel.sqlitepackage.MySqlLite;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

/**
 * Created by Christopher on 05/07/2015.
 */
public class RoomsListAdapter extends ArrayAdapter {
    private List<Room> roomList;
    private TextView textView,textView2,textView3;
    private MySqlLite lite;
    Context context;

    public RoomsListAdapter(Context context, List<Room> roomList) {
        super(context, R.layout.room_list_custom_adapter, roomList);
        this.roomList = roomList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.room_list_custom_adapter,parent,false);
        textView = (TextView) convertView.findViewById(R.id.RoomName);
        textView2 = (TextView) convertView.findViewById(R.id.nbperson);
        textView3 = (TextView) convertView.findViewById(R.id.priceRoom);

        final Room room =  roomList.get(position);
        textView.setText("Chambre N°" + room.getTitle());
        textView2.setText(String.valueOf(room.getNbplace()) + " personne(s) maximum");
        textView3.setText(String.valueOf(room.getPrice())+" euros la nuit");

        return convertView;
    }
}
