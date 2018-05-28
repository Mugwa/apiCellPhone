package com.example.patrick.apicellphone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PositionAdapter extends ArrayAdapter<Position> {

    //Constructeur de la Class
    public PositionAdapter (Context context, List<Position> pos) {
        super(context, 0, pos);
    }

    @Override
    public View getView (int index, View convertView, ViewGroup parent){
        //Creation de convertView si il est vide
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_location, parent, false);
        }

        //Obtenir la vue pour le controlleur
        PositionViewHolder viewHolder = (PositionViewHolder) convertView.getTag();
        //Verifie si le viewHolder n'est pas vide
        if (viewHolder == null) {
            viewHolder = new PositionViewHolder();
            viewHolder.city = (TextView) convertView.findViewById(R.id.city);
            viewHolder.code = (TextView) convertView.findViewById(R.id.code);
            viewHolder.state = (TextView) convertView.findViewById(R.id.state);
            viewHolder.country = (TextView) convertView.findViewById(R.id.country);
            convertView.setTag(viewHolder);
        }

        //Chercher la position de la cellule
        Position position = getItem(index);

        //Remplir les cellules
        viewHolder.city.setText(position.getCity());
        viewHolder.code.setText(position.getCode());
        viewHolder.state.setText(position.getState());
        viewHolder.country.setText(position.getCountry());
        viewHolder.latitude= position.getLatitude();
        viewHolder.longitude= position.getLongitude();

        //Retour final des cellules
        return convertView;
    }

    //Controlleur des cellules
    private class PositionViewHolder{
        public TextView city;
        public TextView code;
        public TextView state;
        public TextView country;
        private String latitude;
        private String longitude;
    }
}
