package com.boloutaredoubeni.pokedex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by faraz on 3/1/16.
 */
public class PokeDexArrayAdapter extends ArrayAdapter<PokemonResource> {
    private LayoutInflater mInflater;

    public PokeDexArrayAdapter(Context context, List<PokemonResource> objects) {
        super(context, R.layout.pokemon_item, objects);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.pokemon_item, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String name  = getItem(position).getName();
        holder.name.setText(name);
        return convertView;
    }

    static class ViewHolder {
        TextView name;
    }
}
