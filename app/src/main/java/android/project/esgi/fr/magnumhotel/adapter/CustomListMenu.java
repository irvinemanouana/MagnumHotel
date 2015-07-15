package android.project.esgi.fr.magnumhotel.adapter;

import android.content.Context;
import android.project.esgi.fr.magnumhotel.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Christopher on 05/07/2015.
 */
public class CustomListMenu extends ArrayAdapter {
    private ArrayList<String> resource;
    private ArrayList<Integer> image;
    TextView textView;
    public CustomListMenu(Context context,ArrayList resource,ArrayList<Integer> image) {
        super(context,R.layout.custom_menu, resource);
        this.resource = resource;
        this.image =image;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView =inflater.inflate(R.layout.custom_menu,parent,false);
        textView =(TextView) convertView.findViewById(R.id.item_menu);
        String s = resource.get(position);
        ImageView imageView =(ImageView) convertView.findViewById(R.id.icon);
        imageView.setImageResource(image.get(position));

        textView.setText(s);
        return convertView;

    }
}
