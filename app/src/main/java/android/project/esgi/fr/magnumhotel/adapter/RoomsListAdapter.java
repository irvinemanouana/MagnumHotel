package android.project.esgi.fr.magnumhotel.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.project.esgi.fr.magnumhotel.R;
import android.project.esgi.fr.magnumhotel.model.Room;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Christopher on 05/07/2015.
 */
public class RoomsListAdapter extends BaseAdapter {

    private ArrayList<Room> roomList;
    ViewHolderRoom viewHolderRoom;
    Context context;

    public RoomsListAdapter(Context context, ArrayList<Room> roomList) {
        this.roomList = roomList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return roomList.size();
    }

    @Override
    public Object getItem(int position) {
        return roomList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.row_room, null);
            viewHolderRoom = new ViewHolderRoom();
            viewHolderRoom.title = (TextView) convertView.findViewById(R.id.RoomName);
            viewHolderRoom.capacity = (TextView) convertView.findViewById(R.id.nbperson);
            viewHolderRoom.price = (TextView) convertView.findViewById(R.id.priceRoom);
            viewHolderRoom.availability = (TextView) convertView.findViewById(R.id.room_availability);
            viewHolderRoom.cover = (ImageView) convertView.findViewById(R.id.room_cover);
            convertView.setTag(viewHolderRoom);
        }else{
            // Utilisation du viewHolder pour éviter de réutiliser le "findViewById
            viewHolderRoom = (ViewHolderRoom) convertView.getTag();
        }
        Room room = roomList.get(position);

            viewHolderRoom.title.setText(Html.fromHtml(String.format(context.getResources().getString(R.string.room_title_detail), room.getTitle())));
            viewHolderRoom.capacity.setText(String.format(context.getResources().getString(R.string.room_capacity_detail), room.getCapacity(),
                    room.getCapacity() > 1 ? "s" : ""));
            viewHolderRoom.price.setText(String.format(context.getResources().getString(R.string.room_price_detail), String.valueOf(room.getPrice()).replace('.',',').replace(",0","")));
            if(room.getImageLink() != null)viewHolderRoom.cover.setImageBitmap(BitmapFactory.decodeFile(room.getImageLink()));

            if(!room.isAvailable()){
                viewHolderRoom.availability.setText(context.getResources().getString(R.string.unavailable_text)+"("+room.getReservationDayCount()+")");
                viewHolderRoom.availability.setTextColor(context.getResources().getColor(R.color.unavailable));
            }else{
                viewHolderRoom.availability.setText(context.getResources().getString(R.string.available_text));
                viewHolderRoom.availability.setTextColor(context.getResources().getColor(R.color.avalaible));
            }

        return convertView;
    }

    // Possesseur de vue
    static class ViewHolderRoom {
        TextView title,
                 capacity,
                 price,
                 availability;
        ImageView cover;
    }

}
